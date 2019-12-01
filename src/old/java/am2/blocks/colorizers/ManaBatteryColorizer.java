package am2.blocks.colorizers;

import am2.blocks.tileentity.TileEntityManaBattery;
import am2.power.PowerTypes;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class ManaBatteryColorizer implements IBlockColor{
	@Override
	public int colorMultiplier(IBlockState state, IBlockAccess worldIn, BlockPos pos, int tintIndex) {
		try {
			TileEntity te = worldIn.getTileEntity(pos);
			if (te instanceof TileEntityManaBattery) {
				TileEntityManaBattery mb = (TileEntityManaBattery)te;
				if (mb.getPowerType() == PowerTypes.NONE)
					return 0xAAAAAA;
				return mb.getPowerType().getColor();
			}
		} catch (Throwable t) {}
		return 0xAAAAAA;
	}
}
