package minecraftschurli.arsmagicalegacy.data;

import minecraftschurli.arsmagicalegacy.init.ModItems;
import minecraftschurli.arsmagicalegacy.init.ModTags;
import net.minecraft.data.*;
import net.minecraft.item.Items;

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
        RecipeHelper.addLargeCompressRecipe(ModItems.CHIMERITE_BLOCK.get(), ModItems.CHIMERITE.get());
        RecipeHelper.addLargeCompressRecipe(ModItems.TOPAZ_BLOCK.get(), ModItems.TOPAZ.get());
        RecipeHelper.addLargeCompressRecipe(ModItems.VINTEUM_BLOCK.get(), ModItems.VINTEUM.get());
        RecipeHelper.addLargeCompressRecipe(ModItems.MOONSTONE_BLOCK.get(), ModItems.MOONSTONE.get());
        RecipeHelper.addLargeCompressRecipe(ModItems.SUNSTONE_BLOCK.get(), ModItems.SUNSTONE.get());
        RecipeHelper.addLargeDecompressRecipe(ModItems.CHIMERITE.get(), ModItems.CHIMERITE_BLOCK.get());
        RecipeHelper.addLargeDecompressRecipe(ModItems.CHIMERITE.get(), ModItems.TOPAZ_BLOCK.get());
        RecipeHelper.addLargeDecompressRecipe(ModItems.CHIMERITE.get(), ModItems.VINTEUM_BLOCK.get());
        RecipeHelper.addLargeDecompressRecipe(ModItems.CHIMERITE.get(), ModItems.CHIMERITE_BLOCK.get());
        RecipeHelper.addLargeDecompressRecipe(ModItems.CHIMERITE.get(), ModItems.CHIMERITE_BLOCK.get());
        ShapelessRecipeBuilder.shapelessRecipe(ModItems.WITCHWOOD_PLANKS.get(), 4).addIngredient(ModTags.Items.LOGS_WITCHWOOD);
        ShapedRecipeBuilder.shapedRecipe(ModItems.WITCHWOOD_DOOR.get(), 3).patternLine("XX").patternLine("XX").patternLine("XX").key('X', ModItems.WITCHWOOD_PLANKS.get());
        ShapedRecipeBuilder.shapedRecipe(ModItems.WITCHWOOD_TRAPDOOR.get(), 2).patternLine("XXX").patternLine("XXX").key('X', ModItems.WITCHWOOD_PLANKS.get());
        ShapedRecipeBuilder.shapedRecipe(ModItems.WITCHWOOD_FENCE.get(), 3).patternLine("XSX").patternLine("XSX").key('X', ModItems.WITCHWOOD_PLANKS.get()).key('S', Items.STICK);
        ShapedRecipeBuilder.shapedRecipe(ModItems.WITCHWOOD_FENCE_GATE.get(), 3).patternLine("SXS").patternLine("SXS").key('X', ModItems.WITCHWOOD_PLANKS.get()).key('S', Items.STICK);
        ShapedRecipeBuilder.shapedRecipe(ModItems.WITCHWOOD_PRESSURE_PLATE.get()).patternLine("XX").key('X', ModItems.WITCHWOOD_PLANKS.get());
        ShapelessRecipeBuilder.shapelessRecipe(ModItems.WITCHWOOD_BUTTON.get()).addIngredient(ModItems.WITCHWOOD_PLANKS.get());
    }
}
