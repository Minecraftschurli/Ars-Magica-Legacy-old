package minecraftschurli.arsmagicalegacy.init;

import minecraftschurli.arsmagicalegacy.worldgen.biomes.WitchwoodForestBiome;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.RegistryObject;

/**
 * @author Minecraftschurli
 * @version 2019-11-22
 */
public class ModBiomes implements IInit {
    public static final RegistryObject<Biome> WITCHWOOD_FOREST = BIOMES.register("witchwood_forest", WitchwoodForestBiome::new);

    public static void register() {}
}
