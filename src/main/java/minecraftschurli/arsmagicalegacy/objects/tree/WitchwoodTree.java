package minecraftschurli.arsmagicalegacy.objects.tree;

import minecraftschurli.arsmagicalegacy.init.ModBlocks;
import net.minecraft.block.trees.Tree;
import net.minecraft.world.gen.feature.AbstractTreeFeature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.TreeFeature;

import javax.annotation.Nonnull;
import java.util.Random;

/**
 * @author Minecraftschurli
 * @version 2019-11-22
 */
public class WitchwoodTree extends Tree {
    @Override
    protected AbstractTreeFeature<NoFeatureConfig> getTreeFeature(@Nonnull Random random) {
        return new TreeFeature(NoFeatureConfig::deserialize, true, 5, ModBlocks.WITCHWOOD_LOG.get().getDefaultState(), ModBlocks.WITCHWOOD_LEAVES.get().getDefaultState(), false);
    }
}
