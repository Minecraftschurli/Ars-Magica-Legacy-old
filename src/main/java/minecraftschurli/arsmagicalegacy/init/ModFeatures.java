package minecraftschurli.arsmagicalegacy.init;

import java.util.function.Supplier;
import minecraftschurli.arsmagicalegacy.worldgen.features.MoonstoneMeteor;
import minecraftschurli.arsmagicalegacy.worldgen.features.SunstoneOre;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.TreeFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer;
import net.minecraftforge.fml.RegistryObject;

/**
 * @author Minecraftschurli
 * @version 2020-01-18
 */
public final class ModFeatures implements IInit {
    public static final Supplier<TreeFeatureConfig> WITCHWOOD_TREE_CONFIG = () -> new TreeFeatureConfig.Builder(new SimpleBlockStateProvider(ModBlocks.WITCHWOOD_LOG.get().getDefaultState()), new SimpleBlockStateProvider(ModBlocks.WITCHWOOD_LEAVES.get().getDefaultState()), new BlobFoliagePlacer(2, 0)).baseHeight(5).heightRandA(2).foliageHeight(3).ignoreVines().setSapling(ModBlocks.WITCHWOOD_SAPLING.get()).build();
    public static final RegistryObject<MoonstoneMeteor> MOONSTONE_METEOR = FEATURES.register("moonstone_meteor", MoonstoneMeteor::new);
    public static final RegistryObject<SunstoneOre> SUNSTONE_ORE = FEATURES.register("sunstone_ore", SunstoneOre::new);
    public static final RegistryObject<Feature<TreeFeatureConfig>> WITCHWOOD_TREE = FEATURES.register("witchwood_tree", () -> new TreeFeature(TreeFeatureConfig::deserializeFoliage));
//    public static final RegistryObject<AMFlowerFeature> FLOWERS = FEATURES.register("flowers", AMFlowerFeature::new);
//    public static final RegistryObject<DesertNova> DESERT_NOVA = FEATURES.register("desert_nova", DesertNova::new);

    public static void register() {
    }
}
