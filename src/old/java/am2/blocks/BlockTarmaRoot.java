package am2.blocks;

import java.util.HashSet;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.oredict.OreDictionary;

public class BlockTarmaRoot extends BlockAMFlower{

	static HashSet<Block> blockStones = null;

	public BlockTarmaRoot(){
		super();
	}

	@Override
	public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos){
		return EnumPlantType.Cave;
	}

	//EoD: restrict Tarma Roots growth by the blocks in canPlaceBlockOn()
	@Override
	public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state){
		return canPlaceBlockAt(worldIn, pos) && super.canBlockStay(worldIn, pos, state);
	}
	
	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		Block block = worldIn.getBlockState(pos.down()).getBlock();
		if (block.equals(Blocks.STONE) || block == Blocks.COBBLESTONE){
			return true;
		}
		if (blockStones == null){// stone and cobblestone are defined by Forge, hence only first call will be 'true'
			HashSet<ItemStack> itemStackStones = new HashSet<ItemStack>();
			itemStackStones.addAll(OreDictionary.getOres("stone", false));
			itemStackStones.addAll(OreDictionary.getOres("stoneCobble", false));
			itemStackStones.addAll(OreDictionary.getOres("cobblestone", false));
			blockStones = new HashSet<Block>(itemStackStones.size());
			for (ItemStack itemStack : itemStackStones){
				Block oreBlock = Block.getBlockFromItem(itemStack.getItem());
				if (oreBlock != Blocks.AIR){
					blockStones.add(oreBlock);
				}
			}
		}
		return blockStones != null && blockStones.contains(block);
	}

	@Override
	public boolean canGrowOn(World worldIn, BlockPos pos) {
		return canPlaceBlockAt(worldIn, pos) && worldIn.getLight(pos) < 4;
	}
}
