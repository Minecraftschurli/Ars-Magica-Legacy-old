package minecraftschurli.arsmagicalegacy.objects.block.craftingaltar;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nullable;

/**
 * @author Minecraftschurli
 * @version 2019-12-13
 */
public class CraftingAltarBlock extends Block {
    public CraftingAltarBlock() {
        super(Block.Properties.create(Material.IRON).harvestLevel(0).hardnessAndResistance(3.0f).harvestTool(ToolType.PICKAXE));
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
