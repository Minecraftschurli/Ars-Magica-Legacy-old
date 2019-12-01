package am2.items;

import am2.api.items.ItemFocus;
import am2.defs.ItemDefs;
import net.minecraft.item.ItemStack;

public class ItemFocusMana extends ItemFocus{

	public ItemFocusMana(){
		super();
	}

	@Override
	public Object[] getRecipeItems(){
		return new Object[]{
				"P", "F", "P",
				'P', new ItemStack(ItemDefs.itemOre, 1, ItemOre.META_VINTEUM),
				'F', ItemDefs.standardFocus
		};
	}

	@Override
	public String getInGameName(){
		return "Mana Focus";
	}
}
