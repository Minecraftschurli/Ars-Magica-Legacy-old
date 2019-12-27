package minecraftschurli.arsmagicalegacy.objects.block.craftingaltar;

import minecraftschurli.arsmagicalegacy.ArsMagicaLegacy;
import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.*;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
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
        //((CraftingAltarTileEntity)worldIn.getTileEntity(pos)).STRUCTURE.place(worldIn, pos, Direction.NORTH);
        ArsMagicaLegacy.LOGGER.debug("{}", ((CraftingAltarTileEntity)worldIn.getTileEntity(pos)).checkMultiblock(worldIn));
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
