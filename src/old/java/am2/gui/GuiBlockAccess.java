package am2.gui;

import net.minecraft.block.state.*;
import net.minecraft.init.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import net.minecraft.world.biome.*;
import net.minecraftforge.fml.common.registry.*;
import net.minecraftforge.fml.relauncher.*;

public class GuiBlockAccess implements IBlockAccess{

	private IBlockState fakeBlock = Blocks.AIR.getDefaultState();
	private TileEntity controllingTileEntity;
	private IBlockAccess outerBlockAccess;

	private int overridex, overridey, overridez;

	public void setControllingTileEntity(TileEntity controllingTileEntity){
		this.controllingTileEntity = controllingTileEntity;
	}

	public void setOuterBlockAccess(IBlockAccess outer){
		this.outerBlockAccess = outer;
	}

	public void setOverrideCoords(int x, int y, int z){
		overridex = x;
		overridey = y;
		overridez = z;
	}

	public void setFakeBlockAndMeta(IBlockState state){
		this.fakeBlock = state;
	}

	@Override
	public IBlockState getBlockState(BlockPos pos){
		if (pos.getX() == overridex && pos.getY() == overridey && pos.getZ() == overridez)
			return this.fakeBlock;
		if (outerBlockAccess != null)
			return outerBlockAccess.getBlockState(pos);
		return Blocks.AIR.getDefaultState();
	}

	@Override
	public TileEntity getTileEntity(BlockPos pos){
		if (pos.getX() == overridex && pos.getY() == overridey && pos.getZ() == overridez) return controllingTileEntity;
		if (outerBlockAccess != null)
			return outerBlockAccess.getTileEntity(pos);
		return null;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean isAirBlock(BlockPos pos){
		return false;
	}

	@Override
	public Biome getBiome(BlockPos blockPos) {
		return GameRegistry.findRegistry(Biome.class).getValue(new ResourceLocation("plains"));
	}

	//@Override
	//@SideOnly(Side.CLIENT)
	//public boolean extendedLevelsInChunkCache(){
	//	return false;
	//}

	@Override
	public boolean isSideSolid(BlockPos pos, EnumFacing arg3, boolean arg4){
		return false;
	}

	@Override
	public int getCombinedLight(BlockPos pos, int lightValue) {
		if (outerBlockAccess != null)
			return outerBlockAccess.getCombinedLight(pos, lightValue);
		return 15728704;
	}

	@Override
	public int getStrongPower(BlockPos pos, EnumFacing direction) {
		return 0;
	}

	@Override
	public WorldType getWorldType() {
		return WorldType.FLAT;
	}

}
