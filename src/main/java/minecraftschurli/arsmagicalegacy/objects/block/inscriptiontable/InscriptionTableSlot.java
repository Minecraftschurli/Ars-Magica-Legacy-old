package minecraftschurli.arsmagicalegacy.objects.block.inscriptiontable;

import minecraftschurli.arsmagicalegacy.ArsMagicaLegacy;
import minecraftschurli.arsmagicalegacy.init.ModItems;
import minecraftschurli.arsmagicalegacy.objects.item.SpellItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nonnull;

/**
 * @author Minecraftschurli
 * @version 2019-12-10
 */
public class InscriptionTableSlot extends Slot {
    public InscriptionTableSlot(IInventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
    }

    @Override
    public boolean isItemValid(ItemStack par1ItemStack) {
        if (par1ItemStack == null) {
            return false;
        } else {
            par1ItemStack.getItem();
        }
        if (par1ItemStack.getItem() == Items.WRITTEN_BOOK && (par1ItemStack.getTag() == null || !par1ItemStack.getTag().getBoolean("spellFinalized")))
            return true;
        else if (par1ItemStack.getItem() == Items.WRITABLE_BOOK)
            return true;
        else return par1ItemStack.getItem() == ModItems.SPELL.get();
    }

    @Nonnull
    @Override
    public ItemStack onTake(PlayerEntity thePlayer, ItemStack stack) {
        ArsMagicaLegacy.LOGGER.debug(thePlayer.world.isRemote);
        if (stack.getItem() == Items.WRITTEN_BOOK && !thePlayer.world.isRemote)
            stack = ((InscriptionTableTileEntity) this.inventory).writeRecipeAndDataToBook(stack, thePlayer, "Spell Recipe");
        else
            ((InscriptionTableTileEntity) this.inventory).clearCurrentRecipe();
        return super.onTake(thePlayer, stack);
    }

    @Override
    public void onSlotChanged() {
        if (!this.getStack().isEmpty()) {
            if (this.getStack().getItem() instanceof SpellItem) {
                ((InscriptionTableTileEntity) this.inventory).reverseEngineerSpell(this.getStack());
            }
        }
        super.onSlotChanged();
    }

    @Override
    public void putStack(ItemStack stack) {
        if (!stack.isEmpty() && stack.getItem() == Items.WRITABLE_BOOK) {
            stack = new ItemStack(Items.WRITTEN_BOOK, stack.getCount(), stack.getTag());
            stack.setDisplayName(new TranslationTextComponent("am2.tooltip.unfinishedSpellRecipe"));
        }
        super.putStack(stack);
    }
}
