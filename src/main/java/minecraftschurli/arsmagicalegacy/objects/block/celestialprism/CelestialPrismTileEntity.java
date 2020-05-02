package minecraftschurli.arsmagicalegacy.objects.block.celestialprism;

import minecraftschurli.arsmagicalegacy.api.etherium.EtheriumType;
import minecraftschurli.arsmagicalegacy.api.etherium.generator.EtheriumGeneratorTileEntity;
import minecraftschurli.arsmagicalegacy.capabilities.EtheriumStorage;
import minecraftschurli.arsmagicalegacy.init.ModTileEntities;

/**
 * @author Minecraftschurli
 * @version 2020-05-02
 */
public class CelestialPrismTileEntity extends EtheriumGeneratorTileEntity {
    public CelestialPrismTileEntity() {
        super(ModTileEntities.CELESTIAL_PRISM.get(), () -> new EtheriumStorage(5000, EtheriumType.LIGHT.get()));
    }

    @Override
    public void tick() {
        if (world != null && world.canBlockSeeSky(getPos())) {
            addEtherium(1);
        }
    }
}
