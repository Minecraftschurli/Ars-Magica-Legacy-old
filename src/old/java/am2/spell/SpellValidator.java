package am2.spell;

import java.util.ArrayList;

import am2.api.SpellRegistry;
import am2.api.spell.AbstractSpellPart;
import am2.api.spell.SpellComponent;
import am2.api.spell.SpellModifier;
import am2.api.spell.SpellShape;
import am2.spell.component.Summon;
import net.minecraft.util.text.translation.I18n;

@SuppressWarnings("deprecation")
public class SpellValidator{
	public static final SpellValidator instance = new SpellValidator();

	private enum StageValidations{
		VALID,
		TERMINUS,
		PRINCIPUM,
		NOT_VALID
	}

	private SpellValidator(){

	}

	public ValidationResult spellDefIsValid(ArrayList<ArrayList<AbstractSpellPart>> shapeGroups, ArrayList<ArrayList<AbstractSpellPart>> segmented){
		boolean noParts = true;
		for (int i = 0; i < shapeGroups.size(); ++i)
			if (shapeGroups.get(i).size() > 0)
				noParts = false;
		for (int i = 0; i < segmented.size(); ++i)
			if (segmented.get(i).size() > 0)
				noParts = false;
		if (noParts) return new ValidationResult(null, "");

		boolean validatedAny = false;
		for (int x = 0; x < shapeGroups.size(); ++x){
			if (shapeGroups.get(x).size() > 0){
				if (segmented.size() == 0){
					ValidationResult result = internalValidation(splitToStages(shapeGroups.get(x)));
					if (result != null)
						return result;
				}
				ArrayList<AbstractSpellPart> concatenated = new ArrayList<AbstractSpellPart>();
				concatenated.addAll(shapeGroups.get(x));
				for (int i = 0; i < segmented.size(); ++i){
					concatenated.addAll(segmented.get(i));
				}
				ValidationResult result = internalValidation(splitToStages(concatenated));
				if (result != null)
					return result;

				validatedAny = true;
			}
		}

		if (!validatedAny){
			ValidationResult result = internalValidation(segmented);
			if (result != null)
				return result;
		}

		return new ValidationResult();
	}

	private ValidationResult internalValidation(ArrayList<ArrayList<AbstractSpellPart>> segmented){
		for (int i = 0; i < segmented.size(); ++i){
			StageValidations result = validateStage(segmented.get(i), i == segmented.size() - 1);

			if (result == StageValidations.NOT_VALID){
				return new ValidationResult(segmented.get(i).get(0), I18n.translateToLocal("am2.spell.validate.compMiss"));
			}else if (result == StageValidations.PRINCIPUM && i == segmented.size() - 1){
				return new ValidationResult(segmented.get(i).get(0), String.format("%s %s", SpellRegistry.getSkillFromPart(segmented.get(i).get(0)), I18n.translateToLocal("am2.spell.validate.principum")));
			}else if (result == StageValidations.TERMINUS && i < segmented.size() - 1){
				return new ValidationResult(segmented.get(i).get(0), String.format("%s %s", SpellRegistry.getSkillFromPart(segmented.get(i).get(0)), I18n.translateToLocal("am2.spell.validate.terminus")));
			}
		}

		return null;
	}

	private StageValidations validateStage(ArrayList<AbstractSpellPart> stageDefinition, boolean isFinalStage){
		boolean terminus = false;
		boolean principum = false;
		boolean one_component = !isFinalStage;
		boolean one_shape = false;
		for (AbstractSpellPart part : stageDefinition){
			if (part instanceof Summon) return StageValidations.TERMINUS;
			if (part instanceof SpellShape){
				one_shape = true;
				if (((SpellShape)part).isTerminusShape())
					terminus = true;
				if (((SpellShape)part).isPrincipumShape())
					principum = true;
				continue;
			}
			if (part instanceof SpellComponent){
				one_component = true;
				continue;
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

	public static ArrayList<ArrayList<AbstractSpellPart>> splitToStages(ArrayList<AbstractSpellPart> currentRecipe){
		ArrayList<ArrayList<AbstractSpellPart>> segmented = new ArrayList<ArrayList<AbstractSpellPart>>();
		int idx = (currentRecipe.size() > 0 && currentRecipe.get(0) instanceof SpellShape) ? -1 : 0;
		for (int i = 0; i < currentRecipe.size(); ++i){
			AbstractSpellPart part = currentRecipe.get(i);
			if (part instanceof SpellShape)
				idx++;
			if (segmented.size() - 1 < idx) //while loop not necessary as this will keep up
				segmented.add(new ArrayList<AbstractSpellPart>());
			segmented.get(idx).add(part);
		}
		return segmented;
	}

	public boolean modifierCanBeAdded(SpellModifier modifier){
		return false;
	}

	public class ValidationResult{
		public final boolean valid;
		public final AbstractSpellPart offendingPart;
		public final String message;

		public ValidationResult(AbstractSpellPart offendingPart, String message){
			valid = false;
			this.offendingPart = offendingPart;
			this.message = message;
		}

		public ValidationResult(){
			valid = true;
			this.offendingPart = null;
			this.message = "";
		}
	}
}
