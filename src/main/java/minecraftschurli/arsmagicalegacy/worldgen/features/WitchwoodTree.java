package minecraftschurli.arsmagicalegacy.worldgen.features;

import minecraftschurli.arsmagicalegacy.init.*;
import net.minecraft.block.trees.*;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer;

import javax.annotation.*;
import java.util.*;

/**
 * @author Minecraftschurli
 * @version 2019-11-22
 */
public class WitchwoodTree extends Tree {
    public static final ConfiguredFeature<TreeFeatureConfig, ?> WITCHWOOD_TREE = ModFeatures.WITCHWOOD_TREE.get().withConfiguration(new TreeFeatureConfig.Builder(new SimpleBlockStateProvider(ModBlocks.WITCHWOOD_LOG.get().getDefaultState()), new SimpleBlockStateProvider(ModBlocks.WITCHWOOD_LEAVES.get().getDefaultState()), new BlobFoliagePlacer(2, 0)).baseHeight(5).heightRandA(2).foliageHeight(3).ignoreVines().setSapling(ModBlocks.WITCHWOOD_SAPLING.get()).build());
    @Nullable
    @Override
    protected ConfiguredFeature<TreeFeatureConfig, ?> getTreeFeature(Random randomIn, boolean p_225546_2_) {
        return WITCHWOOD_TREE;
    }
}
