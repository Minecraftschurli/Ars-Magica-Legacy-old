package minecraftschurli.arsmagicalegacy.objects.spell.shape;

import minecraftschurli.arsmagicalegacy.api.spellsystem.*;
import minecraftschurli.arsmagicalegacy.init.ModItems;
import minecraftschurli.arsmagicalegacy.objects.item.SpellItem;
import minecraftschurli.arsmagicalegacy.objects.spell.EssenceType;
import minecraftschurli.arsmagicalegacy.util.MathUtils;
import minecraftschurli.arsmagicalegacy.util.SpellHelper;
import minecraftschurli.arsmagicalegacy.util.SpellUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.dragon.EnderDragonPartEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.HashMap;

public class Beam extends SpellShape {
    private final HashMap beams;

    public Beam(){
        beams = new HashMap();
    }

    @Override
    public SpellCastResult beginStackStage(SpellItem item, ItemStack stack, LivingEntity caster, LivingEntity target, World world, Vec3d pos, Direction side, boolean giveXP, int useCount){
        boolean shouldApplyEffect = useCount % 10 == 0;
        double range = SpellUtils.getModifiedDoubleAdd(SpellModifier.Type.RANGE, stack, caster, target, world, 0);
        boolean targetWater = SpellUtils.modifierIsPresent(SpellModifier.Type.TARGET_NONSOLID_BLOCKS, stack, 0);
        EntityRayTraceResult mop = null;//item.getMovingObjectPosition(caster, world, range, true, targetWater);
        SpellCastResult result = null;
        Vec3d beamHitVec = null;
        Vec3d spellVec = null;
        if (mop == null) {
            beamHitVec = MathUtils.extrapolateEntityLook(caster, range);
            spellVec = beamHitVec;
        } else if (mop.getType() == RayTraceResult.Type.ENTITY){
            if (shouldApplyEffect){
                Entity e = mop.getEntity();
                if (e instanceof EnderDragonPartEntity && ((EnderDragonPartEntity)e).dragon instanceof LivingEntity) e = ((EnderDragonPartEntity)e).dragon;
                result = SpellHelper.applyStageToEntity(stack, caster, world, e, 0, giveXP);
                if (result != SpellCastResult.SUCCESS) return result;
            }
            float rng = (float)mop.getHitVec().distanceTo(new Vec3d(caster.posX, caster.posY, caster.posZ));
            beamHitVec = MathUtils.extrapolateEntityLook(caster, rng);
            spellVec = beamHitVec;
        } else {
            if (shouldApplyEffect){
//                result = SpellHelper.applyStageToGround(stack, caster, world, mop.getEntity().getPosition().getX(), mop.getEntity().getPosition().getY(), mop.getEntity().getPosition().getZ(), mop.sideHit, mop.getHitVec().getX(), mop.getHitVec().getY(), mop.getHitVec().getZ(), 0, giveXP);
                if (result != SpellCastResult.SUCCESS) return result;
            }
            beamHitVec = mop.getHitVec();
            spellVec = new Vec3d(mop.getEntity().getPosition().getX(), mop.getEntity().getPosition().getY(), mop.getEntity().getPosition().getZ());
        }
        if (world.isRemote && beamHitVec != null) {
//            AMBeam beam = (AMBeam)beams.get(caster.getEntityId());
            double startX = caster.posX;
            double startY = caster.posY + caster.getEyeHeight() - 0.2f;
            double startZ = caster.posZ;
            int color = -1;
//            Affinity affinity = SpellUtils.mainAffinityFor(stack);
//            if (SpellUtils.modifierIsPresent(new SpellParts.MODIFIER_COLOR., stack, 0)){
//                ISpellModifier[] mods = SpellUtils.getModifiersForStage(stack, 0);
//                int ordinalCount = 0;
//                for (ISpellModifier mod : mods){
//                    if (mod instanceof Color) {
//                        byte[] meta = SpellUtils.instance.getModifierMetadataFromStack(stack, mod, 0, ordinalCount++);
//                        color = (int)mod.getModifier(SpellModifiers.COLOR, null, null, null, meta);
//                    }
//                }
//            }
//            if(beam != null) {
//                if(beam.isDead || beam.getDistanceSqToEntity(caster) > 4) beams.remove(caster.getEntityId());
//                else {
//                    beam.setBeamLocationAndTarget(startX, startY, startZ, beamHitVec.getX(), beamHitVec.getY(), beamHitVec.getZ());
//                }
//            } else {
//                if (affinity == Affinity.LIGHTNING) {
//                    AMCore.instance.proxy.particleManager.BoltFromEntityToPoint(world, caster, beamHitVec.xCoord, beamHitVec.yCoord, beamHitVec.zCoord, 1, color == -1 ? affinity.color : color);
//                } else {
//                    beam = (AMBeam)AMCore.instance.proxy.particleManager.BeamFromEntityToPoint(world, caster, beamHitVec.getX(), beamHitVec.getY(), beamHitVec.getZ(), color == -1 ? affinity.color : color);
//                    if(beam != null) {
//                        if (AMCore.instance.proxy.getProxyUtils().isLocalPlayerInFirstPerson())
//                            beam.setFirstPersonPlayerCast();
//                        beams.put(caster.getEntityId(), beam);
//                    }
//                }
//            }
//            for (int i = 0; i < AMCore.config.getGFXLevel() + 1; ++i){
//                AMParticle particle = (AMParticle)AMCore.proxy.particleManager.spawn(world, AMParticleIcons.instance.getParticleForAffinity(affinity), beamHitVec.xCoord, beamHitVec.yCoord, beamHitVec.zCoord);
//                if (particle != null){
//                    particle.setMaxAge(2);
//                    particle.setParticleScale(0.1f);
//                    particle.setIgnoreMaxAge(false);
//                    if (color != -1)
//                        particle.setRGBColorI(color);
//                    particle.AddParticleController(new ParticleMoveOnHeading(particle, world.rand.nextDouble() * 360, world.rand.nextDouble() * 360, world.rand.nextDouble() * 0.2 + 0.02f, 1, false));
//                }
//            }
        }
        if (result != null && spellVec != null && shouldApplyEffect){
            ItemStack newItemStack = SpellUtils.popStackStage(stack);
//            return SpellHelper.applyStackStage(newItemStack, caster, target, spellVec.getX(), spellVec.getY(), spellVec.getZ(), mop != null ? mop.sideHit : 0, world, true, giveXP, 0);
        } else return SpellCastResult.SUCCESS_REDUCE_MANA;
        return null; //remove this when completing the method
    }

    @Override
    public boolean isChanneled(){
        return true;
    }

    @Override
    public ISpellIngredient[] getRecipeItems() {
        return new ISpellIngredient[]{
//                ModItems.standardFocus,
                new ItemStackSpellIngredient(new ItemStack(ModItems.TOPAZ_ORE.get())),
                new ItemStackSpellIngredient(new ItemStack(ModItems.TOPAZ_ORE.get())),
                new ItemStackSpellIngredient(new ItemStack(ModItems.VINTEUM_ORE.get())),
                new ItemStackSpellIngredient(new ItemStack(ModItems.AUM.get())),
                new EssenceSpellIngredient(EssenceType.NEUTRAL, 500)
        };
    }

    @Override
    public float getManaCostMultiplier(ItemStack spellStack){
        return 0.1f;
    }

    @Override
    public boolean isTerminusShape(){
        return false;
    }

    @Override
    public boolean isPrincipumShape(){
        return false;
    }

//    @Override
//    public String getSoundForAffinity(Affinity affinity, ItemStack stack, World world){
//        switch (affinity){
//            case AIR:
//                return "arsmagica2:spell.loop.air";
//            case ARCANE:
//                return "arsmagica2:spell.loop.arcane";
//            case EARTH:
//                return "arsmagica2:spell.loop.earth";
//            case ENDER:
//                return "arsmagica2:spell.loop.ender";
//            case FIRE:
//                return "arsmagica2:spell.loop.fire";
//            case ICE:
//                return "arsmagica2:spell.loop.ice";
//            case LIFE:
//                return "arsmagica2:spell.loop.life";
//            case LIGHTNING:
//                return "arsmagica2:spell.loop.lightning";
//            case NATURE:
//                return "arsmagica2:spell.loop.nature";
//            case WATER:
//                return "arsmagica2:spell.loop.water";
//            case NONE:
//            default:
//                return "arsmagica2:spell.loop.none";
//        }
//    }
}
