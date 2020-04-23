package minecraftschurli.arsmagicalegacy.objects.block.etheriumgenerator.blackaurem;

import minecraftschurli.arsmagicalegacy.init.ModTileEntities;
import minecraftschurli.arsmagicalegacy.objects.block.etheriumgenerator.EtheriumGeneratorBlock;
import net.minecraft.block.material.Material;

/**
 * @author Georg Burkl
 * @version 2020-04-23
 */
public class BlackAuremBlock extends EtheriumGeneratorBlock {
    public BlackAuremBlock() {
        super(Properties.create(Material.ROCK).notSolid(), ModTileEntities.BLACK_AUREM);
    }
}
