package minecraftschurli.arsmagicalegacy.api.spell.crafting;

import net.minecraft.nbt.*;
import net.minecraft.util.math.*;
import net.minecraft.util.text.*;
import net.minecraft.world.*;

/**
 * @author Minecraftschurli
 * @version 2019-11-19
 */
public class EssenceSpellIngredient implements ISpellIngredient {
    public static final String TYPE = "essence";
    private int amount;
    private EssenceType essenceType;

    EssenceSpellIngredient(CompoundNBT nbt) {
        deserializeNBT(nbt);
    }

    public EssenceSpellIngredient(EssenceType type, int amount) {
        this.essenceType = type;
        this.amount = amount;
    }

    public EssenceSpellIngredient(EssenceType type) {
        this(type, 1);
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putString(TYPE_KEY, TYPE);
        nbt.putString("essence", essenceType.name());
        nbt.putInt("amount", amount);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        //nbt.remove(TYPE_KEY);
        essenceType = EssenceType.valueOf(nbt.getString("essence"));
        amount = nbt.getInt("amount");
    }

    @Override
    public boolean canCombine(ISpellIngredient other) {
        return (other instanceof EssenceSpellIngredient) && ((EssenceSpellIngredient) other).essenceType == this.essenceType;
    }

    @Override
    public ISpellIngredient combine(ISpellIngredient other) {
        return new EssenceSpellIngredient(essenceType, amount + ((EssenceSpellIngredient) other).amount);
    }

    @Override
    public ITextComponent getTooltip() {
        return new TranslationTextComponent(essenceType.getTranslationKey()).appendText(": " + amount);
    }

    @Override
    public boolean consume(World world, BlockPos pos) {
        return true;
    }

    @Override
    public String toString() {
        return "EssenceSpellIngredient{" +
                "amount=" + amount +
                ", essenceType=" + essenceType.toString() +
                '}';
    }
}
