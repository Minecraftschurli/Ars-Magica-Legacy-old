package am2.blocks;

import java.util.List;

import am2.blocks.tileentity.TileEntityGroundRuneSpell;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockGroundRuneSpell extends BlockGroundRune{

	public BlockGroundRuneSpell(){
		super();
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int i){
		return new TileEntityGroundRuneSpell();
	}

	@Override
	protected boolean ActivateRune(World world, List<Entity> entitiesInRange, BlockPos pos){
		TileEntityGroundRuneSpell te = getTileEntity(world, pos);
		if (te == null)
			return false;
		
		boolean hasApplied = false;
		
		for (Entity e : entitiesInRange){
			if (e instanceof EntityLivingBase){
				if (te.canApply((EntityLivingBase)e)) {
					hasApplied = te.applySpellEffect((EntityLivingBase)e);
					break;
				}
			}
		}
		return hasApplied;
	}

	@Override
	protected boolean isPermanent(World world, BlockPos pos, IBlockState state){
		TileEntityGroundRuneSpell te = getTileEntity(world, pos);
		if (te == null)
			return false;
		return te.getPermanent();
	}

	@Override
	protected int getNumTriggers(World world, BlockPos pos, IBlockState state){
		TileEntityGroundRuneSpell te = getTileEntity(world, pos);
		if (te == null)
			return 1;
		return te.getNumTriggers();
	}

	@Override
	public void setNumTriggers(World world, BlockPos pos, IBlockState state, int numTriggers){
		TileEntityGroundRuneSpell te = getTileEntity(world, pos);
		if (te == null)
			return;
		te.setNumTriggers(numTriggers);
		if (numTriggers == -1)
			te.setPermanent(true);
	}

	private TileEntityGroundRuneSpell getTileEntity(World world, BlockPos pos){
		TileEntity te = world.getTileEntity(pos);
		if (te instanceof TileEntityGroundRuneSpell){
			return (TileEntityGroundRuneSpell)te;
		}
		return null;
	}

	public void setSpellStack(World world, BlockPos pos, ItemStack effect){
		TileEntityGroundRuneSpell te = getTileEntity(world, pos);
		if (te == null)
			return;
		te.setSpellStack(effect);
	}

	public void setPlacedBy(World world, BlockPos pos, EntityLivingBase caster){
		TileEntityGroundRuneSpell te = getTileEntity(world, pos);
		if (te == null)
			return;
		te.setPlacedBy(caster);
	}

	@Override
	public boolean placeAt(World world, BlockPos pos, IBlockState state){
		if (!canPlaceBlockAt(world, pos)) return false;
		if (!world.isRemote)
			world.setBlockState(pos, state);
		return true;
	}
}
