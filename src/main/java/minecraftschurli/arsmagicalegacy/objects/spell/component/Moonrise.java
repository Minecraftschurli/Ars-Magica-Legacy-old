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

public class Moonrise extends SpellComponent {
    @Override
    public ISpellIngredient[] getRecipe() {
        return new ISpellIngredient[]{
                new ItemStackSpellIngredient(new ItemStack(ModItems.MOONSTONE.get())),
                new ItemStackSpellIngredient(new ItemStack(Items.CLOCK))
        };
    }

    @Override
    public boolean applyEffectBlock(ItemStack stack, World world, BlockPos pos, Direction blockFace, double impactX, double impactY, double impactZ, LivingEntity caster) {
        if (!world.isDaytime()) return false;
        if (!world.isRemote) {
            long curTime = world.getGameTime();
            int day = (int) Math.floor(curTime / 24000);
            world.setGameTime((day * 24000) + 13250);
        }
        return true;
    }

    @Override
    public boolean applyEffectEntity(ItemStack stack, World world, LivingEntity caster, Entity target) {
        if (!world.isDaytime()) return false;
        if (!world.isRemote) {
            long curTime = world.getGameTime();
            int day = (int) Math.floor(curTime / 24000);
            world.setGameTime((day * 24000) + 13250);
        }
        return true;
    }

    @Override
    public float getManaCost(LivingEntity caster) {
        return 25000;
    }

    @Override
    public EnumSet<SpellModifiers> getModifiers() {
        return EnumSet.noneOf(SpellModifiers.class);
    }

    @Override
    public ItemStack[] getReagents(LivingEntity caster) {
        return null;
    }

    @Override
    public void spawnParticles(World world, double x, double y, double z, LivingEntity caster, Entity target, Random rand, int colorModifier) {
    }

//    @Override
//    public Set<Affinity> getAffinity() {
//        return Sets.newHashSet(Affinity.NONE);
//    }
//
//    @Override
//    public float getAffinityShift(Affinity affinity) {
//        return 0;
//    }
//
    @Override
    public void encodeBasicData(CompoundNBT tag, ISpellIngredient[] recipe) {
    }
}