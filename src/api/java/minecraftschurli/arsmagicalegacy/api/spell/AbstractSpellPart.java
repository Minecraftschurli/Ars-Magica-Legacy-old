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
     * @deprecated use json recipes instead (data/&lt;modid&gt;/spell_recipes/&lt;part_name&gt;.json)
     */
    @Deprecated
    public ISpellIngredient[] getRecipe() {
        return null;
    }

    public void encodeBasicData(CompoundNBT tag, ISpellIngredient[] recipe) {}

    /**
     * @return the {@link EnumSet} of Modifires that affect this {@link AbstractSpellPart SpellPart}
     */
    public abstract EnumSet<SpellModifiers> getModifiers();
}
