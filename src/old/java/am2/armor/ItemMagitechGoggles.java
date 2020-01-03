package am2.armor;

import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;

public class ItemMagitechGoggles extends AMArmor{

	public ItemMagitechGoggles(ArmorMaterial inheritFrom, int renderIndex){
		super(inheritFrom, ArsMagicaArmorMaterial.UNIQUE, renderIndex, EntityEquipmentSlot.HEAD);
	}
	@Override
	public int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot){
		return 2;
	}

	@Override
	public int GetDamageReduction(){
		return 2;
	}
}
