package minecraftschurli.arsmagicalegacy.objects.spell.shape;

import minecraftschurli.arsmagicalegacy.api.spell.*;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.*;
import minecraftschurli.arsmagicalegacy.init.*;
import minecraftschurli.arsmagicalegacy.objects.item.*;
import minecraftschurli.arsmagicalegacy.util.*;
import net.minecraft.entity.*;
import net.minecraft.entity.boss.dragon.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import net.minecraftforge.common.*;

import java.util.*;

public class Chain extends SpellShape {
    @Override
    public boolean isChanneled() {
        return false;
    }

    @Override
    public float manaCostMultiplier(ItemStack spellStack) {
        return 1.5f;
    }

    @Override
    public boolean isTerminusShape() {
        return false;
    }

    @Override
    public boolean isPrincipumShape() {
        return false;
    }

    @Override
    public SpellCastResult beginStackStage(SpellItem item, ItemStack stack, LivingEntity caster, LivingEntity target, World world, double x, double y, double z, Direction side, boolean giveXP, int useCount) {
        RayTraceResult mop = null;//item.getMovingObjectPosition(caster, world, 8.0f, true, false);
        double range = SpellUtils.getModifiedDoubleMul(8, stack, caster, target, world, SpellModifiers.RANGE);
        int num_targets = SpellUtils.getModifiedIntAdd(3, stack, caster, target, world, SpellModifiers.PROCS);
        ArrayList<LivingEntity> targets = new ArrayList<>();
        if (target != null) mop = new EntityRayTraceResult(target);
        if (mop != null && mop.getType() == RayTraceResult.Type.ENTITY && ((EntityRayTraceResult) mop).getEntity() != null) {
            Entity e = ((EntityRayTraceResult) mop).getEntity();
            if (e instanceof EnderDragonPartEntity && ((EnderDragonPartEntity) e).dragon != null)
                e = ((EnderDragonPartEntity) e).dragon;
            if (e instanceof LivingEntity) {
                do {
                    targets.add((LivingEntity) e);
                    List<LivingEntity> nearby = world.getEntitiesWithinAABB(LivingEntity.class, e.getBoundingBox().expand(range, range, range));
                    LivingEntity closest = null;
                    for (LivingEntity near : nearby) {
                        if (targets.contains(near) || near == caster) continue;
                        if (closest == null || closest.getDistanceSq(e) > near.getDistanceSq(e)) closest = near;
                    }
                    e = closest;
                } while (e != null && targets.size() < num_targets);
            }
        }
        boolean atLeastOneApplication = false;
        SpellCastResult result = SpellCastResult.SUCCESS;
        LivingEntity prevEntity = null;
        for (LivingEntity e : targets) {
            if (e == caster) continue;
            result = SpellUtils.applyStageToEntity(stack, caster, world, e, giveXP);
            SpellUtils.applyStackStage(stack, caster, e, e.posX, e.posY, e.posZ, null, world, true, giveXP, 0);
            if (world.isRemote) {
//                if (prevEntity == null) spawnChainParticles(world, x, y, z, e.posX, e.posY + e.getEyeHeight(), e.posZ, stack);
//                else spawnChainParticles(world, prevEntity.posX, prevEntity.posY + e.getEyeHeight(), prevEntity.posZ, e.posX, e.posY + e.getEyeHeight(), e.posZ, stack);
            }
            prevEntity = e;
            if (result == SpellCastResult.SUCCESS) atLeastOneApplication = true;
        }
        if (atLeastOneApplication) return SpellCastResult.SUCCESS;
        return result;
    }

    @Override
    public ISpellIngredient[] getRecipe() {
        return new ISpellIngredient[]{
                new ItemTagSpellIngredient(ModTags.Items.GEMS_SUNSTONE),
                new ItemTagSpellIngredient(Tags.Items.INGOTS_IRON),
                new ItemStackSpellIngredient(new ItemStack(Items.LEAD)),
                new ItemStackSpellIngredient(new ItemStack(Items.STRING)),
                new ItemStackSpellIngredient(new ItemStack(Items.TRIPWIRE_HOOK))
        };
    }

    @Override
    public void encodeBasicData(CompoundNBT tag, ISpellIngredient[] recipe) {

    }

    @Override
    public EnumSet<SpellModifiers> getModifiers() {
        return EnumSet.of(SpellModifiers.RANGE, SpellModifiers.PROCS);
    }
}
