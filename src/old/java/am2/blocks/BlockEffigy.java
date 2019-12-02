package am2.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;

public class BlockEffigy extends BlockAM {
	
	public static final PropertyInteger PROGRESS = PropertyInteger.create("progress", 0, 15);

	public BlockEffigy(Material materialIn) {
		super(materialIn);
		this.setDefaultState(blockState.getBaseState().withProperty(PROGRESS, 0));
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, PROGRESS);
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(PROGRESS);
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(PROGRESS, meta);
	}
}
