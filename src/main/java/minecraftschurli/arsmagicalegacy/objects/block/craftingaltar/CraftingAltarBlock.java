package minecraftschurli.arsmagicalegacy.objects.block.craftingaltar;

import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import net.minecraftforge.common.*;

import javax.annotation.*;

/**
 * @author Minecraftschurli
 * @version 2019-12-13
 */
public class CraftingAltarBlock extends Block {
    public CraftingAltarBlock() {
        super(Block.Properties.create(Material.IRON).harvestLevel(0).hardnessAndResistance(3.0f).harvestTool(ToolType.PICKAXE));
    }

    @Override
    public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (player.abilities.isCreativeMode && player.getHeldItem(handIn).getItem() == Items.NETHER_STAR)
            ((CraftingAltarTileEntity) worldIn.getTileEntity(pos)).placeStructure(worldIn, player.getHorizontalFacing());
        return true;
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new CraftingAltarTileEntity();
    }
}
