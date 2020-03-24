package minecraftschurli.arsmagicalegacy.api.spell;

import java.util.EnumSet;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ISpellIngredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.registries.ForgeRegistryEntry;

/**
 * @author Minecraftschurli
 * @version 2019-11-16
 */
public abstract class AbstractSpellPart extends ForgeRegistryEntry<AbstractSpellPart> {
    /**
     *
     * @return
     */
    public abstract ISpellIngredient[] getRecipe();

    public abstract void encodeBasicData(CompoundNBT tag, ISpellIngredient[] recipe);

    /**
     * What modifier affect this spell part?
     *
     * @return
     */
    public abstract EnumSet<SpellModifiers> getModifiers();
}
