package am2.blocks.colorizers;

import am2.blocks.BlockCrystalMarker;
import am2.utils.RenderUtils;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class CrystalMarkerColorizer implements IBlockColor{
	@Override
	public int colorMultiplier(IBlockState state, IBlockAccess worldIn, BlockPos pos, int tintIndex) {
		try {
			//System.out.println(state);
			int meta = state.getValue(BlockCrystalMarker.TYPE);
			//System.out.println(meta);
			switch (meta){
			case BlockCrystalMarker.META_IN:
				return RenderUtils.getColor(0.94f, 0.69f, 0.01f); //yellow
			case BlockCrystalMarker.META_LIKE_EXPORT:
				return RenderUtils.getColor(0.10f, 0.65f, 0.0f); //green
			case BlockCrystalMarker.META_OUT:
				return RenderUtils.getColor(0.10f, 0.10f, 0.88f); //blue
			case BlockCrystalMarker.META_REGULATE_EXPORT:
				return RenderUtils.getColor(0.56f, 0.08f, 0.66f); //purple
			case BlockCrystalMarker.META_SET_EXPORT:
				return RenderUtils.getColor(0.085f, 0.72f, 0.88f); //light blue
			case BlockCrystalMarker.META_REGULATE_MULTI:
				return RenderUtils.getColor(0.92f, 0.61f, 0.3f); //orange
			case BlockCrystalMarker.META_SET_IMPORT:
				return RenderUtils.getColor(1.0f, 0.0f, 0.0f); //red
			case BlockCrystalMarker.META_SPELL_EXPORT:
				return RenderUtils.getColor(0.0f, 0.5f, 1.0f); //cyan
			}
		} catch (Throwable t) {}
		return 0xAAAAAA;
	}
}
