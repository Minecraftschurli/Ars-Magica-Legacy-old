package minecraftschurli.arsmagicalegacy.init;

import minecraftschurli.arsmagicalegacy.ArsMagicaLegacy;
import minecraftschurli.arsmagicalegacy.objects.item.ArcaneCompendiumItem;
import minecraftschurli.arsmagicalegacy.objects.item.ResearchOrbItem;
import minecraftschurli.arsmagicalegacy.objects.item.SpellItem;
import minecraftschurli.arsmagicalegacy.objects.item.spellbook.SpellBookItem;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.TallBlockItem;
import net.minecraftforge.fml.RegistryObject;

import java.util.Objects;

/**
 * @author Minecraftschurli
 * @version 2019-11-07
 */
public final class ModItems implements IInit {
    public static final Item.Properties ITEM_64 = new Item.Properties().group(ArsMagicaLegacy.ITEM_GROUP).maxStackSize(64);
    public static final Item.Properties ITEM_1 = new Item.Properties().group(ArsMagicaLegacy.ITEM_GROUP).maxStackSize(1);

//    public static final RegistryObject<Item> TEST_ITEM = ITEMS.register("test", () -> new Item(new Item.Properties().maxStackSize(1).food(new Food.Builder().effect(new EffectInstance(ModEffects.MANA_REGEN.get(), 10), 1).fastToEat().setAlwaysEdible().build())));

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
    public static final RegistryObject<Item> WITCHWOOD_DOOR = ITEMS.register("witchwood_door", () -> new TallBlockItem(ModBlocks.WITCHWOOD_DOOR.get(), ITEM_64));
    public static final RegistryObject<Item> WITCHWOOD_TRAPDOOR = blockItem(ModBlocks.WITCHWOOD_TRAPDOOR);
    public static final RegistryObject<Item> WITCHWOOD_BUTTON = blockItem(ModBlocks.WITCHWOOD_BUTTON);
    public static final RegistryObject<Item> WITCHWOOD_PRESSURE_PLATE = blockItem(ModBlocks.WITCHWOOD_PRESSURE_PLATE);
    
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

    public static final RegistryObject<Item> BLUE_ORB = ITEMS.register("blue_orb", () -> new ResearchOrbItem(ResearchOrbItem.OrbTypes.BLUE));
    public static final RegistryObject<Item> GREEN_ORB = ITEMS.register("green_orb", () -> new ResearchOrbItem(ResearchOrbItem.OrbTypes.GREEN));
    public static final RegistryObject<Item> RED_ORB = ITEMS.register("red_orb", () -> new ResearchOrbItem(ResearchOrbItem.OrbTypes.RED));

    public static void register() {}

    private static RegistryObject<Item> blockItem(final RegistryObject<Block> block) {
        Objects.requireNonNull(block);
        return ITEMS.register(block.getId().getPath(), () -> new BlockItem(block.get(), ITEM_64));
    }

    private static RegistryObject<Item> stackableItem64(final String name) {
        Objects.requireNonNull(name);
        return ITEMS.register(name, ModItems::item64);
    }
    
    private static Item item64() {
        return new Item(ITEM_64);
    }
}
