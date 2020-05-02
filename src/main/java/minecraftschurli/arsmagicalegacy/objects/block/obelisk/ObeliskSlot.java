package minecraftschurli.arsmagicalegacy.objects.block.obelisk;

import minecraftschurli.arsmagicalegacy.api.EtheriumGeneratorManager;
import minecraftschurli.arsmagicalegacy.api.etherium.generator.EtheriumGeneratorTileEntity;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

/**
 * @author Minecraftschurli
 * @version 2020-04-21
 */
class ObeliskSlot extends Slot {
    public ObeliskSlot(EtheriumGeneratorTileEntity tile, int x, int y) {
        super(tile.getInv(), 0, x, y);
    }

    @Override
    public boolean isItemValid(@Nonnull ItemStack stack) {
        return EtheriumGeneratorManager.isEtheriumGeneratorFuel(stack);
    }
}
