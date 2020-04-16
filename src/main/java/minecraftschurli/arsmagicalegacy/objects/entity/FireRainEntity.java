package minecraftschurli.arsmagicalegacy.objects.entity;

import javax.annotation.Nonnull;
import minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
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
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public final class FireRainEntity extends Entity {
    private static final DataParameter<Integer> OWNER = EntityDataManager.createKey(FireRainEntity.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> TICKS = EntityDataManager.createKey(FireRainEntity.class, DataSerializers.VARINT);
    private static final DataParameter<Float> DAMAGE = EntityDataManager.createKey(FireRainEntity.class, DataSerializers.FLOAT);
    private static final DataParameter<Float> RADIUS = EntityDataManager.createKey(FireRainEntity.class, DataSerializers.FLOAT);
    private static final DataParameter<ItemStack> STACK = EntityDataManager.createKey(FireRainEntity.class, DataSerializers.ITEMSTACK);

    public FireRainEntity(World world) {
        this(ModEntities.FIRE_RAIN.get(), world);
    }

    public FireRainEntity(EntityType<?> type, World world) {
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
        if (world.isRemote) {
//            int color = 0xFFFFFF;
//            if (SpellUtil.hasModifier(SpellModifiers.COLOR, dataManager.get(STACK))) for (SpellModifier mod : SpellUtil.getModifiers(dataManager.get(STACK), -1)) if (mod instanceof Color) color = (int)mod.getModifier(SpellModifiers.COLOR, null, null, null, dataManager.get(STACK).getTag());
//            for (int i = 0; i < 10; ++i){
//                AMParticle particle = (AMParticle)ArsMagica2.proxy.particleManager.spawn(world, "implosion", getPosX() - dataManager.get(RADIUS) + (rand.nextDouble() * dataManager.get(RADIUS) * 2), getPosY() + 10, dataManager.get(RADIUS) + (rand.nextDouble() * dataManager.get(RADIUS) * 2));
//                if (particle != null){
//                    particle.setMaxAge(20);
//                    particle.addVelocity(rand.nextDouble() * 0.2f, 0, rand.nextDouble() * 0.2f);
//                    particle.setAffectedByGravity();
//                    particle.setDontRequireControllers();
//                    particle.setRGBColorI(color);
//                }
//            }
        } else {
            for (Entity e : world.getEntitiesWithinAABB(LivingEntity.class, new AxisAlignedBB(getPosX() - dataManager.get(RADIUS), getPosY() - 1, getPosZ() - dataManager.get(RADIUS), getPosX() + dataManager.get(RADIUS), getPosY() + 3, getPosZ() + dataManager.get(RADIUS)))) {
                if (e != getOwner()) {
                    if (e instanceof EnderDragonPartEntity && ((EnderDragonPartEntity) e).dragon != null)
                        e = ((EnderDragonPartEntity) e).dragon;
                    if (SpellUtil.attackWithType(null, e, DamageSource.ON_FIRE, 0.75f * dataManager.get(DAMAGE)) && !(e instanceof PlayerEntity))
                        e.hurtResistantTime = 10;
                }
            }
            if (rand.nextInt(10) < 2) {
                BlockPos pos = new BlockPos(getPosX() - dataManager.get(RADIUS) + rand.nextInt(Math.abs((int) Math.ceil(dataManager.get(RADIUS)) * 2)), getPosY(), getPosZ() - dataManager.get(RADIUS) + rand.nextInt(Math.abs((int) Math.ceil(dataManager.get(RADIUS)) * 2)));
                if (world.isAirBlock(pos))
                    world.setBlockState(pos, Blocks.FIRE.getDefaultState());
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

    public void setDamage(float damage) {
        dataManager.set(DAMAGE, damage);
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
