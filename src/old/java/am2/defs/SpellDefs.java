package am2.defs;

import am2.api.SpellRegistry;
import am2.api.skill.SkillPoint;
import am2.api.spell.SpellShape;
import am2.spell.component.Absorption;
import am2.spell.component.Accelerate;
import am2.spell.component.Appropriation;
import am2.spell.component.AstralDistortion;
import am2.spell.component.Attract;
import am2.spell.component.BanishRain;
import am2.spell.component.Blind;
import am2.spell.component.Blink;
import am2.spell.component.Blizzard;
import am2.spell.component.Charm;
import am2.spell.component.ChronoAnchor;
import am2.spell.component.CreateWater;
import am2.spell.component.Daylight;
import am2.spell.component.Dig;
import am2.spell.component.Disarm;
import am2.spell.component.Dispel;
import am2.spell.component.DivineIntervention;
import am2.spell.component.Drought;
import am2.spell.component.Drown;
import am2.spell.component.EnderIntervention;
import am2.spell.component.Entangle;
import am2.spell.component.FallingStar;
import am2.spell.component.FireDamage;
import am2.spell.component.FireRain;
import am2.spell.component.Flight;
import am2.spell.component.Fling;
import am2.spell.component.Forge;
import am2.spell.component.Freeze;
import am2.spell.component.FrostDamage;
import am2.spell.component.Fury;
import am2.spell.component.GravityWell;
import am2.spell.component.Grow;
import am2.spell.component.HarvestPlants;
import am2.spell.component.Haste;
import am2.spell.component.Heal;
import am2.spell.component.Ignition;
import am2.spell.component.Invisiblity;
import am2.spell.component.Knockback;
import am2.spell.component.Leap;
import am2.spell.component.Levitation;
import am2.spell.component.LifeDrain;
import am2.spell.component.LifeTap;
import am2.spell.component.Light;
import am2.spell.component.LightningDamage;
import am2.spell.component.MagicDamage;
import am2.spell.component.ManaBlast;
import am2.spell.component.ManaDrain;
import am2.spell.component.ManaLink;
import am2.spell.component.ManaShield;
import am2.spell.component.Mark;
import am2.spell.component.MeltArmor;
import am2.spell.component.Moonrise;
import am2.spell.component.Nauseate;
import am2.spell.component.NightVision;
import am2.spell.component.PhysicalDamage;
import am2.spell.component.PlaceBlock;
import am2.spell.component.Plant;
import am2.spell.component.Plow;
import am2.spell.component.RandomTeleport;
import am2.spell.component.Recall;
import am2.spell.component.Reflect;
import am2.spell.component.Regeneration;
import am2.spell.component.Repel;
import am2.spell.component.Rift;
import am2.spell.component.ScrambleSynapses;
import am2.spell.component.Shield;
import am2.spell.component.Shrink;
import am2.spell.component.Silence;
import am2.spell.component.Slow;
import am2.spell.component.Slowfall;
import am2.spell.component.Storm;
import am2.spell.component.Summon;
import am2.spell.component.SwiftSwim;
import am2.spell.component.Telekinesis;
import am2.spell.component.Transplace;
import am2.spell.component.TrueSight;
import am2.spell.component.WaterBreathing;
import am2.spell.component.WateryGrave;
import am2.spell.component.WizardsAutumn;
import am2.spell.modifier.Bounce;
import am2.spell.modifier.BuffPower;
import am2.spell.modifier.Colour;
import am2.spell.modifier.Damage;
import am2.spell.modifier.Dismembering;
import am2.spell.modifier.Duration;
import am2.spell.modifier.FeatherTouch;
import am2.spell.modifier.Gravity;
import am2.spell.modifier.Healing;
import am2.spell.modifier.Lunar;
import am2.spell.modifier.MiningPower;
import am2.spell.modifier.Piercing;
import am2.spell.modifier.Prosperity;
import am2.spell.modifier.Radius;
import am2.spell.modifier.Range;
import am2.spell.modifier.RuneProcs;
import am2.spell.modifier.Solar;
import am2.spell.modifier.Speed;
import am2.spell.modifier.TargetNonSolidBlocks;
import am2.spell.modifier.VelocityAdded;
import am2.spell.shape.AoE;
import am2.spell.shape.Beam;
import am2.spell.shape.Binding;
import am2.spell.shape.Chain;
import am2.spell.shape.Channel;
import am2.spell.shape.Contingency_Death;
import am2.spell.shape.Contingency_Fall;
import am2.spell.shape.Contingency_Fire;
import am2.spell.shape.Contingency_Health;
import am2.spell.shape.Contingency_Hit;
import am2.spell.shape.MissingShape;
import am2.spell.shape.Projectile;
import am2.spell.shape.Rune;
import am2.spell.shape.Self;
import am2.spell.shape.Toggle;
import am2.spell.shape.Touch;
import am2.spell.shape.Wall;
import am2.spell.shape.Wave;
import am2.spell.shape.Zone;
import net.minecraft.util.ResourceLocation;

public class SpellDefs {
	public static final SpellShape MISSING_SHAPE = new MissingShape();
	
	public static void init() {
		SpellRegistry.registerSpellShape("null", null, null, MISSING_SHAPE, null, 0, 0);
		SpellRegistry.registerSpellComponent("melt_armor", null, null, new MeltArmor(), null, 0, 0);
		SpellRegistry.registerSpellComponent("nauseate", null, null, new Nauseate(), null, 0, 0);
		SpellRegistry.registerSpellComponent("scramble_synapses", null, null, new ScrambleSynapses(), null, 0, 0);
		
		SpellRegistry.registerSpellModifier("colour", getModifierTexture("Colour"), SkillPoint.SKILL_POINT_1, new Colour(), SkillDefs.TREE_TALENT, 230, 75);

		handleOffenseTree();
		handleDefenseTree();
		handleUtilityTree();
	}
	
	public static void handleOffenseTree() {
		SpellRegistry.registerSpellShape("projectile", getShapeTexture("Projectile"), SkillPoint.SKILL_POINT_1, new Projectile(), SkillDefs.TREE_OFFENSE, 300, 45);
		SpellRegistry.registerSpellComponent("physical_damage", getComponentTexture("PhysicalDamage"), SkillPoint.SKILL_POINT_1, new PhysicalDamage(), SkillDefs.TREE_OFFENSE, 300, 90, "arsmagica2:projectile");
		SpellRegistry.registerSpellModifier("gravity", getModifierTexture("Gravity"), SkillPoint.SKILL_POINT_1, new Gravity(), SkillDefs.TREE_OFFENSE, 255, 70, "arsmagica2:projectile");
		SpellRegistry.registerSpellModifier("bounce", getModifierTexture("Bounce"), SkillPoint.SKILL_POINT_1, new Bounce(), SkillDefs.TREE_OFFENSE, 345, 70, "arsmagica2:projectile");
		
		SpellRegistry.registerSpellComponent("fire_damage", getComponentTexture("FireDamage"), SkillPoint.SKILL_POINT_1, new FireDamage(), SkillDefs.TREE_OFFENSE, 210, 135, "arsmagica2:physical_damage");
		SpellRegistry.registerSpellComponent("lightning_damage", getComponentTexture("LightningDamage"), SkillPoint.SKILL_POINT_1, new LightningDamage(), SkillDefs.TREE_OFFENSE, 255, 135, "arsmagica2:fire_damage");
		SpellRegistry.registerSpellComponent("ignition", getComponentTexture("Ignition"), SkillPoint.SKILL_POINT_2, new Ignition(), SkillDefs.TREE_OFFENSE, 165, 135, "arsmagica2:fire_damage");
		SpellRegistry.registerSpellComponent("forge", getComponentTexture("Forge"), SkillPoint.SKILL_POINT_2, new Forge(), SkillDefs.TREE_OFFENSE, 120, 135, "arsmagica2:ignition");	
		
		SpellRegistry.registerSpellComponent("magic_damage", getComponentTexture("MagicDamage"), SkillPoint.SKILL_POINT_1, new MagicDamage(), SkillDefs.TREE_OFFENSE, 390, 135, "arsmagica2:physical_damage");
		SpellRegistry.registerSpellComponent("frost_damage", getComponentTexture("FrostDamage"), SkillPoint.SKILL_POINT_1, new FrostDamage(), SkillDefs.TREE_OFFENSE, 345, 135, "arsmagica2:magic_damage");
		
		SpellRegistry.registerSpellComponent("drown", getComponentTexture("Drown"), SkillPoint.SKILL_POINT_1, new Drown(), SkillDefs.TREE_OFFENSE, 435, 135, "arsmagica2:magic_damage");
		
		SpellRegistry.registerSpellComponent("blind", getComponentTexture("Blind"), SkillPoint.SKILL_POINT_2, new Blind(), SkillDefs.TREE_OFFENSE, 233, 180, "arsmagica2:fire_damage", "arsmagica2:lightning_damage");
		SpellRegistry.registerSpellShape("aoe", getShapeTexture("AoE"), SkillPoint.SKILL_POINT_2, new AoE(), SkillDefs.TREE_OFFENSE, 300, 180, "arsmagica2:frost_damage", "arsmagica2:physical_damage", "arsmagica2:fire_damage", "arsmagica2:lightning_damage", "arsmagica2:magic_damage");
		SpellRegistry.registerSpellComponent("freeze", getComponentTexture("Freeze"), SkillPoint.SKILL_POINT_2, new Freeze(), SkillDefs.TREE_OFFENSE, 345, 180, "arsmagica2:frost_damage");
		SpellRegistry.registerSpellComponent("knockback", getComponentTexture("Knockback"), SkillPoint.SKILL_POINT_2, new Knockback(), SkillDefs.TREE_OFFENSE, 390, 180, "arsmagica2:magic_damage");
		
		SpellRegistry.registerSpellShape("contingency_fire", getShapeTexture("Contingency_Fire"), SkillPoint.SKILL_POINT_2, new Contingency_Fire(), SkillDefs.TREE_OFFENSE, 165, 190, "arsmagica2:ignition");
		SpellRegistry.registerSpellModifier("solar", getModifierTexture("Solar"), SkillPoint.SKILL_POINT_3, new Solar(), SkillDefs.TREE_OFFENSE, 210, 255, "arsmagica2:blind");
		
		SpellRegistry.registerSpellComponent("storm", getComponentTexture("Storm"), SkillPoint.SKILL_POINT_3, new Storm(), SkillDefs.TREE_OFFENSE, 255, 225, "arsmagica2:lightning_damage");
		SpellRegistry.registerSpellComponent("astral_distortion", getComponentTexture("AstralDistortion"), SkillPoint.SKILL_POINT_2, new AstralDistortion(), SkillDefs.TREE_OFFENSE, 367, 215, "arsmagica2:magic_damage", "arsmagica2:frost_damage");
		SpellRegistry.registerSpellComponent("silence", getComponentTexture("Silence"), SkillPoint.SKILL_POINT_3, new Silence(), SkillDefs.TREE_OFFENSE, 345, 245, "arsmagica2:astral_distortion");
		
		SpellRegistry.registerSpellComponent("fling", getComponentTexture("Fling"), SkillPoint.SKILL_POINT_2, new Fling(), SkillDefs.TREE_OFFENSE, 390, 245, "arsmagica2:knockback");
		SpellRegistry.registerSpellModifier("velocity_added", getModifierTexture("VelocityAdded"), SkillPoint.SKILL_POINT_3, new VelocityAdded(), SkillDefs.TREE_OFFENSE, 390, 290, "arsmagica2:fling");
		SpellRegistry.registerSpellComponent("watery_grave", getComponentTexture("WateryGrave"), SkillPoint.SKILL_POINT_2, new WateryGrave(), SkillDefs.TREE_OFFENSE, 435, 245, "arsmagica2:drown");
		
		SpellRegistry.registerSpellModifier("piercing", getModifierTexture("Piercing"), SkillPoint.SKILL_POINT_3, new Piercing(), SkillDefs.TREE_OFFENSE, 323, 215, "arsmagica2:freeze");
		
		SpellRegistry.registerSpellShape("beam", getShapeTexture("Beam"), SkillPoint.SKILL_POINT_3, new Beam(), SkillDefs.TREE_OFFENSE, 300, 270, "arsmagica2:aoe");	
		SpellRegistry.registerSpellModifier("damage", getModifierTexture("Damage"), SkillPoint.SKILL_POINT_3, new Damage(), SkillDefs.TREE_OFFENSE, 300, 315, "arsmagica2:beam");
		SpellRegistry.registerSpellComponent("fury", getComponentTexture("Fury"), SkillPoint.SKILL_POINT_3, new Fury(), SkillDefs.TREE_OFFENSE, 255, 315, "arsmagica2:beam", "arsmagica2:storm");
		SpellRegistry.registerSpellShape("wave", getShapeTexture("Wave"), SkillPoint.SKILL_POINT_3, new Wave(), SkillDefs.TREE_OFFENSE, 367, 315, "arsmagica2:beam", "arsmagica2:fling");	
		
		SpellRegistry.registerSpellComponent("blizzard", getComponentTexture("Blizzard"), SkillPoint.SILVER_POINT, new Blizzard(), SkillDefs.TREE_OFFENSE, 75, 45);
		SpellRegistry.registerSpellComponent("falling_star", getComponentTexture("FallingStar"), SkillPoint.SILVER_POINT, new FallingStar(), SkillDefs.TREE_OFFENSE, 75, 90);
		SpellRegistry.registerSpellComponent("fire_rain", getComponentTexture("FireRain"), SkillPoint.SILVER_POINT, new FireRain(), SkillDefs.TREE_OFFENSE, 75, 135);
		SpellRegistry.registerSpellComponent("mana_blast", getComponentTexture("ManaBlast"), SkillPoint.SILVER_POINT, new ManaBlast(), SkillDefs.TREE_OFFENSE, 75, 180);
		SpellRegistry.registerSpellModifier("dismembering", getModifierTexture("Dismembering"), SkillPoint.SILVER_POINT, new Dismembering(), SkillDefs.TREE_OFFENSE, 75, 225);
	}
	
	public static void handleDefenseTree () {
		//defense tree
		SpellRegistry.registerSpellShape("self", getShapeTexture("Self"), SkillPoint.SKILL_POINT_1, new Self(), SkillDefs.TREE_DEFENSE, 267, 45);

		SpellRegistry.registerSpellComponent("leap", getComponentTexture("Leap"), SkillPoint.SKILL_POINT_1, new Leap(), SkillDefs.TREE_DEFENSE, 222, 90, "arsmagica2:self");
		SpellRegistry.registerSpellComponent("regeneration", getComponentTexture("Regeneration"), SkillPoint.SKILL_POINT_1, new Regeneration(), SkillDefs.TREE_DEFENSE, 357, 90, "arsmagica2:self");

		SpellRegistry.registerSpellComponent("shrink", getComponentTexture("Shrink"), SkillPoint.SKILL_POINT_1, new Shrink(), SkillDefs.TREE_DEFENSE, 402, 90, "arsmagica2:regeneration");
		SpellRegistry.registerSpellComponent("slowfall", getComponentTexture("Slowfall"), SkillPoint.SKILL_POINT_1, new Slowfall(), SkillDefs.TREE_DEFENSE, 222, 135, "arsmagica2:leap");
		SpellRegistry.registerSpellComponent("heal", getComponentTexture("Heal"), SkillPoint.SKILL_POINT_1, new Heal(), SkillDefs.TREE_DEFENSE, 357, 135, "arsmagica2:regeneration");
		SpellRegistry.registerSpellComponent("life_tap", getComponentTexture("LifeTap"), SkillPoint.SKILL_POINT_2, new LifeTap(), SkillDefs.TREE_DEFENSE, 312, 135, "arsmagica2:heal");
		SpellRegistry.registerSpellModifier("healing", getModifierTexture("Healing"), SkillPoint.SKILL_POINT_3, new Healing(), SkillDefs.TREE_DEFENSE, 402, 135, "arsmagica2:heal");

		SpellRegistry.registerSpellComponent("summon", getComponentTexture("Summon"), SkillPoint.SKILL_POINT_2, new Summon(), SkillDefs.TREE_DEFENSE, 267, 135, "arsmagica2:life_tap");
		SpellRegistry.registerSpellShape("contingency_damage", getShapeTexture("Contingency_Damage"), SkillPoint.SKILL_POINT_2, new Contingency_Hit(), SkillDefs.TREE_DEFENSE, 447, 180, "arsmagica2:healing");

		SpellRegistry.registerSpellComponent("haste", getComponentTexture("Haste"), SkillPoint.SKILL_POINT_1, new Haste(), SkillDefs.TREE_DEFENSE, 177, 155, "arsmagica2:slowfall");
		SpellRegistry.registerSpellComponent("slow", getComponentTexture("Slow"), SkillPoint.SKILL_POINT_1, new Slow(), SkillDefs.TREE_DEFENSE, 132, 155, "arsmagica2:slowfall");

		SpellRegistry.registerSpellComponent("gravity_well", getComponentTexture("GravityWell"), SkillPoint.SKILL_POINT_2, new GravityWell(), SkillDefs.TREE_DEFENSE, 222, 180, "arsmagica2:slowfall");
		SpellRegistry.registerSpellComponent("life_drain", getComponentTexture("LifeDrain"), SkillPoint.SKILL_POINT_2, new LifeDrain(), SkillDefs.TREE_DEFENSE, 312, 180, "arsmagica2:life_tap");
		SpellRegistry.registerSpellComponent("dispel", getComponentTexture("Dispel"), SkillPoint.SKILL_POINT_2, new Dispel(), SkillDefs.TREE_DEFENSE, 357, 180, "arsmagica2:heal");

		SpellRegistry.registerSpellShape("contingency_fall", getShapeTexture("Contingency_Fall"), SkillPoint.SKILL_POINT_2, new Contingency_Fall(), SkillDefs.TREE_DEFENSE, 267, 180, "arsmagica2:gravity_well");

		SpellRegistry.registerSpellComponent("swift_swim", getComponentTexture("SwiftSwim"), SkillPoint.SKILL_POINT_1, new SwiftSwim(), SkillDefs.TREE_DEFENSE, 177, 200, "arsmagica2:haste");
		SpellRegistry.registerSpellComponent("repel", getComponentTexture("Repel"), SkillPoint.SKILL_POINT_2, new Repel(), SkillDefs.TREE_DEFENSE, 132, 200, "arsmagica2:slow");

		SpellRegistry.registerSpellComponent("levitate", getComponentTexture("Levitate"), SkillPoint.SKILL_POINT_2, new Levitation(), SkillDefs.TREE_DEFENSE, 222, 225, "arsmagica2:gravity_well");
		SpellRegistry.registerSpellComponent("mana_drain", getComponentTexture("ManaDrain"), SkillPoint.SKILL_POINT_2, new ManaDrain(), SkillDefs.TREE_DEFENSE, 312, 225, "arsmagica2:life_drain");
		SpellRegistry.registerSpellShape("zone", getShapeTexture("Zone"), SkillPoint.SKILL_POINT_3, new Zone(), SkillDefs.TREE_DEFENSE, 357, 225, "arsmagica2:dispel");

		SpellRegistry.registerSpellShape("wall", getShapeTexture("Wall"), SkillPoint.SKILL_POINT_2, new Wall(), SkillDefs.TREE_DEFENSE, 87, 200, "arsmagica2:repel");
		SpellRegistry.registerSpellComponent("accelerate", getComponentTexture("Accelerate"), SkillPoint.SKILL_POINT_2, new Accelerate(), SkillDefs.TREE_DEFENSE, 177, 245, "arsmagica2:swift_swim");
		SpellRegistry.registerSpellComponent("entangle", getComponentTexture("Entangle"), SkillPoint.SKILL_POINT_2, new Entangle(), SkillDefs.TREE_DEFENSE, 132, 245, "arsmagica2:repel");
		SpellRegistry.registerSpellComponent("appropriation", getComponentTexture("Appropriation"), SkillPoint.SKILL_POINT_3, new Appropriation(), SkillDefs.TREE_DEFENSE, 87, 245, "arsmagica2:entangle");

		SpellRegistry.registerSpellComponent("flight", getComponentTexture("Flight"), SkillPoint.SKILL_POINT_3, new Flight(), SkillDefs.TREE_DEFENSE, 222, 270, "arsmagica2:levitate");
		SpellRegistry.registerSpellComponent("shield", getComponentTexture("Shield"), SkillPoint.SKILL_POINT_1, new Shield(), SkillDefs.TREE_DEFENSE, 357, 270, "arsmagica2:zone");

		SpellRegistry.registerSpellShape("contingency_health", getShapeTexture("Contingency_Health"), SkillPoint.SKILL_POINT_3, new Contingency_Health(), SkillDefs.TREE_DEFENSE, 402, 270, "arsmagica2:shield");

		SpellRegistry.registerSpellShape("rune", getShapeTexture("Rune"), SkillPoint.SKILL_POINT_2, new Rune(), SkillDefs.TREE_DEFENSE, 157, 315, "arsmagica2:accelerate", "arsmagica2:entangle");

		SpellRegistry.registerSpellModifier("rune_procs", getModifierTexture("RuneProcs"), SkillPoint.SKILL_POINT_2, new RuneProcs(), SkillDefs.TREE_DEFENSE, 157, 360, "arsmagica2:rune");

		SpellRegistry.registerSpellModifier("speed", getModifierTexture("Speed"), SkillPoint.SKILL_POINT_3, new Speed(), SkillDefs.TREE_DEFENSE, 202, 315, "arsmagica2:accelerate", "arsmagica2:flight");

		SpellRegistry.registerSpellComponent("reflect", getComponentTexture("Reflect"), SkillPoint.SKILL_POINT_3, new Reflect(), SkillDefs.TREE_DEFENSE, 357, 315, "arsmagica2:shield");
		SpellRegistry.registerSpellComponent("chrono_anchor", getComponentTexture("ChronoAnchor"), SkillPoint.SKILL_POINT_3, new ChronoAnchor(), SkillDefs.TREE_DEFENSE, 312, 315, "arsmagica2:reflect");

		SpellRegistry.registerSpellModifier("duration", getModifierTexture("Duration"), SkillPoint.SKILL_POINT_3, new Duration(), SkillDefs.TREE_DEFENSE, 312, 360, "arsmagica2:chrono_anchor");

		SpellRegistry.registerSpellComponent("absorption", getComponentTexture("Absorption"), SkillPoint.SKILL_POINT_3, new Absorption(), SkillDefs.TREE_DEFENSE, 312, 270, "arsmagica2:shield");
		
		SpellRegistry.registerSpellComponent("mana_link", getComponentTexture("ManaLink"), SkillPoint.SILVER_POINT, new ManaLink(), SkillDefs.TREE_DEFENSE, 30, 45);
		SpellRegistry.registerSpellComponent("mana_shield", getComponentTexture("ManaShield"), SkillPoint.SILVER_POINT, new ManaShield(), SkillDefs.TREE_DEFENSE, 30, 90);
		SpellRegistry.registerSpellModifier("buff_power", getModifierTexture("BuffPower"), SkillPoint.SILVER_POINT, new BuffPower(), SkillDefs.TREE_DEFENSE, 30, 135);
		
	}
	
	public static void handleUtilityTree () {
		//utility tree
		SpellRegistry.registerSpellShape("touch", getShapeTexture("Touch"), SkillPoint.SKILL_POINT_1, new Touch(), SkillDefs.TREE_UTILITY, 275, 75);

		SpellRegistry.registerSpellComponent("dig", getComponentTexture("Dig"), SkillPoint.SKILL_POINT_1, new Dig(), SkillDefs.TREE_UTILITY, 275, 120, "arsmagica2:touch");
		SpellRegistry.registerSpellComponent("wizards_autumn", getComponentTexture("WizardsAutumn"), SkillPoint.SKILL_POINT_1, new WizardsAutumn(), SkillDefs.TREE_UTILITY, 315, 120, "arsmagica2:dig");
		SpellRegistry.registerSpellModifier("target_non_solid", getModifierTexture("TargetNonSolid"), SkillPoint.SKILL_POINT_1, new TargetNonSolidBlocks(), SkillDefs.TREE_UTILITY, 230, 75, "arsmagica2:touch");

		SpellRegistry.registerSpellComponent("place_block", getComponentTexture("PlaceBlock"), SkillPoint.SKILL_POINT_1, new PlaceBlock(), SkillDefs.TREE_UTILITY, 185, 93, "arsmagica2:dig");
		SpellRegistry.registerSpellModifier("feather_touch", getModifierTexture("FeatherTouch"), SkillPoint.SKILL_POINT_1, new FeatherTouch(), SkillDefs.TREE_UTILITY, 230, 137, "arsmagica2:dig");
		SpellRegistry.registerSpellModifier("mining_power", getModifierTexture("MiningPower"), SkillPoint.SKILL_POINT_2, new MiningPower(), SkillDefs.TREE_UTILITY, 185, 137, "arsmagica2:feather_touch");

		SpellRegistry.registerSpellComponent("light", getComponentTexture("Light"), SkillPoint.SKILL_POINT_1, new Light(), SkillDefs.TREE_UTILITY, 275, 165, "arsmagica2:dig");
		SpellRegistry.registerSpellComponent("night_vision", getComponentTexture("NightVision"), SkillPoint.SKILL_POINT_1, new NightVision(), SkillDefs.TREE_UTILITY, 185, 165, "arsmagica2:light");

		SpellRegistry.registerSpellShape("binding", getShapeTexture("Binding"), SkillPoint.SKILL_POINT_1, new Binding(), SkillDefs.TREE_UTILITY, 275, 210, "arsmagica2:light");
		SpellRegistry.registerSpellComponent("disarm", getComponentTexture("Disarm"), SkillPoint.SKILL_POINT_1, new Disarm(), SkillDefs.TREE_UTILITY, 230, 210, "arsmagica2:binding");
		SpellRegistry.registerSpellComponent("charm", getComponentTexture("Charm"), SkillPoint.SKILL_POINT_1, new Charm(), SkillDefs.TREE_UTILITY, 315, 235, "arsmagica2:binding");
		SpellRegistry.registerSpellComponent("true_sight", getComponentTexture("TrueSight"), SkillPoint.SKILL_POINT_1, new TrueSight(), SkillDefs.TREE_UTILITY, 185, 210, "arsmagica2:night_vision");

		SpellRegistry.registerSpellModifier("lunar", getModifierTexture("Lunar"), SkillPoint.SKILL_POINT_3, new Lunar(), SkillDefs.TREE_UTILITY, 145, 210, "arsmagica2:true_sight");

		SpellRegistry.registerSpellComponent("harvest_plants", getComponentTexture("HarvestPlants"), SkillPoint.SKILL_POINT_2, new HarvestPlants(), SkillDefs.TREE_UTILITY, 365, 120, "arsmagica2:binding");
		SpellRegistry.registerSpellComponent("plow", getComponentTexture("Plow"), SkillPoint.SKILL_POINT_1, new Plow(), SkillDefs.TREE_UTILITY, 365, 165, "arsmagica2:binding");
		SpellRegistry.registerSpellComponent("plant", getComponentTexture("Plant"), SkillPoint.SKILL_POINT_1, new Plant(), SkillDefs.TREE_UTILITY, 365, 210, "arsmagica2:binding");
		SpellRegistry.registerSpellComponent("create_water", getComponentTexture("CreateWater"), SkillPoint.SKILL_POINT_2, new CreateWater(), SkillDefs.TREE_UTILITY, 365, 255, "arsmagica2:binding");
		SpellRegistry.registerSpellComponent("drought", getComponentTexture("Drought"), SkillPoint.SKILL_POINT_2, new Drought(), SkillDefs.TREE_UTILITY, 365, 300, "arsmagica2:binding");

		SpellRegistry.registerSpellComponent("banish_rain", getComponentTexture("BanishRain"), SkillPoint.SKILL_POINT_2, new BanishRain(), SkillDefs.TREE_UTILITY, 365, 345, "arsmagica2:drought");
		SpellRegistry.registerSpellComponent("water_breathing", getComponentTexture("WaterBreathing"), SkillPoint.SKILL_POINT_1, new WaterBreathing(), SkillDefs.TREE_UTILITY, 410, 345, "arsmagica2:drought");

		SpellRegistry.registerSpellComponent("grow", getComponentTexture("Grow"), SkillPoint.SKILL_POINT_3, new Grow(), SkillDefs.TREE_UTILITY, 410, 210, "arsmagica2:drought", "arsmagica2:create_water", "arsmagica2:plant", "arsmagica2:plow", "arsmagica2:harvest_plants");

		SpellRegistry.registerSpellShape("chain", getShapeTexture("Chain"), SkillPoint.SKILL_POINT_3, new Chain(), SkillDefs.TREE_UTILITY, 455, 210, "arsmagica2:grow");

		SpellRegistry.registerSpellComponent("rift", getComponentTexture("Rift"), SkillPoint.SKILL_POINT_2, new Rift(), SkillDefs.TREE_UTILITY, 275, 255, "arsmagica2:binding");
		SpellRegistry.registerSpellComponent("invisibility", getComponentTexture("Invisibility"), SkillPoint.SKILL_POINT_2, new Invisiblity(), SkillDefs.TREE_UTILITY, 185, 255, "arsmagica2:true_sight");

		SpellRegistry.registerSpellComponent("random_teleport", getComponentTexture("RandomTeleport"), SkillPoint.SKILL_POINT_1, new RandomTeleport(), SkillDefs.TREE_UTILITY, 185, 300, "arsmagica2:invisibility");
		SpellRegistry.registerSpellComponent("attract", getComponentTexture("Attract"), SkillPoint.SKILL_POINT_2, new Attract(), SkillDefs.TREE_UTILITY, 245, 300, "arsmagica2:rift");
		SpellRegistry.registerSpellComponent("telekinesis", getComponentTexture("Telekinesis"), SkillPoint.SKILL_POINT_2, new Telekinesis(), SkillDefs.TREE_UTILITY, 305, 300, "arsmagica2:rift");

		SpellRegistry.registerSpellComponent("blink", getComponentTexture("Blink"), SkillPoint.SKILL_POINT_2, new Blink(), SkillDefs.TREE_UTILITY, 185, 345, "arsmagica2:random_teleport");
		SpellRegistry.registerSpellModifier("range", getModifierTexture("Range"), SkillPoint.SKILL_POINT_3, new Range(), SkillDefs.TREE_UTILITY, 140, 345, "arsmagica2:blink");
		SpellRegistry.registerSpellShape("channel", getShapeTexture("Channel"), SkillPoint.SKILL_POINT_2, new Channel(), SkillDefs.TREE_UTILITY, 275, 345, "arsmagica2:attract", "arsmagica2:telekinesis");
		SpellRegistry.registerSpellShape("toggle", getShapeTexture("Toggle"), SkillPoint.SKILL_POINT_3, new Toggle(), SkillDefs.TREE_UTILITY, 315, 345, "arsmagica2:channel");

		SpellRegistry.registerSpellModifier("radius", getModifierTexture("Radius"), SkillPoint.SKILL_POINT_3, new Radius(), SkillDefs.TREE_UTILITY, 275, 390, "arsmagica2:channel");
		SpellRegistry.registerSpellComponent("transplace", getComponentTexture("Transplace"), SkillPoint.SKILL_POINT_1, new Transplace(), SkillDefs.TREE_UTILITY, 185, 390, "arsmagica2:blink");

		SpellRegistry.registerSpellComponent("mark", getComponentTexture("Mark"), SkillPoint.SKILL_POINT_2, new Mark(), SkillDefs.TREE_UTILITY, 155, 435, "arsmagica2:transplace");
		SpellRegistry.registerSpellComponent("recall", getComponentTexture("Recall"), SkillPoint.SKILL_POINT_2, new Recall(), SkillDefs.TREE_UTILITY, 215, 435, "arsmagica2:transplace");

		SpellRegistry.registerSpellComponent("divine_intervention", getComponentTexture("DivineIntervention"), SkillPoint.SKILL_POINT_3, new DivineIntervention(), SkillDefs.TREE_UTILITY, 172, 480, "arsmagica2:recall", "arsmagica2:mark");
		SpellRegistry.registerSpellComponent("ender_intervention", getComponentTexture("EnderIntervention"), SkillPoint.SKILL_POINT_3, new EnderIntervention(), SkillDefs.TREE_UTILITY, 198, 480, "arsmagica2:recall", "arsmagica2:mark");

		SpellRegistry.registerSpellShape("contingency_death", getShapeTexture("Contingency_Death"), SkillPoint.SKILL_POINT_3, new Contingency_Death(), SkillDefs.TREE_UTILITY, 198, 524, "arsmagica2:ender_intervention");

		SpellRegistry.registerSpellComponent("daylight", getComponentTexture("Daylight"), SkillPoint.SILVER_POINT, new Daylight(), SkillDefs.TREE_UTILITY, 75, 45);
		SpellRegistry.registerSpellComponent("moonrise", getComponentTexture("Moonrise"), SkillPoint.SILVER_POINT, new Moonrise(), SkillDefs.TREE_UTILITY, 75, 90);
		SpellRegistry.registerSpellModifier("prosperity", getModifierTexture("Prosperity"), SkillPoint.SILVER_POINT, new Prosperity(), SkillDefs.TREE_UTILITY, 75, 135);
	}
	
	private static ResourceLocation getComponentTexture(String name) {
		return new ResourceLocation("arsmagica2", "items/spells/components/" + name);
	}
	
	private static ResourceLocation getShapeTexture(String name) {
		return new ResourceLocation("arsmagica2", "items/spells/shapes/" + name);		
	}
	
	private static ResourceLocation getModifierTexture(String name) {
		return new ResourceLocation("arsmagica2", "items/spells/modifiers/" + name);		
	}
}
