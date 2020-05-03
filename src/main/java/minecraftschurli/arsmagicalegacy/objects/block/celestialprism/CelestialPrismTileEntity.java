package minecraftschurli.arsmagicalegacy.objects.block.celestialprism;

import minecraftschurli.arsmagicalegacy.api.etherium.EtheriumType;
import minecraftschurli.arsmagicalegacy.api.etherium.generator.EtheriumGeneratorTileEntity;
import minecraftschurli.arsmagicalegacy.compat.patchouli.PatchouliCompat;
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
        if (world != null) {
            if (time > 0) {
                time--;
            } else {
                time = getTimer();
                if (world.canBlockSeeSky(getPos())) {
                    addEtherium(1);
                }
            }
        }
    }

    private int getTimer() {
        if (world == null) return 0;
        int tier = 0;
        if (PatchouliCompat.CELESTIAL_PRISM_CHALK.get().validate(world, pos) != null) {
            if (PatchouliCompat.CELESTIAL_PRISM_PILLAR_1.get().validate(world, pos) != null) {
                tier = 2;
            } else if (PatchouliCompat.CELESTIAL_PRISM_PILLAR_2.get().validate(world, pos) != null) {
                tier = 3;
            } else if (PatchouliCompat.CELESTIAL_PRISM_PILLAR_3.get().validate(world, pos) != null) {
                tier = 4;
            } else if (PatchouliCompat.CELESTIAL_PRISM_PILLAR_4.get().validate(world, pos) != null) {
                tier = 5;
            } else {
                tier = 1;
            }
        }
        return 6 / (tier+1);
    }
}
