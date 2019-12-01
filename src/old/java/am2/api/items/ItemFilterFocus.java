package am2.api.items;

import net.minecraft.entity.Entity;

public abstract class ItemFilterFocus extends ItemFocus{

	public ItemFilterFocus(){
		super();
	}

	public abstract Class<? extends Entity> getFilterClass();
}
