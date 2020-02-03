package am2.api.items.armor;

import am2.extensions.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.living.LivingEvent.*;
import net.minecraftforge.event.entity.player.PlayerEvent.*;
import net.minecraftforge.fml.common.registry.*;

import java.util.*;

public abstract class ArmorImbuement extends IForgeRegistryEntry.Impl<ArmorImbuement>{
	
	public abstract String getID();
	
	/**
	 * Gets the tier for this infusion
	 */
	public abstract ImbuementTiers getTier();

	/**
	 * Gets all situations under which this infusion applies
	 */
	public abstract EnumSet<ImbuementApplicationTypes> getApplicationTypes();

	/**
	 * Applies the effect.  This will be called when any of the application types is matched in an event.
	 *
	 * @param player      The player wearing the armor
	 * @param world       The world the player is in
	 * @param stack       The itemstack that was matched
	 * @param matchedType The application type that was matched.
	 * @param params      Depends on the application type.
	 *                    <br/>
	 *                    In the case of ON_TICK, it will be a 1-length array with the first element being the {@link LivingEvent} event <br/>
	 *                    In the case of ON_JUMP, it will be a 1-length array with the first element being the {@link LivingJumpEvent} event<br/>
	 *                    In the case of ON_HIT, it will be a 1-length array with the first element being the {@link LivingHurtEvent} event<br/>
	 *                    In the case of ON_MINING_SPEED, it will be a 1-length array with the first element being the {@link BreakSpeed} event <br/>
	 */
	public abstract boolean applyEffect(EntityPlayer player, World world, ItemStack stack, ImbuementApplicationTypes matchedType, Object... params);
	
	public boolean canApply(EntityPlayer player) {
		if (canApplyOnCooldown())
			return true;
		if (AffinityData.For(player).getCooldown(this.getRegistryName().toString()) == 0) return true;
		return false;
	}
	
	/**
	 * Gets all armor slots that this effect can be applied to
	 */
	public abstract EntityEquipmentSlot[] getValidSlots();

	/**
	 * If the slot is on cooldown, can the effect still apply?
	 */
	public abstract boolean canApplyOnCooldown();

	/**
	 * Get the amount of cooldown to add to the slot once the effect applies
	 */
	public abstract int getCooldown();

	/**
	 * How much does the infusion damage the armor?
	 */
	public abstract int getArmorDamage();
	
	/**
	 * Can the player apply the imbuement on current armor?
	 */
	public boolean canApplyToArmor(ItemStack stack, EntityPlayer player) {
		return true;
	}
}
