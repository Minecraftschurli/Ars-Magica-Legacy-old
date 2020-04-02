package minecraftschurli.arsmagicalegacy.api.config;

import minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Arrays;
import java.util.List;

/**
 * @author Minecraftschurli
 * @version 2019-12-30
 */
public class Config {
    public static final String PREFIX = ArsMagicaAPI.MODID + ".config.";
    private static final String SPLIT_CHAR = "|";
    private static final String SPLIT_REGEX = "\\|";

    public static final Common COMMON;
    static final ForgeConfigSpec commonSpec;

    static {
        final Pair<Common, ForgeConfigSpec> commonPair = new ForgeConfigSpec.Builder().configure(Common::new);
        commonSpec = commonPair.getRight();
        COMMON = commonPair.getLeft();
    }

    @SubscribeEvent
    static void reload(ModConfig.ModConfigEvent event) {
        switch (event.getConfig().getType()) {
            case COMMON:
                COMMON.parse();
                break;
            case CLIENT:
            case SERVER:
                break;
        }
    }

    public static void setup() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.commonSpec);
    }

    public static class Common {
        public final ForgeConfigSpec.IntValue DEFAULT_MAX_BURNOUT;
        public final ForgeConfigSpec.IntValue DEFAULT_MAX_MANA;
        final ForgeConfigSpec.ConfigValue<List<? extends String>> CRAFTING_ALTAR_MAIN_MAP;
        final ForgeConfigSpec.ConfigValue<List<? extends String>> CRAFTING_ALTAR_CAPS_MAP;

        Common(ForgeConfigSpec.Builder builder) {
            builder.comment("Common configuration settings");
            builder.push("common");
            builder.push("craftingaltar");
            CRAFTING_ALTAR_MAIN_MAP = builder
                    .comment("The blocks usable for the main body of the crafting altar structure")
                    .translation(PREFIX + "altarstructure.main")
                    .worldRestart()
                    .defineList("main", this::getDefaultMain, o -> ((o instanceof String) && ((String) o).split(SPLIT_REGEX).length == 3));
            CRAFTING_ALTAR_CAPS_MAP = builder
                    .comment("The blocks usable for the caps of the crafting altar structure")
                    .translation(PREFIX + "altarstructure.caps")
                    .worldRestart()
                    .defineList("caps", this::getDefaultCaps, o -> ((o instanceof String) && ((String) o).split(SPLIT_REGEX).length == 2));
            builder.pop();
            builder.push("magicvalues");
            DEFAULT_MAX_MANA = builder
                    .comment("The default maximum mana for the player")
                    .translation(PREFIX + "maxmana")
                    .worldRestart()
                    .defineInRange("maxmana", 100, 0, 10000);
            DEFAULT_MAX_BURNOUT = builder
                    .comment("The default maximum burnout for the player")
                    .translation(PREFIX + "maxburnout")
                    .worldRestart()
                    .defineInRange("maxburnout", 100, 0, 10000);
            builder.pop();
            builder.pop();
        }

        private List<String> getDefaultCaps() {
            return Arrays.asList(
                    capFromBlock(Blocks.GLASS, 1),
                    capFromBlock(Blocks.COAL_BLOCK, 2),
                    capFromBlock(Blocks.REDSTONE_BLOCK, 3),
                    capFromBlock(Blocks.IRON_BLOCK, 4),
                    capFromBlock(Blocks.LAPIS_BLOCK, 5),
                    capFromBlock(Blocks.GOLD_BLOCK, 6),
                    capFromBlock(Blocks.DIAMOND_BLOCK, 7),
                    capFromBlock(Blocks.EMERALD_BLOCK, 8),
                    capFromBlock(ForgeRegistries.BLOCKS.getValue(new ResourceLocation(ArsMagicaAPI.MODID, "moonstone_block")), 9),
                    capFromBlock(ForgeRegistries.BLOCKS.getValue(new ResourceLocation(ArsMagicaAPI.MODID, "sunstone_block")), 10)
            );
        }

        private String capFromBlock(Block block, int value) {
            return block.getRegistryName().toString() + SPLIT_CHAR + value;
        }

        private List<String> getDefaultMain() {
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
                    mainFromBlocks(ForgeRegistries.BLOCKS.getValue(new ResourceLocation(ArsMagicaAPI.MODID, "witchwood_planks")), ForgeRegistries.BLOCKS.getValue(new ResourceLocation(ArsMagicaAPI.MODID, "witchwood_stairs")), 3),
                    mainFromBlocks(Blocks.QUARTZ_BLOCK, Blocks.QUARTZ_STAIRS, 3),
                    mainFromBlocks(Blocks.NETHER_BRICKS, Blocks.NETHER_BRICK_STAIRS, 3),
                    mainFromBlocks(Blocks.PURPUR_BLOCK, Blocks.PURPUR_STAIRS, 4)
            );
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
                } catch (Exception ignored) {
                }
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
                } catch (Exception ignored) {
                }
            });
        }
    }
}
