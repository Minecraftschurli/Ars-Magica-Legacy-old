package am2.armor.infusions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Iterator;

import am2.api.items.armor.ArmorImbuement;
import am2.api.items.armor.ImbuementApplicationTypes;
import am2.api.items.armor.ImbuementTiers;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class Freedom extends ArmorImbuement{

	@Override
	public String getID(){
		return "freedom";
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
		ModifiableAttributeInstance instance = (ModifiableAttributeInstance)player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.MOVEMENT_SPEED);

		ArrayList<AttributeModifier> toRemove = new ArrayList<AttributeModifier>();

		Collection<AttributeModifier> c = instance.getModifiers();
		ArrayList<AttributeModifier> arraylist = new ArrayList<>(c);
		Iterator<AttributeModifier> iterator = arraylist.iterator();

		while (iterator.hasNext()){
			AttributeModifier attributemodifier = iterator.next();
			if (attributemodifier.getOperation() == 2 && attributemodifier.getAmount() < 0.0f){
				toRemove.add(attributemodifier);
			}
		}

		for (AttributeModifier modifier : toRemove){
			instance.removeModifier(modifier);
		}

		return toRemove.size() > 0;
	}

	@Override
	public EntityEquipmentSlot[] getValidSlots(){
		return new EntityEquipmentSlot[]{EntityEquipmentSlot.FEET};
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
