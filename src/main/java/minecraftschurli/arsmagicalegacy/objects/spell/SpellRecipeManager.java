package minecraftschurli.arsmagicalegacy.objects.spell;

import com.google.common.collect.*;
import com.google.gson.*;
import javafx.util.*;
import minecraftschurli.arsmagicalegacy.api.*;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.*;
import net.minecraft.client.resources.*;
import net.minecraft.nbt.*;
import net.minecraft.profiler.*;
import net.minecraft.resources.*;
import net.minecraft.util.*;

import java.util.*;

/**
 * @author Minecraftschurli
 * @version 2020-02-03
 */
public class SpellRecipeManager extends JsonReloadListener {
    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().create();
    private Map<ResourceLocation, ISpellIngredient[]> recipes = ImmutableMap.of();

    public SpellRecipeManager() {
        super(GSON, "recipes/spell");
    }

    public ISpellIngredient[] getRecipe(ResourceLocation spellPart) {
        ISpellIngredient[] ingredients = this.recipes.get(spellPart);
        /*if (ingredients == null)
            ingredients = ArsMagicaAPI.getSpellPartRegistry().getValue(spellPart).getRecipe();*/
        return ingredients;
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonObject> splashList, IResourceManager resourceManagerIn, IProfiler profilerIn) {
        this.recipes = splashList.entrySet().stream().map(entry -> {
            List<ISpellIngredient> ingredients = new ArrayList<>();
            for (JsonElement e : entry.getValue().getAsJsonArray("ingredients")) {
                ingredients.add(IngredientTypes.deserialize((CompoundNBT) NBTUtils.jsonToNBT(e)));
            }
            return new Pair<>(entry.getKey(), ingredients.toArray(new ISpellIngredient[0]));
        }).collect(ImmutableMap.toImmutableMap(Pair::getKey, Pair::getValue));
    }
}
