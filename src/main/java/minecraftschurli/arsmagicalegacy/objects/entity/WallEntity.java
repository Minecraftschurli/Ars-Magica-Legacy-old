package minecraftschurli.arsmagicalegacy.objects.entity;

import javax.annotation.Nonnull;
import minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import minecraftschurli.arsmagicalegacy.init.ModEntities;
import minecraftschurli.arsmagicalegacy.util.RenderUtil;
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
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public final class WallEntity extends Entity {
    private static final DataParameter<Integer> EFFECT = EntityDataManager.createKey(WallEntity.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> OWNER = EntityDataManager.createKey(WallEntity.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> TICKS = EntityDataManager.createKey(WallEntity.class, DataSerializers.VARINT);
    private static final DataParameter<Float> DAMAGE = EntityDataManager.createKey(WallEntity.class, DataSerializers.FLOAT);
    private static final DataParameter<Float> GRAVITY = EntityDataManager.createKey(WallEntity.class, DataSerializers.FLOAT);
    private static final DataParameter<Float> RADIUS = EntityDataManager.createKey(WallEntity.class, DataSerializers.FLOAT);
    private static final DataParameter<ItemStack> STACK = EntityDataManager.createKey(WallEntity.class, DataSerializers.ITEMSTACK);

    public WallEntity(World world) {
        this(ModEntities.WALL.get(), world);
    }

    public WallEntity(EntityType<?> type, World world) {
        super(type, world);
        setBoundingBox(new AxisAlignedBB(getPosX() - 0.25, getPosY() - 0.25, getPosZ() - 0.25, getPosX() + 0.25, getPosY() + 0.25, getPosZ() + 0.25));
    }

    @Override
    protected void readAdditional(CompoundNBT nbt) {
        CompoundNBT tag = nbt.getCompound(ArsMagicaAPI.MODID);
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
//            if (spell == null) spell = dataManager.get(STACK);
//            double dist = dataManager.get(RADIUS);
//            int color = 0xFFFFFF;
//            if (SpellUtil.hasModifier(SpellModifiers.COLOR, spell)) for (SpellModifier mod : SpellUtil.getModifiers(spell, -1)) if (mod instanceof Color) color = (int) mod.getModifier(SpellModifiers.COLOR, null, null, null, spell.getTag());
//            for (float i = 0; i < dist; i += 0.5f) {
//                double x = getPosX() - Math.cos(3.14159265358979 / 180 * (rotationYaw)) * i;
//                double z = getPosZ() - Math.sin(3.14159265358979 / 180 * (rotationYaw)) * i;
//                AMParticle effect = (AMParticle) ArsMagica2.proxy.particleManager.spawn(world, AMParticleDefs.getParticleForAffinity(AffinityShiftUtils.getMainShiftForStack(spell)), x, getPosY(), z);
//                if (effect != null) {
//                    effect.setIgnoreMaxAge(false);
//                    effect.setMaxAge(20);
//                    effect.addRandomOffset(1, 1, 1);
//                    effect.setParticleScale(0.15f);
//                    effect.setRGBColorI(color);
//                    effect.AddParticleController(new ParticleFloatUpward(effect, 0, 0.07f, 1, false));
//                }
//                x = getPosX() - Math.cos(Math.toRadians(rotationYaw)) * -i;
//                z = getPosZ() - Math.sin(Math.toRadians(rotationYaw)) * -i;
//                effect = (AMParticle) ArsMagica2.proxy.particleManager.spawn(world, AMParticleDefs.getParticleForAffinity(AffinityShiftUtils.getMainShiftForStack(spell)), x, getPosY(), z);
//                if (effect != null) {
//                    effect.setIgnoreMaxAge(false);
//                    effect.addRandomOffset(1, 1, 1);
//                    effect.setMaxAge(20);
//                    effect.setParticleScale(0.15f);
//                    effect.setRGBColorI(color);
//                    effect.AddParticleController(new ParticleFloatUpward(effect, 0, 0.07f, 1, false));
//                }
//            }
        } else {
            dataManager.set(EFFECT, dataManager.get(EFFECT) - 1);
            if (dataManager.get(EFFECT) <= 0) {
                dataManager.set(EFFECT, 5);
                for (Entity e : world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(getPosX() - dataManager.get(RADIUS), getPosY() - 1, getPosZ() - dataManager.get(RADIUS), getPosX() + dataManager.get(RADIUS), getPosY() + 3, getPosZ() + dataManager.get(RADIUS)))) {
                    if (e == this || e == getOwner()) continue;
                    if (e instanceof EnderDragonPartEntity && ((EnderDragonPartEntity) e).dragon != null)
                        e = ((EnderDragonPartEntity) e).dragon;
                    Vec3d a = new Vec3d(getPosX() - Math.cos(3.1415926f / 180 * (rotationYaw)) * dataManager.get(RADIUS), getPosY(), getPosZ() - Math.sin(3.1415926f / 180 * (rotationYaw)) * dataManager.get(RADIUS));
                    Vec3d b = new Vec3d(getPosX() - Math.cos(3.1415926f / 180 * (rotationYaw)) * -dataManager.get(RADIUS), getPosY(), getPosZ() - Math.sin(3.1415926f / 180 * (rotationYaw)) * -dataManager.get(RADIUS));
                    Vec3d target = new Vec3d(e.getPosX(), e.getPosY(), e.getPosZ());
                    Vec3d closest = RenderUtil.closestPointOnLine(target, a, b);
                    target = new Vec3d(target.x, 0, target.z);
                    closest = new Vec3d(closest.x, 0, closest.z);
                    if (e instanceof LivingEntity && closest.distanceTo(target) < 0.75f && Math.abs(getPosY() - e.getPosY()) < 2)
                        SpellUtil.applyStage(dataManager.get(STACK), getOwner(), (LivingEntity) e, getPosX(), getPosY(), getPosZ(), null, world, false, false, 0);
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

    public void setOwner(LivingEntity owner) {
        dataManager.set(OWNER, owner.getEntityId());
    }

    public void setDamage(float damage) {
        dataManager.set(DAMAGE, damage);
    }

    public void setGravity(float gravity) {
        dataManager.set(GRAVITY, gravity);
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
