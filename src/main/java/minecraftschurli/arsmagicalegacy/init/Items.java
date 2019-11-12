package minecraftschurli.arsmagicalegacy.init;

import minecraftschurli.arsmagicalegacy.ArsMagicaLegacy;
import minecraftschurli.arsmagicalegacy.objects.item.spellbook.SpellBookItem;
import minecraftschurli.arsmagicalegacy.objects.item.SpellItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.Rarity;
import net.minecraftforge.fml.RegistryObject;

/**
 * @author Minecraftschurli
 * @version 2019-11-07
 */
public class Items implements Registries {
    public static final RegistryObject<Item> SPELL = ITEMS.register("spell", ()->new SpellItem(new Item.Properties().maxStackSize(1)));
    public static final RegistryObject<Item> SPELL_BOOK = ITEMS.register("spell_book", ()->new SpellBookItem(new Item.Properties().group(ArsMagicaLegacy.ITEM_GROUP).maxStackSize(1).rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> CHIMERITE = ITEMS.register("chimerite", ()->new Item(new Item.Properties().group(ArsMagicaLegacy.ITEM_GROUP).maxStackSize(64)));
    public static final RegistryObject<Item> TOPAZ = ITEMS.register("topaz", ()->new Item(new Item.Properties().group(ArsMagicaLegacy.ITEM_GROUP).maxStackSize(64)));
    public static final RegistryObject<Item> VINTEUM = ITEMS.register("vinteum", ()->new Item(new Item.Properties().group(ArsMagicaLegacy.ITEM_GROUP).maxStackSize(64)));
    public static final RegistryObject<Item> MOONSTONE = ITEMS.register("moonstone", ()->new Item(new Item.Properties().group(ArsMagicaLegacy.ITEM_GROUP).maxStackSize(64)));
    public static final RegistryObject<Item> SUNSTONE = ITEMS.register("sunstone", ()->new Item(new Item.Properties().group(ArsMagicaLegacy.ITEM_GROUP).maxStackSize(64)));
    public static final RegistryObject<Item> CHIMERITE_ORE = ITEMS.register("chimerite_ore", ()->new BlockItem(Blocks.CHIMERITE_ORE.get(), new Item.Properties().group(ArsMagicaLegacy.ITEM_GROUP).maxStackSize(64)));
    public static final RegistryObject<Item> TOPAZ_ORE = ITEMS.register("topaz_ore", ()->new BlockItem(Blocks.TOPAZ_ORE.get(), new Item.Properties().group(ArsMagicaLegacy.ITEM_GROUP).maxStackSize(64)));
    public static final RegistryObject<Item> VINTEUM_ORE = ITEMS.register("vinteum_ore", ()->new BlockItem(Blocks.VINTEUM_ORE.get(), new Item.Properties().group(ArsMagicaLegacy.ITEM_GROUP).maxStackSize(64)));
    public static final RegistryObject<Item> MOONSTONE_ORE = ITEMS.register("moonstone_ore", ()->new BlockItem(Blocks.MOONSTONE_ORE.get(), new Item.Properties().group(ArsMagicaLegacy.ITEM_GROUP).maxStackSize(64)));
    public static final RegistryObject<Item> SUNSTONE_ORE = ITEMS.register("sunstone_ore", ()->new BlockItem(Blocks.SUNSTONE_ORE.get(), new Item.Properties().group(ArsMagicaLegacy.ITEM_GROUP).maxStackSize(64)));
    public static final RegistryObject<Item> CHIMERITE_BLOCK = ITEMS.register("chimerite_block", ()->new BlockItem(Blocks.CHIMERITE_BLOCK.get(), new Item.Properties().group(ArsMagicaLegacy.ITEM_GROUP).maxStackSize(64)));
    public static final RegistryObject<Item> TOPAZ_BLOCK = ITEMS.register("topaz_block", ()->new BlockItem(Blocks.TOPAZ_BLOCK.get(), new Item.Properties().group(ArsMagicaLegacy.ITEM_GROUP).maxStackSize(64)));
    public static final RegistryObject<Item> VINTEUM_BLOCK = ITEMS.register("vinteum_block", ()->new BlockItem(Blocks.VINTEUM_BLOCK.get(), new Item.Properties().group(ArsMagicaLegacy.ITEM_GROUP).maxStackSize(64)));
    public static final RegistryObject<Item> MOONSTONE_BLOCK = ITEMS.register("moonstone_block", ()->new BlockItem(Blocks.MOONSTONE_BLOCK.get(), new Item.Properties().group(ArsMagicaLegacy.ITEM_GROUP).maxStackSize(64)));
    public static final RegistryObject<Item> SUNSTONE_BLOCK = ITEMS.register("sunstone_block", ()->new BlockItem(Blocks.SUNSTONE_BLOCK.get(), new Item.Properties().group(ArsMagicaLegacy.ITEM_GROUP).maxStackSize(64)));
    public static final RegistryObject<Item> WITCHWOOD_LOG = ITEMS.register("witchwood_log", ()->new BlockItem(Blocks.WITCHWOOD_LOG.get(), new Item.Properties().group(ArsMagicaLegacy.ITEM_GROUP).maxStackSize(64)));
    public static final RegistryObject<Item> WITCHWOOD_PLANKS = ITEMS.register("witchwood_planks", ()->new BlockItem(Blocks.WITCHWOOD_PLANKS.get(), new Item.Properties().group(ArsMagicaLegacy.ITEM_GROUP).maxStackSize(64)));
    public static final RegistryObject<Item> WITCHWOOD_SLAB = ITEMS.register("witchwood_slab", ()->new BlockItem(Blocks.WITCHWOOD_SLAB.get(), new Item.Properties().group(ArsMagicaLegacy.ITEM_GROUP).maxStackSize(64)));
    public static final RegistryObject<Item> WITCHWOOD_STAIRS = ITEMS.register("witchwood_stairs", ()->new BlockItem(Blocks.WITCHWOOD_STAIRS.get(), new Item.Properties().group(ArsMagicaLegacy.ITEM_GROUP).maxStackSize(64)));
    public static final RegistryObject<Item> WITCHWOOD_LEAVES = ITEMS.register("witchwood_leaves", ()->new BlockItem(Blocks.WITCHWOOD_LEAVES.get(), new Item.Properties().group(ArsMagicaLegacy.ITEM_GROUP).maxStackSize(64)));
    public static final RegistryObject<Item> MAGIC_WALL = ITEMS.register("magic_wall", ()->new BlockItem(Blocks.MAGIC_WALL.get(), new Item.Properties().group(ArsMagicaLegacy.ITEM_GROUP).maxStackSize(64)));
    public static void register() {}
}
