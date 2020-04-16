package minecraftschurli.arsmagicalegacy.objects.spell.component;

import com.google.common.collect.Sets;
import java.util.EnumSet;
import java.util.Random;
import java.util.Set;
import minecraftschurli.arsmagicalegacy.api.affinity.Affinity;
import minecraftschurli.arsmagicalegacy.api.capability.CapabilityHelper;
import minecraftschurli.arsmagicalegacy.api.spell.SpellComponent;
import minecraftschurli.arsmagicalegacy.api.spell.SpellModifiers;
import minecraftschurli.arsmagicalegacy.init.ModSpellParts;
import minecraftschurli.arsmagicalegacy.util.SpellUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

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
                return SpellUtil.attackWithType(stack, target, DamageSource.causeIndirectMagicDamage(caster, null), (float) (healing * (0.5f + 2 * CapabilityHelper.getAffinityDepth(caster, ModSpellParts.LIFE.get()))));
            } else {
                int healing = SpellUtil.modifyIntMul(2, stack, caster, target, world, SpellModifiers.HEALING);
                if (caster.isNonBoss())
                    healing *= 1 + CapabilityHelper.getAffinityDepth(caster, ModSpellParts.LIFE.get());
                ((LivingEntity) target).heal(healing);
                return true;
            }
        }
        return false;
    }

    @Override
    public Set<Affinity> getAffinity() {
        return Sets.newHashSet(ModSpellParts.LIFE.get());
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
    public void spawnParticles(World world, double x, double y, double z, LivingEntity caster, Entity target, Random rand, int colorModifier) {
//        if (target instanceof LivingEntity && ((LivingEntity) target).isEntityUndead()) {
//            for (int i = 0; i < 25; i++) {
//                AMParticle particle = (AMParticle) ArsMagicaLegacy.proxy.particleManager.spawn(world, "symbols", x, y - 1, z);
//                if (particle != null) {
//                    particle.addRandomOffset(1, 1, 1);
//                    particle.AddParticleController(new ParticleHoldPosition(particle, 20, 1, true));
//                    particle.AddParticleController(new ParticleFloatUpward(particle, 0, -0.01f, 2, false));
//                    particle.AddParticleController(new ParticleFadeOut(particle, 2, false).setFadeSpeed(0.02f));
//                    particle.setParticleScale(0.1f);
//                    particle.setRGBColorF(1f, 0.2f, 0.2f);
//                }
//            }
//        } else {
//            for (int i = 0; i < 25; i++) {
//                AMParticle particle = (AMParticle) ArsMagicaLegacy.proxy.particleManager.spawn(world, "sparkle", x, y - 1, z);
//                if (particle != null) {
//                    particle.addRandomOffset(1, 1, 1);
//                    particle.AddParticleController(new ParticleFloatUpward(particle, 0, 0.1f, 1, false));
//                    particle.AddParticleController(new ParticleOrbitEntity(particle, target, 0.5f, 2, false).setIgnoreYCoordinate(true).SetTargetDistance(0.3f + rand.nextDouble() * 0.3));
//                    particle.setMaxAge(20);
//                    particle.setParticleScale(0.2f);
//                    particle.setRGBColorF(0.1f, 1f, 0.1f);
//                    if (colorModifier > -1) particle.setRGBColorF(((colorModifier >> 16) & 0xFF) / 255, ((colorModifier >> 8) & 0xFF) / 255, (colorModifier & 0xFF) / 255);
//                }
//            }
//        }
    }
}
