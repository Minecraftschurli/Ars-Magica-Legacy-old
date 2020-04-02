package minecraftschurli.arsmagicalegacy.objects.spell.modifier;

import minecraftschurli.arsmagicalegacy.api.spell.SpellModifier;
import minecraftschurli.arsmagicalegacy.api.spell.SpellModifiers;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ISpellIngredient;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ItemStackSpellIngredient;
import minecraftschurli.arsmagicalegacy.init.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;
import net.minecraft.world.World;

import java.util.EnumSet;

public final class TargetNonSolidBlocks extends SpellModifier {
    @Override
    public EnumSet<SpellModifiers> getAspectsModified() {
        return EnumSet.of(SpellModifiers.TARGET_NONSOLID_BLOCKS);
    }

    @Override
    public float getManaCostMultiplier(ItemStack spellStack, int stage, int quantity) {
        return 1;
    }

    @Override
    public float getModifier(SpellModifiers type, LivingEntity caster, Entity target, World world, CompoundNBT nbt) {
        return 1;
    }

    @Override
    public ISpellIngredient[] getRecipe() {
        return new ISpellIngredient[]{
                new ItemStackSpellIngredient(new ItemStack(Items.BROWN_MUSHROOM)),
                new ItemStackSpellIngredient(new ItemStack(Items.POPPY)),
                new ItemStackSpellIngredient(new ItemStack(ModItems.CERUBLOSSOM.get())),
                new ItemStackSpellIngredient(new ItemStack(ModItems.WATER_ESSENCE.get())),
                new ItemStackSpellIngredient(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), Potions.WATER))
        };
    }
}
