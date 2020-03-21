package minecraftschurli.arsmagicalegacy.objects.spell;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import javafx.util.Pair;
import minecraftschurli.arsmagicalegacy.api.util.NBTUtils;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ISpellIngredient;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.IngredientTypes;
import net.minecraft.client.resources.JsonReloadListener;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Minecraftschurli
 * @version 2020-02-03
 */
public class SpellRecipeManager extends JsonReloadListener {
    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().create();
    private Map<ResourceLocation, ISpellIngredient[]> recipes = ImmutableMap.of();

    public SpellRecipeManager() {
        super(GSON, "spell_recipes");
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
