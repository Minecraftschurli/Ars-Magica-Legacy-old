package minecraftschurli.arsmagicalegacy.objects.entity;

import minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import minecraftschurli.arsmagicalegacy.api.spell.SpellModifiers;
import minecraftschurli.arsmagicalegacy.init.ModEntities;
import minecraftschurli.arsmagicalegacy.util.SpellUtils;
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

import java.util.List;

/**
 * @author Minecraftschurli
 * @version 2019-11-17
 */
public class SpellProjectileEntity extends Entity {
    private static final DataParameter<Integer> DW_BOUNCE_COUNTER = EntityDataManager.createKey(SpellProjectileEntity.class, DataSerializers.VARINT);
    private static final DataParameter<Float> DW_GRAVITY = EntityDataManager.createKey(SpellProjectileEntity.class, DataSerializers.FLOAT);
    private static final DataParameter<ItemStack> DW_EFFECT = EntityDataManager.createKey(SpellProjectileEntity.class, DataSerializers.ITEMSTACK);
    private static final DataParameter<String> DW_ICON_NAME = EntityDataManager.createKey(SpellProjectileEntity.class, DataSerializers.STRING);
    private static final DataParameter<Integer> DW_PIERCE_COUNT = EntityDataManager.createKey(SpellProjectileEntity.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> DW_COLOR = EntityDataManager.createKey(SpellProjectileEntity.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> DW_SHOOTER = EntityDataManager.createKey(SpellProjectileEntity.class, DataSerializers.VARINT);
    private static final DataParameter<Boolean> DW_TARGETGRASS = EntityDataManager.createKey(SpellProjectileEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> DW_HOMING = EntityDataManager.createKey(SpellProjectileEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Integer> DW_HOMING_TARGET = EntityDataManager.createKey(SpellProjectileEntity.class, DataSerializers.VARINT);

    private int currentPierces;

    public SpellProjectileEntity(World worldIn) {
        this(ModEntities.SPELL_PROJECTILE.get(), worldIn);
    }

    public SpellProjectileEntity(EntityType<Entity> entityEntityType, World worldIn) {
        super(entityEntityType, worldIn);
    }

    public void setTargetWater() {
        if (!this.world.isRemote)
            this.getDataManager().set(DW_TARGETGRASS, true);
    }

    public boolean targetWater() {
        return this.getDataManager().get(DW_TARGETGRASS);
    }

    @Override
    protected void registerData() {
        this.getDataManager().register(DW_BOUNCE_COUNTER, 0);
        this.getDataManager().register(DW_GRAVITY, 0.f);
        this.getDataManager().register(DW_EFFECT, ItemStack.EMPTY);
        this.getDataManager().register(DW_ICON_NAME, "arcane");
        this.getDataManager().register(DW_PIERCE_COUNT, 0);
        this.getDataManager().register(DW_COLOR, 0xFFFFFF);
        this.getDataManager().register(DW_SHOOTER, 0);
        this.getDataManager().register(DW_TARGETGRASS, false);
        this.getDataManager().register(DW_HOMING, false);
        this.getDataManager().register(DW_HOMING_TARGET, -1);
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        Entity entity = getShooter();
        return new SSpawnObjectPacket(this, entity == null ? 0 : entity.getEntityId());
    }

    public void decreaseBounces() {
        setBounces(getBounces() - 1);
    }

    public int getBounces() {
        return this.getDataManager().get(DW_BOUNCE_COUNTER);
    }

    public void setBounces(int projectileBounce) {
        this.getDataManager().set(DW_BOUNCE_COUNTER, projectileBounce);
    }

    public int getPierces() {
        return this.getDataManager().get(DW_PIERCE_COUNT) - this.currentPierces;
    }

    public ItemStack getSpell() {
        return this.getDataManager().get(DW_EFFECT);
    }

    public void setSpell(ItemStack stack) {
        this.getDataManager().set(DW_EFFECT, stack);
        /*Affinity mainAff = AffinityShiftUtils.getMainShiftForStack(stack);
        if (mainAff.equals(Affinity.ENDER)) this.getDataManager().set(DW_COLOR, 0x550055);
        else if (mainAff.equals(Affinity.ICE)) this.getDataManager().set(DW_COLOR, 0x2299FF);
        else if (mainAff.equals(Affinity.LIFE)) this.getDataManager().set(DW_COLOR, 0x22FF44);*/
    }

    public void bounce(Direction facing) {
        if (facing == null) {
            this.setMotion(this.getMotion().inverse());
        } else {
            double projectileSpeed = SpellUtils.getModifiedDoubleMul(1, getSpell(), getShooter(), null, world, SpellModifiers.VELOCITY_ADDED);
            double newMotionX = getMotion().x / projectileSpeed;
            double newMotionY = getMotion().y / projectileSpeed;
            double newMotionZ = getMotion().z / projectileSpeed;
            if (facing.equals(Direction.UP) || facing.equals(Direction.DOWN)) {
                newMotionY = -newMotionY;
            } else if (facing.equals(Direction.NORTH) || facing.equals(Direction.SOUTH)) {
                newMotionZ = -newMotionZ;
            } else if (facing.equals(Direction.EAST) || facing.equals(Direction.WEST)) {
                newMotionX = -newMotionX;
            }
            setMotion(newMotionX * projectileSpeed, newMotionY * projectileSpeed, newMotionZ * projectileSpeed);
        }
        decreaseBounces();
    }

    @Override
    public void tick() {
        try {
            if (ticksExisted > 200)
                this.remove();
            RayTraceResult mop = ProjectileHelper.rayTrace(this, true, false, getShooter(), RayTraceContext.BlockMode.COLLIDER);
            if (mop.getType().equals(RayTraceResult.Type.BLOCK)) {
                if (world.getBlockState(((BlockRayTraceResult) mop).getPos()).isSolid() || targetWater()) {
                    world.getBlockState(((BlockRayTraceResult) mop).getPos()).onEntityCollision(world, ((BlockRayTraceResult) mop).getPos(), this);
                    if (getBounces() > 0) {
                        bounce(((BlockRayTraceResult) mop).getFace());
                    } else {
                        SpellUtils.applyStageToGround(getSpell(), getShooter(), world, ((BlockRayTraceResult) mop).getPos(), ((BlockRayTraceResult) mop).getFace(), getPosX(), getPosY(), getPosZ(), true);
                        SpellUtils.applyStackStage(getSpell(), getShooter(), null, mop.getHitVec().x + getMotion().x, mop.getHitVec().y + getMotion().y, mop.getHitVec().z + getMotion().z, ((BlockRayTraceResult) mop).getFace(), world, false, true, 0);
                        if (this.getPierces() == 1 || !SpellUtils.modifierIsPresent(SpellModifiers.PIERCING, this.getSpell()))
                            this.remove();
                        else
                            this.currentPierces++;
                    }
                }
            } else {
                List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(this, getBoundingBox().grow(getMotion().x, getMotion().y, getMotion().z).expand(0.25D, 0.25D, 0.25D));
                int effSize = list.size();
                for (Entity entity : list) {
                    if (entity instanceof LivingEntity) {
                        if (entity.equals(getShooter())) {
                            effSize--;
                            continue;
                        }
                        SpellUtils.applyStageToEntity(getSpell(), getShooter(), world, entity, true);
                        SpellUtils.applyStackStage(getSpell(), getShooter(), (LivingEntity) entity, entity.getPosX(), entity.getPosY(), entity.getPosZ(), null, world, false, true, 0);
                        break;
                    } else {
                        effSize--;
                    }
                }
                if (effSize != 0) {
                    if (this.getPierces() == 1 || !SpellUtils.modifierIsPresent(SpellModifiers.PIERCING, this.getSpell()))
                        this.remove();
                    else
                        this.currentPierces++;
                }
            }
            setMotion(getMotion().x, getMotion().y + this.getDataManager().get(DW_GRAVITY), getMotion().z);
            Vec3d newPos = getPositionVec().add(getMotion());
            setPosition(newPos.x, newPos.y, newPos.z);
        } catch (NullPointerException e) {
            this.remove();
        }
    }

    public LivingEntity getShooter() {
        try {
            return (LivingEntity) world.getEntityByID(this.getDataManager().get(DW_SHOOTER));
        } catch (RuntimeException e) {
            this.remove();
            return null;
        }
    }

    public void setShooter(LivingEntity living) {
        this.getDataManager().set(DW_SHOOTER, living.getEntityId());
    }

    @Override
    protected void readAdditional(CompoundNBT tagCompound) {
        CompoundNBT am2Tag = tagCompound.getCompound(ArsMagicaAPI.MODID);
        dataManager.set(DW_BOUNCE_COUNTER, am2Tag.getInt("BounceCount"));
        dataManager.set(DW_GRAVITY, am2Tag.getFloat("Gravity"));
        dataManager.set(DW_EFFECT, ItemStack.read(am2Tag.getCompound("Effect")));
        dataManager.set(DW_ICON_NAME, am2Tag.getString("IconName"));
        dataManager.set(DW_PIERCE_COUNT, am2Tag.getInt("PierceCount"));
        dataManager.set(DW_COLOR, am2Tag.getInt("Color"));
        dataManager.set(DW_SHOOTER, am2Tag.getInt("Shooter"));
        dataManager.set(DW_TARGETGRASS, am2Tag.getBoolean("TargetGrass"));
        dataManager.set(DW_HOMING, am2Tag.getBoolean("Homing"));
        dataManager.set(DW_HOMING_TARGET, am2Tag.getInt("HomingTarget"));
    }

    @Override
    protected void writeAdditional(CompoundNBT tagCompound) {
        CompoundNBT am2Tag = tagCompound.getCompound(ArsMagicaAPI.MODID);
        am2Tag.putInt("BounceCount", dataManager.get(DW_BOUNCE_COUNTER));
        am2Tag.putFloat("Gravity", dataManager.get(DW_GRAVITY));
        CompoundNBT tmp = new CompoundNBT();
        dataManager.get(DW_EFFECT).write(tmp);
        am2Tag.put("Effect", tmp);
        am2Tag.putString("IconName", dataManager.get(DW_ICON_NAME));
        am2Tag.putInt("PierceCount", dataManager.get(DW_PIERCE_COUNT));

        am2Tag.putInt("Color", dataManager.get(DW_COLOR));
        am2Tag.putInt("Shooter", dataManager.get(DW_SHOOTER));
        am2Tag.putBoolean("TargetGrass", dataManager.get(DW_TARGETGRASS));
        am2Tag.putBoolean("Homing", dataManager.get(DW_HOMING));
        am2Tag.putInt("HomingTarget", dataManager.get(DW_HOMING_TARGET));
    }

    public void selectHomingTarget() {
        List<Entity> entities = world.getEntitiesWithinAABBExcludingEntity(this, this.getCollisionBoundingBox().expand(10, 10, 10));
        Vec3d pos = new Vec3d(getPosX(), getPosY(), getPosZ());
        LivingEntity target = null;
        double dist = 900;
        for (Entity entity : entities) {
            if (entity instanceof LivingEntity && !entity.equals(getShooter())) {
                Vec3d ePos = new Vec3d(entity.getPosX(), entity.getPosY(), entity.getPosZ());
                double eDist = pos.distanceTo(ePos);
                if (eDist < dist) {
                    dist = eDist;
                    target = (LivingEntity) entity;
                }
            }
        }

        if (target != null) {
            this.getDataManager().set(DW_HOMING_TARGET, target.getEntityId());
        }
    }

    public LivingEntity getHomingTarget() {
        return (LivingEntity) world.getEntityByID(this.getDataManager().get(DW_HOMING_TARGET));
    }

    public void setGravity(float projectileGravity) {
        this.getDataManager().set(DW_GRAVITY, projectileGravity);
    }

    public void setNumPierces(int pierces) {
        this.getDataManager().set(DW_PIERCE_COUNT, pierces);
        this.currentPierces = 0;
    }

    public void setHoming(boolean homing) {
        this.getDataManager().set(DW_HOMING, homing);
    }

    public String getIcon() {
        return this.getDataManager().get(DW_ICON_NAME);
    }

    public void setIcon(String icon) {
        this.getDataManager().set(DW_ICON_NAME, icon);
    }

    public int getColor() {
        return this.getDataManager().get(DW_COLOR);
    }
}
