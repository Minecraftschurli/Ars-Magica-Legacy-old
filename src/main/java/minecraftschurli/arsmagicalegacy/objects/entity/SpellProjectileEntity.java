package minecraftschurli.arsmagicalegacy.objects.entity;

import minecraftschurli.arsmagicalegacy.api.spellsystem.ISpellIngredient;
import minecraftschurli.arsmagicalegacy.api.spellsystem.SpellModifier;
import minecraftschurli.arsmagicalegacy.init.ModItems;
import minecraftschurli.arsmagicalegacy.util.SpellHelper;
import minecraftschurli.arsmagicalegacy.util.SpellUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.dragon.EnderDragonPartEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.math.*;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Minecraftschurli
 * @version 2019-11-17
 */
//public class SpellProjectileEntity extends Entity implements IProjectile {
//    private static final int maxTicksToExist = -1;
//    private static final int DW_BOUNCE_COUNTER = 21;
//    private static final int DW_GRAVITY = 22;
//    private static final int DW_EFFECT = 23;
//    private static final int DW_ICON_NAME = 24;
//    private static final int DW_PIERCE_COUNT = 25;
//    private static final int DW_COLOR = 26;
//    private static final int DW_SHOOTER = 27;
//    private static final int DW_TARGETGRASS = 28;
//    private static final int DW_HOMING = 29;
//    private static final int DW_HOMING_TARGET = 30;
//    private static final float GRAVITY_TERMINAL_VELOCITY = -2.0f;
//    private static final double friction_coefficient = 0.8d;
//    private int ticksExisted;
//    private int originalBounceCount;
//    private ArrayList<Vec3d> blockhits;
//    private ArrayList<Integer> entityHits;
//    private String particleType;
//
//    public SpellProjectileEntity(World world) {
//        super(null, world);
//        ticksExisted = 0;
//        this.noClip = true;
//    }
//
//    public SpellProjectileEntity(World world, LivingEntity entityLiving, double projectileSpeed) {
//        this(world);
//        setSize(0.25F, 0.25F);
//        setLocationAndAngles(entityLiving.getPosition().getX(), entityLiving.getPosition().getY() + entityLiving.getEyeHeight(), entityLiving.getPosition().getZ(), entityLiving.rotationYaw, entityLiving.rotationPitch);
//        posX -= MathHelper.cos((rotationYaw / 180F) * 3.141593F) * 0.16F;
//        posY -= 0.10000000149011612D;
//        posZ -= MathHelper.sin((rotationYaw / 180F) * 3.141593F) * 0.16F;
//        setPosition(posX, posY, posZ);
//        float yOffset = 0.0F;
//        float f = 0.01F;
//        float motionX = -MathHelper.sin((rotationYaw / 180F) * 3.141593F) * MathHelper.cos((rotationPitch / 180F) * 3.141593F) * f;
//        float motionZ = MathHelper.cos((rotationYaw / 180F) * 3.141593F) * MathHelper.cos((rotationPitch / 180F) * 3.141593F) * f;
//        float motionY = -MathHelper.sin((rotationPitch / 180F) * 3.141593F) * f;
//        setSpellProjectileHeading(motionX, motionY, motionZ, projectileSpeed, projectileSpeed);
//    }
//
//    protected void entityInit() {
//        this.dataManager.addObject(DW_BOUNCE_COUNTER, 0);
//        this.dataManager.addObject(DW_GRAVITY, 0);
//        this.dataManager.addObject(DW_EFFECT, new ItemStack(ModItems.SPELL.get()));
//        this.dataManager.addObject(DW_ICON_NAME, "arcane");
//        this.dataManager.addObject(DW_PIERCE_COUNT, 0);
//        this.dataManager.addObject(DW_COLOR, 0xFFFFFF);
//        this.dataManager.addObject(DW_SHOOTER, 0);
//        this.dataManager.addObject(DW_TARGETGRASS, (byte) 0);
//        this.dataManager.addObject(DW_HOMING, (byte) 0);
//        this.dataManager.addObject(DW_HOMING_TARGET, -1);
//        blockhits = new ArrayList<Vec3d>();
//        entityHits = new ArrayList<Integer>();
//    }
//
//    public void setSpellProjectileHeading(double movementX, double movementY, double movementZ, double projectileSpeed, double projectileSpeed2) {
//        float f = MathHelper.sqrt(movementX * movementX + movementY * movementY + movementZ * movementZ);
//        movementX /= f;
//        movementY /= f;
//        movementZ /= f;
//        movementX *= projectileSpeed;
//        movementY *= projectileSpeed;
//        movementZ *= projectileSpeed;
//        f = MathHelper.sqrt(movementX * movementX + movementZ * movementZ);
//        prevRotationYaw = rotationYaw = (float) ((Math.atan2(movementX, movementZ) * 180D) / Math.PI);
//        prevRotationPitch = rotationPitch = (float) ((Math.atan2(movementY, f) * 180D) / Math.PI);
//    }
//
//    public void setHomingTarget(LivingEntity entity) {
//        if (!this.world.isRemote) this.dataManager.updateObject(DW_HOMING_TARGET, entity.getEntityId());
//    }
//
//    private void findHomingTarget() {
//        List<LivingEntity> entities = this.world.getEntitiesWithinAABB(LivingEntity.class, AxisAlignedBB.func_216363_a(new MutableBoundingBox((int) this.posX - 15, (int) this.posY - 15, (int) this.posZ - 15, (int) this.posX + 15, (int) this.posY + 15, (int) this.posZ + 15)));
//        LivingEntity closest = null;
//        double curShortestDistance = 900;
//        Vec3d me = new Vec3d(getPosition().getX(), getPosition().getY(), getPosition().getZ());
//        for (LivingEntity e : entities) {
//            if (e == this.getShootingEntity()) continue;
//            double distance = new Vec3d(e.getPosition().getX(), e.getPosition().getY(), e.getPosition().getZ()).distanceTo(me);
//            if (distance < curShortestDistance) {
//                curShortestDistance = distance;
//                closest = e;
//            }
//        }
//        if (closest != null) setHomingTarget(closest);
//    }
//
//    public void setTargetWater() {
//        if (!this.world.isRemote)
//            this.dataManager.updateObject(DW_TARGETGRASS, (byte) 1);
//    }
//
//    private boolean targetWater() {
//        return this.dataManager.getWatchableObjectByte(DW_TARGETGRASS) == (byte) 1;
//    }
//
//    private LivingEntity getShootingEntity() {
//        int entityID = this.dataManager.getWatchableObjectInt(DW_SHOOTER);
//        Entity e = this.world.getEntityByID(entityID);
//        if (e != null && e instanceof LivingEntity)
//            return (LivingEntity) e;
//        return null;
//    }
//
//    public void setShootingEntity(LivingEntity caster) {
//        if (!this.world.isRemote) {
//            this.dataManager.updateObject(DW_SHOOTER, caster.getEntityId());
//        }
//    }
//
//    private LivingEntity getHomingEntity() {
//        int entityID = this.dataManager.getWatchableObjectInt(DW_HOMING_TARGET);
//        Entity e = this.world.getEntityByID(entityID);
//        if (e != null && e instanceof LivingEntity)
//            return (LivingEntity) e;
//        return null;
//    }
//
//    public void moveTowards(Entity entity, float maxYaw, float maxPitch) {
//        double deltaX = entity.posX - this.posX;
//        double deltaZ = entity.posZ - this.posZ;
//        double deltaY;
//        if (entity instanceof LivingEntity) {
//            LivingEntity livingEntity = (LivingEntity) entity;
//            deltaY = livingEntity.posY + (double) livingEntity.getEyeHeight() - (this.posY + (double) this.getEyeHeight());
//        } else
//            deltaY = (entity.getBoundingBox().minY + entity.getBoundingBox().maxY) / 2.0D - (this.posY + (double) this.getEyeHeight());
//        double hypotenuse = MathHelper.sqrt(deltaX * deltaX + deltaZ * deltaZ);
//        float f2 = (float) (Math.atan2(deltaZ, deltaX));// * 180.0D / Math.PI) - 90.0F;
//        float f3 = (float) (Math.atan2(deltaY, hypotenuse));// * 180.0D / Math.PI);
//        this.rotationPitch = this.updateRotation(this.rotationPitch, f3, maxPitch);
//        this.rotationYaw = this.updateRotation(this.rotationYaw, f2, maxYaw);
//        this.motionX = Math.cos(rotationYaw) * 0.4;
//        this.motionZ = Math.sin(rotationYaw) * 0.4;
//        this.motionY = Math.sin(rotationPitch);
//    }
//
//    private float updateRotation(float f1, float f2, float f3) {
//        float f = MathHelper.wrapAngleTo180(f2 - f1);
//        if (f > f3) f = f3;
//        if (f < -f3) f = -f3;
//        return f1 + f;
//    }
//
//    public void onUpdate() {
//        if (!world.isRemote && (getShootingEntity() == null || getShootingEntity().onDeath(DamageSource.GENERIC)))
//            remove();
//        else {
//            ticksExisted++;
//            int maxTicksToLive = maxTicksToExist > -1 ? maxTicksToExist : 100;
//            if (ticksExisted >= maxTicksToLive && !world.isRemote) {
//                remove();
//                return;
//            }
//        }
//        //TODO Fix homing
//        if (this.dataManager.getWatchableObjectByte(DW_HOMING) != (byte) 0 && this.ticksExisted > 10) {
//            if (this.dataManager.getWatchableObjectInt(DW_HOMING_TARGET) == -1) findHomingTarget();
//            else {
//                LivingEntity homingTarget = getHomingEntity();
//                if (homingTarget != null && new Vec3d(this).distanceSqTo(new Vec3d(homingTarget)) > 2) this.moveTowards(homingTarget, 60, 60);
//            }
//        }
//        Vec3d vec3d = new Vec3d(posX, posY, posZ);
//        Vec3d vec3d1 = new Vec3d(posX + motionX, posY + motionY, posZ + motionZ);
//        EntityRayTraceResult movingobjectposition = null;
//        movingobjectposition = world.rayTraceBlocks(vec3d, vec3d1, true);
//        vec3d = new Vec3d(posX, posY, posZ);
//        vec3d1 = new Vec3d(posX + motionX, posY + motionY, posZ + motionZ);
//        if (movingobjectposition != null)
//            vec3d1 = new Vec3d(movingobjectposition.getHitVec().getX(), movingobjectposition.getHitVec().getY(), movingobjectposition.getHitVec().getZ());
//        Entity entity = null;
//        List list = world.getEntitiesWithinAABBExcludingEntity(this, getBoundingBox().addCoord(motionX, motionY, motionZ).expand(1.0D, 1.0D, 1.0D));
//        double d = 0.0D;
//        for (int j = 0; j < list.size(); j++) {
//            Entity entity1 = (Entity) list.get(j);
//            if (!entity1.canBeCollidedWith() || entity1.isEntityEqual(getShootingEntity())) continue;
//            float f2 = 0.3F;
//            AxisAlignedBB axisalignedbb = entity1.getBoundingBox().expand(f2, f2, f2);
//            EntityRayTraceResult movingobjectposition1 = axisalignedbb.calculateIntercept(vec3d, vec3d1);
//            if (movingobjectposition1 == null) continue;
//            double d1 = vec3d.distanceTo(movingobjectposition1.getHitVec());
//            if (d1 < d || d == 0.0D) {
//                entity = entity1;
//                d = d1;
//            }
//        }
//        if (entity != null) {
//            if (entity instanceof EnderDragonPartEntity && ((EnderDragonPartEntity) entity).dragon != null && ((EnderDragonPartEntity) entity).dragon instanceof LivingEntity)
//                entity = (LivingEntity) ((EnderDragonPartEntity) entity).dragon;
//            movingobjectposition = new EntityRayTraceResult(entity);
//        }
//        if (movingobjectposition != null) {
//            boolean doHit = true;
//            boolean pierce = this.getNumPierces() > 0;
//            if (movingobjectposition.typeOfHit == MovingObjectType.ENTITY && movingobjectposition.entityHit != null && movingobjectposition.entityHit instanceof LivingEntity) {
//                LivingEntity ent = (LivingEntity) movingobjectposition.entityHit;
//                if (ent.isPotionActive(BuffList.spellReflect.id) && !pierce) {
//                    doHit = false;
//                    this.setShootingEntity(ent);
//                    this.motionX = -this.motionX;
//                    this.motionY = -this.motionY;
//                    this.motionZ = -this.motionZ;
//                    if (!world.isRemote) ent.removePotionEffect(BuffList.spellReflect.id);
//                    setBounces(originalBounceCount);
////                    if (world.isRemote) for (int i = 0; i < 13; ++i) {
////                        AMParticle effect = (AMParticle) AMCore.instance.proxy.particleManager.spawn(world, "hr_lensflare", ent.posX + rand.nextDouble() - 0.5, ent.posY + ent.getEyeHeight() + rand.nextDouble() - 0.5, ent.posZ + rand.nextDouble() - 0.5);
////                        if (effect != null) {
////                            EntityPlayer player = AMCore.instance.proxy.getLocalPlayer();
////                            effect.setIgnoreMaxAge(true);
////                            if (player != null && ent != player) effect.setParticleScale(1.5f);
////                            effect.setRGBColorF(0.5f + rand.nextFloat() * 0.5f, 0.2f, 0.5f + rand.nextFloat() * 0.5f);
////                            effect.AddParticleController(new ParticleHoldPosition(effect, 100, 1, false));
////                        }
////                    }
//                }
//            } else if (movingobjectposition.typeOfHit == MovingObjectType.BLOCK && getBounces() > 0) {
//                doHit = false;
//                switch (movingobjectposition.sideHit) {
//                    case 0:
//                    case 1:
//                        motionY = motionY * friction_coefficient * -1;
//                        break;
//                    case 2:
//                    case 3:
//                        motionZ = motionZ * friction_coefficient * -1;
//                        break;
//                    case 4:
//                    case 5:
//                        motionX = motionX * friction_coefficient * -1;
//                        break;
//                }
//                this.setBounces(getBounces() - 1);
//            }
//            if (movingobjectposition.typeOfHit == MovingObjectType.BLOCK) {
//                Block block = world.getBlock(movingobjectposition.getEntity().getPosition().getX(), movingobjectposition.getEntity().getPosition().getY(), movingobjectposition.getEntity().getPosition().getZ());
//                AxisAlignedBB bb = block.getCollisionBoundingBoxFromPool(world, movingobjectposition.getEntity().getPosition().getX(), movingobjectposition.getEntity().getPosition().getY(), movingobjectposition.getEntity().getPosition().getZ());
//                if (bb == null && !SpellUtils.modifierIsPresent(SpellModifier.Type.TARGET_NONSOLID_BLOCKS, getEffectStack(), 0))
//                    doHit = false;
//            }
//            if (doHit) HitObject(movingobjectposition, pierce);
//        }
//        if (getGravity() < 0 && motionY > GRAVITY_TERMINAL_VELOCITY) this.motionY += getGravity();
//        else if (getGravity() > 0 && motionY < -GRAVITY_TERMINAL_VELOCITY) this.motionY -= getGravity();
//        posX += motionX;
//        posY += motionY;
//        posZ += motionZ;
//        float f = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
//        rotationYaw = (float) (Math.atan2(motionX, motionZ));
//        for (rotationPitch = (float) (Math.atan2(motionY, f)); rotationPitch - prevRotationPitch < -180F; prevRotationPitch -= 360F) {
//        }
//        for (; rotationPitch - prevRotationPitch >= 180F; prevRotationPitch += 360F) {
//        }
//        for (; rotationYaw - prevRotationYaw < -180F; prevRotationYaw -= 360F) {
//        }
//        for (; rotationYaw - prevRotationYaw >= 180F; prevRotationYaw += 360F) {
//        }
//        rotationPitch = prevRotationPitch + (rotationPitch - prevRotationPitch) * 0.2F;
//        rotationYaw = prevRotationYaw + (rotationYaw - prevRotationYaw) * 0.2F;
////        float f1 = 0.95F;
////        if (isInWater()) {
////            for (int k = 0; k < 4; k++) {
////                float f3 = 0.25F;
////                world.spawnParticle("bubble", posX - motionX * f3, posY - motionY * f3, posZ - motionZ * f3, motionX, motionY, motionZ);
////            }
////            f1 = 0.8F;
////        }
////        if (!AMCore.config.NoGFX() && world.isRemote && this.getShootingEntity() instanceof EntityPlayer) {
////            if (this.particleType == null || this.particleType.isEmpty())
////                particleType = AMParticleIcons.instance.getSecondaryParticleForAffinity(SpellUtils.mainAffinityFor(getEffectStack()));
////            AMParticle particle = (AMParticle) AMCore.proxy.particleManager.spawn(world, particleType, this.posX, this.posY, this.posZ);
////            if (particle != null) {
////                particle.addRandomOffset(0.3f, 0.3f, 0.3f);
////                particle.AddParticleController(new ParticleFloatUpward(particle, 0.1f, 0, 1, false));
////                particle.setMaxAge(10);
////                particle.setParticleScale(0.05f);
////            }
////        }
////        setPosition(posX, posY, posZ);
//>    }
//
//    protected void HitObject(EntityRayTraceResult movingobjectposition, boolean pierce) {
//
//        if (movingobjectposition.getEntity() != null) {
//            if (movingobjectposition.getEntity() == getShootingEntity() || getShootingEntity() == null) return;
//            Entity e = movingobjectposition.getEntity();
//            if (e instanceof EnderDragonPartEntity && ((EnderDragonPartEntity) e).dragon instanceof LivingEntity) e = (LivingEntity) ((EnderDragonPartEntity) e).dragon;
//            if (e instanceof LivingEntity && getShootingEntity() != null && !this.entityHits.contains(movingobjectposition.getEntity().getEntityId())) {
//                SpellHelper.applyStageToEntity(this.getEffectStack(), getShootingEntity(), this.world, e, 0, true);
//                SpellHelper.applyStackStage(SpellUtils.popStackStage(getEffectStack()), getShootingEntity(), (LivingEntity) e, new Vec3d(movingobjectposition.getHitVec().getX(), movingobjectposition.getHitVec().getY(), movingobjectposition.getHitVec().getZ()), Direction.UP, world, false, true, 0);
//                this.entityHits.add(movingobjectposition.getEntity().getEntityId());
//            }
//        } else {
//            Vec3d blockLoc = new Vec3d(movingobjectposition.getEntity().getPosition().getX(), movingobjectposition.getEntity().getPosition().getY(), movingobjectposition.getEntity().getPosition().getZ());
//            if (getShootingEntity() != null && !this.blockhits.contains(blockLoc)) {
//                SpellHelper.applyStageToGround(getEffectStack(), getShootingEntity(), world, movingobjectposition.getEntity().getPosition().getX(), movingobjectposition.getEntity().getPosition().getY(), movingobjectposition.getEntity().getPosition().getZ(), movingobjectposition.sideHit, movingobjectposition.getHitVec().getX(), movingobjectposition.getHitVec().getY(), movingobjectposition.getHitVec().getZ(), 0, true);
//                SpellHelper.applyStackStage(SpellUtils.popStackStage(getEffectStack()), getShootingEntity(), getShootingEntity(), movingobjectposition.getEntity().getPosition().getX(), movingobjectposition.getEntity().getPosition().getY(), movingobjectposition.getEntity().getPosition().getZ(), movingobjectposition.sideHit, world, false, true, 0);
//                this.blockhits.add(blockLoc);
//            }
//        }
//        this.setNumPierces(this.getNumPierces() - 1);
//        if (!world.isRemote && !pierce) this.remove();
//    }
//
//    public int getBounces() {
//        return this.dataManager.getWatchableObjectInt(DW_BOUNCE_COUNTER);
//    }
//
//    public void setBounces(int bounces) {
//        this.dataManager.updateObject(DW_BOUNCE_COUNTER, bounces);
//    }
//
//    public double getGravity() {
//        return this.dataManager.getWatchableObjectInt(DW_GRAVITY) / 1000D;
//    }
//
//    public void setGravity(double gravity) {
//        this.dataManager.updateObject(DW_GRAVITY, (int) (gravity * 1000));
//    }
//
//    public ItemStack getEffectStack() {
//        return this.dataManager.getWatchableObjectItemStack(DW_EFFECT);
//    }
//
//    public void setEffectStack(ItemStack stack) {
//        this.dataManager.updateObject(DW_EFFECT, stack);
////        if (!this.world.isRemote) {
////            Affinity aff = SpellUtils.mainAffinityFor(stack);
////            switch (aff) {
////                case AIR:
////                    setIcon("wind");
////                    break;
////                case ARCANE:
////                    setIcon("arcane");
////                    break;
////                case EARTH:
////                    setIcon("rock");
////                    break;
////                case ENDER:
////                    setIcon("pulse");
////                    setColor(0x550055);
////                    break;
////                case FIRE:
////                    setIcon("explosion_2");
////                    break;
////                case ICE:
////                    setIcon("ember");
////                    setColor(0x2299FF);
////                    break;
////                case LIFE:
////                    setIcon("sparkle");
////                    setColor(0x22FF44);
////                    break;
////                case LIGHTNING:
////                    setIcon("lightning_hand");
////                    break;
////                case NATURE:
////                    setIcon("plant");
////                    break;
////                case WATER:
////                    setIcon("water_ball");
////                    break;
////                case NONE:
////                default:
////                    setIcon("lens_flare");
////                    break;
////            }
////        }
//        if (SpellUtils.modifierIsPresent(SpellModifier.Type.COLOR, stack, 0)) {
//            SpellModifier[] mods = SpellUtils.getModifiersForStage(stack, 0);
//            int ordinalCount = 0;
//            for (SpellModifier mod : mods) {
////                if (mod instanceof Colour) {
////                    byte[] meta = SpellUtils.getModifierMetadataFromStack(stack, mod, 0, ordinalCount++);
////                    setColor((int) mod.getModifier(SpellModifier.Type.COLOR, null, null, null, meta));
////                }
//            }
//        }
//    }
//
//    public int getNumPierces() {
//        return this.dataManager.getWatchableObjectInt(DW_PIERCE_COUNT);
//    }
//
//    public void setNumPierces(int pierces) {
//        this.dataManager.updateObject(DW_PIERCE_COUNT, pierces);
//    }
//
//    public boolean isHoming() {
//        return this.dataManager.getWatchableObjectByte(DW_HOMING) != 0;
//    }
//
//    public void setHoming(int homing) {
//        this.dataManager.updateObject(DW_HOMING, (byte) homing);
//    }
//
//    public int getColor() {
//        return this.dataManager.getWatchableObjectInt(DW_COLOR);
//    }
//
//    private void setColor(int color) {
//        this.dataManager.updateObject(DW_COLOR, color);
//    }
//
//    @Override
//    public boolean isInRangeToRenderDist(double d) {
//        double d1 = getBoundingBox().getAverageEdgeLength() * 4D;
//        d1 *= 64D;
//        return d < d1 * d1;
//    }
//
//    @Override
//    public boolean canBeCollidedWith() {
//        return false;
//    }
//
//    @Override
//    public float getCollisionBorderSize() {
//        return 0.0F;
//    }
//
//    @Override
//    public float getShadowSize() {
//        return 0.0F;
//    }
//
//    @Override
//    protected void readEntityFromNBT(CompoundNBT nbttagcompound) {
//    }
//
//    @Override
//    protected void writeEntityToNBT(CompoundNBT nbttagcompound) {
//    }
//}
