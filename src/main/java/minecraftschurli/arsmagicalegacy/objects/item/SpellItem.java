package minecraftschurli.arsmagicalegacy.objects.item;

import minecraftschurli.arsmagicalegacy.*;
import minecraftschurli.arsmagicalegacy.api.*;
import minecraftschurli.arsmagicalegacy.api.spell.*;
import minecraftschurli.arsmagicalegacy.util.*;
import net.minecraft.client.util.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.util.text.*;
import net.minecraft.world.*;
import net.minecraftforge.api.distmarker.*;

import javax.annotation.*;
import java.util.*;

/**
 * @author Minecraftschurli
 * @version 2019-11-07
 */
public class SpellItem extends Item implements ISpellItem {
    public SpellItem() {
        super(new Properties().maxStackSize(1));
    }

    @Override
    @Nonnull
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, @Nonnull Hand handIn) {
        playerIn.setActiveHand(handIn);
        if (worldIn.isRemote)
            return new ActionResult<>(ActionResultType.PASS, playerIn.getHeldItem(handIn));
        return new ActionResult<>(ActionResultType.SUCCESS, playerIn.getHeldItem(handIn));
    }

    @Override
    @Nonnull
    public ActionResultType onItemUse(ItemUseContext context) {
        context.getPlayer().setActiveHand(context.getHand());
        if (context.getWorld().isRemote)
            return ActionResultType.PASS;
        return ActionResultType.SUCCESS;
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft) {
        if (worldIn.isRemote)
            return;
        SpellShape shape = SpellUtils.getShapeForStage(stack, 0);
        if (!stack.hasTag()) return;
        if (shape != null) {
            if (!shape.isChanneled()) {
                SpellCastResult result = SpellUtils.applyStackStage(stack, entityLiving, null, entityLiving.getPosX(), entityLiving.getPosY(), entityLiving.getPosZ(), Direction.UP, worldIn, true, true, 0);
                ArsMagicaLegacy.LOGGER.debug(result);
            }
            /*if (worldIn.isRemote && shape.isChanneled()){
                //SoundHelper.instance.stopSound(shape.getSoundForAffinity(SpellUtils.instance.mainAffinityFor(stack), stack, null));
            }*/
        }
    }

    @Override
    public void onUsingTick(ItemStack stack, LivingEntity player, int count) {
        SpellShape shape = SpellUtils.getShapeForStage(stack, 0);
        if (shape.isChanneled())
            SpellUtils.applyStackStage(stack, player, null, player.getPosX(), player.getPosY(), player.getPosZ(), Direction.UP, player.world, true, true, count - 1);
        super.onUsingTick(stack, player, count);
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 72000;
    }

    @Override
    public RayTraceResult getMovingObjectPosition(LivingEntity caster, World world, double range, boolean includeEntities, boolean targetWater) {
        RayTraceResult entityPos = null;
        if (includeEntities) {
            Entity pointedEntity = EntityUtils.getPointedEntity(world, caster, range, 1.0f, false, targetWater);
            if (pointedEntity != null) {
                entityPos = new EntityRayTraceResult(pointedEntity);
            }
        }

        float factor = 1;
        float interpPitch = caster.prevRotationPitch + (caster.rotationPitch - caster.prevRotationPitch) * factor;
        float interpYaw = caster.prevRotationYaw + (caster.rotationYaw - caster.prevRotationYaw) * factor;
        double interpPosX = caster.prevPosX + (caster.getPosX() - caster.prevPosX) * factor;
        double interpPosY = caster.prevPosY + (caster.getPosY() - caster.prevPosY) * factor + caster.getEyeHeight();
        double interpPosZ = caster.prevPosZ + (caster.getPosZ() - caster.prevPosZ) * factor;
        Vec3d vec3 = new Vec3d(interpPosX, interpPosY, interpPosZ);
        float offsetYawCos = MathHelper.cos(-interpYaw * 0.017453292F - (float) Math.PI);
        float offsetYawSin = MathHelper.sin(-interpYaw * 0.017453292F - (float) Math.PI);
        float offsetPitchCos = -MathHelper.cos(-interpPitch * 0.017453292F);
        float offsetPitchSin = MathHelper.sin(-interpPitch * 0.017453292F);
        float finalXOffset = offsetYawSin * offsetPitchCos;
        float finalZOffset = offsetYawCos * offsetPitchCos;
        Vec3d targetVector = vec3.add(finalXOffset * range, offsetPitchSin * range, finalZOffset * range);
        RayTraceResult mop = world.rayTraceBlocks(new RayTraceContext(vec3, targetVector, RayTraceContext.BlockMode.OUTLINE, targetWater ? RayTraceContext.FluidMode.SOURCE_ONLY : RayTraceContext.FluidMode.NONE, caster));

        if (entityPos != null) {
            if (mop.getHitVec().distanceTo(new EntityRayTraceResult(caster).getHitVec()) < entityPos.getHitVec().distanceTo(new EntityRayTraceResult(caster).getHitVec())) {
                return mop;
            } else {
                return entityPos;
            }
        }

        return mop;

    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        if (!stack.hasTag()) return;

        float manaCost = SpellUtils.getManaCost(stack, ArsMagicaLegacy.proxy.getLocalPlayer());
        tooltip.add(new TranslationTextComponent(ArsMagicaLegacy.MODID+".spell.manacost", manaCost));
    }
}
