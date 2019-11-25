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

public class Velocity extends SpellModifier {
    @Override
    public ISpellIngredient[] getRecipeItems() {
        return new ISpellIngredient[]{
                new ItemStackSpellIngredient(new ItemStack(ModItems.ICE_ESSENCE.get())),
                new ItemStackSpellIngredient(new ItemStack(Items.FEATHER)),
                new ItemStackSpellIngredient(new ItemStack(Items.MINECART)),
                new ItemStackSpellIngredient(new ItemStack(Items.OAK_BOAT))
        };
    }

    @Override
    public float getModifier(SpellModifier.Type type, LivingEntity caster, Entity target, World world, byte[] metadata) {
        return 2.6f;
    }

    @Override
    public float getManaCostMultiplier(ItemStack spellStack, int stage, int quantity) {
        return 1.15f * quantity;
    }

    @Override
    public EnumSet<Type> getAspectsModified() {
        return EnumSet.of(SpellModifier.Type.SPEED);
    }
}
