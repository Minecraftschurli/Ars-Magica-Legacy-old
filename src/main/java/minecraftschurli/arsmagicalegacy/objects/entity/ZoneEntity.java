package minecraftschurli.arsmagicalegacy.objects.entity;

import minecraftschurli.arsmagicalegacy.init.ModEntities;
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

public class ZoneEntity extends Entity {
    private static final DataParameter<ItemStack> STACK = EntityDataManager.createKey(ZoneEntity.class, DataSerializers.ITEMSTACK);
    private static final DataParameter<Float> RADIUS = EntityDataManager.createKey(ZoneEntity.class, DataSerializers.FLOAT);
    private static final DataParameter<Float> GRAVITY = EntityDataManager.createKey(ZoneEntity.class, DataSerializers.FLOAT);
    private static final DataParameter<Float> DAMAGE = EntityDataManager.createKey(ZoneEntity.class, DataSerializers.FLOAT);
    private boolean firstApply = true;
    private int ticksToEffect = 20;
    private int ticksToExist = 100;
    private ItemStack spell;
    private PlayerEntity caster;

    public ZoneEntity(World world) {
        this(ModEntities.WALL.get(), world);
    }

    public ZoneEntity(EntityType<?> type, World world) {
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
        dataManager.register(DAMAGE, 1f);
        dataManager.register(GRAVITY, 0f);
        dataManager.register(RADIUS, 3f);
        dataManager.register(STACK, new ItemStack(Items.GOLDEN_APPLE));
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
        ticksToEffect--;
        if (spell == null) {
            if (!world.isRemote) remove();
            return;
        }
        if (ticksToEffect <= 0) {
            ticksToEffect = 20;
            float radius = dataManager.get(RADIUS);
            for (Entity e : world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(getPosX() - radius, getPosY() - 3, getPosZ() - radius, getPosX() + radius, getPosY() + 3, getPosZ() + radius))) {
                if (e instanceof EnderDragonPartEntity && ((EnderDragonPartEntity) e).dragon != null)
                    e = ((EnderDragonPartEntity) e).dragon;
                if (e instanceof LivingEntity)
                    SpellUtil.applyStageEntity(spell, caster, world, e, false);
                SpellUtil.applyStage(spell.copy(), caster, (LivingEntity) e, e.getPosX(), e.getPosY() - 1, e.getPosZ(), null, world, false, false, ticksExisted);
            }
            if (dataManager.get(GRAVITY) < 0 && !firstApply)
                SpellUtil.applyStage(spell.copy(), caster, null, getPosX(), getPosY() - 1, getPosZ(), null, world, false, false, ticksExisted);
            else
                SpellUtil.applyStage(spell.copy(), caster, null, getPosX(), getPosY(), getPosZ(), null, world, false, false, ticksExisted);
            firstApply = false;
            for (float i = -radius; i <= radius; i++) {
                for (int j = -3; j <= 3; j++) {
                    Vec3d a = new Vec3d(getPosX() + i, getPosY() + j, getPosZ() - radius);
                    Vec3d b = new Vec3d(getPosX() + i, getPosY() + j, getPosZ() + radius);
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
                        SpellUtil.applyStageBlock(spell.copy(), caster, world, new BlockPos(vec), Direction.UP, vec.x + 0.5, vec.y + 0.5, vec.z + 0.5, false);
                }
            }
        }
        if (!world.isRemote && ticksExisted >= ticksToExist) remove();
    }

    @Override
    protected void writeAdditional(CompoundNBT compound) {
    }

    public void setCasterAndStack(LivingEntity entity, ItemStack stack) {
        if (entity instanceof PlayerEntity) caster = (PlayerEntity) entity;
        spell = stack;
        if (spell != null)
            dataManager.set(STACK, spell);
    }

    public void setGravity(double gravity) {
        dataManager.set(GRAVITY, (float) gravity);
    }

    public void setRadius(float radius) {
        dataManager.set(RADIUS, radius);
    }

    public void setTicksToExist(int ticks) {
        ticksToExist = ticks;
    }
}
