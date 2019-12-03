package minecraftschurli.arsmagicalegacy.init;

import minecraftschurli.arsmagicalegacy.ArsMagicaLegacy;
import minecraftschurli.arsmagicalegacy.api.spell.SpellModifier;
import minecraftschurli.arsmagicalegacy.api.spell.SpellShape;
import minecraftschurli.arsmagicalegacy.api.spell.skill.SkillPoint;
import minecraftschurli.arsmagicalegacy.api.spell.skill.SkillTree;
import minecraftschurli.arsmagicalegacy.objects.spell.modifier.*;
import minecraftschurli.arsmagicalegacy.objects.spell.shape.*;
import minecraftschurli.arsmagicalegacy.util.SpellRegistry;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.RegistryObject;

/**
 * @author Minecraftschurli
 * @version 2019-11-16
 */
public final class SpellParts implements IInit {
    //TODO: skill trees and (x|y) positions
    //  [src/old/java/am2/defs/SpellDefs.java] am2.defs.SpellDefs
    //  [src/old/java/am2/defs/SkillDefs.java] am2.defs.SkillDefs

    //skill points
    public static final SkillPoint SILVER_POINT = SpellRegistry.registerSkillPoint(new SkillPoint(0, TextFormatting.GRAY, 0x777777, -1, -1).disableRender());
    public static final SkillPoint SKILL_POINT_1 = SpellRegistry.registerSkillPoint(new SkillPoint(1, TextFormatting.BLUE, 0x0000dd, 0, 1));
    public static final SkillPoint SKILL_POINT_3 = SpellRegistry.registerSkillPoint(new SkillPoint(2, TextFormatting.RED, 0xdd0000, 30, 2));
    public static final SkillPoint SKILL_POINT_2 = SpellRegistry.registerSkillPoint(new SkillPoint(3, TextFormatting.GREEN, 0x00dd00, 20, 2));
    public static final SkillPoint SKILL_POINT_4 = SpellRegistry.registerSkillPoint(new SkillPoint(4, TextFormatting.YELLOW, 0xdddd00, 40, 3));
    public static final SkillPoint SKILL_POINT_5 = SpellRegistry.registerSkillPoint(new SkillPoint(5, TextFormatting.LIGHT_PURPLE, 0xdd00dd, 50, 3));
    public static final SkillPoint SKILL_POINT_6 = SpellRegistry.registerSkillPoint(new SkillPoint(6, TextFormatting.AQUA, 0x00dddd, 60, 4));

    //skill trees
    public static final SkillTree OFFENSE = SpellRegistry.registerSkillTree(ArsMagicaLegacy.MODID, "offense");
    public static final SkillTree DEFENSE = SpellRegistry.registerSkillTree(ArsMagicaLegacy.MODID, "defense");
    public static final SkillTree UTILITY = SpellRegistry.registerSkillTree(ArsMagicaLegacy.MODID, "utility");
    public static final SkillTree AFFINITY = SpellRegistry.registerSkillTree(ArsMagicaLegacy.MODID, "affinity");
    public static final SkillTree TALENT = SpellRegistry.registerSkillTree(ArsMagicaLegacy.MODID, "talent");

    //skills


    //modifiers
    public static final RegistryObject<SpellModifier> BOUNCE = SpellRegistry.registerSpellModifier(ArsMagicaLegacy.MODID, "bounce", SKILL_POINT_1, new Bounce(), null, 0, 0);
    public static final RegistryObject<SpellModifier> BUFF_POWER = SpellRegistry.registerSpellModifier(ArsMagicaLegacy.MODID, "buff_power", SILVER_POINT, new BuffPower(), null, 0, 0);
    public static final RegistryObject<SpellModifier> COLOR = SpellRegistry.registerSpellModifier(ArsMagicaLegacy.MODID, "color", SKILL_POINT_1, new Color(), null, 0, 0);
    public static final RegistryObject<SpellModifier> DAMAGE = SpellRegistry.registerSpellModifier(ArsMagicaLegacy.MODID, "damage", SKILL_POINT_3, new Damage(), null, 0, 0);
    public static final RegistryObject<SpellModifier> DISMEMBERING = SpellRegistry.registerSpellModifier(ArsMagicaLegacy.MODID, "dismembering", SILVER_POINT, new Dismembering(), null, 0, 0);
    public static final RegistryObject<SpellModifier> DURATION = SpellRegistry.registerSpellModifier(ArsMagicaLegacy.MODID, "duration", SKILL_POINT_3, new Bounce(), null, 0, 0);
    public static final RegistryObject<SpellModifier> FORTUNE = SpellRegistry.registerSpellModifier(ArsMagicaLegacy.MODID, "fortune", SILVER_POINT, new Fortune(), null, 0, 0);
    public static final RegistryObject<SpellModifier> GRAVITY = SpellRegistry.registerSpellModifier(ArsMagicaLegacy.MODID, "gravity", SKILL_POINT_1, new Gravity(), null, 0, 0);
    public static final RegistryObject<SpellModifier> HEALING = SpellRegistry.registerSpellModifier(ArsMagicaLegacy.MODID, "healing", SKILL_POINT_3, new Healing(), null, 0, 0);
    public static final RegistryObject<SpellModifier> LUNAR = SpellRegistry.registerSpellModifier(ArsMagicaLegacy.MODID, "lunar", SKILL_POINT_3, new Lunar(), null, 0, 0);
    public static final RegistryObject<SpellModifier> MINING = SpellRegistry.registerSpellModifier(ArsMagicaLegacy.MODID, "mining", SKILL_POINT_2, new Mining(), null, 0, 0);
    public static final RegistryObject<SpellModifier> MISSING_MODIFIER = SpellRegistry.registerSpellModifier(ArsMagicaLegacy.MODID, "missing_modifier", SILVER_POINT, new MissingModifier(), null, 0, 0);
    public static final RegistryObject<SpellModifier> PIERCING = SpellRegistry.registerSpellModifier(ArsMagicaLegacy.MODID, "piercing", SKILL_POINT_3, new Piercing(), null, 0, 0);
    public static final RegistryObject<SpellModifier> RADIUS = SpellRegistry.registerSpellModifier(ArsMagicaLegacy.MODID, "radius", SKILL_POINT_3, new Radius(), null, 0, 0);
    public static final RegistryObject<SpellModifier> RANGE = SpellRegistry.registerSpellModifier(ArsMagicaLegacy.MODID, "range", SKILL_POINT_3, new Range(), null, 0, 0);
    public static final RegistryObject<SpellModifier> RUNE_PROCS = SpellRegistry.registerSpellModifier(ArsMagicaLegacy.MODID, "rune_procs", SKILL_POINT_2, new RuneProcs(), null, 0, 0);
    public static final RegistryObject<SpellModifier> SILK_TOUCH = SpellRegistry.registerSpellModifier(ArsMagicaLegacy.MODID, "silk_touch", SKILL_POINT_1, new SilkTouch(), null, 0, 0);
    public static final RegistryObject<SpellModifier> SOLAR = SpellRegistry.registerSpellModifier(ArsMagicaLegacy.MODID, "solar", SKILL_POINT_3, new Solar(), null, 0, 0);
    public static final RegistryObject<SpellModifier> SPEED = SpellRegistry.registerSpellModifier(ArsMagicaLegacy.MODID, "speed", SKILL_POINT_3, new Speed(), null, 0, 0);
    public static final RegistryObject<SpellModifier> TARGET_NON_SOLID_BLOCKS = SpellRegistry.registerSpellModifier(ArsMagicaLegacy.MODID, "target_non_solid_blocks", SKILL_POINT_1, new TargetNonSolidBlocks(), null, 0, 0);
    public static final RegistryObject<SpellModifier> VELOCITY_ADDED = SpellRegistry.registerSpellModifier(ArsMagicaLegacy.MODID, "velocity_added", SKILL_POINT_3, new VelocityAdded(), null, 0, 0);
    
    //shapes
    public static final RegistryObject<SpellShape> AOE = SpellRegistry.registerSpellShape(ArsMagicaLegacy.MODID, "aoe", SKILL_POINT_2, new AoE(), OFFENSE, 300, 180, "arsmagicalegacy:frost_damage", "arsmagicalegacy:physical_damage", "arsmagicalegacy:fire_damage", "arsmagicalegacy:lightning_damage", "arsmagicalegacy:magic_damage");//done
    public static final RegistryObject<SpellShape> BEAM = SpellRegistry.registerSpellShape(ArsMagicaLegacy.MODID, "beam", SKILL_POINT_3, new Beam(), null, 0, 0);
    public static final RegistryObject<SpellShape> CHAIN = SpellRegistry.registerSpellShape(ArsMagicaLegacy.MODID, "chain", SKILL_POINT_3, new Chain(), null, 0, 0);
    public static final RegistryObject<SpellShape> CHANNEL = SpellRegistry.registerSpellShape(ArsMagicaLegacy.MODID, "channel", SKILL_POINT_2, new MissingShape(), null, 0, 0);
    public static final RegistryObject<SpellShape> MISSING_SHAPE = SpellRegistry.registerSpellShape(ArsMagicaLegacy.MODID, "null", null, new MissingShape(), null, 0, 0);
    public static final RegistryObject<SpellShape> PROJECTILE = SpellRegistry.registerSpellShape(ArsMagicaLegacy.MODID, "projectile", SKILL_POINT_1, new Projectile(), OFFENSE, 300, 45);//done
    public static final RegistryObject<SpellShape> RUNE = SpellRegistry.registerSpellShape(ArsMagicaLegacy.MODID, "rune", SKILL_POINT_2, new Rune(), null, 0, 0);
    public static final RegistryObject<SpellShape> SELF = SpellRegistry.registerSpellShape(ArsMagicaLegacy.MODID, "self", SKILL_POINT_1, new Self(), DEFENSE, 267, 45);//done
    public static final RegistryObject<SpellShape> TOGGLE = SpellRegistry.registerSpellShape(ArsMagicaLegacy.MODID, "toggle", SKILL_POINT_3, new Toggle(), null, 0, 0);
    public static final RegistryObject<SpellShape> TOUCH = SpellRegistry.registerSpellShape(ArsMagicaLegacy.MODID, "touch", SKILL_POINT_1, new Touch(), null, 0, 0);
    public static final RegistryObject<SpellShape> WALL = SpellRegistry.registerSpellShape(ArsMagicaLegacy.MODID, "wall", SKILL_POINT_2, new Wall(), DEFENSE, 87, 200, "arsmagicalegacy:repel");//done
    public static final RegistryObject<SpellShape> WAVE = SpellRegistry.registerSpellShape(ArsMagicaLegacy.MODID, "wave", SKILL_POINT_3, new Wave(), null, 0, 0);
    public static final RegistryObject<SpellShape> ZONE = SpellRegistry.registerSpellShape(ArsMagicaLegacy.MODID, "zone", SKILL_POINT_3, new Zone(), null, 0, 0);

    //components
    
    public static void register() {}
}
