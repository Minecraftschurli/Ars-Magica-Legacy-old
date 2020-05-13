package minecraftschurli.arsmagicalegacy.objects.block.craftingaltar;

import java.util.function.Supplier;
import javax.annotation.Nullable;
import minecraftschurli.arsmagicalegacy.api.multiblock.Structure;
import net.minecraft.block.BlockState;
import net.minecraft.block.LecternBlock;
import net.minecraft.block.LeverBlock;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

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
        if (block == null) return true;
        else if (block.getBlock() instanceof LecternBlock)
            return world.getBlockState(pos).get(LecternBlock.FACING) == Direction.byHorizontalIndex((block.get(LecternBlock.FACING).getHorizontalIndex() + direction.getHorizontalIndex()));
        else if (block.getBlock() instanceof LeverBlock)
            return world.getBlockState(pos).equals(block.with(BlockStateProperties.HORIZONTAL_FACING, Direction.byHorizontalIndex((block.get(BlockStateProperties.HORIZONTAL_FACING).getHorizontalIndex() + direction.getHorizontalIndex())))) || world.getBlockState(pos).equals(block.with(BlockStateProperties.HORIZONTAL_FACING, Direction.byHorizontalIndex((block.get(BlockStateProperties.HORIZONTAL_FACING).getHorizontalIndex() + direction.getHorizontalIndex()))).with(LeverBlock.POWERED, true));
        else if (block.getBlock() instanceof CraftingAltarBlock)
            return world.getBlockState(pos).equals(block) || world.getBlockState(pos).equals(block.with(CraftingAltarBlock.FORMED, true));
        else return super.checkState(block, world, pos, direction);
    }
}
