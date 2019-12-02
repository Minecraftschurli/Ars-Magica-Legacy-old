package am2.items;

import am2.api.items.ItemFocus;
import am2.defs.ItemDefs;
import net.minecraft.init.Blocks;

public class ItemFocusCharge extends ItemFocus{

	public ItemFocusCharge(){
		super();
	}

	@Override
	public Object[] getRecipeItems(){
		return new Object[]{
				"CFC",
				'F', ItemDefs.standardFocus,
				'C', Blocks.GLASS
		};
	}

	@Override
	public String getInGameName(){
		return "Charge Focus";
	}
}
