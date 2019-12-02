package am2.items;

import am2.buffs.BuffEffectManaRegen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemManaCake extends ItemFood{

	public ItemManaCake(){
		super(3, 0.6f, false);
	}

	public ItemManaCake registerAndName(String name) {
		this.setUnlocalizedName(new ResourceLocation("arsmagica2", name).toString());
		GameRegistry.register(this, new ResourceLocation("arsmagica2", name));
		return this;
	}
	
	@Override
	protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) {
		player.addPotionEffect(new BuffEffectManaRegen(600, 0));
		super.onFoodEaten(stack, worldIn, player);
	}

}
