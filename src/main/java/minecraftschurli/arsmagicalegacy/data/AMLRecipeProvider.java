package minecraftschurli.arsmagicalegacy.data;

import java.util.function.Consumer;
import javax.annotation.Nonnull;
import minecraftschurli.arsmagicalegacy.ArsMagicaLegacy;
import minecraftschurli.arsmagicalegacy.init.ModEffects;
import minecraftschurli.arsmagicalegacy.init.ModItems;
import minecraftschurli.arsmagicalegacy.init.ModTags;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.data.ShapelessRecipeBuilder;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.common.data.ForgeRecipeProvider;

/**
 * @author Minecraftschurli
 * @version 2019-11-12
 */
public class AMLRecipeProvider extends ForgeRecipeProvider {
    public AMLRecipeProvider(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected void registerRecipes(@Nonnull Consumer<IFinishedRecipe> consumer) {
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
        RecipeHelper.addSmallDecompressRecipe(ModItems.WITCHWOOD_PLANKS.get(), ModTags.Items.LOGS_WITCHWOOD).build(consumer);
        RecipeHelper.addSlabRecipe(ModItems.WITCHWOOD_SLAB.get(), ModItems.WITCHWOOD_PLANKS.get()).build(consumer);
        RecipeHelper.addStairRecipe(ModItems.WITCHWOOD_STAIRS.get(), ModItems.WITCHWOOD_PLANKS.get()).build(consumer);
        ShapedRecipeBuilder
                .shapedRecipe(ModItems.WITCHWOOD_DOOR.get(), 3)
                .patternLine("XX")
                .patternLine("XX")
                .patternLine("XX")
                .key('X', ModItems.WITCHWOOD_PLANKS.get())
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(ModItems.WITCHWOOD_PLANKS.get()))
                .build(consumer);
        ShapedRecipeBuilder
                .shapedRecipe(ModItems.WITCHWOOD_TRAPDOOR.get(), 2)
                .patternLine("XXX")
                .patternLine("XXX")
                .key('X', ModItems.WITCHWOOD_PLANKS.get())
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(ModItems.WITCHWOOD_PLANKS.get()))
                .build(consumer);
        ShapedRecipeBuilder
                .shapedRecipe(ModItems.WITCHWOOD_FENCE.get(), 3)
                .patternLine("XSX")
                .patternLine("XSX")
                .key('X', ModItems.WITCHWOOD_PLANKS.get())
                .key('S', Tags.Items.RODS_WOODEN)
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(ModItems.WITCHWOOD_PLANKS.get()))
                .build(consumer);
        ShapedRecipeBuilder
                .shapedRecipe(ModItems.WITCHWOOD_FENCE_GATE.get(), 3)
                .patternLine("SXS")
                .patternLine("SXS")
                .key('X', ModItems.WITCHWOOD_PLANKS.get())
                .key('S', Tags.Items.RODS_WOODEN)
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(ModItems.WITCHWOOD_PLANKS.get()))
                .build(consumer);
        ShapedRecipeBuilder
                .shapedRecipe(ModItems.WITCHWOOD_PRESSURE_PLATE.get())
                .patternLine("XX")
                .key('X', ModItems.WITCHWOOD_PLANKS.get())
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(ModItems.WITCHWOOD_PLANKS.get()))
                .build(consumer);
        ShapelessRecipeBuilder
                .shapelessRecipe(ModItems.WITCHWOOD_BUTTON.get())
                .addIngredient(ModItems.WITCHWOOD_PLANKS.get())
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(ModItems.WITCHWOOD_PLANKS.get()))
                .build(consumer);
        ShapedRecipeBuilder
                .shapedRecipe(ModItems.RUNE.get(), 2)
                .patternLine(" X ")
                .patternLine("XXX")
                .patternLine("XX ")
                .key('X', Tags.Items.COBBLESTONE)
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(ItemPredicate.Builder.create().tag(Tags.Items.COBBLESTONE).build()))
                .build(consumer);
        ShapelessRecipeBuilder
                .shapelessRecipe(ModItems.WHITE_RUNE.get())
                .addIngredient(Tags.Items.DYES_WHITE)
                .addIngredient(ModItems.RUNE.get())
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(ModItems.RUNE.get()))
                .build(consumer);
        ShapelessRecipeBuilder
                .shapelessRecipe(ModItems.ORANGE_RUNE.get())
                .addIngredient(Tags.Items.DYES_ORANGE)
                .addIngredient(ModItems.RUNE.get())
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(ModItems.RUNE.get()))
                .build(consumer);
        ShapelessRecipeBuilder
                .shapelessRecipe(ModItems.MAGENTA_RUNE.get())
                .addIngredient(Tags.Items.DYES_MAGENTA)
                .addIngredient(ModItems.RUNE.get())
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(ModItems.RUNE.get()))
                .build(consumer);
        ShapelessRecipeBuilder
                .shapelessRecipe(ModItems.LIGHT_BLUE_RUNE.get())
                .addIngredient(Tags.Items.DYES_LIGHT_BLUE)
                .addIngredient(ModItems.RUNE.get())
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(ModItems.RUNE.get()))
                .build(consumer);
        ShapelessRecipeBuilder
                .shapelessRecipe(ModItems.YELLOW_RUNE.get())
                .addIngredient(Tags.Items.DYES_YELLOW)
                .addIngredient(ModItems.RUNE.get())
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(ModItems.RUNE.get()))
                .build(consumer);
        ShapelessRecipeBuilder
                .shapelessRecipe(ModItems.LIME_RUNE.get())
                .addIngredient(Tags.Items.DYES_LIME)
                .addIngredient(ModItems.RUNE.get())
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(ModItems.RUNE.get()))
                .build(consumer);
        ShapelessRecipeBuilder
                .shapelessRecipe(ModItems.PINK_RUNE.get())
                .addIngredient(Tags.Items.DYES_PINK)
                .addIngredient(ModItems.RUNE.get())
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(ModItems.RUNE.get()))
                .build(consumer);
        ShapelessRecipeBuilder
                .shapelessRecipe(ModItems.GRAY_RUNE.get())
                .addIngredient(Tags.Items.DYES_GRAY)
                .addIngredient(ModItems.RUNE.get())
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(ModItems.RUNE.get()))
                .build(consumer);
        ShapelessRecipeBuilder
                .shapelessRecipe(ModItems.LIGHT_GRAY_RUNE.get())
                .addIngredient(Tags.Items.DYES_LIGHT_GRAY)
                .addIngredient(ModItems.RUNE.get())
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(ModItems.RUNE.get()))
                .build(consumer);
        ShapelessRecipeBuilder
                .shapelessRecipe(ModItems.CYAN_RUNE.get())
                .addIngredient(Tags.Items.DYES_CYAN)
                .addIngredient(ModItems.RUNE.get())
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(ModItems.RUNE.get()))
                .build(consumer);
        ShapelessRecipeBuilder
                .shapelessRecipe(ModItems.PURPLE_RUNE.get())
                .addIngredient(Tags.Items.DYES_PURPLE)
                .addIngredient(ModItems.RUNE.get())
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(ModItems.RUNE.get()))
                .build(consumer);
        ShapelessRecipeBuilder
                .shapelessRecipe(ModItems.BLUE_RUNE.get())
                .addIngredient(Tags.Items.DYES_BLUE)
                .addIngredient(ModItems.RUNE.get())
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(ModItems.RUNE.get()))
                .build(consumer);
        ShapelessRecipeBuilder
                .shapelessRecipe(ModItems.BROWN_RUNE.get())
                .addIngredient(Tags.Items.DYES_BROWN)
                .addIngredient(ModItems.RUNE.get())
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(ModItems.RUNE.get()))
                .build(consumer);
        ShapelessRecipeBuilder
                .shapelessRecipe(ModItems.GREEN_RUNE.get())
                .addIngredient(Tags.Items.DYES_GREEN)
                .addIngredient(ModItems.RUNE.get())
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(ModItems.RUNE.get()))
                .build(consumer);
        ShapelessRecipeBuilder
                .shapelessRecipe(ModItems.RED_RUNE.get())
                .addIngredient(Tags.Items.DYES_RED)
                .addIngredient(ModItems.RUNE.get())
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(ModItems.RUNE.get()))
                .build(consumer);
        ShapelessRecipeBuilder
                .shapelessRecipe(ModItems.BLACK_RUNE.get())
                .addIngredient(Tags.Items.DYES_BLACK)
                .addIngredient(ModItems.RUNE.get())
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(ModItems.RUNE.get()))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModItems.LESSER_FOCUS.get())
                .patternLine(" N ")
                .patternLine("NGN")
                .patternLine(" N ")
                .key('N', Tags.Items.NUGGETS_GOLD)
                .key('G', Tags.Items.GLASS)
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(ItemPredicate.Builder.create().tag(Tags.Items.NUGGETS_GOLD).build(), ItemPredicate.Builder.create().tag(Tags.Items.GLASS).build()))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModItems.STANDARD_FOCUS.get())
                .patternLine(" R ")
                .patternLine("RFR")
                .patternLine(" R ")
                .key('R', Tags.Items.DUSTS_REDSTONE)
                .key('F', ModItems.LESSER_FOCUS.get())
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(ModItems.LESSER_FOCUS.get()))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModItems.GREATER_FOCUS.get())
                .patternLine("A A")
                .patternLine("VFV")
                .patternLine("A A")
                .key('F', ModItems.STANDARD_FOCUS.get())
                .key('V', ModItems.PURIFIED_VINTEUM.get())
                .key('A', ModItems.ARCANE_ASH.get())
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(ModItems.STANDARD_FOCUS.get()))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModItems.MANA_FOCUS.get())
                .patternLine("V")
                .patternLine("F")
                .patternLine("V")
                .key('V', ModTags.Items.DUSTS_VINTEUM)
                .key('F', ModItems.STANDARD_FOCUS.get())
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(ModItems.STANDARD_FOCUS.get()))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModItems.PLAYER_FOCUS.get())
                .patternLine("E")
                .patternLine("F")
                .key('E', ModItems.LIFE_ESSENCE.get())
                .key('F', ModItems.STANDARD_FOCUS.get())
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(ModItems.STANDARD_FOCUS.get()))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModItems.CREATURE_FOCUS.get())
                .patternLine("P ")
                .patternLine("SF")
                .patternLine("W ")
                .key('P', Items.PORKCHOP)
                .key('S', ModItems.STANDARD_FOCUS.get())
                .key('F', Items.FEATHER)
                .key('W', ItemTags.WOOL)
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(ModItems.STANDARD_FOCUS.get()))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModItems.MONSTER_FOCUS.get())
                .patternLine("I")
                .patternLine("F")
                .patternLine("I")
                .key('I', Items.IRON_SWORD)
                .key('F', ModItems.STANDARD_FOCUS.get())
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(ModItems.STANDARD_FOCUS.get()))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModItems.CHARGE_FOCUS.get())
                .patternLine("GFG")
                .key('G', Tags.Items.GLASS)
                .key('F', ModItems.STANDARD_FOCUS.get())
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(ModItems.STANDARD_FOCUS.get()))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModItems.ITEM_FOCUS.get())
                .patternLine("C")
                .patternLine("F")
                .patternLine("W")
                .key('C', Items.COBBLESTONE)
                .key('F', ModItems.STANDARD_FOCUS.get())
                .key('W', Items.CRAFTING_TABLE)
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(ModItems.STANDARD_FOCUS.get()))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModItems.FLICKER_JAR.get())
                .patternLine("GMG")
                .patternLine("P P")
                .patternLine(" P ")
                .key('G', Tags.Items.NUGGETS_GOLD)
                .key('M', ModItems.MAGIC_WALL.get())
                .key('P', Tags.Items.GLASS_PANES)
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(ItemPredicate.Builder.create().tag(Tags.Items.NUGGETS_GOLD).build()))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModItems.BUTCHER_FLICKER_FOCUS.get())
                .patternLine("123")
                .patternLine("456")
                .patternLine("787")
                .key('1', Items.PORKCHOP)
                .key('2', Items.BEEF)
                .key('3', Items.CHICKEN)
                .key('4', ModItems.FIRE_FLICKER_JAR.get())
                .key('5', Items.IRON_SWORD)
                .key('6', ModItems.LIFE_FLICKER_JAR.get())
                .key('7', ModItems.RED_RUNE.get())
                .key('8', ModItems.EVIL_BOOK.get())
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(ModItems.RUNE.get()))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModItems.CONTAINMENT_FLICKER_FOCUS.get())
                .patternLine("121")
                .patternLine("345")
                .patternLine("626")
                .key('1', Tags.Items.FENCES_WOODEN)
                .key('2', Items.COBBLESTONE_WALL)
                .key('3', ModItems.AIR_FLICKER_JAR.get())
                .key('4', ModItems.BLUE_RUNE.get())
                .key('5', ModItems.ENDER_FLICKER_JAR.get())
                .key('6', Items.IRON_BARS)
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(ModItems.RUNE.get()))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModItems.FELLING_FLICKER_FOCUS.get())
                .patternLine("12 ")
                .patternLine("3 4")
                .patternLine("  1")
                .key('1', ModTags.Items.LOGS_WITCHWOOD)
                .key('2', Items.IRON_AXE)
                .key('3', ModItems.NATURE_FLICKER_JAR.get())
                .key('4', ModItems.LIGHTNING_FLICKER_JAR.get())
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(ItemPredicate.Builder.create().tag(ModTags.Items.LOGS_WITCHWOOD).build()))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModItems.FISHING_FLICKER_FOCUS.get())
                .patternLine(" 1 ")
                .patternLine("2 3")
                .patternLine(" 4 ")
                .key('1', ItemTags.FISHES)
                .key('2', ModItems.NATURE_FLICKER_JAR.get())
                .key('3', ModItems.WATER_FLICKER_JAR.get())
                .key('4', Items.FISHING_ROD)
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(ItemPredicate.Builder.create().tag(ItemTags.FISHES).build()))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModItems.FLAT_LAND_FLICKER_FOCUS.get())
                .patternLine("1 2")
                .patternLine("345")
                .patternLine(" 6 ")
                .key('1', Items.IRON_SHOVEL)
                .key('2', Items.IRON_PICKAXE)
                .key('3', ModItems.EARTH_FLICKER_JAR.get())
                .key('4', ModItems.EARTH_ESSENCE.get())
                .key('5', ModItems.ICE_FLICKER_JAR.get())
                .key('6', ModItems.BLACK_RUNE.get())
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(ModItems.RUNE.get()))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModItems.GENTLE_RAIN_FLICKER_FOCUS.get())
                .patternLine("1 ")
                .patternLine("23")
                .patternLine("1 ")
                .key('1', ModItems.BLUE_RUNE.get())
                .key('2', ModItems.WATER_FLICKER_JAR.get())
                .key('3', ModItems.TARMA_ROOT.get())
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(ModItems.RUNE.get()))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModItems.INTERDICTION_FLICKER_FOCUS.get())
                .patternLine("121")
                .patternLine("345")
                .patternLine("626")
                .key('1', Tags.Items.FENCES_WOODEN)
                .key('2', Items.COBBLESTONE_WALL)
                .key('3', ModItems.ARCANE_FLICKER_JAR.get())
                .key('4', ModItems.PURPLE_RUNE.get())
                .key('5', ModItems.AIR_FLICKER_JAR.get())
                .key('6', Items.IRON_BARS)
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(ModItems.RUNE.get()))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModItems.ITEM_TRANSPORT_FLICKER_FOCUS.get())
                .patternLine(" 1 ")
                .patternLine("232")
                .patternLine(" 1 ")
                .key('1', ModTags.Items.GEMS_TOPAZ)
                .key('2', ModTags.Items.GEMS_CHIMERITE)
                .key('3', ModItems.AIR_FLICKER_JAR.get())
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(ItemPredicate.Builder.create().tag(ModTags.Items.GEMS_TOPAZ).build()))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModItems.LIGHT_FLICKER_FOCUS.get())
                .patternLine("121")
                .patternLine("3 4")
                .patternLine("121")
                .key('1', Items.GLASS_BOTTLE)
                .key('2', ModTags.Items.GEMS_MOONSTONE)
                .key('3', ModItems.FIRE_FLICKER_JAR.get())
                .key('4', ModItems.LIGHTNING_FLICKER_JAR.get())
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(ItemPredicate.Builder.create().tag(ModTags.Items.GEMS_MOONSTONE).build()))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModItems.LUNAR_FLICKER_FOCUS.get())
                .patternLine("121")
                .patternLine("345")
                .patternLine("6 7")
                .key('1', ModItems.ORANGE_RUNE.get())
                .key('2', ModItems.LIGHTNING_FLICKER_JAR.get())
                .key('3', ModItems.ARCANE_FLICKER_JAR.get())
                .key('4', ModTags.Items.ORES_MOONSTONE)
                .key('5', ModItems.EARTH_FLICKER_JAR.get())
                .key('6', ModItems.AIR_ESSENCE.get())
                .key('7', ModItems.EARTH_ESSENCE.get())
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(ModItems.RUNE.get()))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModItems.NATURE_BOUNTY_FLICKER_FOCUS.get())
                .patternLine("121")
                .patternLine("345")
                .patternLine("161")
                .key('1', Items.BONE_MEAL)
                .key('2', ModItems.NATURE_FLICKER_JAR.get())
                .key('3', ModItems.LIFE_FLICKER_JAR.get())
                .key('4', ModItems.NATURE_ESSENCE.get())
                .key('5', ModItems.WATER_FLICKER_JAR.get())
                .key('6', ModItems.GREEN_RUNE.get())
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(ModItems.RUNE.get()))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModItems.PACKED_EARTH_FLICKER_FOCUS.get())
                .patternLine("111")
                .patternLine("232")
                .patternLine(" 4 ")
                .key('1', Items.DIRT)
                .key('2', ModItems.BLACK_RUNE.get())
                .key('3', ModItems.EARTH_FLICKER_JAR.get())
                .key('4', ModItems.EARTH_ESSENCE.get())
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(ModItems.RUNE.get()))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModItems.PROGENY_FLICKER_FOCUS.get())
                .patternLine("121")
                .patternLine("131")
                .patternLine("141")
                .key('1', Tags.Items.EGGS)
                .key('2', ModItems.LIFE_ESSENCE.get())
                .key('3', ModItems.LIFE_FLICKER_JAR.get())
                .key('4', ModItems.WHITE_RUNE.get())
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(ModItems.RUNE.get()))
                .build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(ModItems.MANA_CAKE.get(), 3)
                .addIngredient(ModItems.CERUBLOSSOM.get())
                .addIngredient(ModItems.AUM.get())
                .addIngredient(Items.SUGAR)
                .addIngredient(Items.WHEAT)
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(Items.WHEAT))
                .build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(ModItems.MANA_MARTINI.get())
                .addIngredient(Items.ICE)
                .addIngredient(Items.POTATO)
                .addIngredient(Items.SUGAR)
                .addIngredient(Tags.Items.RODS_WOODEN)
                .addIngredient(Ingredient.fromStacks(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), ModEffects.MANA_POTION.get())))
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(Items.POTATO))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModItems.IRON_INLAY.get(), 4)
                .patternLine("MMM")
                .patternLine("MVM")
                .patternLine("MMM")
                .key('M', Tags.Items.INGOTS_IRON)
                .key('V', ModItems.ARCANE_ASH.get())
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(ItemPredicate.Builder.create().tag(Tags.Items.INGOTS_IRON).build()))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModItems.GOLD_INLAY.get(), 4)
                .patternLine("MMM")
                .patternLine("MVM")
                .patternLine("MMM")
                .key('M', Tags.Items.INGOTS_GOLD)
                .key('V', ModItems.PURIFIED_VINTEUM.get())
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(ItemPredicate.Builder.create().tag(Tags.Items.INGOTS_GOLD).build()))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModItems.REDSTONE_INLAY.get(), 4)
                .patternLine("MMM")
                .patternLine("MVM")
                .patternLine("MMM")
                .key('M', Tags.Items.DUSTS_REDSTONE)
                .key('V', ModTags.Items.DUSTS_VINTEUM)
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(ItemPredicate.Builder.create().tag(Tags.Items.DUSTS_REDSTONE).build()))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModItems.VINTEUM_TORCH.get(), 4)
                .patternLine("V")
                .patternLine("S")
                .key('V', ModTags.Items.DUSTS_VINTEUM)
                .key('S', Tags.Items.RODS_WOODEN)
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(ItemPredicate.Builder.create().tag(ModTags.Items.DUSTS_VINTEUM).build()))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModItems.MAGE_HELMET.get())
                .patternLine("WLW")
                .patternLine("WRW")
                .key('W', Items.BROWN_WOOL)
                .key('L', Items.LEATHER)
                .key('R', ModItems.PURPLE_RUNE.get())
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(ItemPredicate.Builder.create().tag(ItemTags.WOOL).build()))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModItems.MAGE_CHESTPLATE.get())
                .patternLine("RCR")
                .patternLine("WLW")
                .patternLine("WWW")
                .key('R', ModItems.WHITE_RUNE.get())
                .key('C', ItemTags.COALS)
                .key('W', Items.BROWN_WOOL)
                .key('L', Items.LEATHER)
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(ItemPredicate.Builder.create().tag(ItemTags.WOOL).build()))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModItems.MAGE_LEGGINGS.get())
                .patternLine("WLW")
                .patternLine("WGW")
                .patternLine("R R")
                .key('R', ModItems.YELLOW_RUNE.get())
                .key('G', Tags.Items.GUNPOWDER)
                .key('W', Items.BROWN_WOOL)
                .key('L', Items.LEATHER)
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(ItemPredicate.Builder.create().tag(ItemTags.WOOL).build()))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModItems.MAGE_BOOTS.get())
                .patternLine("R R")
                .patternLine("L L")
                .patternLine("WFW")
                .key('R', ModItems.BLACK_RUNE.get())
                .key('F', Tags.Items.FEATHERS)
                .key('W', ItemTags.WOOL)
                .key('L', Items.LEATHER)
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(ItemPredicate.Builder.create().tag(ItemTags.WOOL).build()))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModItems.BATTLEMAGE_HELMET.get())
                .patternLine("OGO")
                .patternLine("ORO")
                .patternLine(" E ")
                .key('O', Tags.Items.OBSIDIAN)
                .key('G', ModItems.GOLD_INLAY.get())
                .key('R', ModItems.RED_RUNE.get())
                .key('E', ModItems.WATER_ESSENCE.get())
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(ItemPredicate.Builder.create().tag(Tags.Items.OBSIDIAN).build()))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModItems.BATTLEMAGE_CHESTPLATE.get())
                .patternLine("RER")
                .patternLine("OGO")
                .patternLine("OOO")
                .key('O', Tags.Items.OBSIDIAN)
                .key('G', ModItems.GOLD_INLAY.get())
                .key('R', ModItems.RED_RUNE.get())
                .key('E', ModItems.EARTH_ESSENCE.get())
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(ItemPredicate.Builder.create().tag(Tags.Items.OBSIDIAN).build()))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModItems.BATTLEMAGE_LEGGINGS.get())
                .patternLine("ORO")
                .patternLine("GEG")
                .patternLine("O O")
                .key('O', Tags.Items.OBSIDIAN)
                .key('G', ModItems.GOLD_INLAY.get())
                .key('R', ModItems.RED_RUNE.get())
                .key('E', ModItems.FIRE_ESSENCE.get())
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(ItemPredicate.Builder.create().tag(Tags.Items.OBSIDIAN).build()))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModItems.BATTLEMAGE_BOOTS.get())
                .patternLine("R R")
                .patternLine("OEO")
                .patternLine("OGO")
                .key('O', Tags.Items.OBSIDIAN)
                .key('G', ModItems.GOLD_INLAY.get())
                .key('R', ModItems.RED_RUNE.get())
                .key('E', ModItems.AIR_ESSENCE.get())
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(ItemPredicate.Builder.create().tag(Tags.Items.OBSIDIAN).build()))
                .build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(ModItems.INSCRIPTION_UPGRADE.get())
                .addIngredient(Items.BOOK)
                .addIngredient(Tags.Items.STRING)
                .addIngredient(Tags.Items.FEATHERS)
                .addIngredient(Items.INK_SAC)
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(Items.BOOK))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModItems.INSCRIPTION_TABLE.get())
                .patternLine("TXF")
                .patternLine("SSS")
                .patternLine("P P")
                .key('T', Items.TORCH)
                .key('X', ModItems.SPELL_PARCHMENT.get())
                .key('F', Tags.Items.FEATHERS)
                .key('S', ItemTags.WOODEN_SLABS)
                .key('P', ItemTags.PLANKS)
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(ItemPredicate.Builder.create().tag(ItemTags.PLANKS).build()))
                .build(consumer);
        ShapedRecipeBuilder
                .shapedRecipe(ModItems.ALTAR_CORE.get())
                .patternLine("V")
                .patternLine("S")
                .key('V', ModTags.Items.DUSTS_VINTEUM)
                .key('S', Tags.Items.STONE)
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(ItemPredicate.Builder.create().tag(ModTags.Items.DUSTS_VINTEUM).build()))
                .build(consumer);
        ShapedRecipeBuilder
                .shapedRecipe(ModItems.MAGIC_WALL.get(), 16)
                .patternLine("VSV")
                .key('V', ModTags.Items.DUSTS_VINTEUM)
                .key('S', Tags.Items.STONE)
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(ItemPredicate.Builder.create().tag(ModTags.Items.DUSTS_VINTEUM).build()))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModItems.OCCULUS.get())
                .patternLine("SGS")
                .patternLine(" S ")
                .patternLine("CTC")
                .key('S', Items.STONE_BRICKS)
                .key('G', Tags.Items.GLASS)
                .key('C', ItemTags.COALS)
                .key('T', ModTags.Items.GEMS_TOPAZ)
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(ItemPredicate.Builder.create().tag(ModTags.Items.GEMS_TOPAZ).build()))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModItems.MANA_BATTERY.get())
                .patternLine("CVC")
                .patternLine("VAV")
                .patternLine("CVC")
                .key('C', ModTags.Items.GEMS_CHIMERITE)
                .key('V', ModTags.Items.DUSTS_VINTEUM)
                .key('A', ModItems.ARCANE_ASH.get())
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(ItemPredicate.Builder.create().tag(ModTags.Items.GEMS_CHIMERITE).build()))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModItems.ESSENCE_REFINER.get())
                .patternLine("PDP")
                .patternLine("OAO")
                .patternLine("PPP")
                .key('P', ItemTags.PLANKS)
                .key('D', Tags.Items.GEMS_DIAMOND)
                .key('O', Tags.Items.OBSIDIAN)
                .key('A', ModItems.ARCANE_ASH.get())
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(ItemPredicate.Builder.create().tag(Tags.Items.OBSIDIAN).build()))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModItems.ARMOR_IMBUEMENT_TABLE.get())
                .patternLine("ACA")
                .patternLine("OEO")
                .patternLine("OOO")
                .key('C', ItemTags.CARPETS)
                .key('E', Items.ENCHANTING_TABLE)
                .key('O', Tags.Items.OBSIDIAN)
                .key('A', ModItems.ALTAR_CORE.get())
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(ItemPredicate.Builder.create().tag(Tags.Items.OBSIDIAN).build()))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModItems.SLIPSTREAM_GENERATOR.get())
                .patternLine("WWW")
                .patternLine("FAF")
                .patternLine("WWW")
                .key('W', ModTags.Items.LOGS_WITCHWOOD)
                .key('F', Tags.Items.FEATHERS)
                .key('A', ModItems.AIR_ESSENCE.get())
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(ModItems.AIR_ESSENCE.get()))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModItems.DRAINING_WELL.get())
                .patternLine("MPM")
                .patternLine("VAV")
                .patternLine("MTM")
                .key('M', ModItems.MAGIC_WALL.get())
                .key('P', ModItems.PURE_ESSENCE_CORE.get())
                .key('V', ModTags.Items.DUSTS_VINTEUM)
                .key('A', ModItems.ARCANE_ESSENCE.get())
                .key('T', ModTags.Items.GEMS_TOPAZ)
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(ModItems.ARCANE_ESSENCE.get()))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModItems.FLICKER_LURE.get())
                .patternLine("CIV")
                .patternLine("SSS")
                .key('C', ModTags.Items.GEMS_CHIMERITE)
                .key('I', Tags.Items.INGOTS_IRON)
                .key('V', ModTags.Items.DUSTS_VINTEUM)
                .key('S', Tags.Items.STONE)
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(ItemPredicate.Builder.create().tag(Tags.Items.STONE).build()))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModItems.EVERSTONE.get())
                .patternLine(" T ")
                .patternLine("CSC")
                .patternLine(" T ")
                .key('C', ModTags.Items.GEMS_CHIMERITE)
                .key('T', ModTags.Items.GEMS_TOPAZ)
                .key('S', Tags.Items.STONE)
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(ItemPredicate.Builder.create().tag(Tags.Items.STONE).build()))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModItems.KEYSTONE_DOOR.get())
                .patternLine("PMP")
                .patternLine("RRR")
                .patternLine("PMP")
                .key('P', ModItems.WITCHWOOD_PLANKS.get())
                .key('M', ModItems.MAGIC_WALL.get())
                .key('R', ModItems.RUNE.get())
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(ModItems.WITCHWOOD_PLANKS.get()))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModItems.SPELL_SEALED_DOOR.get())
                .patternLine(" G ")
                .patternLine("SDS")
                .patternLine(" L ")
                .key('G', ModItems.GREATER_FOCUS.get())
                .key('S', ModItems.STANDARD_FOCUS.get())
                .key('D', ModItems.KEYSTONE_DOOR.get())
                .key('L', ModItems.LESSER_FOCUS.get())
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(ModItems.WITCHWOOD_PLANKS.get()))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModItems.KEYSTONE.get())
                .patternLine("GIG")
                .patternLine("IVI")
                .patternLine("GIG")
                .key('G', Tags.Items.INGOTS_GOLD)
                .key('I', Tags.Items.INGOTS_IRON)
                .key('V', ModTags.Items.DUSTS_VINTEUM)
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(ItemPredicate.Builder.create().tag(ModTags.Items.DUSTS_VINTEUM).build()))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModItems.SPELL_PARCHMENT.get())
                .patternLine("S")
                .patternLine("P")
                .patternLine("S")
                .key('S', Tags.Items.RODS_WOODEN)
                .key('P', Items.PAPER)
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(ItemPredicate.Builder.create().tag(Tags.Items.RODS_WOODEN).build()))
                .build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(ModItems.PURIFIED_VINTEUM.get())
                .addIngredient(ModItems.CERUBLOSSOM.get())
                .addIngredient(ModItems.DESERT_NOVA.get())
                .addIngredient(ModTags.Items.DUSTS_VINTEUM)
                .addIngredient(ModItems.ARCANE_ASH.get())
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(ItemPredicate.Builder.create().tag(ModTags.Items.DUSTS_VINTEUM).build()))
                .build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(ModItems.ARCANE_COMPOUND.get())
                .addIngredient(Items.STONE)
                .addIngredient(Items.NETHERRACK)
                .addIngredient(Tags.Items.DUSTS_REDSTONE)
                .addIngredient(Tags.Items.DUSTS_GLOWSTONE)
                .addIngredient(Items.STONE)
                .addIngredient(Items.NETHERRACK)
                .addIngredient(Tags.Items.DUSTS_REDSTONE)
                .addIngredient(Tags.Items.DUSTS_GLOWSTONE)
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(Items.NETHERRACK))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModItems.WOODEN_LEG.get())
                .patternLine("P")
                .patternLine("W")
                .patternLine("S")
                .key('P', ItemTags.PLANKS)
                .key('W', ItemTags.WOODEN_SLABS)
                .key('S', Tags.Items.RODS_WOODEN)
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(ItemPredicate.Builder.create().tag(Tags.Items.RODS_WOODEN).build()))
                .build(consumer);
        RecipeHelper.addBlastingRecipe(ModItems.ARCANE_COMPOUND.get(), ModItems.ARCANE_ASH.get(), 0.2f);
        RecipeHelper.addSmeltingRecipe(ModItems.ARCANE_COMPOUND.get(), ModItems.ARCANE_ASH.get(), 0.2f);
        ShapelessRecipeBuilder.shapelessRecipe(ModItems.EVIL_BOOK.get())
                .addIngredient(Ingredient.fromStacks(ArsMagicaLegacy.getCompendium()))
                .addIngredient(ModItems.WOODEN_LEG.get())
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(ModItems.WOODEN_LEG.get()))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModItems.SPELL_BOOK.get())
                .patternLine("SLL")
                .patternLine("SPP")
                .patternLine("SLL")
                .key('S', Tags.Items.STRING)
                .key('L', Tags.Items.LEATHER)
                .key('P', Items.PAPER)
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(ItemPredicate.Builder.create().tag(Tags.Items.STRING).build()))
                .build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(ModItems.DEFICIT_CRYSTAL.get())
                .addIngredient(Tags.Items.GEMS_DIAMOND)
                .addIngredient(Items.ENDER_EYE)
                .addIngredient(ModItems.ARCANE_ASH.get())
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(ItemPredicate.Builder.create().tag(Tags.Items.GEMS_DIAMOND).build()))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModItems.RUNE_BAG.get())
                .patternLine("LLL")
                .patternLine("W W")
                .patternLine("LLL")
                .key('L', Items.LEATHER)
                .key('W', ItemTags.WOOL)
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(Items.LEATHER))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModItems.ESSENCE_BAG.get())
                .patternLine("LLL")
                .patternLine("WGW")
                .patternLine("LLL")
                .key('L', Items.LEATHER)
                .key('W', ItemTags.WOOL)
                .key('G', Tags.Items.NUGGETS_GOLD)
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(Items.LEATHER))
                .build(consumer);
        /*ShapelessRecipeBuilder.shapelessRecipe(ModItems.DEFICIT_CRYSTAL.get())
                .addIngredient(Items.WRITABLE_BOOK)
                .addIngredient(ModItems.ARCANE_ESSENCE.get())
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(ModItems.ARCANE_ESSENCE.get()))
                .build(consumer);*/
        ShapedRecipeBuilder.shapedRecipe(ModItems.MAGITECH_GOGGLES.get())
                .patternLine("LLL")
                .patternLine("CGC")
                .patternLine("TLT")
                .key('L', Items.LEATHER)
                .key('C', ModTags.Items.GEMS_CHIMERITE)
                .key('G', Tags.Items.NUGGETS_GOLD)
                .key('T', ModTags.Items.GEMS_TOPAZ)
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(ItemPredicate.Builder.create().tag(ModTags.Items.GEMS_TOPAZ).build()))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModItems.MAGITECH_STAFF.get())
                .patternLine(" GT")
                .patternLine("G G")
                .patternLine("GGG")
                .key('G', Tags.Items.NUGGETS_GOLD)
                .key('T', ModTags.Items.GEMS_TOPAZ)
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(ItemPredicate.Builder.create().tag(ModTags.Items.GEMS_TOPAZ).build()))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModItems.CRYSTAL_WRENCH.get())
                .patternLine("I I")
                .patternLine("CVD")
                .patternLine(" I ")
                .key('C', ModItems.CERUBLOSSOM.get())
                .key('D', ModItems.DESERT_NOVA.get())
                .key('I', Tags.Items.INGOTS_IRON)
                .key('V', ModTags.Items.DUSTS_VINTEUM)
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(ItemPredicate.Builder.create().tag(ModTags.Items.DUSTS_VINTEUM).build()))
                .build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(ModItems.WIZARD_CHALK.get())
                .addIngredient(ModTags.Items.DUSTS_VINTEUM)
                .addIngredient(Items.BONE_MEAL)
                .addIngredient(Items.CLAY_BALL)
                .addIngredient(Items.FLINT)
                .addIngredient(Items.PAPER)
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(ItemPredicate.Builder.create().tag(ModTags.Items.DUSTS_VINTEUM).build()))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModItems.CRYSTAL_PHYLACTERY.get())
                .patternLine(" M ")
                .patternLine("GVG")
                .patternLine(" W ")
                .key('M', ModTags.Items.GEMS_MOONSTONE)
                .key('G', Tags.Items.GLASS)
                .key('V', ModItems.PURIFIED_VINTEUM.get())
                .key('W', ModItems.MAGIC_WALL.get())
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(ItemPredicate.Builder.create().tag(ModTags.Items.GEMS_MOONSTONE).build()))
                .build(consumer);
        BrewingRecipeRegistry.addRecipe(Ingredient.fromStacks(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), Potions.AWKWARD)), Ingredient.fromTag(ModTags.Items.GEMS_CHIMERITE), PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), ModEffects.LESSER_MANA_POTION.get()));
        BrewingRecipeRegistry.addRecipe(Ingredient.fromStacks(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), Potions.AWKWARD)), Ingredient.fromTag(ModTags.Items.GEMS_TOPAZ), PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), ModEffects.MANA_POTION.get()));
        BrewingRecipeRegistry.addRecipe(Ingredient.fromStacks(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), Potions.AWKWARD)), Ingredient.fromTag(ModTags.Items.DUSTS_VINTEUM), PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), ModEffects.GREATER_MANA_POTION.get()));
        BrewingRecipeRegistry.addRecipe(Ingredient.fromStacks(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), Potions.AWKWARD)), Ingredient.fromItems(ModItems.ARCANE_ASH.get()), PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), ModEffects.EPIC_MANA_POTION.get()));
        BrewingRecipeRegistry.addRecipe(Ingredient.fromStacks(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), Potions.AWKWARD)), Ingredient.fromItems(ModItems.PURIFIED_VINTEUM.get()), PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), ModEffects.LEGENDARY_MANA_POTION.get()));
    }
}
