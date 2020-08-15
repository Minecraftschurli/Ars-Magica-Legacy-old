package minecraftschurli.arsmagicalegacy.api.config;

import java.util.Arrays;
import java.util.List;
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

/**
 * @author Minecraftschurli
 * @version 2019-12-30
 */
public class Config {
    public static final String PREFIX = ArsMagicaAPI.MODID + ".config.";
    private static final String SPLIT_CHAR = "|";
    private static final String SPLIT_REGEX = "\\|";
    public static final Server SERVER;
    static final ForgeConfigSpec serverSpec;

    static {
        final Pair<Server, ForgeConfigSpec> serverPair = new ForgeConfigSpec.Builder().configure(Server::new);
        serverSpec = serverPair.getRight();
        SERVER = serverPair.getLeft();
    }

    @SubscribeEvent
    static void reload(ModConfig.ModConfigEvent event) {
        switch (event.getConfig().getType()) {
            case COMMON:
            case CLIENT:
                break;
            case SERVER:
                SERVER.parse();
                break;
        }
    }

    public static void setup() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, Config.serverSpec);
    }

    public static class Server {
        final ForgeConfigSpec.ConfigValue<List<? extends String>> CRAFTING_ALTAR_MAIN_MAP;
        final ForgeConfigSpec.ConfigValue<List<? extends String>> CRAFTING_ALTAR_CAPS_MAP;
        public final ForgeConfigSpec.DoubleValue DEFAULT_MAX_BURNOUT;
        public final ForgeConfigSpec.DoubleValue DEFAULT_MAX_MANA;
        public final ForgeConfigSpec.DoubleValue MANA_BURNOUT_RATIO;
        public final ForgeConfigSpec.IntValue LAKE_FREQUENCY;
        public final ForgeConfigSpec.BooleanValue DESERT_NOVA_ENABLED;
        public final ForgeConfigSpec.BooleanValue OTHER_FLOWERS_ENABLED;
        public final ForgeConfigSpec.IntValue METEOR_FREQUENCY;
        public final ForgeConfigSpec.IntValue SUNSTONE_VEIN_SIZE;
        public final ForgeConfigSpec.IntValue CHIMERITE_VEIN_SIZE;
        public final ForgeConfigSpec.IntValue CHIMERITE_CHANCE;
        public final ForgeConfigSpec.IntValue CHIMERITE_MIN_HEIGHT;
        public final ForgeConfigSpec.IntValue CHIMERITE_MAX_HEIGHT;
        public final ForgeConfigSpec.IntValue TOPAZ_VEIN_SIZE;
        public final ForgeConfigSpec.IntValue TOPAZ_CHANCE;
        public final ForgeConfigSpec.IntValue TOPAZ_MIN_HEIGHT;
        public final ForgeConfigSpec.IntValue TOPAZ_MAX_HEIGHT;
        public final ForgeConfigSpec.IntValue VINTEUM_VEIN_SIZE;
        public final ForgeConfigSpec.IntValue VINTEUM_CHANCE;
        public final ForgeConfigSpec.IntValue VINTEUM_MIN_HEIGHT;
        public final ForgeConfigSpec.IntValue VINTEUM_MAX_HEIGHT;

        Server(ForgeConfigSpec.Builder builder) {
            builder.comment("Server configuration settings");
            builder.push("server");
            builder.push("craftingaltar");
            CRAFTING_ALTAR_MAIN_MAP = builder.comment("The blocks usable for the main body of the crafting altar structure.").translation(PREFIX + "altar.main").worldRestart().defineList("main", this::getDefaultMain, o -> ((o instanceof String) && ((String) o).split(SPLIT_REGEX).length == 3));
            CRAFTING_ALTAR_CAPS_MAP = builder.comment("The blocks usable for the caps of the crafting altar structure.").translation(PREFIX + "altar.caps").worldRestart().defineList("caps", this::getDefaultCaps, o -> ((o instanceof String) && ((String) o).split(SPLIT_REGEX).length == 2));
            builder.pop();
            builder.push("magicvalues");
            DEFAULT_MAX_MANA = builder.comment("The default maximum mana for the player.").translation(PREFIX + "maxMana").worldRestart().defineInRange("maxmana", 100, 0., 10000);
            DEFAULT_MAX_BURNOUT = builder.comment("The default maximum burnout for the player.").translation(PREFIX + "maxBurnout").worldRestart().defineInRange("maxburnout", 100, 0., 10000);
            MANA_BURNOUT_RATIO = builder.comment("The mana to burnout ratio.").translation(PREFIX + "burnoutRatio").worldRestart().defineInRange("burnoutratio", 0.5, 0, 10.0);
            builder.pop();
            builder.push("worldgen");
            LAKE_FREQUENCY = builder.comment("The frequency of mana lakes. Higher means rarer. Set to 0 to disable.").translation(PREFIX + "lakeFrequency").worldRestart().defineInRange("lakefrequency", 200, 0, 100000);
            DESERT_NOVA_ENABLED = builder.comment("Whether desert novas will generate or not.").translation(PREFIX + "desertNovaEnabled").worldRestart().define("desertnovaenabled", true);
            OTHER_FLOWERS_ENABLED = builder.comment("Whether the other flowers (aum, cerublossom, tarma root, wakebloom) will generate or not.").translation(PREFIX + "otherFlowersEnabled").worldRestart().define("otherflowersenabled", true);
            builder.push("oregen");
            METEOR_FREQUENCY = builder.comment("The frequency of moonstone meteors. Higher means more common. Set to 0 to disable.").translation(PREFIX + "meteorFrequency").worldRestart().defineInRange("meteorfrequency", 1, 0, 100);
            SUNSTONE_VEIN_SIZE = builder.comment("The vein size of sunstone ore. Set to 0 to disable.").translation(PREFIX + "sunstoneVeinSize").worldRestart().defineInRange("sunstoneveinsize", 1, 0, 64);
            CHIMERITE_VEIN_SIZE = builder.comment("The vein size of chimerite ore.").translation(PREFIX + "chimeriteVeinSize").worldRestart().defineInRange("chimeriteveinsize", 8, 1, 64);
            CHIMERITE_CHANCE = builder.comment("How common chimerite ore is.").translation(PREFIX + "chimeriteChance").worldRestart().defineInRange("chimeritechance", 6, 1, 64);
            CHIMERITE_MIN_HEIGHT = builder.comment("Minimum height of chimerite ore.").translation(PREFIX + "chimeriteMinHeight").worldRestart().defineInRange("chimeriteminheight", 10, 0, 255);
            CHIMERITE_MAX_HEIGHT = builder.comment("Maximum height of chimerite ore.").translation(PREFIX + "chimeriteMaxHeight").worldRestart().defineInRange("chimeritemaxheight", 80, 0, 255);
            TOPAZ_VEIN_SIZE = builder.comment("The vein size of topaz ore.").translation(PREFIX + "topazVeinSize").worldRestart().defineInRange("topazveinsize", 8, 1, 64);
            TOPAZ_CHANCE = builder.comment("How common topaz ore is.").translation(PREFIX + "topazChance").worldRestart().defineInRange("topazchance", 6, 1, 64);
            TOPAZ_MIN_HEIGHT = builder.comment("Minimum height of topaz ore.").translation(PREFIX + "topazMinHeight").worldRestart().defineInRange("topazminheight", 10, 0, 255);
            TOPAZ_MAX_HEIGHT = builder.comment("Maximum height of topaz ore.").translation(PREFIX + "topazMaxHeight").worldRestart().defineInRange("topazmaxheight", 80, 0, 255);
            VINTEUM_VEIN_SIZE = builder.comment("The vein size of vinteum ore.").translation(PREFIX + "vinteumVeinSize").worldRestart().defineInRange("vinteumveinsize", 6, 1, 64);
            VINTEUM_CHANCE = builder.comment("How common vinteum ore is.").translation(PREFIX + "vinteumChance").worldRestart().defineInRange("vinteumchance", 4, 1, 64);
            VINTEUM_MIN_HEIGHT = builder.comment("Minimum height of vinteum ore.").translation(PREFIX + "vinteumMinHeight").worldRestart().defineInRange("vinteumminheight", 10, 0, 255);
            VINTEUM_MAX_HEIGHT = builder.comment("Maximum height of vinteum ore.").translation(PREFIX + "vinteumMaxHeight").worldRestart().defineInRange("vinteummaxheight", 45, 0, 255);
            builder.pop();
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
                    if (rl == null) return;
                    Block block = ForgeRegistries.BLOCKS.getValue(rl);
                    if (block == null) return;
                    CraftingAltarStructureMaterials.addCapMaterial(block, Integer.parseInt(strings[1]));
                } catch (Throwable ignored) {
                }
            });
            CRAFTING_ALTAR_MAIN_MAP.get().stream().map(s -> s.split(SPLIT_REGEX)).filter(strings -> strings.length == 3).forEach(strings -> {
                try {
                    ResourceLocation blockRl = ResourceLocation.tryCreate(strings[0]);
                    ResourceLocation stairRl = ResourceLocation.tryCreate(strings[1]);
                    if (blockRl == null || stairRl == null) return;
                    Block block = ForgeRegistries.BLOCKS.getValue(blockRl);
                    Block stair = ForgeRegistries.BLOCKS.getValue(stairRl);
                    if (block == null || stair == null) return;
                    CraftingAltarStructureMaterials.addMainMaterial(block, stair, Integer.parseInt(strings[2]));
                } catch (Exception ignored) {
                }
            });
        }
    }
}
