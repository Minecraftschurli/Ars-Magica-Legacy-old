package minecraftschurli.arsmagicalegacy.objects.spell.component;

import minecraftschurli.arsmagicalegacy.api.spell.*;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.*;
import minecraftschurli.arsmagicalegacy.init.*;
import minecraftschurli.arsmagicalegacy.util.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.util.text.*;
import net.minecraft.world.*;

import java.util.*;

public class Blink extends SpellComponent {
    @Override
    public boolean applyEffectEntity(ItemStack stack, World world, LivingEntity caster, Entity target) {
        if (!(target instanceof LivingEntity)) return false;
//        if (world.isRemote) EntityExtension.For((LivingEntity) target).astralBarrierBlocked = false;
        double distance = SpellUtils.getModifiedIntAdd(12, stack, caster, target, caster.getEntityWorld(), SpellModifiers.RANGE);
        double motionX = -MathHelper.sin((target.rotationYaw / 180F) * 3.141593F) * MathHelper.cos((target.rotationPitch / 180F) * 3.141593F) * distance;
        double motionZ = MathHelper.cos((target.rotationYaw / 180F) * 3.141593F) * MathHelper.cos((target.rotationPitch / 180F) * 3.141593F) * distance;
        double motionY = -MathHelper.sin((target.rotationPitch / 180F) * 3.141593F) * distance;
        double d = motionX, d1 = motionY, d2 = motionZ;
        float f2 = MathHelper.sqrt(d * d + d1 * d1 + d2 * d2);
        d /= f2;
        d1 /= f2;
        d2 /= f2;
        d *= distance;
        d1 *= distance;
        d2 *= distance;
        motionX = d;
        motionY = d1;
        motionZ = d2;
//        ArrayList<Long> keystoneKeys = KeystoneUtilities.instance.GetKeysInInvenory((LivingEntity) target);
        double newX = target.posX + motionX;
        double newZ = target.posZ + motionZ;
        double newY = target.posY + motionY;
        boolean coordsValid = false;
        boolean astralBarrierBlocked = false;
//        TileEntityAstralBarrier finalBlocker = null;
        while (!coordsValid && distance > 0) {
            if (caster.isPotionActive(ModEffects.ASTRAL_DISTORTION.get())) {
                coordsValid = true;
                newX = caster.posX;
                newY = caster.posY;
                newZ = caster.posZ;
            }
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
//                newX = target.posX + motionX;
//                newZ = target.posZ + motionZ;
//                newY = target.posY + motionY;
//                blocker = DimensionUtilities.GetBlockingAstralBarrier(world, new BlockPos(newX, newY, newZ), keystoneKeys);
//            }
            if (distance < 0) {
                coordsValid = false;
                break;
            }
            distance--;
            motionX = -MathHelper.sin((target.rotationYaw / 180F) * 3.141593F) * MathHelper.cos((target.rotationPitch / 180F) * 3.141593F) * distance;
            motionZ = MathHelper.cos((target.rotationYaw / 180F) * 3.141593F) * MathHelper.cos((target.rotationPitch / 180F) * 3.141593F) * distance;
            motionY = -MathHelper.sin((target.rotationPitch / 180F) * 3.141593F) * distance;
            d = motionX;
            d1 = motionY;
            d2 = motionZ;
            f2 = MathHelper.sqrt(d * d + d1 * d1 + d2 * d2);
            d /= f2;
            d1 /= f2;
            d2 /= f2;
            d *= distance;
            d1 *= distance;
            d2 *= distance;
            motionX = d;
            motionY = d1;
            motionZ = d2;
            newX = target.posX + motionX;
            newZ = target.posZ + motionZ;
            newY = target.posY + motionY;
        }
//        if (world.isRemote && astralBarrierBlocked && coordsValid) {
//            EntityExtension.For((LivingEntity) target).astralBarrierBlocked = true;
//            if (finalBlocker != null) finalBlocker.onEntityBlocked((LivingEntity) target);
//        }
        if (!world.isRemote) {
            if (!coordsValid && target instanceof PlayerEntity) {
                ((PlayerEntity) target).sendMessage(new TranslationTextComponent("minecraftschurli.arsmagicalegacy.tooltip.noBlink"));
                return false;
            }
        }
        if (!world.isRemote) target.setPositionAndUpdate(newX, newY, newZ);
        return true;
    }

    @Override
    public float getManaCost(LivingEntity caster) {
        return 160;
    }

    @Override
    public ItemStack[] getReagents(LivingEntity caster) {
        return new ItemStack[0];
    }

    @Override
    public void spawnParticles(World world, double x, double y, double z, LivingEntity caster, Entity target, Random rand, int colorModifier) {
        for (int i = 0; i < 25; ++i) {
//            AMParticle particle = (AMParticle) ArsMagica2.proxy.particleManager.spawn(world, "sparkle", x, y, z);
//            if (particle != null) {
//                particle.addRandomOffset(1, 2, 1);
//                particle.AddParticleController(new ParticleMoveOnHeading(particle, MathHelper.wrapDegrees((target instanceof LivingEntity ? ((LivingEntity) target).rotationYawHead : target.rotationYaw) + 90), MathHelper.wrapDegrees(target.rotationPitch), 0.1 + rand.nextDouble() * 0.5, 1, false));
//                particle.AddParticleController(new ParticleFadeOut(particle, 1, false).setFadeSpeed(0.05f));
//                particle.setMaxAge(20);
//                if (colorModifier > -1) particle.setRGBColorF(((colorModifier >> 16) & 0xFF) / 255.0f, ((colorModifier >> 8) & 0xFF) / 255.0f, (colorModifier & 0xFF) / 255.0f);
//            }
        }
    }

    //    @Override
//    public Set<Affinity> getAffinity() {
//        return Sets.newHashSet(Affinity.ENDER);
//    }
//
    @Override
    public EnumSet<SpellModifiers> getModifiers() {
        return EnumSet.of(SpellModifiers.RANGE);
    }

    @Override
    public ISpellIngredient[] getRecipe() {
        return new ISpellIngredient[]{
                new ItemStackSpellIngredient(new ItemStack(ModItems.PURPLE_RUNE.get())),
                new ItemStackSpellIngredient(new ItemStack(Items.ENDER_PEARL))
        };
    }

    //    @Override
//    public float getAffinityShift(Affinity affinity) {
//        return 0.05f;
//    }
//
    @Override
    public void encodeBasicData(CompoundNBT tag, ISpellIngredient[] recipe) {
    }

    @Override
    public boolean applyEffectBlock(ItemStack stack, World world, BlockPos blockPos, Direction blockFace, double impactX, double impactY, double impactZ, LivingEntity caster) {
        return false;
    }
}
