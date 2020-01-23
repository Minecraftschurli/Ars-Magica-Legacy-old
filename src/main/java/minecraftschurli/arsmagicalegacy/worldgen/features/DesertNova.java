package minecraftschurli.arsmagicalegacy.worldgen.features;

import minecraftschurli.arsmagicalegacy.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.feature.FlowersFeature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;

/**
 * @author Minecraftschurli
 * @version 2020-01-18
 */
public class DesertNova extends FlowersFeature {
    public DesertNova() {
        super(NoFeatureConfig::deserialize);
    }

    @Override
    public BlockState getRandomFlower(Random random, BlockPos pos) {
        return ModBlocks.DESERT_NOVA.lazyMap(Block::getDefaultState).get();
    }
}
