package minecraftschurli.arsmagicalegacy.event;

import minecraftschurli.arsmagicalegacy.api.spell.crafting.*;
import net.minecraftforge.eventbus.api.*;

/**
 * @author Minecraftschurli
 * @version 2019-12-09
 */
public class SpellRecipeItemsEvent extends Event {
    public String id;
    public ISpellIngredient[] recipeItems;

    public SpellRecipeItemsEvent(String id, ISpellIngredient[] recipeItems) {
        this.id = id;
        this.recipeItems = recipeItems;
    }
}
