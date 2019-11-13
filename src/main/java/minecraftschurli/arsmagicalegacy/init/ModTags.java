package minecraftschurli.arsmagicalegacy.init;

import minecraftschurli.arsmagicalegacy.ArsMagicaLegacy;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;

/**
 * @author Minecraftschurli
 * @version 2019-11-13
 */
public class ModTags {
    public static final class Blocks {
        public static final Tag<Block> STORAGE_BLOCKS_TOPAZ = forgeTag("storage_blocks/topaz");
        public static final Tag<Block> STORAGE_BLOCKS_CHIMERITE = forgeTag("storage_blocks/chimerite");
        public static final Tag<Block> STORAGE_BLOCKS_MOONSTONE = forgeTag("storage_blocks/moonstone");
        public static final Tag<Block> STORAGE_BLOCKS_SUNSTONE = forgeTag("storage_blocks/sunstone");
        public static final Tag<Block> STORAGE_BLOCKS_VINTEUM = forgeTag("storage_blocks/vinteum");

        public static final Tag<Block> ORES_TOPAZ = forgeTag("ores/topaz");
        public static final Tag<Block> ORES_CHIMERITE = forgeTag("ores/chimerite");
        public static final Tag<Block> ORES_MOONSTONE = forgeTag("ores/moonstone");
        public static final Tag<Block> ORES_SUNSTONE = forgeTag("ores/sunstone");
        public static final Tag<Block> ORES_VINTEUM = forgeTag("ores/vinteum");
        public static final Tag<Block> WITCHWOOD_LOGS = tag("witchwood_logs");

        private static Tag<Block> forgeTag(String name) {
            return new BlockTags.Wrapper(new ResourceLocation("forge", name));
        }
        private static Tag<Block> tag(String name) {
            return new BlockTags.Wrapper(new ResourceLocation(ArsMagicaLegacy.MODID, name));
        }
    }
    public static final class Items {
        public static final Tag<Item> GEMS_TOPAZ = forgeTag("gems/topaz");
        public static final Tag<Item> GEMS_CHIMERITE = forgeTag("gems/chimerite");
        public static final Tag<Item> GEMS_MOONSTONE = forgeTag("gems/moonstone");
        public static final Tag<Item> GEMS_SUNSTONE = forgeTag("gems/sunstone");

        public static final Tag<Item> DUSTS_VINTEUM = forgeTag("dusts/vinteum");

        public static final Tag<Item> STORAGE_BLOCKS_TOPAZ = forgeTag("storage_blocks/topaz");
        public static final Tag<Item> STORAGE_BLOCKS_CHIMERITE = forgeTag("storage_blocks/chimerite");
        public static final Tag<Item> STORAGE_BLOCKS_MOONSTONE = forgeTag("storage_blocks/moonstone");
        public static final Tag<Item> STORAGE_BLOCKS_SUNSTONE = forgeTag("storage_blocks/sunstone");
        public static final Tag<Item> STORAGE_BLOCKS_VINTEUM = forgeTag("storage_blocks/vinteum");

        public static final Tag<Item> ORES_TOPAZ = forgeTag("ores/topaz");
        public static final Tag<Item> ORES_CHIMERITE = forgeTag("ores/chimerite");
        public static final Tag<Item> ORES_MOONSTONE = forgeTag("ores/moonstone");
        public static final Tag<Item> ORES_SUNSTONE = forgeTag("ores/sunstone");
        public static final Tag<Item> ORES_VINTEUM = forgeTag("ores/vinteum");
        public static final Tag<Item> WITCHWOOD_LOGS = tag("logs/witchwood");

        private static Tag<Item> forgeTag(String name) {
            return new ItemTags.Wrapper(new ResourceLocation("forge", name));
        }
        private static Tag<Item> tag(String name) {
            return new ItemTags.Wrapper(new ResourceLocation(ArsMagicaLegacy.MODID, name));
        }
    }
}
