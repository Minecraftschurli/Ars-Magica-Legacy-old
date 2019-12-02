package am2.items;

import am2.api.items.ISpellFocus;
import am2.api.items.ItemFocus;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;

public class ItemFocusLesser extends ItemFocus implements ISpellFocus{

	public ItemFocusLesser(){
		super();
	}

	@Override
	public Object[] getRecipeItems(){
		return new Object[]{
				" G ", "GRG", " G ",
				'G', Items.GOLD_NUGGET,
				'R', Blocks.GLASS
		};
	}

	@Override
	public String getInGameName(){
		return "Lesser Focus";
	}

	@Override
	public int getFocusLevel(){
		return 0;
	}
}
