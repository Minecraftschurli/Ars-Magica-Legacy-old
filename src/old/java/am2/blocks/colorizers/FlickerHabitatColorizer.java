package am2.blocks.colorizers;

import am2.blocks.tileentity.TileEntityFlickerHabitat;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class FlickerHabitatColorizer implements IBlockColor{

	@Override
	public int colorMultiplier(IBlockState state, IBlockAccess worldIn, BlockPos pos, int tintIndex) {
//		if (tintIndex == 0)
//			return 0xffffff;
//		TileEntity te = worldIn.getTileEntity(pos);
//		if (te == null || !(te instanceof TileEntityFlickerHabitat))
//			return 0xffffff;
//		int meta = ArsMagicaAPI.getAffinityRegistry().getId(((TileEntityFlickerHabitat)te).getSelectedAffinity());
//		Affinity aff = ArsMagicaAPI.getAffinityRegistry().getObjectById(meta);
		TileEntity te = worldIn.getTileEntity(pos);
		if (te == null || !(te instanceof TileEntityFlickerHabitat))
			return 0xffffff;
		return ((TileEntityFlickerHabitat)te).getCrystalColor();
	}

}
