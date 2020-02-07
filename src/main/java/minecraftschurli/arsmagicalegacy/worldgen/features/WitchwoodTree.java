package minecraftschurli.arsmagicalegacy.worldgen.features;

import minecraftschurli.arsmagicalegacy.init.ModFeatures;
import net.minecraft.block.trees.Tree;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;

import javax.annotation.Nullable;
import java.util.Random;

/**
 * @author Minecraftschurli
 * @version 2019-11-22
 */
public class WitchwoodTree extends Tree {
    @Nullable
    @Override
    protected ConfiguredFeature<TreeFeatureConfig, ?> getTreeFeature(Random randomIn, boolean p_225546_2_) {
        return ModFeatures.WITCHWOOD_TREE.get().withConfiguration(ModFeatures.WITCHWOOD_TREE_CONFIG.get());
    }
}
