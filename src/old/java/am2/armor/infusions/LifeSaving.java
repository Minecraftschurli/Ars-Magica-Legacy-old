package am2.armor.infusions;

import am2.api.items.armor.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraftforge.event.entity.living.*;

import java.util.*;

public class LifeSaving extends ArmorImbuement{

	@Override
	public String getID(){
		return "lifesave";
	}

	@Override
	public ImbuementTiers getTier(){
		return ImbuementTiers.FOURTH;
	}

	@Override
	public EnumSet<ImbuementApplicationTypes> getApplicationTypes(){
		return EnumSet.of(ImbuementApplicationTypes.ON_DEATH);
	}

	@Override
	public boolean applyEffect(EntityPlayer player, World world, ItemStack stack, ImbuementApplicationTypes matchedType, Object... params){
		LivingDeathEvent event = (LivingDeathEvent)params[0];
		event.setCanceled(true);
		player.setHealth(10);
		player.isDead = false;
		return true;
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
		return 8000;
	}

	@Override
	public int getArmorDamage(){
		return 75;
	}
}
