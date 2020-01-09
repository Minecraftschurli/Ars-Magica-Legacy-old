package minecraftschurli.arsmagicalegacy.init;

import minecraftschurli.arsmagicalegacy.worldgen.biomes.*;
import net.minecraft.world.biome.*;
import net.minecraftforge.fml.*;

/**
 * @author Minecraftschurli
 * @version 2019-11-22
 */
public final class ModBiomes implements IInit {
    public static final RegistryObject<Biome> WITCHWOOD_FOREST = BIOMES.register("witchwood_forest", WitchwoodForestBiome::new);
    public static void register() {}
}
