package am2.container.slot;

import am2.defs.ItemDefs;
import am2.items.ItemRune;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class SlotGhostRune extends SlotGhostItem{

	public SlotGhostRune(IInventory par1iInventory, int par2, int par3, int par4){
		super(par1iInventory, par2, par3, par4);
	}

	@Override
	public boolean isItemValid(ItemStack par1ItemStack){
		return par1ItemStack != null && par1ItemStack.getItem() == ItemDefs.rune && ((ItemRune)ItemDefs.rune).getKeyIndex(par1ItemStack) > 0;
	}

}
