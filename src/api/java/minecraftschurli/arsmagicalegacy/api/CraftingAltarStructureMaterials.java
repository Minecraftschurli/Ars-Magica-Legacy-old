package minecraftschurli.arsmagicalegacy.api;

import net.minecraft.block.*;

import java.util.*;

/**
 * @author Minecraftschurli
 * @version 2019-12-31
 */
public class CraftingAltarStructureMaterials {
    private static final HashMap<Block, Integer> CAPS = new HashMap<>();
    private static final HashMap<Block, Integer> MAIN = new HashMap<>();
    private static final HashMap<Block, StairsBlock> STAIRS = new HashMap<>();

    /*public static Tag<Block> getCapsTag() {
        return new Tag<>(new ResourceLocation(ArsMagicaAPI.MODID, "altar_caps"), Collections.singletonList(new Tag.ListEntry<>(CAPS.keySet())),true);
    }

    public static Tag<Block> getMainTag() {
        return new Tag<>(new ResourceLocation(ArsMagicaAPI.MODID, "altar_caps"), Collections.singletonList(new Tag.ListEntry<>(MAIN.keySet())),true);
    }*/

    public static void addCapMaterial(Block block, int value) {
        CAPS.put(block, value);
    }

    public static void addMainMaterial(Block block, Block stair, int value) {
        MAIN.put(block, value);
        STAIRS.put(block, (StairsBlock) stair);
    }

    public static Block getStairForBlock(Block block) {
        return STAIRS.get(block);
    }

    public static boolean isValidCapMaterial(Block block) {
        return CAPS.containsKey(block);
    }

    public static boolean isValidMainMaterial(Block block) {
        return MAIN.containsKey(block);
    }

    public static int getCapPower(Block block) {
        return CAPS.get(block);
    }

    public static int getMainPower(Block block) {
        return MAIN.get(block);
    }
}
