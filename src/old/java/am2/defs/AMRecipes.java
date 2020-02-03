package am2.defs;

import am2.*;
import am2.api.*;
import am2.api.affinity.*;
import am2.api.flickers.*;
import am2.blocks.BlockArsMagicaBlock.*;
import am2.blocks.BlockArsMagicaOre.*;
import am2.blocks.*;
import am2.items.*;
import am2.utils.*;
import net.minecraft.block.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.item.crafting.*;
import net.minecraft.potion.*;
import net.minecraftforge.common.*;
import net.minecraftforge.common.brewing.*;
import net.minecraftforge.fluids.*;
import net.minecraftforge.fml.common.registry.*;
import net.minecraftforge.oredict.*;

import java.util.*;

public class AMRecipes {
	public static void addRecipes() {
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack (BlockDefs.magicWall, 16), new Object[] {
			"VSV",
			'V', new ItemStack(ItemDefs.itemOre, 1, ItemOre.META_VINTEUM),
			'S', "stone"
		}));
		GameRegistry.addRecipe(new ShapedOreRecipe(BlockDefs.craftingAltar, new Object[] {
			"V",
			"S",
			'V', new ItemStack(ItemDefs.itemOre, 1, ItemOre.META_VINTEUM),
			'S', "stone"
		}));
		
		ObeliskFuelHelper.instance.registerFuelType(new ItemStack(ItemDefs.itemOre, 0, ItemOre.META_VINTEUM), 200);
		ObeliskFuelHelper.instance.registerFuelType(UniversalBucket.getFilledBucket(ForgeModContainer.getInstance().universalBucket, BlockDefs.liquid_essence), 2000);
		
		for (AbstractFlickerFunctionality func : ArsMagicaAPI.getFlickerFocusRegistry().getValues()) {
			if (func != null) {
				Object[] recipeItems = func.getRecipe();
				if (recipeItems != null) {
					GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemDefs.flickerFocus, 1, ArsMagicaAPI.getFlickerFocusRegistry().getId(func)), recipeItems));
				} else {
					LogHelper.info("Flicker operator %s was registered with no recipe.  It is un-craftable.  This may have been intentional.",func.getClass().getSimpleName());
				}
			}
		}

		List<IRecipe> recipes = CraftingManager.getInstance().getRecipeList();

		//essence refiner
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BlockDefs.essenceRefiner, 1),
				new Object[]
						{
								"PDP", "OAO", "PPP",
								Character.valueOf('P'), "plankWood",
								Character.valueOf('O'), Blocks.OBSIDIAN,
								Character.valueOf('A'), "arcaneAsh",
								Character.valueOf('D'), "gemDiamond"
						}));

		//essence conduit
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BlockDefs.essenceConduit, 1), new Object[]{
				" C ", " S ", "SSS",
				Character.valueOf('S'), "stone",
				Character.valueOf('C'), "gemChimerite"
		}));

		//summoner
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BlockDefs.summoner, 1), new Object[]{
				"GVG", "GOG", "OOO",
				Character.valueOf('G'), "ingotGold",
				Character.valueOf('O'), Blocks.OBSIDIAN,
				Character.valueOf('V'), "dustVinteum"
		}));

		//Calefactor
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BlockDefs.calefactor, 1), new Object[]{
				"L L",
				"SRS",
				"SVS",
				Character.valueOf('L'), new ItemStack(Items.DYE, 1, 4), //lapis
				Character.valueOf('S'), "stone",
				Character.valueOf('R'), "dustRedstone",
				Character.valueOf('V'), "dustVinteum"
		}));

		//keystone recepticle
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BlockDefs.keystoneRecepticle, 1), new Object[]{
				"SVS", "EPE", "SVS",
				Character.valueOf('P'), new ItemStack(ItemDefs.essence, 1, 9),
				Character.valueOf('S'), Blocks.STONEBRICK,
				Character.valueOf('E'), Items.ENDER_EYE,
				Character.valueOf('V'), "dustVinteum"
		}));

		//astral barrier
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BlockDefs.astralBarrier, 1), new Object[]{
				"WVW", "E E", "WVW",
				//Character.valueOf('P'), new ItemStack(ItemDefs.essence, 1, ItemDefs.essence.META_ENDER),
				Character.valueOf('W'), Blocks.COBBLESTONE_WALL,
				Character.valueOf('E'), Items.ENDER_EYE,
				Character.valueOf('V'), "dustVinteum"
		}));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BlockDefs.seerStone, 1), new Object[]{
				" E ", "SRS",
				Character.valueOf('S'), "stone", //stone wall
				Character.valueOf('E'), Items.ENDER_EYE,
				Character.valueOf('R'), "dustRedstone"
		}));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BlockDefs.keystoneChest), new Object[]{
				"WRW", "WVW", "WRW",
				Character.valueOf('W'), "plankWood",
				Character.valueOf('R'), new ItemStack(ItemDefs.rune, 1, 0),
				Character.valueOf('V'), "dustVinteum"
		}));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BlockDefs.lectern), new Object[]{
				"SSS", " P ",
				Character.valueOf('S'), "slabWood",
				Character.valueOf('P'), "plankWood"
		}));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BlockDefs.occulus), new Object[]{
				"SPS", " S ", "CVC",
				Character.valueOf('S'), Blocks.STONEBRICK,
				Character.valueOf('C'), Items.COAL,
				Character.valueOf('P'), "blockGlassColorless",
				Character.valueOf('V'), "gemBlueTopaz"
		}));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BlockDefs.manaDrain), new Object[]{
				"WPW", "VAV", "WBW",
				Character.valueOf('B'), "gemBlueTopaz",
				Character.valueOf('V'), "dustVinteum",
				Character.valueOf('A'), new ItemStack(ItemDefs.essence, 1, ArsMagicaAPI.getAffinityRegistry().getId(Affinity.ARCANE)),
				Character.valueOf('W'), BlockDefs.magicWall,
				Character.valueOf('P'), new ItemStack(ItemDefs.core, 1, ItemCore.META_PURE)
				
		}));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BlockDefs.arcaneReconstructor), new Object[]{
				"SWS", "VDV", "SOS",
				Character.valueOf('S'), "stone",
				Character.valueOf('V'), "dustVinteum",
				Character.valueOf('D'), "gemDiamond",
				Character.valueOf('W'), BlockDefs.magicWall,
				Character.valueOf('O'), Blocks.OBSIDIAN
		}));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BlockDefs.arcaneDeconstructor), new Object[]{
				"IGR",
				"WDW",
				"WWW",
				Character.valueOf('I'), ItemDefs.itemFocus,
				Character.valueOf('G'), "blockGlassColorless",
				Character.valueOf('R'), new ItemStack(ItemDefs.core, 1, ItemCore.META_PURE),
				Character.valueOf('W'), BlockDefs.witchwoodPlanks,
				Character.valueOf('D'), ItemDefs.deficitCrystal
		}));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BlockDefs.flickerLure), new Object[]{
				"CIV",
				"SSS",
				Character.valueOf('C'), "gemChimerite",
				Character.valueOf('I'), "ingotIron",
				Character.valueOf('V'), "dustVinteum",
				Character.valueOf('S'), Blocks.STONEBRICK
		}));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BlockDefs.manaBattery), new Object[]{
				"IVI",
				"VAV",
				"IVI",
				Character.valueOf('I'), "gemChimerite",
				Character.valueOf('V'), "dustVinteum",
				Character.valueOf('A'), "arcaneAsh"
		}));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BlockDefs.magicWall, 16, 0), new Object[]{
				"VSV",
				Character.valueOf('V'), "dustVinteum",
				Character.valueOf('S'), "stone"
		}));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BlockDefs.craftingAltar), new Object[]{
				"V",
				"S",
				Character.valueOf('V'), "dustVinteum",
				Character.valueOf('S'), "stone"
		}));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BlockDefs.vinteumTorch, 4), new Object[]{
				"V",
				"S",
				Character.valueOf('V'), "dustVinteum",
				Character.valueOf('S'), "stickWood"
		}));

		GameRegistry.addRecipe(new ShapedOreRecipe(BlockDefs.inscriptionTable, new Object[]{
				"TPF",
				"SSS",
				"W W",
				Character.valueOf('T'), Blocks.TORCH,
				Character.valueOf('P'), ItemDefs.spellParchment,
				Character.valueOf('F'), Items.FEATHER,
				Character.valueOf('S'), "slabWood",
				Character.valueOf('W'), "plankWood"
		}));

		//GameRegistry.addRecipe(new ItemStack(Blocks.STONEBRICK, 1, 3), new Object[]{
		//		"SS",
		//		"SS",
		//		Character.valueOf('S'), Blocks.STONEBRICK
		//});

		//Inlays

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BlockDefs.redstoneInlay, 4, 0), new Object[]{
				"RRR",
				"RVR",
				"RRR",
				Character.valueOf('R'), "dustRedstone",
				Character.valueOf('V'), "dustVinteum"
		}));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BlockDefs.ironInlay, 4, 0), new Object[]{
				"III",
				"IVI",
				"III",
				Character.valueOf('I'), "ingotIron",
				Character.valueOf('V'), "arcaneAsh"
		}));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BlockDefs.goldInlay, 4, 0), new Object[]{
				"GGG",
				"GVG",
				"GGG",
				Character.valueOf('G'), "ingotGold",
				Character.valueOf('V'), new ItemStack(ItemDefs.itemOre, 1, ItemOre.META_PURIFIED_VINTEUM)
		}));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BlockDefs.particleEmitter), new Object[]{
				" C ",
				"CIC",
				" C ",
				Character.valueOf('I'), BlockDefs.illusionBlock,
				Character.valueOf('C'), "gemChimerite"
		}));

		GameRegistry.addRecipe(new ItemStack(BlockDefs.witchwoodPlanks, 4), new Object[]{
				"W",
				Character.valueOf('W'), BlockDefs.witchwoodLog
		});

		RecipeUtils.addShapedRecipeFirst(recipes, new ItemStack(BlockDefs.witchwoodSingleSlab, 6), new Object[]{
				"WWW",
				Character.valueOf('W'), BlockDefs.witchwoodPlanks
		});

		RecipeUtils.addShapedRecipeFirst(recipes, new ItemStack(BlockDefs.witchwoodStairs, 4), new Object[]{
				"  W",
				" WW",
				"WWW",
				Character.valueOf('W'), BlockDefs.witchwoodPlanks
		});

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BlockDefs.everstone), new Object[]{
				" B ",
				"CSC",
				" B ",
				Character.valueOf('C'), "gemChimerite",
				Character.valueOf('S'), "stone",
				Character.valueOf('B'), "gemBlueTopaz"
		}));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BlockDefs.magiciansWorkbench), new Object[]{
				"COC",
				"SWS",
				"LHL",
				Character.valueOf('C'), "craftingTableWood",
				Character.valueOf('O'), new ItemStack(Blocks.CARPET),
				Character.valueOf('W'), "logWood",
				Character.valueOf('S'), "slabWood",
				Character.valueOf('L'), "plankWood",
				Character.valueOf('H'), "chestWood"
		}));

		GameRegistry.addRecipe(new ItemStack(BlockDefs.slipstreamGenerator), new Object[]{
				"WWW",
				"FAF",
				"WWW",
				Character.valueOf('W'), BlockDefs.witchwoodLog,
				Character.valueOf('F'), Items.FEATHER,
				Character.valueOf('A'), new ItemStack(ItemDefs.essence, 1, ArsMagicaAPI.getAffinityRegistry().getId(Affinity.AIR))
		});

		//Flicker Habitat
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BlockDefs.elementalAttuner), new Object[]{
				"IDI",
				"DBD",
				"IDI",
				Character.valueOf('I'), "ingotIron",
				Character.valueOf('D'), "dustVinteum",
				Character.valueOf('B'), new ItemStack(BlockDefs.blocks, 1, EnumBlockType.CHIMERITE.ordinal())
		}));

		//Import Gem
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BlockDefs.crystalMarker, 1, BlockCrystalMarker.META_IN), new Object[]{
				" G ",
				"GDG",
				" G ",
				Character.valueOf('G'), "gemBlueTopaz",
				Character.valueOf('D'), "dyeYellow" //Yellow Dye
		}));


		//Export Gem
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BlockDefs.crystalMarker, 1, BlockCrystalMarker.META_OUT), new Object[]{
				" G ",
				"GDG",
				" G ",
				Character.valueOf('G'), "gemBlueTopaz",
				Character.valueOf('D'), "dyeBlue" //Lapis
		}));

		//Final Export Gem
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BlockDefs.crystalMarker, 1, BlockCrystalMarker.META_FINAL_DEST), new Object[]{
				" G ",
				"GDG",
				" G ",
				Character.valueOf('G'), "gemBlueTopaz",
				Character.valueOf('D'), "dyeGray" //Lapis
		}));

		//Like Export Gem
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BlockDefs.crystalMarker, 1, BlockCrystalMarker.META_LIKE_EXPORT), new Object[]{
				"GDG",
				"DED",
				"GDG",
				Character.valueOf('G'), "gemBlueTopaz",
				Character.valueOf('D'), "dyeGreen", //Cactus Green
				Character.valueOf('E'), new ItemStack(BlockDefs.crystalMarker, 1, BlockCrystalMarker.META_OUT)
		}));

		//Regulate Export Gem
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BlockDefs.crystalMarker, 1, BlockCrystalMarker.META_REGULATE_EXPORT), new Object[]{
				"GDG",
				"DED",
				"GDG",
				Character.valueOf('G'), "gemBlueTopaz",
				Character.valueOf('D'), "dyePurple", //Purple Dye
				Character.valueOf('E'), new ItemStack(BlockDefs.crystalMarker, 1, BlockCrystalMarker.META_OUT)
		}));

		//Set Export Gem
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BlockDefs.crystalMarker, 1, BlockCrystalMarker.META_SET_EXPORT), new Object[]{
				"GDG",
				"DED",
				"GDG",
				Character.valueOf('G'), "gemBlueTopaz",
				Character.valueOf('D'), "dyeLightBlue", //Light Blue Dye
				Character.valueOf('E'), new ItemStack(BlockDefs.crystalMarker, 1, BlockCrystalMarker.META_OUT)
		}));

		//Regulate Bidirectional
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BlockDefs.crystalMarker, 1, BlockCrystalMarker.META_REGULATE_MULTI), new Object[]{
				"DSD",
				"GEG",
				"DSD",
				Character.valueOf('S'), "gemSunstone",
				Character.valueOf('G'), "gemBlueTopaz",
				Character.valueOf('D'), "dyeOrange",
				Character.valueOf('E'), new ItemStack(BlockDefs.crystalMarker, 1, BlockCrystalMarker.META_REGULATE_EXPORT)
		}));

		//Set Export Gem
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BlockDefs.crystalMarker, 1, BlockCrystalMarker.META_SET_IMPORT), new Object[]{
				"DSD",
				"GEG",
				"DSD",
				Character.valueOf('S'), "gemSunstone",
				Character.valueOf('G'), "gemBlueTopaz",
				Character.valueOf('D'), "dyeRed",
				Character.valueOf('E'), new ItemStack(BlockDefs.crystalMarker, 1, BlockCrystalMarker.META_SET_EXPORT)
		}));

		//Spell Export Gem
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BlockDefs.crystalMarker, 1, BlockCrystalMarker.META_SPELL_EXPORT), new Object[]{
				"C C",
				"RPI",
				"C C",
				Character.valueOf('P'), new ItemStack(ItemDefs.core, 1, ItemCore.META_PURE),
				Character.valueOf('I'), new ItemStack(BlockDefs.crystalMarker, 1, BlockCrystalMarker.META_SET_IMPORT),
				Character.valueOf('C'), "dyeCyan",
				Character.valueOf('R'), new ItemStack(BlockDefs.crystalMarker, 1, BlockCrystalMarker.META_REGULATE_MULTI)
		}));

		//Gem Conversions
		createTier2GemConverstionRecipies(new ItemStack(BlockDefs.crystalMarker, 1, BlockCrystalMarker.META_LIKE_EXPORT), "dyeGreen");
		createTier2GemConverstionRecipies(new ItemStack(BlockDefs.crystalMarker, 1, BlockCrystalMarker.META_REGULATE_EXPORT), "dyePurple");
		createTier2GemConverstionRecipies(new ItemStack(BlockDefs.crystalMarker, 1, BlockCrystalMarker.META_SET_EXPORT), "dyeLightBlue");

		//Obelisk
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BlockDefs.obelisk), new Object[]{
				"VSV",
				"SCS",
				"VSV",
				Character.valueOf('V'), "dustVinteum",
				Character.valueOf('S'), "stone",
				Character.valueOf('C'), new ItemStack(Blocks.STONEBRICK, 1, 3)
		}));

		//Armor Infuser
		GameRegistry.addRecipe(new ItemStack(BlockDefs.armorImbuer), new Object[]{
				"ACA",
				"OPO",
				"OOO",
				Character.valueOf('A'), BlockDefs.craftingAltar,
				Character.valueOf('C'), new ItemStack(Blocks.CARPET, 1, Short.MAX_VALUE),
				Character.valueOf('O'), Blocks.OBSIDIAN,
				Character.valueOf('P'), Blocks.ENCHANTING_TABLE
		});

		//storage blocks
		createStorageBlockRecipe(new ItemStack(BlockDefs.blocks, 1, EnumBlockType.MOONSTONE.ordinal()), new ItemStack(ItemDefs.itemOre, 1, ItemOre.META_MOONSTONE));
		createStorageBlockRecipe(new ItemStack(BlockDefs.blocks, 1, EnumBlockType.VINTEUM.ordinal()), new ItemStack(ItemDefs.itemOre, 1, ItemOre.META_VINTEUM));
		createStorageBlockRecipe(new ItemStack(BlockDefs.blocks, 1, EnumBlockType.BLUETOPAZ.ordinal()), new ItemStack(ItemDefs.itemOre, 1, ItemOre.META_BLUE_TOPAZ));
		createStorageBlockRecipe(new ItemStack(BlockDefs.blocks, 1, EnumBlockType.SUNSTONE.ordinal()), new ItemStack(ItemDefs.itemOre, 1, ItemOre.META_SUNSTONE));
		createStorageBlockRecipe(new ItemStack(BlockDefs.blocks, 1, EnumBlockType.CHIMERITE.ordinal()), new ItemStack(ItemDefs.itemOre, 1, ItemOre.META_CHIMERITE));

		//furnace recipes
		GameRegistry.addSmelting(new ItemStack(ItemDefs.itemOre, 1, ItemOre.META_ARCANECOMPOUND), new ItemStack(ItemDefs.itemOre, 2, ItemOre.META_ARCANEASH), 0);
		GameRegistry.addSmelting(new ItemStack(BlockDefs.witchwoodLog, 1), new ItemStack(Items.COAL, 1, 1), 0.15f);

		addMetaSmeltingRecipe(BlockDefs.ores, EnumOreType.VINTEUM.ordinal(), new ItemStack(ItemDefs.itemOre, 1, ItemOre.META_VINTEUM));
		addMetaSmeltingRecipe(BlockDefs.ores, EnumOreType.SUNSTONE.ordinal(), new ItemStack(ItemDefs.itemOre, 1, ItemOre.META_SUNSTONE));
		addMetaSmeltingRecipe(BlockDefs.ores, EnumOreType.BLUETOPAZ.ordinal(), new ItemStack(ItemDefs.itemOre, 1, ItemOre.META_BLUE_TOPAZ));
		addMetaSmeltingRecipe(BlockDefs.ores, EnumOreType.CHIMERITE.ordinal(), new ItemStack(ItemDefs.itemOre, 1, ItemOre.META_CHIMERITE));
		addMetaSmeltingRecipe(BlockDefs.ores, EnumOreType.MOONSTONE.ordinal(), new ItemStack(ItemDefs.itemOre, 1, ItemOre.META_MOONSTONE));

		GameRegistry.addRecipe(new ItemStack(BlockDefs.illusionBlock, BlockDefs.illusionBlock.GetCraftingQuantity(), 0), BlockDefs.illusionBlock.GetRecipeComponents(false));
		GameRegistry.addRecipe(new ItemStack(BlockDefs.illusionBlock, BlockDefs.illusionBlock.GetCraftingQuantity(), 1), BlockDefs.illusionBlock.GetRecipeComponents(true));
		//crafting recipes
		GameRegistry.addRecipe(new ShapedOreRecipe(
				new ItemStack(ItemDefs.itemOre, 1, ItemOre.META_ARCANECOMPOUND),
				new Object[]
						{
								"BRN", "G G", "NRB",
								Character.valueOf('B'), "stone",
								Character.valueOf('R'), "dustRedstone",
								Character.valueOf('N'), "netherrack",
								Character.valueOf('G'), "dustGlowstone"
						}));

		//spell crafting
		GameRegistry.addRecipe(new ShapedOreRecipe(
				new ItemStack(ItemDefs.spellParchment, 1),
				new Object[]{
						"S",
						"P",
						"S",
						Character.valueOf('S'), "stickWood",
						Character.valueOf('P'), Items.PAPER
				}));

		//spell book
		GameRegistry.addRecipe(
				new ItemStack(ItemDefs.spellBook, 1),
				new Object[]{
						"SLL",
						"SPP",
						"SLL",
						Character.valueOf('S'), Items.STRING,
						Character.valueOf('L'), Items.LEATHER,
						Character.valueOf('P'), Items.PAPER
				});

		//crystal wrench
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemDefs.crystalWrench),
				new Object[]{
						"I I",
						"AVD",
						" I ",
						Character.valueOf('I'), "ingotIron",
						Character.valueOf('A'), BlockDefs.cerublossom,
						Character.valueOf('D'), BlockDefs.desertNova,
						Character.valueOf('V'), "dustVinteum",
				}));
		
		//spell book colors
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ItemDefs.spellBook, 1, 0), new Object[]{"dyeBrown", new ItemStack(ItemDefs.spellBook, 1, OreDictionary.WILDCARD_VALUE)}));  //brown
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ItemDefs.spellBook, 1, 1), new Object[]{"dyeCyan", new ItemStack(ItemDefs.spellBook, 1, OreDictionary.WILDCARD_VALUE)}));  //cyan
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ItemDefs.spellBook, 1, 2), new Object[]{"dyeGray", new ItemStack(ItemDefs.spellBook, 1, OreDictionary.WILDCARD_VALUE)}));  //gray
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ItemDefs.spellBook, 1, 3), new Object[]{"dyeLightBlue", new ItemStack(ItemDefs.spellBook, 1, OreDictionary.WILDCARD_VALUE)}));  //light blue
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ItemDefs.spellBook, 1, 4), new Object[]{"dyeWhite", new ItemStack(ItemDefs.spellBook, 1, OreDictionary.WILDCARD_VALUE)}));  //white
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ItemDefs.spellBook, 1, 5), new Object[]{"dyeBlack", new ItemStack(ItemDefs.spellBook, 1, OreDictionary.WILDCARD_VALUE)}));  //black
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ItemDefs.spellBook, 1, 6), new Object[]{"dyeOrange", new ItemStack(ItemDefs.spellBook, 1, OreDictionary.WILDCARD_VALUE)}));  //orange
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ItemDefs.spellBook, 1, 7), new Object[]{"dyePurple", new ItemStack(ItemDefs.spellBook, 1, OreDictionary.WILDCARD_VALUE)}));  //purple
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ItemDefs.spellBook, 1, 8), new Object[]{"dyeBlue", new ItemStack(ItemDefs.spellBook, 1, OreDictionary.WILDCARD_VALUE)}));  //blue
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ItemDefs.spellBook, 1, 9), new Object[]{"dyeGreen", new ItemStack(ItemDefs.spellBook, 1, OreDictionary.WILDCARD_VALUE)}));  //green
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ItemDefs.spellBook, 1, 10), new Object[]{"dyeYellow", new ItemStack(ItemDefs.spellBook, 1, OreDictionary.WILDCARD_VALUE)}));  //yellow
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ItemDefs.spellBook, 1, 11), new Object[]{"dyeRed", new ItemStack(ItemDefs.spellBook, 1, OreDictionary.WILDCARD_VALUE)}));  //red
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ItemDefs.spellBook, 1, 12), new Object[]{"dyeLime", new ItemStack(ItemDefs.spellBook, 1, OreDictionary.WILDCARD_VALUE)}));  //lime
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ItemDefs.spellBook, 1, 13), new Object[]{"dyePink", new ItemStack(ItemDefs.spellBook, 1, OreDictionary.WILDCARD_VALUE)}));  //pink
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ItemDefs.spellBook, 1, 14), new Object[]{"dyeMagenta", new ItemStack(ItemDefs.spellBook, 1, OreDictionary.WILDCARD_VALUE)}));  //magenta
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ItemDefs.spellBook, 1, 15), new Object[]{"dyeLightGray", new ItemStack(ItemDefs.spellBook, 1, OreDictionary.WILDCARD_VALUE)}));  //light gray

		GameRegistry.addRecipe(new ItemStack(ItemDefs.runeBag), new Object[]{
				"LLL", "W W", "LLL",
				Character.valueOf('L'), Items.LEATHER,
				Character.valueOf('W'), new ItemStack(Blocks.WOOL, 1, OreDictionary.WILDCARD_VALUE),
		});

		GameRegistry.addRecipe(new ShapedOreRecipe(ItemDefs.magicBroom, new Object[]{
				" S ",
				"ASA",
				" H ",
				Character.valueOf('S'), "stickWood",
				Character.valueOf('A'), "arcaneAsh",
				Character.valueOf('H'), Blocks.HAY_BLOCK
		}));

		GameRegistry.addRecipe(new ShapedOreRecipe(ItemDefs.woodenLeg, new Object[]{
				"P",
				"L",
				"S",
				Character.valueOf('P'), "plankWood",
				Character.valueOf('L'), "slabWood",
				Character.valueOf('S'), "stickWood"
		}));

		GameRegistry.addShapelessRecipe(
				new ItemStack(ItemDefs.evilBook), new Object[]{
						new ItemStack(ItemDefs.woodenLeg),
						new ItemStack(ItemDefs.arcaneCompendium)
				});

		GameRegistry.addShapelessRecipe(new ItemStack(ItemDefs.journal), new Object[]{
				new ItemStack(ItemDefs.essence, 1, ArsMagicaAPI.getAffinityRegistry().getId(Affinity.ARCANE)),
				new ItemStack(Items.WRITABLE_BOOK)
		});

		GameRegistry.addShapelessRecipe(
				new ItemStack(ItemDefs.manaPotionBundle, 1, (0 << 8) + 3), new Object[]{
						new ItemStack(ItemDefs.lesserManaPotion, 1, OreDictionary.WILDCARD_VALUE),
						new ItemStack(ItemDefs.lesserManaPotion, 1, OreDictionary.WILDCARD_VALUE),
						new ItemStack(ItemDefs.lesserManaPotion, 1, OreDictionary.WILDCARD_VALUE),
						new ItemStack(Items.STRING)
				});

		GameRegistry.addShapelessRecipe(
				new ItemStack(ItemDefs.manaPotionBundle, 1, (1 << 8) + 3), new Object[]{
						new ItemStack(ItemDefs.standardManaPotion, 1, OreDictionary.WILDCARD_VALUE),
						new ItemStack(ItemDefs.standardManaPotion, 1, OreDictionary.WILDCARD_VALUE),
						new ItemStack(ItemDefs.standardManaPotion, 1, OreDictionary.WILDCARD_VALUE),
						new ItemStack(Items.STRING)
				});

		GameRegistry.addShapelessRecipe(
				new ItemStack(ItemDefs.manaPotionBundle, 1, (2 << 8) + 3), new Object[]{
						new ItemStack(ItemDefs.greaterManaPotion, 1, OreDictionary.WILDCARD_VALUE),
						new ItemStack(ItemDefs.greaterManaPotion, 1, OreDictionary.WILDCARD_VALUE),
						new ItemStack(ItemDefs.greaterManaPotion, 1, OreDictionary.WILDCARD_VALUE),
						new ItemStack(Items.STRING)
				});

		GameRegistry.addShapelessRecipe(
				new ItemStack(ItemDefs.manaPotionBundle, 1, (3 << 8) + 3), new Object[]{
						new ItemStack(ItemDefs.epicManaPotion, 1, OreDictionary.WILDCARD_VALUE),
						new ItemStack(ItemDefs.epicManaPotion, 1, OreDictionary.WILDCARD_VALUE),
						new ItemStack(ItemDefs.epicManaPotion, 1, OreDictionary.WILDCARD_VALUE),
						new ItemStack(Items.STRING)
				});

		GameRegistry.addShapelessRecipe(
				new ItemStack(ItemDefs.manaPotionBundle, 1, (4 << 8) + 3), new Object[]{
						new ItemStack(ItemDefs.legendaryManaPotion, 1, OreDictionary.WILDCARD_VALUE),
						new ItemStack(ItemDefs.legendaryManaPotion, 1, OreDictionary.WILDCARD_VALUE),
						new ItemStack(ItemDefs.legendaryManaPotion, 1, OreDictionary.WILDCARD_VALUE),
						new ItemStack(Items.STRING)
				});

		//blank rune
		GameRegistry.addRecipe(new ShapedOreRecipe(
				new ItemStack(ItemDefs.blankRune, 2),
				new Object[]{
						" S ", "SSS", "SS ",
						Character.valueOf('S'), "cobblestone"
				}));
		//blue rune
		GameRegistry.addRecipe(new ShapelessOreRecipe(
				new ItemStack(ItemDefs.rune, 1, EnumDyeColor.BLUE.getDyeDamage()),
				new Object[]{
						"dyeBlue",
						new ItemStack(ItemDefs.blankRune)
				}));
		//red rune
		GameRegistry.addRecipe(new ShapelessOreRecipe(
				new ItemStack(ItemDefs.rune, 1, EnumDyeColor.RED.getDyeDamage()),
				new Object[]{
						"dyeRed",
						new ItemStack(ItemDefs.blankRune)
				}));
		//yellow rune
		GameRegistry.addRecipe(new ShapelessOreRecipe(
				new ItemStack(ItemDefs.rune, 1, EnumDyeColor.YELLOW.getDyeDamage()),
				new Object[]{
						"dyeYellow",
						new ItemStack(ItemDefs.blankRune)
				}));
		//orange rune
		GameRegistry.addRecipe(new ShapelessOreRecipe(
				new ItemStack(ItemDefs.rune, 1, EnumDyeColor.ORANGE.getDyeDamage()),
				new Object[]{
						"dyeOrange",
						new ItemStack(ItemDefs.blankRune)
				}));
		//green rune
		GameRegistry.addRecipe(new ShapelessOreRecipe(
				new ItemStack(ItemDefs.rune, 1, EnumDyeColor.GREEN.getDyeDamage()),
				new Object[]{
						"dyeGreen",
						new ItemStack(ItemDefs.blankRune)
				}));
		//purple rune
		GameRegistry.addRecipe(new ShapelessOreRecipe(
				new ItemStack(ItemDefs.rune, 1, EnumDyeColor.PURPLE.getDyeDamage()),
				new Object[]{
						"dyePurple",
						new ItemStack(ItemDefs.blankRune)
				}));
		//white rune
		GameRegistry.addRecipe(new ShapelessOreRecipe(
				new ItemStack(ItemDefs.rune, 1, EnumDyeColor.WHITE.getDyeDamage()),
				new Object[]{
						"dyeWhite",
						new ItemStack(ItemDefs.blankRune)
				}));
		//black rune
		GameRegistry.addRecipe(new ShapelessOreRecipe(
				new ItemStack(ItemDefs.rune, 1, EnumDyeColor.BLACK.getDyeDamage()),
				new Object[]{
						"dyeBlack",
						new ItemStack(ItemDefs.blankRune)
				}));
		//brown rune
		GameRegistry.addRecipe(new ShapelessOreRecipe(
				new ItemStack(ItemDefs.rune, 1, EnumDyeColor.BROWN.getDyeDamage()),
				new Object[]{
						"dyeBrown",
						new ItemStack(ItemDefs.blankRune)
				}));
		//cyan rune
		GameRegistry.addRecipe(new ShapelessOreRecipe(
				new ItemStack(ItemDefs.rune, 1, EnumDyeColor.CYAN.getDyeDamage()),
				new Object[]{
						"dyeCyan",
						new ItemStack(ItemDefs.blankRune)
				}));
		//gray rune
		GameRegistry.addRecipe(new ShapelessOreRecipe(
				new ItemStack(ItemDefs.rune, 1, EnumDyeColor.GRAY.getDyeDamage()),
				new Object[]{
						"dyeGray",
						new ItemStack(ItemDefs.blankRune)
				}));
		//light blue rune
		GameRegistry.addRecipe(new ShapelessOreRecipe(
				new ItemStack(ItemDefs.rune, 1, EnumDyeColor.LIGHT_BLUE.getDyeDamage()),
				new Object[]{
						"dyeLightBlue",
						new ItemStack(ItemDefs.blankRune)
				}));
		//light gray rune
		GameRegistry.addRecipe(new ShapelessOreRecipe(
				new ItemStack(ItemDefs.rune, 1, EnumDyeColor.SILVER.getDyeDamage()),
				new Object[]{
						"dyeLightGray",
						new ItemStack(ItemDefs.blankRune)
				}));
		//magenta rune
		GameRegistry.addRecipe(new ShapelessOreRecipe(
				new ItemStack(ItemDefs.rune, 1, EnumDyeColor.MAGENTA.getDyeDamage()),
				new Object[]{
						"dyeMagenta",
						new ItemStack(ItemDefs.blankRune)
				}));
		//pink rune
		GameRegistry.addRecipe(new ShapelessOreRecipe(
				new ItemStack(ItemDefs.rune, 1, EnumDyeColor.PINK.getDyeDamage()),
				new Object[]{
						"dyePink",
						new ItemStack(ItemDefs.blankRune)
				}));
		//pink rune
		GameRegistry.addRecipe(new ShapelessOreRecipe(
				new ItemStack(ItemDefs.rune, 1, EnumDyeColor.LIME.getDyeDamage()),
				new Object[]{
						"dyeLime",
						new ItemStack(ItemDefs.blankRune)
				}));

		//wizard chalk
		GameRegistry.addRecipe(new ShapelessOreRecipe(
				new ItemStack(ItemDefs.chalk, 1),
				new Object[]{
						"dyeWhite",
						new ItemStack(Items.CLAY_BALL),
						"dustVinteum",
						new ItemStack(Items.FLINT),
						new ItemStack(Items.PAPER)
				}));

		//empty flicker jar
		GameRegistry.addRecipe(new ShapedOreRecipe(
				new ItemStack(ItemDefs.flickerJar, 1),
				new Object[]{
						"NWN",
						"G G",
						" G ",
						Character.valueOf('W'), BlockDefs.magicWall,
						Character.valueOf('N'), "nuggetGold",
						Character.valueOf('G'), "paneGlassColorless"
				}));

		//warding candle
		GameRegistry.addRecipe(new ItemStack(ItemDefs.wardingCandle), new Object[]{
				"S",
				"F",
				"P",
				Character.valueOf('S'), Items.STRING,
				Character.valueOf('F'), new ItemStack(ItemDefs.itemOre, 1, ItemOre.META_ANIMALFAT),
				Character.valueOf('P'), BlockDefs.witchwoodSingleSlab
		});

		//magitech goggles
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemDefs.magitechGoggles), new Object[]{
				"LLL",
				"CGC",
				"TLT",
				Character.valueOf('C'), "gemChimerite",
				Character.valueOf('T'), "gemBlueTopaz",
				Character.valueOf('L'), Items.LEATHER,
				Character.valueOf('G'), "nuggetGold",
		}));

		//magitech staff
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemDefs.spellStaffMagitech), new Object[]{
				" GT",
				"G G",
				"GG ",
				Character.valueOf('T'), "gemBlueTopaz",
				Character.valueOf('G'), "nuggetGold",
		}));

		//armor recipes
		//MAGE
		GameRegistry.addRecipe(new ItemStack(ItemDefs.mageHood, 1),
				new Object[]{
						"WLW", "WRW", " B ",
						Character.valueOf('W'), new ItemStack(Blocks.WOOL, 1, 12),
						Character.valueOf('L'), Items.LEATHER,
						Character.valueOf('R'), new ItemStack(ItemDefs.rune, 1, EnumDyeColor.PURPLE.getDyeDamage()),
						Character.valueOf('B'), new ItemStack(Items.POTIONITEM, 1, 0),
				});
		GameRegistry.addRecipe(new ItemStack(ItemDefs.mageArmor, 1),
				new Object[]{
						"RCR", "WLW", "WWW",
						Character.valueOf('W'), new ItemStack(Blocks.WOOL, 1, 12),
						Character.valueOf('L'), Items.LEATHER,
						Character.valueOf('R'), new ItemStack(ItemDefs.rune, 1, EnumDyeColor.WHITE.getDyeDamage()),
						Character.valueOf('C'), Items.COAL
				});
		GameRegistry.addRecipe(new ItemStack(ItemDefs.mageLeggings, 1),
				new Object[]{
						"WRW", "WGW", "L L",
						Character.valueOf('W'), new ItemStack(Blocks.WOOL, 1, 12),
						Character.valueOf('L'), Items.LEATHER,
						Character.valueOf('R'), new ItemStack(ItemDefs.rune, 1, EnumDyeColor.YELLOW.getDyeDamage()),
						Character.valueOf('G'), Items.GUNPOWDER
				});
		GameRegistry.addRecipe(new ItemStack(ItemDefs.mageBoots, 1),
				new Object[]{
						"R R", "L L", "WFW",
						Character.valueOf('W'), new ItemStack(Blocks.WOOL, 1, 12),
						Character.valueOf('L'), Items.LEATHER,
						Character.valueOf('R'), new ItemStack(ItemDefs.rune, 1, EnumDyeColor.BLACK.getDyeDamage()),
						Character.valueOf('F'), Items.FEATHER
				});
		//BATTLEMAGE
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemDefs.battlemageHood, 1),
				new Object[]{
						"WLW", "WRW", " E ",
						Character.valueOf('W'), new ItemStack(Blocks.OBSIDIAN),
						Character.valueOf('L'), BlockDefs.goldInlay,
						Character.valueOf('R'), new ItemStack(ItemDefs.rune, 1, 1),
						Character.valueOf('E'), new ItemStack(ItemDefs.essence, 1, ArsMagicaAPI.getAffinityRegistry().getId(Affinity.WATER))
				}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemDefs.battlemageArmor, 1),
				new Object[]{
						"RER", "WLW", "WWW",
						Character.valueOf('W'), new ItemStack(Blocks.OBSIDIAN),
						Character.valueOf('E'), new ItemStack(ItemDefs.essence, 1, ArsMagicaAPI.getAffinityRegistry().getId(Affinity.EARTH)),
						Character.valueOf('R'), new ItemStack(ItemDefs.rune, 1, 1),
						Character.valueOf('L'), BlockDefs.goldInlay
				}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemDefs.battlemageLeggings, 1),
				new Object[]{
						"WRW", "LEL", "W W",
						Character.valueOf('W'), new ItemStack(Blocks.OBSIDIAN),
						Character.valueOf('L'), BlockDefs.goldInlay,
						Character.valueOf('R'), new ItemStack(ItemDefs.rune, 1, 1),
						Character.valueOf('E'), new ItemStack(ItemDefs.essence, 1, ArsMagicaAPI.getAffinityRegistry().getId(Affinity.FIRE))
				}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemDefs.battlemageBoots, 1),
				new Object[]{
						"R R", "WEW", "WLW",
						Character.valueOf('W'), new ItemStack(Blocks.OBSIDIAN),
						Character.valueOf('L'), BlockDefs.goldInlay,
						Character.valueOf('R'), new ItemStack(ItemDefs.rune, 1, 1),
						Character.valueOf('E'), new ItemStack(ItemDefs.essence, 1, ArsMagicaAPI.getAffinityRegistry().getId(Affinity.AIR))
				}));
		//ARCHMAGE
		/*GameRegistry.addRecipe(new ItemStack(archmageHood, 1),
				new Object[]{
			"WPW", "WRW",
			Character.valueOf('W'), new ItemStack(Blocks.cloth, 1, 0),
			Character.valueOf('P'), new ItemStack(essence, 1, 10),
			Character.valueOf('R'), new ItemStack(ItemDefs.rune, 1, 6)
		});
		GameRegistry.addRecipe(new ItemStack(archmageArmor, 1),
				new Object[]{
			"RGR", "WPW", "WWW",
			Character.valueOf('W'), new ItemStack(Blocks.cloth, 1, 0),
			Character.valueOf('P'), new ItemStack(essence, 1, 10),
			Character.valueOf('R'), new ItemStack(ItemDefs.rune, 1, 6),
			Character.valueOf('G'), Item.ingotGold
		});
		GameRegistry.addRecipe(new ItemStack(archmageLeggings, 1),
				new Object[]{
			"WPW", "R R", "W W",
			Character.valueOf('W'), new ItemStack(Blocks.cloth, 1, 0),
			Character.valueOf('P'), new ItemStack(essence, 1, 10),
			Character.valueOf('R'), new ItemStack(ItemDefs.rune, 1, 6)
		});
		GameRegistry.addRecipe(new ItemStack(archmageBoots, 1),
				new Object[]{
			"P R", "W W", "W W",
			Character.valueOf('W'), new ItemStack(Blocks.cloth, 1, 0),
			Character.valueOf('P'), new ItemStack(essence, 1, 10),
			Character.valueOf('R'), new ItemStack(ItemDefs.rune, 1, 6)
		});*/

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemDefs.essenceBag), new Object[]{
				"LLL", "WNW", "LLL",
				Character.valueOf('L'), Items.LEATHER,
				Character.valueOf('W'), new ItemStack(Blocks.WOOL, 1, OreDictionary.WILDCARD_VALUE),
				Character.valueOf('N'), "nuggetGold"
		}));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemDefs.crystalPhylactery), new Object[]{
				" B ", "GPG", " W ",
				Character.valueOf('B'), "gemMoonstone",
				Character.valueOf('W'), BlockDefs.magicWall,
				Character.valueOf('G'), "blockGlassColorless",
				Character.valueOf('P'), new ItemStack(ItemDefs.itemOre, 1, ItemOre.META_PURIFIED_VINTEUM)
		}));

		//lesser mana potion
		//GameRegistry.addRecipe(new ItemStack(Item.potion, 1, ))

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemDefs.keystone, 1), new Object[]{
				"GIG",
				"IVI",
				"GIG",
				Character.valueOf('G'), "ingotGold",
				Character.valueOf('I'), "ingotIron",
				Character.valueOf('V'), "dustVinteum"
		}));

		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ItemDefs.itemOre, 1, ItemOre.META_PURIFIED_VINTEUM), new Object[]{
				new ItemStack(BlockDefs.cerublossom),
				new ItemStack(BlockDefs.desertNova),
				"dustVinteum",
				"arcaneAsh"
		}));

		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ItemDefs.manaCake, 3, 0), new Object[]{
				new ItemStack(BlockDefs.cerublossom),
				new ItemStack(BlockDefs.desertNova),
				new ItemStack(Items.SUGAR),
				"cropWheat"
		}));
		
		BrewingRecipeRegistry.addRecipe(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypes.AWKWARD), new ItemStack(ItemDefs.itemOre, 1, ItemOre.META_VINTEUM), new ItemStack(ItemDefs.greaterManaPotion));
		BrewingRecipeRegistry.addRecipe(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypes.AWKWARD), new ItemStack(ItemDefs.itemOre, 1, ItemOre.META_ARCANEASH), new ItemStack(ItemDefs.epicManaPotion));
		BrewingRecipeRegistry.addRecipe(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypes.AWKWARD), new ItemStack(ItemDefs.itemOre, 1, ItemOre.META_PURIFIED_VINTEUM), new ItemStack(ItemDefs.legendaryManaPotion));
		
		GameRegistry.addShapelessRecipe(new ItemStack(ItemDefs.lesserManaPotion), new Object[]{
				new ItemStack(Items.WHEAT_SEEDS),
				new ItemStack(Items.SUGAR),
				new ItemStack(Items.POTIONITEM, 1, Short.MAX_VALUE)
		});

		GameRegistry.addShapelessRecipe(new ItemStack(ItemDefs.standardManaPotion), new Object[]{
				new ItemStack(Items.GUNPOWDER),
				new ItemStack(ItemDefs.lesserManaPotion, 1, Short.MAX_VALUE)
		});

//		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ItemDefs.greaterManaPotion), new Object[]{
//				"dustVinteum",
//				new ItemStack(ItemDefs.standardManaPotion, 1, Short.MAX_VALUE)
//		}));
//
//		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ItemDefs.epicManaPotion), new Object[]{
//				"arcaneAsh",
//				new ItemStack(ItemDefs.greaterManaPotion, 1, Short.MAX_VALUE)
//		}));
//
//		GameRegistry.addShapelessRecipe(new ItemStack(ItemDefs.legendaryManaPotion), new Object[]{
//				new ItemStack(ItemDefs.itemOre, 1, ItemOre.META_PURIFIED_VINTEUM),
//				new ItemStack(ItemDefs.epicManaPotion, 1, Short.MAX_VALUE)
//		});

		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ItemDefs.liquidEssenceBottle), new Object[]{
				"gemChimerite",
				new ItemStack(BlockDefs.tarmaRoot),
				UniversalBucket.getFilledBucket(ForgeModContainer.getInstance().universalBucket, BlockDefs.liquid_essence)
		}));

		GameRegistry.addRecipe(new ItemStack(ItemDefs.lesserFocus), ItemDefs.lesserFocus.getRecipeItems());
		GameRegistry.addRecipe(new ItemStack(ItemDefs.standardFocus), ItemDefs.standardFocus.getRecipeItems());
		GameRegistry.addRecipe(new ItemStack(ItemDefs.greaterFocus), ItemDefs.greaterFocus.getRecipeItems());
		GameRegistry.addRecipe(new ItemStack(ItemDefs.manaFocus), ItemDefs.manaFocus.getRecipeItems());
		GameRegistry.addRecipe(new ItemStack(ItemDefs.chargeFocus), ItemDefs.chargeFocus.getRecipeItems());

		GameRegistry.addRecipe(new ItemStack(ItemDefs.playerFocus), ItemDefs.playerFocus.getRecipeItems());
		GameRegistry.addRecipe(new ItemStack(ItemDefs.mobFocus), ItemDefs.mobFocus.getRecipeItems());
		GameRegistry.addRecipe(new ItemStack(ItemDefs.itemFocus), ItemDefs.itemFocus.getRecipeItems());
		GameRegistry.addRecipe(new ItemStack(ItemDefs.creatureFocus), ItemDefs.creatureFocus.getRecipeItems());

		//binding catalysts
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemDefs.bindingCatalyst, 1, ItemBindingCatalyst.META_AXE), new Object[]{
				"SVS",
				"SAS",
				"SVS",
				Character.valueOf('V'), "dustVinteum",
				Character.valueOf('S'), "slimeball",
				Character.valueOf('A'), Items.GOLDEN_AXE
		}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemDefs.bindingCatalyst, 1, ItemBindingCatalyst.META_PICK), new Object[]{
				"SVS",
				"SAS",
				"SVS",
				Character.valueOf('V'), "dustVinteum",
				Character.valueOf('S'), "slimeball",
				Character.valueOf('A'), Items.GOLDEN_PICKAXE
		}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemDefs.bindingCatalyst, 1, ItemBindingCatalyst.META_SHOVEL), new Object[]{
				"SVS",
				"SAS",
				"SVS",
				Character.valueOf('V'), "dustVinteum",
				Character.valueOf('S'), "slimeball",
				Character.valueOf('A'), Items.GOLDEN_SHOVEL
		}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemDefs.bindingCatalyst, 1, ItemBindingCatalyst.META_SWORD), new Object[]{
				"SVS",
				"SAS",
				"SVS",
				Character.valueOf('V'), new ItemStack(ItemDefs.itemOre, 1, ItemOre.META_PURIFIED_VINTEUM),
				Character.valueOf('S'), "slimeball",
				Character.valueOf('A'), Items.GOLDEN_SWORD
		}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemDefs.bindingCatalyst, 1, ItemBindingCatalyst.META_HOE), new Object[]{
				"SVS",
				"SAS",
				"SVS",
				Character.valueOf('V'), "dustVinteum",
				Character.valueOf('S'), "slimeball",
				Character.valueOf('A'), Items.GOLDEN_HOE
		}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemDefs.bindingCatalyst, 1, ItemBindingCatalyst.META_BOW), new Object[]{
				"SVS",
				"SAS",
				"SVS",
				Character.valueOf('V'), "dustVinteum",
				Character.valueOf('S'), "slimeball",
				Character.valueOf('A'), Items.BOW
		}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemDefs.bindingCatalyst, 1, ItemBindingCatalyst.META_SHIELD), new Object[]{
				"SVS",
				"SAS",
				"SVS",
				Character.valueOf('V'), "dustVinteum",
				Character.valueOf('S'), "slimeball",
				Character.valueOf('A'), Items.SHIELD
		}));

		GameRegistry.addRecipe(new ItemStack(ItemDefs.itemKeystoneDoor, 1, ItemKeystoneDoor.KEYSTONE_DOOR), new Object[]{
				"PWP",
				"RRR",
				"PWP",
				Character.valueOf('P'), BlockDefs.witchwoodPlanks,
				Character.valueOf('R'), new ItemStack(ItemDefs.blankRune),
				Character.valueOf('W'), BlockDefs.magicWall
		});

		GameRegistry.addRecipe(new ItemStack(ItemDefs.itemKeystoneDoor, 1, ItemKeystoneDoor.SPELL_SEALED_DOOR), new Object[]{
				" G ",
				"SKS",
				" L ",
				Character.valueOf('G'), ItemDefs.greaterFocus,
				Character.valueOf('S'), ItemDefs.standardFocus,
				Character.valueOf('L'), ItemDefs.lesserFocus,
				Character.valueOf('K'), new ItemStack(ItemDefs.itemKeystoneDoor, 1, ItemKeystoneDoor.KEYSTONE_DOOR)
		});

		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ItemDefs.manaMartini), new Object[]{
				new ItemStack(Blocks.ICE),
				"cropPotato",
				new ItemStack(Items.SUGAR),
				"stickWood",
				new ItemStack(ItemDefs.standardManaPotion)
		}));

		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ItemDefs.inscriptionUpgrade, 1, 0),
				new ItemStack(Items.BOOK),
				new ItemStack(Items.STRING),
				new ItemStack(Items.FEATHER),
				"dyeBlack"
		));

		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ItemDefs.inscriptionUpgrade, 1, 1),
				new ItemStack(Blocks.CARPET, 1, Short.MAX_VALUE),
				new ItemStack(Items.BOOK),
				new ItemStack(ItemDefs.chalk)
		));

		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ItemDefs.inscriptionUpgrade, 1, 2),
				new ItemStack(ItemDefs.wardingCandle),
				new ItemStack(Items.FLINT_AND_STEEL),
				new ItemStack(ItemDefs.itemOre, 1, ItemOre.META_ANIMALFAT),
				new ItemStack(Items.GLASS_BOTTLE),
				new ItemStack(Items.BOOK)
		));

		GameRegistry.addShapelessRecipe(new ItemStack(ItemDefs.workbenchUpgrade), new ItemStack(BlockDefs.magiciansWorkbench), new ItemStack(Blocks.CHEST), new ItemStack(Blocks.CRAFTING_TABLE), new ItemStack(Blocks.CRAFTING_TABLE), new ItemStack(Items.GOLD_INGOT));

		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ItemDefs.deficitCrystal), new ItemStack(Items.DIAMOND), "arcaneAsh", new ItemStack(Items.ENDER_EYE)));
	}

	
	private static void addMetaSmeltingRecipe(Block input, int meta, ItemStack output){
		ItemStack stack = new ItemStack(input, 1, meta);
		GameRegistry.addSmelting(stack, output, 0);
	}
	
	private static void createStorageBlockRecipe(ItemStack storageBlock, ItemStack storageItem){
		GameRegistry.addRecipe(storageBlock, new Object[]{
				"III",
				"III",
				"III",
				Character.valueOf('I'), new ItemStack(storageItem.getItem(), 1, storageItem.getItemDamage())
		});

		GameRegistry.addShapelessRecipe(new ItemStack(storageItem.getItem(), 9, storageItem.getItemDamage()), storageBlock);
	}

	private static void createTier2GemConverstionRecipies(ItemStack stack, String dyeCode){
		if (stack.getItemDamage() != BlockCrystalMarker.META_LIKE_EXPORT){
			GameRegistry.addRecipe(new ShapelessOreRecipe(stack, new Object[]{
					new ItemStack(BlockDefs.crystalMarker, 1, BlockCrystalMarker.META_LIKE_EXPORT),
					dyeCode
			}));
		}

		if (stack.getItemDamage() != BlockCrystalMarker.META_REGULATE_EXPORT){
			GameRegistry.addRecipe(new ShapelessOreRecipe(stack, new Object[]{
					new ItemStack(BlockDefs.crystalMarker, 1, BlockCrystalMarker.META_REGULATE_EXPORT),
					dyeCode
			}));
		}

		if (stack.getItemDamage() != BlockCrystalMarker.META_SET_EXPORT){
			GameRegistry.addRecipe(new ShapelessOreRecipe(stack, new Object[]{
					new ItemStack(BlockDefs.crystalMarker, 1, BlockCrystalMarker.META_SET_EXPORT),
					dyeCode
			}));
		}
	}
}
