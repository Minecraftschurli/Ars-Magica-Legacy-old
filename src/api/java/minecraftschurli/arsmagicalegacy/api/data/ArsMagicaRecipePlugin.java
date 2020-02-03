package minecraftschurli.arsmagicalegacy.api.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import minecraftschurli.arsmagicalegacy.api.NBTUtils;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ISpellIngredient;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.INBTSerializable;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.function.Consumer;

/**
 * @author Minecraftschurli
 * @version 2020-02-03
 */
public interface ArsMagicaRecipePlugin {
    default void addSpellRecipe(ResourceLocation spellID, ISpellIngredient[] ingredients, Consumer<IFinishedRecipe> consumer) {
        JsonArray array = new JsonArray();
        Arrays.stream(ingredients).map(INBTSerializable::serializeNBT).map(NBTUtils::NBTToJson).forEach(array::add);
        consumer.accept(new Recipe(spellID, array));
    }

    final class Recipe implements IFinishedRecipe {
        private final ResourceLocation spellID;
        private final JsonArray ingredients;

        private Recipe(ResourceLocation spellID, JsonArray ingredients) {
            this.spellID = spellID;
            this.ingredients = ingredients;
        }

        @Override
        public void serialize(JsonObject json) {}

        /**
         * Gets the JSON for the recipe.
         */
        @Override
        public JsonObject getRecipeJson() {
            JsonObject object = new JsonObject();
            object.add("ingredients", ingredients);
            return object;
        }

        @Override
        public ResourceLocation getID() {
            return new ResourceLocation(spellID.getNamespace(), "spell/"+spellID.getPath());
        }

        @Override
        public IRecipeSerializer<?> getSerializer() {
            return null;
        }

        @Nullable
        @Override
        public JsonObject getAdvancementJson() {
            return null;
        }

        @Nullable
        @Override
        public ResourceLocation getAdvancementID() {
            return null;
        }
    }
}
