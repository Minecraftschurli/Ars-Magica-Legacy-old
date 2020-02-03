package am2.armor;

import am2.defs.*;
import am2.proxy.gui.*;
import net.minecraft.client.model.*;
import net.minecraft.creativetab.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.potion.*;
import net.minecraft.util.*;
import net.minecraft.util.text.translation.*;
import net.minecraft.world.*;
import net.minecraftforge.fml.relauncher.*;

import java.util.*;

@SuppressWarnings("deprecation")
public class ItemFireGuardianEars extends AMArmor{

	public ItemFireGuardianEars(ArmorMaterial inheritFrom, ArsMagicaArmorMaterial enumarmormaterial, int par3, EntityEquipmentSlot par4){
		super(inheritFrom, enumarmormaterial, par3, par4);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped _default) {
		return ModelLibrary.instance.fireEars;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot){
		return 0;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List<String> par3List, boolean par4){
		par3List.add(I18n.translateToLocal("am2.tooltip.fire_ears"));
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type){
		return "arsmagica2:textures/mobs/bosses/fire_guardian.png";
	}

	@Override
	public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List<ItemStack> par3List){
		par3List.add(ItemDefs.fireEarsEnchanted.copy());
	}

	@Override
	public void onArmorTick(World world, EntityPlayer plr, ItemStack stack) {
		super.onArmorTick(world, plr, stack);
		if (!plr.getActivePotionEffects().contains(PotionTypes.FIRE_RESISTANCE))
			plr.addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation(new ResourceLocation("fire_resistance").toString())));
	}
}
