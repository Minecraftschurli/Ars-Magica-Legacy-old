package minecraftschurli.arsmagicalegacy.util;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;

public final class SummonUtils {
    private static final String IS_SUMMON = "Is_Summon";
    private static final String SUMMON_DURATION = "Summon_Duration";
    private static final String SUMMON_ENTITY_IDS = "Summon_Entity_IDs";
    private static final String SUMMON_OWNER = "Summon_Owner";
    private static final String SUMMON_X = "Summon_X";
    private static final String SUMMON_Y = "Summon_Y";
    private static final String SUMMON_Z = "Summon_Z";

    public static int getOwner(LivingEntity entityliving) {
        if (!isSummon(entityliving)) return -1;
        return entityliving.getPersistentData().getInt(SUMMON_OWNER);
    }

    public static boolean isSummon(LivingEntity entityliving) {
        return entityliving.getPersistentData().getBoolean(IS_SUMMON);
    }

    public static void makeSummonMonsterFaction(CreatureEntity entityliving, boolean storeForRevert) {
        if (!entityliving.isAIDisabled()) {
//            if (storeForRevert) storedTasks.put(entityliving.getEntityId(), new ArrayList<EntityAITasks.EntityAITaskEntry>(entityliving.targetTasks.taskEntries));
//            entityliving.targetTasks.taskEntries.clear();
//            entityliving.targetTasks.addTask(1, new EntityAIHurtByTarget(entityliving, true));
//            entityliving.targetTasks.addTask(2, new EntityAINearestAttackableTarget<PlayerEntity>(entityliving, PlayerEntity.class, true));
//            if (!entityliving.world.isRemote && entityliving.getAttackTarget() != null && entityliving.getAttackTarget() instanceof MobEntity) ArsMagica2.proxy.addDeferredTargetSet(entityliving, null);
            entityliving.getPersistentData().putBoolean(IS_SUMMON, true);
        }
    }

    public static void makeSummonPlayerFaction(CreatureEntity entityliving, PlayerEntity player, boolean storeForRevert) {
//        if (isAIEnabled(entityliving) && EntityExtension.For(player).getCurrentSummons() < EntityExtension.For(player).getMaxSummons()){
//            if (storeForRevert) storedTasks.put(entityliving.getEntityId(), new ArrayList<EntityAITasks.EntityAITaskEntry>(entityliving.targetTasks.taskEntries));
//        boolean addMeleeAttack = false;
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
//        if (addMeleeAttack) {
//            float speed = entityliving.getAIMoveSpeed();
//            if (speed <= 0) speed = 1;
//            entityliving.addTask(3, new EntityAIAttackMelee(entityliving, speed, true));
//        }
//        entityliving.targetTasks.taskEntries.clear();
//        entityliving.targetTasks.addTask(1, new EntityAIHurtByTarget(entityliving, true));
//        entityliving.targetTasks.addTask(2, new EntityAINearestAttackableTarget<MobEntity>(entityliving, MobEntity.class, 0, true, false, SummonEntitySelector.instance));
//        entityliving.targetTasks.addTask(2, new EntityAINearestAttackableTarget<SlimeEntity>(entityliving, SlimeEntity.class, true));
//        entityliving.targetTasks.addTask(2, new EntityAINearestAttackableTarget<GhastEntity>(entityliving, GhastEntity.class, true));
//        entityliving.targetTasks.addTask(2, new EntityAINearestAttackableTarget<ShulkerEntity>(entityliving, ShulkerEntity.class, true));
//        if (!entityliving.world.isRemote && entityliving.getAttackTarget() != null && entityliving.getAttackTarget() instanceof PlayerEntity) ArsMagica2.proxy.addDeferredTargetSet(entityliving, null);
        if (entityliving instanceof TameableEntity) {
            ((TameableEntity) entityliving).setTamed(true);
            ((TameableEntity) entityliving).setOwnerId(player.getUniqueID());
        }
//        entityliving.getPersistentData().putBoolean(IS_SUMMON, true);
//        EntityExtension.For(player).addSummon(entityliving);
//        }
    }

    public static boolean revertAI(CreatureEntity entityliving) {
//        int ownerID = getOwner(entityliving);
//        Entity owner = entityliving.world.getEntityByID(ownerID);
//        if (owner instanceof LivingEntity) {
//            EntityExtension.For((LivingEntity)owner).removeSummon();
//            if (EntityExtension.For((LivingEntity)owner).isManaLinkedTo(entityliving)) EntityExtension.For((LivingEntity)owner).updateManaLink(entityliving);
//        }
//        entityliving.getPersistentData().putBoolean(IS_SUMMON, false);
//        setOwner(entityliving, null);
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

    public static void setOwner(LivingEntity entityliving, LivingEntity owner) {
        if (owner == null) {
            entityliving.getPersistentData().remove(SUMMON_OWNER);
            return;
        }
        entityliving.getPersistentData().putInt(SUMMON_OWNER, owner.getEntityId());
//        if (entityliving instanceof CreatureEntity) {
//            float speed = entityliving.getAIMoveSpeed();
//            if (speed <= 0) speed = 1;
//            ((CreatureEntity)entityliving).tasks.addTask(1, new EntityAISummonFollowOwner((CreatureEntity)entityliving, speed, 10, 20));
//        }
    }

    public static void setSummonDuration(LivingEntity entity, int duration) {
        entity.getPersistentData().putInt(SUMMON_DURATION, duration);
    }
}
