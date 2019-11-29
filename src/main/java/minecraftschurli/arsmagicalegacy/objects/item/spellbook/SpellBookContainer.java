package minecraftschurli.arsmagicalegacy.objects.item.spellbook;

import minecraftschurli.arsmagicalegacy.init.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.container.*;
import net.minecraft.item.*;

/**
 * @author Minecraftschurli
 * @version 2019-11-07
 */
public class SpellBookContainer extends Container {
    public int specialSlotIndex;
    private ItemStack bookStack;
    private SpellBookInventory spellBookInventory;
    private int bookSlot;

    public SpellBookContainer(int id, PlayerInventory playerInventory) {
        this(id, playerInventory, new SpellBookInventory());
    }

    public SpellBookContainer(int id, PlayerInventory inventoryplayer, SpellBookInventory inventoryspellbook) {
        super(ModContainers.SPELLBOOK.get(), id);
        //addSlot(new Slot(spellBook,0, 21, 36)); //inventory, index, x, y
        this.bookStack = inventoryplayer.getCurrentItem();
        if (bookStack.getTag() != null) {
            inventoryspellbook.readNBT(bookStack.getTag());
        }
        this.spellBookInventory = inventoryspellbook;
        this.bookSlot = inventoryplayer.currentItem;

        int slotIndex = 0;
        //Spell Book Pages - active spells
        for (int i = 0; i < 8; ++i) {
            addSlot(new SpellBookSlot(spellBookInventory, slotIndex++, 18, 5 + (i * 18)));
        }

        //Spell Book Pages - reserve spells
        for (int i = 0; i < 4; ++i) {
            for (int k = 0; k < 8; k++) {
                addSlot(new SpellBookSlot(spellBookInventory, slotIndex++, 138 + (i * 26), 5 + (k * 18)));
            }
        }

        //display player inventory
        for (int i = 0; i < 3; i++) {
            for (int k = 0; k < 9; k++) {
                addSlot(new Slot(inventoryplayer, k + i * 9 + 9, 48 + k * 18, 171 + i * 18));
            }
        }

        //display player action bar
        for (int j1 = 0; j1 < 9; j1++) {
            if (inventoryplayer.getStackInSlot(j1) == bookStack) {
                specialSlotIndex = j1 + (8 + 4 * 8 + 3 * 9);
            } else {
                addSlot(new Slot(inventoryplayer, j1, 48 + j1 * 18, 229));
            }
        }

    }

    @Override
    public void onContainerClosed(PlayerEntity entityplayer) {

        ItemStack spellBookItemStack = bookStack;
        spellBookInventory.writeNBT(spellBookItemStack.getOrCreateTag());

        super.onContainerClosed(entityplayer);
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack slotClick(int slotId, int dragType, ClickType clickType, PlayerEntity player) {
        Slot tmpSlot;
        if (slotId >= 0 && slotId < inventorySlots.size()) {
            tmpSlot = inventorySlots.get(slotId);
        } else {
            tmpSlot = null;
        }
        if (tmpSlot != null) {
            if (tmpSlot.inventory == player.inventory && tmpSlot.getSlotIndex() == player.inventory.currentItem) {
                return tmpSlot.getStack();
            }
        }
        if (clickType == ClickType.SWAP) {
            ItemStack stack = player.inventory.getStackInSlot(slotId);
            if (stack == player.inventory.getCurrentItem()) {
                return ItemStack.EMPTY;
            }
        }
        return super.slotClick(slotId, dragType, clickType, player);
    }

    @Override
    public boolean canInteractWith(PlayerEntity entityplayer) {
        return spellBookInventory.isUsableByPlayer(entityplayer);
    }
}
