package minecraftschurli.arsmagicalegacy.worldgen.features;

import minecraftschurli.arsmagicalegacy.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraftforge.common.Tags;

import javax.annotation.Nonnull;
import java.util.Random;

/**
 * @author Minecraftschurli
 * @version 2019-11-22
 */
public class MoonstoneMeteor extends Feature<NoFeatureConfig> {
    public MoonstoneMeteor() {
        super(NoFeatureConfig::deserialize);
    }

    @Override
    public boolean place(@Nonnull IWorld worldIn, @Nonnull ChunkGenerator<? extends GenerationSettings> generator, @Nonnull Random rand, @Nonnull BlockPos pos, @Nonnull NoFeatureConfig config) {
        if (!canReplace(worldIn, pos.down()))
            return false;
        if (rand.nextInt(30) > 0)
            return false;
        BlockState moonstone_ore = ModBlocks.MOONSTONE_ORE.get().getDefaultState();
        //pos = pos.offset(Direction.UP);
        setBlockState(worldIn, pos, moonstone_ore);
        if (rand.nextBoolean()) {
            for (Direction value : Direction.values()) {
                if (rand.nextBoolean()) {
                    if (canReplace(worldIn, pos.offset(value)) || worldIn.isAirBlock(pos.offset(value))) {
                        setBlockState(worldIn, pos.offset(value), moonstone_ore);
                    }
                }
            }
        }
        return false;
    }

    private boolean canReplace(@Nonnull IWorld worldIn, @Nonnull BlockPos pos) {
        BlockState state = worldIn.getBlockState(pos);
        Block block = state.getBlock();
        return BlockTags.SAND.contains(block) ||
                Tags.Blocks.DIRT.contains(block) ||
                Tags.Blocks.SAND.contains(block) ||
                Tags.Blocks.STONE.contains(block) ||
                Tags.Blocks.GRAVEL.contains(block);
    }
}
