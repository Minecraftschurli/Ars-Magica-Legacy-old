package minecraftschurli.arsmagicalegacy.init;

import minecraftschurli.arsmagicalegacy.ArsMagicaLegacy;
import minecraftschurli.arsmagicalegacy.api.spellsystem.SkillPoint;
import minecraftschurli.arsmagicalegacy.api.spellsystem.SpellModifier;
import minecraftschurli.arsmagicalegacy.api.spellsystem.SpellShape;
import minecraftschurli.arsmagicalegacy.objects.spell.modifier.*;
import minecraftschurli.arsmagicalegacy.objects.spell.shape.*;
import minecraftschurli.arsmagicalegacy.util.SpellRegistry;
import net.minecraftforge.fml.RegistryObject;

/**
 * @author Minecraftschurli
 * @version 2019-11-16
 */
public final class SpellParts implements IInit {
    public static final RegistryObject<SpellModifier> BOUNCE = SpellRegistry.registerSpellModifier(ArsMagicaLegacy.MODID, "bounce", null, SkillPoint.SKILL_POINT_1, new Bounce(), null, 0, 0);
    public static final RegistryObject<SpellModifier> BUFF_POWER = SpellRegistry.registerSpellModifier(ArsMagicaLegacy.MODID, "buff_power", null, SkillPoint.SILVER_POINT, new BuffPower(), null, 0, 0);
    public static final RegistryObject<SpellModifier> COLOR = SpellRegistry.registerSpellModifier(ArsMagicaLegacy.MODID, "color", null, SkillPoint.SKILL_POINT_1, new Color(), null, 0, 0);
    public static final RegistryObject<SpellModifier> DAMAGE = SpellRegistry.registerSpellModifier(ArsMagicaLegacy.MODID, "damage", null, SkillPoint.SKILL_POINT_3, new Damage(), null, 0, 0);
    public static final RegistryObject<SpellModifier> DISMEMBERING = SpellRegistry.registerSpellModifier(ArsMagicaLegacy.MODID, "dismembering", null, SkillPoint.SILVER_POINT, new Dismembering(), null, 0, 0);
    public static final RegistryObject<SpellModifier> DURATION = SpellRegistry.registerSpellModifier(ArsMagicaLegacy.MODID, "duration", null, SkillPoint.SKILL_POINT_3, new Bounce(), null, 0, 0);
    public static final RegistryObject<SpellModifier> FORTUNE = SpellRegistry.registerSpellModifier(ArsMagicaLegacy.MODID, "fortune", null, SkillPoint.SILVER_POINT, new Fortune(), null, 0, 0);
    public static final RegistryObject<SpellModifier> GRAVITY = SpellRegistry.registerSpellModifier(ArsMagicaLegacy.MODID, "gravity", null, SkillPoint.SKILL_POINT_1, new Gravity(), null, 0, 0);
    public static final RegistryObject<SpellModifier> HEALING = SpellRegistry.registerSpellModifier(ArsMagicaLegacy.MODID, "healing", null, SkillPoint.SKILL_POINT_3, new Healing(), null, 0, 0);
    public static final RegistryObject<SpellModifier> LUNAR = SpellRegistry.registerSpellModifier(ArsMagicaLegacy.MODID, "lunar", null, SkillPoint.SKILL_POINT_3, new Lunar(), null, 0, 0);
    public static final RegistryObject<SpellModifier> MINING = SpellRegistry.registerSpellModifier(ArsMagicaLegacy.MODID, "mining", null, SkillPoint.SKILL_POINT_2, new Mining(), null, 0, 0);
    public static final RegistryObject<SpellModifier> MISSING_MODIFIER = SpellRegistry.registerSpellModifier(ArsMagicaLegacy.MODID, "missing_modifier", null, SkillPoint.SILVER_POINT, new MissingModifier(), null, 0, 0);
    public static final RegistryObject<SpellModifier> PIERCING = SpellRegistry.registerSpellModifier(ArsMagicaLegacy.MODID, "piercing", null, SkillPoint.SKILL_POINT_3, new Piercing(), null, 0, 0);
    public static final RegistryObject<SpellModifier> RADIUS = SpellRegistry.registerSpellModifier(ArsMagicaLegacy.MODID, "radius", null, SkillPoint.SKILL_POINT_3, new Radius(), null, 0, 0);
    public static final RegistryObject<SpellModifier> RANGE = SpellRegistry.registerSpellModifier(ArsMagicaLegacy.MODID, "range", null, SkillPoint.SKILL_POINT_3, new Range(), null, 0, 0);
    public static final RegistryObject<SpellModifier> RUNE_PROCS = SpellRegistry.registerSpellModifier(ArsMagicaLegacy.MODID, "rune_procs", null, SkillPoint.SKILL_POINT_2, new RuneProcs(), null, 0, 0);
    public static final RegistryObject<SpellModifier> SILK_TOUCH = SpellRegistry.registerSpellModifier(ArsMagicaLegacy.MODID, "silk_touch", null, SkillPoint.SKILL_POINT_1, new SilkTouch(), null, 0, 0);
    public static final RegistryObject<SpellModifier> SOLAR = SpellRegistry.registerSpellModifier(ArsMagicaLegacy.MODID, "solar", null, SkillPoint.SKILL_POINT_3, new Solar(), null, 0, 0);
    public static final RegistryObject<SpellModifier> SPEED = SpellRegistry.registerSpellModifier(ArsMagicaLegacy.MODID, "speed", null, SkillPoint.SKILL_POINT_3, new Speed(), null, 0, 0);
    public static final RegistryObject<SpellModifier> TARGET_NON_SOLID_BLOCKS = SpellRegistry.registerSpellModifier(ArsMagicaLegacy.MODID, "target_non_solid_blocks", null, SkillPoint.SKILL_POINT_1, new TargetNonSolidBlocks(), null, 0, 0);
    public static final RegistryObject<SpellModifier> VELOCITY_ADDED = SpellRegistry.registerSpellModifier(ArsMagicaLegacy.MODID, "velocity_added", null, SkillPoint.SKILL_POINT_3, new VelocityAdded(), null, 0, 0);
    public static final RegistryObject<SpellShape> AOE = SpellRegistry.registerSpellShape(ArsMagicaLegacy.MODID, "aoe", null, SkillPoint.SKILL_POINT_2, new AoE(), null, 0, 0);
    public static final RegistryObject<SpellShape> BEAM = SpellRegistry.registerSpellShape(ArsMagicaLegacy.MODID, "beam", null, SkillPoint.SKILL_POINT_3, new Beam(), null, 0, 0);
    public static final RegistryObject<SpellShape> CHAIN = SpellRegistry.registerSpellShape(ArsMagicaLegacy.MODID, "chain", null, SkillPoint.SKILL_POINT_3, new Chain(), null, 0, 0);
    public static final RegistryObject<SpellShape> CHANNEL = SpellRegistry.registerSpellShape(ArsMagicaLegacy.MODID, "channel", null, SkillPoint.SKILL_POINT_2, new MissingShape(), null, 0, 0);
    public static final RegistryObject<SpellShape> MISSING_SHAPE = SpellRegistry.registerSpellShape(ArsMagicaLegacy.MODID, "missing_shape", null, SkillPoint.SILVER_POINT, new MissingShape(), null, 0, 0);
    public static final RegistryObject<SpellShape> PROJECTILE = SpellRegistry.registerSpellShape(ArsMagicaLegacy.MODID, "projectile", null, SkillPoint.SKILL_POINT_1, new Projectile(), null, 0, 0);
    public static final RegistryObject<SpellShape> RUNE = SpellRegistry.registerSpellShape(ArsMagicaLegacy.MODID, "rune", null, SkillPoint.SKILL_POINT_2, new Rune(), null, 0, 0);
    public static final RegistryObject<SpellShape> SELF = SpellRegistry.registerSpellShape(ArsMagicaLegacy.MODID, "self", null, SkillPoint.SKILL_POINT_1, new Self(), null, 0, 0);
    public static final RegistryObject<SpellShape> TOGGLE = SpellRegistry.registerSpellShape(ArsMagicaLegacy.MODID, "toggle", null, SkillPoint.SKILL_POINT_3, new Toggle(), null, 0, 0);
    public static final RegistryObject<SpellShape> TOUCH = SpellRegistry.registerSpellShape(ArsMagicaLegacy.MODID, "touch", null, SkillPoint.SKILL_POINT_1, new Touch(), null, 0, 0);
    public static final RegistryObject<SpellShape> WALL = SpellRegistry.registerSpellShape(ArsMagicaLegacy.MODID, "wall", null, SkillPoint.SKILL_POINT_2, new Wall(), null, 0, 0);
    public static final RegistryObject<SpellShape> WAVE = SpellRegistry.registerSpellShape(ArsMagicaLegacy.MODID, "wave", null, SkillPoint.SKILL_POINT_3, new Wave(), null, 0, 0);
    public static final RegistryObject<SpellShape> ZONE = SpellRegistry.registerSpellShape(ArsMagicaLegacy.MODID, "zone", null, SkillPoint.SKILL_POINT_3, new Zone(), null, 0, 0);

    public static void register() {
    }
}
