package minecraftschurli.arsmagicalegacy.objects.item.spellbook;

import minecraftschurli.arsmagicalegacy.objects.item.*;
import minecraftschurli.arsmagicalegacy.util.*;
import net.minecraft.inventory.*;

/**
 * @author Minecraftschurli
 * @version 2019-11-11
 */
public class SpellBookSlot extends SlotOneItemClassOnly<SpellItem> {
    public SpellBookSlot(IInventory inventoryIn, int index, int xPosition, int yPosition) {
        super(inventoryIn, index, xPosition, yPosition, SpellItem.class, 1);
    }
}
