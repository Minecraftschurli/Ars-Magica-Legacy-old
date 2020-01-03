package am2.api.enchantment;

import net.minecraft.enchantment.*;

public interface IAMEnchantmentHelper{
	/**
	 * Gets the current ID for soulbound enchantment
	 */
	public Enchantment getSoulbound();

	/**
	 * Gets the current ID for magic resist enchantment
	 */
	public Enchantment getMagicResist();
}
