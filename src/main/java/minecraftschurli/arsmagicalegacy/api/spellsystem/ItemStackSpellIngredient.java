package minecraftschurli.arsmagicalegacy.api.spellsystem;

import net.minecraft.item.ItemStack;

/**
 * @author Minecraftschurli
 * @version 2019-11-19
 */
public class ItemStackSpellIngredient implements ISpellIngredient {
    public final ItemStack stack;

    public ItemStackSpellIngredient(ItemStack stack) {
        this.stack = stack;
    }
}
