package minecraftschurli.arsmagicalegacy.init;

import minecraftschurli.arsmagicalegacy.capabilities.ResearchStorage;
import minecraftschurli.arsmagicalegacy.worldgen.features.AMFlowerFeature;
import minecraftschurli.arsmagicalegacy.worldgen.features.DesertNova;
import minecraftschurli.arsmagicalegacy.worldgen.features.MoonstoneMeteor;
import minecraftschurli.arsmagicalegacy.worldgen.features.SunstoneOre;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.TreeFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraftforge.fml.RegistryObject;

/**
 * @author Minecraftschurli
 * @version 2020-01-18
 */
public final class ModFeatures implements IInit {
    public static final RegistryObject<MoonstoneMeteor> MOONSTONE_METEOR = FEATURES.register("moonstone_meteor", MoonstoneMeteor::new);
    public static final RegistryObject<SunstoneOre> SUNSTONE_ORE = FEATURES.register("sunstone_ore", SunstoneOre::new);
    public static final RegistryObject<Feature<TreeFeatureConfig>> WITCHWOOD_TREE = FEATURES.register("witchwood_tree", () -> new TreeFeature(TreeFeatureConfig::func_227338_a_));
//    public static final RegistryObject<AMFlowerFeature> FLOWERS = FEATURES.register("flowers", AMFlowerFeature::new);
//    public static final RegistryObject<DesertNova> DESERT_NOVA = FEATURES.register("desert_nova", DesertNova::new);

    public static void register() {
    }
}
