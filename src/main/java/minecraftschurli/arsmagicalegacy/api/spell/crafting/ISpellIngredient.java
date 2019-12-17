package minecraftschurli.arsmagicalegacy.api.spell.crafting;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.util.INBTSerializable;

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
}
