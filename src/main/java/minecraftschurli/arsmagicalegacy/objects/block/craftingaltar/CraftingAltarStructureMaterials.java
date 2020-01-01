package minecraftschurli.arsmagicalegacy.objects.block.craftingaltar;

import minecraftschurli.arsmagicalegacy.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.StairsBlock;
import net.minecraft.tags.BlockTags;

import java.util.LinkedHashMap;

/**
 * @author Minecraftschurli
 * @version 2019-12-31
 */
public class CraftingAltarStructureMaterials {
    private static final LinkedHashMap<Block, Integer> CAPS = new LinkedHashMap<>();
    private static final LinkedHashMap<Block, Integer> MAIN = new LinkedHashMap<>();
    private static final LinkedHashMap<Block, StairsBlock> STAIRS = new LinkedHashMap<>();

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
}
