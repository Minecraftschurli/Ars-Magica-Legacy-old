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

public final class Heal extends SpellComponent {
    @Override
    public boolean applyEffectBlock(ItemStack stack, World world, BlockPos blockPos, Direction blockFace, double impactX, double impactY, double impactZ, LivingEntity caster) {
        return false;
    }

    @Override
    public boolean applyEffectEntity(ItemStack stack, World world, LivingEntity caster, Entity target) {
        if (target instanceof LivingEntity) {
            if (((LivingEntity) target).isEntityUndead()) {
                int healing = SpellUtil.modifyIntMul(10, stack, caster, target, world, SpellModifiers.HEALING);
                return SpellUtil.attackWithType(stack, target, DamageSource.causeIndirectMagicDamage(caster, null), (float) (healing * (0.5f + 2 * CapabilityHelper.getAffinityDepth(caster, ModAffinities.LIFE.get()))));
            } else {
                int healing = SpellUtil.modifyIntMul(2, stack, caster, target, world, SpellModifiers.HEALING);
                if (caster.isNonBoss())
                    healing *= 1 + CapabilityHelper.getAffinityDepth(caster, ModAffinities.LIFE.get());
                ((LivingEntity) target).heal(healing);
                return true;
            }
        }
        return false;
    }

    @Override
    public Set<Affinity> getAffinity() {
        return Sets.newHashSet(ModAffinities.LIFE.get());
    }

    @Override
    public float getAffinityShift(Affinity affinity) {
        return 0.05f;
    }

    @Override
    public float getManaCost(LivingEntity caster) {
        return 225f;
    }

    @Override
    public EnumSet<SpellModifiers> getModifiers() {
        return EnumSet.of(SpellModifiers.HEALING);
    }

    @Override
    public void spawnParticles(ServerWorld world, double x, double y, double z, LivingEntity caster, Entity target, Random rand, int colorModifier) {
        if(target instanceof LivingEntity && ((LivingEntity)target).isEntityUndead()) for (int i = 0; i < 25; i++) ParticleUtil.addParticle(world, target, ModParticles.SYMBOL, colorModifier, 0xff3232, x + rand.nextInt(2) - 1, y + rand.nextInt(2) - 1, z + rand.nextInt(2) - 1);
        else for (int i = 0; i < 25; i++) ParticleUtil.addParticle(world, target, ModParticles.SPARKLE, colorModifier, 0x17ff17, x + rand.nextInt(2) - 1, y + rand.nextInt(2) - 1, z + rand.nextInt(2) - 1);
    }
}
