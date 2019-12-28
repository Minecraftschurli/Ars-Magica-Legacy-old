package minecraftschurli.arsmagicalegacy.objects.item;

import minecraftschurli.arsmagicalegacy.ArsMagicaLegacy;
import minecraftschurli.arsmagicalegacy.api.spell.*;
import minecraftschurli.arsmagicalegacy.util.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.world.*;

import javax.annotation.*;

/**
 * @author Minecraftschurli
 * @version 2019-11-07
 */
public class SpellItem extends Item {
    public SpellItem() {
        super(new Properties().maxStackSize(1));
    }

    @Override
    @Nonnull
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, @Nonnull Hand handIn) {
        if (worldIn.isRemote)
            return new ActionResult<>(ActionResultType.PASS, playerIn.getHeldItem(handIn));
        return new ActionResult<>(ActionResultType.SUCCESS, playerIn.getHeldItem(handIn));
    }

    @Override
    @Nonnull
    public ActionResultType onItemUse(ItemUseContext context) {
        if (context.getWorld().isRemote)
            return ActionResultType.PASS;
        return ActionResultType.SUCCESS;
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft) {
        ArsMagicaLegacy.LOGGER.debug(stack);
        SpellShape shape = SpellUtils.getShapeForStage(stack, 0);
        if (!stack.hasTag()) return;
        if (shape != null) {
            if (!shape.isChanneled())
                SpellUtils.applyStackStage(stack, entityLiving, null, entityLiving.posX, entityLiving.posY, entityLiving.posZ, Direction.UP, worldIn, true, true, 0);
            /*if (worldIn.isRemote && shape.isChanneled()){
                //SoundHelper.instance.stopSound(shape.getSoundForAffinity(SpellUtils.instance.mainAffinityFor(stack), stack, null));
            }*/
        }
    }

    @Override
    public void onUsingTick(ItemStack stack, LivingEntity player, int count) {
        SpellShape shape = SpellUtils.getShapeForStage(stack, 0);
        if (shape.isChanneled())
            SpellUtils.applyStackStage(stack, player, null, player.posX, player.posY, player.posZ, Direction.UP, player.world, true, true, count - 1);
        super.onUsingTick(stack, player, count);
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 72000;
    }
}
