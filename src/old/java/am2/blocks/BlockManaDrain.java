package am2.blocks;

import am2.blocks.tileentity.TileEntityManaDrain;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockManaDrain extends BlockAMPowered{

	public BlockManaDrain() {
		super(Material.IRON);
		defaultRender = true;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityManaDrain();
	}

}
