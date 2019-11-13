package minecraftschurli.arsmagicalegacy.init;

import minecraftschurli.arsmagicalegacy.ArsMagicaLegacy;
import minecraftschurli.arsmagicalegacy.objects.item.ArcaneCompendiumItem;
import minecraftschurli.arsmagicalegacy.objects.item.spellbook.SpellBookItem;
import minecraftschurli.arsmagicalegacy.objects.item.SpellItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;

/**
 * @author Minecraftschurli
 * @version 2019-11-07
 */
public class Items implements Registries {
    public static final Item.Properties ITEM_64 = new Item.Properties().group(ArsMagicaLegacy.ITEM_GROUP).maxStackSize(64);
    public static final Item.Properties ITEM_1 = new Item.Properties().group(ArsMagicaLegacy.ITEM_GROUP).maxStackSize(1);
    
    public static final RegistryObject<Item> ARCANE_COMPENDIUM = ITEMS.register("arcane_compendium", ArcaneCompendiumItem::new);
    public static final RegistryObject<Item> SPELL = ITEMS.register("spell", SpellItem::new);
    public static final RegistryObject<Item> SPELL_BOOK = ITEMS.register("spell_book", SpellBookItem::new);

    public static final RegistryObject<Item> CHIMERITE_ORE = ITEMS.register("chimerite_ore", ()->new BlockItem(Blocks.CHIMERITE_ORE.get(), ITEM_64));
    public static final RegistryObject<Item> CHIMERITE_BLOCK = ITEMS.register("chimerite_block", ()->new BlockItem(Blocks.CHIMERITE_BLOCK.get(), ITEM_64));
    public static final RegistryObject<Item> CHIMERITE = ITEMS.register("chimerite", ()->new Item(ITEM_64));
    
    public static final RegistryObject<Item> TOPAZ_ORE = ITEMS.register("topaz_ore", ()->new BlockItem(Blocks.TOPAZ_ORE.get(), ITEM_64));
    public static final RegistryObject<Item> TOPAZ_BLOCK = ITEMS.register("topaz_block", ()->new BlockItem(Blocks.TOPAZ_BLOCK.get(), ITEM_64));
    public static final RegistryObject<Item> TOPAZ = ITEMS.register("topaz", ()->new Item(ITEM_64));
    
    public static final RegistryObject<Item> VINTEUM_ORE = ITEMS.register("vinteum_ore", ()->new BlockItem(Blocks.VINTEUM_ORE.get(), ITEM_64));
    public static final RegistryObject<Item> VINTEUM_BLOCK = ITEMS.register("vinteum_block", ()->new BlockItem(Blocks.VINTEUM_BLOCK.get(), ITEM_64));
    public static final RegistryObject<Item> VINTEUM = ITEMS.register("vinteum", ()->new Item(ITEM_64));
    
    public static final RegistryObject<Item> MOONSTONE_ORE = ITEMS.register("moonstone_ore", ()->new BlockItem(Blocks.MOONSTONE_ORE.get(), ITEM_64));
    public static final RegistryObject<Item> MOONSTONE_BLOCK = ITEMS.register("moonstone_block", ()->new BlockItem(Blocks.MOONSTONE_BLOCK.get(), ITEM_64));
    public static final RegistryObject<Item> MOONSTONE = ITEMS.register("moonstone", ()->new Item(ITEM_64));

    public static final RegistryObject<Item> SUNSTONE_ORE = ITEMS.register("sunstone_ore", ()->new BlockItem(Blocks.SUNSTONE_ORE.get(), ITEM_64));
    public static final RegistryObject<Item> SUNSTONE_BLOCK = ITEMS.register("sunstone_block", ()->new BlockItem(Blocks.SUNSTONE_BLOCK.get(), ITEM_64));
    public static final RegistryObject<Item> SUNSTONE = ITEMS.register("sunstone", ()->new Item(ITEM_64));

    public static final RegistryObject<Item> ALTAR_CORE = ITEMS.register("altar_core", ()->new BlockItem(Blocks.ALTAR_CORE.get(), ITEM_64));
    public static final RegistryObject<Item> MAGIC_WALL = ITEMS.register("magic_wall", ()->new BlockItem(Blocks.MAGIC_WALL.get(), ITEM_64));

    public static final RegistryObject<Item> WITCHWOOD_LOG = ITEMS.register("witchwood_log", ()->new BlockItem(Blocks.WITCHWOOD_LOG.get(), ITEM_64));
    public static final RegistryObject<Item> WITCHWOOD_PLANKS = ITEMS.register("witchwood_planks", ()->new BlockItem(Blocks.WITCHWOOD_PLANKS.get(), ITEM_64));
    public static final RegistryObject<Item> WITCHWOOD_SLAB = ITEMS.register("witchwood_slab", ()->new BlockItem(Blocks.WITCHWOOD_SLAB.get(), ITEM_64));
    public static final RegistryObject<Item> WITCHWOOD_STAIRS = ITEMS.register("witchwood_stairs", ()->new BlockItem(Blocks.WITCHWOOD_STAIRS.get(), ITEM_64));
    public static final RegistryObject<Item> WITCHWOOD_LEAVES = ITEMS.register("witchwood_leaves", ()->new BlockItem(Blocks.WITCHWOOD_LEAVES.get(), ITEM_64));
    public static final RegistryObject<Item> WITCHWOOD_FENCE = ITEMS.register("witchwood_fence", ()->new BlockItem(Blocks.WITCHWOOD_FENCE.get(), ITEM_64));
    public static final RegistryObject<Item> WITCHWOOD_FENCE_GATE = ITEMS.register("witchwood_fence_gate", ()->new BlockItem(Blocks.WITCHWOOD_FENCE_GATE.get(), ITEM_64));
    public static final RegistryObject<Item> WITCHWOOD_DOOR = ITEMS.register("witchwood_door", ()->new BlockItem(Blocks.WITCHWOOD_DOOR.get(), ITEM_64));
    public static final RegistryObject<Item> WITCHWOOD_TRAPDOOR = ITEMS.register("witchwood_trapdoor", ()->new BlockItem(Blocks.WITCHWOOD_TRAPDOOR.get(), ITEM_64));
    public static final RegistryObject<Item> WITCHWOOD_BUTTON = ITEMS.register("witchwood_button", ()->new BlockItem(Blocks.WITCHWOOD_BUTTON.get(), ITEM_64));
    public static final RegistryObject<Item> WITCHWOOD_PRESSURE_PLATE = ITEMS.register("witchwood_pressure_plate", ()->new BlockItem(Blocks.WITCHWOOD_PRESSURE_PLATE.get(), ITEM_64));
    public static void register() {}
}
