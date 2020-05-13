package minecraftschurli.arsmagicalegacy.objects.item;

import javax.annotation.Nonnull;
import minecraftschurli.arsmagicalegacy.ArsMagicaLegacy;
import minecraftschurli.arsmagicalegacy.init.ModBlocks;
import minecraftschurli.arsmagicalegacy.objects.block.WizardChalk;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;

public class WizardChalkItem extends Item {
    public WizardChalkItem() {
        super(new Item.Properties().group(ArsMagicaLegacy.ITEM_GROUP).maxStackSize(1).maxDamage(50));
    }

    @Nonnull
    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        if (context.getFace() != Direction.UP || !context.getWorld().getBlockState(context.getPos().up()).isAir(context.getWorld(), context.getPos().up())) return ActionResultType.FAIL;
        if (!context.getWorld().isRemote){
            context.getWorld().setBlockState(context.getPos().up(), ModBlocks.WIZARD_CHALK.get().getDefaultState().with(WizardChalk.TYPE, context.getWorld().rand.nextInt(15)).with(WizardChalk.FACING, context.getPlacementHorizontalFacing()));
            context.getItem().damageItem(1, context.getPlayer(), (p_220039_0_) -> {
                p_220039_0_.sendBreakAnimation(EquipmentSlotType.MAINHAND);
            });
            if(getDamage(context.getItem()) < 0) context.getPlayer().setHeldItem(Hand.MAIN_HAND, ItemStack.EMPTY);
        }
        return ActionResultType.PASS;
    }
}
