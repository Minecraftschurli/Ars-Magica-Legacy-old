package minecraftschurli.arsmagicalegacy.objects.item;

import minecraftschurli.arsmagicalegacy.api.spell.SpellShape;
import minecraftschurli.arsmagicalegacy.util.SpellUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

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
            return new ActionResult<>(ActionResultType.FAIL, playerIn.getHeldItem(handIn));
        return new ActionResult<>(ActionResultType.SUCCESS, playerIn.getHeldItem(handIn));
    }

    @Override
    @Nonnull
    public ActionResultType onItemUse(ItemUseContext context) {
        if (context.getWorld().isRemote)
            return ActionResultType.FAIL;
        return ActionResultType.SUCCESS;
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft) {
        SpellShape shape = SpellUtils.getShapeForStage(stack, 0);
        if (!stack.hasTag()) return;
        if (shape != null) {
            if (!shape.isChanneled())
                SpellUtils.applyStackStage(stack, entityLiving, null, entityLiving.getPositionVec().x, entityLiving.getPositionVec().y, entityLiving.getPositionVec().z, Direction.UP, worldIn, true, true, 0);
            /*if (worldIn.isRemote && shape.isChanneled()){
                //SoundHelper.instance.stopSound(shape.getSoundForAffinity(SpellUtils.instance.mainAffinityFor(stack), stack, null));
            }*/
        }
    }
}
