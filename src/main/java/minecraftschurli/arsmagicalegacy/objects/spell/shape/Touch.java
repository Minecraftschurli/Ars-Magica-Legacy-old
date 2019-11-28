package minecraftschurli.arsmagicalegacy.objects.spell.shape;

import minecraftschurli.arsmagicalegacy.api.spellsystem.*;
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

import java.util.*;

public class Touch extends SpellShape {
    @Override
    public boolean isChanneled() {
        return false;
    }

    @Override
    public float manaCostMultiplier(ItemStack spellStack) {
        return 0;
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
        if (target != null) {
            Entity e = target;
            if (e instanceof EnderDragonPartEntity && ((EnderDragonPartEntity)e).dragon instanceof LivingEntity) e = ((EnderDragonPartEntity)e).dragon;
            return SpellUtils.applyStageToEntity(stack, caster, world, e, giveXP);
        }
        boolean targetWater = SpellUtils.modifierIsPresent(SpellModifiers.TARGET_NONSOLID_BLOCKS, stack);
        RayTraceResult mop = null;//item.getMovingObjectPosition(caster, world, 2.5f, true, targetWater);
        if (mop == null) return SpellCastResult.EFFECT_FAILED;
        else {
            if (mop.getType() == RayTraceResult.Type.ENTITY){
                Entity e = ((EntityRayTraceResult)mop).getEntity();
                if (e instanceof EnderDragonPartEntity && ((EnderDragonPartEntity)e).dragon != null) e = ((EnderDragonPartEntity)e).dragon;
                SpellCastResult result = SpellUtils.applyStageToEntity(stack, caster, world, (target == null) ? e : target, giveXP);
                if (result != SpellCastResult.SUCCESS) return result;
                return SpellUtils.applyStackStage(stack, caster, target, mop.getHitVec().getX(), mop.getHitVec().getY(), mop.getHitVec().getZ(), null, world, true, giveXP, 0);
            } else {
                SpellCastResult result = SpellUtils.applyStageToGround(stack, caster, world, ((BlockRayTraceResult)mop).getPos(), ((BlockRayTraceResult)mop).getFace(), mop.getHitVec().getX(), mop.getHitVec().getY(), mop.getHitVec().getZ(), giveXP);
                if (result != SpellCastResult.SUCCESS) return result;
                return SpellUtils.applyStackStage(stack, caster, target, ((BlockRayTraceResult)mop).getPos().getX(), ((BlockRayTraceResult)mop).getPos().getY(), ((BlockRayTraceResult)mop).getPos().getZ(), ((BlockRayTraceResult)mop).getFace(), world, true, giveXP, 0);
            }
        }
    }

    @Override
    public ISpellIngredient[] getRecipe() {
        return new ISpellIngredient[]{
                new ItemStackSpellIngredient(new ItemStack(ModItems.VINTEUM.get())),
                new ItemStackSpellIngredient(new ItemStack(Items.CLAY_BALL)),
                new ItemStackSpellIngredient(new ItemStack(Items.COD)),
                new ItemStackSpellIngredient(new ItemStack(Items.FEATHER))
        };
    }

    @Override
    public void encodeBasicData(CompoundNBT tag, ISpellIngredient[] recipe) {

    }

    @Override
    public EnumSet<SpellModifiers> getModifiers() {
        return EnumSet.of(SpellModifiers.TARGET_NONSOLID_BLOCKS);
    }
}
