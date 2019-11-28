package minecraftschurli.arsmagicalegacy.init;

import minecraftschurli.arsmagicalegacy.ArsMagicaLegacy;
import minecraftschurli.arsmagicalegacy.api.spellsystem.SkillPoint;
import minecraftschurli.arsmagicalegacy.api.spellsystem.SpellShape;
import minecraftschurli.arsmagicalegacy.objects.spell.shape.MissingShape;
import minecraftschurli.arsmagicalegacy.util.SpellRegistry;
import net.minecraftforge.fml.RegistryObject;

/**
 * @author Minecraftschurli
 * @version 2019-11-16
 */
public final class SpellParts implements IInit {
    public static final RegistryObject<SpellShape> MISSING_SHAPE = SpellRegistry.registerSpellShape(ArsMagicaLegacy.MODID, "missing_shape", null, SkillPoint.SILVER_POINT, new MissingShape(), null, 0, 0);

    public static void register() {}
}
