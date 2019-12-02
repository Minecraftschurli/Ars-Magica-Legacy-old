package am2.blocks;

import am2.container.ContainerEssenceRefiner;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;

public class CraftingEssenceExtractor implements IInventory{

	private ItemStack stackList[];
	private Container eventHandler;

	public CraftingEssenceExtractor(ContainerEssenceRefiner container){
		stackList = new ItemStack[getSizeInventory()];
		eventHandler = container;
	}

	@Override
	public int getSizeInventory(){
		return 5;
	}

	@Override
	public String getName(){
		return "Extracting";
	}

	@Override
	public boolean hasCustomName(){
		return false;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack){
		return false;
	}

	public ItemStack getStackInSlot(int i){
		if (i >= getSizeInventory()){
			return null;
		}else{
			return stackList[i];
		}
	}

	public ItemStack getStackInRowAndColumn(int i, int j){
		return null;
	}

	public ItemStack decrStackSize(int i, int j){
		if (stackList[i] != null){
			if (stackList[i].stackSize <= j){
				ItemStack itemstack = stackList[i];
				stackList[i] = null;
				eventHandler.onCraftMatrixChanged(this);
				return itemstack;
			}
			ItemStack itemstack1 = stackList[i].splitStack(j);
			if (stackList[i].stackSize == 0){
				stackList[i] = null;
			}
			eventHandler.onCraftMatrixChanged(this);
			return itemstack1;
		}else{
			return null;
		}
	}

	public void setInventorySlotContents(int i, ItemStack itemstack){
		stackList[i] = itemstack;
		eventHandler.onCraftMatrixChanged(this);
	}

	public int getInventoryStackLimit(){
		return 64;
	}

	public void onInventoryChanged(){
	}

	public boolean isUseableByPlayer(EntityPlayer entityplayer){
		return true;
	}

	@Override
	public ItemStack removeStackFromSlot(int i){
		if (stackList[i] != null){
			ItemStack itemstack = stackList[i];
			stackList[i] = null;
			return itemstack;
		}else{
			return null;
		}
	}

	@Override
	public void closeInventory(EntityPlayer entityplayer){
	}

	@Override
	public void markDirty(){
	}

	@Override
	public void openInventory(EntityPlayer entityplayer){
	}

	@Override
	public ITextComponent getDisplayName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getField(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setField(int id, int value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getFieldCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}
}
