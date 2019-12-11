package minecraftschurli.arsmagicalegacy.api.spell.crafting;

import net.minecraft.item.Item;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

/**
 * @author Minecraftschurli
 * @version 2019-11-19
 */
public class ItemTagSpellIngredient implements ISpellIngredient {
    private int amount;
    private Tag<Item> tag;

    ItemTagSpellIngredient(CompoundNBT nbt) {
        deserializeNBT(nbt);
    }

    public ItemTagSpellIngredient(Tag<Item> tag, int amount) {
        this.tag = tag;
        this.amount = amount;
    }

    public ItemTagSpellIngredient(Tag<Item> tag) {
        this(tag, 1);
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putString(TYPE_KEY, "item_tag");
        nbt.putString("location", tag.getId().toString());
        nbt.putInt("amount", amount);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        nbt.remove(TYPE_KEY);
        tag = new ItemTags.Wrapper(new ResourceLocation(nbt.getString("location")));
        amount = nbt.getInt("amount");
    }

    @Override
    public boolean canCombine(ISpellIngredient other) {
        return (other instanceof ItemTagSpellIngredient) && ((ItemTagSpellIngredient) other).tag.getId() == tag.getId();
    }

    @Override
    public ISpellIngredient combine(ISpellIngredient other) {
        return new ItemTagSpellIngredient(tag, amount + ((ItemTagSpellIngredient) other).amount);
    }

    @Override
    public ITextComponent getTooltip() {
        return new StringTextComponent("#"+tag.getId().toString()+": "+amount);
    }
}
