package minecraftschurli.arsmagicalegacy.objects.spell.shape;

import minecraftschurli.arsmagicalegacy.api.spellsystem.*;
import minecraftschurli.arsmagicalegacy.init.ModBlocks;
import minecraftschurli.arsmagicalegacy.objects.item.SpellItem;
import minecraftschurli.arsmagicalegacy.objects.spell.modifier.Color;
import minecraftschurli.arsmagicalegacy.util.SpellHelper;
import minecraftschurli.arsmagicalegacy.util.SpellUtils;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.dragon.EnderDragonPartEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Direction;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Minecraftschurli
 * @version 2019-11-19
 */
public class Chain extends SpellShape {
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
        EntityRayTraceResult mop = null;//item.getRayTraced(caster, world, 8.0f, true, false);
        double range = SpellUtils.getModifiedDoubleMul(4, stack, caster, target, world, 0, SpellModifier.Type.RANGE);
        int num_targets = SpellUtils.getModifiedIntAdd(3, stack, caster, target, world, 0, SpellModifier.Type.PROCS);

        ArrayList<LivingEntity> targets = new ArrayList<>();

        if (target != null){
            mop = new EntityRayTraceResult(target);
        }

        if (mop != null && mop.getType() == RayTraceResult.Type.ENTITY){
            Entity e = mop.getEntity();
            if (e instanceof EnderDragonPartEntity && ((EnderDragonPartEntity) e).dragon != null)
                e = ((EnderDragonPartEntity)e).dragon;
            if (e instanceof LivingEntity){
                do{
                    targets.add((LivingEntity)e);

                    List<LivingEntity> nearby = world.getEntitiesWithinAABB(LivingEntity.class, e.getBoundingBox().expand(range, range, range));
                    LivingEntity closest = null;
                    for (LivingEntity near : nearby){
                        if (targets.contains(near) || near == caster) continue;

                        if (closest == null || closest.getDistanceSq(e) > near.getDistanceSq(e)){
                            closest = near;
                        }
                    }

                    e = closest;

                }while (e != null && targets.size() < num_targets);
            }
        }

        ItemStack newItemStack = SpellUtils.popStackStage(stack);
        boolean atLeastOneApplication = false;
        SpellCastResult result = SpellCastResult.SUCCESS;

        LivingEntity prevEntity = null;

        for (LivingEntity e : targets){
            if (e == caster)
                continue;
            result = SpellHelper.applyStageToEntity(stack, caster, world, e, 0, giveXP);
            //Todo SpellHelper.applyStackStage(newItemStack, caster, e, e.posX, e.posY, e.posZ, 0, world, true, giveXP, 0);

            if (world.isRemote){
                if (prevEntity == null)
                    spawnChainParticles(world, pos.x, pos.y, pos.z, e.posX, e.posY + e.getEyeHeight(), e.posZ, stack);
                else
                    spawnChainParticles(world, prevEntity.posX, prevEntity.posY + e.getEyeHeight(), prevEntity.posZ, e.posX, e.posY + e.getEyeHeight(), e.posZ, stack);
            }
            prevEntity = e;

            if (result == SpellCastResult.SUCCESS){
                atLeastOneApplication = true;
            }
        }

        if (atLeastOneApplication){
            return SpellCastResult.SUCCESS;
        }
        return result;
    }

    private void spawnChainParticles(World world, double startX, double startY, double startZ, double endX, double endY, double endZ, ItemStack spellStack){
        int color = getPFXColor(spellStack);

        /* Todo Affinity aff = SpellUtils.instance.mainAffinityFor(spellStack);

        if (aff == Affinity.LIGHTNING){
            AMCore.proxy.particleManager.BoltFromPointToPoint(world, startX, startY, startZ, endX, endY, endZ, 1, color);
        }else{
            if (color == -1)
                color = aff.color;
            AMCore.proxy.particleManager.BeamFromPointToPoint(world, startX, startY, startZ, endX, endY, endZ, color);
        }*/
    }

    private int getPFXColor(ItemStack stack){
        int color = -1;
        if (SpellUtils.modifierIsPresent(SpellModifier.Type.COLOR, stack, 0)){
            SpellModifier[] mods = SpellUtils.getModifiersForStage(stack, 0);
            int ordinalCount = 0;
            for (SpellModifier mod : mods){
                if (mod instanceof Color){
                    byte[] meta = SpellUtils.getModifierMetadataFromStack(stack, mod, 0, ordinalCount++);
                    color = (int)mod.getModifier(SpellModifier.Type.COLOR, null, null, null, meta);
                }
            }
        }
        return color;
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
        return 1.5f;
    }

    /**
     * Is the spell a terminus shape?  Return true if this component does not continue the spell chain when proccing.
     */
    @Override
    public boolean isTerminusShape() {
        return false;
    }

    /**
     * Is the shape a principal shape?  Return true if this spell requires another shape to proc (like runes and zones)
     */
    @Override
    public boolean isPrincipumShape() {
        return false;
    }

    @Override
    public ISpellIngredient[] getRecipeItems() {
        return new ISpellIngredient[] {
                new ItemStackSpellIngredient(new ItemStack(ModBlocks.SUNSTONE_ORE.get(), 1)),
                new ItemStackSpellIngredient(new ItemStack(Items.LEAD, 1)),
                new ItemStackSpellIngredient(new ItemStack(Items.IRON_INGOT, 1)),
                new ItemStackSpellIngredient(new ItemStack(Blocks.TRIPWIRE_HOOK, 1)),
                new ItemStackSpellIngredient(new ItemStack(Items.STRING, 1))
        };
    }
}
