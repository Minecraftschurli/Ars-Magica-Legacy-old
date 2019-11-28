package minecraftschurli.arsmagicalegacy.objects.entity;

import minecraftschurli.arsmagicalegacy.api.spellsystem.*;
import minecraftschurli.arsmagicalegacy.init.*;
import minecraftschurli.arsmagicalegacy.objects.spell.modifier.*;
import minecraftschurli.arsmagicalegacy.util.*;
import net.minecraft.block.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.network.*;
import net.minecraft.network.datasync.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;

import java.util.*;

/**
 * @author Minecraftschurli
 * @version 2019-11-28
 */
public class EntityThrownRock extends Entity {
    private LivingEntity throwingEntity;
    private int maxTicksToExist;
    private Vec3d target = null;
    private float damage;

    private static final DataParameter<Boolean> IS_MOONSTONE_METEOR = EntityDataManager.createKey(EntityThrownRock.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> IS_SHOOTING_STAR = EntityDataManager.createKey(EntityThrownRock.class, DataSerializers.BOOLEAN);
    private static final DataParameter<ItemStack> SPELL_STACK = EntityDataManager.createKey(EntityThrownRock.class, DataSerializers.ITEMSTACK);


    public EntityThrownRock(World worldIn) {
        this(ModEntities.THROWN_ROCK.get(), worldIn);
    }

    public EntityThrownRock(EntityType<?> entityTypeIn, World worldIn) {
        super(entityTypeIn, worldIn);
        ticksExisted = 0;
        maxTicksToExist = 120;
        this.noClip = true;
    }
    public void setMoonstoneMeteor(){
        this.dataManager.set(IS_MOONSTONE_METEOR, true);
    }

    public void setShootingStar(float damage){
        this.dataManager.set(IS_SHOOTING_STAR, true);
        this.damage = damage;
    }

    public void setMoonstoneMeteorTarget(Vec3d target){
        this.target = target;
    }

    public boolean getIsMoonstoneMeteor(){
        return dataManager.get(IS_MOONSTONE_METEOR);
    }

    public boolean getIsShootingStar(){
        return dataManager.get(IS_SHOOTING_STAR) ;
    }

    public EntityThrownRock(World world, LivingEntity entityLiving, double projectileSpeed){
        this(world);
        this.noClip = true;
        throwingEntity = entityLiving;
        //setSize(0.25F, 0.25F);
        setLocationAndAngles(entityLiving.posX, entityLiving.posY + entityLiving.getEyeHeight(), entityLiving.posZ, entityLiving.rotationYaw, entityLiving.rotationPitch);
        posX -= MathHelper.cos((rotationYaw / 180F) * 3.141593F) * 0.16F;
        posY -= 0.10000000149011612D;
        posZ -= MathHelper.sin((rotationYaw / 180F) * 3.141593F) * 0.16F;
        setPosition(posX, posY, posZ);
        float f = 0.05F;
        setMotion(
                -MathHelper.sin((rotationYaw / 180F) * 3.141593F) * MathHelper.cos((rotationPitch / 180F) * 3.141593F) * f,
                -MathHelper.sin((rotationPitch / 180F) * 3.141593F) * f,
                MathHelper.cos((rotationYaw / 180F) * 3.141593F) * MathHelper.cos((rotationPitch / 180F) * 3.141593F) * f
        );
        maxTicksToExist = 100;
        setHeading(getMotion().x, getMotion().y, getMotion().z, projectileSpeed, projectileSpeed);
    }

    public void setHeading(double movementX, double movementY, double movementZ, double projectileSpeed, double projectileSpeed2){
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
        prevRotationYaw = rotationYaw = (float)((Math.atan2(movementX, movementZ) * 180D) / Math.PI);
        prevRotationPitch = rotationPitch = (float)((Math.atan2(movementY, f1) * 180D) / Math.PI);
    }

    public void setThrowingEntity(LivingEntity thrower){
        this.throwingEntity = thrower;
    }

    @Override
    protected void registerData(){
        this.dataManager.register(IS_MOONSTONE_METEOR, false);
        this.dataManager.register(IS_SHOOTING_STAR, false);
        this.dataManager.register(SPELL_STACK, new ItemStack(ModItems.SPELL.get()));
    }

    private ItemStack getSpellStack(){
        return this.dataManager.get(SPELL_STACK);
    }

    public void setSpellStack(ItemStack spellStack){
        this.dataManager.set(SPELL_STACK, spellStack);
    }

    @Override
    public void tick(){
        super.tick();
        if (this.target != null && this.posY > this.target.y){
            double deltaX = this.posX - target.x;
            double deltaY = this.posY - target.y;
            double deltaZ = this.posZ - target.z;

            double angle = Math.atan2(deltaZ, deltaX);

            double hDist = Math.sqrt(deltaX * deltaX + deltaZ * deltaZ);

            double vAngle = Math.atan2(deltaY, hDist);

            setMotion(-Math.cos(angle) * 0.2, -Math.sin(vAngle) * 2.5, -Math.sin(angle) * 0.2);
        }

        if (!getIsMoonstoneMeteor() && !getIsShootingStar()){
            if (!world.isRemote && (throwingEntity == null || !throwingEntity.isAlive())){
                remove();
            }else{
                ticksExisted++;
                int maxTicksToLive = maxTicksToExist > -1 ? maxTicksToExist : 100;
                if (ticksExisted >= maxTicksToLive && !world.isRemote){
                    remove();
                    return;
                }
            }
        }

        if (getIsShootingStar()){
            setMotion(getMotion().x, getMotion().y - 0.1f, getMotion().z);
            if (getMotion().y < -2f)
                setMotion(getMotion().x, -2f, getMotion().z);
        }

        if (world.isRemote){
            /*if (getIsMoonstoneMeteor()){
                AMParticle fire = (AMParticle)ArsMagica2.proxy.particleManager.spawn(world, "explosion_2", posX, posY, posZ);
                if (fire != null){
                    fire.setMaxAge(20);
                    fire.setRGBColorF(1, 1, 1);
                    fire.setParticleScale(2.0f);
                    fire.AddParticleController(new ParticleHoldPosition(fire, 20, 1, false));
                    fire.AddParticleController(new ParticleColorShift(fire, 1, false).SetShiftSpeed(0.1f).SetColorTarget(0.01f, 0.01f, 0.01f).SetEndOnReachingTargetColor().setKillParticleOnFinish(false));
                }
            }else*/ if (getIsShootingStar()){

                int color = -1;
                getSpellStack();
                if (SpellUtils.modifierIsPresent(SpellModifiers.COLOR, getSpellStack())){
                    List<SpellModifier> mods = SpellUtils.getModifiersForStage(getSpellStack(), -1);
                    for (SpellModifier mod : mods){
                        if (mod instanceof Color){
                            color = (int)mod.getModifier(SpellModifiers.COLOR, null, null, null, getSpellStack().getTag());
                        }
                    }
                }

                /*for (float i = 0; i < Math.abs(motionY); i += 0.1f){
                    AMParticle star = (AMParticle)ArsMagica2.proxy.particleManager.spawn(world, "ember", posX + motionX * i, posY + motionY * i, posZ + motionZ * i);
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
                }*/
            }
        }

        Vec3d vec3d = getPositionVec();
        Vec3d vec3d1 = getPositionVec().add(getMotion());
        RayTraceResult movingobjectposition = world.rayTraceBlocks(new RayTraceContext(vec3d, vec3d1, RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, this));
        vec3d = getPositionVec();
        vec3d1 = getPositionVec().add(getMotion());
        if (movingobjectposition != null){
            vec3d1 = movingobjectposition.getHitVec();
        }
        Entity entity = null;
        List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(this, getBoundingBox().grow(getMotion().x, getMotion().y, getMotion().z).expand(1.0D, 1.0D, 1.0D));
        double d = 0.0D;
        for (Entity entity1 : list) {
            if (!entity1.canBeCollidedWith() || entity1.isEntityEqual(throwingEntity) && ticksExisted < 25) {
                continue;
            }
            float f2 = 0.3F;
            AxisAlignedBB axisalignedbb = entity1.getBoundingBox().expand(f2, f2, f2);
            RayTraceResult movingobjectposition1 = null;//axisalignedbb.calculateIntercept(vec3d, vec3d1);
            if (movingobjectposition1 == null) {
                continue;
            }
            double d1 = vec3d.distanceTo(movingobjectposition1.getHitVec());
            if (d1 < d || d == 0.0D) {
                entity = entity1;
                d = d1;
            }
        }

        if (entity != null){
            movingobjectposition = new EntityRayTraceResult(entity);
        }
        if (movingobjectposition != null){
            hitObject(movingobjectposition);
        }

        posX += getMotion().x;
        posY += getMotion().y;
        posZ += getMotion().z;
        float f = MathHelper.sqrt(getMotion().x * getMotion().x + getMotion().z * getMotion().z);
        rotationYaw = (float)((Math.atan2(getMotion().x, getMotion().z) * 180D) / 3.1415927410125732D);
        for (rotationPitch = (float)((Math.atan2(getMotion().y, f) * 180D) / 3.1415927410125732D); rotationPitch - prevRotationPitch < -180F; prevRotationPitch -= 360F){}
        for (; rotationPitch - prevRotationPitch >= 180F; prevRotationPitch += 360F){}
        for (; rotationYaw - prevRotationYaw < -180F; prevRotationYaw -= 360F){}
        for (; rotationYaw - prevRotationYaw >= 180F; prevRotationYaw += 360F){}
        rotationPitch = prevRotationPitch + (rotationPitch - prevRotationPitch) * 0.2F;
        rotationYaw = prevRotationYaw + (rotationYaw - prevRotationYaw) * 0.2F;
        setPosition(posX, posY, posZ);
    }



    protected void hitObject(RayTraceResult movingobjectposition){
        if (world.isRemote){
            return;
        }


        if (getIsShootingStar()){
            //AMNetHandler.INSTANCE.sendStarImpactToClients(posX, posY + ((movingobjectposition.getType() == RayTraceResult.Type.ENTITY) ? -((EntityRayTraceResult)movingobjectposition).getEntity().getEyeHeight() : 1.5f), posZ, world, this.getSpellStack());
            List<LivingEntity> ents = world.getEntitiesWithinAABB(LivingEntity.class, getBoundingBox().expand(12, 5, 12));
            this.posY++;
            for (LivingEntity e : ents){
                if (e == throwingEntity) continue;
                if (this.getDistance(e) < 12)
                    SpellUtils.attackTargetSpecial(null, e, DamageSource.causeIndirectMagicDamage(throwingEntity, this), damage);
            }
        }else{

            ((EntityRayTraceResult) movingobjectposition).getEntity();
            if (((EntityRayTraceResult)movingobjectposition).getEntity() instanceof LivingEntity){
                if (((EntityRayTraceResult) movingobjectposition).getEntity() == throwingEntity || throwingEntity == null) return;
                if (throwingEntity != null){
                    ((EntityRayTraceResult) movingobjectposition).getEntity().attackEntityFrom(DamageSource.causeMobDamage(throwingEntity), 10);
                }
            }else if (movingobjectposition.getType() == RayTraceResult.Type.BLOCK){
                if (this.getIsMoonstoneMeteor()){

                    if (this.target == null){
                        this.target = movingobjectposition.getHitVec();
                    }
                    this.world.createExplosion(this, this.target.getX(), this.target.getY(), this.target.getZ(), 0.8f, Explosion.Mode.NONE /*ArsMagica2.config.moonstoneMeteorsDestroyTerrain()*/);

                    int numOres = rand.nextInt(4) + 1;

                    for (int i = 0; i < numOres; ++i){
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

    private void generateSurfaceOreAtOffset(World world, BlockPos pos, boolean force){
        pos = pos.east(rand.nextInt(4) - 2);
        pos = pos.south(rand.nextInt(4) - 2);

        while (!world.isAirBlock(pos) && pos.getY() < world.getActualHeight())
            pos = pos.up();

        if (rand.nextInt(4) < 2 || force)
            world.setBlockState(pos, ModBlocks.MOONSTONE_ORE.get().getDefaultState());
        else
            world.setBlockState(pos, Blocks.STONE.getDefaultState());
    }

    @Override
    public boolean attackEntityFrom(DamageSource par1DamageSource, float par2){
        return false;
    }

    /**
     * Reads the entity from NBT (calls an abstract helper method to read specialized data)
     *
     * @param compound
     */
    @Override
    public void readAdditional(CompoundNBT compound) {
        this.damage = compound.getFloat("star_damage");
        if (compound.getBoolean("MoonstoneMeteor"))
            this.setMoonstoneMeteor();
    }

    /**
     * Writes this entity to NBT, unless it has been removed. Also writes this entity's passengers, and the entity type
     * ID (so the produced NBT is sufficient to recreate the entity).
     * <p>
     * Generally, {@link #writeUnlessPassenger} or {@link #writeWithoutTypeId} should be used instead of this method.
     *
     * @param compound
     * @return True if the entity was written (and the passed compound should be saved); false if the entity was not
     * written.
     */
    @Override
    public void writeAdditional(CompoundNBT compound) {
        compound.putFloat("star_damage", damage);
        compound.putBoolean("MoonstoneMeteor", getIsMoonstoneMeteor());
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return null;
    }
}
