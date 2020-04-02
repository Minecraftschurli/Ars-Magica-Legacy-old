package minecraftschurli.arsmagicalegacy.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.lang.reflect.Method;
import java.util.List;

public final class EntityUtil {
//    private static final HashMap<Integer, ArrayList<EntityAITasks.EntityAITaskEntry>> storedTasks = new HashMap<>();
//    private static final HashMap<Integer, ArrayList<EntityAITasks.EntityAITaskEntry>> storedAITasks = new HashMap<>();
    private static Method ptrSetSize = null;

    public static boolean canBlockDamageSource(LivingEntity living, DamageSource damageSourceIn) {
        if (!damageSourceIn.isUnblockable()) {
            Vec3d vec3d = damageSourceIn.getDamageLocation();
            if (vec3d != null) {
                Vec3d vec3d1 = living.getLook(1);
                Vec3d vec3d2 = vec3d.subtractReverse(new Vec3d(living.getPosX(), living.getPosY(), living.getPosZ())).normalize();
                vec3d2 = new Vec3d(vec3d2.getX(), 0, vec3d2.getZ());
                return vec3d2.dotProduct(vec3d1) < 0;
            }
        }
        return false;
    }

    public static Vec3d extrapolateEntityLook(LivingEntity entity, double range) {
        float pitch = entity.rotationPitch;
        float yaw = entity.rotationYaw;
        double x = entity.getPosX();
        double y = entity.getPosY() + 1.6 - entity.getYOffset();
        double z = entity.getPosZ();
        Vec3d vec = new Vec3d(x, y, z);
        float f1 = MathHelper.cos(-yaw * 0.017453292f - (float) Math.PI);
        float f2 = MathHelper.sin(-yaw * 0.017453292f - (float) Math.PI);
        float f3 = -MathHelper.cos(-pitch * 0.017453292f);
        float f4 = MathHelper.sin(-pitch * 0.017453292f);
        return vec.add(f2 * f3 * range, f4 * range, f1 * f3 * range);
    }

    public static RayTraceResult getMovingObjectPosition(LivingEntity caster, World world, double range, boolean includeEntities, boolean targetWater) {
        RayTraceResult entityPos = null;
        if (includeEntities) {
            Entity pointedEntity = getPointedEntity(world, caster, range, 1, false, targetWater);
            if (pointedEntity != null) entityPos = new EntityRayTraceResult(pointedEntity);
        }
        float factor = 1;
        float interpPitch = caster.prevRotationPitch + (caster.rotationPitch - caster.prevRotationPitch) * factor;
        float interpYaw = caster.prevRotationYaw + (caster.rotationYaw - caster.prevRotationYaw) * factor;
        double interpPosX = caster.prevPosX + (caster.getPosX() - caster.prevPosX) * factor;
        double interpPosY = caster.prevPosY + (caster.getPosY() - caster.prevPosY) * factor + caster.getEyeHeight();
        double interpPosZ = caster.prevPosZ + (caster.getPosZ() - caster.prevPosZ) * factor;
        Vec3d vec3 = new Vec3d(interpPosX, interpPosY, interpPosZ);
        float offsetYawCos = MathHelper.cos(-interpYaw * 0.017453292F - (float) Math.PI);
        float offsetYawSin = MathHelper.sin(-interpYaw * 0.017453292F - (float) Math.PI);
        float offsetPitchCos = -MathHelper.cos(-interpPitch * 0.017453292F);
        float offsetPitchSin = MathHelper.sin(-interpPitch * 0.017453292F);
        float finalXOffset = offsetYawSin * offsetPitchCos;
        float finalZOffset = offsetYawCos * offsetPitchCos;
        Vec3d targetVector = vec3.add(finalXOffset * range, offsetPitchSin * range, finalZOffset * range);
        RayTraceResult mop = world.rayTraceBlocks(new RayTraceContext(vec3, targetVector, RayTraceContext.BlockMode.OUTLINE, targetWater ? RayTraceContext.FluidMode.SOURCE_ONLY : RayTraceContext.FluidMode.NONE, caster));
        return entityPos == null || mop.getHitVec().distanceTo(new EntityRayTraceResult(caster).getHitVec()) < entityPos.getHitVec().distanceTo(new EntityRayTraceResult(caster).getHitVec()) ? mop : entityPos;
    }

    public static Entity getPointedEntity(World world, LivingEntity player, double range, double collideRadius, boolean nonCollide, boolean targetWater) {
        Entity pointedEntity = null;
        Vec3d vec = new Vec3d(player.getPosX(), player.getPosY() + player.getEyeHeight(), player.getPosZ());
        Vec3d lookVec = player.getLookVec();
        List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(player, player.getBoundingBox().grow(lookVec.getX() * range, lookVec.getY() * range, lookVec.getZ() * range).expand(collideRadius, collideRadius, collideRadius));
        double d = 0;
        for (Entity entity : list) {
            RayTraceResult mop = null;//world.rayTraceBlocks(new Vec3d(player.getPosX(), player.getPosY() + player.getEyeHeight(), player.getPosZ()), new Vec3d(entity.getPosX(), entity.getPosY() + entity.getEyeHeight(), entity.getPosZ()), targetWater, !targetWater, false);
            if ((entity.canBeCollidedWith() || nonCollide) && mop == null) {
                float f2 = Math.max(0.8F, entity.getCollisionBorderSize());
                AxisAlignedBB axisalignedbb = entity.getBoundingBox().expand(f2, f2, f2);
                RayTraceResult movingobjectposition = null;//axisalignedbb.calculateIntercept(vec, lookVec);
                if (axisalignedbb.contains(vec)) {
                    pointedEntity = entity;
                    d = 0;
                } else if (movingobjectposition != null) {
                    double d3 = vec.distanceTo(movingobjectposition.getHitVec());
                    if ((d3 < d) || (d == 0)) {
                        pointedEntity = entity;
                        d = d3;
                    }
                }
            }
        }
        return pointedEntity;
    }

    public static float invertEyePos(float floatIn, Entity entityIn) {
        float curHeight = floatIn;
        if (entityIn instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entityIn;
            curHeight = player.getHeight() - floatIn - 0.1f;
        }
//        if (entityIn instanceof LivingEntity && EntityExtension.For((LivingEntity) entityIn).shrinkAmount != 0) curHeight *= EntityExtension.For((LivingEntity) entityIn).shrinkAmount;
        return curHeight;
    }

    public static Vec3d invertLook(Vec3d vecIn, Entity entityIn) {
        if (entityIn instanceof LivingEntity) return new Vec3d(vecIn.getX(), -vecIn.getY(), vecIn.getZ());
        return vecIn;
    }

    public static boolean invertMovement(float strafe, float forward, float friction, Entity entityIn) {
        if (entityIn instanceof PlayerEntity) {
            float f = strafe * strafe + forward * forward;
            if (f >= 1.0e-4) {
                f = MathHelper.sqrt(f);
                if (f < 1) f = 1;
                f = friction / f;
                strafe *= f;
                forward *= f;
                float f1 = MathHelper.sin(-entityIn.rotationYaw * 0.017453292f);
                float f2 = MathHelper.cos(-entityIn.rotationYaw * 0.017453292f);
                entityIn.setMotion(entityIn.getMotion().getX() + strafe * f2 - forward * f1, entityIn.getMotion().getY(), entityIn.getMotion().getZ() + forward * f2 + strafe * f1);
            }
            return true;
        }
        return false;
    }

    public static double normalizeYaw(double yaw) {
        return Math.abs(yaw % 360);
    }

    public static void setSize(LivingEntity entityliving, float width, float height) {
        if (entityliving.getWidth() == width && entityliving.getHeight() == height) return;
        if (ptrSetSize == null) try {
            ptrSetSize = ObfuscationReflectionHelper.findMethod(Entity.class, "func_70105_a");
        } catch (Throwable t) {
            t.printStackTrace();
        }
        else try {
            ptrSetSize.setAccessible(true);
            ptrSetSize.invoke(entityliving, width, height);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
