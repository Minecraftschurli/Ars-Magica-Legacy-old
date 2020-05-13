package minecraftschurli.arsmagicalegacy.objects.block.celestialprism;

import minecraftschurli.arsmagicalegacy.api.etherium.EtheriumType;
import minecraftschurli.arsmagicalegacy.api.etherium.generator.EtheriumGeneratorTileEntity;
import minecraftschurli.arsmagicalegacy.init.ModTileEntities;

/**
 * @author Minecraftschurli
 * @version 2020-05-02
 */
public class CelestialPrismTileEntity extends EtheriumGeneratorTileEntity {
    private int time;

    public CelestialPrismTileEntity() {
        super(ModTileEntities.CELESTIAL_PRISM.get(), EtheriumType.LIGHT.get());
    }

    @Override
    public void tick() {
        if (world != null && world.canBlockSeeSky(getPos()) && world.isDaytime()) if (time > 0) time--;
        else {
            time = 6 / (getTier() + 1);
            addEtherium(1);
        }
    }
}
