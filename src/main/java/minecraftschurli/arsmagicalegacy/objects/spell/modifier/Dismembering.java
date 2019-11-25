package minecraftschurli.arsmagicalegacy.objects.spell.modifier;

import minecraftschurli.arsmagicalegacy.api.spellsystem.ISpellIngredient;
import minecraftschurli.arsmagicalegacy.api.spellsystem.ItemStackSpellIngredient;
import minecraftschurli.arsmagicalegacy.api.spellsystem.SpellModifier;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;

import java.util.EnumSet;

public class Dismembering extends SpellModifier {
    @Override
    public ISpellIngredient[] getRecipeItems() {
        return new ISpellIngredient[]{
                new ItemStackSpellIngredient(new ItemStack(Items.WITHER_SKELETON_SKULL)),
                new ItemStackSpellIngredient(new ItemStack(Items.BONE))
        };
    }

    @Override
    public float getModifier(Type type, LivingEntity caster, Entity target, World world, byte[] metadata) {
        return 0.05f;
    }

    @Override
    public float getManaCostMultiplier(ItemStack spellStack, int stage, int quantity) {
        return 1.25f * quantity;
    }

    @Override
    public EnumSet<Type> getAspectsModified() {
        return EnumSet.of(Type.DISMEMBERING_LEVEL);
    }
}
