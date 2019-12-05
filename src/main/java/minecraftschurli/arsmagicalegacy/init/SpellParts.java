package minecraftschurli.arsmagicalegacy.init;

import minecraftschurli.arsmagicalegacy.*;
import minecraftschurli.arsmagicalegacy.api.*;
import minecraftschurli.arsmagicalegacy.api.skill.*;
import minecraftschurli.arsmagicalegacy.api.spell.*;
import minecraftschurli.arsmagicalegacy.objects.spell.modifier.*;
import minecraftschurli.arsmagicalegacy.objects.spell.shape.*;
import net.minecraft.util.text.*;
import net.minecraftforge.fml.*;

/**
 * @author Minecraftschurli
 * @version 2019-11-16
 */
@SuppressWarnings("unused")
public final class SpellParts implements IInit {
    //TODO: skill trees and (x|y) positions
    //  [src/old/java/am2/defs/SpellDefs.java] am2.defs.SpellDefs
    //  [src/old/java/am2/defs/SkillDefs.java] am2.defs.SkillDefs

    public static final RegistryObject<SpellShape> MISSING_SHAPE = SpellRegistry.registerSpellShape(ArsMagicaLegacy.MODID, "null", null, new MissingShape(), null, 0, 0);

    //skill points
    public static final SkillPoint SILVER_POINT = SkillPointRegistry.registerSkillPoint(-1, new SkillPoint(TextFormatting.GRAY, 0x999999, -1, -1).disableRender());
    public static final SkillPoint SKILL_POINT_1 = SkillPointRegistry.registerSkillPoint(0, new SkillPoint(TextFormatting.BLUE, 0x0000ff, 0, 1));
    public static final SkillPoint SKILL_POINT_3 = SkillPointRegistry.registerSkillPoint(1, new SkillPoint(TextFormatting.RED, 0xff0000, 30, 2));
    public static final SkillPoint SKILL_POINT_2 = SkillPointRegistry.registerSkillPoint(2, new SkillPoint(TextFormatting.GREEN, 0x00ff00, 20, 2));
//    public static final SkillPoint SKILL_POINT_4 = SpellRegistry.registerSkillPoint(new SkillPoint(4, TextFormatting.YELLOW, 0xffff00, 40, 3));
//    public static final SkillPoint SKILL_POINT_5 = SpellRegistry.registerSkillPoint(new SkillPoint(5, TextFormatting.LIGHT_PURPLE, 0xff00ff, 50, 3));
//    public static final SkillPoint SKILL_POINT_6 = SpellRegistry.registerSkillPoint(new SkillPoint(6, TextFormatting.AQUA, 0x00ffff, 60, 4));

    //skill trees
    public static final SkillTree OFFENSE = SkillTreeRegistry.registerSkillTree(ArsMagicaLegacy.MODID, "offense");
    public static final SkillTree DEFENSE = SkillTreeRegistry.registerSkillTree(ArsMagicaLegacy.MODID, "defense");
    public static final SkillTree UTILITY = SkillTreeRegistry.registerSkillTree(ArsMagicaLegacy.MODID, "utility");
    public static final SkillTree AFFINITY = SkillTreeRegistry.registerSkillTree(ArsMagicaLegacy.MODID, "affinity");
    public static final SkillTree TALENT = SkillTreeRegistry.registerSkillTree(ArsMagicaLegacy.MODID, "talent");

    //skills


    //modifiers
    public static final RegistryObject<SpellModifier> BOUNCE = SpellRegistry.registerSpellModifier(ArsMagicaLegacy.MODID, "bounce", SKILL_POINT_1, new Bounce(), OFFENSE, 345, 70, "arsmagicalegacy:projectile");
    public static final RegistryObject<SpellModifier> BUFF_POWER = SpellRegistry.registerSpellModifier(ArsMagicaLegacy.MODID, "buff_power", SILVER_POINT, new BuffPower(), DEFENSE, 30, 135);
    public static final RegistryObject<SpellModifier> COLOR = SpellRegistry.registerSpellModifier(ArsMagicaLegacy.MODID, "color", SKILL_POINT_1, new Color(), TALENT, 230, 75);
    public static final RegistryObject<SpellModifier> DAMAGE = SpellRegistry.registerSpellModifier(ArsMagicaLegacy.MODID, "damage", SKILL_POINT_3, new Damage(), OFFENSE, 300, 315, "arsmagicalegacy:beam");
    public static final RegistryObject<SpellModifier> DISMEMBERING = SpellRegistry.registerSpellModifier(ArsMagicaLegacy.MODID, "dismembering", SILVER_POINT, new Dismembering(), OFFENSE, 75, 225);
    public static final RegistryObject<SpellModifier> DURATION = SpellRegistry.registerSpellModifier(ArsMagicaLegacy.MODID, "duration", SKILL_POINT_3, new Bounce(), DEFENSE, 312, 360, "arsmagicalegacy:chrono_anchor");
    public static final RegistryObject<SpellModifier> FORTUNE = SpellRegistry.registerSpellModifier(ArsMagicaLegacy.MODID, "fortune", SILVER_POINT, new Fortune(), null, 0, 0);
    public static final RegistryObject<SpellModifier> GRAVITY = SpellRegistry.registerSpellModifier(ArsMagicaLegacy.MODID, "gravity", SKILL_POINT_1, new Gravity(), OFFENSE, 255, 70, "arsmagicalegacy:projectile");
    public static final RegistryObject<SpellModifier> HEALING = SpellRegistry.registerSpellModifier(ArsMagicaLegacy.MODID, "healing", SKILL_POINT_3, new Healing(), DEFENSE, 402, 135, "arsmagicalegacy:heal");
    public static final RegistryObject<SpellModifier> LUNAR = SpellRegistry.registerSpellModifier(ArsMagicaLegacy.MODID, "lunar", SKILL_POINT_3, new Lunar(), UTILITY, 145, 210, "arsmagicalegacy:true_sight");
    public static final RegistryObject<SpellModifier> MINING = SpellRegistry.registerSpellModifier(ArsMagicaLegacy.MODID, "mining_power", SKILL_POINT_2, new MiningPower(), UTILITY, 185, 137, "arsmagicalegacy:silk_touch");
    public static final RegistryObject<SpellModifier> PIERCING = SpellRegistry.registerSpellModifier(ArsMagicaLegacy.MODID, "piercing", SKILL_POINT_3, new Piercing(), OFFENSE, 323, 215, "arsmagicalegacy:freeze");
    public static final RegistryObject<SpellModifier> RADIUS = SpellRegistry.registerSpellModifier(ArsMagicaLegacy.MODID, "radius", SKILL_POINT_3, new Radius(), UTILITY, 275, 390, "arsmagicalegacy:channel");
    public static final RegistryObject<SpellModifier> RANGE = SpellRegistry.registerSpellModifier(ArsMagicaLegacy.MODID, "range", SKILL_POINT_3, new Range(), UTILITY, 140, 345, "arsmagicalegacy:blink");
    public static final RegistryObject<SpellModifier> RUNE_PROCS = SpellRegistry.registerSpellModifier(ArsMagicaLegacy.MODID, "rune_procs", SKILL_POINT_2, new RuneProcs(), DEFENSE, 157, 360, "arsmagicalegacy:rune");
    public static final RegistryObject<SpellModifier> SILK_TOUCH = SpellRegistry.registerSpellModifier(ArsMagicaLegacy.MODID, "silk_touch", SKILL_POINT_1, new SilkTouch(), UTILITY, 230, 137, "arsmagicalegacy:dig");
    public static final RegistryObject<SpellModifier> SOLAR = SpellRegistry.registerSpellModifier(ArsMagicaLegacy.MODID, "solar", SKILL_POINT_3, new Solar(), OFFENSE, 210, 255, "arsmagicalegacy:blind");
    public static final RegistryObject<SpellModifier> SPEED = SpellRegistry.registerSpellModifier(ArsMagicaLegacy.MODID, "speed", SKILL_POINT_3, new Speed(), DEFENSE, 202, 315, "arsmagicalegacy:accelerate", "arsmagicalegacy:flight");
    public static final RegistryObject<SpellModifier> TARGET_NON_SOLID_BLOCKS = SpellRegistry.registerSpellModifier(ArsMagicaLegacy.MODID, "target_non_solid", SKILL_POINT_1, new TargetNonSolidBlocks(), UTILITY, 230, 75, "arsmagicalegacy:touch");
    public static final RegistryObject<SpellModifier> VELOCITY_ADDED = SpellRegistry.registerSpellModifier(ArsMagicaLegacy.MODID, "velocity_added", SKILL_POINT_3, new VelocityAdded(), OFFENSE, 390, 290, "arsmagicalegacy:fling");

    //shapes
    public static final RegistryObject<SpellShape> AOE = SpellRegistry.registerSpellShape(ArsMagicaLegacy.MODID, "aoe", SKILL_POINT_2, new AoE(), OFFENSE, 300, 180, "arsmagicalegacy:frost_damage", "arsmagicalegacy:physical_damage", "arsmagicalegacy:fire_damage", "arsmagicalegacy:lightning_damage", "arsmagicalegacy:magic_damage");
    public static final RegistryObject<SpellShape> BEAM = SpellRegistry.registerSpellShape(ArsMagicaLegacy.MODID, "beam", SKILL_POINT_3, new Beam(), OFFENSE, 300, 270, "arsmagicalegacy:aoe");
    public static final RegistryObject<SpellShape> CHAIN = SpellRegistry.registerSpellShape(ArsMagicaLegacy.MODID, "chain", SKILL_POINT_3, new Chain(), UTILITY, 455, 210, "arsmagicalegacy:grow");
    public static final RegistryObject<SpellShape> CHANNEL = SpellRegistry.registerSpellShape(ArsMagicaLegacy.MODID, "channel", SKILL_POINT_2, new MissingShape(), UTILITY, 275, 345, "arsmagicalegacy:attract", "arsmagicalegacy:telekinesis");
    public static final RegistryObject<SpellShape> PROJECTILE = SpellRegistry.registerSpellShape(ArsMagicaLegacy.MODID, "projectile", SKILL_POINT_1, new Projectile(), OFFENSE, 300, 45);
    public static final RegistryObject<SpellShape> RUNE = SpellRegistry.registerSpellShape(ArsMagicaLegacy.MODID, "rune", SKILL_POINT_2, new Rune(), DEFENSE, 157, 315, "arsmagicalegacy:accelerate", "arsmagicalegacy:entangle");
    public static final RegistryObject<SpellShape> SELF = SpellRegistry.registerSpellShape(ArsMagicaLegacy.MODID, "self", SKILL_POINT_1, new Self(), DEFENSE, 267, 45);
    public static final RegistryObject<SpellShape> TOGGLE = SpellRegistry.registerSpellShape(ArsMagicaLegacy.MODID, "toggle", SKILL_POINT_3, new Toggle(), UTILITY, 315, 345, "arsmagicalegacy:channel");
    public static final RegistryObject<SpellShape> TOUCH = SpellRegistry.registerSpellShape(ArsMagicaLegacy.MODID, "touch", SKILL_POINT_1, new Touch(), UTILITY, 275, 75);
    public static final RegistryObject<SpellShape> WALL = SpellRegistry.registerSpellShape(ArsMagicaLegacy.MODID, "wall", SKILL_POINT_2, new Wall(), DEFENSE, 87, 200, "arsmagicalegacy:repel");
    public static final RegistryObject<SpellShape> WAVE = SpellRegistry.registerSpellShape(ArsMagicaLegacy.MODID, "wave", SKILL_POINT_3, new Wave(), OFFENSE, 367, 315, "arsmagicalegacy:beam", "arsmagicalegacy:fling");
    public static final RegistryObject<SpellShape> ZONE = SpellRegistry.registerSpellShape(ArsMagicaLegacy.MODID, "zone", SKILL_POINT_3, new Zone(), DEFENSE, 357, 225, "arsmagicalegacy:dispel");

    //components

    public static void register() {
    }
}
