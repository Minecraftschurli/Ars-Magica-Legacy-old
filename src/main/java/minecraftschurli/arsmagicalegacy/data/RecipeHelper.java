package minecraftschurli.arsmagicalegacy.data;

import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.data.ShapelessRecipeBuilder;
import net.minecraft.item.Item;
import net.minecraft.tags.Tag;
import net.minecraft.util.IItemProvider;

/**
 * @author Minecraftschurli
 * @version 2019-11-13
 */
public final class RecipeHelper {
    public static ShapedRecipeBuilder addLargeCompressRecipe(IItemProvider item1, IItemProvider item2) {
        return ShapedRecipeBuilder.shapedRecipe(item1)
                .patternLine("XXX")
                .patternLine("XXX")
                .patternLine("XXX")
                .key('X', item2)
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(ItemPredicate.Builder.create().item(item2).build()));
    }

    public static ShapedRecipeBuilder addLargeCompressRecipe(IItemProvider item1, Tag<Item> item2) {
        return ShapedRecipeBuilder.shapedRecipe(item1)
                .patternLine("XXX")
                .patternLine("XXX")
                .patternLine("XXX")
                .key('X', item2)
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(ItemPredicate.Builder.create().tag(item2).build()));
    }

    public static ShapedRecipeBuilder addSmallCompressRecipe(IItemProvider item1, IItemProvider item2) {
        return ShapedRecipeBuilder.shapedRecipe(item1)
                .patternLine("XX")
                .patternLine("XX")
                .key('X', item2)
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(ItemPredicate.Builder.create().item(item2).build()));
    }

    public static ShapedRecipeBuilder addSmallCompressRecipe(IItemProvider item1, Tag<Item> item2) {
        return ShapedRecipeBuilder.shapedRecipe(item1)
                .patternLine("XX")
                .patternLine("XX")
                .key('X', item2)
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(ItemPredicate.Builder.create().tag(item2).build()));
    }

    public static ShapelessRecipeBuilder addLargeDecompressRecipe(IItemProvider item1, IItemProvider item2) {
        return ShapelessRecipeBuilder
                .shapelessRecipe(item1, 9)
                .addIngredient(item2)
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(ItemPredicate.Builder.create().item(item2).build()));
    }

    public static ShapelessRecipeBuilder addLargeDecompressRecipe(IItemProvider item1, Tag<Item> item2) {
        return ShapelessRecipeBuilder
                .shapelessRecipe(item1, 9)
                .addIngredient(item2)
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(ItemPredicate.Builder.create().tag(item2).build()));
    }

    public static ShapelessRecipeBuilder addSmallDecompressRecipe(IItemProvider item1, IItemProvider item2) {
        return ShapelessRecipeBuilder
                .shapelessRecipe(item1, 4)
                .addIngredient(item2)
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(ItemPredicate.Builder.create().item(item2).build()));
    }

    public static ShapelessRecipeBuilder addSmallDecompressRecipe(IItemProvider item1, Tag<Item> item2) {
        return ShapelessRecipeBuilder
                .shapelessRecipe(item1, 4)
                .addIngredient(item2)
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(ItemPredicate.Builder.create().tag(item2).build()));
    }

    public static ShapedRecipeBuilder addStairRecipe(IItemProvider item1, IItemProvider item2) {
        return ShapedRecipeBuilder
                .shapedRecipe(item1, 4)
                .patternLine("X  ")
                .patternLine("XX ")
                .patternLine("XXX")
                .key('X', item2)
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(ItemPredicate.Builder.create().item(item2).build()));
    }

    public static ShapedRecipeBuilder addStairRecipe(IItemProvider item1, Tag<Item> item2) {
        return ShapedRecipeBuilder
                .shapedRecipe(item1, 4)
                .patternLine("X  ")
                .patternLine("XX ")
                .patternLine("XXX")
                .key('X', item2)
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(ItemPredicate.Builder.create().tag(item2).build()));
    }

    public static ShapedRecipeBuilder addSlabRecipe(IItemProvider item1, IItemProvider item2) {
        return ShapedRecipeBuilder
                .shapedRecipe(item1, 6)
                .patternLine("XXX")
                .key('X', item2)
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(ItemPredicate.Builder.create().item(item2).build()));
    }

    public static ShapedRecipeBuilder addSlabRecipe(IItemProvider item1, Tag<Item> item2) {
        return ShapedRecipeBuilder
                .shapedRecipe(item1, 6)
                .patternLine("XXX")
                .key('X', item2)
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(ItemPredicate.Builder.create().tag(item2).build()));
    }
    //TODO @Minecraftschurli : Add furnace recipe helper
}
