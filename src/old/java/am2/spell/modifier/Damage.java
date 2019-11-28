package am2.spell.modifier;

import java.util.EnumSet;

import am2.api.affinity.Affinity;
import am2.api.spell.SpellModifier;
import am2.api.spell.SpellModifiers;
import am2.utils.AffinityShiftUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionUtils;
import net.minecraft.world.World;

public class Damage extends SpellModifier{
	@Override
	public EnumSet<SpellModifiers> getAspectsModified(){
		return EnumSet.of(SpellModifiers.DAMAGE);
	}

	@Override
	public float getModifier(SpellModifiers type, EntityLivingBase caster, Entity target, World world, NBTTagCompound metadata){
		return 2.2f;
	}

	@Override
	public Object[] getRecipe(){
		return new Object[]{
				AffinityShiftUtils.getEssenceForAffinity(Affinity.ENDER),
				Items.IRON_SWORD,
				PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypes.HARMING)
		};
	}

	@Override
	public float getManaCostMultiplier(ItemStack spellStack, int stage, int quantity){
		return 1.3f * quantity;
	}

	@Override
	public void encodeBasicData(NBTTagCompound tag, Object[] recipe) {}
}
