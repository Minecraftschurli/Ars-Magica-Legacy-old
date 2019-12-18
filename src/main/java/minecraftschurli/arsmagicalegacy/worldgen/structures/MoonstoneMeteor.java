package minecraftschurli.arsmagicalegacy.worldgen.structures;

import com.mojang.datafixers.Dynamic;
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

import java.util.Random;
import java.util.function.Function;

/**
 * @author Minecraftschurli
 * @version 2019-11-22
 */
public class MoonstoneMeteor extends Feature<NoFeatureConfig> {
    public MoonstoneMeteor(Function<Dynamic<?>, ? extends NoFeatureConfig> deserializer) {
        super(deserializer);
    }

    @Override
    public boolean place(IWorld worldIn, ChunkGenerator<? extends GenerationSettings> generator, Random rand, BlockPos pos, NoFeatureConfig config) {
        BlockState moonstone_ore = ModBlocks.MOONSTONE_ORE.get().getDefaultState();
        pos = pos.offset(Direction.UP);
        setBlockState(worldIn, pos, moonstone_ore);
        for (Direction value : Direction.values()) {
            if (canReplace(worldIn, pos.offset(value)))
                setBlockState(worldIn, pos.offset(value), moonstone_ore);
        }
        return false;
    }

    private boolean canReplace(IWorld worldIn, BlockPos pos) {
        if (worldIn.isAirBlock(pos))
            return true;
        BlockState state = worldIn.getBlockState(pos);
        Block block = state.getBlock();
        return Tags.Blocks.GRAVEL.contains(block) ||
                BlockTags.SAND.contains(block) ||
                Tags.Blocks.DIRT.contains(block) ||
                Tags.Blocks.SAND.contains(block) ||
                Tags.Blocks.STONE.contains(block);
    }
}
