package minecraftschurli.arsmagicalegacy.api.spell.crafting;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.items.ItemHandlerHelper;

/**
 * @author Minecraftschurli
 * @version 2019-11-19
 */
public class ItemStackSpellIngredient implements ISpellIngredient {
    private ItemStack stack;

    ItemStackSpellIngredient(CompoundNBT nbt) {
        deserializeNBT(nbt);
    }

    public ItemStackSpellIngredient(ItemStack stack) {
        this.stack = stack;
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putString(TYPE_KEY, "item_stack");
        return this.stack.write(nbt);
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        nbt.remove(TYPE_KEY);
        this.stack = ItemStack.read(nbt);
    }

    @Override
    public boolean canCombine(ISpellIngredient other) {
        return (other instanceof ItemStackSpellIngredient) &&
                ItemHandlerHelper.canItemStacksStack(stack, ((ItemStackSpellIngredient) other).stack);
    }

    @Override
    public ISpellIngredient combine(ISpellIngredient other) {
        ItemStack newStack = new ItemStack(stack.getItem(), stack.getCount(), stack.getTag());
        newStack.grow(((ItemStackSpellIngredient) other).stack.getCount());
        return new ItemStackSpellIngredient(newStack);
    }

    @Override
    public ITextComponent getTooltip() {
        return stack.getDisplayName().appendSibling(new StringTextComponent(": "+stack.getCount()));
    }
}
