package minecraftschurli.arsmagicalegacy;

import minecraftschurli.arsmagicalegacy.init.*;
import minecraftschurli.arsmagicalegacy.objects.block.craftingaltar.*;
import net.minecraft.block.*;
import net.minecraft.util.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.*;
import net.minecraftforge.fml.config.*;
import net.minecraftforge.registries.*;
import org.apache.commons.lang3.tuple.*;

import java.util.*;

/**
 * @author Minecraftschurli
 * @version 2019-12-30
 */
public class Config {
    public static class Common {
        public static final String PREFIX = ArsMagicaLegacy.MODID+".config.";
        private static final String SPLIT_CHAR = "|";
        private static final String SPLIT_REGEX = "\\|";
        final ForgeConfigSpec.ConfigValue<List<? extends String>> CRAFTING_ALTAR_MAIN_MAP;
        final ForgeConfigSpec.ConfigValue<List<? extends String>> CRAFTING_ALTAR_CAPS_MAP;

        Common(ForgeConfigSpec.Builder builder) {
            builder.comment("Common configuration settings");
            builder.push("common");
            CRAFTING_ALTAR_MAIN_MAP = builder
                    .comment("The blocks usable for the main body of the crafting altar structure")
                    .translation(PREFIX+"main")
                    .worldRestart()
                    .defineList("main", this::getMainDefault, o -> ((o instanceof String) && ((String) o).split(SPLIT_REGEX).length == 3));
            CRAFTING_ALTAR_CAPS_MAP = builder
                    .comment("The blocks usable for the caps of the crafting altar structure")
                    .translation(PREFIX+"caps")
                    .worldRestart()
                    .defineList("caps", this::getCapsDefault, o -> ((o instanceof String) && ((String) o).split(SPLIT_REGEX).length == 2));
            builder.pop();
        }

        private List<String> getCapsDefault() {
            return Arrays.asList(
                    capFromBlock(Blocks.GLASS, 1),
                    capFromBlock(Blocks.COAL_BLOCK, 2),
                    capFromBlock(Blocks.REDSTONE_BLOCK, 3),
                    capFromBlock(Blocks.IRON_BLOCK, 4),
                    capFromBlock(Blocks.LAPIS_BLOCK, 5),
                    capFromBlock(Blocks.GOLD_BLOCK, 6),
                    capFromBlock(Blocks.DIAMOND_BLOCK, 7),
                    capFromBlock(Blocks.EMERALD_BLOCK, 8),
                    capFromRegistryObject(ModBlocks.MOONSTONE_BLOCK, 9),
                    capFromRegistryObject(ModBlocks.SUNSTONE_BLOCK, 10)
            );
        }

        private String capFromRegistryObject(RegistryObject<Block> blockRegistryObject, int value) {
            return blockRegistryObject.getId().toString() + SPLIT_CHAR + value;
        }

        private String capFromBlock(Block block, int value) {
            return block.getRegistryName().toString() + SPLIT_CHAR + value;
        }

        private List<String> getMainDefault() {
            return Arrays.asList(
                    mainFromBlocks(Blocks.OAK_PLANKS, Blocks.OAK_STAIRS, 1),
                    mainFromBlocks(Blocks.ACACIA_PLANKS, Blocks.ACACIA_STAIRS, 1),
                    mainFromBlocks(Blocks.BIRCH_PLANKS, Blocks.BIRCH_STAIRS, 1),
                    mainFromBlocks(Blocks.DARK_OAK_PLANKS, Blocks.DARK_OAK_STAIRS, 1),
                    mainFromBlocks(Blocks.SPRUCE_PLANKS, Blocks.SPRUCE_STAIRS, 1),
                    mainFromBlocks(Blocks.JUNGLE_PLANKS, Blocks.JUNGLE_STAIRS, 1),
                    mainFromBlocks(Blocks.STONE_BRICKS, Blocks.STONE_BRICK_STAIRS, 1),
                    mainFromBlocks(Blocks.SANDSTONE, Blocks.SANDSTONE_STAIRS, 1),
                    mainFromBlocks(Blocks.BRICKS, Blocks.BRICK_STAIRS, 2),
                    mainFromBlocks(Blocks.RED_SANDSTONE, Blocks.RED_SANDSTONE_STAIRS, 2),
                    mainFromBlocks(ModBlocks.WITCHWOOD_PLANKS.get(), ModBlocks.WITCHWOOD_STAIRS.get(), 3),
                    mainFromBlocks(Blocks.QUARTZ_BLOCK, Blocks.QUARTZ_STAIRS, 3),
                    mainFromBlocks(Blocks.NETHER_BRICKS, Blocks.NETHER_BRICK_STAIRS, 3),
                    mainFromBlocks(Blocks.PURPUR_BLOCK, Blocks.PURPUR_STAIRS, 4)
            );
        }

        private String mainFromRegistryObjects(RegistryObject<Block> blockRegistryObject, RegistryObject<Block> stairRegistryObject, int value) {
            return blockRegistryObject.getId().toString() + SPLIT_CHAR + stairRegistryObject.getId().toString() + SPLIT_CHAR + value;
        }

        private String mainFromBlocks(Block block, Block stairs, int value) {
            return block.getRegistryName().toString() + SPLIT_CHAR + stairs.getRegistryName().toString() + SPLIT_CHAR + value;
        }

        public void parse() {
            CRAFTING_ALTAR_CAPS_MAP.get().stream().map(s -> s.split(SPLIT_REGEX)).filter(strings -> strings.length == 2).forEach(strings -> {
                try {
                    ResourceLocation rl = ResourceLocation.tryCreate(strings[0]);
                    if (rl == null)
                        return;
                    Block block = ForgeRegistries.BLOCKS.getValue(rl);
                    if (block == null)
                        return;
                    CraftingAltarStructureMaterials.addCapMaterial(block, Integer.parseInt(strings[1]));
                } catch (Exception ignored) {}
            });
            CRAFTING_ALTAR_MAIN_MAP.get().stream().map(s -> s.split(SPLIT_REGEX)).filter(strings -> strings.length == 3).forEach(strings -> {
                try {
                    ResourceLocation blockRl = ResourceLocation.tryCreate(strings[0]);
                    ResourceLocation stairRl = ResourceLocation.tryCreate(strings[1]);
                    if (blockRl == null || stairRl == null)
                        return;
                    Block block = ForgeRegistries.BLOCKS.getValue(blockRl);
                    Block stair = ForgeRegistries.BLOCKS.getValue(stairRl);
                    if (block == null || stair == null)
                        return;
                    CraftingAltarStructureMaterials.addMainMaterial(block, stair, Integer.parseInt(strings[2]));
                } catch (Exception ignored) {}
            });
        }
    }

    static final ForgeConfigSpec commonSpec;
    public static final Common COMMON;
    static {
        final Pair<Common, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Common::new);
        commonSpec = specPair.getRight();
        COMMON = specPair.getLeft();
    }

    static void reload(ModConfig.ModConfigEvent event) {
        switch (event.getConfig().getType()) {
            case CLIENT:
                break;
            case COMMON:
                COMMON.parse();
                break;
            case SERVER:
                break;
        }
    }
}
