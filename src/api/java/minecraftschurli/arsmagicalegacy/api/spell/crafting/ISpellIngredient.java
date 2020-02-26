package minecraftschurli.arsmagicalegacy.api.spell.crafting;

import net.minecraft.nbt.*;
import net.minecraft.util.math.*;
import net.minecraft.util.text.*;
import net.minecraft.world.*;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.common.util.*;

/**
 * @author Minecraftschurli
 * @version 2019-11-19
 */
public interface ISpellIngredient extends INBTSerializable<CompoundNBT> {
    String TYPE_KEY = "ingredient_type";

    boolean canCombine(ISpellIngredient other);

    ISpellIngredient combine(ISpellIngredient other);

    ITextComponent getTooltip();

    boolean consume(World world, BlockPos pos);

    @OnlyIn(Dist.CLIENT)
    default void render(){}

    String toString();

    String getType();

    @Override
    default CompoundNBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putString(TYPE_KEY, getType());
        this.writeToNBT(nbt);
        return nbt;
    }

    void writeToNBT(CompoundNBT nbt);
}
