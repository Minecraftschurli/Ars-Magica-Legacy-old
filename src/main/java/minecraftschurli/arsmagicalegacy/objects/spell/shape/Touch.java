package minecraftschurli.arsmagicalegacy.objects.spell.shape;

import minecraftschurli.arsmagicalegacy.api.util.EntityUtils;
import minecraftschurli.arsmagicalegacy.api.spell.SpellCastResult;
import minecraftschurli.arsmagicalegacy.api.spell.SpellModifiers;
import minecraftschurli.arsmagicalegacy.api.spell.SpellShape;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ISpellIngredient;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ItemStackSpellIngredient;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ItemTagSpellIngredient;
import minecraftschurli.arsmagicalegacy.init.ModTags;
import minecraftschurli.arsmagicalegacy.util.SpellUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.dragon.EnderDragonPartEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.common.Tags;

import java.util.EnumSet;

public class Touch extends SpellShape {
    @Override
    public boolean isChanneled() {
        return false;
    }

    @Override
    public float manaCostMultiplier(ItemStack spellStack) {
        return 1;
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
    public SpellCastResult beginStackStage(Item item, ItemStack stack, LivingEntity caster, LivingEntity target, World world, double x, double y, double z, Direction side, boolean giveXP, int useCount) {
        if (target != null) {
            return SpellUtils.applyStageToEntity(stack, caster, world, target, giveXP);
        }
        boolean targetWater = SpellUtils.modifierIsPresent(SpellModifiers.TARGET_NONSOLID_BLOCKS, stack);
        RayTraceResult mop = EntityUtils.getMovingObjectPosition(caster, world, 2.5f, true, targetWater);
        if (mop == null) return SpellCastResult.EFFECT_FAILED;
        else {
            if (mop.getType() == RayTraceResult.Type.ENTITY) {
                Entity e = ((EntityRayTraceResult) mop).getEntity();
                if (e instanceof EnderDragonPartEntity && ((EnderDragonPartEntity) e).dragon != null)
                    e = ((EnderDragonPartEntity) e).dragon;
                SpellCastResult result = SpellUtils.applyStageToEntity(stack, caster, world, e, giveXP);
                if (result != SpellCastResult.SUCCESS) return result;
                return SpellUtils.applyStackStage(stack, caster, target, mop.getHitVec().getX(), mop.getHitVec().getY(), mop.getHitVec().getZ(), null, world, true, giveXP, 0);
            } else if (mop.getType() == RayTraceResult.Type.BLOCK) {
                SpellCastResult result = SpellUtils.applyStageToGround(stack, caster, world, ((BlockRayTraceResult) mop).getPos(), ((BlockRayTraceResult) mop).getFace(), mop.getHitVec().getX(), mop.getHitVec().getY(), mop.getHitVec().getZ(), giveXP);
                if (result != SpellCastResult.SUCCESS) return result;
                return SpellUtils.applyStackStage(stack, caster, target, ((BlockRayTraceResult) mop).getPos().getX(), ((BlockRayTraceResult) mop).getPos().getY(), ((BlockRayTraceResult) mop).getPos().getZ(), ((BlockRayTraceResult) mop).getFace(), world, true, giveXP, 0);
            } else {
                return SpellCastResult.EFFECT_FAILED;
            }
        }
    }

    @Override
    public ISpellIngredient[] getRecipe() {
        return new ISpellIngredient[]{
                new ItemTagSpellIngredient(ModTags.Items.DUSTS_VINTEUM),
                new ItemStackSpellIngredient(new ItemStack(Items.CLAY_BALL)),
                new ItemStackSpellIngredient(new ItemStack(Items.COD)),
                new ItemTagSpellIngredient(Tags.Items.FEATHERS)
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
