package am2.blocks.tileentity.flickers;

import am2.api.ArsMagicaAPI;
import am2.api.affinity.Affinity;
import am2.api.flickers.IFlickerController;
import am2.api.flickers.AbstractFlickerFunctionality;
import am2.defs.BlockDefs;
import am2.defs.ItemDefs;
import am2.utils.AffinityShiftUtils;
import am2.utils.DummyEntityPlayer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;

public class FlickerOperatorFlatLands extends AbstractFlickerFunctionality{
	
	public final static FlickerOperatorFlatLands instance = new FlickerOperatorFlatLands();
	
	@Override
	public boolean RequiresPower(){
		return false;
	}

	@Override
	public int PowerPerOperation(){
		return 10;
	}

	@Override
	public boolean DoOperation(World worldObj, IFlickerController<?> habitat, boolean powered){
		int searchesPerLoop = 12;

		int radius = 6;
		int diameter = radius * 2 + 1;

		if (!worldObj.isRemote){

			boolean actionPerformed = false;

			for (int i = 0; i < searchesPerLoop && !actionPerformed; ++i){
				int effectX = ((TileEntity)habitat).getPos().getX() - radius + (worldObj.rand.nextInt(diameter));
				int effectZ = ((TileEntity)habitat).getPos().getZ() - radius + (worldObj.rand.nextInt(diameter));
				int effectY = ((TileEntity)habitat).getPos().getY() + worldObj.rand.nextInt(radius);
				
				BlockPos effectPos = new BlockPos(effectX, effectY, effectZ);
				
				if (effectPos == ((TileEntity)habitat).getPos())
					return false;

				IBlockState block = worldObj.getBlockState(effectPos);

				if (block != null && !worldObj.isAirBlock(effectPos) && block.isOpaqueCube() && block.getBlock() != BlockDefs.invisibleUtility){
					if (ForgeEventFactory.doPlayerHarvestCheck(new DummyEntityPlayer(worldObj), block, true)){
						if (block.getBlock().removedByPlayer(block, worldObj, effectPos, new DummyEntityPlayer(worldObj), true)){
							block.getBlock().onBlockDestroyedByPlayer(worldObj, effectPos, block);
							block.getBlock().dropBlockAsItem(worldObj, effectPos, block, 0);
							actionPerformed = true;
						}
					}
				}
			}

			return actionPerformed;
		}else{
			return false;
		}
	}

	@Override
	public boolean DoOperation(World worldObj, IFlickerController<?> habitat, boolean powered, Affinity[] flickers){
		return DoOperation(worldObj, habitat, powered);
	}

	@Override
	public void RemoveOperator(World worldObj, IFlickerController<?> habitat, boolean powered){
	}

	@Override
	public int TimeBetweenOperation(boolean powered, Affinity[] flickers){
		return powered ? 1 : 20;
	}

	@Override
	public void RemoveOperator(World worldObj, IFlickerController<?> habitat, boolean powered, Affinity[] flickers){
	}


	@Override
	public Object[] getRecipe(){
		return new Object[]{
				"S P",
				"ENI",
				" R ",
				Character.valueOf('S'), Items.IRON_SHOVEL,
				Character.valueOf('P'), Items.IRON_PICKAXE,
				Character.valueOf('E'), new ItemStack(ItemDefs.flickerJar, 1, ArsMagicaAPI.getAffinityRegistry().getId(Affinity.EARTH)),
				Character.valueOf('N'), AffinityShiftUtils.getEssenceForAffinity(Affinity.EARTH),
				Character.valueOf('I'), new ItemStack(ItemDefs.flickerJar, 1, ArsMagicaAPI.getAffinityRegistry().getId(Affinity.ICE)),
				Character.valueOf('R'), new ItemStack(ItemDefs.rune, 1, EnumDyeColor.BLACK.getDyeDamage())
		};
	}
	
	@Override
	public ResourceLocation getTexture() {
		return new ResourceLocation("arsmagica2", "FlickerOperatorFlatLands");
	}

	@Override
	public Affinity[] getMask() {
		return new Affinity[]{Affinity.EARTH, Affinity.ICE};
	}

}
