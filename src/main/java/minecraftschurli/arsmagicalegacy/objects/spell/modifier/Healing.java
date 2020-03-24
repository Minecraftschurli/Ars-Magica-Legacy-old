package minecraftschurli.arsmagicalegacy.objects.spell.modifier;

import java.util.EnumSet;
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

public class Healing extends SpellModifier {
    @Override
    public EnumSet<SpellModifiers> getAspectsModified() {
        return EnumSet.of(SpellModifiers.HEALING);
    }

    @Override
    public float getModifier(SpellModifiers type, LivingEntity caster, Entity target, World world, CompoundNBT nbt) {
        return 2;
    }

    @Override
    public float getManaCostMultiplier(ItemStack spellStack, int stage, int quantity) {
        return quantity;
    }

    @Override
    public ISpellIngredient[] getRecipe() {
        return new ISpellIngredient[]{
                new ItemStackSpellIngredient(new ItemStack(ModItems.LIFE_ESSENCE.get())),
                new ItemStackSpellIngredient(new ItemStack(Items.EGG)),
                new ItemStackSpellIngredient(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), Potions.HEALING))
        };
    }

    @Override
    public void encodeBasicData(CompoundNBT tag, ISpellIngredient[] recipe) {
    }
}
