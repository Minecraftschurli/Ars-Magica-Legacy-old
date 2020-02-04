package minecraftschurli.arsmagicalegacy.worldgen.features;

import minecraftschurli.arsmagicalegacy.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowerBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.FlowersFeature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraftforge.fml.RegistryObject;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * @author Minecraftschurli
 * @version 2020-01-18
 */
public class AMFlowerFeature /*extends FlowersFeature*/ {
    /*private static final List<RegistryObject<FlowerBlock>> FLOWERS = Arrays.asList(ModBlocks.CERUBLOSSOM, ModBlocks.TARMA_ROOT, ModBlocks.WAKEBLOOM, ModBlocks.AUM);

    public AMFlowerFeature() {
        super(NoFeatureConfig::deserialize);
    }

    @Nonnull
    @Override
    public BlockState getRandomFlower(@Nonnull Random random, BlockPos pos) {
        double d0 = MathHelper.clamp((1.0D + Biome.INFO_NOISE.getValue((double)pos.getX() / 48.0D, (double)pos.getZ() / 48.0D)) / 2.0D, 0.0D, 0.9999D);
        Block block = FLOWERS.get((int)(d0 * (double)FLOWERS.size())).orElse((FlowerBlock)Blocks.POPPY);
        return block.getDefaultState();
    }*/
}
