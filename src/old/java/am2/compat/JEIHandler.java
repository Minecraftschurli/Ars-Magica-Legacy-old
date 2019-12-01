package am2.compat;

import am2.api.recipes.RecipesEssenceRefiner;
import am2.compat.jei.EssenceRefinerRecipeCategory;
import am2.compat.jei.EssenceRefinerRecipeHandler;
import mezz.jei.api.IJeiRuntime;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;

@JEIPlugin
public class JEIHandler implements IModPlugin{

	@Override
	public void register(IModRegistry registry) {
		registry.addRecipeCategories(new EssenceRefinerRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
		registry.addRecipeHandlers(new EssenceRefinerRecipeHandler());
		registry.addRecipes(RecipesEssenceRefiner.essenceRefinement().getAllRecipes());
	}

	@Override
	public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
		
	}

}
