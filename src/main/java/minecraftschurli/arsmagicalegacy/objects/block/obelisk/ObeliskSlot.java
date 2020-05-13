package minecraftschurli.arsmagicalegacy.objects.block.obelisk;

import javax.annotation.Nonnull;
import minecraftschurli.arsmagicalegacy.api.EtheriumGeneratorManager;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

/**
 * @author Minecraftschurli
 * @version 2020-04-21
 */
class ObeliskSlot extends Slot {
    public ObeliskSlot(ObeliskTileEntity tile, int x, int y) {
        super(tile.getInv(), 0, x, y);
    }

    @Override
    public boolean isItemValid(@Nonnull ItemStack stack) {
        return EtheriumGeneratorManager.isEtheriumGeneratorFuel(stack);
    }
}
