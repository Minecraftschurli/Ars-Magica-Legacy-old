package minecraftschurli.arsmagicalegacy.init;

import minecraftschurli.arsmagicalegacy.ArsMagicaLegacy;
import minecraftschurli.arsmagicalegacy.objects.item.ArcaneCompendiumItem;
import minecraftschurli.arsmagicalegacy.objects.item.ResearchOrbItem;
import minecraftschurli.arsmagicalegacy.objects.item.SpellItem;
import minecraftschurli.arsmagicalegacy.objects.item.spellbook.SpellBookItem;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.TallBlockItem;
import net.minecraft.item.WallOrFloorItem;
import net.minecraftforge.fml.RegistryObject;

/**
 * @author Minecraftschurli
 * @version 2019-11-07
 */
public final class ModItems implements Registries {
    public static final Item.Properties ITEM_64 = new Item.Properties().group(ArsMagicaLegacy.ITEM_GROUP).maxStackSize(64);
    public static final Item.Properties ITEM_1 = new Item.Properties().group(ArsMagicaLegacy.ITEM_GROUP).maxStackSize(1);
    public static final RegistryObject<Item> ARCANE_COMPENDIUM = ITEMS.register("arcane_compendium", ArcaneCompendiumItem::new);
    public static final RegistryObject<Item> SPELL = ITEMS.register("spell", SpellItem::new);
    public static final RegistryObject<Item> SPELL_BOOK = ITEMS.register("spell_book", SpellBookItem::new);
    public static final RegistryObject<Item> CHIMERITE_ORE = ITEMS.register("chimerite_ore", ()->new BlockItem(ModBlocks.CHIMERITE_ORE.get(), ITEM_64));
    public static final RegistryObject<Item> CHIMERITE_BLOCK = ITEMS.register("chimerite_block", ()->new BlockItem(ModBlocks.CHIMERITE_BLOCK.get(), ITEM_64));
    public static final RegistryObject<Item> CHIMERITE = ITEMS.register("chimerite", ()->new Item(ITEM_64));
    public static final RegistryObject<Item> TOPAZ_ORE = ITEMS.register("topaz_ore", ()->new BlockItem(ModBlocks.TOPAZ_ORE.get(), ITEM_64));
    public static final RegistryObject<Item> TOPAZ_BLOCK = ITEMS.register("topaz_block", ()->new BlockItem(ModBlocks.TOPAZ_BLOCK.get(), ITEM_64));
    public static final RegistryObject<Item> TOPAZ = ITEMS.register("topaz", ()->new Item(ITEM_64));
    public static final RegistryObject<Item> VINTEUM_ORE = ITEMS.register("vinteum_ore", ()->new BlockItem(ModBlocks.VINTEUM_ORE.get(), ITEM_64));
    public static final RegistryObject<Item> VINTEUM_BLOCK = ITEMS.register("vinteum_block", ()->new BlockItem(ModBlocks.VINTEUM_BLOCK.get(), ITEM_64));
    public static final RegistryObject<Item> VINTEUM = ITEMS.register("vinteum", ()->new Item(ITEM_64));
    public static final RegistryObject<Item> MOONSTONE_ORE = ITEMS.register("moonstone_ore", ()->new BlockItem(ModBlocks.MOONSTONE_ORE.get(), ITEM_64));
    public static final RegistryObject<Item> MOONSTONE_BLOCK = ITEMS.register("moonstone_block", ()->new BlockItem(ModBlocks.MOONSTONE_BLOCK.get(), ITEM_64));
    public static final RegistryObject<Item> MOONSTONE = ITEMS.register("moonstone", ()->new Item(ITEM_64));
    public static final RegistryObject<Item> SUNSTONE_ORE = ITEMS.register("sunstone_ore", ()->new BlockItem(ModBlocks.SUNSTONE_ORE.get(), ITEM_64));
    public static final RegistryObject<Item> SUNSTONE_BLOCK = ITEMS.register("sunstone_block", ()->new BlockItem(ModBlocks.SUNSTONE_BLOCK.get(), ITEM_64));
    public static final RegistryObject<Item> SUNSTONE = ITEMS.register("sunstone", ()->new Item(ITEM_64));
    public static final RegistryObject<Item> ALTAR_CORE = ITEMS.register("altar_core", ()->new BlockItem(ModBlocks.ALTAR_CORE.get(), ITEM_64));
    public static final RegistryObject<Item> MAGIC_WALL = ITEMS.register("magic_wall", ()->new BlockItem(ModBlocks.MAGIC_WALL.get(), ITEM_64));
    public static final RegistryObject<Item> WITCHWOOD_LOG = ITEMS.register("witchwood_log", ()->new BlockItem(ModBlocks.WITCHWOOD_LOG.get(), ITEM_64));
    public static final RegistryObject<Item> WITCHWOOD_WOOD = ITEMS.register("witchwood_wood", ()->new BlockItem(ModBlocks.WITCHWOOD_WOOD.get(), ITEM_64));
    public static final RegistryObject<Item> STRIPPED_WITCHWOOD_LOG = ITEMS.register("stripped_witchwood_log", ()->new BlockItem(ModBlocks.STRIPPED_WITCHWOOD_LOG.get(), ITEM_64));
    public static final RegistryObject<Item> STRIPPED_WITCHWOOD_WOOD = ITEMS.register("stripped_witchwood_wood", ()->new BlockItem(ModBlocks.STRIPPED_WITCHWOOD_WOOD.get(), ITEM_64));
    public static final RegistryObject<Item> WITCHWOOD_PLANKS = ITEMS.register("witchwood_planks", ()->new BlockItem(ModBlocks.WITCHWOOD_PLANKS.get(), ITEM_64));
    public static final RegistryObject<Item> WITCHWOOD_SLAB = ITEMS.register("witchwood_slab", ()->new BlockItem(ModBlocks.WITCHWOOD_SLAB.get(), ITEM_64));
    public static final RegistryObject<Item> WITCHWOOD_STAIRS = ITEMS.register("witchwood_stairs", ()->new BlockItem(ModBlocks.WITCHWOOD_STAIRS.get(), ITEM_64));
    public static final RegistryObject<Item> WITCHWOOD_LEAVES = ITEMS.register("witchwood_leaves", ()->new BlockItem(ModBlocks.WITCHWOOD_LEAVES.get(), ITEM_64));
    public static final RegistryObject<Item> WITCHWOOD_FENCE = ITEMS.register("witchwood_fence", ()->new BlockItem(ModBlocks.WITCHWOOD_FENCE.get(), ITEM_64));
    public static final RegistryObject<Item> WITCHWOOD_FENCE_GATE = ITEMS.register("witchwood_fence_gate", ()->new BlockItem(ModBlocks.WITCHWOOD_FENCE_GATE.get(), ITEM_64));
    public static final RegistryObject<Item> WITCHWOOD_DOOR = ITEMS.register("witchwood_door", ()->new TallBlockItem(ModBlocks.WITCHWOOD_DOOR.get(), ITEM_64));
    public static final RegistryObject<Item> WITCHWOOD_TRAPDOOR = ITEMS.register("witchwood_trapdoor", ()->new BlockItem(ModBlocks.WITCHWOOD_TRAPDOOR.get(), ITEM_64));
    public static final RegistryObject<Item> WITCHWOOD_BUTTON = ITEMS.register("witchwood_button", ()->new BlockItem(ModBlocks.WITCHWOOD_BUTTON.get(), ITEM_64));
    public static final RegistryObject<Item> WITCHWOOD_PRESSURE_PLATE = ITEMS.register("witchwood_pressure_plate", ()->new BlockItem(ModBlocks.WITCHWOOD_PRESSURE_PLATE.get(), ITEM_64));
    public static final RegistryObject<Item> WITCHWOOD_SAPLING = ITEMS.register("witchwood_sapling", ()->new BlockItem(ModBlocks.WITCHWOOD_SAPLING.get(), ITEM_64));
    public static final RegistryObject<Item> AUM = ITEMS.register("aum", ()->new BlockItem(ModBlocks.AUM.get(), ITEM_64));
    public static final RegistryObject<Item> CERUBLOSSOM = ITEMS.register("cerublossom", ()->new BlockItem(ModBlocks.CERUBLOSSOM.get(), ITEM_64));
    public static final RegistryObject<Item> DESERT_NOVA = ITEMS.register("desert_nova", ()->new BlockItem(ModBlocks.DESERT_NOVA.get(), ITEM_64));
    public static final RegistryObject<Item> TARMA_ROOT = ITEMS.register("tarma_root", ()->new BlockItem(ModBlocks.TARMA_ROOT.get(), ITEM_64));
    public static final RegistryObject<Item> WAKEBLOOM = ITEMS.register("wakebloom", ()->new BlockItem(ModBlocks.WAKEBLOOM.get(), ITEM_64));
    public static final RegistryObject<Item> RUNE = ITEMS.register("rune", ()->new Item(ITEM_64));
    public static final RegistryObject<Item> WHITE_RUNE = ITEMS.register("white_rune", ()->new Item(ITEM_64));
    public static final RegistryObject<Item> ORANGE_RUNE = ITEMS.register("orange_rune", ()->new Item(ITEM_64));
    public static final RegistryObject<Item> MAGENTA_RUNE = ITEMS.register("magenta_rune", ()->new Item(ITEM_64));
    public static final RegistryObject<Item> LIGHT_BLUE_RUNE = ITEMS.register("light_blue_rune", ()->new Item(ITEM_64));
    public static final RegistryObject<Item> YELLOW_RUNE = ITEMS.register("yellow_rune", ()->new Item(ITEM_64));
    public static final RegistryObject<Item> LIME_RUNE = ITEMS.register("lime_rune", ()->new Item(ITEM_64));
    public static final RegistryObject<Item> PINK_RUNE = ITEMS.register("pink_rune", ()->new Item(ITEM_64));
    public static final RegistryObject<Item> GRAY_RUNE = ITEMS.register("gray_rune", ()->new Item(ITEM_64));
    public static final RegistryObject<Item> LIGHT_GRAY_RUNE = ITEMS.register("light_gray_rune", ()->new Item(ITEM_64));
    public static final RegistryObject<Item> CYAN_RUNE = ITEMS.register("cyan_rune", ()->new Item(ITEM_64));
    public static final RegistryObject<Item> PURPLE_RUNE = ITEMS.register("purple_rune", ()->new Item(ITEM_64));
    public static final RegistryObject<Item> BLUE_RUNE = ITEMS.register("blue_rune", ()->new Item(ITEM_64));
    public static final RegistryObject<Item> BROWN_RUNE = ITEMS.register("brown_rune", ()->new Item(ITEM_64));
    public static final RegistryObject<Item> GREEN_RUNE = ITEMS.register("green_rune", ()->new Item(ITEM_64));
    public static final RegistryObject<Item> RED_RUNE = ITEMS.register("red_rune", ()->new Item(ITEM_64));
    public static final RegistryObject<Item> BLACK_RUNE = ITEMS.register("black_rune", ()->new Item(ITEM_64));
    public static final RegistryObject<Item> BLUE_ORB = ITEMS.register("blue_orb", ()->new ResearchOrbItem(ResearchOrbItem.OrbTypes.BLUE));
    public static final RegistryObject<Item> GREEN_ORB = ITEMS.register("green_orb", ()->new ResearchOrbItem(ResearchOrbItem.OrbTypes.GREEN));
    public static final RegistryObject<Item> RED_ORB = ITEMS.register("red_orb", ()->new ResearchOrbItem(ResearchOrbItem.OrbTypes.RED));
    public static final RegistryObject<Item> REDSTONE_INLAY = ITEMS.register("redstone_inlay", ()->new BlockItem(ModBlocks.REDSTONE_INLAY.get(), ITEM_64));
    public static final RegistryObject<Item> IRON_INLAY = ITEMS.register("iron_inlay", ()->new BlockItem(ModBlocks.IRON_INLAY.get(), ITEM_64));
    public static final RegistryObject<Item> GOLD_INLAY = ITEMS.register("gold_inlay", ()->new BlockItem(ModBlocks.GOLD_INLAY.get(), ITEM_64));
    public static final RegistryObject<Item> VINTEUM_TORCH = ITEMS.register("vinteum_torch", ()->new WallOrFloorItem(ModBlocks.VINTEUM_TORCH.get(), ModBlocks.VINTEUM_WALL_TORCH.get(), ITEM_64));
    public static void register() {}
}
