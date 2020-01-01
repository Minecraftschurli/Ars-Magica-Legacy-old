package am2.armor.infusions;

import am2.api.items.armor.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraftforge.event.entity.player.PlayerEvent.*;

import java.util.*;

public class MiningSpeed extends ArmorImbuement{

	@Override
	public String getID(){
		return "minespd";
	}

	@Override
	public ImbuementTiers getTier(){
		return ImbuementTiers.FOURTH;
	}

	@Override
	public EnumSet<ImbuementApplicationTypes> getApplicationTypes(){
		return EnumSet.of(ImbuementApplicationTypes.ON_MINING_SPEED);
	}

	@Override
	public boolean applyEffect(EntityPlayer player, World world, ItemStack stack, ImbuementApplicationTypes matchedType, Object... params){
		BreakSpeed event = (BreakSpeed)params[0];
		event.setNewSpeed(event.getOriginalSpeed() * 1.5f);
		return true;
	}

	@Override
	public EntityEquipmentSlot[] getValidSlots(){
		return new EntityEquipmentSlot[]{EntityEquipmentSlot.HEAD};
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
		return 1;
	}
}
