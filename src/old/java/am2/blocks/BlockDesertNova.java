package am2.blocks;

import java.util.Collection;
import java.util.HashSet;
import java.util.Random;

import am2.ArsMagica2;
import am2.particles.AMParticle;
import am2.particles.ParticleExpandingCollapsingRingAtPoint;
import am2.particles.ParticleFadeOut;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

public class BlockDesertNova extends BlockAMFlower{

	static HashSet<Block> blockSands = null;

	public BlockDesertNova(){
		super();
	}

	@Override
	public int tickRate(World par1World){
		return 800;
	}
	
	@Override
	public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos) {
		return EnumPlantType.Desert;
	}
	
	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		if (worldIn.getBlockState(pos.down()).getBlock() == Blocks.SAND){
			return true;
		}
		if (blockSands == null){// sand is defined by Forge, hence only first call will be 'true'
			Collection<ItemStack> itemStackSands = OreDictionary.getOres("sand", false);
			blockSands = new HashSet<Block>(itemStackSands.size());
			for (ItemStack itemStack : itemStackSands){
				Block oreBlock = Block.getBlockFromItem(itemStack.getItem());
				if (oreBlock != Blocks.AIR){
					blockSands.add(oreBlock);
				}
			}
		}
		return blockSands != null && blockSands.contains(worldIn.getBlockState(pos.down()).getBlock());
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random par5Random){

		if (par5Random.nextInt(10) != 0) return;

		int increment = ArsMagica2.config.getGFXLevel() * 15;

		if (increment <= 0) return;

		for (int i = 0; i < 360; i += increment){
			int angle = i;
			double posX = pos.getX() + 0.5 + Math.cos(angle) * 3;
			double posZ = pos.getY() + 0.5 + Math.sin(angle) * 3;
			double posY = pos.getZ() + 0.6 + par5Random.nextFloat() * 0.2f;

			AMParticle effect = (AMParticle)ArsMagica2.proxy.particleManager.spawn(world, "explosion_2", pos.getX() + 0.5, posY, pos.getZ() + 0.5);
			if (effect != null){
				effect.setIgnoreMaxAge(true);
				effect.AddParticleController(new ParticleExpandingCollapsingRingAtPoint(effect, posX, posY, posZ, 0.3, 3, 0.2, 1, false).setExpanding());
				//effect.AddParticleController(new ParticleOrbitPoint(effect, x+0.5, y, z+0.5, 1, false).SetTargetDistance(0.35f + par5Random.nextFloat() * 0.2f).SetOrbitSpeed(0.5f).setIgnoreYCoordinate(true));
				//effect.AddParticleController(new ParticleFloatUpward(effect, 0, 0.035f, 1, false));
				effect.AddParticleController(new ParticleFadeOut(effect, 2, false).setFadeSpeed(0.05f).setKillParticleOnFinish(true));
				effect.setParticleScale(0.05f);
			}
		}
	}
}
