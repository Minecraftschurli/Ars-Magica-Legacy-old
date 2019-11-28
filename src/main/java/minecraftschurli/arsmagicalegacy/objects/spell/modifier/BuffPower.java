package minecraftschurli.arsmagicalegacy.objects.spell.modifier;

import minecraftschurli.arsmagicalegacy.api.spellsystem.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.world.*;

import java.util.*;

public class BuffPower extends SpellModifier {
    @Override
    public EnumSet<SpellModifiers> getAspectsModified() {
        return EnumSet.of(SpellModifiers.BUFF_POWER);
    }

    @Override
    public float getModifier(SpellModifiers type, LivingEntity caster, Entity target, World world, CompoundNBT nbt) {
        return 1;
    }

    @Override
    public float getManaCostMultiplier(ItemStack spellStack, int stage, int quantity) {
        return 1.25f * quantity;
    }

    @Override
    public ISpellIngredient[] getRecipe() {
        return new ISpellIngredient[]{
                new ItemStackSpellIngredient(new ItemStack(Items.EMERALD)),
                new ItemStackSpellIngredient(new ItemStack(Items.GHAST_TEAR)),
                new ItemStackSpellIngredient(new ItemStack(Items.GLASS_BOTTLE)),
                new ItemStackSpellIngredient(new ItemStack(Items.GLOWSTONE_DUST)),
                new ItemStackSpellIngredient(new ItemStack(Items.GUNPOWDER)),
                new ItemStackSpellIngredient(new ItemStack(Items.NETHER_WART)),
                new ItemStackSpellIngredient(new ItemStack(Items.REDSTONE)),
                new ItemStackSpellIngredient(new ItemStack(Items.SPIDER_EYE))
        };
    }

    @Override
    public void encodeBasicData(CompoundNBT tag, ISpellIngredient[] recipe) {

    }
}
