package minecraftschurli.arsmagicalegacy.data;

import minecraftschurli.arsmagicalegacy.init.*;
import minecraftschurli.arsmagicalegacy.util.*;
import net.minecraft.data.*;
import net.minecraft.item.*;
import net.minecraft.tags.*;
import net.minecraftforge.common.*;

import java.util.function.*;

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
        RecipeUtils.addLargeCompressRecipe(consumer, ModItems.CHIMERITE_BLOCK.get(), ModTags.Items.GEMS_CHIMERITE);
        RecipeUtils.addLargeCompressRecipe(consumer, ModItems.TOPAZ_BLOCK.get(), ModTags.Items.GEMS_TOPAZ);
        RecipeUtils.addLargeCompressRecipe(consumer, ModItems.VINTEUM_BLOCK.get(), ModTags.Items.DUSTS_VINTEUM);
        RecipeUtils.addLargeCompressRecipe(consumer, ModItems.MOONSTONE_BLOCK.get(), ModTags.Items.GEMS_MOONSTONE);
        RecipeUtils.addLargeCompressRecipe(consumer, ModItems.SUNSTONE_BLOCK.get(), ModTags.Items.GEMS_SUNSTONE);
        RecipeUtils.addLargeDecompressRecipe(consumer, ModItems.CHIMERITE.get(), ModTags.Items.STORAGE_BLOCKS_CHIMERITE);
        RecipeUtils.addLargeDecompressRecipe(consumer, ModItems.MOONSTONE.get(), ModTags.Items.STORAGE_BLOCKS_MOONSTONE);
        RecipeUtils.addLargeDecompressRecipe(consumer, ModItems.SUNSTONE.get(), ModTags.Items.STORAGE_BLOCKS_SUNSTONE);
        RecipeUtils.addLargeDecompressRecipe(consumer, ModItems.TOPAZ.get(), ModTags.Items.STORAGE_BLOCKS_TOPAZ);
        RecipeUtils.addLargeDecompressRecipe(consumer, ModItems.VINTEUM.get(), ModTags.Items.STORAGE_BLOCKS_VINTEUM);
        RecipeUtils.addSlabRecipe(consumer, ModItems.WITCHWOOD_SLAB.get(), ModItems.WITCHWOOD_PLANKS.get());
        RecipeUtils.addStairRecipe(consumer, ModItems.WITCHWOOD_STAIRS.get(), ModItems.WITCHWOOD_PLANKS.get());
        RecipeUtils.addDoorRecipe(consumer, ModItems.WITCHWOOD_DOOR.get(), ModItems.WITCHWOOD_PLANKS.get());
        RecipeUtils.addTrapdoorRecipe(consumer, ModItems.WITCHWOOD_TRAPDOOR.get(), ModItems.WITCHWOOD_PLANKS.get());
        RecipeUtils.addFenceRecipe(consumer, ModItems.WITCHWOOD_FENCE.get(), ModItems.WITCHWOOD_PLANKS.get());
        RecipeUtils.addFenceGateRecipe(consumer, ModItems.WITCHWOOD_FENCE_GATE.get(), ModItems.WITCHWOOD_PLANKS.get());
        RecipeUtils.addPressurePlateRecipe(consumer, ModItems.WITCHWOOD_PRESSURE_PLATE.get(), ModItems.WITCHWOOD_PLANKS.get());
        RecipeUtils.addButtonRecipe(consumer, ModItems.WITCHWOOD_BUTTON.get(), ModItems.WITCHWOOD_PLANKS.get());
        RecipeUtils.addSmallDecompressRecipe(consumer, ModItems.WITCHWOOD_PLANKS.get(), ModTags.Items.LOGS_WITCHWOOD);
        RecipeUtils.addBlastingRecipe(consumer, ModItems.ARCANE_COMPOUND.get(), ModItems.ARCANE_ASH.get(), 0.2f);
        RecipeUtils.addSmeltingRecipe(consumer, ModItems.ARCANE_COMPOUND.get(), ModItems.ARCANE_ASH.get(), 0.2f);
        RecipeUtils.ShapedRecipeHelper
                .make(consumer, ModItems.ALTAR_CORE.get())
                .line("V")
                .line("S")
                .key('V', ModTags.Items.DUSTS_VINTEUM)
                .key('S', Tags.Items.STONE)
                .build();
        RecipeUtils.ShapedRecipeHelper
                .make(consumer, ModItems.MAGIC_WALL.get(), 16)
                .line("VSV")
                .key('V', ModTags.Items.DUSTS_VINTEUM)
                .key('S', Tags.Items.STONE)
                .build();
        RecipeUtils.ShapedRecipeHelper
                .make(consumer, ModItems.RUNE.get(), 2)
                .line(" X ")
                .line("XXX")
                .line("XX ")
                .key('X', Tags.Items.COBBLESTONE)
                .build();
        RecipeUtils.ShapelessRecipeHelper
                .make(consumer, ModItems.WHITE_RUNE.get())
                .add(Tags.Items.DYES_WHITE)
                .add(ModItems.RUNE.get())
                .build();
        RecipeUtils.ShapelessRecipeHelper
                .make(consumer, ModItems.ORANGE_RUNE.get())
                .add(Tags.Items.DYES_ORANGE)
                .add(ModItems.RUNE.get())
                .build();
        RecipeUtils.ShapelessRecipeHelper
                .make(consumer, ModItems.MAGENTA_RUNE.get())
                .add(Tags.Items.DYES_MAGENTA)
                .add(ModItems.RUNE.get())
                .build();
        RecipeUtils.ShapelessRecipeHelper
                .make(consumer, ModItems.LIGHT_BLUE_RUNE.get())
                .add(Tags.Items.DYES_LIGHT_BLUE)
                .add(ModItems.RUNE.get())
                .build();
        RecipeUtils.ShapelessRecipeHelper
                .make(consumer, ModItems.YELLOW_RUNE.get())
                .add(Tags.Items.DYES_YELLOW)
                .add(ModItems.RUNE.get())
                .build();
        RecipeUtils.ShapelessRecipeHelper
                .make(consumer, ModItems.LIME_RUNE.get())
                .add(Tags.Items.DYES_LIME)
                .add(ModItems.RUNE.get())
                .build();
        RecipeUtils.ShapelessRecipeHelper
                .make(consumer, ModItems.PINK_RUNE.get())
                .add(Tags.Items.DYES_PINK)
                .add(ModItems.RUNE.get())
                .build();
        RecipeUtils.ShapelessRecipeHelper
                .make(consumer, ModItems.GRAY_RUNE.get())
                .add(Tags.Items.DYES_GRAY)
                .add(ModItems.RUNE.get())
                .build();
        RecipeUtils.ShapelessRecipeHelper
                .make(consumer, ModItems.LIGHT_GRAY_RUNE.get())
                .add(Tags.Items.DYES_LIGHT_GRAY)
                .add(ModItems.RUNE.get())
                .build();
        RecipeUtils.ShapelessRecipeHelper
                .make(consumer, ModItems.CYAN_RUNE.get())
                .add(Tags.Items.DYES_CYAN)
                .add(ModItems.RUNE.get())
                .build();
        RecipeUtils.ShapelessRecipeHelper
                .make(consumer, ModItems.PURPLE_RUNE.get())
                .add(Tags.Items.DYES_PURPLE)
                .add(ModItems.RUNE.get())
                .build();
        RecipeUtils.ShapelessRecipeHelper
                .make(consumer, ModItems.BLUE_RUNE.get())
                .add(Tags.Items.DYES_BLUE)
                .add(ModItems.RUNE.get())
                .build();
        RecipeUtils.ShapelessRecipeHelper
                .make(consumer, ModItems.BROWN_RUNE.get())
                .add(Tags.Items.DYES_BROWN)
                .add(ModItems.RUNE.get())
                .build();
        RecipeUtils.ShapelessRecipeHelper
                .make(consumer, ModItems.GREEN_RUNE.get())
                .add(Tags.Items.DYES_GREEN)
                .add(ModItems.RUNE.get())
                .build();
        RecipeUtils.ShapelessRecipeHelper
                .make(consumer, ModItems.RED_RUNE.get())
                .add(Tags.Items.DYES_RED)
                .add(ModItems.RUNE.get())
                .build();
        RecipeUtils.ShapelessRecipeHelper
                .make(consumer, ModItems.BLACK_RUNE.get())
                .add(Tags.Items.DYES_BLACK)
                .add(ModItems.RUNE.get())
                .build();
        RecipeUtils.ShapedRecipeHelper.make(consumer, ModItems.SPELL_BOOK.get())
                .line("SLL")
                .line("SPP")
                .line("SLL")
                .key('S', Tags.Items.STRING)
                .key('L', Tags.Items.LEATHER)
                .key('P', Items.PAPER)
                .build();
        RecipeUtils.ShapedRecipeHelper.make(consumer, ModItems.VINTEUM_TORCH.get(), 4)
                .line("V")
                .line("S")
                .key('V', ModTags.Items.DUSTS_VINTEUM)
                .key('S', Items.STICK)
                .build();
        RecipeUtils.ShapedRecipeHelper.make(consumer, ModItems.REDSTONE_INLAY.get(), 4)
                .line("MMM")
                .line("MVM")
                .line("MMM")
                .key('M', Tags.Items.DUSTS_REDSTONE)
                .key('V', ModTags.Items.DUSTS_VINTEUM)
                .build();
        RecipeUtils.ShapedRecipeHelper.make(consumer, ModItems.IRON_INLAY.get(), 4)
                .line("MMM")
                .line("MVM")
                .line("MMM")
                .key('M', Tags.Items.INGOTS_IRON)
                .key('V', ModItems.ARCANE_ASH.get())
                .build();
        RecipeUtils.ShapedRecipeHelper.make(consumer, ModItems.GOLD_INLAY.get(), 4)
                .line("MMM")
                .line("MVM")
                .line("MMM")
                .key('M', Tags.Items.INGOTS_GOLD)
                .key('V', ModItems.PURIFIED_VINTEUM.get())
                .build();
        RecipeUtils.ShapedRecipeHelper.make(consumer, ModItems.SPELL_PARCHMENT.get())
                .line("S")
                .line("P")
                .line("S")
                .key('S', Items.STICK)
                .key('P', Items.PAPER)
                .build();
        RecipeUtils.ShapelessRecipeHelper.make(consumer, ModItems.PURIFIED_VINTEUM.get())
                .add(ModItems.CERUBLOSSOM.get())
                .add(ModItems.DESERT_NOVA.get())
                .add(ModItems.VINTEUM.get())
                .add(ModItems.ARCANE_ASH.get())
                .build();
        RecipeUtils.ShapelessRecipeHelper.make(consumer, ModItems.ARCANE_COMPOUND.get())
                .add(Items.STONE)
                .add(Items.NETHERRACK)
                .add(Tags.Items.DUSTS_REDSTONE)
                .add(Tags.Items.DUSTS_GLOWSTONE)
                .add(Items.STONE)
                .add(Items.NETHERRACK)
                .add(Tags.Items.DUSTS_REDSTONE)
                .add(Tags.Items.DUSTS_GLOWSTONE)
                .build();
        RecipeUtils.ShapedRecipeHelper.make(consumer, ModItems.WOODEN_LEG.get())
                .line("P")
                .line("W")
                .line("S")
                .key('P', ItemTags.PLANKS)
                .key('W', ItemTags.WOODEN_SLABS)
                .key('S', Items.STICK)
                .build();
        RecipeUtils.ShapedRecipeHelper.make(consumer, ModItems.LESSER_FOCUS.get())
                .line(" N ")
                .line("NGN")
                .line(" N ")
                .key('N', Tags.Items.NUGGETS_GOLD)
                .key('G', Tags.Items.GLASS)
                .build();
        RecipeUtils.ShapedRecipeHelper.make(consumer, ModItems.STANDARD_FOCUS.get())
                .line(" R ")
                .line("RFR")
                .line(" R ")
                .key('R', Tags.Items.DUSTS_REDSTONE)
                .key('F', ModItems.LESSER_FOCUS.get())
                .build();
        RecipeUtils.ShapedRecipeHelper.make(consumer, ModItems.GREATER_FOCUS.get())
                .line("A A")
                .line("VFV")
                .line("A A")
                .key('F', ModItems.STANDARD_FOCUS.get())
                .key('V', ModItems.PURIFIED_VINTEUM.get())
                .key('A', ModItems.ARCANE_ASH.get())
                .build();
        RecipeUtils.ShapedRecipeHelper.make(consumer, ModItems.MANA_FOCUS.get())
                .line("V")
                .line("F")
                .line("V")
                .key('V', ModTags.Items.DUSTS_VINTEUM)
                .key('F', ModItems.STANDARD_FOCUS.get())
                .build();
        RecipeUtils.ShapedRecipeHelper.make(consumer, ModItems.PLAYER_FOCUS.get())
                .line("E")
                .line("F")
                .key('E', ModItems.LIFE_ESSENCE.get())
                .key('F', ModItems.STANDARD_FOCUS.get())
                .build();
        RecipeUtils.ShapedRecipeHelper.make(consumer, ModItems.LESSER_FOCUS.get())
                .line("P ")
                .line("SF")
                .line("W ")
                .key('P', Items.PORKCHOP)
                .key('S', ModItems.STANDARD_FOCUS.get())
                .key('F', Items.FEATHER)
                .key('W', ItemTags.WOOL)
                .build();
        RecipeUtils.ShapedRecipeHelper.make(consumer, ModItems.MANA_FOCUS.get())
                .line("I")
                .line("F")
                .line("I")
                .key('I', Items.IRON_SWORD)
                .key('F', ModItems.STANDARD_FOCUS.get())
                .build();
        RecipeUtils.ShapedRecipeHelper.make(consumer, ModItems.MANA_FOCUS.get())
                .line("GFG")
                .key('G', Tags.Items.GLASS)
                .key('F', ModItems.STANDARD_FOCUS.get())
                .build();
        RecipeUtils.ShapedRecipeHelper.make(consumer, ModItems.MANA_FOCUS.get())
                .line("C")
                .line("F")
                .line("W")
                .key('C', Items.COBBLESTONE)
                .key('F', ModItems.STANDARD_FOCUS.get())
                .key('W', Items.CRAFTING_TABLE)
                .build();
    }
}
