package minecraftschurli.arsmagicalegacy.data;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.IDataProvider;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;

import java.util.function.Consumer;

/**
 * @author Minecraftschurli
 * @version 2019-11-12
 */
public class AMLRecipeProvider extends RecipeProvider {
    public AMLRecipeProvider(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
        //TODO @IchHabeHunger54 : Register recipes
    }
}
