package minecraftschurli.arsmagicalegacy.util;

import net.minecraft.entity.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;

import java.lang.reflect.*;
import java.util.*;

public class EntityUtils {
//    private static final HashMap<Integer, ArrayList<EntityAITasks.EntityAITaskEntry>> storedTasks = new HashMap<>();
//    private static final HashMap<Integer, ArrayList<EntityAITasks.EntityAITaskEntry>> storedAITasks = new HashMap<>();
    private static final String isSummonKey = "AM2_Entity_Is_Made_Summon";
    //	private static final String summonEntityIDs = "AM2_Summon_Entity_IDs";
    private static final String summonDurationKey = "AM2_Summon_Duration";
    private static final String summonOwnerKey = "AM2_Summon_Owner";
    private static Method ptrSetSize = null;
    private static final String summonTileXKey = "AM2_Summon_Tile_X";
    private static final String summonTileYKey = "AM2_Summon_Tile_Y";
    private static final String summonTileZKey = "AM2_Summon_Tile_Z";


    public static int getLevelFromXP(float totalXP){
        int level = 0;
        int xp = (int)Math.floor(totalXP);
        while(true) {
            int cap = xpBarCap(level);
            xp -= cap;
            if (xp < 0) break;
            level++;
        }
        return level;
    }

    public static Entity getPointedEntity(World world, LivingEntity entityplayer, double range, double collideRadius, boolean nonCollide, boolean targetWater){
        Entity pointedEntity = null;
        double d = range;
        Vec3d vec3d = new Vec3d(entityplayer.posX, entityplayer.posY + entityplayer.getEyeHeight(), entityplayer.posZ);
        Vec3d vec3d1 = entityplayer.getLookVec();
        Vec3d vec3d2 = vec3d.add(vec3d1.getX() * d, vec3d1.getY() * d, vec3d1.getZ() * d);
        double f1 = collideRadius;
        List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(entityplayer, entityplayer.getBoundingBox().grow(vec3d1.getX() * d, vec3d1.getY() * d, vec3d1.getZ() * d).expand(f1, f1, f1));
        double d2 = 0.0D;
        for (Entity entity : list) {
            RayTraceResult mop = null;//world.rayTraceBlocks(new Vec3d(entityplayer.posX, entityplayer.posY + entityplayer.getEyeHeight(), entityplayer.posZ), new Vec3d(entity.posX, entity.posY + entity.getEyeHeight(), entity.posZ), targetWater, !targetWater, false);
            if ((entity.canBeCollidedWith() || nonCollide) && mop == null) {
                float f2 = Math.max(0.8F, entity.getCollisionBorderSize());
                AxisAlignedBB axisalignedbb = entity.getBoundingBox().expand(f2, f2, f2);
//                RayTraceResult movingobjectposition = axisalignedbb.calculateIntercept(vec3d, vec3d2);
//                if (axisalignedbb.contains(vec3d)) {
//                    if ((0.0D < d2) || (d2 == 0.0D)) {
//                        pointedEntity = entity;
//                        d2 = 0.0D;
//                    }
//                } else if (movingobjectposition != null) {
//                    double d3 = vec3d.distanceTo(movingobjectposition.getHitVec());
//                    if ((d3 < d2) || (d2 == 0.0D)) {
//                        pointedEntity = entity;
//                        d2 = d3;
//                    }
//                }
            }
        }
        return pointedEntity;
    }

    public static int getXPFromLevel(int level){
        int totalXP = 0;
        for(int i = 0; i < level; i++) totalXP += xpBarCap(i);
        return totalXP;
    }

    public static int xpBarCap(int experienceLevel){
        return experienceLevel >= 30 ? 112 + (experienceLevel - 30) * 9 : (experienceLevel >= 15 ? 37 + (experienceLevel - 15) * 5 : 7 + experienceLevel * 2);//experienceLevel >= 30 ? 62 + (experienceLevel - 30) * 7 : (experienceLevel >= 15 ? 17 + (experienceLevel - 15) * 3 : 17);
    }

    public static boolean isAIEnabled(CreatureEntity ent){
        return !ent.isAIDisabled();
    }

    public static void makeSummonPlayerFaction(CreatureEntity entityliving, PlayerEntity player, boolean storeForRevert){
//        if (isAIEnabled(entityliving) && EntityExtension.For(player).getCurrentSummons() < EntityExtension.For(player).getMaxSummons()){
//            if (storeForRevert) storedTasks.put(entityliving.getEntityId(), new ArrayList<EntityAITasks.EntityAITaskEntry>(entityliving.targetTasks.taskEntries));
            boolean addMeleeAttack = false;
//            ArrayList<EntityAITaskEntry> toRemove = new ArrayList<EntityAITaskEntry>();
//            for (Object task : entityliving.tasks.taskEntries){
//                EntityAITaskEntry base = (EntityAITaskEntry)task;
//                if (base.action instanceof EntityAIAttackMelee){
//                    toRemove.add(base);
//                    addMeleeAttack = true;
//                }
//            }
//            entityliving.tasks.taskEntries.removeAll(toRemove);
//            if (storeForRevert) storedAITasks.put(entityliving.getEntityId(), toRemove);
            if (addMeleeAttack){
                float speed = entityliving.getAIMoveSpeed();
                if (speed <= 0) speed = 1.0f;
//                entityliving.tasks.addTask(3, new EntityAIAttackMelee(entityliving, speed, true));
            }
//            entityliving.targetTasks.taskEntries.clear();
//            entityliving.targetTasks.addTask(1, new EntityAIHurtByTarget(entityliving, true));
//            entityliving.targetTasks.addTask(2, new EntityAINearestAttackableTarget<MobEntity>(entityliving, MobEntity.class, 0, true, false, SummonEntitySelector.instance));
//            entityliving.targetTasks.addTask(2, new EntityAINearestAttackableTarget<SlimeEntity>(entityliving, SlimeEntity.class, true));
//            entityliving.targetTasks.addTask(2, new EntityAINearestAttackableTarget<GhastEntity>(entityliving, GhastEntity.class, true));
//            entityliving.targetTasks.addTask(2, new EntityAINearestAttackableTarget<ShulkerEntity>(entityliving, ShulkerEntity.class, true));
//            if (!entityliving.world.isRemote && entityliving.getAttackTarget() != null && entityliving.getAttackTarget() instanceof PlayerEntity) ArsMagica2.proxy.addDeferredTargetSet(entityliving, null);
            if (entityliving instanceof TameableEntity){
                ((TameableEntity)entityliving).setTamed(true);
                ((TameableEntity)entityliving).setOwnerId(player.getUniqueID());
            }
            entityliving.getPersistentData().putBoolean(isSummonKey, true);
//            EntityExtension.For(player).addSummon(entityliving);
//        }
    }

    public static boolean isSummon(LivingEntity entityliving) {
        return entityliving.getPersistentData().getBoolean(isSummonKey);
    }

    public static void makeSummonMonsterFaction(CreatureEntity entityliving, boolean storeForRevert){
        if (isAIEnabled(entityliving)){
//            if (storeForRevert) storedTasks.put(entityliving.getEntityId(), new ArrayList<EntityAITasks.EntityAITaskEntry>(entityliving.targetTasks.taskEntries));
//            entityliving.targetTasks.taskEntries.clear();
//            entityliving.targetTasks.addTask(1, new EntityAIHurtByTarget(entityliving, true));
//            entityliving.targetTasks.addTask(2, new EntityAINearestAttackableTarget<PlayerEntity>(entityliving, PlayerEntity.class, true));
//            if (!entityliving.world.isRemote && entityliving.getAttackTarget() != null && entityliving.getAttackTarget() instanceof MobEntity) ArsMagica2.proxy.addDeferredTargetSet(entityliving, null);
            entityliving.getPersistentData().putBoolean(isSummonKey, true);
        }
    }

    public static void setOwner(LivingEntity entityliving, LivingEntity owner){
        if (owner == null){
            entityliving.getPersistentData().remove(summonOwnerKey);
            return;
        }
        entityliving.getPersistentData().putInt(summonOwnerKey, owner.getEntityId());
        if (entityliving instanceof CreatureEntity){
            float speed = entityliving.getAIMoveSpeed();
            if (speed <= 0) speed = 1.0f;
//            ((CreatureEntity)entityliving).tasks.addTask(1, new EntityAISummonFollowOwner((CreatureEntity)entityliving, speed, 10, 20));
        }
    }

    public static void setSummonDuration(LivingEntity entity, int duration){
        entity.getPersistentData().putInt(summonDurationKey, duration);
    }

    public static int getOwner(LivingEntity entityliving){
        if (!isSummon(entityliving)) return -1;
        return entityliving.getPersistentData().getInt(summonOwnerKey);
    }

    public static boolean revertAI(CreatureEntity entityliving){
        int ownerID = getOwner(entityliving);
        Entity owner = entityliving.world.getEntityByID(ownerID);
        if (owner instanceof LivingEntity){
//            EntityExtension.For((LivingEntity)owner).removeSummon();
//            if (EntityExtension.For((LivingEntity)owner).isManaLinkedTo(entityliving)) EntityExtension.For((LivingEntity)owner).updateManaLink(entityliving);
        }
        entityliving.getPersistentData().putBoolean(isSummonKey, false);
        setOwner(entityliving, null);
//        if (storedTasks.containsKey(entityliving.getEntityId())){
//            entityliving.targetTasks.taskEntries.clear();
//            entityliving.targetTasks.taskEntries.addAll(storedTasks.get(entityliving.getEntityId()));
//            storedTasks.remove(entityliving.getEntityId());
//            if (storedAITasks.get(entityliving.getEntityId()) != null){
//                ArrayList<EntityAITaskEntry> toRemove = new ArrayList<EntityAITaskEntry>();
//                for (Object task : entityliving.tasks.taskEntries){
//                    EntityAITaskEntry base = (EntityAITaskEntry)task;
//                    if (base.action instanceof EntityAIAttackMelee || base.action instanceof EntityAISummonFollowOwner) toRemove.add(base);
//                }
//                entityliving.tasks.taskEntries.removeAll(toRemove);
//                entityliving.tasks.taskEntries.addAll(storedAITasks.get(entityliving.getEntityId()));
//                storedAITasks.remove(entityliving.getEntityId());
//            }
//            if (!entityliving.world.isRemote && entityliving.getAttackTarget() != null) ArsMagica2.proxy.addDeferredTargetSet(entityliving, null);
//            if (entityliving instanceof TameableEntity) ((TameableEntity)entityliving).setTamed(false);
//            return true;
//        }
        return false;
    }

    public static boolean canBlockDamageSource(LivingEntity living, DamageSource damageSourceIn) {
        if (!damageSourceIn.isUnblockable()) {
            Vec3d vec3d = damageSourceIn.getDamageLocation();
            if (vec3d != null) {
                Vec3d vec3d1 = living.getLook(1.0F);
                Vec3d vec3d2 = vec3d.subtractReverse(new Vec3d(living.posX, living.posY, living.posZ)).normalize();
                vec3d2 = new Vec3d(vec3d2.getX(), 0.0D, vec3d2.getZ());
                return vec3d2.dotProduct(vec3d1) < 0.0D;
            }
        }
        return false;
    }

    public static void setSize(LivingEntity entityliving, float width, float height){
        if (entityliving.getWidth() == width && entityliving.getHeight() == height) return;
        if (ptrSetSize == null) try {
//            ptrSetSize = ReflectionHelper.findMethod(Entity.class, entityliving, new String[]{"func_70105_a", "setSize"}, Float.TYPE, Float.TYPE);
        }catch (Throwable t){
            t.printStackTrace();
        }
        else try {
            ptrSetSize.setAccessible(true);
            ptrSetSize.invoke(entityliving, width, height);
        }catch (Throwable t){
            t.printStackTrace();
        }
    }

    public static Vec3d correctLook(Vec3d vecIn, Entity entityIn) {
//        if (entityIn instanceof LivingEntity && EntityExtension.For((LivingEntity) entityIn).isInverted()) return new Vec3d(-vecIn.getX(), -vecIn.getY(), vecIn.getZ());
        return vecIn;
    }

    public static float correctEyePos(float floatIn, Entity entityIn) {
        float curHeight = floatIn;
//        if (entityIn instanceof PlayerEntity && EntityExtension.For((LivingEntity) entityIn).isInverted()) {
//            PlayerEntity player = (PlayerEntity) entityIn;
//            curHeight = player.height - floatIn - 0.1f;
//        }
//        if (entityIn instanceof LivingEntity && EntityExtension.For((LivingEntity) entityIn).shrinkAmount != 0) curHeight *= EntityExtension.For((LivingEntity) entityIn).shrinkAmount;
        return curHeight;
    }

    public static boolean correctMouvement(float strafe, float forward, float friction, Entity entityIn) {
//        if (entityIn instanceof PlayerEntity && EntityExtension.For((LivingEntity) entityIn).isInverted()) {
//            float f = strafe * strafe + forward * forward;
//            if (f >= 1.0E-4F) {
//                f = MathHelper.sqrt(f);
//                if (f < 1.0F) f = 1.0F;
//                f = friction / f;
//                strafe = strafe * f;
//                forward = forward * f;
//                float f1 = MathHelper.sin(-entityIn.rotationYaw * 0.017453292F);
//                float f2 = MathHelper.cos(-entityIn.rotationYaw * 0.017453292F);
//                entityIn.setMotion(entityIn.getMotion().getX() + strafe * f2 - forward * f1, entityIn.getMotion().getY(), entityIn.getMotion().getZ() + forward * f2 + strafe * f1);
//            }
//            return true;
//        }
        return false;
    }

//    public static void setTileSpawned(LivingEntity entityliving, TileEntitySummoner summoner){
//        entityliving.getPersistentData().putInt(summonTileXKey, summoner.getPos().getX());
//        entityliving.getPersistentData().putInt(summonTileYKey, summoner.getPos().getY());
//        entityliving.getPersistentData().putInt(summonTileZKey, summoner.getPos().getZ());
//    }

    public static void setGuardSpawnLocation(CreatureEntity entity, double x, double y, double z){
        float speed = entity.getAIMoveSpeed();
        if (speed <= 0) speed = 1.0f;
//        entity.tasks.addTask(1, new EntityAIGuardSpawnLocation(entity, speed, 3, 16, new Vec3d(x, y, z)));
    }

    public static int deductXP(int amount, PlayerEntity player) {
        int i = player.experienceTotal;
        if (amount > i) amount = i;
        player.experienceTotal -= amount;
        player.experience = 0;
        player.experienceLevel = 0;
        int addedXP = 0;
        while (addedXP < player.experienceTotal) {
            int toAdd = player.experienceTotal - addedXP;
            toAdd = Math.min(toAdd, player.xpBarCap());
            player.experience = toAdd / (float)player.xpBarCap();
            if (player.experience == 1f) {
                player.experienceLevel++;
                player.experience = 0;
            }
            addedXP += toAdd;
        }
        return amount;
    }
}
