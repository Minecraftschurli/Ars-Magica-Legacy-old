package minecraftschurli.arsmagicalegacy.api;

import minecraftschurli.arsmagicalegacy.api.config.CraftingAltarStructureMaterials;
import net.minecraft.block.Block;
import net.minecraft.block.StairsBlock;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

/**
 * @author Minecraftschurli
 * @version 2020-04-23
 */
public class IMCHandler {
    public static final String ADD_CRAFTING_ALTAR_MAIN_MATERIAL = "addCraftingAltarMainMaterial";
    public static final String ADD_CRAFTING_ALTAR_CAP_MATERIAL = "addCraftingAltarCapMaterial";

    public static void processIMC(final InterModProcessEvent event) {
        event.getIMCStream(ADD_CRAFTING_ALTAR_MAIN_MATERIAL::equals).forEach(imcMessage -> {
            Triple<Block, StairsBlock, Integer> message = imcMessage.<Triple<Block, StairsBlock, Integer>>getMessageSupplier().get();
            CraftingAltarStructureMaterials.addMainMaterial(message.getLeft(),message.getMiddle(),message.getRight());
        });
        event.getIMCStream(ADD_CRAFTING_ALTAR_CAP_MATERIAL::equals).forEach(imcMessage -> {
            Pair<Block, Integer> message = imcMessage.<Pair<Block, Integer>>getMessageSupplier().get();
            CraftingAltarStructureMaterials.addCapMaterial(message.getLeft(),message.getRight());
        });
    }
}
