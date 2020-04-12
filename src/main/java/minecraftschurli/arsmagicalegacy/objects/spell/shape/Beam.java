package minecraftschurli.arsmagicalegacy.objects.spell.shape;

import minecraftschurli.arsmagicalegacy.api.spell.SpellCastResult;
import minecraftschurli.arsmagicalegacy.api.spell.SpellModifiers;
import minecraftschurli.arsmagicalegacy.api.spell.SpellShape;
import minecraftschurli.arsmagicalegacy.api.util.NBTUtil;
import minecraftschurli.arsmagicalegacy.util.EntityUtil;
import minecraftschurli.arsmagicalegacy.util.SpellUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.dragon.EnderDragonPartEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.EnumSet;

public final class Beam extends SpellShape {
//    private final HashMap<Integer, AMBeam> beams;
//    public Beam() {
//        beams = new HashMap<Integer, AMBeam>();
//    }

    @Override
    public SpellCastResult beginStackStage(Item item, ItemStack stack, LivingEntity caster, LivingEntity target, World world, double x, double y, double z, Direction side, boolean giveXP, int useCount) {
        boolean shouldApplyEffectBlock = useCount % 5 == 0;
        boolean shouldApplyEffectEntity = useCount % 10 == 0;
        double range = SpellUtil.modifyDoubleAdd(stack, caster, target, world, SpellModifiers.RANGE);
        boolean targetWater = SpellUtil.hasModifier(SpellModifiers.TARGET_NONSOLID_BLOCKS, stack);
        RayTraceResult mop = EntityUtil.getMovingObjectPosition(caster, world, range, true, targetWater);
        SpellCastResult result = null;
        Vec3d beamHitVec;
        Vec3d spellVec;
        if (mop.getType() == RayTraceResult.Type.ENTITY) {
            if (shouldApplyEffectEntity && !world.isRemote) {
                Entity e = ((EntityRayTraceResult) mop).getEntity();
                if (e instanceof EnderDragonPartEntity && ((EnderDragonPartEntity) e).dragon != null)
                    e = ((EnderDragonPartEntity) e).dragon;
                result = SpellUtil.applyStageEntity(stack, caster, world, e, giveXP);
                if (result != SpellCastResult.SUCCESS) return result;
            }
            float rng = (float) mop.getHitVec().distanceTo(new Vec3d(caster.getPosX(), caster.getPosY(), caster.getPosZ()));
            beamHitVec = EntityUtil.extrapolateEntityLook(caster, rng);
            spellVec = beamHitVec;
        } else {
            if (shouldApplyEffectBlock && !world.isRemote) {
                result = SpellUtil.applyStageBlock(stack, caster, world, ((BlockRayTraceResult) mop).getPos(), ((BlockRayTraceResult) mop).getFace(), mop.getHitVec().getX(), mop.getHitVec().getY(), mop.getHitVec().getZ(), giveXP);
                if (result != SpellCastResult.SUCCESS) return result;
            }
//            beamHitVec = mop.getHitVec();
            spellVec = new Vec3d(((BlockRayTraceResult) mop).getPos());
        }
//        if (world.isRemote) {
//            AMBeam beam = beams.get(caster.getEntityId());
//            double startX = caster.getPosX();
//            double startY = caster.getPosY() + caster.getEyeHeight() - 0.2f;
//            double startZ = caster.getPosZ();
//            Affinity affinity = SpellUtils.getMainShiftForStack(stack);
//            int color = -1;
//            if (SpellUtils.hasModifier(SpellModifiers.COLOR, stack)) {
//                List<SpellModifier> mods = SpellUtils.getModifiers(stack, -1);
//                for (SpellModifier mod : mods) if (mod instanceof Color) color = (int) mod.getModifier(SpellModifiers.COLOR, null, null, null, stack.getTag());
//            }
//            if (beam != null){
//                if (!beam.isAlive() || caster.getDistanceSq(beam.getPosX(), beam.getPosY(), beam.getPosZ()) > 4) beams.remove(caster.getEntityId());
//                else beam.setBeamLocationAndTarget(startX, startY, startZ, beamHitVec.getX(), beamHitVec.getY(), beamHitVec.getZ());
//            } else {
//                if (affinity.equals(Affinity.LIGHTNING)) ArsMagica2.proxy.particleManager.BoltFromEntityToPoint(world, caster, beamHitVec.getX(), beamHitVec.getY(), beamHitVec.getZ(), 1, color == -1 ? affinity.getColor() : color);
//                else {
//                    beam = (AMBeam)ArsMagica2.proxy.particleManager.BeamFromEntityToPoint(world, caster, beamHitVec.getX(), beamHitVec.getY(), beamHitVec.getZ(), color == -1 ? affinity.getColor() : color);
//                    if (beam != null) {
//                        if (Minecraft.getInstance().gameSettings.thirdPersonView == 0) beam.setFirstPersonPlayerCast();
//                        beams.put(caster.getEntityId(), beam);
//                    }
//                }
//            }
//            for (int i = 0; i < ArsMagica2.config.getGFXLevel() + 1; i++){
//                AMParticle particle = (AMParticle)ArsMagica2.proxy.particleManager.spawn(world, AMParticleDefs.getParticleForAffinity(affinity), beamHitVec.getX(), beamHitVec.getY(), beamHitVec.getZ());
//                if (particle != null){
//                    particle.setMaxAge(2);
//                    particle.setParticleScale(0.1f);
//                    particle.setIgnoreMaxAge(false);
//                    if (color != -1) particle.setRGBColorI(color);
//                    particle.AddParticleController(new ParticleMoveOnHeading(particle, world.rand.nextDouble() * 360, world.rand.nextDouble() * 360, world.rand.nextDouble() * 0.2 + 0.02f, 1, false));
//                }
//            }
//        }
        if (result != null && (mop.getType() == RayTraceResult.Type.ENTITY ? shouldApplyEffectEntity : shouldApplyEffectBlock)) {
            NBTUtil.getAMLTag(stack.getTag()).putInt("CurrentGroup", NBTUtil.getAMLTag(stack.getTag()).getInt("CurrentGroup") + 1);
            if (mop instanceof BlockRayTraceResult)
                return SpellUtil.applyStage(stack, caster, target, spellVec.getX(), spellVec.getY(), spellVec.getZ(), ((BlockRayTraceResult) mop).getFace(), world, true, giveXP, 0);
        }
        return SpellCastResult.SUCCESS_REDUCE_MANA;
    }

    @Override
    public EnumSet<SpellModifiers> getModifiers() {
        return EnumSet.of(SpellModifiers.RANGE, SpellModifiers.TARGET_NONSOLID_BLOCKS);
    }

    @Override
    public boolean isChanneled() {
        return true;
    }

    @Override
    public boolean isPrincipumShape() {
        return false;
    }

    @Override
    public boolean isTerminusShape() {
        return false;
    }

    @Override
    public float manaCostMultiplier(ItemStack spellStack) {
        int stages = SpellUtil.stageNum(spellStack);
        for (int i = SpellUtil.currentStage(spellStack); i < stages; i++) {
            SpellShape shape = SpellUtil.getShape(spellStack, i);
            if (!shape.equals(this)) continue;
            if (shape.getClass() == Beam.class) return 1;
        }
        return 0.2f;
    }
}
