package minecraftschurli.arsmagicalegacy.objects.entity;

import java.util.ArrayList;
import javax.annotation.Nonnull;
import minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import minecraftschurli.arsmagicalegacy.init.ModEntities;
import minecraftschurli.arsmagicalegacy.util.SpellUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.dragon.EnderDragonPartEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.play.server.SSpawnObjectPacket;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public final class ZoneEntity extends Entity {
    private static final DataParameter<Boolean> FIRST = EntityDataManager.createKey(ZoneEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Integer> EFFECT = EntityDataManager.createKey(ZoneEntity.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> OWNER = EntityDataManager.createKey(ZoneEntity.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> TICKS = EntityDataManager.createKey(ZoneEntity.class, DataSerializers.VARINT);
    private static final DataParameter<Float> DAMAGE = EntityDataManager.createKey(ZoneEntity.class, DataSerializers.FLOAT);
    private static final DataParameter<Float> GRAVITY = EntityDataManager.createKey(ZoneEntity.class, DataSerializers.FLOAT);
    private static final DataParameter<Float> RADIUS = EntityDataManager.createKey(ZoneEntity.class, DataSerializers.FLOAT);
    private static final DataParameter<ItemStack> STACK = EntityDataManager.createKey(ZoneEntity.class, DataSerializers.ITEMSTACK);

    public ZoneEntity(World world) {
        this(ModEntities.WALL.get(), world);
    }

    public ZoneEntity(EntityType<?> type, World world) {
        super(type, world);
        setBoundingBox(new AxisAlignedBB(getPosX() - 0.25, getPosY() - 0.25, getPosZ() - 0.25, getPosX() + 0.25, getPosY() + 0.25, getPosZ() + 0.25));
    }

    @Override
    protected void readAdditional(CompoundNBT nbt) {
        CompoundNBT tag = nbt.getCompound(ArsMagicaAPI.MODID);
        dataManager.set(FIRST, tag.getBoolean("First"));
        dataManager.set(EFFECT, tag.getInt("Effect"));
        dataManager.set(OWNER, tag.getInt("Owner"));
        dataManager.set(TICKS, tag.getInt("Ticks"));
        dataManager.set(DAMAGE, tag.getFloat("Damage"));
        dataManager.set(GRAVITY, tag.getFloat("Gravity"));
        dataManager.set(RADIUS, tag.getFloat("Radius"));
        dataManager.set(STACK, ItemStack.read(tag.getCompound("Stack")));
    }

    @Override
    protected void writeAdditional(CompoundNBT nbt) {
        CompoundNBT tag = nbt.getCompound(ArsMagicaAPI.MODID);
        tag.putBoolean("First", dataManager.get(FIRST));
        tag.putInt("Effect", dataManager.get(EFFECT));
        tag.putInt("Owner", dataManager.get(OWNER));
        tag.putInt("Ticks", dataManager.get(TICKS));
        tag.putFloat("Damage", dataManager.get(DAMAGE));
        tag.putFloat("Gravity", dataManager.get(GRAVITY));
        tag.putFloat("Radius", dataManager.get(RADIUS));
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
        dataManager.register(FIRST, true);
        dataManager.register(EFFECT, 20);
        dataManager.register(OWNER, 0);
        dataManager.register(TICKS, 100);
        dataManager.register(DAMAGE, 1f);
        dataManager.register(GRAVITY, 0f);
        dataManager.register(RADIUS, 3f);
        dataManager.register(STACK, ItemStack.EMPTY);
    }

    @Override
    public void tick() {
        if (world.isRemote) {
//            rot += 10;
//            rot %= 360;
//            double dist = dataManager.get(RADIUS_DATA);
//            if (spell == null) spell = dataManager.get(STACK_DATA);
//            int color = 0xFFFFFF;
//            if (SpellUtils.hasModifier(SpellModifiers.COLOR, spell)) for (SpellModifier mod : SpellUtils.getModifiers(spell, -1)) if (mod instanceof Color) color = (int) mod.getModifier(SpellModifiers.COLOR, null, null, null, spell.getTag());
//            if (ticksExisted % 8 == 0) {
//                for (int i = 0; i < 4; ++i) {
//                    double x = getPosX() - Math.cos(3.141 / 180 * (rot + 90 * i) % 360) * dist;
//                    double z = getPosZ() - Math.sin(3.141 / 180 * (rot + 90 * i) % 360) * dist;
//                    AMParticle effect = (AMParticle) ArsMagica2.proxy.particleManager.spawn(world, AMParticleDefs.getParticleForAffinity(AffinityShiftUtils.getMainShiftForStack(spell)), x, getPosY(), z);
//                    if (effect != null) {
//                        effect.setIgnoreMaxAge(false);
//                        effect.setMaxAge(20);
//                        effect.setParticleScale(0.15f);
//                        effect.setRGBColorI(color);
//                        effect.AddParticleController(new ParticleFloatUpward(effect, 0, 0.07f, 1, false));
//                        if (ArsMagica2.config.LowGFX()) effect.AddParticleController(new ParticleOrbitPoint(effect, getPosX(), getPosY(), getPosZ(), 2, false).setIgnoreYCoordinate(true).SetOrbitSpeed(0.05f).SetTargetDistance(dist).setRotateDirection(true));
//                    }
//                }
//            }
        }
        moveForced(0, dataManager.get(GRAVITY), 0);
        dataManager.set(EFFECT, dataManager.get(EFFECT) - 1);
        if (dataManager.get(EFFECT) <= 0) {
            dataManager.set(EFFECT, 20);
            for (Entity e : world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(getPosX() - dataManager.get(RADIUS), getPosY() - 3, getPosZ() - dataManager.get(RADIUS), getPosX() + dataManager.get(RADIUS), getPosY() + 3, getPosZ() + dataManager.get(RADIUS)))) {
                if (e == this || e == getOwner()) continue;
                if (e instanceof EnderDragonPartEntity && ((EnderDragonPartEntity) e).dragon != null)
                    e = ((EnderDragonPartEntity) e).dragon;
                if (e instanceof LivingEntity)
                    SpellUtil.applyStageEntity(dataManager.get(STACK), getOwner(), world, e, false);
                SpellUtil.applyStage(dataManager.get(STACK), getOwner(), (LivingEntity) e, e.getPosX(), e.getPosY() - 1, e.getPosZ(), null, world, false, false, ticksExisted);
            }
            if (dataManager.get(GRAVITY) < 0 && !dataManager.get(FIRST))
                SpellUtil.applyStage(dataManager.get(STACK), getOwner(), null, getPosX(), getPosY() - 1, getPosZ(), null, world, false, false, ticksExisted);
            else
                SpellUtil.applyStage(dataManager.get(STACK), getOwner(), null, getPosX(), getPosY(), getPosZ(), null, world, false, false, ticksExisted);
            dataManager.set(FIRST, false);
            for (float i = -dataManager.get(RADIUS); i <= dataManager.get(RADIUS); i++) {
                for (int j = -3; j <= 3; j++) {
                    Vec3d a = new Vec3d(getPosX() + i, getPosY() + j, getPosZ() - dataManager.get(RADIUS));
                    Vec3d b = new Vec3d(getPosX() + i, getPosY() + j, getPosZ() + dataManager.get(RADIUS));
                    double stepX = a.x < b.x ? 0.2f : -0.2f;
                    double stepZ = a.z < b.z ? 0.2f : -0.2f;
                    ArrayList<Vec3d> vecs = new ArrayList<>();
                    Vec3d curPos = new Vec3d(a.x, a.y, a.z);
                    for (int k = 0; k < getHeight(); k++) vecs.add(new Vec3d(curPos.x, curPos.y + k, curPos.z));
                    while (stepX != 0 || stepZ != 0) {
                        if ((stepX < 0 && curPos.x <= b.x) || (stepX > 0 && curPos.x >= b.x))
                            stepX = 0;
                        if ((stepZ < 0 && curPos.z <= b.z) || (stepZ > 0 && curPos.z >= b.z))
                            stepZ = 0;
                        curPos = new Vec3d(curPos.x + stepX, curPos.y, curPos.z + stepZ);
                        Vec3d tempPos = curPos.add(Vec3d.ZERO);
                        if (!vecs.contains(tempPos)) for (int k = 0; k < getHeight(); k++)
                            vecs.add(new Vec3d(tempPos.x, tempPos.y + k, tempPos.z));
                    }
                    for (Vec3d vec : vecs)
                        SpellUtil.applyStageBlock(dataManager.get(STACK), getOwner(), world, new BlockPos(vec), Direction.UP, vec.x + 0.5, vec.y + 0.5, vec.z + 0.5, false);
                }
            }
        }
        if (!world.isRemote && ticksExisted >= dataManager.get(TICKS)) remove();
    }

    public LivingEntity getOwner() {
        try {
            return (LivingEntity) world.getEntityByID(getDataManager().get(OWNER));
        } catch (RuntimeException e) {
            remove();
            return null;
        }
    }

    public void setGravity(float gravity) {
        dataManager.set(GRAVITY, gravity);
    }

    public void setOwner(LivingEntity owner) {
        dataManager.set(OWNER, owner.getEntityId());
    }

    public void setRadius(float radius) {
        dataManager.set(RADIUS, radius);
    }

    public void setStack(ItemStack stack) {
        dataManager.set(STACK, stack);
    }

    public void setTicks(int ticks) {
        dataManager.set(TICKS, ticks);
    }
}
