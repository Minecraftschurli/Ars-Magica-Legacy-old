package minecraftschurli.arsmagicalegacy.init;

import minecraftschurli.arsmagicalegacy.worldgen.features.AMFlowerFeature;
import minecraftschurli.arsmagicalegacy.worldgen.features.DesertNova;
import minecraftschurli.arsmagicalegacy.worldgen.features.MoonstoneMeteor;
import minecraftschurli.arsmagicalegacy.worldgen.features.SunstoneOre;
import net.minecraftforge.fml.RegistryObject;

/**
 * @author Minecraftschurli
 * @version 2020-01-18
 */
public final class ModFeatures implements IInit {
    public static final RegistryObject<MoonstoneMeteor> MOONSTONE_METEOR = FEATURES.register("moonstone_meteor", MoonstoneMeteor::new);
    public static final RegistryObject<SunstoneOre> SUNSTONE_ORE = FEATURES.register("sunstone_ore", SunstoneOre::new);
    public static final RegistryObject<AMFlowerFeature> FLOWERS = FEATURES.register("flowers", AMFlowerFeature::new);
    public static final RegistryObject<DesertNova> DESERT_NOVA = FEATURES.register("desert_nova", DesertNova::new);

    public static void register() {
    }
}
