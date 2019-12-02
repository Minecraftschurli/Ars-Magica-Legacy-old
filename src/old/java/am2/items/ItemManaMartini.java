package am2.items;

import java.util.List;

import am2.defs.PotionEffectsDefs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fml.common.registry.GameRegistry;


@SuppressWarnings("deprecation")
public class ItemManaMartini extends ItemFood{
	public ItemManaMartini(){
		super(0, 0, false);
		this.setPotionEffect(new PotionEffect(PotionEffectsDefs.burnoutReduction, 300, 0), 1.0f);
	}

	public Item registerAndName(String name) {
		this.setUnlocalizedName(new ResourceLocation("arsmagica2", name).toString());
		GameRegistry.register(this, new ResourceLocation("arsmagica2", name));
		return this;
	}

	public EnumAction getItemUseAction(ItemStack p_77661_1_){
		return EnumAction.DRINK;
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List<String> lines, boolean iHaveNoIdea){
		super.addInformation(stack, player, lines, iHaveNoIdea);
		lines.add(I18n.translateToLocal("am2.tooltip.shaken"));
	}
}
