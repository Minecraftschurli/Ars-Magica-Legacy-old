package am2.items;

import am2.api.items.ISpellFocus;
import am2.api.items.ItemFocus;
import am2.defs.ItemDefs;
import net.minecraft.init.Items;

public class ItemFocusStandard extends ItemFocus implements ISpellFocus{

	public ItemFocusStandard(){
		super();
	}

	@Override
	public Object[] getRecipeItems(){
		return new Object[]{
				" R ", "RFR", " R ",
				'R', Items.REDSTONE,
				'F', ItemDefs.lesserFocus
		};
	}

	@Override
	public String getInGameName(){
		return "Focus";
	}

	@Override
	public int getFocusLevel(){
		return 1;
	}
}
