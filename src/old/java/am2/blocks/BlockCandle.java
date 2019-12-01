package am2.blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import am2.blocks.tileentity.TileEntityCandle;
import am2.defs.ItemDefs;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockCandle extends BlockAMSpecialRenderContainer{

	public BlockCandle(){
		super(Material.WOOD);
		setHardness(1.0f);
		setResistance(1.0f);
		setBlockBounds(0.35f, 0f, 0.35f, 0.65f, 0.45f, 0.65f);
	}
	
	@Override
	public int getLightValue(IBlockState state) {
		return 14;
	}
	
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}
	
	@Override
	public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
		ArrayList<ItemStack> drops = new ArrayList<ItemStack>();
		drops.add(new ItemStack(ItemDefs.wardingCandle));
		return drops;
	}
	
	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
		return new ItemStack(ItemDefs.wardingCandle);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int i){
		return new TileEntityCandle();
	}
	
	@Override
	public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		worldIn.spawnParticle(EnumParticleTypes.FLAME, pos.getX() + 0.5, pos.getY() + 0.65, pos.getZ() + 0.5, 0, 0, 0);
	}
	
	@Override
	public BlockAMContainer registerAndName(ResourceLocation rl) {
		this.setUnlocalizedName(rl.toString());
		GameRegistry.register(this, rl);
		return this;
	}
}
