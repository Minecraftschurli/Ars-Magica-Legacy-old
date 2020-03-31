package minecraftschurli.arsmagicalegacy.objects.entity;

import java.util.List;
import minecraftschurli.arsmagicalegacy.init.ModEffects;
import minecraftschurli.arsmagicalegacy.init.ModEntities;
import minecraftschurli.arsmagicalegacy.util.SpellUtils;
import net.minecraft.block.Blocks;
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
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlizzardEntity extends Entity {
    private int ticksToExist;
    private ItemStack spell;
    private PlayerEntity caster;
    private static final DataParameter<ItemStack> STACK_DATA = EntityDataManager.createKey(BlizzardEntity.class, DataSerializers.ITEMSTACK);
    private static final DataParameter<Float> RADIUS_DATA = EntityDataManager.createKey(BlizzardEntity.class, DataSerializers.FLOAT);
    private static final DataParameter<Float> DAMAGE_DATA = EntityDataManager.createKey(BlizzardEntity.class, DataSerializers.FLOAT);

    public BlizzardEntity(World world) {
        this(ModEntities.BLIZZARD.get(), world);
    }

    public BlizzardEntity(EntityType<?> type, World world) {
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
        dataManager.register(RADIUS_DATA, 3f);
        dataManager.register(STACK_DATA, new ItemStack(Items.GOLDEN_APPLE));
        dataManager.register(DAMAGE_DATA, 1f);
    }

    @Override
    public void tick() {
        if (caster != null) caster.tick();
        float radius = dataManager.get(RADIUS_DATA);
        if (world.isRemote) {
//            if (spellStack == null) {
//                spellStack = dataManager.get(WATCHER_STACK);
//                if (spellStack == null) return;
//            }
//            int color = 0xFFFFFF;
//            if (SpellUtils.hasModifier(SpellModifiers.COLOR, spellStack)) {
//                List<SpellModifier> mods = SpellUtils.getModifiers(spellStack, -1);
//                for (SpellModifier mod : mods)
//                    if (mod instanceof Color)
//                        color = (int) mod.getModifier(SpellModifiers.COLOR, null, null, null, spellStack.getTag());
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
            //TODO: SoundHelper.instance.loopSound(world, (float)getPosX(), (float)getPosY(), (float)getPosZ(), "arsmagica2:spell.loop.air", 1.0f);
        } else {
            List<Entity> possibleTargets = world.getEntitiesWithinAABB(LivingEntity.class, new AxisAlignedBB(getPosX() - radius, getPosY() - 1, getPosZ() - radius, getPosX() + radius, getPosY() + 3, getPosZ() + radius));
            for (Entity e : possibleTargets) {
                if (e != caster) {
                    if (e instanceof EnderDragonPartEntity && ((EnderDragonPartEntity) e).dragon != null)
                        e = ((EnderDragonPartEntity) e).dragon;
                    if (e instanceof LivingEntity)
                        ((LivingEntity) e).addPotionEffect(new EffectInstance(ModEffects.FROST.get(), 80, 3));
                    float damage = dataManager.get(DAMAGE_DATA);
                    double lastVelX = e.getMotion().x;
                    double lastVelY = e.getMotion().y;
                    double lastVelZ = e.getMotion().z;
                    if (SpellUtils.attackWithType(spell, e, DamageSource.causeIndirectMagicDamage(caster, null), damage) && !(e instanceof PlayerEntity))
                        e.hurtResistantTime = 15;
                    e.addVelocity(-(e.getMotion().x - lastVelX), -(e.getMotion().y - lastVelY), -(e.getMotion().z - lastVelZ));
                }
            }
            if (rand.nextInt(10) < 2) {
                int pX = (int) (getPosX() - radius + rand.nextInt((int) Math.ceil(radius) * 2));
                int pY = (int) getPosY() + rand.nextInt(2);
                int pZ = (int) (getPosZ() - radius + rand.nextInt((int) Math.ceil(radius) * 2));
                BlockPos pos = new BlockPos(pX, pY, pZ);
                if (world.isAirBlock(pos) && !world.isAirBlock(pos.down()) && world.getBlockState(pos).isOpaqueCube(null, pos))
                    world.setBlockState(pos, Blocks.SNOW.getDefaultState());
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
            dataManager.set(STACK_DATA, spell);
    }

    public void setDamage(float damage) {
        dataManager.set(DAMAGE_DATA, damage);
    }

    public void setRadius(float radius) {
        dataManager.set(RADIUS_DATA, radius);
    }

    public void setTicksToExist(int ticks) {
        ticksToExist = ticks;
    }
}
