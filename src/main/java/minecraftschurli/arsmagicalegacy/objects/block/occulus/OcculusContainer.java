package minecraftschurli.arsmagicalegacy.objects.block.occulus;

import minecraftschurli.arsmagicalegacy.init.ModContainers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;

/**
 * @author Minecraftschurli
 * @version 2019-12-02
 */
public class OcculusContainer extends Container {

    public OcculusContainer(int id, PlayerInventory playerInventory) {
        super(ModContainers.OCCULUS.get(), id);
    }

    /**
     * Determines whether supplied player can use this container
     *
     * @param playerIn
     */
    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return true;
    }
}
