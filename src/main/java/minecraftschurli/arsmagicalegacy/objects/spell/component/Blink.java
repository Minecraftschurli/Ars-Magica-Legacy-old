package minecraftschurli.arsmagicalegacy.objects.spell.component;

import com.google.common.collect.Sets;
import java.util.EnumSet;
import java.util.Random;
import java.util.Set;
import minecraftschurli.arsmagicalegacy.api.affinity.Affinity;
import minecraftschurli.arsmagicalegacy.api.spell.SpellComponent;
import minecraftschurli.arsmagicalegacy.api.spell.SpellModifiers;
import minecraftschurli.arsmagicalegacy.init.ModAffinities;
import minecraftschurli.arsmagicalegacy.init.ModParticles;
import minecraftschurli.arsmagicalegacy.util.ParticleUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public final class Blink extends SpellComponent {
    @Override
    public boolean applyEffectBlock(ItemStack stack, World world, BlockPos blockPos, Direction blockFace, double impactX, double impactY, double impactZ, LivingEntity caster) {
        return false;
    }

    @Override
    public boolean applyEffectEntity(ItemStack stack, World world, LivingEntity caster, Entity target) {
//        if (!(target instanceof LivingEntity)) return false;
//        if (world.isRemote) EntityExtension.For((LivingEntity) target).astralBarrierBlocked = false;
//        double distance = SpellUtils.getModifiedIntAdd(12, stack, caster, target, caster.getEntityWorld(), SpellModifiers.RANGE);
//        double motionX = -MathHelper.sin((target.rotationYaw / 180F) * 3.141593F) * MathHelper.cos((target.rotationPitch / 180F) * 3.141593F) * distance;
//        double motionZ = MathHelper.cos((target.rotationYaw / 180F) * 3.141593F) * MathHelper.cos((target.rotationPitch / 180F) * 3.141593F) * distance;
//        double motionY = -MathHelper.sin((target.rotationPitch / 180F) * 3.141593F) * distance;
//        double d = motionX, d1 = motionY, d2 = motionZ;
//        float f2 = MathHelper.sqrt(d * d + d1 * d1 + d2 * d2);
//        d /= f2;
//        d1 /= f2;
//        d2 /= f2;
//        d *= distance;
//        d1 *= distance;
//        d2 *= distance;
//        motionX = d;
//        motionY = d1;
//        motionZ = d2;
//        ArrayList<Long> keystoneKeys = KeystoneUtilities.instance.GetKeysInInvenory((LivingEntity) target);
//        double newX = target.getPosX() + motionX;
//        double newZ = target.getPosZ() + motionZ;
//        double newY = target.getPosY() + motionY;
//        boolean coordsValid = false;
//        boolean astralBarrierBlocked = false;
//        TileEntityAstralBarrier finalBlocker = null;
//        while (!coordsValid && distance > 0) {
//            if (caster.isPotionActive(ModEffects.ASTRAL_DISTORTION.get())) {
//                coordsValid = true;
//                newX = caster.getPosX();
//                newY = caster.getPosY();
//                newZ = caster.getPosZ();
//            }
//            TileEntityAstralBarrier blocker = DimensionUtilities.GetBlockingAstralBarrier(world, new BlockPos(newX, newY, newZ), keystoneKeys);
//            while (blocker != null) {
//                finalBlocker = blocker;
//                astralBarrierBlocked = true;
//                int dx = (int) newX - blocker.getPos().getX();
//                int dy = (int) newY - blocker.getPos().getY();
//                int dz = (int) newZ - blocker.getPos().getZ();
//                int sqDist = (dx * dx + dy * dy + dz * dz);
//                int delta = blocker.getRadius() - (int) Math.floor(Math.sqrt(sqDist));
//                distance -= delta;
//                if (distance < 0) break;
//                motionX = -MathHelper.sin((target.rotationYaw / 180F) * 3.141593F) * MathHelper.cos((target.rotationPitch / 180F) * 3.141593F) * distance;
//                motionZ = MathHelper.cos((target.rotationYaw / 180F) * 3.141593F) * MathHelper.cos((target.rotationPitch / 180F) * 3.141593F) * distance;
//                motionY = -MathHelper.sin((target.rotationPitch / 180F) * 3.141593F) * distance;
//                d = motionX;
//                d1 = motionY;
//                d2 = motionZ;
//                f2 = MathHelper.sqrt(d * d + d1 * d1 + d2 * d2);
//                d /= f2;
//                d1 /= f2;
//                d2 /= f2;
//                d *= distance;
//                d1 *= distance;
//                d2 *= distance;
//                motionX = d;
//                motionY = d1;
//                motionZ = d2;
//                newX = target.getPosX() + motionX;
//                newZ = target.getPosZ() + motionZ;
//                newY = target.getPosY() + motionY;
//                blocker = DimensionUtilities.GetBlockingAstralBarrier(world, new BlockPos(newX, newY, newZ), keystoneKeys);
//            }
//            if (distance < 0) {
//                coordsValid = false;
//                break;
//            }
//            distance--;
//            motionX = -MathHelper.sin((target.rotationYaw / 180F) * 3.141593F) * MathHelper.cos((target.rotationPitch / 180F) * 3.141593F) * distance;
//            motionZ = MathHelper.cos((target.rotationYaw / 180F) * 3.141593F) * MathHelper.cos((target.rotationPitch / 180F) * 3.141593F) * distance;
//            motionY = -MathHelper.sin((target.rotationPitch / 180F) * 3.141593F) * distance;
//            d = motionX;
//            d1 = motionY;
//            d2 = motionZ;
//            f2 = MathHelper.sqrt(d * d + d1 * d1 + d2 * d2);
//            d /= f2;
//            d1 /= f2;
//            d2 /= f2;
//            d *= distance;
//            d1 *= distance;
//            d2 *= distance;
//            motionX = d;
//            motionY = d1;
//            motionZ = d2;
//            newX = target.getPosX() + motionX;
//            newZ = target.getPosZ() + motionZ;
//            newY = target.getPosY() + motionY;
//        }
//        if (world.isRemote && astralBarrierBlocked && coordsValid) {
//            EntityExtension.For((LivingEntity) target).astralBarrierBlocked = true;
//            if (finalBlocker != null) finalBlocker.onEntityBlocked((LivingEntity) target);
//        }
//        if (!world.isRemote) {
//            if (!coordsValid && target instanceof PlayerEntity) {
//                target.sendMessage(new TranslationTextComponent(ArsMagicaAPI.MODID + ".chat.noBlink"));
//                return false;
//            }
//        }
//        if (!world.isRemote) target.setPositionAndUpdate(newX, newY, newZ);
        return false;//true;
    }

    @Override
    public Set<Affinity> getAffinity() {
        return Sets.newHashSet(ModAffinities.ENDER.get());
    }

    @Override
    public float getAffinityShift(Affinity affinity) {
        return 0.05f;
    }

    @Override
    public float getManaCost(LivingEntity caster) {
        return 160;
    }

    @Override
    public EnumSet<SpellModifiers> getModifiers() {
        return EnumSet.of(SpellModifiers.RANGE);
    }

    @Override
    public void spawnParticles(ServerWorld world, double x, double y, double z, LivingEntity caster, Entity target, Random rand, int colorModifier) {
        for (int i = 0; i < 25; i++)
            ParticleUtil.addParticle(world, target, ModParticles.SPARKLE, colorModifier, 0, x + rand.nextInt(2) - 1, y + rand.nextInt(4) - 2, z + rand.nextInt(2) - 1);
    }
}
