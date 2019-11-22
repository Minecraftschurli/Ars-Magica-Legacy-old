package minecraftschurli.arsmagicalegacy.objects.spell.shape;

import minecraftschurli.arsmagicalegacy.api.spellsystem.*;
import minecraftschurli.arsmagicalegacy.init.ModItems;
import minecraftschurli.arsmagicalegacy.objects.item.SpellItem;
import minecraftschurli.arsmagicalegacy.util.SpellHelper;
import minecraftschurli.arsmagicalegacy.util.SpellUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.dragon.EnderDragonPartEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Direction;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class Touch extends SpellShape {
    @Override
    public SpellCastResult beginStackStage(SpellItem item, ItemStack stack, LivingEntity caster, LivingEntity target, World world, Vec3d pos, Direction side, boolean giveXP, int useCount) {
        if (target != null){
            Entity e = target;
            if (e instanceof EnderDragonPartEntity && ((EnderDragonPartEntity)e).dragon instanceof LivingEntity) e = ((EnderDragonPartEntity)e).dragon;
            SpellCastResult result = SpellHelper.applyStageToEntity(stack, caster, world, e, 0, giveXP);
            return result;
        }
        boolean targetWater = SpellUtils.modifierIsPresent(SpellModifier.Type.TARGET_NONSOLID_BLOCKS, stack, 0);
        EntityRayTraceResult mop = null;//item.getMovingObjectPosition(caster, world, 2.5f, true, targetWater);
        if (mop == null) return SpellCastResult.EFFECT_FAILED;
        else {
            if (mop.getType() == EntityRayTraceResult.Type.ENTITY){
                Entity e = mop.getEntity();
                if (e instanceof EnderDragonPartEntity && ((EnderDragonPartEntity)e).dragon instanceof LivingEntity) e = ((EnderDragonPartEntity)e).dragon;
                SpellCastResult result = SpellHelper.applyStageToEntity(stack, caster, world, (target == null) ? e : target, 0, giveXP);
                if (result != SpellCastResult.SUCCESS) return result;
                ItemStack newItemStack = SpellUtils.popStackStage(stack);
                return SpellHelper.applyStackStage(newItemStack, caster, target, mop.getHitVec(), Direction.UP, world, true, giveXP, 0);
            } else {
//                SpellCastResult result = SpellHelper.applyStageToGround(stack, caster, world, mop.blockX, mop.blockY, mop.blockZ, mop.sideHit, mop.getHitVec().getX(), mop.getHitVec().getY(), mop.getHitVec().getZ(), 0, giveXP);
//                if (result != SpellCastResult.SUCCESS) return result;
//                ItemStack newItemStack = SpellUtils.popStackStage(stack);
                return null;//SpellHelper.applyStackStage(newItemStack, caster, target, mop.blockX, mop.blockY, mop.blockZ, mop.sideHit, world, true, giveXP, 0);
            }
        }
    }

    @Override
    public boolean isChanneled() {
        return false;
    }

    @Override
    public float getManaCostMultiplier(ItemStack spellStack) {
        return 1;
    }

    @Override
    public ISpellIngredient[] getRecipeItems(){
        return new ISpellIngredient[]{
                new ItemStackSpellIngredient(new ItemStack(ModItems.VINTEUM.get())),
                new ItemStackSpellIngredient(new ItemStack(Items.CLAY_BALL)),
                new ItemStackSpellIngredient(new ItemStack(Items.COD)),
                new ItemStackSpellIngredient(new ItemStack(Items.FEATHER))
        };
    }

    @Override
    public boolean isTerminusShape() {
        return false;
    }

    @Override
    public boolean isPrincipumShape() {
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
