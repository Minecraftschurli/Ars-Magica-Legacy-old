package minecraftschurli.arsmagicalegacy.objects.entity;

import java.util.Collections;
import java.util.List;
import javax.annotation.Nonnull;
import minecraftschurli.arsmagicalegacy.init.ModBlocks;
import minecraftschurli.arsmagicalegacy.init.ModEntities;
import minecraftschurli.arsmagicalegacy.init.ModItems;
import minecraftschurli.arsmagicalegacy.util.SpellUtils;
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

/**
 * @author Minecraftschurli
 * @version 2019-11-28
 */
public class ThrownRockEntity extends Entity {
    private static final DataParameter<Boolean> IS_MOONSTONE_METEOR = EntityDataManager.createKey(ThrownRockEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> IS_SHOOTING_STAR = EntityDataManager.createKey(ThrownRockEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<ItemStack> SPELL_STACK = EntityDataManager.createKey(ThrownRockEntity.class, DataSerializers.ITEMSTACK);
    private static final DataParameter<Integer> OWNER = EntityDataManager.createKey(ThrownRockEntity.class, DataSerializers.VARINT);
    private int maxTicksToExist;
    private Vec3d target = null;
    private float damage;

    public ThrownRockEntity(World worldIn) {
        this(ModEntities.THROWN_ROCK.get(), worldIn);
    }

    public ThrownRockEntity(EntityType<?> entityTypeIn, World worldIn) {
        super(entityTypeIn, worldIn);
        ticksExisted = 0;
        maxTicksToExist = 120;
        this.noClip = true;
    }

    @SuppressWarnings("unused")
    public ThrownRockEntity(World world, LivingEntity entityLiving, double projectileSpeed) {
        this(world);
        this.noClip = true;
        setThrowingEntity(entityLiving);
        //setSize(0.25F, 0.25F);
        setLocationAndAngles(entityLiving.getPosX(), entityLiving.getPosY() + entityLiving.getEyeHeight(), entityLiving.getPosZ(), entityLiving.rotationYaw, entityLiving.rotationPitch);
        double tmpX = getPosX() - MathHelper.cos((rotationYaw / 180F) * 3.141593F) * 0.16F;
        double tmpY = getPosY() - 0.10000000149011612D;
        double tmpZ = getPosZ() - MathHelper.sin((rotationYaw / 180F) * 3.141593F) * 0.16F;
        setPosition(tmpX, tmpY, tmpZ);
        float f = 0.05F;
        setMotion(
                -MathHelper.sin((rotationYaw / 180F) * 3.141593F) * MathHelper.cos((rotationPitch / 180F) * 3.141593F) * f,
                -MathHelper.sin((rotationPitch / 180F) * 3.141593F) * f,
                MathHelper.cos((rotationYaw / 180F) * 3.141593F) * MathHelper.cos((rotationPitch / 180F) * 3.141593F) * f
        );
        maxTicksToExist = 100;
        setHeading(getMotion().x, getMotion().y, getMotion().z, projectileSpeed, projectileSpeed);
    }

    public void setMoonstoneMeteor() {
        this.dataManager.set(IS_MOONSTONE_METEOR, true);
    }

    public void setShootingStar(float damage) {
        this.dataManager.set(IS_SHOOTING_STAR, true);
        this.damage = damage;
    }

    @SuppressWarnings("unused")
    public void setMoonstoneMeteorTarget(Vec3d target) {
        this.target = target;
    }

    public boolean getIsMoonstoneMeteor() {
        return dataManager.get(IS_MOONSTONE_METEOR);
    }

    public boolean getIsShootingStar() {
        return dataManager.get(IS_SHOOTING_STAR);
    }

    public void setHeading(double movementX, double movementY, double movementZ, double projectileSpeed, double projectileSpeed2) {
        float f = MathHelper.sqrt(movementX * movementX + movementY * movementY + movementZ * movementZ);
        movementX /= f;
        movementY /= f;
        movementZ /= f;
        movementX += rand.nextGaussian() * 0.0074999998323619366D * projectileSpeed2;
        movementY += rand.nextGaussian() * 0.0074999998323619366D * projectileSpeed2;
        movementZ += rand.nextGaussian() * 0.0074999998323619366D * projectileSpeed2;
        movementX *= projectileSpeed;
        movementY *= projectileSpeed;
        movementZ *= projectileSpeed;
        setMotion(movementX, movementY, movementZ);
        float f1 = MathHelper.sqrt(movementX * movementX + movementZ * movementZ);
        prevRotationYaw = rotationYaw = (float) ((Math.atan2(movementX, movementZ) * 180D) / Math.PI);
        prevRotationPitch = rotationPitch = (float) ((Math.atan2(movementY, f1) * 180D) / Math.PI);
    }

    public void setThrowingEntity(LivingEntity thrower) {
        this.dataManager.set(OWNER, thrower.getEntityId());
    }

    @Override
    protected void registerData() {
        this.dataManager.register(IS_MOONSTONE_METEOR, false);
        this.dataManager.register(IS_SHOOTING_STAR, false);
        this.dataManager.register(SPELL_STACK, new ItemStack(ModItems.SPELL.get()));
        this.dataManager.register(OWNER, 0);
    }

    @SuppressWarnings("unused")
    private ItemStack getSpellStack() {
        return this.dataManager.get(SPELL_STACK);
    }

    public void setSpellStack(ItemStack spellStack) {
        this.dataManager.set(SPELL_STACK, spellStack);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.target != null && this.getPosY() > this.target.y) {
            double deltaX = this.getPosX() - target.x;
            double deltaY = this.getPosY() - target.y;
            double deltaZ = this.getPosZ() - target.z;
            double angle = Math.atan2(deltaZ, deltaX);
            double hDist = Math.sqrt(deltaX * deltaX + deltaZ * deltaZ);
            double vAngle = Math.atan2(deltaY, hDist);
            setMotion(-Math.cos(angle) * 0.2, -Math.sin(angle) * 0.2, -Math.sin(vAngle) * 2.5);
        }
        if (!getIsMoonstoneMeteor() && !getIsShootingStar()) {
            //noinspection ConstantConditions
            if (!world.isRemote && (world.getEntityByID(getDataManager().get(OWNER)) == null || !world.getEntityByID(getDataManager().get(OWNER)).isAlive())) {
                remove();
            } else {
                ticksExisted++;
                int maxTicksToLive = maxTicksToExist > -1 ? maxTicksToExist : 100;
                if (ticksExisted >= maxTicksToLive && !world.isRemote) {
                    remove();
                    return;
                }
            }
        }
        if (getIsShootingStar()) {
            setMotion(getMotion().add(0, -0.1f, 0));
            if (getMotion().y < -2f)
                setMotion(getMotion().x, -2f, getMotion().z);
        }//TODO: @IchHabeHunger54

        /*if (worldObj.isRemote){
            if (getIsMoonstoneMeteor()){
                AMParticle fire = (AMParticle)ArsMagica2.proxy.particleManager.spawn(worldObj, "explosion_2", getPosX(), getPosY(), getPosZ());
                if (fire != null){
                    fire.setMaxAge(20);
                    fire.setRGBColorF(1, 1, 1);
                    fire.setParticleScale(2);
                    fire.AddParticleController(new ParticleHoldPosition(fire, 20, 1, false));
                    fire.AddParticleController(new ParticleColorShift(fire, 1, false).SetShiftSpeed(0.1f).SetColorTarget(0.01f, 0.01f, 0.01f).SetEndOnReachingTargetColor().setKillParticleOnFinish(false));
                }
            } else if (getIsShootingStar()){

                int color = -1;
                if (getSpellStack() != null){
                    if (SpellUtils.modifierIsPresent(SpellModifiers.COLOR, getSpellStack())){
                        List<SpellModifier> mods = SpellUtils.getModifiersForStage(getSpellStack(), -1);
                        for (SpellModifier mod : mods){
                            if (mod instanceof Color){
                                color = (int)mod.getModifier(SpellModifiers.COLOR, null, null, null, getSpellStack().getTagCompound());
                            }
                        }
                    }
                }

                for (float i = 0; i < Math.abs(getMotion().y); i += 0.1f){
                    AMParticle star = (AMParticle)ArsMagica2.proxy.particleManager.spawn(worldObj, "ember", getPosX() + getMotion().x * i, getPosY() + getMotion().y * i, getPosZ() + getMotion().z * i);
                    if (star != null){
                        star.setMaxAge(22);
                        float clrMod = Minecraft.getMinecraft().theWorld.rand.nextFloat();
                        int finalColor = -1;
                        if (color == -1)
                            finalColor = MathUtilities.colorFloatsToInt(0.24f * clrMod, 0.58f * clrMod, 0.71f * clrMod);
                        else{
                            float[] colors = MathUtilities.colorIntToFloats(color);
                            for (int c = 0; c < colors.length; ++c)
                                colors[c] = colors[c] * clrMod;
                            finalColor = MathUtilities.colorFloatsToInt(colors[0], colors[1], colors[2]);
                        }
                        star.setRGBColorI(finalColor);
                        star.AddParticleController(new ParticleHoldPosition(star, 20, 1, false));
                        star.AddParticleController(new ParticleChangeSize(star, 0.5f, 0.05f, 20, 1, false));
                    }
                }
            }
        }*/
        Vec3d vec3d = new Vec3d(getPosX(), getPosY(), getPosZ());
        Vec3d vec3d1 = new Vec3d(getPosX() + getMotion().x, getPosY() + getMotion().y, getPosZ() + getMotion().z);
        RayTraceResult movingobjectposition = world.rayTraceBlocks(new RayTraceContext(vec3d, vec3d1, RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, this));
        vec3d = new Vec3d(getPosX(), getPosY(), getPosZ());
        //vec3d1 = new Vec3d(getPosX() + getMotion().x, getPosY() + getMotion().y, getPosZ() + getMotion().z);
        vec3d1 = new Vec3d(movingobjectposition.getHitVec().x, movingobjectposition.getHitVec().y, movingobjectposition.getHitVec().z);
        Entity entity = null;
        List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(this, getBoundingBox().expand(getMotion().x, getMotion().y, getMotion().z).expand(1, 1, 1));
        double d = 0;
        for (Entity value : list) {
            if (!value.canBeCollidedWith() || value.isEntityEqual(world.getEntityByID(getDataManager().get(OWNER))) && ticksExisted < 25) {
                continue;
            }
            float f2 = 0.3F;
            AxisAlignedBB axisalignedbb = value.getBoundingBox().expand(f2, f2, f2);
            RayTraceResult movingobjectposition1 = AxisAlignedBB.rayTrace(Collections.singletonList(axisalignedbb), vec3d, vec3d1, getPosition());
            if (movingobjectposition1 == null) {
                continue;
            }
            double d1 = vec3d.distanceTo(movingobjectposition1.getHitVec());
            if (d1 < d || d == 0) {
                entity = value;
                d = d1;
            }
        }
        if (entity != null) {
            movingobjectposition = new EntityRayTraceResult(entity);
        }
        hitObject(movingobjectposition);
        double tmpX = getPosX() + getMotion().x;
        double tmpY = getPosY() + getMotion().y;
        double tmpZ = getPosZ() + getMotion().z;
        this.setPosition(tmpX, tmpY, tmpZ);
        float f = MathHelper.sqrt(getMotion().x * getMotion().x + getMotion().z * getMotion().z);
        rotationYaw = (float) ((Math.atan2(getMotion().x, getMotion().z) * 180D) / 3.1415927410125732D);
        //noinspection StatementWithEmptyBody
        for (rotationPitch = (float) ((Math.atan2(getMotion().y, f) * 180D) / 3.1415927410125732D); rotationPitch - prevRotationPitch < -180F; prevRotationPitch -= 360F) {
        }
        //noinspection StatementWithEmptyBody
        for (; rotationPitch - prevRotationPitch >= 180F; prevRotationPitch += 360F) {
        }
        //noinspection StatementWithEmptyBody
        for (; rotationYaw - prevRotationYaw < -180F; prevRotationYaw -= 360F) {
        }
        //noinspection StatementWithEmptyBody
        for (; rotationYaw - prevRotationYaw >= 180F; prevRotationYaw += 360F) {
        }
        rotationPitch = prevRotationPitch + (rotationPitch - prevRotationPitch) * 0.2F;
        rotationYaw = prevRotationYaw + (rotationYaw - prevRotationYaw) * 0.2F;
        setPosition(getPosX(), getPosY(), getPosZ());
    }

    protected void hitObject(RayTraceResult movingobjectposition) {
        if (world.isRemote) {
            return;
        }
        if (getIsShootingStar()) {
            //AMNetHandler.INSTANCE.sendStarImpactToClients(getPosX(), getPosY() + ((movingobjectposition.getType() == RayTraceResult.Type.ENTITY) ? -((EntityRayTraceResult)movingobjectposition).getEntity().getEyeHeight() : 1.5f), getPosZ(), world, this.getSpellStack());
            List<LivingEntity> ents = world.getEntitiesWithinAABB(LivingEntity.class, getBoundingBox().expand(12, 5, 12));
            this.setPosition(getPosX(), getPosY() + 1, getPosZ());
            for (LivingEntity e : ents) {
                if (e == world.getEntityByID(getDataManager().get(OWNER))) continue;
                if (this.getDistance(e) < 12)
                    //noinspection ConstantConditions
                    SpellUtils.attackTargetSpecial(null, e, DamageSource.causeIndirectMagicDamage(world.getEntityByID(getDataManager().get(OWNER)), this), damage);
            }
        } else {
            if (movingobjectposition.getType() == RayTraceResult.Type.ENTITY && ((EntityRayTraceResult) movingobjectposition).getEntity() instanceof LivingEntity) {
                if (((EntityRayTraceResult) movingobjectposition).getEntity() == world.getEntityByID(getDataManager().get(OWNER)) || world.getEntityByID(getDataManager().get(OWNER)) == null)
                    return;
                //noinspection ConstantConditions
                ((EntityRayTraceResult) movingobjectposition).getEntity().attackEntityFrom(DamageSource.causeMobDamage((LivingEntity) world.getEntityByID(getDataManager().get(OWNER))), 10);
            } else if (movingobjectposition.getType() == RayTraceResult.Type.BLOCK) {
                if (this.getIsMoonstoneMeteor()) {
                    if (this.target == null) {
                        this.target = movingobjectposition.getHitVec();
                    }
                    this.world.createExplosion(this, this.target.getX(), this.target.getY(), this.target.getZ(), 0.8f, Explosion.Mode.NONE /*ArsMagica2.config.moonstoneMeteorsDestroyTerrain()*/);
                    int numOres = rand.nextInt(4) + 1;
                    for (int i = 0; i < numOres; ++i) {
                        generateSurfaceOreAtOffset(world, new BlockPos(target), i == 0);
                    }
//					if (this.world.isRemote){
//						for (Object player : world.playerEntities)
//							if (((EntityPlayer)player).getDistanceSqToEntity(this) < 4096)
//								CompendiumUnlockHandler.unlockEntry("moonstone_meteors");
//					}
                }
            }
        }
        this.remove();
    }

    private void generateSurfaceOreAtOffset(World world, BlockPos pos, boolean force) {
        pos = pos.east(rand.nextInt(4) - 2);
        pos = pos.south(rand.nextInt(4) - 2);
        while (!world.isAirBlock(pos) && pos.getY() < world.getActualHeight())
            pos = pos.up();
        if (rand.nextInt(4) < 2 || force)
            //noinspection ConstantConditions
            world.setBlockState(pos, ModBlocks.MOONSTONE_ORE.get().getDefaultState());
        else
            world.setBlockState(pos, Blocks.STONE.getDefaultState());
    }

    @Override
    public boolean attackEntityFrom(@Nonnull DamageSource par1DamageSource, float par2) {
        return false;
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        this.damage = compound.getFloat("star_damage");
        if (compound.getBoolean("MoonstoneMeteor"))
            this.setMoonstoneMeteor();
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        compound.putFloat("star_damage", damage);
        compound.putBoolean("MoonstoneMeteor", getIsMoonstoneMeteor());
    }

    @Nonnull
    @Override
    public IPacket<?> createSpawnPacket() {
        return new SSpawnObjectPacket(this, getDataManager().get(OWNER));
    }
}
