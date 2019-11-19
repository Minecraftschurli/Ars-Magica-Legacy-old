package minecraftschurli.arsmagicalegacy.init;

import minecraftschurli.arsmagicalegacy.api.spellsystem.SpellComponent;
import minecraftschurli.arsmagicalegacy.api.spellsystem.SpellModifier;
import minecraftschurli.arsmagicalegacy.api.spellsystem.SpellShape;
import minecraftschurli.arsmagicalegacy.objects.spell.component.FallingStar;
import minecraftschurli.arsmagicalegacy.objects.spell.modifier.Color;
import minecraftschurli.arsmagicalegacy.objects.spell.shape.AOE;
import net.minecraftforge.fml.RegistryObject;

/**
 * @author Minecraftschurli
 * @version 2019-11-16
 */
public final class SpellParts implements IInit {
    public static final RegistryObject<SpellShape> SHAPE_AOE = SPELL_SHAPES.register("aoe", AOE::new);

    public static final RegistryObject<SpellModifier> MODIFIER_COLOR = SPELL_MODIFIERS.register("color", Color::new);

    public static final RegistryObject<SpellComponent> COMPONENT_FALLING_STAR = SPELL_COMPONENTS.register("falling_star", FallingStar::new);

    public static void register() {}
}
