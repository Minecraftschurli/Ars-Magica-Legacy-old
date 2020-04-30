package minecraftschurli.arsmagicalegacy.objects.block.etheriumgenerator;

import minecraftschurli.arsmagicalegacy.api.EtheriumGeneratorManager;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

/**
 * @author Minecraftschurli
 * @version 2020-04-21
 */
class EtheriumGeneratorSlot extends Slot {
    public EtheriumGeneratorSlot(EtheriumGeneratorTileEntity tile, int x, int y) {
        super(tile.getInv(), 0, x, y);
    }

    @Override
    public boolean isItemValid(@Nonnull ItemStack stack) {
        return EtheriumGeneratorManager.isEtheriumGeneratorFuel(stack);
    }
}
