package minecraftschurli.arsmagicalegacy.objects.block.etheriumgenerator;

import minecraftschurli.arsmagicalegacy.init.ModContainers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;

import javax.annotation.Nonnull;

/**
 * @author Minecraftschurli
 * @version 2020-04-21
 */
public class EtheriumGeneratorContainer extends Container {

    private static final int INVENTORY_STORAGE_START = 0;
    private static final int PLAYER_INVENTORY_START = 1;
    private static final int PLAYER_ACTION_BAR_START = 28;
    private static final int PLAYER_ACTION_BAR_END = 37;

    private final EtheriumGeneratorTileEntity tile;

    public EtheriumGeneratorContainer(int id, PlayerInventory inventory, EtheriumGeneratorTileEntity tile) {
        super(ModContainers.ETHERIUM_GENERATOR.get(), id);
        this.tile = tile;
        addSlot(new EtheriumGeneratorSlot(tile, 79, 47));
        //display player inventory
        for (int i = 0; i < 3; i++) {
            for (int k = 0; k < 9; k++) {
                addSlot(new Slot(inventory, k + i * 9 + 9, 8 + k * 18, 84 + i * 18));
            }
        }
        //display player action bar
        for (int j1 = 0; j1 < 9; j1++) {
            addSlot(new Slot(inventory, j1, 8 + j1 * 18, 142));
        }
    }

    public EtheriumGeneratorContainer(int id, PlayerInventory inventory, PacketBuffer additionalData) {
        this(id, inventory, (EtheriumGeneratorTileEntity) inventory.player.world.getTileEntity(additionalData.readBlockPos()));
    }

    @Nonnull
    @Override
    public ItemStack transferStackInSlot(@Nonnull PlayerEntity par1EntityPlayer, int i){
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = inventorySlots.get(i);
        if (slot != null && slot.getHasStack()){
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            if (i < PLAYER_INVENTORY_START) {
                if (EtheriumGeneratorTileEntity.isObeliskFuel(itemstack)){
                    if (!mergeItemStack(itemstack1, PLAYER_INVENTORY_START, PLAYER_ACTION_BAR_END, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            } else if (i < PLAYER_ACTION_BAR_START) {
                if (EtheriumGeneratorTileEntity.isObeliskFuel(itemstack)){
                    if (!mergeItemStack(itemstack1, INVENTORY_STORAGE_START, PLAYER_INVENTORY_START, false)){
                        if (!mergeItemStack(itemstack1, PLAYER_ACTION_BAR_START, PLAYER_ACTION_BAR_END, false)){
                            return ItemStack.EMPTY;
                        }
                    }
                }
            } else if (i < PLAYER_ACTION_BAR_END) {
                if (EtheriumGeneratorTileEntity.isObeliskFuel(itemstack)){
                    if (!mergeItemStack(itemstack1, INVENTORY_STORAGE_START, PLAYER_ACTION_BAR_START - 1, false)){
                        return ItemStack.EMPTY;
                    }
                }
            } else if (!mergeItemStack(itemstack1, PLAYER_INVENTORY_START, PLAYER_ACTION_BAR_END, false)){
                return ItemStack.EMPTY;
            }

            if (itemstack1.getCount() == 0){
                slot.putStack(ItemStack.EMPTY);
            }else{
                slot.onSlotChanged();
            }

            if (itemstack1.getCount() != itemstack.getCount()){
                slot.onSlotChange(itemstack1, itemstack);
            }else{
                return ItemStack.EMPTY;
            }
        }
        return itemstack;
    }

    @Override
    public boolean canInteractWith(@Nonnull PlayerEntity playerIn) {
        return true;
    }

    public int getCookProgressScaled(int i) {
        return tile.getCookProgressScaled(i);
    }
}
