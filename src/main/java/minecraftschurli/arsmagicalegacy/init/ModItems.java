package minecraftschurli.arsmagicalegacy.init;

import minecraftschurli.arsmagicalegacy.ArsMagicaLegacy;
import minecraftschurli.arsmagicalegacy.objects.item.ArcaneCompendiumItem;
import minecraftschurli.arsmagicalegacy.objects.item.InfinityOrbItem;
import minecraftschurli.arsmagicalegacy.objects.item.SpellItem;
import minecraftschurli.arsmagicalegacy.objects.item.spellbook.SpellBookItem;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.TallBlockItem;
import net.minecraft.item.WallOrFloorItem;
import net.minecraftforge.fml.RegistryObject;

import java.util.Objects;

/**
 * @author Minecraftschurli
 * @version 2019-11-07
 */
public final class ModItems implements IInit {
    public static final Item.Properties ITEM_64 = new Item.Properties().group(ArsMagicaLegacy.ITEM_GROUP).maxStackSize(64);
    public static final Item.Properties ITEM_1 = new Item.Properties().group(ArsMagicaLegacy.ITEM_GROUP).maxStackSize(1);
    public static final RegistryObject<Item> ARCANE_COMPENDIUM = ITEMS.register("arcane_compendium", ArcaneCompendiumItem::new);
    public static final RegistryObject<Item> SPELL = ITEMS.register("spell", SpellItem::new);
    public static final RegistryObject<Item> SPELL_BOOK = ITEMS.register("spell_book", SpellBookItem::new);
    public static final RegistryObject<Item> CHIMERITE_ORE = blockItem(ModBlocks.CHIMERITE_ORE);
    public static final RegistryObject<Item> CHIMERITE_BLOCK = blockItem(ModBlocks.CHIMERITE_BLOCK);
    public static final RegistryObject<Item> CHIMERITE = stackableItem64("chimerite");
    public static final RegistryObject<Item> TOPAZ_ORE = blockItem(ModBlocks.TOPAZ_ORE);
    public static final RegistryObject<Item> TOPAZ_BLOCK = blockItem(ModBlocks.TOPAZ_BLOCK);
    public static final RegistryObject<Item> TOPAZ = stackableItem64("topaz");
    public static final RegistryObject<Item> VINTEUM_ORE = blockItem(ModBlocks.VINTEUM_ORE);
    public static final RegistryObject<Item> VINTEUM_BLOCK = blockItem(ModBlocks.VINTEUM_BLOCK);
    public static final RegistryObject<Item> VINTEUM = stackableItem64("vinteum");
    public static final RegistryObject<Item> MOONSTONE_ORE = blockItem(ModBlocks.MOONSTONE_ORE);
    public static final RegistryObject<Item> MOONSTONE_BLOCK = blockItem(ModBlocks.MOONSTONE_BLOCK);
    public static final RegistryObject<Item> MOONSTONE = stackableItem64("moonstone");
    public static final RegistryObject<Item> SUNSTONE_ORE = blockItem(ModBlocks.SUNSTONE_ORE);
    public static final RegistryObject<Item> SUNSTONE_BLOCK = blockItem(ModBlocks.SUNSTONE_BLOCK);
    public static final RegistryObject<Item> SUNSTONE = stackableItem64("sunstone");
    public static final RegistryObject<Item> ALTAR_CORE = blockItem(ModBlocks.ALTAR_CORE);
    public static final RegistryObject<Item> MAGIC_WALL = blockItem(ModBlocks.MAGIC_WALL);
    public static final RegistryObject<Item> WITCHWOOD_LOG = blockItem(ModBlocks.WITCHWOOD_LOG);
    public static final RegistryObject<Item> WITCHWOOD_WOOD = blockItem(ModBlocks.WITCHWOOD_WOOD);
    public static final RegistryObject<Item> STRIPPED_WITCHWOOD_LOG = blockItem(ModBlocks.STRIPPED_WITCHWOOD_LOG);
    public static final RegistryObject<Item> STRIPPED_WITCHWOOD_WOOD = blockItem(ModBlocks.STRIPPED_WITCHWOOD_WOOD);
    public static final RegistryObject<Item> WITCHWOOD_PLANKS = blockItem(ModBlocks.WITCHWOOD_PLANKS);
    public static final RegistryObject<Item> WITCHWOOD_SLAB = blockItem(ModBlocks.WITCHWOOD_SLAB);
    public static final RegistryObject<Item> WITCHWOOD_STAIRS = blockItem(ModBlocks.WITCHWOOD_STAIRS);
    public static final RegistryObject<Item> WITCHWOOD_LEAVES = blockItem(ModBlocks.WITCHWOOD_LEAVES);
    public static final RegistryObject<Item> WITCHWOOD_FENCE = blockItem(ModBlocks.WITCHWOOD_FENCE);
    public static final RegistryObject<Item> WITCHWOOD_FENCE_GATE = blockItem(ModBlocks.WITCHWOOD_FENCE_GATE);
    public static final RegistryObject<Item> WITCHWOOD_DOOR = ITEMS.register("witchwood_door", ()->new TallBlockItem(ModBlocks.WITCHWOOD_DOOR.get(), ITEM_64));
    public static final RegistryObject<Item> WITCHWOOD_TRAPDOOR = blockItem(ModBlocks.WITCHWOOD_TRAPDOOR);
    public static final RegistryObject<Item> WITCHWOOD_BUTTON = blockItem(ModBlocks.WITCHWOOD_BUTTON);
    public static final RegistryObject<Item> WITCHWOOD_PRESSURE_PLATE = blockItem(ModBlocks.WITCHWOOD_PRESSURE_PLATE);
    public static final RegistryObject<Item> WITCHWOOD_SAPLING = blockItem(ModBlocks.WITCHWOOD_SAPLING);
    public static final RegistryObject<Item> AUM = blockItem(ModBlocks.AUM);
    public static final RegistryObject<Item> CERUBLOSSOM = blockItem(ModBlocks.CERUBLOSSOM);
    public static final RegistryObject<Item> DESERT_NOVA = blockItem(ModBlocks.DESERT_NOVA);
    public static final RegistryObject<Item> TARMA_ROOT = blockItem(ModBlocks.TARMA_ROOT);
    public static final RegistryObject<Item> WAKEBLOOM = blockItem(ModBlocks.WAKEBLOOM);
    public static final RegistryObject<Item> REDSTONE_INLAY = blockItem(ModBlocks.REDSTONE_INLAY);
    public static final RegistryObject<Item> IRON_INLAY = blockItem(ModBlocks.IRON_INLAY);
    public static final RegistryObject<Item> GOLD_INLAY = blockItem(ModBlocks.GOLD_INLAY);
    public static final RegistryObject<Item> VINTEUM_TORCH = ITEMS.register("vinteum_torch", ()->new WallOrFloorItem(ModBlocks.VINTEUM_TORCH.get(), ModBlocks.VINTEUM_WALL_TORCH.get(), ITEM_64));
    public static final RegistryObject<Item> RUNE = stackableItem64("rune");
    public static final RegistryObject<Item> WHITE_RUNE = stackableItem64("white_rune");
    public static final RegistryObject<Item> ORANGE_RUNE = stackableItem64("orange_rune");
    public static final RegistryObject<Item> MAGENTA_RUNE = stackableItem64("magenta_rune");
    public static final RegistryObject<Item> LIGHT_BLUE_RUNE = stackableItem64("light_blue_rune");
    public static final RegistryObject<Item> YELLOW_RUNE = stackableItem64("yellow_rune");
    public static final RegistryObject<Item> LIME_RUNE = stackableItem64("lime_rune");
    public static final RegistryObject<Item> PINK_RUNE = stackableItem64("pink_rune");
    public static final RegistryObject<Item> GRAY_RUNE = stackableItem64("gray_rune");
    public static final RegistryObject<Item> LIGHT_GRAY_RUNE = stackableItem64("light_gray_rune");
    public static final RegistryObject<Item> CYAN_RUNE = stackableItem64("cyan_rune");
    public static final RegistryObject<Item> PURPLE_RUNE = stackableItem64("purple_rune");
    public static final RegistryObject<Item> BLUE_RUNE = stackableItem64("blue_rune");
    public static final RegistryObject<Item> BROWN_RUNE = stackableItem64("brown_rune");
    public static final RegistryObject<Item> GREEN_RUNE = stackableItem64("green_rune");
    public static final RegistryObject<Item> RED_RUNE = stackableItem64("red_rune");
    public static final RegistryObject<Item> BLACK_RUNE = stackableItem64("black_rune");
    public static final RegistryObject<Item> INFINITY_ORB = ITEMS.register("blue_orb", InfinityOrbItem::new);
    public static final RegistryObject<Item> PURIFIED_VINTEUM = stackableItem64("purified_vinteum");
    public static final RegistryObject<Item> ARCANE_ASH = stackableItem64("arcane_ash");
    public static final RegistryObject<Item> ARCANE_COMPOUND = stackableItem64("arcane_compound");
    public static final RegistryObject<Item> PIG_FAT = stackableItem64("pig_fat");
    public static final RegistryObject<Item> SPELL_PARCHMENT = stackableItem64("spell_parchment");
    public static final RegistryObject<Item> WOODEN_LEG = stackableItem64("wooden_leg");
    public static final RegistryObject<Item> WATER_ESSENCE = stackableItem64("water_essence");
    public static final RegistryObject<Item> FIRE_ESSENCE = stackableItem64("fire_essence");
    public static final RegistryObject<Item> EARTH_ESSENCE = stackableItem64("earth_essence");
    public static final RegistryObject<Item> AIR_ESSENCE = stackableItem64("air_essence");
    public static final RegistryObject<Item> LIGHTNING_ESSENCE = stackableItem64("lightning_essence");
    public static final RegistryObject<Item> ICE_ESSENCE = stackableItem64("ice_essence");
    public static final RegistryObject<Item> NATURE_ESSENCE = stackableItem64("nature_essence");
    public static final RegistryObject<Item> LIFE_ESSENCE = stackableItem64("life_essence");
    public static final RegistryObject<Item> ARCANE_ESSENCE = stackableItem64("arcane_essence");
    public static final RegistryObject<Item> ENDER_ESSENCE = stackableItem64("ender_essence");
    public static final RegistryObject<Item> LESSER_FOCUS = stackableItem64("lesser_focus");
    public static final RegistryObject<Item> STANDARD_FOCUS = stackableItem64("standard_focus");
    public static final RegistryObject<Item> GREATER_FOCUS = stackableItem64("greater_focus");
    public static final RegistryObject<Item> MANA_FOCUS = stackableItem64("mana_focus");
    public static final RegistryObject<Item> PLAYER_FOCUS = stackableItem64("player_focus");
    public static final RegistryObject<Item> CREATURE_FOCUS = stackableItem64("creature_focus");
    public static final RegistryObject<Item> MONSTER_FOCUS = stackableItem64("monster_focus");
    public static final RegistryObject<Item> CHARGE_FOCUS = stackableItem64("charge_focus");
    public static final RegistryObject<Item> ITEM_FOCUS = stackableItem64("item_focus");
    public static void register() {}
    private static RegistryObject<Item> blockItem(final RegistryObject<Block> block) {
        Objects.requireNonNull(block);
        return ITEMS.register(block.getId().getPath(), ()->new BlockItem(block.get(), ITEM_64));
    }
    private static RegistryObject<Item> stackableItem64(final String name) {
        Objects.requireNonNull(name);
        return ITEMS.register(name, ModItems::item64);
    }
    private static Item item64() {
        return new Item(ITEM_64);
    }
}
