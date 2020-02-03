package am2.armor.infusions;

import am2.api.items.armor.*;
import am2.buffs.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.world.*;

import java.util.*;

public class Healing extends ArmorImbuement{

	@Override
	public String getID(){
		return "healing";
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

		if (world.isRemote)
			return false;

		if (player.getHealth() < (player.getMaxHealth() * 0.25f)){
			player.addPotionEffect(new BuffEffectRegeneration(240, 2));
			return true;
		}
		return false;
	}

	@Override
	public EntityEquipmentSlot[] getValidSlots(){
		return new EntityEquipmentSlot[]{EntityEquipmentSlot.CHEST};
	}

	@Override
	public boolean canApplyOnCooldown(){
		return false;
	}

	@Override
	public int getCooldown(){
		return 6400;
	}

	@Override
	public int getArmorDamage(){
		return 30;
	}
}
