package minecraftschurli.arsmagicalegacy.init;

import minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import minecraftschurli.arsmagicalegacy.api.affinity.AbstractAffinityAbility;
import minecraftschurli.arsmagicalegacy.api.registry.AbilityRegistry;
import minecraftschurli.arsmagicalegacy.objects.ability.AntiEnderAbility;
import minecraftschurli.arsmagicalegacy.objects.ability.ClarityAbility;
import minecraftschurli.arsmagicalegacy.objects.ability.EnderWaterWeaknessAbility;
import minecraftschurli.arsmagicalegacy.objects.ability.FeatherLightAbility;
import minecraftschurli.arsmagicalegacy.objects.ability.FireAspectAbility;
import minecraftschurli.arsmagicalegacy.objects.ability.FireImmunityAbility;
import minecraftschurli.arsmagicalegacy.objects.ability.FireWaterWeaknessAbility;
import minecraftschurli.arsmagicalegacy.objects.ability.FireWeaknessAbility;
import minecraftschurli.arsmagicalegacy.objects.ability.FrostAbility;
import minecraftschurli.arsmagicalegacy.objects.ability.FrostWalkerAbility;
import minecraftschurli.arsmagicalegacy.objects.ability.FulminationAbility;
import minecraftschurli.arsmagicalegacy.objects.ability.JumpHeightAbility;
import minecraftschurli.arsmagicalegacy.objects.ability.LavaWalkerAbility;
import minecraftschurli.arsmagicalegacy.objects.ability.LeafLikeAbility;
import minecraftschurli.arsmagicalegacy.objects.ability.LightningWaterWeaknessAbility;
import minecraftschurli.arsmagicalegacy.objects.ability.LungsAbility;
import minecraftschurli.arsmagicalegacy.objects.ability.MagicWeaknessAbility;
import minecraftschurli.arsmagicalegacy.objects.ability.ManaCostDecreaseAbility;
import minecraftschurli.arsmagicalegacy.objects.ability.NightVisionAbility;
import minecraftschurli.arsmagicalegacy.objects.ability.PacifistAbility;
import minecraftschurli.arsmagicalegacy.objects.ability.PhotosynthesisAbility;
import minecraftschurli.arsmagicalegacy.objects.ability.PoisonImmunityAbility;
import minecraftschurli.arsmagicalegacy.objects.ability.ReflexesAbility;
import minecraftschurli.arsmagicalegacy.objects.ability.RootedAbility;
import minecraftschurli.arsmagicalegacy.objects.ability.SolidBonesAbility;
import minecraftschurli.arsmagicalegacy.objects.ability.StepUpAbility;
import minecraftschurli.arsmagicalegacy.objects.ability.SunlightWeaknessAbility;
import minecraftschurli.arsmagicalegacy.objects.ability.SwiftSwimAbility;
import minecraftschurli.arsmagicalegacy.objects.ability.ThornsAbility;
import minecraftschurli.arsmagicalegacy.objects.ability.ThunderPunchAbility;
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
