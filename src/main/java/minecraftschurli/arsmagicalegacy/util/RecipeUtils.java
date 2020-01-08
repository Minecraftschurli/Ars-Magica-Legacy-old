package minecraftschurli.arsmagicalegacy.util;

import net.minecraft.advancements.criterion.*;
import net.minecraft.data.*;
import net.minecraft.item.*;
import net.minecraft.item.crafting.*;
import net.minecraft.tags.*;
import net.minecraft.util.*;

import java.util.function.*;

/**
 * @author Minecraftschurli
 * @version 2019-11-13
 */
public final class RecipeUtils {
    public static void addLargeCompressRecipe(Consumer<IFinishedRecipe> c, IItemProvider item1, IItemProvider item2) {
        ShapedRecipeBuilder.shapedRecipe(item1).patternLine("XXX").patternLine("XXX").patternLine("XXX").key('X', item2).addCriterion("item", InventoryChangeTrigger.Instance.forItems(ItemPredicate.Builder.create().item(item2).build())).build(c);
    }

    public static void addLargeCompressRecipe(Consumer<IFinishedRecipe> c, IItemProvider item1, Tag<Item> item2) {
        ShapedRecipeBuilder.shapedRecipe(item1).patternLine("XXX").patternLine("XXX").patternLine("XXX").key('X', item2).addCriterion("item", InventoryChangeTrigger.Instance.forItems(ItemPredicate.Builder.create().tag(item2).build())).build(c);
    }

    public static void addSmallCompressRecipe(Consumer<IFinishedRecipe> c, IItemProvider item1, IItemProvider item2) {
        ShapedRecipeBuilder.shapedRecipe(item1).patternLine("XX").patternLine("XX").key('X', item2).addCriterion("item", InventoryChangeTrigger.Instance.forItems(ItemPredicate.Builder.create().item(item2).build())).build(c);
    }

    public static void addSmallCompressRecipe(Consumer<IFinishedRecipe> c, IItemProvider item1, Tag<Item> item2) {
        ShapedRecipeBuilder.shapedRecipe(item1).patternLine("XX").patternLine("XX").key('X', item2).addCriterion("item", InventoryChangeTrigger.Instance.forItems(ItemPredicate.Builder.create().tag(item2).build())).build(c);
    }

    public static void addLargeDecompressRecipe(Consumer<IFinishedRecipe> c, IItemProvider item1, IItemProvider item2) {
        ShapelessRecipeBuilder.shapelessRecipe(item1, 9).addIngredient(item2).addCriterion("item", InventoryChangeTrigger.Instance.forItems(ItemPredicate.Builder.create().item(item2).build())).build(c);
    }

    public static void addLargeDecompressRecipe(Consumer<IFinishedRecipe> c, IItemProvider item1, Tag<Item> item2) {
        ShapelessRecipeBuilder.shapelessRecipe(item1, 9).addIngredient(item2).addCriterion("item", InventoryChangeTrigger.Instance.forItems(ItemPredicate.Builder.create().tag(item2).build())).build(c);
    }

    public static void addSmallDecompressRecipe(Consumer<IFinishedRecipe> c, IItemProvider item1, IItemProvider item2) {
        ShapelessRecipeBuilder.shapelessRecipe(item1, 4).addIngredient(item2).addCriterion("item", InventoryChangeTrigger.Instance.forItems(ItemPredicate.Builder.create().item(item2).build())).build(c);
    }

    public static void addSmallDecompressRecipe(Consumer<IFinishedRecipe> c, IItemProvider item1, Tag<Item> item2) {
        ShapelessRecipeBuilder.shapelessRecipe(item1, 4).addIngredient(item2).addCriterion("item", InventoryChangeTrigger.Instance.forItems(ItemPredicate.Builder.create().tag(item2).build())).build(c);
    }

    public static void addStairRecipe(Consumer<IFinishedRecipe> c, IItemProvider item1, IItemProvider item2) {
        ShapedRecipeBuilder.shapedRecipe(item1, 4).patternLine("X  ").patternLine("XX ").patternLine("XXX").key('X', item2).addCriterion("item", InventoryChangeTrigger.Instance.forItems(ItemPredicate.Builder.create().item(item2).build())).build(c); 
    }

    public static void addStairRecipe(Consumer<IFinishedRecipe> c, IItemProvider item1, Tag<Item> item2) {
        ShapedRecipeBuilder.shapedRecipe(item1, 4).patternLine("X  ").patternLine("XX ").patternLine("XXX").key('X', item2).addCriterion("item", InventoryChangeTrigger.Instance.forItems(ItemPredicate.Builder.create().tag(item2).build())).build(c);
    }

    public static void addSlabRecipe(Consumer<IFinishedRecipe> c, IItemProvider item1, IItemProvider item2) {
        ShapedRecipeBuilder.shapedRecipe(item1, 6).patternLine("XXX").key('X', item2).addCriterion("item", InventoryChangeTrigger.Instance.forItems(ItemPredicate.Builder.create().item(item2).build())).build(c);
    }

    public static void addSlabRecipe(Consumer<IFinishedRecipe> c, IItemProvider item1, Tag<Item> item2) {
        ShapedRecipeBuilder.shapedRecipe(item1, 6).patternLine("XXX").key('X', item2).addCriterion("item", InventoryChangeTrigger.Instance.forItems(ItemPredicate.Builder.create().tag(item2).build())).build(c);
    }

    public static void addDoorRecipe(Consumer<IFinishedRecipe> c, IItemProvider item1, IItemProvider item2) {
        ShapedRecipeBuilder.shapedRecipe(item1, 3).patternLine("XX").patternLine("XX").patternLine("XX").key('X', item2).addCriterion("item", InventoryChangeTrigger.Instance.forItems(ItemPredicate.Builder.create().item(item2).build())).build(c);
    }

    public static void addDoorRecipe(Consumer<IFinishedRecipe> c, IItemProvider item1, Tag<Item> item2) {
        ShapedRecipeBuilder.shapedRecipe(item1, 3).patternLine("XX").patternLine("XX").patternLine("XX").key('X', item2).addCriterion("item", InventoryChangeTrigger.Instance.forItems(ItemPredicate.Builder.create().tag(item2).build())).build(c);
    }

    public static void addTrapdoorRecipe(Consumer<IFinishedRecipe> c, IItemProvider item1, IItemProvider item2) {
        ShapedRecipeBuilder.shapedRecipe(item1, 2).patternLine("XXX").patternLine("XXX").key('X', item2).addCriterion("item", InventoryChangeTrigger.Instance.forItems(ItemPredicate.Builder.create().item(item2).build())).build(c);
    }

    public static void addTrapdoorRecipe(Consumer<IFinishedRecipe> c, IItemProvider item1, Tag<Item> item2) {
        ShapedRecipeBuilder.shapedRecipe(item1, 2).patternLine("XXX").patternLine("XXX").key('X', item2).addCriterion("item", InventoryChangeTrigger.Instance.forItems(ItemPredicate.Builder.create().tag(item2).build())).build(c);
    }

    public static void addFenceRecipe(Consumer<IFinishedRecipe> c, IItemProvider item1, IItemProvider item2) {
        ShapedRecipeBuilder.shapedRecipe(item1, 3).patternLine("XSX").patternLine("XSX").key('X', item2).key('S', Items.STICK).addCriterion("item", InventoryChangeTrigger.Instance.forItems(ItemPredicate.Builder.create().item(item2).build())).build(c);
    }

    public static void addFenceRecipe(Consumer<IFinishedRecipe> c, IItemProvider item1, Tag<Item> item2) {
        ShapedRecipeBuilder.shapedRecipe(item1, 3).patternLine("XSX").patternLine("XSX").key('X', item2).key('S', Items.STICK).addCriterion("item", InventoryChangeTrigger.Instance.forItems(ItemPredicate.Builder.create().tag(item2).build())).build(c);
    }

    public static void addFenceGateRecipe(Consumer<IFinishedRecipe> c, IItemProvider item1, IItemProvider item2) {
        ShapedRecipeBuilder.shapedRecipe(item1).patternLine("SXS").patternLine("SXS").key('X', item2).key('S', Items.STICK).addCriterion("item", InventoryChangeTrigger.Instance.forItems(ItemPredicate.Builder.create().item(item2).build())).build(c);
    }

    public static void addFenceGateRecipe(Consumer<IFinishedRecipe> c, IItemProvider item1, Tag<Item> item2) {
        ShapedRecipeBuilder.shapedRecipe(item1).patternLine("SXS").patternLine("SXS").key('X', item2).key('S', Items.STICK).addCriterion("item", InventoryChangeTrigger.Instance.forItems(ItemPredicate.Builder.create().tag(item2).build())).build(c);
    }

    public static void addPressurePlateRecipe(Consumer<IFinishedRecipe> c, IItemProvider item1, IItemProvider item2) {
        ShapedRecipeBuilder.shapedRecipe(item1).patternLine("XX").key('X', item2).addCriterion("item", InventoryChangeTrigger.Instance.forItems(ItemPredicate.Builder.create().item(item2).build())).build(c);
    }

    public static void addPressurePlateRecipe(Consumer<IFinishedRecipe> c, IItemProvider item1, Tag<Item> item2) {
        ShapedRecipeBuilder.shapedRecipe(item1).patternLine("XX").key('X', item2).addCriterion("item", InventoryChangeTrigger.Instance.forItems(ItemPredicate.Builder.create().tag(item2).build())).build(c);
    }

    public static void addButtonRecipe(Consumer<IFinishedRecipe> c, IItemProvider item1, IItemProvider item2) {
        ShapelessRecipeBuilder.shapelessRecipe(item1).addIngredient(item2).addCriterion("item", InventoryChangeTrigger.Instance.forItems(ItemPredicate.Builder.create().item(item2).build())).build(c);
    }

    public static void addButtonRecipe(Consumer<IFinishedRecipe> c, IItemProvider item1, Tag<Item> item2) {
        ShapelessRecipeBuilder.shapelessRecipe(item1).addIngredient(item2).addCriterion("item", InventoryChangeTrigger.Instance.forItems(ItemPredicate.Builder.create().tag(item2).build())).build(c);
    }

    public static void addSmeltingRecipe(Consumer<IFinishedRecipe> c, IItemProvider output, IItemProvider input, float exp) {
        addSmeltingRecipe(c, output, input, exp, 200);
    }

    public static void addSmeltingRecipe(Consumer<IFinishedRecipe> c, IItemProvider output, Tag<Item> input, float exp) {
        addSmeltingRecipe(c, output, input, exp, 200);
    }

    public static void addSmeltingRecipe(Consumer<IFinishedRecipe> c, IItemProvider output, IItemProvider input, float exp, int time) {
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(input), output, exp, time).addCriterion("item", InventoryChangeTrigger.Instance.forItems(input)).build(c);
    }

    public static void addSmeltingRecipe(Consumer<IFinishedRecipe> c, IItemProvider output, Tag<Item> input, float exp, int time) {
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromTag(input), output, exp, time).addCriterion("item", InventoryChangeTrigger.Instance.forItems(ItemPredicate.Builder.create().tag(input).build())).build(c);
    }

    public static void addBlastingRecipe(Consumer<IFinishedRecipe> c, IItemProvider output, IItemProvider input, float exp) {
        addBlastingRecipe(c, output, input, exp, 100);
    }

    public static void addBlastingRecipe(Consumer<IFinishedRecipe> c, IItemProvider output, Tag<Item> input, float exp) {
        addBlastingRecipe(c, output, input, exp, 100);
    }

    public static void addBlastingRecipe(Consumer<IFinishedRecipe> c, IItemProvider output, IItemProvider input, float exp, int time) {
        CookingRecipeBuilder.blastingRecipe(Ingredient.fromItems(input), output, exp, time).addCriterion("item", InventoryChangeTrigger.Instance.forItems(input)).build(c);
    }

    public static void addBlastingRecipe(Consumer<IFinishedRecipe> c, IItemProvider output, Tag<Item> input, float exp, int time) {
        CookingRecipeBuilder.blastingRecipe(Ingredient.fromTag(input), output, exp, time).addCriterion("item", InventoryChangeTrigger.Instance.forItems(ItemPredicate.Builder.create().tag(input).build())).build(c);
    }

    public static void addSmokingRecipe(Consumer<IFinishedRecipe> c, IItemProvider output, IItemProvider input, float exp) {
        addSmokingRecipe(c, output, input, exp, 100);
    }

    public static void addSmokingRecipe(Consumer<IFinishedRecipe> c, IItemProvider output, Tag<Item> input, float exp) {
        addSmokingRecipe(c, output, input, exp, 100);
    }

    public static void addSmokingRecipe(Consumer<IFinishedRecipe> c, IItemProvider output, IItemProvider input, float exp, int time) {
        CookingRecipeBuilder.cookingRecipe(Ingredient.fromItems(input), output, exp, time, IRecipeSerializer.SMOKING).addCriterion("item", InventoryChangeTrigger.Instance.forItems(input)).build(c);
    }

    public static void addSmokingRecipe(Consumer<IFinishedRecipe> c, IItemProvider output, Tag<Item> input, float exp, int time) {
        CookingRecipeBuilder.cookingRecipe(Ingredient.fromTag(input), output, exp, time, IRecipeSerializer.SMOKING).addCriterion("item", InventoryChangeTrigger.Instance.forItems(ItemPredicate.Builder.create().tag(input).build())).build(c);
    }

    public static void addCampfireRecipe(Consumer<IFinishedRecipe> c, IItemProvider output, IItemProvider input, float exp, boolean campfire) {
        addCampfireRecipe(c, output, input, exp, 600);
    }

    public static void addCampfireRecipe(Consumer<IFinishedRecipe> c, IItemProvider output, Tag<Item> input, float exp, boolean campfire) {
        addCampfireRecipe(c, output, input, exp, 600);
    }

    public static void addCampfireRecipe(Consumer<IFinishedRecipe> c, IItemProvider output, IItemProvider input, float exp, int time) {
        CookingRecipeBuilder.cookingRecipe(Ingredient.fromItems(input), output, exp, time, IRecipeSerializer.CAMPFIRE_COOKING).addCriterion("item", InventoryChangeTrigger.Instance.forItems(input)).build(c);
    }

    public static void addCampfireRecipe(Consumer<IFinishedRecipe> c, IItemProvider output, Tag<Item> input, float exp, int time) {
        CookingRecipeBuilder.cookingRecipe(Ingredient.fromTag(input), output, exp, time, IRecipeSerializer.CAMPFIRE_COOKING).addCriterion("item", InventoryChangeTrigger.Instance.forItems(ItemPredicate.Builder.create().tag(input).build())).build(c);
    }
}
