package am2.armor.infusions;

import am2.api.items.armor.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraftforge.event.entity.living.*;

import java.util.*;

public class Recoil extends ArmorImbuement{

	@Override
	public String getID(){
		return "recoil";
	}

	@Override
	public ImbuementTiers getTier(){
		return ImbuementTiers.FOURTH;
	}

	@Override
	public EnumSet<ImbuementApplicationTypes> getApplicationTypes(){
		return EnumSet.of(ImbuementApplicationTypes.ON_HIT);
	}

	@Override
	public boolean applyEffect(EntityPlayer player, World world, ItemStack stack, ImbuementApplicationTypes matchedType, Object... params){
		LivingHurtEvent event = (LivingHurtEvent)params[0];
		Entity e = event.getSource().getSourceOfDamage();
		if (e != null && e instanceof EntityLivingBase){
			((EntityLivingBase)e).knockBack(player, 10, player.posX - e.posX, player.posZ - e.posZ);
		}
		return true;
	}

	@Override
	public EntityEquipmentSlot[] getValidSlots(){
		return new EntityEquipmentSlot[]{EntityEquipmentSlot.CHEST};
	}

	@Override
	public boolean canApplyOnCooldown(){
		return true;
	}

	@Override
	public int getCooldown(){
		return 0;
	}

	@Override
	public int getArmorDamage(){
		return 2;
	}
}
