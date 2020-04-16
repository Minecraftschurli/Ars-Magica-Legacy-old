package minecraftschurli.arsmagicalegacy.objects.entity;

import minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import minecraftschurli.arsmagicalegacy.init.ModBlocks;
import minecraftschurli.arsmagicalegacy.init.ModEntities;
import minecraftschurli.arsmagicalegacy.init.ModItems;
import minecraftschurli.arsmagicalegacy.util.SpellUtil;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.play.server.SSpawnObjectPacket;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;

/**
 * @author Minecraftschurli
 * @version 2019-11-28
 */
public final class ThrownRockEntity extends Entity {
    private static final DataParameter<Boolean> MOONSTONE_METEOR = EntityDataManager.createKey(ThrownRockEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> SHOOTING_STAR = EntityDataManager.createKey(ThrownRockEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Integer> MAX_TICKS = EntityDataManager.createKey(ThrownRockEntity.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> OWNER = EntityDataManager.createKey(ThrownRockEntity.class, DataSerializers.VARINT);
    private static final DataParameter<Float> DAMAGE = EntityDataManager.createKey(ThrownRockEntity.class, DataSerializers.FLOAT);
    private static final DataParameter<ItemStack> STACK = EntityDataManager.createKey(ThrownRockEntity.class, DataSerializers.ITEMSTACK);
    private Vec3d target;

    public ThrownRockEntity(World world) {
        this(ModEntities.THROWN_ROCK.get(), world);
    }

    public ThrownRockEntity(EntityType<?> type, World world) {
        super(type, world);
        noClip = true;
        target = null;
    }

    @Override
    public void readAdditional(CompoundNBT nbt) {
        CompoundNBT tag = nbt.getCompound(ArsMagicaAPI.MODID);
        dataManager.set(MAX_TICKS, tag.getInt("MaxTicks"));
        dataManager.set(OWNER, tag.getInt("Owner"));
        dataManager.set(DAMAGE, tag.getFloat("Damage"));
        dataManager.set(STACK, ItemStack.read(tag.getCompound("Stack")));
        if (tag.getBoolean("MoonstoneMeteor")) setMoonstoneMeteor();
        if (tag.getBoolean("ShootingStar")) setShootingStar(getDataManager().get(DAMAGE));
    }

    @Override
    public void writeAdditional(CompoundNBT nbt) {
        CompoundNBT tag = nbt.getCompound(ArsMagicaAPI.MODID);
        tag.putBoolean("MoonstoneMeteor", dataManager.get(MOONSTONE_METEOR));
        tag.putBoolean("ShootingStar", dataManager.get(SHOOTING_STAR));
        tag.putFloat("Damage", dataManager.get(DAMAGE));
        tag.putInt("MaxTicks", dataManager.get(MAX_TICKS));
        tag.putInt("Owner", dataManager.get(OWNER));
        CompoundNBT tmp = new CompoundNBT();
        dataManager.get(STACK).write(tmp);
        tag.put("Stack", tmp);
    }

    @Override
    public boolean attackEntityFrom(@Nonnull DamageSource source, float damage) {
        return false;
    }

    @Nonnull
    @Override
    public IPacket<?> createSpawnPacket() {
        return new SSpawnObjectPacket(this, getOwner() == null ? 0 : getOwner().getEntityId());
    }

    @Override
    protected void registerData() {
        dataManager.register(MOONSTONE_METEOR, false);
        dataManager.register(SHOOTING_STAR, false);
        dataManager.register(DAMAGE, 0f);
        dataManager.register(MAX_TICKS, 120);
        dataManager.register(OWNER, 0);
        dataManager.register(STACK, new ItemStack(ModItems.SPELL.get()));
    }

    @Override
    public void tick() {
        super.tick();
        if (target != null && getPosY() > target.y) {
            double x = getPosX() - target.x;
            double z = getPosZ() - target.z;
            double angle = Math.atan2(z, x);
            setMotion(-Math.cos(angle) * 0.2, -Math.sin(angle) * 0.2, -Math.sin(Math.atan2(getPosY() - target.y, Math.sqrt(x * x + z * z))) * 2.5);
        }
        if (!dataManager.get(MOONSTONE_METEOR) && !isShootingStar()) {
            if (!world.isRemote && (world.getEntityByID(dataManager.get(OWNER)) == null || !world.getEntityByID(dataManager.get(OWNER)).isAlive())) remove();
            else {
                ticksExisted++;
                int maxTicksToLive = getMaxTicks() > -1 ? getMaxTicks() : 100;
                if (ticksExisted >= maxTicksToLive && !world.isRemote) {
                    remove();
                    return;
                }
            }
        }
        if (isShootingStar()) {
            setMotion(getMotion().add(0, -0.1f, 0));
            if (getMotion().y < -2f)
                setMotion(getMotion().x, -2f, getMotion().z);
        }
//        if (world.isRemote) {
//            if (dataManager.get(MOONSTONE_METEOR)) {
//                AMParticle fire = (AMParticle) ArsMagica2.proxy.particleManager.spawn(worldObj, "explosion_2", getPosX(), getPosY(), getPosZ());
//                if (fire != null) {
//                    fire.setMaxAge(20);
//                    fire.setRGBColorF(1, 1, 1);
//                    fire.setParticleScale(2);
//                    fire.AddParticleController(new ParticleHoldPosition(fire, 20, 1, false));
//                    fire.AddParticleController(new ParticleColorShift(fire, 1, false).SetShiftSpeed(0.1f).SetColorTarget(0.01f, 0.01f, 0.01f).SetEndOnReachingTargetColor().setKillParticleOnFinish(false));
//                }
//            } else if (isShootingStar()) {
//                int color = -1;
//                dataManager.get(STACK);
//                if (SpellUtil.hasModifier(SpellModifiers.COLOR, dataManager.get(STACK)))
//                    for (SpellModifier mod : SpellUtil.getModifiers(dataManager.get(STACK), -1))
//                        if (mod instanceof Color)
//                            color = (int) mod.getModifier(SpellModifiers.COLOR, null, null, null, dataManager.get(STACK).getTag());
//                for (float i = 0; i < Math.abs(getMotion().y); i += 0.1f) {
//                    AMParticle star = (AMParticle) ArsMagica2.proxy.particleManager.spawn(worldObj, "ember", getPosX() + getMotion().x * i, getPosY() + getMotion().y * i, getPosZ() + getMotion().z * i);
//                    if (star != null) {
//                        star.setMaxAge(22);
//                        float clrMod = Minecraft.getInstance().world.rand.nextFloat();
//                        int finalColor = -1;
//                        if (color == -1)
//                            finalColor = MathUtilities.colorFloatsToInt(0.24f * clrMod, 0.58f * clrMod, 0.71f * clrMod);
//                        else {
//                            float[] colors = MathUtilities.colorIntToFloats(color);
//                            for (int c = 0; c < colors.length; c++)
//                                colors[c] = colors[c] * clrMod;
//                            finalColor = MathUtilities.colorFloatsToInt(colors[0], colors[1], colors[2]);
//                        }
//                        star.setRGBColorI(finalColor);
//                        star.AddParticleController(new ParticleHoldPosition(star, 20, 1, false));
//                        star.AddParticleController(new ParticleChangeSize(star, 0.5f, 0.05f, 20, 1, false));
//                    }
//                }
//            }
//        }
        Vec3d vec3d = new Vec3d(getPosX(), getPosY(), getPosZ());
        Vec3d vec3d1 = new Vec3d(getPosX() + getMotion().x, getPosY() + getMotion().y, getPosZ() + getMotion().z);
        RayTraceResult mop = world.rayTraceBlocks(new RayTraceContext(vec3d, vec3d1, RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, this));
        vec3d = new Vec3d(getPosX(), getPosY(), getPosZ());
        vec3d1 = new Vec3d(mop.getHitVec().x, mop.getHitVec().y, mop.getHitVec().z);
        Entity entity = null;
        double d = 0;
        for (Entity value : world.getEntitiesWithinAABBExcludingEntity(this, getBoundingBox().expand(getMotion().x, getMotion().y, getMotion().z).expand(1, 1, 1))) {
            if (!value.canBeCollidedWith() || value.isEntityEqual(world.getEntityByID(dataManager.get(OWNER))) && ticksExisted < 25)
                continue;
            float f2 = 0.3F;
            AxisAlignedBB axisalignedbb = value.getBoundingBox().expand(f2, f2, f2);
            RayTraceResult rtr = AxisAlignedBB.rayTrace(Collections.singletonList(axisalignedbb), vec3d, vec3d1, getPosition());
            if (rtr == null) continue;
            double d1 = vec3d.distanceTo(rtr.getHitVec());
            if (d1 < d || d == 0) {
                entity = value;
                d = d1;
            }
        }
        if (entity != null) mop = new EntityRayTraceResult(entity);
        if (!world.isRemote) {
            if (isShootingStar()) {
                //AMNetHandler.INSTANCE.sendStarImpactToClients(getPosX(), getPosY() + ((mop.getType() == RayTraceResult.Type.ENTITY) ? -((EntityRayTraceResult)mop).getEntity().getEyeHeight() : 1.5f), getPosZ(), world, getSpellStack());
                setPosition(getPosX(), getPosY() + 1, getPosZ());
                for (LivingEntity e : world.getEntitiesWithinAABB(LivingEntity.class, getBoundingBox().expand(12, 5, 12))) {
                    if (e == world.getEntityByID(dataManager.get(OWNER))) continue;
                    if (getDistance(e) < 12)
                        SpellUtil.attackWithType(null, e, DamageSource.causeIndirectMagicDamage(world.getEntityByID(dataManager.get(OWNER)), this), dataManager.get(DAMAGE));
                }
            } else {
                if (mop.getType() == RayTraceResult.Type.ENTITY && ((EntityRayTraceResult) mop).getEntity() instanceof LivingEntity) {
                    if (((EntityRayTraceResult) mop).getEntity() == world.getEntityByID(dataManager.get(OWNER)) || world.getEntityByID(dataManager.get(OWNER)) == null)
                        return;
                    ((EntityRayTraceResult) mop).getEntity().attackEntityFrom(DamageSource.causeMobDamage((LivingEntity) world.getEntityByID(dataManager.get(OWNER))), 10);
                } else if (mop.getType() == RayTraceResult.Type.BLOCK) {
                    if (dataManager.get(MOONSTONE_METEOR)) {
                        if (target == null) target = mop.getHitVec();
                        world.createExplosion(this, target.getX(), target.getY(), target.getZ(), 0.8f, Explosion.Mode.NONE);
                        int numOres = rand.nextInt(4) + 1;
                        for (int i = 0; i < numOres; i++) {
                            BlockPos pos = new BlockPos(target);
                            pos = pos.east(rand.nextInt(4) - 2);
                            pos = pos.south(rand.nextInt(4) - 2);
                            while (!world.isAirBlock(pos) && pos.getY() < world.getActualHeight())
                                pos = pos.up();
                            if (rand.nextInt(4) < 2 || i == 0)
                                world.setBlockState(pos, ModBlocks.MOONSTONE_ORE.get().getDefaultState());
                            else
                                world.setBlockState(pos, Blocks.STONE.getDefaultState());
                        }
                    }
                }
            }
            remove();
        }
        setPosition(getPosX() + getMotion().x, getPosY() + getMotion().y, getPosZ() + getMotion().z);
        rotationYaw = (float) ((Math.atan2(getMotion().x, getMotion().z) * 180) / 3.14159265358979);
        rotationPitch = prevRotationPitch + (rotationPitch - prevRotationPitch) * 0.2F;
        rotationYaw = prevRotationYaw + (rotationYaw - prevRotationYaw) * 0.2F;
    }

    public int getMaxTicks() {
        return dataManager.get(MAX_TICKS);
    }

    public LivingEntity getOwner() {
        try {
            return (LivingEntity) world.getEntityByID(getDataManager().get(OWNER));
        } catch (RuntimeException e) {
            remove();
            return null;
        }
    }

    public boolean isShootingStar() {
        return dataManager.get(SHOOTING_STAR);
    }

    public void setMoonstoneMeteor() {
        dataManager.set(MOONSTONE_METEOR, true);
    }

    public void setOwner(LivingEntity owner) {
        dataManager.set(OWNER, owner.getEntityId());
    }

    public void setShootingStar(float damage) {
        dataManager.set(SHOOTING_STAR, true);
        dataManager.set(DAMAGE, 0f);
    }

    public void setStack(ItemStack stack) {
        dataManager.set(STACK, stack);
    }

    public void setTarget(Vec3d target) {
        this.target = target;
    }
}
