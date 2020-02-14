package minecraftschurli.arsmagicalegacy.objects.spell.modifier;

import minecraftschurli.arsmagicalegacy.api.spell.*;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.*;
import minecraftschurli.arsmagicalegacy.init.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.world.*;

import java.util.*;

public class TargetNonSolidBlocks extends SpellModifier {
    @Override
    public EnumSet<SpellModifiers> getAspectsModified() {
        return EnumSet.of(SpellModifiers.TARGET_NONSOLID_BLOCKS);
    }

    @Override
    public float getModifier(SpellModifiers type, LivingEntity caster, Entity target, World world, CompoundNBT nbt) {
        return 1;
    }

    @Override
    public float getManaCostMultiplier(ItemStack spellStack, int stage, int quantity) {
        return 1;
    }

    @Override
    public ISpellIngredient[] getRecipe() {
        return new ISpellIngredient[]{
                new ItemStackSpellIngredient(new ItemStack(ModItems.WATER_ESSENCE.get())),
                new ItemStackSpellIngredient(new ItemStack(ModItems.CERUBLOSSOM.get())),
                new ItemStackSpellIngredient(new ItemStack(Items.BROWN_MUSHROOM)),
                new ItemStackSpellIngredient(new ItemStack(Items.POPPY)),
                new ItemStackSpellIngredient(new ItemStack(Items.POTION))
        };
    }

    @Override
    public void encodeBasicData(CompoundNBT tag, ISpellIngredient[] recipe) {

    }
}
