package minecraftschurli.arsmagicalegacy.api.spellsystem;

import net.minecraftforge.registries.ForgeRegistryEntry;

/**
 * @author Minecraftschurli
 * @version 2019-11-16
 */
public abstract class SpellPart<T extends SpellPart<T>> extends ForgeRegistryEntry<T> {
    SpellPart (){

    }
    public abstract ISpellIngredient[] getRecipeItems();
}
