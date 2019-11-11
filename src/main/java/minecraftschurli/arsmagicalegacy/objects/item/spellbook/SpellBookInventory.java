package minecraftschurli.arsmagicalegacy.objects.item.spellbook;

import minecraftschurli.arsmagicalegacy.init.Items;
import minecraftschurli.arsmagicalegacy.objects.item.SpellItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.items.IItemHandler;

import java.util.Arrays;

/**
 * @author Minecraftschurli
 * @version 2019-11-09
 */
public class SpellBookInventory extends Inventory {
    public static int inventorySize = 40;
    public static int activeInventorySize = 8;
    private ItemStack[] inventoryItems;

    public SpellBookInventory(){
        inventoryItems = new ItemStack[inventorySize];
        Arrays.fill(inventoryItems, ItemStack.EMPTY);
    }

    public void setInventoryContents(ItemStack[] inventoryContents){
        int loops = (int)Math.min(inventorySize, inventoryContents.length);
        for (int i = 0; i < loops; ++i){
            inventoryItems[i] = inventoryContents[i];
        }
    }

    @Override
    public int getSizeInventory(){
        return inventorySize;
    }

    @Override
    public ItemStack getStackInSlot(int i){
        if (i < 0 || i > inventoryItems.length - 1){
            return ItemStack.EMPTY;
        }
        return inventoryItems[i];
    }

    @Override
    public void setInventorySlotContents(int i, ItemStack itemstack){
        inventoryItems[i] = itemstack;
    }

    @Override
    public int getInventoryStackLimit(){
        return 1;
    }

    public boolean isUseableByPlayer(PlayerEntity entityplayer){
        return true;
    }

    public ItemStack[] getInventoryContents(){
        return inventoryItems;
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack itemstack){
        return itemstack.getItem() instanceof SpellItem;
    }

    @Override
    public void markDirty(){
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
}
