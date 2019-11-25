package minecraftschurli.arsmagicalegacy.objects.spell.modifier;

import minecraftschurli.arsmagicalegacy.api.spellsystem.ISpellIngredient;
import minecraftschurli.arsmagicalegacy.api.spellsystem.ItemStackSpellIngredient;
import minecraftschurli.arsmagicalegacy.api.spellsystem.ItemTagSpellIngredient;
import minecraftschurli.arsmagicalegacy.api.spellsystem.SpellModifier;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;
import net.minecraftforge.common.Tags;

import java.util.EnumSet;

public class BuffPower extends SpellModifier {
    @Override
    public EnumSet<Type> getAspectsModified() {
        return EnumSet.of(Type.BUFF_POWER);
    }

    @Override
    public float getManaCostMultiplier(ItemStack spellStack, int stage, int quantity) {
        return 1.25f * quantity;
    }

    @Override
    public float getModifier(Type type, LivingEntity caster, Entity target, World world, byte[] metadata) {
        return 1;
    }

    @Override
    public ISpellIngredient[] getRecipeItems() {
        return new ISpellIngredient[]{
                new ItemStackSpellIngredient(new ItemStack(Items.GLASS_BOTTLE)),
                new ItemTagSpellIngredient(Tags.Items.DUSTS_REDSTONE, 1),
                new ItemTagSpellIngredient(Tags.Items.DUSTS_GLOWSTONE, 1),
                new ItemStackSpellIngredient(new ItemStack(Items.GHAST_TEAR)),
                new ItemStackSpellIngredient(new ItemStack(Items.SPIDER_EYE)),
                new ItemTagSpellIngredient(Tags.Items.CROPS_NETHER_WART, 1),
                new ItemTagSpellIngredient(Tags.Items.GUNPOWDER, 1),
                new ItemTagSpellIngredient(Tags.Items.GEMS_EMERALD, 1),
        };
    }
}
