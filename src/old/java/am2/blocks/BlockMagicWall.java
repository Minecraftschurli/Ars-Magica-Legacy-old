package am2.blocks;

import am2.defs.CreativeTabsDefs;
import am2.entity.EntitySpellProjectile;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockMagicWall extends BlockAM {

	public BlockMagicWall() {
		super(Material.BARRIER);
		setCreativeTab(CreativeTabsDefs.tabAM2Blocks);
		setHardness(3.0F);
		setResistance(5.0F);
		setHarvestLevel("pickaxe", -1);
	}
	
	@Override
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
		if (entityIn instanceof EntitySpellProjectile) {
			EntitySpellProjectile projectile = (EntitySpellProjectile)entityIn;
			projectile.setBounces(projectile.getBounces() + 1);
		}
	}
	
	
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}
	
	@Override
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.TRANSLUCENT;
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
}
