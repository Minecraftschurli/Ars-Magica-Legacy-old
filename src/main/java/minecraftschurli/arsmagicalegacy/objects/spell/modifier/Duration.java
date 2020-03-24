package minecraftschurli.arsmagicalegacy.objects.spell.modifier;

import java.util.EnumSet;
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

public class Duration extends SpellModifier {
    @Override
    public EnumSet<SpellModifiers> getAspectsModified() {
        return EnumSet.of(SpellModifiers.DURATION);
    }

    @Override
    public float getModifier(SpellModifiers type, LivingEntity caster, Entity target, World world, CompoundNBT nbt) {
        return 2.2f;
    }

    @Override
    public float getManaCostMultiplier(ItemStack spellStack, int stage, int quantity) {
        return 1.25f * quantity;
    }

    @Override
    public ISpellIngredient[] getRecipe() {
        return new ISpellIngredient[]{
                new ItemStackSpellIngredient(new ItemStack(ModItems.AIR_ESSENCE.get())),
                new ItemTagSpellIngredient(Tags.Items.DUSTS_REDSTONE)
        };
    }

    @Override
    public void encodeBasicData(CompoundNBT tag, ISpellIngredient[] recipe) {
    }
}
