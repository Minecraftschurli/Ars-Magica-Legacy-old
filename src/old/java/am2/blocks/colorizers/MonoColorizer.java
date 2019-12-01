package am2.blocks.colorizers;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class MonoColorizer implements IBlockColor{
	
	private int color;

	public MonoColorizer(int color) {
		this.color = color;
	}
	
	@Override
	public int colorMultiplier(IBlockState state, IBlockAccess worldIn, BlockPos pos, int tintIndex) {
		return color;
	}
}
