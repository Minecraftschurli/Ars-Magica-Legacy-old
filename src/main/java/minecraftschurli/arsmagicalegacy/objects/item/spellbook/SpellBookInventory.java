package minecraftschurli.arsmagicalegacy.objects.item.spellbook;

import minecraftschurli.arsmagicalegacy.objects.item.SpellItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.NonNullList;

import javax.annotation.Nonnull;

/**
 * @author Minecraftschurli
 * @version 2019-11-09
 */
public class SpellBookInventory implements IInventory {
    public static int inventorySize = 40;
    private NonNullList<ItemStack> contents;

    public SpellBookInventory(){
        contents = NonNullList.withSize(inventorySize, ItemStack.EMPTY);
    }

    public void readNBT(CompoundNBT compound) {
        final NonNullList<ItemStack> list = NonNullList.withSize(getSizeInventory(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(compound, list);
        for (int index = 0; index < list.size(); index++) {
            setInventorySlotContents(index, list.get(index));
        }
    }

    public void writeNBT(CompoundNBT compound) {
        final NonNullList<ItemStack> list = NonNullList.withSize(getSizeInventory(), ItemStack.EMPTY);
        for (int index = 0; index < list.size(); index++) {
            list.set(index, getStackInSlot(index));
        }
        ItemStackHelper.saveAllItems(compound, list, false);
    }

    @Override
    public int getInventoryStackLimit() {
        return 1;
    }

    /**
     * Returns the number of slots in the inventory.
     */
    @Override
    public int getSizeInventory() {
        return inventorySize;
    }

    @Override
    public boolean isEmpty() {
        return this.contents.stream().noneMatch(ItemStack::isEmpty);
    }

    /**
     * Returns the stack in the given slot.
     *
     * @param index
     */
    @Nonnull
    @Override
    public ItemStack getStackInSlot(int index) {
        return this.contents.get(index);
    }

    /**
     * Removes up to a specified number of items from an inventory slot and returns them in a new stack.
     *
     * @param index
     * @param count
     */
    @Nonnull
    @Override
    public ItemStack decrStackSize(int index, int count) {
        return this.contents.get(index).split(count);
    }

    /**
     * Removes a stack from the given slot and returns it.
     *
     * @param index
     */
    @Nonnull
    @Override
    public ItemStack removeStackFromSlot(int index) {
        final ItemStack stack = this.contents.get(index).copy();
        this.contents.set(index, ItemStack.EMPTY);
        return stack;
    }

    /**
     * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
     *
     * @param index
     * @param stack
     */
    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        this.contents.set(index, stack);
    }

    /**
     * For tile entities, ensures the chunk containing the tile entity is saved to disk later - the game won't think it
     * hasn't changed and skip it.
     */
    @Override
    public void markDirty() {

    }

    /**
     * Don't rename this method to canInteractWith due to conflicts with Container
     *
     * @param player
     */
    @Override
    public boolean isUsableByPlayer(PlayerEntity player) {
        return true;
    }

    @Override
    public void clear() {
        this.contents = NonNullList.withSize(inventorySize, ItemStack.EMPTY);
    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        return stack.getItem() instanceof SpellItem;
    }
}
