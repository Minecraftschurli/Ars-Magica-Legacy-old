package minecraftschurli.arsmagicalegacy.objects.spell;

import java.util.ArrayList;
import java.util.List;
import minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import minecraftschurli.arsmagicalegacy.api.registry.SpellRegistry;
import minecraftschurli.arsmagicalegacy.api.spell.AbstractSpellPart;
import minecraftschurli.arsmagicalegacy.api.spell.SpellComponent;
import minecraftschurli.arsmagicalegacy.api.spell.SpellModifier;
import minecraftschurli.arsmagicalegacy.api.spell.SpellShape;
import minecraftschurli.arsmagicalegacy.objects.spell.component.Summon;
import net.minecraft.util.text.TranslationTextComponent;

public class SpellValidator {
    public static final SpellValidator instance = new SpellValidator();

    private SpellValidator() {
    }

    public static List<List<AbstractSpellPart>> splitToStages(List<AbstractSpellPart> currentRecipe) {
        List<List<AbstractSpellPart>> segmented = new ArrayList<>();
        int idx = (currentRecipe.size() > 0 && currentRecipe.get(0) instanceof SpellShape) ? -1 : 0;
        for (AbstractSpellPart part : currentRecipe) {
            if (part instanceof SpellShape)
                idx++;
            if (segmented.size() - 1 < idx) //while loop not necessary as this will keep up
                segmented.add(new ArrayList<AbstractSpellPart>());
            segmented.get(idx).add(part);
        }
        return segmented;
    }

    public ValidationResult spellDefIsValid(List<List<AbstractSpellPart>> shapeGroups, List<List<AbstractSpellPart>> segmented) {
        boolean noParts = true;
        for (int i = 0; i < shapeGroups.size(); i++)
            if (shapeGroups.get(i).size() > 0)
                noParts = false;
        for (int i = 0; i < segmented.size(); i++)
            if (segmented.get(i).size() > 0)
                noParts = false;
        if (noParts) return new ValidationResult(null, "");
        boolean validatedAny = false;
        for (int x = 0; x < shapeGroups.size(); x++) {
            if (shapeGroups.get(x).size() > 0) {
                if (segmented.size() == 0) {
                    ValidationResult result = internalValidation(splitToStages(shapeGroups.get(x)));
                    if (result != null)
                        return result;
                }
                ArrayList<AbstractSpellPart> concatenated = new ArrayList<AbstractSpellPart>();
                concatenated.addAll(shapeGroups.get(x));
                for (int i = 0; i < segmented.size(); i++) {
                    concatenated.addAll(segmented.get(i));
                }
                ValidationResult result = internalValidation(splitToStages(concatenated));
                if (result != null)
                    return result;
                validatedAny = true;
            }
        }
        if (!validatedAny) {
            ValidationResult result = internalValidation(segmented);
            if (result != null)
                return result;
        }
        return new ValidationResult();
    }

    private ValidationResult internalValidation(List<List<AbstractSpellPart>> segmented) {
        for (int i = 0; i < segmented.size(); i++) {
            StageValidations result = validateStage(segmented.get(i), i == segmented.size() - 1);
            if (result == StageValidations.NOT_VALID) {
                return new ValidationResult(segmented.get(i).get(0), new TranslationTextComponent(ArsMagicaAPI.MODID + ".spell.validate.invalid").getFormattedText());
            } else if (result == StageValidations.PRINCIPUM && i == segmented.size() - 1) {
                return new ValidationResult(segmented.get(i).get(0), String.format("%s %s", SpellRegistry.getSkillFromPart(segmented.get(i).get(0)).getName().getFormattedText(), new TranslationTextComponent(ArsMagicaAPI.MODID + "spell.validate.principum").getFormattedText()));
            } else if (result == StageValidations.TERMINUS && i < segmented.size() - 1) {
                return new ValidationResult(segmented.get(i).get(0), String.format("%s %s", SpellRegistry.getSkillFromPart(segmented.get(i).get(0)).getName().getFormattedText(), new TranslationTextComponent(ArsMagicaAPI.MODID + "spell.validate.terminus").getFormattedText()));
            }
        }
        return null;
    }

    private StageValidations validateStage(List<AbstractSpellPart> stageDefinition, boolean isFinalStage) {
        boolean terminus = false;
        boolean principum = false;
        boolean one_component = !isFinalStage;
        boolean one_shape = false;
        for (AbstractSpellPart part : stageDefinition) {
            if (part instanceof Summon) return StageValidations.TERMINUS;
            if (part instanceof SpellShape) {
                one_shape = true;
                if (((SpellShape) part).isTerminusShape())
                    terminus = true;
                if (((SpellShape) part).isPrincipumShape())
                    principum = true;
                continue;
            }
            if (part instanceof SpellComponent) {
                one_component = true;
            }
        }
        if (principum)
            return StageValidations.PRINCIPUM;
        if (!one_component || !one_shape)
            return StageValidations.NOT_VALID;
        if (terminus)
            return StageValidations.TERMINUS;
        return StageValidations.VALID;
    }

    public boolean modifierCanBeAdded(SpellModifier modifier) {
        return false;
    }

    private enum StageValidations {
        VALID,
        TERMINUS,
        PRINCIPUM,
        NOT_VALID
    }

    public static class ValidationResult {
        public final boolean valid;
        public final AbstractSpellPart offendingPart;
        public final String message;

        public ValidationResult(AbstractSpellPart offendingPart, String message) {
            valid = false;
            this.offendingPart = offendingPart;
            this.message = message;
        }

        public ValidationResult() {
            valid = true;
            this.offendingPart = null;
            this.message = "";
        }
    }
}
