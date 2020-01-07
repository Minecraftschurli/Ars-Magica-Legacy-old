package minecraftschurli.arsmagicalegacy.objects.block.craftingaltar;

import net.minecraft.block.*;
import net.minecraft.tileentity.*;
import net.minecraft.world.*;

import javax.annotation.*;

/**
 * @author Minecraftschurli
 * @version 2019-12-30
 */
public class CraftingAltarViewBlock extends AirBlock {
    public CraftingAltarViewBlock() {
        super(Properties.from(Blocks.AIR));
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new CraftingAltarViewTileEntity();
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.ENTITYBLOCK_ANIMATED;
    }
}
