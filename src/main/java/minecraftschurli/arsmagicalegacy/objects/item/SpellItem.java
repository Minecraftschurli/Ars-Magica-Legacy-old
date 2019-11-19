package minecraftschurli.arsmagicalegacy.objects.item;

import minecraftschurli.arsmagicalegacy.capabilities.spell.ISpell;
import minecraftschurli.arsmagicalegacy.capabilities.spell.TestSpell;
import minecraftschurli.arsmagicalegacy.util.SpellUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

/**
 * @author Minecraftschurli
 * @version 2019-11-07
 */
public class SpellItem extends Item {
    private ISpell spell;

    public SpellItem() {
        super(new Properties().maxStackSize(1));
        this.spell = new TestSpell();
    }

    @Override
    @Nonnull
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, @Nonnull Hand handIn) {
        if (worldIn.isRemote)
            return new ActionResult<>(ActionResultType.FAIL, playerIn.getHeldItem(handIn));
        return performSpell(worldIn, playerIn.getHeldItem(handIn), playerIn) ?
                new ActionResult<>(ActionResultType.SUCCESS, playerIn.getHeldItem(handIn)) :
                new ActionResult<>(ActionResultType.FAIL, playerIn.getHeldItem(handIn));
    }

    @Override
    @Nonnull
    public ActionResultType onItemUse(ItemUseContext context) {
        if (context.getWorld().isRemote)
            return ActionResultType.FAIL;
        return performSpell(context.getWorld(), context.getItem(), context.getPlayer()) ? ActionResultType.SUCCESS : ActionResultType.FAIL;
    }

    protected boolean performSpell(World world, ItemStack stack, PlayerEntity player) {
        player.getCooldownTracker().setCooldown(stack.getItem(), this.getCooldown(stack));
        return SpellUtils.getSpell(stack).map(iSpell -> iSpell.execute(world, stack, player)).orElse(false);
    }

    protected int getCooldown(ItemStack stack) {
        return SpellUtils.getSpell(stack).map(ISpell::getCooldown).orElse(0);
    }
}
