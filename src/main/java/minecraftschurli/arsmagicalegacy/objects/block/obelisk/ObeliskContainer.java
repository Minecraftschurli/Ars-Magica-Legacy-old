package minecraftschurli.arsmagicalegacy.objects.block.obelisk;

import minecraftschurli.arsmagicalegacy.init.ModContainers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.network.PacketBuffer;

/**
 * @author Minecraftschurli
 * @version 2020-04-21
 */
public class ObeliskContainer extends Container {
    private final PlayerInventory inventory;
    private final ObeliskTileEntity tile;

    public ObeliskContainer(int id, PlayerInventory inventory, ObeliskTileEntity tile) {
        super(ModContainers.OBELISK.get(), id);
        this.tile = tile;
        this.inventory = inventory;
        addSlot(new ObeliskSlot(tile, 78, 46));
        //display player inventory
        for (int i = 0; i < 3; i++) {
            for (int k = 0; k < 9; k++) {
                addSlot(new Slot(inventory, k + i * 9 + 9, 30 + k * 18, 170 + i * 18));
            }
        }
        //display player action bar
        for (int j1 = 0; j1 < 9; j1++) {
            addSlot(new Slot(inventory, j1, 30 + j1 * 18, 228));
        }
    }

    public ObeliskContainer(int id, PlayerInventory inventory, PacketBuffer additionalData) {
        this(id, inventory, (ObeliskTileEntity) inventory.player.world.getTileEntity(additionalData.readBlockPos()));
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return true;
    }

    public int getCookProgressScaled(int i) {
        return tile.getCookProgressScaled(i);
    }
}
