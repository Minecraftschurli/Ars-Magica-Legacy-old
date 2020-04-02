package minecraftschurli.arsmagicalegacy.objects.entity;

import minecraftschurli.arsmagicalegacy.init.ModEntities;
import minecraftschurli.arsmagicalegacy.util.RenderUtil;
import minecraftschurli.arsmagicalegacy.util.SpellUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.dragon.EnderDragonPartEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.ArrayList;

public class WaveEntity extends Entity {
    private static final DataParameter<ItemStack> STACK_DATA = EntityDataManager.createKey(WaveEntity.class, DataSerializers.ITEMSTACK);
    private static final DataParameter<Float> RADIUS_DATA = EntityDataManager.createKey(WaveEntity.class, DataSerializers.FLOAT);
    private static final DataParameter<Float> GRAVITY_DATA = EntityDataManager.createKey(WaveEntity.class, DataSerializers.FLOAT);
    private static final DataParameter<Float> DAMAGE_DATA = EntityDataManager.createKey(WaveEntity.class, DataSerializers.FLOAT);
    private int ticksToEffect = 20;
    private int ticksToExist = 100;
    private float moveSpeed;
    private ItemStack spell;
    private PlayerEntity caster;

    public WaveEntity(World world) {
        this(ModEntities.WAVE.get(), world);
    }

    public WaveEntity(EntityType<?> type, World world) {
        super(type, world);
        setBoundingBox(new AxisAlignedBB(getPosX() - 0.25, getPosY() - 0.25, getPosZ() - 0.25, getPosX() + 0.25, getPosY() + 0.25, getPosZ() + 0.25));
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return null;
    }

    @Override
    protected void readAdditional(CompoundNBT compound) {
    }

    @Override
    protected void registerData() {
        this.dataManager.register(RADIUS_DATA, 3f);
        this.dataManager.register(STACK_DATA, new ItemStack(Items.GOLDEN_APPLE));
        this.dataManager.register(GRAVITY_DATA, 0F);
        this.dataManager.register(DAMAGE_DATA, 1.0f);
    }

    @Override
    public void tick() {
        ticksToEffect = 0;
        if (world.isRemote) {
//            if (spell == null) spell = dataManager.get(STACK_DATA);
//            double dist = dataManager.get(RADIUS_DATA);
//            int color = 0xFFFFFF;
//            if (SpellUtils.hasModifier(SpellModifiers.COLOR, spell)) for (SpellModifier mod : SpellUtils.getModifiers(spell, -1)) if (mod instanceof Color) color = (int) mod.getModifier(SpellModifiers.COLOR, null, null, null, spell.getTag());
//            for (float i = 0; i < dist; i += 0.5f) {
//                double x = this.getPosX() - Math.cos(Math.PI / 180 * (rotationYaw)) * i;
//                double z = this.getPosZ() - Math.sin(Math.PI / 180 * (rotationYaw)) * i;
//                AMParticle effect = (AMParticle) ArsMagica2.proxy.particleManager.spawn(world, AMParticleDefs.getParticleForAffinity(AffinityShiftUtils.getMainShiftForStack(spell)), x, getPosY(), z);
//                if (effect != null) {
//                    effect.setIgnoreMaxAge(false);
//                    effect.setMaxAge(20);
//                    effect.addRandomOffset(1, 1, 1);
//                    effect.setParticleScale(0.15f);
//                    effect.setRGBColorI(color);
//                    effect.AddParticleController(new ParticleFloatUpward(effect, 0, 0.07f, 1, false));
//                }
//                x = this.getPosX() - Math.cos(Math.toRadians(rotationYaw)) * -i;
//                z = this.getPosZ() - Math.sin(Math.toRadians(rotationYaw)) * -i;
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
            ticksToEffect--;
            if (spell == null) {
                this.remove();
                return;
            }
            if (ticksToEffect <= 0) {
                ticksToEffect = 5;
                float radius = this.dataManager.get(RADIUS_DATA);
                for (Entity e : world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(getPosX() - radius, getPosY() - 1, getPosZ() - radius, getPosX() + radius, getPosY() + 3, getPosZ() + radius))) {
                    if (e == this || e == caster || e.getEntityId() == caster.getEntityId()) continue;
                    if (e instanceof EnderDragonPartEntity && ((EnderDragonPartEntity) e).dragon != null)
                        e = ((EnderDragonPartEntity) e).dragon;
                    Vec3d a = new Vec3d(this.getPosX() - Math.cos(3.141 / 180 * (rotationYaw)) * radius, this.getPosY(), this.getPosZ() - Math.sin(3.141 / 180 * (rotationYaw)) * radius);
                    Vec3d b = new Vec3d(this.getPosX() - Math.cos(3.141 / 180 * (rotationYaw)) * -radius, this.getPosY(), this.getPosZ() - Math.sin(3.141 / 180 * (rotationYaw)) * -radius);
                    Vec3d target = new Vec3d(e.getPosX(), e.getPosY(), e.getPosZ());
                    Vec3d closest = RenderUtil.closestPointOnLine(target, a, b);
                    target = new Vec3d(target.x, 0, target.z);
                    closest = new Vec3d(closest.x, 0, closest.z);
                    if (e instanceof LivingEntity && closest.distanceTo(target) < 0.75f && Math.abs(this.getPosY() - e.getPosY()) < 2)
                        SpellUtil.applyStage(spell, caster, (LivingEntity) e, this.getPosX(), this.getPosY(), this.getPosZ(), null, world, false, false, 0);
                }
            }
        }
        double dx = Math.cos(Math.toRadians(this.rotationYaw + 90));
        double dz = Math.sin(Math.toRadians(this.rotationYaw + 90));
        this.moveForced(dx * moveSpeed, 0, dz * moveSpeed);
        double dxH = Math.cos(Math.toRadians(this.rotationYaw));
        double dzH = Math.sin(Math.toRadians(this.rotationYaw));
        float radius = this.dataManager.get(RADIUS_DATA);
        for (int j = -1; j <= 1; j++) {
            Vec3d a = new Vec3d((this.getPosX() + dx) - dxH * radius, this.getPosY() + j, (this.getPosZ() + dz) - dzH * radius);
            Vec3d b = new Vec3d((this.getPosX() + dx) - dxH * -radius, this.getPosY() + j, (this.getPosZ() + dz) - dzH * -radius);
            double stepX = a.x < b.x ? 0.2f : -0.2f;
            double stepZ = a.z < b.z ? 0.2f : -0.2f;
            ArrayList<Vec3d> vecs = new ArrayList<Vec3d>();
            Vec3d curPos = new Vec3d(a.x, a.y, a.z);
            for (int i = 0; i < this.getHeight(); ++i) vecs.add(new Vec3d(curPos.x, curPos.y + i, curPos.z));
            while (stepX != 0 || stepZ != 0) {
                if ((stepX < 0 && curPos.x <= b.x) || (stepX > 0 && curPos.x >= b.x))
                    stepX = 0;
                if ((stepZ < 0 && curPos.z <= b.z) || (stepZ > 0 && curPos.z >= b.z))
                    stepZ = 0;
                curPos = new Vec3d(curPos.x + stepX, curPos.y, curPos.z + stepZ);
                Vec3d tempPos = curPos.add(Vec3d.ZERO);
                if (!vecs.contains(tempPos))
                    for (int i = 0; i < this.getHeight(); ++i) vecs.add(new Vec3d(tempPos.x, tempPos.y + i, tempPos.z));
            }
            for (Vec3d vec : vecs)
                SpellUtil.applyStageBlock(dataManager.get(STACK_DATA), caster, world, new BlockPos(vec), Direction.UP, vec.x + 0.5, vec.y + 0.5, vec.z + 0.5, false);
        }
        if (!world.isRemote && this.ticksExisted >= this.ticksToExist) this.remove();
    }

    @Override
    protected void writeAdditional(CompoundNBT compound) {
    }

    public void setCasterAndStack(LivingEntity entity, ItemStack stack) {
        if (entity instanceof PlayerEntity) caster = (PlayerEntity) entity;
        spell = stack;
        if (spell != null)
            dataManager.set(STACK_DATA, spell);
    }

    public void setRadius(float newRadius) {
        this.dataManager.set(RADIUS_DATA, newRadius);
    }

    public void setTicksToExist(int ticks) {
        this.ticksToExist = ticks;
    }

    public void setGravity(double gravity) {
        dataManager.set(GRAVITY_DATA, (float) gravity);
    }
}
