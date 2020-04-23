package minecraftschurli.arsmagicalegacy.objects.block.etheriumgenerator.celestialprism;

import minecraftschurli.arsmagicalegacy.init.ModTileEntities;
import minecraftschurli.arsmagicalegacy.objects.block.etheriumgenerator.EtheriumGeneratorBlock;
import net.minecraft.block.material.Material;

/**
 * @author Georg Burkl
 * @version 2020-04-23
 */
public class CelestialPrismBlock extends EtheriumGeneratorBlock {
    public CelestialPrismBlock() {
        super(Properties.create(Material.ROCK).notSolid(), ModTileEntities.CELESTIAL_PRISM);
    }
}
