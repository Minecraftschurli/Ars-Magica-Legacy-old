package minecraftschurli.arsmagicalegacy.objects.item.spellbook;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;

import javax.annotation.Nullable;

/**
 * @author Georg Burkl
 * @version 2019-11-07
 */
public class SpellBookContainer extends Container {
    protected SpellBookContainer(@Nullable ContainerType<?> type, int id) {
        super(type, id);
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
