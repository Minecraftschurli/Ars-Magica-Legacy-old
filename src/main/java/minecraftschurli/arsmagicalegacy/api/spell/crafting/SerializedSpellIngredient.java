package minecraftschurli.arsmagicalegacy.api.spell.crafting;

import net.minecraft.item.*;

/**
 * @author Minecraftschurli
 * @version 2019-12-09
 */
public class SerializedSpellIngredient {
    private String key;
    private int quantity;
    private ItemStack stack;

    public SerializedSpellIngredient(String key, int quantity, ItemStack stack) {
        this.key = key;
        this.quantity = quantity;
        this.stack = stack;
    }

    public String getKey() {
        return key;
    }

    public ItemStack getStack() {
        return stack;
    }

    public int getQuantity() {
        return quantity;
    }
}
