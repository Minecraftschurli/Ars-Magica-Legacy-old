package minecraftschurli.arsmagicalegacy.objects.spell.modifier;

import java.util.EnumSet;
import minecraftschurli.arsmagicalegacy.api.spell.SpellModifier;
import minecraftschurli.arsmagicalegacy.api.spell.SpellModifiers;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ISpellIngredient;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ItemStackSpellIngredient;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ItemTagSpellIngredient;
import minecraftschurli.arsmagicalegacy.init.ModItems;
import minecraftschurli.arsmagicalegacy.init.ModTags;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.World;

public class Lunar extends SpellModifier {
    @Override
    public EnumSet<SpellModifiers> getAspectsModified() {
        return EnumSet.of(SpellModifiers.RANGE, SpellModifiers.RADIUS, SpellModifiers.DAMAGE, SpellModifiers.DURATION, SpellModifiers.HEALING);
    }

    @Override
    public float getModifier(SpellModifiers type, LivingEntity caster, Entity target, World world, CompoundNBT nbt) {
        switch (type) {
            case DAMAGE:
                return modifyValueOnTime(world, 2.4f);
            case DURATION:
                return modifyValueOnTime(world, 5);
            case HEALING:
                return modifyValueOnTime(world, 2);
            case RADIUS:
            case RANGE:
                return modifyValueOnLunarCycle(world, 3);
        }
        return 1;
    }

    @Override
    public float getManaCostMultiplier(ItemStack spellStack, int stage, int quantity) {
        return 0;
    }

    @Override
    public ISpellIngredient[] getRecipe() {
        return new ISpellIngredient[]{
                new ItemTagSpellIngredient(ModTags.Items.GEMS_MOONSTONE),
                new ItemStackSpellIngredient(new ItemStack(ModItems.NATURE_ESSENCE.get())),
                new ItemStackSpellIngredient(new ItemStack(Items.CLOCK))
        };
    }

    @Override
    public void encodeBasicData(CompoundNBT tag, ISpellIngredient[] recipe) {
    }

    private float modifyValueOnTime(World world, float value) {
        long x = world.getGameTime() % 24000;
        float multiplierFromTime = (float) (Math.sin(((x / 4600f) * (x / 21000f) - 900f) * (180f / Math.PI)) * 3f) + 1;
        if (multiplierFromTime < 0) multiplierFromTime *= -0.5f;
        return value * multiplierFromTime;
    }

    private float modifyValueOnLunarCycle(World world, float value) {
        long boundedTime = world.getGameTime() % 24000;
        int phase = 8 - world.getMoonPhase();
        if (boundedTime > 12500 && boundedTime < 23500) return value + (phase / 2);
        return Math.abs(value - 1);
    }
}
