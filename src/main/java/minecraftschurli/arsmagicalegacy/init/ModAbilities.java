package minecraftschurli.arsmagicalegacy.init;

import minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import minecraftschurli.arsmagicalegacy.api.affinity.AbstractAffinityAbility;
import minecraftschurli.arsmagicalegacy.api.registry.AbilityRegistry;
import minecraftschurli.arsmagicalegacy.objects.ability.*;
import net.minecraftforge.fml.RegistryObject;

public final class ModAbilities {
    public static final RegistryObject<AbstractAffinityAbility> ANTI_ENDER = AbilityRegistry.registerAbility(ArsMagicaAPI.MODID, "anti_ender", AntiEnderAbility::new);
    public static final RegistryObject<AbstractAffinityAbility> CLARITY = AbilityRegistry.registerAbility(ArsMagicaAPI.MODID, "clarity", ClarityAbility::new);
    public static final RegistryObject<AbstractAffinityAbility> ENDER_WATER_WEAKNESS = AbilityRegistry.registerAbility(ArsMagicaAPI.MODID, "ender_water_weakness", EnderWaterWeaknessAbility::new);
    public static final RegistryObject<AbstractAffinityAbility> FEATHER_LIGHT = AbilityRegistry.registerAbility(ArsMagicaAPI.MODID, "feather_light", FeatherLightAbility::new);
    public static final RegistryObject<AbstractAffinityAbility> FIRE_ASPECT = AbilityRegistry.registerAbility(ArsMagicaAPI.MODID, "fire_aspect", FireAspectAbility::new);
    public static final RegistryObject<AbstractAffinityAbility> FIRE_IMMUNITY = AbilityRegistry.registerAbility(ArsMagicaAPI.MODID, "fire_immunity", FireImmunityAbility::new);
    public static final RegistryObject<AbstractAffinityAbility> FIRE_WATER_WEAKNESS = AbilityRegistry.registerAbility(ArsMagicaAPI.MODID, "fire_water_weakness", FireWaterWeaknessAbility::new);
    public static final RegistryObject<AbstractAffinityAbility> FIRE_WEAKNESS = AbilityRegistry.registerAbility(ArsMagicaAPI.MODID, "fire_weakness", FireWeaknessAbility::new);
    public static final RegistryObject<AbstractAffinityAbility> FROST = AbilityRegistry.registerAbility(ArsMagicaAPI.MODID, "frost", FrostAbility::new);
    public static final RegistryObject<AbstractAffinityAbility> FROST_WALKER = AbilityRegistry.registerAbility(ArsMagicaAPI.MODID, "frost_walker", FrostWalkerAbility::new);
    public static final RegistryObject<AbstractAffinityAbility> FULMINATION = AbilityRegistry.registerAbility(ArsMagicaAPI.MODID, "fulmination", FulminationAbility::new);
    public static final RegistryObject<AbstractAffinityAbility> JUMP_HEIGHT = AbilityRegistry.registerAbility(ArsMagicaAPI.MODID, "jump_height", JumpHeightAbility::new);
    public static final RegistryObject<AbstractAffinityAbility> LAVA_WALKER = AbilityRegistry.registerAbility(ArsMagicaAPI.MODID, "lava_walker", LavaWalkerAbility::new);
    public static final RegistryObject<AbstractAffinityAbility> LEAF_LIKE = AbilityRegistry.registerAbility(ArsMagicaAPI.MODID, "leaf_like", LeafLikeAbility::new);
    public static final RegistryObject<AbstractAffinityAbility> LIGHTNING_WATER_WEAKNESS = AbilityRegistry.registerAbility(ArsMagicaAPI.MODID, "lightning_water_weakness", LightningWaterWeaknessAbility::new);
    public static final RegistryObject<AbstractAffinityAbility> LUNGS = AbilityRegistry.registerAbility(ArsMagicaAPI.MODID, "lungs", LungsAbility::new);
    public static final RegistryObject<AbstractAffinityAbility> MAGIC_WEAKNESS = AbilityRegistry.registerAbility(ArsMagicaAPI.MODID, "magic_weakness", MagicWeaknessAbility::new);
    public static final RegistryObject<AbstractAffinityAbility> MANA_COST_DECREASE = AbilityRegistry.registerAbility(ArsMagicaAPI.MODID, "mana_cost_decrease", ManaCostDecreaseAbility::new);
    public static final RegistryObject<AbstractAffinityAbility> NIGHT_VISION = AbilityRegistry.registerAbility(ArsMagicaAPI.MODID, "night_vision", NightVisionAbility::new);
    public static final RegistryObject<AbstractAffinityAbility> PACIFIST = AbilityRegistry.registerAbility(ArsMagicaAPI.MODID, "pacifist", PacifistAbility::new);
    public static final RegistryObject<AbstractAffinityAbility> PHOTOSYNTHESIS = AbilityRegistry.registerAbility(ArsMagicaAPI.MODID, "photosynthesis", PhotosynthesisAbility::new);
    public static final RegistryObject<AbstractAffinityAbility> POISON_IMMUNITY = AbilityRegistry.registerAbility(ArsMagicaAPI.MODID, "poison_immunity", PoisonImmunityAbility::new);
    public static final RegistryObject<AbstractAffinityAbility> REFLEXES = AbilityRegistry.registerAbility(ArsMagicaAPI.MODID, "reflexes", ReflexesAbility::new);
    public static final RegistryObject<AbstractAffinityAbility> ROOTED = AbilityRegistry.registerAbility(ArsMagicaAPI.MODID, "rooted", RootedAbility::new);
    public static final RegistryObject<AbstractAffinityAbility> SOLID_BONES = AbilityRegistry.registerAbility(ArsMagicaAPI.MODID, "solid_bones", SolidBonesAbility::new);
    public static final RegistryObject<AbstractAffinityAbility> STEP_UP = AbilityRegistry.registerAbility(ArsMagicaAPI.MODID, "step_up", StepUpAbility::new);
    public static final RegistryObject<AbstractAffinityAbility> SUNLIGHT_WEAKNESS = AbilityRegistry.registerAbility(ArsMagicaAPI.MODID, "sunlight_weakness", SunlightWeaknessAbility::new);
    public static final RegistryObject<AbstractAffinityAbility> SWIFT_SWIM = AbilityRegistry.registerAbility(ArsMagicaAPI.MODID, "swift_swim", SwiftSwimAbility::new);
    public static final RegistryObject<AbstractAffinityAbility> THORNS = AbilityRegistry.registerAbility(ArsMagicaAPI.MODID, "thorns", ThornsAbility::new);
    public static final RegistryObject<AbstractAffinityAbility> THUNDER_PUNCH = AbilityRegistry.registerAbility(ArsMagicaAPI.MODID, "thunder_punch", ThunderPunchAbility::new);

    public static void register() {

    }
}
