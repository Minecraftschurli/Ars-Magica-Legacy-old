package minecraftschurli.arsmagicalegacy.objects.spell.component;

import minecraftschurli.arsmagicalegacy.api.spell.SpellComponent;
import minecraftschurli.arsmagicalegacy.api.spell.SpellModifiers;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ISpellIngredient;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ItemStackSpellIngredient;
import minecraftschurli.arsmagicalegacy.init.ModItems;
import minecraftschurli.arsmagicalegacy.util.SpellUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponent;
import net.minecraft.world.World;

import java.util.EnumSet;
import java.util.Random;

public class Blink extends SpellComponent {
    @Override
    public boolean applyEffectBlock(ItemStack stack, World world, BlockPos blockPos, Direction blockFace, double impactX, double impactY, double impactZ, LivingEntity caster) {
        return false;
    }

    @Override
    public boolean applyEffectEntity(ItemStack stack, World world, LivingEntity caster, Entity target) {
        if (!(target instanceof LivingEntity)) return false;
//        if (world.isRemote) EntityExtension.For((LivingEntity)target).astralBarrierBlocked = false;
        double distance = SpellUtils.getModifiedIntAdd(12, stack, caster, target, caster.getEntityWorld(), SpellModifiers.RANGE);
        double motionX = -MathHelper.sin((target.rotationYaw / 180F) * (float) Math.PI) * MathHelper.cos((target.rotationPitch / 180F) * (float) Math.PI) * distance;
        double motionZ = MathHelper.cos((target.rotationYaw / 180F) * (float) Math.PI) * MathHelper.cos((target.rotationPitch / 180F) * (float) Math.PI) * distance;
        double motionY = -MathHelper.sin((target.rotationPitch / 180F) * (float) Math.PI) * distance;
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
//        ArrayList<Long> keystoneKeys = KeystoneUtilities.GetKeysInInvenory((LivingEntity)target);
        double newX = target.posX + motionX;
        double newZ = target.posZ + motionZ;
        double newY = target.posY + motionY;
        boolean coordsValid = false;
        boolean astralBarrierBlocked = false;
//        TileEntityAstralBarrier finalBlocker = null;
//        while (!coordsValid && distance > 0){
//            if (caster.isPotionActive(PotionEffectsDefs.astralDistortion)){
//                coordsValid = true;
//                newX = caster.posX;
//                newY = caster.posY;
//                newZ = caster.posZ;
//            }
//            TileEntityAstralBarrier blocker = DimensionUtilities.GetBlockingAstralBarrier(world, new BlockPos(newX, newY, newZ), keystoneKeys);
//            while (blocker != null) {
//                finalBlocker = blocker;
//                astralBarrierBlocked = true;
//                int dx = (int)newX - blocker.getPos().getX();
//                int dy = (int)newY - blocker.getPos().getY();
//                int dz = (int)newZ - blocker.getPos().getZ();
//                int sqDist = (dx * dx + dy * dy + dz * dz);
//                int delta = blocker.getRadius() - (int)Math.floor(Math.sqrt(sqDist));
//                distance -= delta;
//                if (distance < 0) break;
//                motionX = -MathHelper.sin((target.rotationYaw / 180F) * (float)Math.PI) * MathHelper.cos((target.rotationPitch / 180F) * (float)Math.PI) * distance;
//                motionZ = MathHelper.cos((target.rotationYaw / 180F) * (float)Math.PI) * MathHelper.cos((target.rotationPitch / 180F) * (float)Math.PI) * distance;
//                motionY = -MathHelper.sin((target.rotationPitch / 180F) * (float)Math.PI) * distance;
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
//                break;
        }
        if (newY >= 0) {
            coordsValid = true;
//                break;
        }
        if (newY - 1 >= 0) {
            newY--;
            coordsValid = true;
//                break;
            if (newY + 1 >= 0) {
                newY++;
                coordsValid = true;
//                break;
            }
            distance--;
            motionX = -MathHelper.sin((target.rotationYaw / 180F) * (float) Math.PI) * MathHelper.cos((target.rotationPitch / 180F) * (float) Math.PI) * distance;
            motionZ = MathHelper.cos((target.rotationYaw / 180F) * (float) Math.PI) * MathHelper.cos((target.rotationPitch / 180F) * (float) Math.PI) * distance;
            motionY = -MathHelper.sin((target.rotationPitch / 180F) * (float) Math.PI) * distance;
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
        if (world.isRemote && astralBarrierBlocked && coordsValid) {
//            EntityExtension.For((LivingEntity)target).astralBarrierBlocked = true;
//            if (finalBlocker != null) finalBlocker.onEntityBlocked((LivingEntity)target);
        }
        if (!world.isRemote) {
            if (!coordsValid && target instanceof PlayerEntity) {
                target.sendMessage(new TextComponent() {
                    @Override
                    public String getUnformattedComponentText() {
                        return "Can't find a place to blink forward to.";
                    }

                    @Override
                    public ITextComponent shallowCopy() {
                        return null;
                    }
                });
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
//            AMParticle particle = (AMParticle)ArsMagica2.proxy.particleManager.spawn(world, "sparkle", x, y, z);
//            if (particle != null){
//                particle.addRandomOffset(1, 2, 1);
//                particle.AddParticleController(new ParticleMoveOnHeading(particle, MathHelper.wrapDegrees((target instanceof LivingEntity ? ((LivingEntity)target).rotationYawHead : target.rotationYaw) + 90), MathHelper.wrapDegrees(target.rotationPitch), 0.1 + rand.nextDouble() * 0.5, 1, false));
//                particle.AddParticleController(new ParticleFadeOut(particle, 1, false).setFadeSpeed(0.05f));
//                particle.setMaxAge(20);
//                if (colorModifier > -1) particle.setRGBColorF(((colorModifier >> 16) & 0xFF) / 255.0f, ((colorModifier >> 8) & 0xFF) / 255.0f, (colorModifier & 0xFF) / 255.0f);
//            }
        }
    }

    @Override
    public ISpellIngredient[] getRecipe() {
        return new ISpellIngredient[]{
                new ItemStackSpellIngredient(new ItemStack(ModItems.PURPLE_RUNE.get())),
                new ItemStackSpellIngredient(new ItemStack(Items.ENDER_PEARL))
        };
    }

    @Override
    public void encodeBasicData(CompoundNBT tag, ISpellIngredient[] recipe) {

    }

    @Override
    public EnumSet<SpellModifiers> getModifiers() {
        return EnumSet.of(SpellModifiers.RANGE);
    }
}
