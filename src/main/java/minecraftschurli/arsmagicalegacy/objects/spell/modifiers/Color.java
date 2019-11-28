package minecraftschurli.arsmagicalegacy.objects.spell.modifiers;

import minecraftschurli.arsmagicalegacy.api.spellsystem.*;
import minecraftschurli.arsmagicalegacy.init.ModItems;
import minecraftschurli.arsmagicalegacy.util.NBTUtils;
import minecraftschurli.arsmagicalegacy.util.SpellUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.DyeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.World;
import net.minecraftforge.common.Tags;

import java.util.EnumSet;

public class Color extends SpellModifier {
	
	@Override
	public ISpellIngredient[] getRecipe() {
		return new ISpellIngredient[]{
				new ItemStackSpellIngredient(new ItemStack(ModItems.CHIMERITE.get())),
				new ItemTagSpellIngredient(Tags.Items.DYES, 1)
		};
	}

	@Override
	public void encodeBasicData(CompoundNBT tag, ISpellIngredient[] recipe) {
		for (Object obj : recipe) {
			if (obj instanceof ItemStack) {
				ItemStack is = (ItemStack) obj;
				if (Tags.Items.DYES.contains(is.getItem())) {
					NBTUtils.addTag(tag, SpellUtils.SPELL_DATA).putInt("Color", ((DyeItem)is.getItem()).getDyeColor().ordinal());
				}
			}
		}
	}
	@Override
	public EnumSet<SpellModifiers> getAspectsModified() {
		return EnumSet.of(SpellModifiers.COLOR);
	}

	@Override
	public float getModifier(SpellModifiers type, LivingEntity caster, Entity target, World world, CompoundNBT nbt) {
		if (type == SpellModifiers.COLOR) {
			return NBTUtils.addTag(nbt, SpellUtils.SPELL_DATA).getInt("Color");
		}
		return 0;
	}

	@Override
	public float getManaCostMultiplier(ItemStack spellStack, int stage, int quantity) {
		return 1F;
	}

}
