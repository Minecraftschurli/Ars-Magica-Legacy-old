package minecraftschurli.arsmagicalegacy.spell.component;

import com.google.common.collect.*;
import minecraftschurli.arsmagicalegacy.api.affinity.*;
import minecraftschurli.arsmagicalegacy.api.spell.*;
import minecraftschurli.arsmagicalegacy.init.*;
import minecraftschurli.arsmagicalegacy.packet.*;
import minecraftschurli.arsmagicalegacy.particles.*;
import minecraftschurli.arsmagicalegacy.utils.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;

import java.util.*;

public class Knockback extends SpellComponent {
    @Override
    public boolean applyEffectEntity(ItemStack stack, World world, LivingEntity caster, Entity target) {
        if (target instanceof LivingEntity) {
            double speed = 1.5;
            speed = SpellUtils.getModifiedDoubleAdd(speed, stack, caster, target, world, SpellModifiers.VELOCITY_ADDED);
            double vertSpeed = 0.325;
            LivingEntity curEntity = (LivingEntity) target;
            double deltaZ = curEntity.posZ - caster.posZ;
            double deltaX = curEntity.posX - caster.posX;
            double angle = Math.atan2(deltaZ, deltaX);
            double radians = angle;
            if (curEntity instanceof PlayerEntity) {
                AMNetHandler.INSTANCE.sendVelocityAddPacket(world, curEntity, speed * Math.cos(radians), vertSpeed, speed * Math.sin(radians));
            } else {
                curEntity.motionX += (speed * Math.cos(radians));
                curEntity.motionZ += (speed * Math.sin(radians));
                curEntity.motionY += vertSpeed;
            }
            return true;
        }
        return false;
    }

    @Override
    public EnumSet<SpellModifiers> getModifiers() {
        return EnumSet.of(SpellModifiers.VELOCITY_ADDED);
    }

    @Override
    public float getManaCost(LivingEntity caster) {
        return 60;
    }

    @Override
    public ItemStack[] reagents(LivingEntity caster) {
        return null;
    }

    @Override
    public void spawnParticles(World world, double x, double y, double z, LivingEntity caster, Entity target, Random rand, int colorModifier) {
        for (int i = 0; i < 25; ++i) {
            AMParticle particle = (AMParticle) ArsMagica2.proxy.particleManager.spawn(world, "sparkle", x, y, z);
            if (particle != null) {
                particle.addRandomOffset(1, 2, 1);
                double dx = caster.posX - target.posX;
                double dz = caster.posZ - target.posZ;
                double angle = Math.toDegrees(Math.atan2(-dz, -dx));
                particle.AddParticleController(new ParticleMoveOnHeading(particle, angle, 0, 0.1 + rand.nextDouble() * 0.5, 1, false));
                particle.AddParticleController(new ParticleFadeOut(particle, 1, false).setFadeSpeed(0.05f));
                particle.setMaxAge(20);
                if (colorModifier > -1) {
                    particle.setRGBColorF(((colorModifier >> 16) & 0xFF) / 255.0f, ((colorModifier >> 8) & 0xFF) / 255.0f, (colorModifier & 0xFF) / 255.0f);
                }
            }
        }
    }

    @Override
    public Set<Affinity> getAffinity() {
        return Sets.newHashSet(Affinity.AIR, Affinity.WATER, Affinity.EARTH);
    }

    @Override
    public ISpellIngredient[] getRecipe() {
        return new ISpellIngredient[]{
                new ItemStack(ModItems.YELLOW_RUNE.get()),
                Blocks.PISTON
        };
    }

    @Override
    public float getAffinityShift(Affinity affinity) {
        return 0.01f;
    }

    @Override
    public void encodeBasicData(CompoundNBT tag, Object[] recipe) {
    }

    @Override
    public boolean applyEffectBlock(ItemStack stack, World world, BlockPos blockPos, Direction blockFace,
                                    double impactX, double impactY, double impactZ, LivingEntity caster) {
        return false;
    }
}