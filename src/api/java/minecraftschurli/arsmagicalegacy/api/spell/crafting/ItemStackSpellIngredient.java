package minecraftschurli.arsmagicalegacy.api.spell.crafting;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemHandlerHelper;

/**
 * @author Minecraftschurli
 * @version 2019-11-19
 */
public class ItemStackSpellIngredient implements ISpellIngredient {
    public static final String TYPE = "item_stack";
    private ItemStack stack;

    ItemStackSpellIngredient(CompoundNBT nbt) {
        deserializeNBT(nbt);
    }

    public ItemStackSpellIngredient(ItemStack stack) {
        this.stack = stack;
    }

    @Override
    public void writeToNBT(CompoundNBT nbt) {
        this.stack.write(nbt);
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
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
        return stack.getDisplayName().appendSibling(new StringTextComponent(": " + stack.getCount()));
    }

    @Override
    public boolean consume(World world, BlockPos pos) {
        List<ItemEntity> items = world.getEntitiesWithinAABB(ItemEntity.class, new AxisAlignedBB(pos).grow(0.5, 1, 0.5).offset(0, -2, 0));
        if (items.isEmpty())
            return false;
        Predicate<ItemEntity> filter = entity -> stack.equals(entity.getItem(), false);
        Optional<ItemEntity> optional = items.stream().filter(filter).findFirst();
        if (optional.isPresent()) {
            ItemEntity entity = optional.get();
            entity.getItem().shrink(1);
            return true;
        }
        return false;
    }

    public ItemStack getStack() {
        return stack;
    }

    @Override
    public String toString() {
        return "ItemStackSpellIngredient{" + stack.toString() + '}';
    }

    @Override
    public String getType() {
        return TYPE;
    }
}
