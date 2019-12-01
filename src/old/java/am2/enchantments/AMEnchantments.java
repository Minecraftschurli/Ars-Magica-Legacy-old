package am2.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantment.Rarity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class AMEnchantments{
	public static EnchantMagicResist magicResist = new EnchantMagicResist(Rarity.COMMON);
	public static EnchantmentSoulbound soulbound = new EnchantmentSoulbound(Rarity.RARE);

	public static void Init(){
		GameRegistry.register(magicResist, new ResourceLocation("arsmagica2", "magicResist"));
		GameRegistry.register(soulbound, new ResourceLocation("arsmagica2", "soulbound"));
	}

	public static int GetEnchantmentLevelSpecial(Enchantment ench, ItemStack stack){
		int baseEnchLvl = EnchantmentHelper.getEnchantmentLevel(ench, stack);
		/*if (enchID == imbuedArmor.effectId || enchID == imbuedBow.effectId || enchID == imbuedWeapon.effectId){
			if (baseEnchLvl > 3)
				return (baseEnchLvl & 0x6000) >> 13;
		}*/
		return baseEnchLvl;
	}
}
