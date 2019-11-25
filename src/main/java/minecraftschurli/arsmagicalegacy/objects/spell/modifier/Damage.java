package minecraftschurli.arsmagicalegacy.objects.spell.modifier;

import minecraftschurli.arsmagicalegacy.api.spellsystem.ISpellIngredient;
import minecraftschurli.arsmagicalegacy.api.spellsystem.ItemStackSpellIngredient;
import minecraftschurli.arsmagicalegacy.api.spellsystem.ItemTagSpellIngredient;
import minecraftschurli.arsmagicalegacy.api.spellsystem.SpellModifier;
import minecraftschurli.arsmagicalegacy.init.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.World;
import net.minecraftforge.common.Tags;

import java.util.EnumSet;

public class Damage extends SpellModifier {
    @Override
    public EnumSet<SpellModifier.Type> getAspectsModified() {
        return EnumSet.of(Type.DAMAGE);
    }

    @Override
    public float getManaCostMultiplier(ItemStack spellStack, int stage, int quantity) {
        return 1.3f * quantity;
    }

    @Override
    public float getModifier(SpellModifier.Type type, LivingEntity caster, Entity target, World world, byte[] metadata) {
        return 2.2f;
    }

    @Override
    public ISpellIngredient[] getRecipeItems() {
        return new ISpellIngredient[]{
                new ItemStackSpellIngredient(new ItemStack(ModItems.ENDER_ESSENCE.get())),
                new ItemStackSpellIngredient(new ItemStack(Items.IRON_SWORD)),
                new ItemStackSpellIngredient(new ItemStack(Items.POTION))
        };
    }
}
