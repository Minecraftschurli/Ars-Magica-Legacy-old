package minecraftschurli.arsmagicalegacy.worldgen.features;

import minecraftschurli.arsmagicalegacy.init.*;
import net.minecraft.block.trees.*;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer;

import javax.annotation.*;
import java.util.*;
import java.util.function.Supplier;

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
