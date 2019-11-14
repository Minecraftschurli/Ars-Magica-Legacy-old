package minecraftschurli.arsmagicalegacy.data;

import minecraftschurli.arsmagicalegacy.init.ModItems;
import minecraftschurli.arsmagicalegacy.init.ModTags;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.data.*;
import net.minecraft.item.Items;
import net.minecraftforge.common.Tags;

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
        RecipeHelper.addLargeCompressRecipe(ModItems.CHIMERITE_BLOCK.get(), ModTags.Items.GEMS_CHIMERITE).build(consumer);
        RecipeHelper.addLargeCompressRecipe(ModItems.TOPAZ_BLOCK.get(), ModTags.Items.GEMS_TOPAZ).build(consumer);
        RecipeHelper.addLargeCompressRecipe(ModItems.VINTEUM_BLOCK.get(), ModTags.Items.DUSTS_VINTEUM).build(consumer);
        RecipeHelper.addLargeCompressRecipe(ModItems.MOONSTONE_BLOCK.get(), ModTags.Items.GEMS_MOONSTONE).build(consumer);
        RecipeHelper.addLargeCompressRecipe(ModItems.SUNSTONE_BLOCK.get(), ModTags.Items.GEMS_SUNSTONE).build(consumer);
        RecipeHelper.addLargeDecompressRecipe(ModItems.CHIMERITE.get(), ModTags.Items.STORAGE_BLOCKS_CHIMERITE).build(consumer);
        RecipeHelper.addLargeDecompressRecipe(ModItems.MOONSTONE.get(), ModTags.Items.STORAGE_BLOCKS_MOONSTONE).build(consumer);
        RecipeHelper.addLargeDecompressRecipe(ModItems.SUNSTONE.get(), ModTags.Items.STORAGE_BLOCKS_SUNSTONE).build(consumer);
        RecipeHelper.addLargeDecompressRecipe(ModItems.TOPAZ.get(), ModTags.Items.STORAGE_BLOCKS_TOPAZ).build(consumer);
        RecipeHelper.addLargeDecompressRecipe(ModItems.VINTEUM.get(), ModTags.Items.STORAGE_BLOCKS_VINTEUM).build(consumer);
        RecipeHelper.addSlabRecipe(ModItems.WITCHWOOD_SLAB.get(), ModItems.WITCHWOOD_PLANKS.get()).build(consumer);
        RecipeHelper.addStairRecipe(ModItems.WITCHWOOD_STAIRS.get(), ModItems.WITCHWOOD_PLANKS.get()).build(consumer);
        ShapedRecipeBuilder
                .shapedRecipe(ModItems.WITCHWOOD_DOOR.get(), 3)
                .patternLine("XX")
                .patternLine("XX")
                .patternLine("XX")
                .key('X', ModItems.WITCHWOOD_PLANKS.get())
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(ItemPredicate.Builder.create().item(ModItems.WITCHWOOD_PLANKS.get()).build()))
                .build(consumer);
        ShapedRecipeBuilder
                .shapedRecipe(ModItems.WITCHWOOD_TRAPDOOR.get(), 2)
                .patternLine("XXX")
                .patternLine("XXX")
                .key('X', ModItems.WITCHWOOD_PLANKS.get())
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(ItemPredicate.Builder.create().item(ModItems.WITCHWOOD_PLANKS.get()).build()))
                .build(consumer);
        ShapedRecipeBuilder
                .shapedRecipe(ModItems.WITCHWOOD_FENCE.get(), 3)
                .patternLine("XSX")
                .patternLine("XSX")
                .key('X', ModItems.WITCHWOOD_PLANKS.get())
                .key('S', Items.STICK)
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(ItemPredicate.Builder.create().item(ModItems.WITCHWOOD_PLANKS.get()).build()))
                .build(consumer);
        ShapedRecipeBuilder
                .shapedRecipe(ModItems.WITCHWOOD_FENCE_GATE.get(), 3)
                .patternLine("SXS")
                .patternLine("SXS")
                .key('X', ModItems.WITCHWOOD_PLANKS.get())
                .key('S', Items.STICK)
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(ItemPredicate.Builder.create().item(ModItems.WITCHWOOD_PLANKS.get()).build()))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModItems.WITCHWOOD_PRESSURE_PLATE.get())
                .patternLine("XX")
                .key('X', ModItems.WITCHWOOD_PLANKS.get())
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(ItemPredicate.Builder.create().item(ModItems.WITCHWOOD_PLANKS.get()).build()))
                .build(consumer);
        ShapelessRecipeBuilder
                .shapelessRecipe(ModItems.WITCHWOOD_BUTTON.get())
                .addIngredient(ModItems.WITCHWOOD_PLANKS.get())
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(ItemPredicate.Builder.create().item(ModItems.WITCHWOOD_PLANKS.get()).build()))
                .build(consumer);
        RecipeHelper.addSmallDecompressRecipe(ModItems.WITCHWOOD_PLANKS.get(), ModTags.Items.LOGS_WITCHWOOD).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModItems.ALTAR_CORE.get())
                .patternLine("V")
                .patternLine("S")
                .key('V', ModTags.Items.DUSTS_VINTEUM)
                .key('S', Tags.Items.STONE)
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(ItemPredicate.Builder.create().tag(ModTags.Items.DUSTS_VINTEUM).build()))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModItems.MAGIC_WALL.get(), 16)
                .patternLine("VSV")
                .key('V', ModTags.Items.DUSTS_VINTEUM)
                .key('S', Tags.Items.STONE)
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(ItemPredicate.Builder.create().tag(ModTags.Items.DUSTS_VINTEUM).build()))
                .build(consumer);
    }
}
