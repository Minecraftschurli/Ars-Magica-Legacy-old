package am2.blocks;

import java.util.Random;

import am2.ArsMagica2;
import am2.defs.CreativeTabsDefs;
import am2.items.ItemBlockSubtypes;
import am2.particles.AMParticle;
import am2.particles.ParticleFloatUpward;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockVinteumTorch extends BlockTorch{

	public BlockVinteumTorch(){
		super();
		setCreativeTab(CreativeTabsDefs.tabAM2Blocks);
	}

	@Override
	public int getLightValue(IBlockState state){
		return 14;
	}
	
	@Override
	public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        EnumFacing enumfacing = (EnumFacing)stateIn.getValue(FACING);
        double d0 = (double)pos.getX() + 0.5D;
        double d1 = (double)pos.getY() + 0.7D;
        double d2 = (double)pos.getZ() + 0.5D;
        double d3 = 0.22D;
        double d4 = 0.27D;

        if (enumfacing.getAxis().isHorizontal())
        {
            EnumFacing enumfacing1 = enumfacing.getOpposite();
            spawnParticle(worldIn, d0 + d4 * (double)enumfacing1.getFrontOffsetX(), d1 + d3, d2 + d4 * (double)enumfacing1.getFrontOffsetZ());
            spawnParticle(worldIn, d0 + d4 * (double)enumfacing1.getFrontOffsetX(), d1 + d3, d2 + d4 * (double)enumfacing1.getFrontOffsetZ());
        }
        else
        {
            spawnParticle(worldIn, d0, d1, d2);
            spawnParticle(worldIn, d0, d1, d2);
        }
    }

	@SideOnly(Side.CLIENT)
	private void spawnParticle(World world, double x, double y, double z){
		AMParticle effect = (AMParticle)ArsMagica2.proxy.particleManager.spawn(world, "light", x, y, z);
		if (effect != null){
			effect.setMaxAge(3);
			effect.setIgnoreMaxAge(false);
			effect.setRGBColorF(0.69f, 0.89f, 1.0f);
			effect.AddParticleController(new ParticleFloatUpward(effect, 0, 0.01f, 1, false));
		}
	}
	
	public BlockVinteumTorch registerAndName(ResourceLocation rl) {
		this.setUnlocalizedName(rl.toString());
		GameRegistry.register(this, rl);
		GameRegistry.register(new ItemBlockSubtypes(this), rl);
		return this;
	}
}
