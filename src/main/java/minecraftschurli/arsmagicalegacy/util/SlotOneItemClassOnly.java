package minecraftschurli.arsmagicalegacy.util;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * @author Minecraftschurli
 * @version 2019-11-11
 */
public class SlotOneItemClassOnly<T extends Item> extends Slot {
    private final Class<T> itemClass;
    private final int maxStackSize;

    public SlotOneItemClassOnly(IInventory inventoryIn, int index, int xPosition, int yPosition, Class<T> itemClass) {
        super(inventoryIn, index, xPosition, yPosition);
        this.itemClass = itemClass;
        this.maxStackSize = 64;
    }

    public SlotOneItemClassOnly(IInventory inventoryIn, int index, int xPosition, int yPosition, Class<T> itemClass, int maxStackSize) {
        super(inventoryIn, index, xPosition, yPosition);
        this.itemClass = itemClass;
        this.maxStackSize = maxStackSize;
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return itemClass.isAssignableFrom(stack.getItem().getClass());
    }

    public int getMaxStackSize() {
        return maxStackSize;
    }
}
