package minecraftschurli.arsmagicalegacy.objects.block;

import net.minecraft.block.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;

/**
 * @author Minecraftschurli
 * @version 2020-01-15
 */
public class InlayBlock extends RailBlock {
    public InlayBlock(Block.Properties properties) {
        super(properties);
    }

    @Override
    public boolean areCornersDisabled() {
        return false;
    }

    @Override
    public boolean isFlexibleRail(BlockState state, IBlockReader world, BlockPos pos) {
        return true;
    }

    @Override
    public boolean canMakeSlopes(BlockState state, IBlockReader world, BlockPos pos) {
        return false;
    }
}
