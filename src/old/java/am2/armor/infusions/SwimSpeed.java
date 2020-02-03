package am2.armor.infusions;

import am2.api.items.armor.*;
import am2.buffs.*;
import am2.defs.*;
import net.minecraft.block.material.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.world.*;

import java.util.*;

public class SwimSpeed extends ArmorImbuement{

	@Override
	public String getID(){
		return "swimspd";
	}
	
	@Override
	public ImbuementTiers getTier(){
		return ImbuementTiers.FOURTH;
	}

	@Override
	public EnumSet<ImbuementApplicationTypes> getApplicationTypes(){
		return EnumSet.of(ImbuementApplicationTypes.ON_TICK);
	}

	@Override
	public boolean applyEffect(EntityPlayer player, World world, ItemStack stack, ImbuementApplicationTypes matchedType, Object... params){
		if (world.isRemote)
			return false;

		if (player.isInsideOfMaterial(Material.WATER) && !player.isPotionActive(PotionEffectsDefs.swiftSwim)){
			player.addPotionEffect(new BuffEffectSwiftSwim(10, 1));
			return true;
		}
		return false;
	}

	@Override
	public EntityEquipmentSlot[] getValidSlots(){
		return new EntityEquipmentSlot[]{EntityEquipmentSlot.LEGS};
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
		return 0;
	}
}
