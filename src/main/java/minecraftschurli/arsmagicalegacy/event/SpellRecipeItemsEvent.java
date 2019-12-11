package minecraftschurli.arsmagicalegacy.event;

import minecraftschurli.arsmagicalegacy.api.spell.crafting.ISpellIngredient;
import net.minecraftforge.eventbus.api.Event;

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
