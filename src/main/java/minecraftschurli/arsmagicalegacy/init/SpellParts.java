package minecraftschurli.arsmagicalegacy.init;

import minecraftschurli.arsmagicalegacy.ArsMagicaLegacy;
import minecraftschurli.arsmagicalegacy.api.spell.SpellModifier;
import minecraftschurli.arsmagicalegacy.api.spell.SpellShape;
import minecraftschurli.arsmagicalegacy.api.spell.skill.SkillPoint;
import minecraftschurli.arsmagicalegacy.api.spell.skill.SkillTree;
import minecraftschurli.arsmagicalegacy.objects.spell.modifier.*;
import minecraftschurli.arsmagicalegacy.objects.spell.shape.*;
import minecraftschurli.arsmagicalegacy.util.SpellRegistry;
import net.minecraftforge.fml.RegistryObject;

/**
 * @author Minecraftschurli
 * @version 2019-11-16
 */
public final class SpellParts implements IInit {
    //TODO: skill trees and (x|y) positions
    //  [src/old/java/am2/defs/SpellDefs.java]
    //  [src/old/java/am2/defs/SkillDefs.java]

    //skill trees
    public static final SkillTree OFFENSE = new SkillTree(ArsMagicaLegacy.MODID, "offense");
    public static final SkillTree DEFENSE = new SkillTree(ArsMagicaLegacy.MODID, "defence");
    public static final SkillTree UTILITY = new SkillTree(ArsMagicaLegacy.MODID, "utility");
    //public static final SkillTree AFFINITY = new SkillTree(ArsMagicaLegacy.MODID, "affinity");
    public static final SkillTree TALENT = new SkillTree(ArsMagicaLegacy.MODID, "talent");

    //skills


    //modifiers
    public static final RegistryObject<SpellModifier> BOUNCE = SpellRegistry.registerSpellModifier(ArsMagicaLegacy.MODID, "bounce", SkillPoint.SKILL_POINT_1, new Bounce(), null, 0, 0);
    public static final RegistryObject<SpellModifier> BUFF_POWER = SpellRegistry.registerSpellModifier(ArsMagicaLegacy.MODID, "buff_power", SkillPoint.SILVER_POINT, new BuffPower(), null, 0, 0);
    public static final RegistryObject<SpellModifier> COLOR = SpellRegistry.registerSpellModifier(ArsMagicaLegacy.MODID, "color", SkillPoint.SKILL_POINT_1, new Color(), null, 0, 0);
    public static final RegistryObject<SpellModifier> DAMAGE = SpellRegistry.registerSpellModifier(ArsMagicaLegacy.MODID, "damage", SkillPoint.SKILL_POINT_3, new Damage(), null, 0, 0);
    public static final RegistryObject<SpellModifier> DISMEMBERING = SpellRegistry.registerSpellModifier(ArsMagicaLegacy.MODID, "dismembering", SkillPoint.SILVER_POINT, new Dismembering(), null, 0, 0);
    public static final RegistryObject<SpellModifier> DURATION = SpellRegistry.registerSpellModifier(ArsMagicaLegacy.MODID, "duration", SkillPoint.SKILL_POINT_3, new Bounce(), null, 0, 0);
    public static final RegistryObject<SpellModifier> FORTUNE = SpellRegistry.registerSpellModifier(ArsMagicaLegacy.MODID, "fortune", SkillPoint.SILVER_POINT, new Fortune(), null, 0, 0);
    public static final RegistryObject<SpellModifier> GRAVITY = SpellRegistry.registerSpellModifier(ArsMagicaLegacy.MODID, "gravity", SkillPoint.SKILL_POINT_1, new Gravity(), null, 0, 0);
    public static final RegistryObject<SpellModifier> HEALING = SpellRegistry.registerSpellModifier(ArsMagicaLegacy.MODID, "healing", SkillPoint.SKILL_POINT_3, new Healing(), null, 0, 0);
    public static final RegistryObject<SpellModifier> LUNAR = SpellRegistry.registerSpellModifier(ArsMagicaLegacy.MODID, "lunar", SkillPoint.SKILL_POINT_3, new Lunar(), null, 0, 0);
    public static final RegistryObject<SpellModifier> MINING = SpellRegistry.registerSpellModifier(ArsMagicaLegacy.MODID, "mining", SkillPoint.SKILL_POINT_2, new Mining(), null, 0, 0);
    public static final RegistryObject<SpellModifier> MISSING_MODIFIER = SpellRegistry.registerSpellModifier(ArsMagicaLegacy.MODID, "missing_modifier", SkillPoint.SILVER_POINT, new MissingModifier(), null, 0, 0);
    public static final RegistryObject<SpellModifier> PIERCING = SpellRegistry.registerSpellModifier(ArsMagicaLegacy.MODID, "piercing", SkillPoint.SKILL_POINT_3, new Piercing(), null, 0, 0);
    public static final RegistryObject<SpellModifier> RADIUS = SpellRegistry.registerSpellModifier(ArsMagicaLegacy.MODID, "radius", SkillPoint.SKILL_POINT_3, new Radius(), null, 0, 0);
    public static final RegistryObject<SpellModifier> RANGE = SpellRegistry.registerSpellModifier(ArsMagicaLegacy.MODID, "range", SkillPoint.SKILL_POINT_3, new Range(), null, 0, 0);
    public static final RegistryObject<SpellModifier> RUNE_PROCS = SpellRegistry.registerSpellModifier(ArsMagicaLegacy.MODID, "rune_procs", SkillPoint.SKILL_POINT_2, new RuneProcs(), null, 0, 0);
    public static final RegistryObject<SpellModifier> SILK_TOUCH = SpellRegistry.registerSpellModifier(ArsMagicaLegacy.MODID, "silk_touch", SkillPoint.SKILL_POINT_1, new SilkTouch(), null, 0, 0);
    public static final RegistryObject<SpellModifier> SOLAR = SpellRegistry.registerSpellModifier(ArsMagicaLegacy.MODID, "solar", SkillPoint.SKILL_POINT_3, new Solar(), null, 0, 0);
    public static final RegistryObject<SpellModifier> SPEED = SpellRegistry.registerSpellModifier(ArsMagicaLegacy.MODID, "speed", SkillPoint.SKILL_POINT_3, new Speed(), null, 0, 0);
    public static final RegistryObject<SpellModifier> TARGET_NON_SOLID_BLOCKS = SpellRegistry.registerSpellModifier(ArsMagicaLegacy.MODID, "target_non_solid_blocks", SkillPoint.SKILL_POINT_1, new TargetNonSolidBlocks(), null, 0, 0);
    public static final RegistryObject<SpellModifier> VELOCITY_ADDED = SpellRegistry.registerSpellModifier(ArsMagicaLegacy.MODID, "velocity_added", SkillPoint.SKILL_POINT_3, new VelocityAdded(), null, 0, 0);
    
    //shapes
    public static final RegistryObject<SpellShape> AOE = SpellRegistry.registerSpellShape(ArsMagicaLegacy.MODID, "aoe", SkillPoint.SKILL_POINT_2, new AoE(), OFFENSE, 300, 180, "arsmagicalegacy:frost_damage", "arsmagicalegacy:physical_damage", "arsmagicalegacy:fire_damage", "arsmagicalegacy:lightning_damage", "arsmagicalegacy:magic_damage");//done
    public static final RegistryObject<SpellShape> BEAM = SpellRegistry.registerSpellShape(ArsMagicaLegacy.MODID, "beam", SkillPoint.SKILL_POINT_3, new Beam(), null, 0, 0);
    public static final RegistryObject<SpellShape> CHAIN = SpellRegistry.registerSpellShape(ArsMagicaLegacy.MODID, "chain", SkillPoint.SKILL_POINT_3, new Chain(), null, 0, 0);
    public static final RegistryObject<SpellShape> CHANNEL = SpellRegistry.registerSpellShape(ArsMagicaLegacy.MODID, "channel", SkillPoint.SKILL_POINT_2, new MissingShape(), null, 0, 0);
    public static final RegistryObject<SpellShape> MISSING_SHAPE = SpellRegistry.registerSpellShape(ArsMagicaLegacy.MODID, "null", null, new MissingShape(), null, 0, 0);
    public static final RegistryObject<SpellShape> PROJECTILE = SpellRegistry.registerSpellShape(ArsMagicaLegacy.MODID, "projectile", SkillPoint.SKILL_POINT_1, new Projectile(), OFFENSE, 300, 45);//done
    public static final RegistryObject<SpellShape> RUNE = SpellRegistry.registerSpellShape(ArsMagicaLegacy.MODID, "rune", SkillPoint.SKILL_POINT_2, new Rune(), null, 0, 0);
    public static final RegistryObject<SpellShape> SELF = SpellRegistry.registerSpellShape(ArsMagicaLegacy.MODID, "self", SkillPoint.SKILL_POINT_1, new Self(), DEFENSE, 267, 45);//done
    public static final RegistryObject<SpellShape> TOGGLE = SpellRegistry.registerSpellShape(ArsMagicaLegacy.MODID, "toggle", SkillPoint.SKILL_POINT_3, new Toggle(), null, 0, 0);
    public static final RegistryObject<SpellShape> TOUCH = SpellRegistry.registerSpellShape(ArsMagicaLegacy.MODID, "touch", SkillPoint.SKILL_POINT_1, new Touch(), null, 0, 0);
    public static final RegistryObject<SpellShape> WALL = SpellRegistry.registerSpellShape(ArsMagicaLegacy.MODID, "wall", SkillPoint.SKILL_POINT_2, new Wall(), DEFENSE, 87, 200, "arsmagicalegacy:repel");//done
    public static final RegistryObject<SpellShape> WAVE = SpellRegistry.registerSpellShape(ArsMagicaLegacy.MODID, "wave", SkillPoint.SKILL_POINT_3, new Wave(), null, 0, 0);
    public static final RegistryObject<SpellShape> ZONE = SpellRegistry.registerSpellShape(ArsMagicaLegacy.MODID, "zone", SkillPoint.SKILL_POINT_3, new Zone(), null, 0, 0);

    //components
    
    public static void register() {}
}
