package minecraftschurli.arsmagicalegacy.objects.spell.component;

import minecraftschurli.arsmagicalegacy.api.spell.*;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.*;
import minecraftschurli.arsmagicalegacy.init.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;

import java.util.*;

public class Daylight extends SpellComponent {
    @Override
    public boolean applyEffectBlock(ItemStack stack, World world, BlockPos blockPos, Direction blockFace, double impactX, double impactY, double impactZ, LivingEntity caster) {
        if (world.isDaytime()) return false;
        if (!world.isRemote) {
            long curTime = world.getGameTime();
            int day = (int)Math.ceil((curTime / 24000));
            world.setGameTime(day * 24000);
        }
        return true;
    }

    @Override
    public boolean applyEffectEntity(ItemStack stack, World world, LivingEntity caster, Entity target) {
        if (world.isDaytime()) return false;
        if (!world.isRemote) {
            long curTime = world.getGameTime();
            int day = (int)Math.ceil((curTime / 24000));
            world.setGameTime(day * 24000);
        }
        return true;
    }

    @Override
    public float getManaCost(LivingEntity caster) {
        return 25000;
    }

    @Override
    public ItemStack[] getReagents(LivingEntity caster) {
        return new ItemStack[0];
    }

    @Override
    public void spawnParticles(World world, double x, double y, double z, LivingEntity caster, Entity target, Random rand, int colorModifier) {

    }

    @Override
    public ISpellIngredient[] getRecipe() {
        return new ISpellIngredient[]{
                new ItemStackSpellIngredient(new ItemStack(ModItems.SUNSTONE.get())),
                new ItemStackSpellIngredient(new ItemStack(Items.CLOCK))
        };
    }

    @Override
    public void encodeBasicData(CompoundNBT tag, ISpellIngredient[] recipe) {

    }

    @Override
    public EnumSet<SpellModifiers> getModifiers() {
        return EnumSet.noneOf(SpellModifiers.class);
    }
}
