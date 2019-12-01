package am2.compat.jei;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import am2.api.recipes.RecipeArsMagica;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fluids.FluidStack;

public class EssenceRefinerRecipeWrapper implements IRecipeWrapper {
	
	RecipeArsMagica recipe;
	
	public EssenceRefinerRecipeWrapper(RecipeArsMagica recipe) {
		this.recipe = recipe;
	}
	
	@Override
	public List<?> getInputs() {
		return Lists.newArrayList(recipe.getRecipeItems());
	}

	@Override
	public List<?> getOutputs() {
		return Lists.newArrayList(recipe.getOutput());
	}

	@Override
	public List<FluidStack> getFluidInputs() {
		return ImmutableList.of();
	}

	@Override
	public List<FluidStack> getFluidOutputs() {
		return ImmutableList.of();
	}

	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
		
	}

	@Override
	public void drawAnimations(Minecraft minecraft, int recipeWidth, int recipeHeight) {
		
	}

	@Override
	public List<String> getTooltipStrings(int mouseX, int mouseY) {
		return null;
	}

	@Override
	public boolean handleClick(Minecraft minecraft, int mouseX, int mouseY, int mouseButton) {
		return false;
	}

}
