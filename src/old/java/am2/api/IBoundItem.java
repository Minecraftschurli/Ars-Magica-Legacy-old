package am2.api;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface IBoundItem {
	
	public float maintainCost(EntityPlayer player, ItemStack stack);
	
	static final float diminishedMaintain = 0.1f;
	static final float normalMaintain = 0.4f;
	static final float augmentedMaintain = 1.0f;
}
