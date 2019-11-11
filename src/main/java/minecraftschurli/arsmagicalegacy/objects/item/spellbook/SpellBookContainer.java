package minecraftschurli.arsmagicalegacy.objects.item.spellbook;

import minecraftschurli.arsmagicalegacy.init.Containers;
import minecraftschurli.arsmagicalegacy.init.Items;
import minecraftschurli.arsmagicalegacy.objects.item.SpellItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

/**
 * @author Minecraftschurli
 * @version 2019-11-07
 */
public class SpellBookContainer extends Container {
    private ItemStack bookStack;
    private SpellBookInventory spellBookStack;
    private int bookSlot;
    public int specialSlotIndex;

    public SpellBookContainer(int id, PlayerInventory playerInventory) {
        this(id, playerInventory, new SpellBookInventory());
    }

    public SpellBookContainer(int id, PlayerInventory inventoryplayer, SpellBookInventory inventoryspellbook){
        super(Containers.SPELLBOOK.get(), id);
        //addSlot(new Slot(spellBook,0, 21, 36)); //inventory, index, x, y
        bookStack = inventoryplayer.getCurrentItem();
        if (bookStack.getTag() != null) {
            inventoryspellbook.readNBT(bookStack.getTag());
        }
        this.spellBookStack = inventoryspellbook;
        this.bookStack = bookStack;
        this.bookSlot = inventoryplayer.currentItem;

        int slotIndex = 0;
        //Spell Book Pages - active spells
        for (int i = 0; i < 8; ++i){
            addSlot(new SpellBookSlot(spellBookStack, slotIndex++, 18, 5 + (i * 18)));
        }

        //Spell Book Pages - reserve spells
        for (int i = 0; i < 4; ++i){
            for (int k = 0; k < 8; k++){
                addSlot(new SpellBookSlot(spellBookStack, slotIndex++, 138 + (i * 26), 5 + (k * 18)));
            }
        }

        //display player inventory
        for (int i = 0; i < 3; i++){
            for (int k = 0; k < 9; k++){
                addSlot(new Slot(inventoryplayer, k + i * 9 + 9, 48 + k * 18, 171 + i * 18));
            }
        }

        //display player action bar
        for (int j1 = 0; j1 < 9; j1++){
            addSlot(new Slot(inventoryplayer, j1, 48 + j1 * 18, 229));
            if (inventoryplayer.getStackInSlot(j1) == bookStack){
                specialSlotIndex = j1 + (8 + 4 * 8 + 3 * 9);
            }
        }

    }

    public ItemStack[] getActiveSpells(){
        ItemStack[] itemStack = new ItemStack[7];
        for (int i = 0; i < 7; ++i){
            itemStack[i] = spellBookStack.getStackInSlot(i);
        }
        return itemStack;
    }

    public ItemStack[] getFullInventory(){
        ItemStack[] stack = new ItemStack[40];
        for (int i = 0; i < 40; ++i){
            stack[i] = ((Slot)inventorySlots.get(i)).getStack();
        }
        return stack;
    }

    @Override
    public void onContainerClosed(PlayerEntity entityplayer){

        ItemStack spellBookItemStack = bookStack;
        spellBookStack.writeNBT(spellBookItemStack.getOrCreateTag());

        super.onContainerClosed(entityplayer);
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
            ItemStack stack = player.inventory.getStackInSlot(dragType);
            if (stack == player.inventory.getCurrentItem()) {
                return ItemStack.EMPTY;
            }
        }
        return super.slotClick(slotId, dragType, clickType, player);
    }

    @Override
    public boolean canInteractWith(PlayerEntity entityplayer){
        return spellBookStack.isUseableByPlayer(entityplayer);
    }
}
