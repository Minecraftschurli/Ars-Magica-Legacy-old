package minecraftschurli.arsmagicalegacy.objects.spell.modifier;

import minecraftschurli.arsmagicalegacy.api.spell.SpellModifier;
import minecraftschurli.arsmagicalegacy.api.spell.SpellModifiers;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ISpellIngredient;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ItemStackSpellIngredient;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ItemTagSpellIngredient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.World;
import net.minecraftforge.common.Tags;

import java.util.EnumSet;

public final class BuffPower extends SpellModifier {
    @Override
    public EnumSet<SpellModifiers> getAspectsModified() {
        return EnumSet.of(SpellModifiers.BUFF_POWER);
    }

    @Override
    public float getManaCostMultiplier(ItemStack spellStack, int stage, int quantity) {
        return 1.25f * quantity;
    }

    @Override
    public float getModifier(SpellModifiers type, LivingEntity caster, Entity target, World world, CompoundNBT nbt) {
        return 1;
    }

    @Override
    public ISpellIngredient[] getRecipe() {
        return new ISpellIngredient[]{
                new ItemTagSpellIngredient(Tags.Items.DUSTS_GLOWSTONE),
                new ItemTagSpellIngredient(Tags.Items.DUSTS_REDSTONE),
                new ItemTagSpellIngredient(Tags.Items.GEMS_EMERALD),
                new ItemTagSpellIngredient(Tags.Items.GUNPOWDER),
                new ItemStackSpellIngredient(new ItemStack(Items.GHAST_TEAR)),
                new ItemStackSpellIngredient(new ItemStack(Items.GLASS_BOTTLE)),
                new ItemStackSpellIngredient(new ItemStack(Items.NETHER_WART)),
                new ItemStackSpellIngredient(new ItemStack(Items.SPIDER_EYE))
        };
    }
}
