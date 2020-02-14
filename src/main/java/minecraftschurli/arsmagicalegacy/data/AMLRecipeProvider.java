package minecraftschurli.arsmagicalegacy.data;

import minecraftschurli.arsmagicalegacy.api.*;
import minecraftschurli.arsmagicalegacy.api.data.*;
import minecraftschurli.arsmagicalegacy.api.spell.*;
import minecraftschurli.arsmagicalegacy.init.*;
import net.minecraft.advancements.criterion.*;
import net.minecraft.data.*;
import net.minecraft.item.*;
import net.minecraft.item.crafting.*;
import net.minecraft.potion.*;
import net.minecraft.tags.*;
import net.minecraftforge.common.*;

import javax.annotation.*;
import java.util.function.*;

/**
 * @author Minecraftschurli
 * @version 2019-11-12
 */
public class AMLRecipeProvider extends RecipeProvider implements ArsMagicaRecipePlugin {
    public AMLRecipeProvider(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected void registerRecipes(@Nonnull Consumer<IFinishedRecipe> consumer) {
        for (AbstractSpellPart part : ArsMagicaAPI.getSpellPartRegistry()) {
            addSpellRecipe(part.getRegistryName(), part.getRecipe(), consumer);
        }
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
        ShapedRecipeBuilder
                .shapedRecipe(ModItems.WITCHWOOD_PRESSURE_PLATE.get())
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
        ShapedRecipeBuilder.shapedRecipe(ModItems.SPELL_BOOK.get())
                .patternLine("SLL")
                .patternLine("SPP")
                .patternLine("SLL")
                .key('S', Tags.Items.STRING)
                .key('L', Tags.Items.LEATHER)
                .key('P', Items.PAPER)
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(ItemPredicate.Builder.create().tag(Tags.Items.STRING).build()))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModItems.VINTEUM_TORCH.get(), 4)
                .patternLine("V")
                .patternLine("S")
                .key('V', ModTags.Items.DUSTS_VINTEUM)
                .key('S', Items.STICK)
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(ItemPredicate.Builder.create().tag(ModTags.Items.DUSTS_VINTEUM).build()))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModItems.REDSTONE_INLAY.get(), 4)
                .patternLine("MMM")
                .patternLine("MVM")
                .patternLine("MMM")
                .key('M', Tags.Items.DUSTS_REDSTONE)
                .key('V', ModTags.Items.DUSTS_VINTEUM)
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(ItemPredicate.Builder.create().tag(Tags.Items.DUSTS_REDSTONE).build()))
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
        ShapedRecipeBuilder.shapedRecipe(ModItems.SPELL_PARCHMENT.get())
                .patternLine("S")
                .patternLine("P")
                .patternLine("S")
                .key('S', Items.STICK)
                .key('P', Items.PAPER)
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(Items.STICK))
                .build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(ModItems.PURIFIED_VINTEUM.get())
                .addIngredient(ModItems.CERUBLOSSOM.get())
                .addIngredient(ModItems.DESERT_NOVA.get())
                .addIngredient(ModItems.VINTEUM.get())
                .addIngredient(ModItems.ARCANE_ASH.get())
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(ModItems.VINTEUM.get()))
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
                .key('S', Items.STICK)
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(Items.STICK))
                .build(consumer);
        RecipeHelper.addBlastingRecipe(ModItems.ARCANE_COMPOUND.get(), ModItems.ARCANE_ASH.get(), 0.2f);
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
                .addIngredient(Items.STICK)
                .addIngredient(Ingredient.fromStacks(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), ModEffects.MANA_POTION.get())))
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(Items.POTATO))
                .build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(ModItems.INSCRIPTION_UPGRADE.get())
                .addIngredient(Items.BOOK)
                .addIngredient(Tags.Items.STRING)
                .addIngredient(Tags.Items.FEATHERS)
                .addIngredient(Items.INK_SAC)
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(Items.BOOK))
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
        ShapedRecipeBuilder.shapedRecipe(ModItems.MANA_BATTERY.get())
                .patternLine("CVC")
                .patternLine("VAV")
                .patternLine("CVC")
                .key('C', ModTags.Items.GEMS_CHIMERITE)
                .key('V', ModTags.Items.DUSTS_VINTEUM)
                .key('A', ModItems.ARCANE_ASH.get())
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(ModItems.ARCANE_ASH.get()))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModItems.MAGE_HELMET.get())
                .patternLine("WLW")
                .patternLine("WRW")
                .key('W', Items.BROWN_WOOL)
                .key('L', Items.LEATHER)
                .key('R', ModItems.PURPLE_RUNE.get())
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(Items.LEATHER))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModItems.MAGE_CHESTPLATE.get())
                .patternLine("RCR")
                .patternLine("WLW")
                .patternLine("WWW")
                .key('R', ModItems.WHITE_RUNE.get())
                .key('C', ItemTags.COALS)
                .key('W', Items.BROWN_WOOL)
                .key('L', Items.LEATHER)
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(Items.LEATHER))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModItems.MAGE_LEGGINGS.get())
                .patternLine("WLW")
                .patternLine("WGW")
                .patternLine("R R")
                .key('R', ModItems.YELLOW_RUNE.get())
                .key('G', Tags.Items.GUNPOWDER)
                .key('W', Items.BROWN_WOOL)
                .key('L', Items.LEATHER)
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(Items.LEATHER))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModItems.MAGE_BOOTS.get())
                .patternLine("R R")
                .patternLine("L L")
                .patternLine("WFW")
                .key('R', ModItems.BLACK_RUNE.get())
                .key('F', Tags.Items.FEATHERS)
                .key('W', ItemTags.WOOL)
                .key('L', Items.LEATHER)
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(Items.LEATHER))
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
    }
}
