package am2.api.items;

import am2.defs.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraftforge.fml.common.registry.*;

public abstract class ItemFocus extends Item{
	
	public ItemFocus() {
		setCreativeTab(CreativeTabsDefs.tabAM2Items);
		setMaxDamage(0);
		setHasSubtypes(true);
	}
	
	public abstract Object[] getRecipeItems();

	public abstract String getInGameName();
	
	public ItemFocus registerAndName(String name) {
		this.setUnlocalizedName(new ResourceLocation("arsmagica2", name).toString());
		GameRegistry.register(this, new ResourceLocation("arsmagica2", name));
		return this;
	}
}
