package minecraftschurli.arsmagicalegacy.objects.item;

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
}
