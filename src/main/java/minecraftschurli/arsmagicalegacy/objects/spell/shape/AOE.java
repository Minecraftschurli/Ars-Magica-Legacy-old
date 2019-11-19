package minecraftschurli.arsmagicalegacy.objects.spell.shape;

import minecraftschurli.arsmagicalegacy.api.spellsystem.SpellCastResult;
import minecraftschurli.arsmagicalegacy.api.spellsystem.SpellModifier;
import minecraftschurli.arsmagicalegacy.api.spellsystem.SpellShape;
import minecraftschurli.arsmagicalegacy.init.ModItems;
import minecraftschurli.arsmagicalegacy.objects.entity.SpellProjectileEntity;
import minecraftschurli.arsmagicalegacy.objects.item.SpellItem;
import minecraftschurli.arsmagicalegacy.util.SpellHelper;
import minecraftschurli.arsmagicalegacy.util.SpellUtils;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.dragon.EnderDragonPartEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

/**
 * @author Minecraftschurli
 * @version 2019-11-17
 */
public class AOE extends SpellShape {
    /**
     * Creates the target area/entity list and applies the effects to ground/mobs
     *
     * @param item     The spell being cast
     * @param stack    The itemstack representing the spell
     * @param caster   The caster of the spell
     * @param target   The specified target of the spell.  If this is not NULL, this is a forced target, and should be included with any other targets of the shape.  Otherwise the default spell shape logic should apply.
     * @param world    The world the spell is being cast in
     * @param pos      The coordinates of the spell's effect
     * @param side     The side the spell is applied on
     * @param giveXP   This is passed along to be given back to the SpellHelper where needed.
     * @param useCount The number of ticks the spell item has been in use for
     * @return The result of the spell cast.
     */
    @Override
    public SpellCastResult beginStackStage(SpellItem item, ItemStack stack, LivingEntity caster, LivingEntity target, World world, Vec3d pos, Direction side, boolean giveXP, int useCount) {
        double radius = SpellUtils.getModifiedDoubleAdd(1, stack, caster, target, world, 0, SpellModifier.Type.RADIUS);
        List<Entity> entities = world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(pos.x - radius, pos.y - radius, pos.z - radius, pos.x + radius, pos.y + radius, pos.z + radius));

        boolean appliedToAtLeastOneEntity = false;

        for (Entity e : entities){
            if (e == caster || e instanceof SpellProjectileEntity) continue;
            if (e instanceof EnderDragonPartEntity && ((EnderDragonPartEntity) e).dragon != null)
                e = ((EnderDragonPartEntity)e).dragon;
            if (SpellHelper.applyStageToEntity(stack, caster, world, e, 0, giveXP) == SpellCastResult.SUCCESS)
                appliedToAtLeastOneEntity = true;
        }

        if (side.getAxis() == Direction.Axis.Y){ //top/bottom
            if (world.isRemote)
                spawnAoEParticles(stack, caster, world, pos.x + 0.5f, pos.y + ((side == Direction.DOWN) ? 0.5f : (target != null ? target.getEyeHeight() : -2.0f)), pos.z + 0.5f, (int)radius);
            int gravityMagnitude = SpellUtils.countModifiers(SpellModifier.Type.GRAVITY, stack, 0);
            return applyStageHorizontal(stack, caster, world, (int)Math.floor(pos.x), (int)Math.ceil(pos.y), (int)Math.floor(pos.z), side, (int)Math.floor(radius), gravityMagnitude, giveXP);
        }else if (side.getAxis() == Direction.Axis.X){ // +/- x
            //if (side == 3) z--;
            if (world.isRemote)
                spawnAoEParticles(stack, caster, world, pos.x, pos.y, (side.getAxisDirection() == Direction.AxisDirection.POSITIVE) ? pos.z - 0.5f : pos.z + 1.5f, (int)radius);
            return applyStageVerticalZ(stack, caster, world, (int)Math.floor(pos.x), (int)Math.ceil(pos.y), (int)Math.floor(pos.z), side, (int)Math.floor(radius), giveXP);
        }else if (side.getAxis() == Direction.Axis.Z){ // +/- z
            //if (side == 5) x--;
            if (world.isRemote)
                spawnAoEParticles(stack, caster, world, pos.x, pos.y, (side.getAxisDirection() == Direction.AxisDirection.POSITIVE) ? pos.z - 0.5f : pos.z + 1.5f, (int)radius);
            return applyStageVerticalX(stack, caster, world, (int)Math.floor(pos.x), (int)Math.ceil(pos.y), (int)Math.floor(pos.z), side, (int)Math.floor(radius), giveXP);
        }

        if (appliedToAtLeastOneEntity){
            if (world.isRemote)
                spawnAoEParticles(stack, caster, world, pos.x, pos.y + 1, pos.z, (int)radius);
            return SpellCastResult.SUCCESS;
        }

        return SpellCastResult.EFFECT_FAILED;
    }

    private void spawnAoEParticles(ItemStack stack, LivingEntity caster, World world, double x, double y, double z, int radius){
        /*String pfxName = AMParticleIcons.instance.getParticleForAffinity(SpellUtils.instance.mainAffinityFor(stack));
        float speed = 0.08f * radius;

        int color = 0xFFFFFF;
        if (SpellUtils.instance.modifierIsPresent(SpellModifier.Type.COLOR, stack, 0)){
            SpellModifier[] mods = SpellUtils.instance.getModifiersForStage(stack, 0);
            int ordinalCount = 0;
            for (SpellModifier mod : mods){
                if (mod instanceof Color){
                    byte[] meta = SpellUtils.instance.getModifierMetadataFromStack(stack, mod, 0, ordinalCount++);
                    color = (int)mod.getModifier(SpellModifier.Type.COLOR, null, null, null, meta);
                }
            }
        }

        for (int i = 0; i < 360; i += AMCore.config.FullGFX() ? 20 : AMCore.config.LowGFX() ? 40 : 60){
            AMParticle effect = (AMParticle)AMCore.instance.proxy.particleManager.spawn(world, pfxName, x, y + 1.5f, z);
            if (effect != null){
                effect.setIgnoreMaxAge(true);
                effect.AddParticleController(new ParticleMoveOnHeading(effect, i, 0, speed, 1, false));
                effect.noClip = false;
                effect.setRGBColorI(color);
                effect.AddParticleController(new ParticleFadeOut(effect, 1, false).setFadeSpeed(0.05f).setKillParticleOnFinish(true));
                effect.AddParticleController(
                        new ParticleLeaveParticleTrail(effect, pfxName, false, 5, 1, false)
                                .addControllerToParticleList(new ParticleFadeOut(effect, 1, false).setFadeSpeed(0.1f).setKillParticleOnFinish(true))
                                .setParticleRGB_I(color)
                                .addRandomOffset(0.2f, 0.2f, 0.2f)
                );
            }
        }*/
    }

    private SpellCastResult applyStageHorizontal(ItemStack stack, LivingEntity caster, World world, int x, int y, int z, Direction face, int radius, int gravityMagnitude, boolean giveXP){

        for (int i = -radius; i <= radius; ++i){
            for (int j = -radius; j <= radius; ++j){
                int searchY = y;
                BlockPos block = new BlockPos(x + i, searchY, z + j);
                int searchDist = 0;
                if (gravityMagnitude > 0){
                    while (world.isAirBlock(block) && searchDist < gravityMagnitude){
                        searchY--;
                        searchDist++;
                        block = new BlockPos(x + i, searchY, z + j);
                    }
                }
                if (world.isAirBlock(block)) continue;
                SpellCastResult result = SpellHelper.applyStageToGround(stack, caster, world, x + i, searchY, z + j, face, x + i, searchY, z + i, 0, giveXP);
                if (result != SpellCastResult.SUCCESS)
                    return result;
            }
        }
        return SpellCastResult.SUCCESS;
    }

    private SpellCastResult applyStageVerticalX(ItemStack stack, LivingEntity caster, World world, int x, int y, int z, Direction face, int radius, boolean giveXP){
        for (int i = -radius; i <= radius; ++i){
            for (int j = -radius; j <= radius; ++j){
                if (world.isAirBlock(new BlockPos(x, y + i, z + j))) continue;
                SpellCastResult result = SpellHelper.applyStageToGround(stack, caster, world, x, y + i, z + j, face, x, y + i, z + j, 0, giveXP);
                if (result != SpellCastResult.SUCCESS)
                    return result;
            }
        }
        return SpellCastResult.SUCCESS;
    }

    private SpellCastResult applyStageVerticalZ(ItemStack stack, LivingEntity caster, World world, int x, int y, int z, Direction face, int radius, boolean giveXP){
        for (int i = -radius; i <= radius; ++i){
            for (int j = -radius; j <= radius; ++j){
                if (world.isAirBlock(new BlockPos(x + j, y + i, z))) continue;
                SpellCastResult result = SpellHelper.applyStageToGround(stack, caster, world, x + j, y + i, z, face, x + j, y + i, z, 0, giveXP);
                if (result != SpellCastResult.SUCCESS)
                    return result;
            }
        }
        return SpellCastResult.SUCCESS;
    }

    /**
     * Is this shape a valid shape for a channeled/maintained spell?
     */
    @Override
    public boolean isChanneled() {
        return false;
    }

    /**
     * Allows different shapes to vary the mana cost of a spell
     *
     * @param spellStack the itemstack representing the spell (useful if you want to vary based on added modifiers as well, for example)
     * @return
     */
    @Override
    public float getManaCostMultiplier(ItemStack spellStack) {
        int multiplier = 2;
        int radiusMods = 0;
        int stages = SpellUtils.numStages(spellStack);
        for (int i = 0; i < stages; ++i){
            if (!SpellUtils.getShapeForStage(spellStack, i).getRegistryName().equals(this.getRegistryName())) continue;

            SpellModifier[] mods = SpellUtils.getModifiersForStage(spellStack, i);
            for (SpellModifier modifier : mods){
                if (modifier.getAspectsModified().contains(SpellModifier.Type.RADIUS)){
                    radiusMods++;
                }
            }
        }
        return multiplier * (radiusMods + 1);
    }

    /**
     * Is the spell a terminus shape?  Return true if this component does not continue the spell chain when proccing.
     */
    @Override
    public boolean isTerminusShape() {
        return true;
    }

    /**
     * Is the shape a principal shape?  Return true if this spell requires another shape to proc (like runes and zones)
     */
    @Override
    public boolean isPrincipumShape() {
        return false;
    }

    @Override
    public Object[] getRecipeItems() {
        return new Object[]{
                new ItemStack(ModItems.MOONSTONE.get(), 1),
                new ItemStack(ModItems.AIR_ESSENCE.get(), 1),
                //String.format("E:%d|%d|%d", EssenceTypes.LIGHT.ID(), EssenceTypes.NEUTRAL.ID(), EssenceTypes.DARK.ID()), 1000,
                Blocks.TNT
        };
    }
}
