package minecraftschurli.arsmagicalegacy.worldgen.structures;

import com.mojang.datafixers.*;
import minecraftschurli.arsmagicalegacy.init.*;
import net.minecraft.block.*;
import net.minecraft.tags.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import net.minecraft.world.gen.*;
import net.minecraft.world.gen.feature.*;
import net.minecraftforge.common.*;

import java.util.*;
import java.util.function.*;

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
        if (!canReplace(worldIn, pos.down()))
            return false;
        if (rand.nextInt(30) > 0)
            return false;
        BlockState moonstone_ore = ModBlocks.MOONSTONE_ORE.get().getDefaultState();
        //pos = pos.offset(Direction.UP);
        setBlockState(worldIn, pos, moonstone_ore);
        for (Direction value : Direction.values()) {
            if (canReplace(worldIn, pos.offset(value)) || worldIn.isAirBlock(pos.offset(value)))
                setBlockState(worldIn, pos.offset(value), moonstone_ore);
        }
        return false;
    }

    private boolean canReplace(IWorld worldIn, BlockPos pos) {
        BlockState state = worldIn.getBlockState(pos);
        Block block = state.getBlock();
        return BlockTags.DIRT_LIKE.contains(block) ||
                BlockTags.SAND.contains(block) ||
                Tags.Blocks.DIRT.contains(block) ||
                Tags.Blocks.SAND.contains(block) ||
                Tags.Blocks.STONE.contains(block) ||
                Tags.Blocks.GRAVEL.contains(block);
    }
}
