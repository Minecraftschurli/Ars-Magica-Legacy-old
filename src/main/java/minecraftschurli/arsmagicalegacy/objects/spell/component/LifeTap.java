package minecraftschurli.arsmagicalegacy.objects.spell.component;

import com.google.common.collect.Sets;
import java.util.EnumSet;
import java.util.Random;
import java.util.Set;
import minecraftschurli.arsmagicalegacy.api.affinity.Affinity;
import minecraftschurli.arsmagicalegacy.api.capability.CapabilityHelper;
import minecraftschurli.arsmagicalegacy.api.spell.SpellComponent;
import minecraftschurli.arsmagicalegacy.api.spell.SpellModifiers;
import minecraftschurli.arsmagicalegacy.init.ModAffinities;
import minecraftschurli.arsmagicalegacy.init.ModParticles;
import minecraftschurli.arsmagicalegacy.util.ParticleUtil;
import minecraftschurli.arsmagicalegacy.util.SpellUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public final class LifeTap extends SpellComponent {
    @Override
    public boolean applyEffectBlock(ItemStack stack, World world, BlockPos pos, Direction blockFace, double impactX, double impactY, double impactZ, LivingEntity caster) {
        return false;
    }

    @Override
    public boolean applyEffectEntity(ItemStack stack, World world, LivingEntity caster, Entity target) {
        if (!(target instanceof LivingEntity)) return false;
        if (!world.isRemote && (caster).attackEntityFrom(DamageSource.OUT_OF_WORLD, (int) Math.floor(SpellUtil.modifyDoubleMul(2, stack, caster, target, world, SpellModifiers.DAMAGE)))) {
            CapabilityHelper.increaseMana(caster, (float) (SpellUtil.modifyDoubleMul(2, stack, caster, target, world, SpellModifiers.DAMAGE) * 0.01 * CapabilityHelper.getMaxMana(caster)));
            return true;
        }
        return false;
    }

    @Override
    public float getAffinityShift(Affinity affinity) {
        return 0.01f;
    }

    @Override
    public EnumSet<SpellModifiers> getModifiers() {
        return EnumSet.of(SpellModifiers.DAMAGE);
    }

    @Override
    public float getManaCost(LivingEntity caster) {
        return 0;
    }

    @Override
    public Set<Affinity> getAffinity() {
        return Sets.newHashSet(ModAffinities.LIFE.get(), ModAffinities.ENDER.get());
    }

    /*TODO move to ritual
    @Override
    public Set<ItemStack> getReagents(LivingEntity caster) {
        return new ItemStack[]{
                new ItemStack(ModItems.ENDER_ESSENCE.get()),
                new ItemStack(ModItems.MONSTER_FOCUS.get())
        };
    }*/

    //    @Override
//    public MultiblockStructureDefinition getRitualShape() {
//        return RitualShapeHelper.instance.corruption;
//    }
//
    @Override
    public void spawnParticles(ServerWorld world, double x, double y, double z, LivingEntity caster, Entity target, Random rand, int colorModifier) {
        for (int i = 0; i < 25; i++) ParticleUtil.addParticle(world, target, ModParticles.SHINY, colorModifier, rand.nextBoolean() ? 0x17657f : 0x177f17, x + rand.nextInt(4) - 2, y + rand.nextInt(4) - 2, z + rand.nextInt(4) - 2);
    }
}
