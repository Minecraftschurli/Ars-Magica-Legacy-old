package minecraftschurli.arsmagicalegacy.init;

import minecraftschurli.arsmagicalegacy.worldgen.features.*;
import net.minecraft.world.gen.blockstateprovider.*;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.foliageplacer.*;
import net.minecraftforge.fml.*;

import java.util.function.*;

/**
 * @author Minecraftschurli
 * @version 2020-01-18
 */
public final class ModFeatures implements IInit {
    public static final Supplier<TreeFeatureConfig> WITCHWOOD_TREE_CONFIG = () -> {
        return new TreeFeatureConfig.Builder(new SimpleBlockStateProvider(ModBlocks.WITCHWOOD_LOG.get().getDefaultState()), new SimpleBlockStateProvider(ModBlocks.WITCHWOOD_LEAVES.get().getDefaultState()), new BlobFoliagePlacer(2, 0)).baseHeight(5).heightRandA(2).foliageHeight(3).ignoreVines().setSapling(ModBlocks.WITCHWOOD_SAPLING.get()).build();
    };

    public static final RegistryObject<MoonstoneMeteor> MOONSTONE_METEOR = FEATURES.register("moonstone_meteor", MoonstoneMeteor::new);
    public static final RegistryObject<SunstoneOre> SUNSTONE_ORE = FEATURES.register("sunstone_ore", SunstoneOre::new);
    public static final RegistryObject<Feature<TreeFeatureConfig>> WITCHWOOD_TREE = FEATURES.register("witchwood_tree", () -> new TreeFeature(TreeFeatureConfig::func_227338_a_));
//    public static final RegistryObject<AMFlowerFeature> FLOWERS = FEATURES.register("flowers", AMFlowerFeature::new);
//    public static final RegistryObject<DesertNova> DESERT_NOVA = FEATURES.register("desert_nova", DesertNova::new);

    public static void register() {
    }
}
