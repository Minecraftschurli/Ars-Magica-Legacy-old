package minecraftschurli.arsmagicalegacy.init;

import minecraftschurli.arsmagicalegacy.ArsMagicaLegacy;
import minecraftschurli.arsmagicalegacy.api.SkillPointRegistry;
import minecraftschurli.arsmagicalegacy.api.SkillTreeRegistry;
import minecraftschurli.arsmagicalegacy.api.SpellRegistry;
import minecraftschurli.arsmagicalegacy.api.skill.SkillPoint;
import minecraftschurli.arsmagicalegacy.api.skill.SkillTree;
import minecraftschurli.arsmagicalegacy.api.spell.SpellComponent;
import minecraftschurli.arsmagicalegacy.api.spell.SpellModifier;
import minecraftschurli.arsmagicalegacy.api.spell.SpellShape;
import minecraftschurli.arsmagicalegacy.objects.spell.component.*;
import minecraftschurli.arsmagicalegacy.objects.spell.modifier.*;
import minecraftschurli.arsmagicalegacy.objects.spell.shape.*;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.RegistryObject;

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
    public static final RegistryObject<SpellModifier> MINING_POWER = SpellRegistry.registerSpellModifier(ArsMagicaLegacy.MODID, "mining_power", SKILL_POINT_2, new MiningPower(), UTILITY, 185, 137, "arsmagicalegacy:silk_touch");
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
    public static final RegistryObject<SpellComponent> ABSORPTION = SpellRegistry.registerSpellComponent(ArsMagicaLegacy.MODID, "absorption", SKILL_POINT_3, new Absorption(), DEFENSE, 312, 270, "arsmagicalegacy:shield");
    public static final RegistryObject<SpellComponent> ACCELERATE = SpellRegistry.registerSpellComponent(ArsMagicaLegacy.MODID, "accelerate", SKILL_POINT_2, new Accelerate(), DEFENSE, 177, 245, "arsmagicalegacy:swift_swim");
    /*public static final RegistryObject<SpellComponent> APPROPRIATON = SpellRegistry.registerSpellComponent(ArsMagicaLegacy.MODID, "appropriation", SKILL_POINT_3, new Appropriation(), DEFENSE, 87, 245, "arsmagicalegacy:entangle");
    public static final RegistryObject<SpellComponent> ASTRAL_DISTORTION = SpellRegistry.registerSpellComponent(ArsMagicaLegacy.MODID, "astral_distortion", SKILL_POINT_2, new AstralDistortion(), OFFENSE, 367, 215, "arsmagicalegacy:magic_damage", "arsmagicalegacy:frost_damage");
    public static final RegistryObject<SpellComponent> ATTRACT = SpellRegistry.registerSpellComponent(ArsMagicaLegacy.MODID, "attract", SKILL_POINT_2, new Attract(), UTILITY, 245, 300, "arsmagicalegacy:rift");
    public static final RegistryObject<SpellComponent> BANISH_RAIN = SpellRegistry.registerSpellComponent(ArsMagicaLegacy.MODID, "banish_rain", SKILL_POINT_2, new BanishRain(), UTILITY, 365, 345, "arsmagicalegacy:drought");
    public static final RegistryObject<SpellComponent> BLIND = SpellRegistry.registerSpellComponent(ArsMagicaLegacy.MODID, "blind", SKILL_POINT_2, new Blind(), OFFENSE, 233, 180, "arsmagicalegacy:fire_damage", "arsmagicalegacy:lightning_damage");
    public static final RegistryObject<SpellComponent> BLINK = SpellRegistry.registerSpellComponent(ArsMagicaLegacy.MODID, "blink", SKILL_POINT_2, new Blink(), UTILITY, 185, 345, "arsmagicalegacy:random_teleport");
    public static final RegistryObject<SpellComponent> BLIZZARD = SpellRegistry.registerSpellComponent(ArsMagicaLegacy.MODID, "blizzard", SILVER_POINT, new Blizzard(), OFFENSE, 75, 45);
    public static final RegistryObject<SpellComponent> CHARM = SpellRegistry.registerSpellComponent(ArsMagicaLegacy.MODID, "charm", SKILL_POINT_1, new Charm(), UTILITY, 315, 235, "arsmagicalegacy:light");
    public static final RegistryObject<SpellComponent> CHRONO_ANCHOR = SpellRegistry.registerSpellComponent(ArsMagicaLegacy.MODID, "chrono_anchor", SKILL_POINT_3, new ChronoAnchor(), DEFENSE, 312, 315, "arsmagicalegacy:reflect");
    public static final RegistryObject<SpellComponent> CREATE_WATER = SpellRegistry.registerSpellComponent(ArsMagicaLegacy.MODID, "create_water", SKILL_POINT_2, new CreateWater(), UTILITY, 365, 255, "arsmagicalegacy:light");
    public static final RegistryObject<SpellComponent> DAYLIGHT = SpellRegistry.registerSpellComponent(ArsMagicaLegacy.MODID, "daylight", SILVER_POINT, new Daylight(), UTILITY, 75, 45);
    public static final RegistryObject<SpellComponent> DIG = SpellRegistry.registerSpellComponent(ArsMagicaLegacy.MODID, "dig", SKILL_POINT_1, new Dig(), UTILITY, 275, 120, "arsmagicalegacy:touch");
    public static final RegistryObject<SpellComponent> DISARM = SpellRegistry.registerSpellComponent(ArsMagicaLegacy.MODID, "disarm", SKILL_POINT_1, new Disarm(), UTILITY, 230, 210, "arsmagicalegacy:light");
    public static final RegistryObject<SpellComponent> DISPEL = SpellRegistry.registerSpellComponent(ArsMagicaLegacy.MODID, "dispel", SKILL_POINT_2, new Dispel(), DEFENSE, 357, 180, "arsmagicalegacy:heal");
    public static final RegistryObject<SpellComponent> DIVINE_INTERVENTION = SpellRegistry.registerSpellComponent(ArsMagicaLegacy.MODID, "divine_intervention", SKILL_POINT_3, new DivineIntervention(), UTILITY, 172, 480, "arsmagicalegacy:recall", "arsmagicalegacy:mark");
    public static final RegistryObject<SpellComponent> DROUGHT = SpellRegistry.registerSpellComponent(ArsMagicaLegacy.MODID, "drought", SKILL_POINT_2, new Drought(), UTILITY, 365, 300, "arsmagicalegacy:light");
    public static final RegistryObject<SpellComponent> DROWN = SpellRegistry.registerSpellComponent(ArsMagicaLegacy.MODID, "drown", SKILL_POINT_1, new Drown(), OFFENSE, 435, 135, "arsmagicalegacy:magic_damage");*/
    public static final RegistryObject<SpellComponent> ENDER_INTERVENTION = SpellRegistry.registerSpellComponent(ArsMagicaLegacy.MODID, "ender_intervention", SKILL_POINT_3, new EnderIntervention(), UTILITY, 198, 480, "arsmagicalegacy:recall", "arsmagicalegacy:mark");
    public static final RegistryObject<SpellComponent> ENTANGLE = SpellRegistry.registerSpellComponent(ArsMagicaLegacy.MODID, "entangle", SKILL_POINT_2, new Entangle(), DEFENSE, 132, 245, "arsmagicalegacy:repel");
    public static final RegistryObject<SpellComponent> FALLING_STAR = SpellRegistry.registerSpellComponent(ArsMagicaLegacy.MODID, "falling_star", SILVER_POINT, new FallingStar(), OFFENSE, 75, 90);
    public static final RegistryObject<SpellComponent> FIRE_DAMAGE = SpellRegistry.registerSpellComponent(ArsMagicaLegacy.MODID, "fire_damage", SKILL_POINT_1, new FireDamage(), OFFENSE, 210, 135, "arsmagicalegacy:physical_damage");
    public static final RegistryObject<SpellComponent> FIRE_RAIN = SpellRegistry.registerSpellComponent(ArsMagicaLegacy.MODID, "fire_rain", SILVER_POINT, new FireRain(), OFFENSE, 75, 135);
    public static final RegistryObject<SpellComponent> FLIGHT = SpellRegistry.registerSpellComponent(ArsMagicaLegacy.MODID, "flight", SKILL_POINT_3, new Flight(), DEFENSE, 222, 270, "arsmagicalegacy:levitate");
    public static final RegistryObject<SpellComponent> FLING = SpellRegistry.registerSpellComponent(ArsMagicaLegacy.MODID, "fling", SKILL_POINT_2, new Fling(), OFFENSE, 390, 245, "arsmagicalegacy:knockback");
    //    public static final RegistryObject<SpellComponent> FORGE = SpellRegistry.registerSpellComponent(ArsMagicaLegacy.MODID, "forge", SKILL_POINT_2, new Forge(), OFFENSE, 120, 135, "arsmagicalegacy:ignition");
    public static final RegistryObject<SpellComponent> FREEZE = SpellRegistry.registerSpellComponent(ArsMagicaLegacy.MODID, "freeze", SKILL_POINT_2, new Freeze(), OFFENSE, 345, 180, "arsmagicalegacy:frost_damage");
    public static final RegistryObject<SpellComponent> FROST_DAMAGE = SpellRegistry.registerSpellComponent(ArsMagicaLegacy.MODID, "frost_damage", SKILL_POINT_1, new FrostDamage(), OFFENSE, 345, 135, "arsmagicalegacy:magic_damage");
    public static final RegistryObject<SpellComponent> FURY = SpellRegistry.registerSpellComponent(ArsMagicaLegacy.MODID, "fury", SKILL_POINT_3, new Fury(), OFFENSE, 255, 315, "arsmagicalegacy:beam", "arsmagicalegacy:storm");
    public static final RegistryObject<SpellComponent> GRAVITY_WELL = SpellRegistry.registerSpellComponent(ArsMagicaLegacy.MODID, "gravity_well", SKILL_POINT_2, new GravityWell(), DEFENSE, 222, 180, "arsmagicalegacy:slowfall");
    public static final RegistryObject<SpellComponent> GROW = SpellRegistry.registerSpellComponent(ArsMagicaLegacy.MODID, "grow", SKILL_POINT_3, new Grow(), UTILITY, 410, 210, "arsmagicalegacy:drought", "arsmagicalegacy:create_water", "arsmagicalegacy:plant", "arsmagicalegacy:plow", "arsmagicalegacy:harvest_plants");
    public static final RegistryObject<SpellComponent> HARVEST_PLANTS = SpellRegistry.registerSpellComponent(ArsMagicaLegacy.MODID, "harvest_plants", SKILL_POINT_2, new HarvestPlants(), UTILITY, 365, 120, "arsmagicalegacy:light");
    public static final RegistryObject<SpellComponent> HASTE = SpellRegistry.registerSpellComponent(ArsMagicaLegacy.MODID, "haste", SKILL_POINT_1, new Haste(), DEFENSE, 177, 155, "arsmagicalegacy:slowfall");
    public static final RegistryObject<SpellComponent> HEAL = SpellRegistry.registerSpellComponent(ArsMagicaLegacy.MODID, "heal", SKILL_POINT_1, new Heal(), DEFENSE, 357, 135, "arsmagicalegacy:regeneration");
    public static final RegistryObject<SpellComponent> IGNITION = SpellRegistry.registerSpellComponent(ArsMagicaLegacy.MODID, "ignition", SKILL_POINT_2, new Ignition(), OFFENSE, 165, 135, "arsmagicalegacy:fire_damage");
    public static final RegistryObject<SpellComponent> INVISIBILITY = SpellRegistry.registerSpellComponent(ArsMagicaLegacy.MODID, "invisibility", SKILL_POINT_2, new Invisiblity(), UTILITY, 185, 255, "arsmagicalegacy:true_sight");
    public static final RegistryObject<SpellComponent> KNOCKBACK = SpellRegistry.registerSpellComponent(ArsMagicaLegacy.MODID, "knockback", SKILL_POINT_2, new Knockback(), OFFENSE, 390, 180, "arsmagicalegacy:magic_damage");
    public static final RegistryObject<SpellComponent> LEAP = SpellRegistry.registerSpellComponent(ArsMagicaLegacy.MODID, "leap", SKILL_POINT_1, new Leap(), DEFENSE, 222, 90, "arsmagicalegacy:self");
    public static final RegistryObject<SpellComponent> LEVITATE = SpellRegistry.registerSpellComponent(ArsMagicaLegacy.MODID, "levitate", SKILL_POINT_2, new Levitation(), DEFENSE, 222, 225, "arsmagicalegacy:gravity_well");
    public static final RegistryObject<SpellComponent> LIFE_DRAIN = SpellRegistry.registerSpellComponent(ArsMagicaLegacy.MODID, "life_drain", SKILL_POINT_2, new LifeDrain(), DEFENSE, 312, 180, "arsmagicalegacy:life_tap");
    public static final RegistryObject<SpellComponent> LIFE_TAP = SpellRegistry.registerSpellComponent(ArsMagicaLegacy.MODID, "life_tap", SKILL_POINT_2, new LifeTap(), DEFENSE, 312, 135, "arsmagicalegacy:heal");
    public static final RegistryObject<SpellComponent> LIGHT = SpellRegistry.registerSpellComponent(ArsMagicaLegacy.MODID, "light", SKILL_POINT_1, new Light(), UTILITY, 275, 165, "arsmagicalegacy:dig");
    public static final RegistryObject<SpellComponent> LIGHTNING_DAMAGE = SpellRegistry.registerSpellComponent(ArsMagicaLegacy.MODID, "lightning_damage", SKILL_POINT_1, new LightningDamage(), OFFENSE, 255, 135, "arsmagicalegacy:fire_damage");
    public static final RegistryObject<SpellComponent> MAGIC_DAMAGE = SpellRegistry.registerSpellComponent(ArsMagicaLegacy.MODID, "magic_damage", SKILL_POINT_1, new MagicDamage(), OFFENSE, 390, 135, "arsmagicalegacy:physical_damage");
    public static final RegistryObject<SpellComponent> MANA_BLAST = SpellRegistry.registerSpellComponent(ArsMagicaLegacy.MODID, "mana_blast", SILVER_POINT, new ManaBlast(), OFFENSE, 75, 180);
    public static final RegistryObject<SpellComponent> MANA_DRAIN = SpellRegistry.registerSpellComponent(ArsMagicaLegacy.MODID, "mana_drain", SKILL_POINT_2, new ManaDrain(), DEFENSE, 312, 225, "arsmagicalegacy:life_drain");
    public static final RegistryObject<SpellComponent> MANA_SHIELD = SpellRegistry.registerSpellComponent(ArsMagicaLegacy.MODID, "mana_shield", SILVER_POINT, new ManaShield(), DEFENSE, 30, 90);
    public static final RegistryObject<SpellComponent> MARK = SpellRegistry.registerSpellComponent(ArsMagicaLegacy.MODID, "mark", SKILL_POINT_2, new Mark(), UTILITY, 155, 435, "arsmagicalegacy:transplace");
    public static final RegistryObject<SpellComponent> MOONRISE = SpellRegistry.registerSpellComponent(ArsMagicaLegacy.MODID, "moonrise", SILVER_POINT, new Moonrise(), UTILITY, 75, 90);
    public static final RegistryObject<SpellComponent> NIGHT_VISION = SpellRegistry.registerSpellComponent(ArsMagicaLegacy.MODID, "night_vision", SKILL_POINT_1, new NightVision(), UTILITY, 185, 165, "arsmagicalegacy:light");
    public static final RegistryObject<SpellComponent> PHYSICAL_DAMAGE = SpellRegistry.registerSpellComponent(ArsMagicaLegacy.MODID, "physical_damage", SKILL_POINT_1, new PhysicalDamage(), OFFENSE, 300, 90, "arsmagicalegacy:projectile");
    public static final RegistryObject<SpellComponent> PLACE_BLOCK = SpellRegistry.registerSpellComponent(ArsMagicaLegacy.MODID, "place_block", SKILL_POINT_1, new PlaceBlock(), UTILITY, 185, 93, "arsmagicalegacy:dig");
    public static final RegistryObject<SpellComponent> PLANT = SpellRegistry.registerSpellComponent(ArsMagicaLegacy.MODID, "plant", SKILL_POINT_1, new Plant(), UTILITY, 365, 210, "arsmagicalegacy:light");
    public static final RegistryObject<SpellComponent> PLOW = SpellRegistry.registerSpellComponent(ArsMagicaLegacy.MODID, "plow", SKILL_POINT_1, new Plow(), UTILITY, 365, 165, "arsmagicalegacy:light");
    public static final RegistryObject<SpellComponent> RANDOM_TELEPORT = SpellRegistry.registerSpellComponent(ArsMagicaLegacy.MODID, "random_teleport", SKILL_POINT_1, new RandomTeleport(), UTILITY, 185, 300, "arsmagicalegacy:invisibility");
    public static final RegistryObject<SpellComponent> RECALL = SpellRegistry.registerSpellComponent(ArsMagicaLegacy.MODID, "recall", SKILL_POINT_2, new Recall(), UTILITY, 215, 435, "arsmagicalegacy:transplace");
    public static final RegistryObject<SpellComponent> REFLECT = SpellRegistry.registerSpellComponent(ArsMagicaLegacy.MODID, "reflect", SKILL_POINT_3, new Reflect(), DEFENSE, 357, 315, "arsmagicalegacy:shield");
    public static final RegistryObject<SpellComponent> REGENERATION = SpellRegistry.registerSpellComponent(ArsMagicaLegacy.MODID, "regeneration", SKILL_POINT_1, new Regeneration(), DEFENSE, 357, 90, "arsmagicalegacy:self");
    public static final RegistryObject<SpellComponent> REPEL = SpellRegistry.registerSpellComponent(ArsMagicaLegacy.MODID, "repel", SKILL_POINT_2, new Repel(), DEFENSE, 132, 200, "arsmagicalegacy:slow");
    public static final RegistryObject<SpellComponent> RIFT = SpellRegistry.registerSpellComponent(ArsMagicaLegacy.MODID, "rift", SKILL_POINT_2, new Rift(), UTILITY, 275, 255, "arsmagicalegacy:light");
    public static final RegistryObject<SpellComponent> SHIELD = SpellRegistry.registerSpellComponent(ArsMagicaLegacy.MODID, "shield", SKILL_POINT_1, new Shield(), DEFENSE, 357, 270, "arsmagicalegacy:zone");
    public static final RegistryObject<SpellComponent> SHRINK = SpellRegistry.registerSpellComponent(ArsMagicaLegacy.MODID, "shrink", SKILL_POINT_1, new Shrink(), DEFENSE, 402, 90, "arsmagicalegacy:regeneration");
    public static final RegistryObject<SpellComponent> SILENCE = SpellRegistry.registerSpellComponent(ArsMagicaLegacy.MODID, "silence", SKILL_POINT_3, new Silence(), OFFENSE, 345, 245, "arsmagicalegacy:astral_distortion");
    public static final RegistryObject<SpellComponent> SLOW = SpellRegistry.registerSpellComponent(ArsMagicaLegacy.MODID, "slow", SKILL_POINT_1, new Slow(), DEFENSE, 132, 155, "arsmagicalegacy:slowfall");
    public static final RegistryObject<SpellComponent> SLOWFALL = SpellRegistry.registerSpellComponent(ArsMagicaLegacy.MODID, "slowfall", SKILL_POINT_1, new Slowfall(), DEFENSE, 222, 135, "arsmagicalegacy:leap");
    public static final RegistryObject<SpellComponent> STORM = SpellRegistry.registerSpellComponent(ArsMagicaLegacy.MODID, "storm", SKILL_POINT_3, new Storm(), OFFENSE, 255, 225, "arsmagicalegacy:lightning_damage");
    public static final RegistryObject<SpellComponent> SUMMON = SpellRegistry.registerSpellComponent(ArsMagicaLegacy.MODID, "summon", SKILL_POINT_2, new Summon(), DEFENSE, 267, 135, "arsmagicalegacy:life_tap");
    public static final RegistryObject<SpellComponent> SWIFT_SWIM = SpellRegistry.registerSpellComponent(ArsMagicaLegacy.MODID, "swift_swim", SKILL_POINT_1, new SwiftSwim(), DEFENSE, 177, 200, "arsmagicalegacy:haste");
    public static final RegistryObject<SpellComponent> TELEKINESIS = SpellRegistry.registerSpellComponent(ArsMagicaLegacy.MODID, "telekinesis", SKILL_POINT_2, new Telekinesis(), UTILITY, 305, 300, "arsmagicalegacy:rift");
    public static final RegistryObject<SpellComponent> TRANSPLACE = SpellRegistry.registerSpellComponent(ArsMagicaLegacy.MODID, "transplace", SKILL_POINT_1, new Transplace(), UTILITY, 185, 390, "arsmagicalegacy:blink");
    public static final RegistryObject<SpellComponent> TRUE_SIGHT = SpellRegistry.registerSpellComponent(ArsMagicaLegacy.MODID, "true_sight", SKILL_POINT_1, new TrueSight(), UTILITY, 185, 210, "arsmagicalegacy:night_vision");
    public static final RegistryObject<SpellComponent> WATER_BREATHING = SpellRegistry.registerSpellComponent(ArsMagicaLegacy.MODID, "water_breathing", SKILL_POINT_1, new WaterBreathing(), UTILITY, 410, 345, "arsmagicalegacy:drought");
    public static final RegistryObject<SpellComponent> WATERY_GRAVE = SpellRegistry.registerSpellComponent(ArsMagicaLegacy.MODID, "watery_grave", SKILL_POINT_2, new WateryGrave(), OFFENSE, 435, 245, "arsmagicalegacy:drown");
    public static final RegistryObject<SpellComponent> WIZARDS_AUTUMN = SpellRegistry.registerSpellComponent(ArsMagicaLegacy.MODID, "wizards_autumn", SKILL_POINT_1, new WizardsAutumn(), UTILITY, 315, 120, "arsmagicalegacy:dig");

    public static void register() {
    }
}
