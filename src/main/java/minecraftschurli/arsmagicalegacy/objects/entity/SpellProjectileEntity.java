package minecraftschurli.arsmagicalegacy.objects.entity;

import java.util.List;
import javax.annotation.Nonnull;
import minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import minecraftschurli.arsmagicalegacy.api.spell.SpellModifiers;
import minecraftschurli.arsmagicalegacy.init.ModEntities;
import minecraftschurli.arsmagicalegacy.util.SpellUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.play.server.SSpawnObjectPacket;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

/**
 * @author Minecraftschurli
 * @version 2019-11-17
 */
public final class SpellProjectileEntity extends Entity {
    private static final DataParameter<Boolean> HOMING = EntityDataManager.createKey(SpellProjectileEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> NON_SOLID = EntityDataManager.createKey(SpellProjectileEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Integer> BOUNCES = EntityDataManager.createKey(SpellProjectileEntity.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> COLOR = EntityDataManager.createKey(SpellProjectileEntity.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> CURRENT_PIERCES = EntityDataManager.createKey(SpellProjectileEntity.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> HOMING_TARGET = EntityDataManager.createKey(SpellProjectileEntity.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> PIERCING = EntityDataManager.createKey(SpellProjectileEntity.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> OWNER = EntityDataManager.createKey(SpellProjectileEntity.class, DataSerializers.VARINT);
    private static final DataParameter<Float> GRAVITY = EntityDataManager.createKey(SpellProjectileEntity.class, DataSerializers.FLOAT);
    private static final DataParameter<String> ICON = EntityDataManager.createKey(SpellProjectileEntity.class, DataSerializers.STRING);
    private static final DataParameter<ItemStack> STACK = EntityDataManager.createKey(SpellProjectileEntity.class, DataSerializers.ITEMSTACK);

    public SpellProjectileEntity(World world) {
        this(ModEntities.SPELL_PROJECTILE.get(), world);
    }

    public SpellProjectileEntity(EntityType<Entity> type, World world) {
        super(type, world);
    }

    @Override
    protected void readAdditional(CompoundNBT nbt) {
        CompoundNBT tag = nbt.getCompound(ArsMagicaAPI.MODID);
        dataManager.set(HOMING, tag.getBoolean("Homing"));
        dataManager.set(NON_SOLID, tag.getBoolean("NonSolid"));
        dataManager.set(BOUNCES, tag.getInt("Bounces"));
        dataManager.set(COLOR, tag.getInt("Color"));
        dataManager.set(CURRENT_PIERCES, tag.getInt("CurrentPierces"));
        dataManager.set(HOMING_TARGET, tag.getInt("HomingTarget"));
        dataManager.set(OWNER, tag.getInt("Owner"));
        dataManager.set(PIERCING, tag.getInt("Piercing"));
        dataManager.set(GRAVITY, tag.getFloat("Gravity"));
        dataManager.set(ICON, tag.getString("Icon"));
        dataManager.set(STACK, ItemStack.read(tag.getCompound("Stack")));
    }

    @Override
    protected void writeAdditional(CompoundNBT nbt) {
        CompoundNBT tag = nbt.getCompound(ArsMagicaAPI.MODID);
        tag.putBoolean("Homing", dataManager.get(HOMING));
        tag.putBoolean("NonSolid", dataManager.get(NON_SOLID));
        tag.putInt("Bounces", dataManager.get(BOUNCES));
        tag.putInt("Color", dataManager.get(COLOR));
        tag.putInt("CurrentPierces", dataManager.get(CURRENT_PIERCES));
        tag.putInt("HomingTarget", dataManager.get(HOMING_TARGET));
        tag.putInt("Owner", dataManager.get(OWNER));
        tag.putInt("Piercing", dataManager.get(PIERCING));
        tag.putFloat("Gravity", dataManager.get(GRAVITY));
        tag.putString("Icon", dataManager.get(ICON));
        CompoundNBT tmp = new CompoundNBT();
        dataManager.get(STACK).write(tmp);
        tag.put("Stack", tmp);
    }

    @Nonnull
    @Override
    public IPacket<?> createSpawnPacket() {
        return new SSpawnObjectPacket(this, getOwner() == null ? 0 : getOwner().getEntityId());
    }

    @Override
    protected void registerData() {
        getDataManager().register(HOMING, false);
        getDataManager().register(NON_SOLID, false);
        getDataManager().register(BOUNCES, 0);
        getDataManager().register(COLOR, 0xFFFFFF);
        getDataManager().register(CURRENT_PIERCES, 0);
        getDataManager().register(HOMING_TARGET, -1);
        getDataManager().register(OWNER, 0);
        getDataManager().register(PIERCING, 0);
        getDataManager().register(GRAVITY, 0f);
        getDataManager().register(ICON, "arcane");
        getDataManager().register(STACK, ItemStack.EMPTY);
    }

    @Override
    public void tick() {
        try {
            if (ticksExisted > 200)
                remove();
            RayTraceResult mop = ProjectileHelper.rayTrace(this, true, false, getOwner(), RayTraceContext.BlockMode.COLLIDER);
            if (mop.getType().equals(RayTraceResult.Type.BLOCK)) {
                if (world.getBlockState(((BlockRayTraceResult) mop).getPos()).isSolid() || targetWater()) {
                    world.getBlockState(((BlockRayTraceResult) mop).getPos()).onEntityCollision(world, ((BlockRayTraceResult) mop).getPos(), this);
                    if (getBounces() > 0) {
                        Direction face = ((BlockRayTraceResult) mop).getFace();
                        double projectileSpeed = SpellUtil.modifyDoubleMul(1, getStack(), getOwner(), null, world, SpellModifiers.VELOCITY_ADDED);
                        double newMotionX = getMotion().x / projectileSpeed;
                        double newMotionY = getMotion().y / projectileSpeed;
                        double newMotionZ = getMotion().z / projectileSpeed;
                        if (face.equals(Direction.UP) || face.equals(Direction.DOWN)) newMotionY = -newMotionY;
                        else if (face.equals(Direction.NORTH) || face.equals(Direction.SOUTH)) newMotionZ = -newMotionZ;
                        else if (face.equals(Direction.EAST) || face.equals(Direction.WEST)) newMotionX = -newMotionX;
                        setMotion(newMotionX * projectileSpeed, newMotionY * projectileSpeed, newMotionZ * projectileSpeed);
                        decreaseBounces();
                    } else {
                        SpellUtil.applyStageBlock(getStack(), getOwner(), world, ((BlockRayTraceResult) mop).getPos(), ((BlockRayTraceResult) mop).getFace(), getPosX(), getPosY(), getPosZ(), true);
                        SpellUtil.applyStage(getStack(), getOwner(), null, mop.getHitVec().x + getMotion().x, mop.getHitVec().y + getMotion().y, mop.getHitVec().z + getMotion().z, ((BlockRayTraceResult) mop).getFace(), world, false, true, 0);
                        if (getPierces() == 1 || !SpellUtil.hasModifier(SpellModifiers.PIERCING, getStack()))
                            remove();
                        else setCurrentPierces(getCurrentPierces() + 1);
                    }
                }
            } else {
                List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(this, getBoundingBox().grow(getMotion().x, getMotion().y, getMotion().z).expand(0.25D, 0.25D, 0.25D));
                int effSize = list.size();
                for (Entity entity : list) {
                    if (entity instanceof LivingEntity) {
                        if (entity.equals(getOwner())) {
                            effSize--;
                            continue;
                        }
                        SpellUtil.applyStageEntity(getStack(), getOwner(), world, entity, true);
                        SpellUtil.applyStage(getStack(), getOwner(), (LivingEntity) entity, entity.getPosX(), entity.getPosY(), entity.getPosZ(), null, world, false, true, 0);
                        break;
                    } else effSize--;
                }
                if (effSize != 0) {
                    if (getPierces() == 1 || !SpellUtil.hasModifier(SpellModifiers.PIERCING, getStack()))
                        remove();
                    else setCurrentPierces(getCurrentPierces() + 1);
                }
            }
            setMotion(getMotion().x, getMotion().y + getDataManager().get(GRAVITY), getMotion().z);
            Vec3d newPos = getPositionVec().add(getMotion());
            setPosition(newPos.x, newPos.y, newPos.z);
        } catch (NullPointerException e) {
            remove();
        }
    }

    public void decreaseBounces() {
        setBounces(getBounces() - 1);
    }

    public int getBounces() {
        return getDataManager().get(BOUNCES);
    }

    public void setBounces(int bounces) {
        getDataManager().set(BOUNCES, bounces);
    }

    public int getColor() {
        return getDataManager().get(COLOR);
    }

    public int getCurrentPierces() {
        return getDataManager().get(CURRENT_PIERCES);
    }

    public void setCurrentPierces(int pierces) {
        getDataManager().set(CURRENT_PIERCES, pierces);
    }

    public String getIcon() {
        return getDataManager().get(ICON);
    }

    public void setIcon(String icon) {
        getDataManager().set(ICON, icon);
    }

    public int getPierces() {
        return getDataManager().get(PIERCING) - getCurrentPierces();
    }

    public void setPierces(int pierces) {
        getDataManager().set(PIERCING, pierces);
        setCurrentPierces(0);
    }

    public LivingEntity getOwner() {
        try {
            return (LivingEntity) world.getEntityByID(getDataManager().get(OWNER));
        } catch (RuntimeException e) {
            remove();
            return null;
        }
    }

    public void setOwner(LivingEntity owner) {
        getDataManager().set(OWNER, owner.getEntityId());
    }

    public ItemStack getStack() {
        return getDataManager().get(STACK);
    }

    public void setStack(ItemStack stack) {
        getDataManager().set(STACK, stack);
        /*Affinity mainAff = AffinityShiftUtils.getMainShiftForStack(stack);
        if (mainAff.equals(Affinity.ENDER)) getDataManager().set(COLOR, 0x550055);
        else if (mainAff.equals(Affinity.ICE)) getDataManager().set(COLOR, 0x2299FF);
        else if (mainAff.equals(Affinity.LIFE)) getDataManager().set(COLOR, 0x22FF44);*/
    }

    public void setGravity(float gravity) {
        getDataManager().set(GRAVITY, gravity);
    }

    public void setHoming(boolean homing) {
        getDataManager().set(HOMING, homing);
    }

    public void setTargetNonSolid() {
        if (!world.isRemote)
            getDataManager().set(NON_SOLID, true);
    }

    public boolean targetWater() {
        return getDataManager().get(NON_SOLID);
    }
}
