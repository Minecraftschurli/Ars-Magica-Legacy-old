package minecraftschurli.arsmagicalegacy.objects.entity;

import minecraftschurli.arsmagicalegacy.init.ModEntities;
import minecraftschurli.arsmagicalegacy.util.RenderUtils;
import minecraftschurli.arsmagicalegacy.util.SpellUtils;
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
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class WallEntity extends Entity {
    private int ticksToEffect = 20;
    private int ticksToExist = 100;
    private ItemStack spell;
    private PlayerEntity caster;
    private static final DataParameter<ItemStack> STACK_DATA = EntityDataManager.createKey(WallEntity.class, DataSerializers.ITEMSTACK);
    private static final DataParameter<Float> RADIUS_DATA = EntityDataManager.createKey(WallEntity.class, DataSerializers.FLOAT);
    private static final DataParameter<Float> GRAVITY_DATA = EntityDataManager.createKey(WallEntity.class, DataSerializers.FLOAT);
    private static final DataParameter<Float> DAMAGE_DATA = EntityDataManager.createKey(WallEntity.class, DataSerializers.FLOAT);

    public WallEntity(World world) {
        this(ModEntities.WALL.get(), world);
    }

    public WallEntity(EntityType<?> type, World world) {
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
        this.dataManager.register(DAMAGE_DATA, 1f);
        this.dataManager.register(GRAVITY_DATA, 0f);
        this.dataManager.register(RADIUS_DATA, 3f);
        this.dataManager.register(STACK_DATA, new ItemStack(Items.GOLDEN_APPLE));
    }

    @Override
    public void tick() {
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
                    Vec3d closest = RenderUtils.closestPointOnLine(target, a, b);
                    target = new Vec3d(target.x, 0, target.z);
                    closest = new Vec3d(closest.x, 0, closest.z);
                    if (e instanceof LivingEntity && closest.distanceTo(target) < 0.75f && Math.abs(this.getPosY() - e.getPosY()) < 2) SpellUtils.applyStackStage(spell, caster, (LivingEntity) e, this.getPosX(), this.getPosY(), this.getPosZ(), null, world, false, false, 0);
                }
            }
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

    public void setGravity(float gravity) {
        dataManager.set(GRAVITY_DATA, gravity);
    }

    public void setRadius(float radius) {
        this.dataManager.set(RADIUS_DATA, radius);
    }

    public void setTicksToExist(int ticks) {
        this.ticksToExist = ticks;
    }
}
