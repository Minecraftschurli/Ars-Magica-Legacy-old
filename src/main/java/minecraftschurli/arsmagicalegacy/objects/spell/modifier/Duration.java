package minecraftschurli.arsmagicalegacy.objects.spell.modifier;

import minecraftschurli.arsmagicalegacy.api.spell.SpellModifier;
import minecraftschurli.arsmagicalegacy.api.spell.SpellModifiers;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ISpellIngredient;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ItemStackSpellIngredient;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ItemTagSpellIngredient;
import minecraftschurli.arsmagicalegacy.init.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.World;
import net.minecraftforge.common.Tags;

import java.util.EnumSet;

public final class Duration extends SpellModifier {
    @Override
    public EnumSet<SpellModifiers> getAspectsModified() {
        return EnumSet.of(SpellModifiers.DURATION);
    }

    @Override
    public float getManaCostMultiplier(ItemStack spellStack, int stage, int quantity) {
        return 1.25f * quantity;
    }

    @Override
    public float getModifier(SpellModifiers type, LivingEntity caster, Entity target, World world, CompoundNBT nbt) {
        return 2.2f;
    }

    @Override
    public ISpellIngredient[] getRecipe() {
        return new ISpellIngredient[]{
                new ItemTagSpellIngredient(Tags.Items.DUSTS_REDSTONE),
                new ItemStackSpellIngredient(new ItemStack(ModItems.AIR_ESSENCE.get()))
        };
    }
}
