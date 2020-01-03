package am2.api;

import am2.api.affinity.*;
import am2.api.flickers.*;
import am2.api.items.armor.*;
import am2.api.skill.*;
import am2.api.spell.*;
import com.google.common.collect.*;
import net.minecraft.entity.ai.attributes.*;
import net.minecraft.util.*;
import net.minecraftforge.fml.common.*;

import java.util.*;

public class ArsMagicaAPI {
	
	private static final IForgeRegistry<Affinity> AFFINITY_REGISTRY;
	private static final IForgeRegistry<AbstractAffinityAbility> ABILITY_REGISTRY;
	private static final IForgeRegistry<ArmorImbuement> IMBUEMENTS_REGISTRY;
	private static final IForgeRegistry<AbstractSpellPart> SPELL_REGISTRY;
	private static final IForgeRegistry<Skill> SKILL_REGISTRY;
	private static final IForgeRegistry<AbstractFlickerFunctionality> FLICKER_FOCUS_REGISTRY;
	
	private static boolean enableTier4 = false;
	private static boolean enableTier5 = false;
	private static boolean enableTier6 = false;
	
	
	static {
		AFFINITY_REGISTRY = new RegistryBuilder<Affinity>().setName(new ResourceLocation("arsmagica2", "affinities")).setType(Affinity.class).setIDRange(0, Short.MAX_VALUE).addCallback(ObjectCallbacks.AFFINITY).create();
		ABILITY_REGISTRY = new RegistryBuilder<AbstractAffinityAbility>().setName(new ResourceLocation("arsmagica2", "affinityabilities")).setType(AbstractAffinityAbility.class).setIDRange(0, Short.MAX_VALUE).addCallback(ObjectCallbacks.AFFINITY).create();
		IMBUEMENTS_REGISTRY =  new RegistryBuilder<ArmorImbuement>().setName(new ResourceLocation("arsmagica2", "armorimbuments")).setType(ArmorImbuement.class).setIDRange(0, Short.MAX_VALUE).addCallback(ObjectCallbacks.IMBUEMENT).create();
		SPELL_REGISTRY = new RegistryBuilder<AbstractSpellPart>().setName(new ResourceLocation("arsmagica2", "spells")).setType(AbstractSpellPart.class).setIDRange(0, Short.MAX_VALUE).addCallback(ObjectCallbacks.SPELL).create();
		SKILL_REGISTRY = new RegistryBuilder<Skill>().setName(new ResourceLocation("arsmagica2", "skills")).setType(Skill.class).setIDRange(0, Short.MAX_VALUE).addCallback(ObjectCallbacks.SKILL).create();
		FLICKER_FOCUS_REGISTRY = new RegistryBuilder<AbstractFlickerFunctionality>().setName(new ResourceLocation("arsmagica2", "flicker_focus")).setType(AbstractFlickerFunctionality.class).setIDRange(0, Short.MAX_VALUE).addCallback(ObjectCallbacks.FLICKER_FOCUS).create();
	}
	
	//Bonus to max mana.  Applied additively.
	public static final IAttribute maxManaBonus = new RangedAttribute(null, "am2.maxManaBonus", 0.0f, 0.0f, Double.MAX_VALUE).setDescription("Mana Bonus").setShouldWatch(true);
	//Bonus to max burnout.  Applied additively.
	public static final IAttribute maxBurnoutBonus = new RangedAttribute(null, "am2.maxBurnoutBonus", 0.0f, 0.0f, Double.MAX_VALUE).setDescription("Burnout Bonus").setShouldWatch(true);
	//Bonus to XP gained.  Applied multiplicatively.
	public static final IAttribute xpGainModifier = new RangedAttribute(null, "am2.xpMultiplier", 1.0f, 0.0f, Double.MAX_VALUE).setDescription("XP Mutiplier").setShouldWatch(true);
	//Bonus to mana regen rate.  Applied multiplicatively.
	public static final IAttribute manaRegenTimeModifier = new RangedAttribute(null, "am2.manaRegenModifier", 1.0f, 0.5f, 2.0f).setDescription("Mana Regen Rate Multiplier").setShouldWatch(true);
	//Bonus to burnout reduction rate.  Applied multiplicatively.
	public static final IAttribute burnoutReductionRate = new RangedAttribute(null, "am2.burnoutReduction", 1.0f, 0.1f, 2.0f).setDescription("Burnout Reduction Rate").setShouldWatch(true);

	
	public static FMLControlledNamespacedRegistry<Affinity> getAffinityRegistry() {return (FMLControlledNamespacedRegistry<Affinity>)AFFINITY_REGISTRY;}
	public static FMLControlledNamespacedRegistry<AbstractAffinityAbility> getAffinityAbilityRegistry() {return (FMLControlledNamespacedRegistry<AbstractAffinityAbility>)ABILITY_REGISTRY;}
	public static FMLControlledNamespacedRegistry<ArmorImbuement> getArmorImbuementRegistry() {return (FMLControlledNamespacedRegistry<ArmorImbuement>)IMBUEMENTS_REGISTRY;}
	public static FMLControlledNamespacedRegistry<AbstractSpellPart> getSpellRegistry() {return (FMLControlledNamespacedRegistry<AbstractSpellPart>)SPELL_REGISTRY;}
	public static FMLControlledNamespacedRegistry<Skill> getSkillRegistry() {return (FMLControlledNamespacedRegistry<Skill>)SKILL_REGISTRY;}
	public static FMLControlledNamespacedRegistry<AbstractFlickerFunctionality> getFlickerFocusRegistry() {return (FMLControlledNamespacedRegistry<AbstractFlickerFunctionality>)FLICKER_FOCUS_REGISTRY;}
	
	/**
	 * Enable Tier 4, call in static{} for change to take effect.
	 */
	public static void enableTier4() {enableTier4 = true;}
	public static boolean hasTier4() {return enableTier4 || hasTier5();}

	/**
	 * Enable Tier 5, call in static{} for change to take effect.
	 */
	public static void enableTier5() {enableTier5 = true;}
	public static boolean hasTier5() {return enableTier5 || hasTier6();}
	
	/**
	 * Enable Tier 6, call in static{} for change to take effect.
	 */
	public static void enableTier6() {enableTier6 = true;}
	public static boolean hasTier6() {return enableTier6 ;}

	public static String getCurrentModId () {
		ModContainer current = Loader.instance().activeModContainer();
		String modid = "arsmagica2";
		if (current != null)
			modid = current.getModId();
		return modid;
	}
	
    public static class ObjectCallbacks<T extends IForgeRegistryEntry<T>> implements IForgeRegistry.AddCallback<T>,IForgeRegistry.ClearCallback<T>,IForgeRegistry.CreateCallback<T>
	{
		static final ObjectCallbacks<AbstractSpellPart> SPELL = new SpellCallbacks();
		static final ObjectCallbacks<AbstractAffinityAbility> ABILITY = new ObjectCallbacks<>();
		static final ObjectCallbacks<Affinity> AFFINITY = new ObjectCallbacks<>();
		static final ObjectCallbacks<ArmorImbuement> IMBUEMENT = new ObjectCallbacks<>();
		static final ObjectCallbacks<Skill> SKILL = new ObjectCallbacks<>();
		static final ObjectCallbacks<AbstractFlickerFunctionality> FLICKER_FOCUS = new ObjectCallbacks<>();

		@Override
		public void onCreate(Map<ResourceLocation, ?> slaveset, BiMap<ResourceLocation, ? extends IForgeRegistry<?>> registries) {

		}

		@Override
		public void onClear(IForgeRegistry<T> is, Map<ResourceLocation, ?> slaveset) {

		}

		@Override
		public void onAdd(T obj, int id, Map<ResourceLocation, ?> slaveset) {

		}
	}
    
    public static class SpellCallbacks extends ObjectCallbacks<AbstractSpellPart> {

		@Override
		public void onAdd(AbstractSpellPart ability, int id, Map<ResourceLocation, ?> slaves) {
		}
		
	}

}
