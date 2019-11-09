package minecraftschurli.arsmagicalegacy.objects.item.spellbook;

import minecraftschurli.arsmagicalegacy.init.Containers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

/**
 * @author Minecraftschurli
 * @version 2019-11-07
 */
public class SpellBookContainer extends Container {
    private final IItemHandler inv;

    public SpellBookContainer(int id, IInventory inv) {
        this(id, inv, new ItemStackHandler(0));
    }

    public SpellBookContainer(int id, IInventory inventory, IItemHandler inv) {
        super(Containers.SPELLBOOK.get(), id);
        this.inv = inv;

        for(int k = 0; k < 3; ++k) {
            for(int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(inventory, l + k * 9 + 9, 36 + l * 18, 137 + k * 18));
            }
        }

        for(int i1 = 0; i1 < 9; ++i1) {
            this.addSlot(new Slot(inventory, i1, 36 + i1 * 18, 195));
        }
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
