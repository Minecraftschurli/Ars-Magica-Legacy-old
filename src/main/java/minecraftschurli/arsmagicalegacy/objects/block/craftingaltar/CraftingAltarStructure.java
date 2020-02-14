package minecraftschurli.arsmagicalegacy.objects.block.craftingaltar;

import minecraftschurli.arsmagicalegacy.api.multiblock.*;
import net.minecraft.block.*;
import net.minecraft.state.properties.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;

import javax.annotation.*;
import java.util.function.*;

/**
 * @author Minecraftschurli
 * @version 2019-12-26
 */
public class CraftingAltarStructure extends Structure {
    public CraftingAltarStructure(Supplier<BlockState>[][][] suppliers) {
        super(suppliers);
    }

    @Override
    protected boolean checkState(@Nullable BlockState block, World world, BlockPos pos, Direction direction) {
        if (block == null) {
            return true;
        } else if (block.getBlock() instanceof LecternBlock) {
            return world.getBlockState(pos).get(LecternBlock.FACING) == Direction.byHorizontalIndex((block.get(LecternBlock.FACING).getHorizontalIndex() + direction.getHorizontalIndex()));
        } else if (block.getBlock() instanceof LeverBlock) {
            return world.getBlockState(pos).equals(block.with(BlockStateProperties.HORIZONTAL_FACING, Direction.byHorizontalIndex((block.get(BlockStateProperties.HORIZONTAL_FACING).getHorizontalIndex() + direction.getHorizontalIndex())))) || world.getBlockState(pos).equals(block.with(BlockStateProperties.HORIZONTAL_FACING, Direction.byHorizontalIndex((block.get(BlockStateProperties.HORIZONTAL_FACING).getHorizontalIndex() + direction.getHorizontalIndex()))).with(LeverBlock.POWERED, true));
        } else {
            return super.checkState(block, world, pos, direction);
        }
    }
}
