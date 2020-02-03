package am2.armor.infusions;

import am2.api.extensions.*;
import am2.api.items.armor.*;
import am2.buffs.*;
import am2.defs.*;
import am2.extensions.*;
import am2.utils.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.world.*;

import java.util.*;

public class FallProtection extends ArmorImbuement{

	@Override
	public String getID(){
		return "fallprot";
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

		int distanceToGround = MathUtilities.getDistanceToGround(player, world);
		IEntityExtension extendedProperties = EntityExtension.For(player);
		if (player.fallDistance >= extendedProperties.getFallProtection() + 4f && distanceToGround < -8 * player.motionY){
			if (!player.isPotionActive(PotionEffectsDefs.slowfall) && !player.capabilities.isFlying){

				BuffEffectSlowfall sf = new BuffEffectSlowfall(distanceToGround * 3, 1);
				player.addPotionEffect(sf);

				stack.damageItem((int)(player.fallDistance * 6), player);

				player.fallDistance = 0;
				return true;
			}
		}
		return false;
	}

	@Override
	public EntityEquipmentSlot[] getValidSlots(){
		return new EntityEquipmentSlot[]{EntityEquipmentSlot.FEET};
	}

	@Override
	public boolean canApplyOnCooldown(){
		return false;
	}

	@Override
	public int getCooldown(){
		return 900;
	}

	@Override
	public int getArmorDamage(){
		return 0;
	}
}
