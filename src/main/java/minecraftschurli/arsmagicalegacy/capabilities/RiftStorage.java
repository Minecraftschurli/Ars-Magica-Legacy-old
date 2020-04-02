package minecraftschurli.arsmagicalegacy.capabilities;

import minecraftschurli.arsmagicalegacy.api.capability.IRiftStorage;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.NonNullList;

import javax.annotation.Nonnull;

/**
 * @author Minecraftschurli
 * @version 2020-01-20
 */
public class RiftStorage implements IRiftStorage {
    private final NonNullList<ItemStack> storage = NonNullList.withSize(54, ItemStack.EMPTY);
    private int accessLevel;

    @Override
    public int getAccessLevel() {
        return accessLevel;
    }

    @Override
    public void setAccessLevel(int accessLevel) {
        this.accessLevel = accessLevel;
    }

    @Override
    public void load(CompoundNBT nbt) {
        ItemStackHelper.loadAllItems(nbt, storage);
        setAccessLevel(nbt.getInt("access_level"));
    }

    @Override
    public void save(CompoundNBT nbt) {
        ItemStackHelper.saveAllItems(nbt, storage);
        nbt.putInt("access_level", getAccessLevel());
    }

    @Override
    public void setFrom(IRiftStorage riftStorage) {
        storage.clear();
        for (int i = 0; i < riftStorage.getSizeInventory(); i++) {
            this.setInventorySlotContents(i, riftStorage.getStackInSlot(i));
        }
        setAccessLevel(riftStorage.getAccessLevel());
    }

    /**
     * Returns the number of slots in the inventory.
     */
    @Override
    public int getSizeInventory() {
        return storage.size();
    }

    @Override
    public boolean isEmpty() {
        return storage.stream().allMatch(ItemStack::isEmpty);
    }

    /**
     * Returns the stack in the given slot.
     *
     * @param index
     */
    @Nonnull
    @Override
    public ItemStack getStackInSlot(int index) {
        return storage.get(index);
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
        return storage.get(index).split(count);
    }

    /**
     * Removes a stack from the given slot and returns it.
     *
     * @param index
     */
    @Nonnull
    @Override
    public ItemStack removeStackFromSlot(int index) {
        return storage.remove(index);
    }

    /**
     * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
     *
     * @param index
     * @param stack
     */
    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        storage.set(index, stack);
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
        storage.clear();
    }
}
