package minecraftschurli.arsmagicalegacy.api.spellsystem;

import net.minecraft.item.*;
import net.minecraft.tags.*;

/**
 * @author Minecraftschurli
 * @version 2019-11-19
 */
public class ItemTagSpellIngredient implements ISpellIngredient {
    public final int amount;
    public final Tag<Item> tag;

    public ItemTagSpellIngredient(Tag<Item> tag, int amount) {
        this.tag = tag;
        this.amount = amount;
    }
}
