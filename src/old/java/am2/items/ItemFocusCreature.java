package am2.items;

import am2.api.items.ItemFilterFocus;
import am2.defs.ItemDefs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;

public class ItemFocusCreature extends ItemFilterFocus{

	public ItemFocusCreature(){
		super();
	}

	@Override
	public Object[] getRecipeItems(){
		return new Object[]{
				" P ",
				"LFT",
				" W ",
				Character.valueOf('P'), Items.PORKCHOP,
				Character.valueOf('B'), Items.LEATHER,
				Character.valueOf('F'), ItemDefs.standardFocus,
				Character.valueOf('T'), Items.FEATHER,
				Character.valueOf('W'), Blocks.WOOL,
		};
	}

	@Override
	public String getInGameName(){
		return "Creature Focus";
	}

	@Override
	public Class<? extends Entity> getFilterClass(){
		return EntityCreature.class;
	}

}
