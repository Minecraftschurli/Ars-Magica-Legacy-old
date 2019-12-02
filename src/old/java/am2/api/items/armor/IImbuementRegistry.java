package am2.api.items.armor;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;


public interface IImbuementRegistry{
	/**
	 * Registers a new imbuement instance into the system
	 */
	void registerImbuement(ArmorImbuement imbuementInstance);

	/**
	 * Locates the specified imbuement registered to the passed-in ID.
	 * Returns null if not found.
	 */
	ArmorImbuement getImbuementByID(ResourceLocation ID);

	/**
	 * Returns all imbuements registered into the specified tier for the given armor type (slot).
	 */
	ArmorImbuement[] getImbuementsForTier(ImbuementTiers tier, EntityEquipmentSlot armorType);

	/**
	 * Is the given imbuement instance present on the passed-in item stack?
	 */
	boolean isImbuementPresent(ItemStack stack, ArmorImbuement imbuement);

	/**
	 * Is the given imbuement ID present on the passed-in item stack?
	 */
	boolean isImbuementPresent(ItemStack stack, String id);
}
