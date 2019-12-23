package minecraftschurli.arsmagicalegacy.objects.tree;

import minecraftschurli.arsmagicalegacy.init.*;
import net.minecraft.block.trees.*;
import net.minecraft.world.gen.feature.*;

import javax.annotation.*;
import java.util.*;

/**
 * @author Minecraftschurli
 * @version 2019-11-22
 */
public class WitchwoodTree extends Tree {
    public static final AbstractTreeFeature<NoFeatureConfig> WITCHWOOD_TREE = new TreeFeature(NoFeatureConfig::deserialize, true, 5, ModBlocks.WITCHWOOD_LOG.get().getDefaultState(), ModBlocks.WITCHWOOD_LEAVES.get().getDefaultState(), false);

    @Override
    protected AbstractTreeFeature<NoFeatureConfig> getTreeFeature(@Nonnull Random random) {
        return WITCHWOOD_TREE;
    }
}
