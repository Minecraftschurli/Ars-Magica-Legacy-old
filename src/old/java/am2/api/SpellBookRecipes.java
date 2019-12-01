package am2.api;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class SpellBookRecipes extends ShapelessOreRecipe {

	public SpellBookRecipes(ItemStack result, Object[] recipe) {
		super(result, recipe);
	}
	
	@Override
	public ItemStack getCraftingResult(InventoryCrafting var1) {
		NBTTagCompound tag = new NBTTagCompound();
		for (int i = 0; i < var1.getSizeInventory(); i++) {
			if (var1.getStackInSlot(i) != null && var1.getStackInSlot(i).getItem().equals(null)) {
				tag = var1.getStackInSlot(i).getTagCompound();
			}
		}
		ItemStack craftResult = super.getCraftingResult(var1);
		craftResult.setTagCompound(tag);
		return craftResult;
	}
}
