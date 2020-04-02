package minecraftschurli.arsmagicalegacy.objects.spell.modifier;

import minecraftschurli.arsmagicalegacy.api.spell.SpellModifier;
import minecraftschurli.arsmagicalegacy.api.spell.SpellModifiers;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ISpellIngredient;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ItemTagSpellIngredient;
import minecraftschurli.arsmagicalegacy.init.ModTags;
import minecraftschurli.arsmagicalegacy.util.NBTUtil;
import minecraftschurli.arsmagicalegacy.util.SpellUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.DyeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.World;
import net.minecraftforge.common.Tags;

import java.util.EnumSet;

public final class Color extends SpellModifier {
    @Override
    public EnumSet<SpellModifiers> getAspectsModified() {
        return EnumSet.of(SpellModifiers.COLOR);
    }

    @Override
    public void encodeBasicData(CompoundNBT tag, ISpellIngredient[] recipe) {
        for (Object o : recipe)
            if (o instanceof ItemStack && Tags.Items.DYES.contains(((ItemStack) o).getItem()))
                NBTUtil.addTag(tag, SpellUtil.SPELL_DATA).putInt("Color", ((DyeItem) ((ItemStack) o).getItem()).getDyeColor().ordinal());
    }

    @Override
    public float getManaCostMultiplier(ItemStack spellStack, int stage, int quantity) {
        return 1F;
    }

    @Override
    public float getModifier(SpellModifiers type, LivingEntity caster, Entity target, World world, CompoundNBT nbt) {
        if (type == SpellModifiers.COLOR) return NBTUtil.addTag(nbt, SpellUtil.SPELL_DATA).getInt("Color");
        return 0;
    }

    @Override
    public ISpellIngredient[] getRecipe() {
        return new ISpellIngredient[]{
                new ItemTagSpellIngredient(Tags.Items.DYES),
                new ItemTagSpellIngredient(ModTags.Items.GEMS_CHIMERITE)
        };
    }
}
