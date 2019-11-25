package minecraftschurli.arsmagicalegacy.objects.spell.modifier;

import minecraftschurli.arsmagicalegacy.api.spellsystem.ISpellIngredient;
import minecraftschurli.arsmagicalegacy.api.spellsystem.ItemStackSpellIngredient;
import minecraftschurli.arsmagicalegacy.api.spellsystem.SpellModifier;
import minecraftschurli.arsmagicalegacy.init.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;

import java.util.EnumSet;

public class Radius extends SpellModifier {
    @Override
    public ISpellIngredient[] getRecipeItems() {
        return new ISpellIngredient[]{
                new ItemStackSpellIngredient(new ItemStack(ModItems.FIRE_ESSENCE.get())),
                new ItemStackSpellIngredient(new ItemStack(Items.GLOWSTONE_DUST)),
                new ItemStackSpellIngredient(new ItemStack(Items.TNT))
        };
    }

    @Override
    public float getModifier(Type type, LivingEntity caster, Entity target, World world, byte[] metadata) {
        return 4;
    }

    @Override
    public float getManaCostMultiplier(ItemStack spellStack, int stage, int quantity) {
        return 1.2f * quantity;
    }

    @Override
    public EnumSet<Type> getAspectsModified() {
        return EnumSet.of(Type.RANGE);
    }
}
