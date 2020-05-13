package minecraftschurli.arsmagicalegacy.objects.entity;

import javax.annotation.Nonnull;
import minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import minecraftschurli.arsmagicalegacy.init.ModEffects;
import minecraftschurli.arsmagicalegacy.init.ModEntities;
import minecraftschurli.arsmagicalegacy.util.SpellUtil;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.dragon.EnderDragonPartEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.play.server.SSpawnObjectPacket;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public final class BlizzardEntity extends Entity {
    private static final DataParameter<Integer> OWNER = EntityDataManager.createKey(BlizzardEntity.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> TICKS = EntityDataManager.createKey(BlizzardEntity.class, DataSerializers.VARINT);
    private static final DataParameter<Float> DAMAGE = EntityDataManager.createKey(BlizzardEntity.class, DataSerializers.FLOAT);
    private static final DataParameter<Float> RADIUS = EntityDataManager.createKey(BlizzardEntity.class, DataSerializers.FLOAT);
    private static final DataParameter<ItemStack> STACK = EntityDataManager.createKey(BlizzardEntity.class, DataSerializers.ITEMSTACK);

    public BlizzardEntity(World world) {
        this(ModEntities.BLIZZARD.get(), world);
    }

    public BlizzardEntity(EntityType<?> type, World world) {
        super(type, world);
        setBoundingBox(new AxisAlignedBB(getPosX() - 0.25, getPosY() - 0.25, getPosZ() - 0.25, getPosX() + 0.25, getPosY() + 0.25, getPosZ() + 0.25));
    }

    @Override
    protected void readAdditional(CompoundNBT nbt) {
        CompoundNBT tag = nbt.getCompound(ArsMagicaAPI.MODID);
        dataManager.set(OWNER, tag.getInt("Owner"));
        dataManager.set(TICKS, tag.getInt("Ticks"));
        dataManager.set(DAMAGE, tag.getFloat("Damage"));
        dataManager.set(RADIUS, tag.getFloat("Radius"));
        dataManager.set(STACK, ItemStack.read(tag.getCompound("Stack")));
    }

    @Override
    protected void writeAdditional(CompoundNBT nbt) {
        CompoundNBT tag = nbt.getCompound(ArsMagicaAPI.MODID);
        tag.putInt("Owner", dataManager.get(OWNER));
        tag.putInt("Ticks", dataManager.get(TICKS));
        tag.putFloat("Damage", dataManager.get(DAMAGE));
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
        dataManager.register(OWNER, 0);
        dataManager.register(TICKS, 0);
        dataManager.register(DAMAGE, 1f);
        dataManager.register(RADIUS, 3f);
        dataManager.register(STACK, ItemStack.EMPTY);
    }

    @Override
    public void tick() {
        if (getOwner() != null) getOwner().tick();
        float radius = dataManager.get(RADIUS);
        if (world.isRemote) {
//            int color = 0xFFFFFF;
//            if (SpellUtil.hasModifier(SpellModifiers.COLOR, dataManager.get(STACK))) {
//                for (SpellModifier mod : SpellUtil.getModifiers(dataManager.get(STACK), -1))
//                    if (mod instanceof Color)
//                        color = (int) mod.getModifier(SpellModifiers.COLOR, null, null, null, dataManager.get(STACK).getTag());
//            }
//            for (int i = 0; i < 20; ++i) {
//                double x = getPosX() - radius + (rand.nextDouble() * radius * 2);
//                double z = getPosZ() - radius + (rand.nextDouble() * radius * 2);
//                double y = getPosY() + 10;
//                AMParticle particle = (AMParticle) ArsMagica2.proxy.particleManager.spawn(world, "snowflakes", x, y, z);
//                if (particle != null) {
//                    particle.setMaxAge(20);
//                    particle.setParticleScale(0.1f);
//                    particle.addVelocity(rand.nextDouble() * 0.2f - 0.1f, 0, rand.nextDouble() * 0.2f - 0.1f);
//                    particle.setAffectedByGravity();
//                    particle.setRGBColorI(color);
//                    particle.setDontRequireControllers();
//                }
//            }
//            double x = getPosX() - radius + (rand.nextDouble() * radius * 2);
//            double z = getPosZ() - radius + (rand.nextDouble() * radius * 2);
//            double y = getPosY() + rand.nextDouble();
//            AMParticle particle = (AMParticle) ArsMagica2.proxy.particleManager.spawn(world, "smoke", x, y, z);
//            if (particle != null) {
//                particle.setParticleScale(2.0f);
//                particle.setMaxAge(20);
//                particle.setRGBColorF(0.5098f, 0.7843f, 0.7843f);
//                particle.SetParticleAlpha(0.6f);
//                particle.AddParticleController(new ParticleFleePoint(particle, new Vec3d(x, y, z), 0.1f, 3f, 1, false));
//            }
        } else {
            for (Entity e : world.getEntitiesWithinAABB(LivingEntity.class, new AxisAlignedBB(getPosX() - radius, getPosY() - 1, getPosZ() - radius, getPosX() + radius, getPosY() + 3, getPosZ() + radius))) {
                if (e != getOwner()) {
                    if (e instanceof EnderDragonPartEntity && ((EnderDragonPartEntity) e).dragon != null)
                        e = ((EnderDragonPartEntity) e).dragon;
                    if (e instanceof LivingEntity)
                        ((LivingEntity) e).addPotionEffect(new EffectInstance(ModEffects.FROST.get(), 80, 3));
                    if (SpellUtil.attackWithType(dataManager.get(STACK), e, DamageSource.causeIndirectMagicDamage(getOwner(), null), dataManager.get(DAMAGE)) && !(e instanceof PlayerEntity))
                        e.hurtResistantTime = 15;
                }
            }
            if (rand.nextInt(10) < 2) {
                BlockPos pos = new BlockPos(getPosX() - radius + rand.nextInt(Math.abs((int) Math.ceil(radius) * 2)), getPosY() + rand.nextInt(2), getPosZ() - radius + rand.nextInt(Math.abs((int) Math.ceil(radius) * 2)));
                if (world.isAirBlock(pos) && !world.isAirBlock(pos.down()) && world.getBlockState(pos).isOpaqueCube(world, pos))
                    world.setBlockState(pos, Blocks.SNOW.getDefaultState());
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
