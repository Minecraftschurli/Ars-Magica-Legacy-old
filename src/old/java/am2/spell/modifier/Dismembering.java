package am2.spell.modifier;

import java.util.EnumSet;

import am2.api.spell.SpellModifier;
import am2.api.spell.SpellModifiers;
import am2.defs.ItemDefs;
import am2.items.ItemCore;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class Dismembering extends SpellModifier{
	@Override
	public EnumSet<SpellModifiers> getAspectsModified(){
		return EnumSet.of(SpellModifiers.DISMEMBERING_LEVEL);
	}

	@Override
	public float getModifier(SpellModifiers type, EntityLivingBase caster, Entity target, World world, NBTTagCompound metadata){
		return 0.05f;
	}

	@Override
	public Object[] getRecipe(){
		return new Object[]{
				new ItemStack(ItemDefs.core, 1, ItemCore.META_BASE_CORE),
				new ItemStack(Items.SKULL, 1, 1),
				Items.BONE
		};
	}

	@Override
	public float getManaCostMultiplier(ItemStack spellStack, int stage, int quantity){
		return 1.25f * quantity;
	}

	@Override
	public void encodeBasicData(NBTTagCompound tag, Object[] recipe) {}
}
