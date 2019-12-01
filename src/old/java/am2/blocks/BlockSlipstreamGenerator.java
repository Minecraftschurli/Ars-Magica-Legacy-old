package am2.blocks;

import am2.blocks.tileentity.TileEntitySlipstreamGenerator;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.world.World;

public class BlockSlipstreamGenerator extends BlockAMPowered{

	public BlockSlipstreamGenerator(){
		super(Material.WOOD);
		defaultRender = true;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int i){
		return new TileEntitySlipstreamGenerator();
	}
	
	@Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }
	
	@Override
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.TRANSLUCENT;
	}
}
