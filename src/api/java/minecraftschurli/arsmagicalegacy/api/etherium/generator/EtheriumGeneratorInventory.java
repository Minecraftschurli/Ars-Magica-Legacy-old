package minecraftschurli.arsmagicalegacy.api.etherium.generator;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;

/**
 * @author Minecraftschurli
 * @version 2020-04-23
 */
public class EtheriumGeneratorInventory extends ItemStackHandler implements IInventory {
    public EtheriumGeneratorInventory(){
        super(1);
    }

    @Override
    public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
        return EtheriumGeneratorTileEntity.isObeliskFuel(stack);
    }

    @Override
    public int getSizeInventory() {
        return stacks.size();
    }

    @Override
    public boolean isEmpty() {
        return stacks.isEmpty() || stacks.stream().allMatch(ItemStack::isEmpty);
    }

    @Nonnull
    @Override
    public ItemStack decrStackSize(int index, int count) {
        return extractItem(index, count, false);
    }

    @Nonnull
    @Override
    public ItemStack removeStackFromSlot(int index) {
        ItemStack stack = getStackInSlot(index);
        return stack.split(stack.getCount());
    }

    @Override
    public void setInventorySlotContents(int index, @Nonnull ItemStack stack) {
        setStackInSlot(index, stack);
    }

    @Override
    public void markDirty() {

    }

    @Override
    public boolean isUsableByPlayer(@Nonnull PlayerEntity player) {
        return true;
    }

    @Override
    public void clear() {
        stacks.clear();
    }
}
