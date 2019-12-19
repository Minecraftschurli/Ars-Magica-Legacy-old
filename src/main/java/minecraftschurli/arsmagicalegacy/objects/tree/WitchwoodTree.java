package minecraftschurli.arsmagicalegacy.objects.tree;

import minecraftschurli.arsmagicalegacy.init.ModBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.block.trees.Tree;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

/**
 * @author Minecraftschurli
 * @version 2019-11-22
 */
public class WitchwoodTree extends Tree {
    public static final Feature<TreeFeatureConfig> WITCHWOOD_TREE_FEATURE = new TreeFeature(TreeFeatureConfig::func_227338_a_);
    public static final TreeFeatureConfig WITCHWOOD_TREE_FEATURE_CONFIG = new TreeFeatureConfig.Builder(new SimpleBlockStateProvider(ModBlocks.WITCHWOOD_LOG.get().getDefaultState()), new SimpleBlockStateProvider(ModBlocks.WITCHWOOD_LEAVES.get().getDefaultState()), new BlobFoliagePlacer(2, 0)).func_225569_d_(4).func_227354_b_(2).func_227360_i_(3).func_227352_a_().setSapling((net.minecraftforge.common.IPlantable) ModBlocks.WITCHWOOD_SAPLING.get()).func_225568_b_();

    @Nullable
    @Override
    protected ConfiguredFeature<TreeFeatureConfig, ?> func_225546_b_(Random random) {
        return WITCHWOOD_TREE_FEATURE.func_225566_b_(WITCHWOOD_TREE_FEATURE_CONFIG);
    }
}
