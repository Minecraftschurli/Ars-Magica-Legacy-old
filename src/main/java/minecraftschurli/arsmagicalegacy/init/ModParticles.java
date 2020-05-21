package minecraftschurli.arsmagicalegacy.init;

import minecraftschurli.arsmagicalegacy.objects.particle.SimpleParticleData;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleType;
import net.minecraftforge.fml.RegistryObject;

/**
 * @author Minecraftschurli
 * @version 2019-11-28
 */
public final class ModParticles implements IInit {
    public static final RegistryObject<ParticleType<SimpleParticleData>> ARCANE = register("arcane", SimpleParticleData.DESERIALIZER);
    public static final RegistryObject<ParticleType<SimpleParticleData>> CLOCK = register("clock", SimpleParticleData.DESERIALIZER);
    public static final RegistryObject<ParticleType<SimpleParticleData>> EMBER = register("ember", SimpleParticleData.DESERIALIZER);
    public static final RegistryObject<ParticleType<SimpleParticleData>> GHOST = register("ghost", SimpleParticleData.DESERIALIZER);
    public static final RegistryObject<ParticleType<SimpleParticleData>> IMPLOSION = register("implosion", SimpleParticleData.DESERIALIZER);
    public static final RegistryObject<ParticleType<SimpleParticleData>> LEAF = register("leaf", SimpleParticleData.DESERIALIZER);
    public static final RegistryObject<ParticleType<SimpleParticleData>> LENS_FLARE = register("lens_flare", SimpleParticleData.DESERIALIZER);
    public static final RegistryObject<ParticleType<SimpleParticleData>> LIGHT = register("light", SimpleParticleData.DESERIALIZER);
    public static final RegistryObject<ParticleType<SimpleParticleData>> PLANT = register("plant", SimpleParticleData.DESERIALIZER);
    public static final RegistryObject<ParticleType<SimpleParticleData>> PULSE = register("pulse", SimpleParticleData.DESERIALIZER);
    public static final RegistryObject<ParticleType<SimpleParticleData>> ROCK = register("rock", SimpleParticleData.DESERIALIZER);
    public static final RegistryObject<ParticleType<SimpleParticleData>> ROTATING_RINGS = register("rotating_rings", SimpleParticleData.DESERIALIZER);
    public static final RegistryObject<ParticleType<SimpleParticleData>> SHINY = register("shiny", SimpleParticleData.DESERIALIZER);
    public static final RegistryObject<ParticleType<SimpleParticleData>> SNOWFLAKE = register("snowflake", SimpleParticleData.DESERIALIZER);
    public static final RegistryObject<ParticleType<SimpleParticleData>> SMOKE = register("smoke", SimpleParticleData.DESERIALIZER);
    public static final RegistryObject<ParticleType<SimpleParticleData>> SPARKLE = register("sparkle", SimpleParticleData.DESERIALIZER);
    public static final RegistryObject<ParticleType<SimpleParticleData>> SYMBOL = register("symbol", SimpleParticleData.DESERIALIZER);
    public static final RegistryObject<ParticleType<SimpleParticleData>> WATERBALL = register("waterball", SimpleParticleData.DESERIALIZER);
    public static final RegistryObject<ParticleType<SimpleParticleData>> WIND = register("wind", SimpleParticleData.DESERIALIZER);
    public static final RegistryObject<ParticleType<SimpleParticleData>> WITCHWOOD_LEAF = register("witchwood_leaf", SimpleParticleData.DESERIALIZER);
//    public static final RegistryObject<ParticleType<BeamParticleData>> BEAM = register("beam", BeamParticleData.DESERIALIZER);
//    public static final RegistryObject<ParticleType<BeamParticleData>> DARK_BEAM = register("dark_beam", BeamParticleData.DESERIALIZER);
//    public static final RegistryObject<ParticleType<BeamParticleData>> LIGHT_BEAM = register("light_beam", BeamParticleData.DESERIALIZER);
    public static final RegistryObject<ParticleType<SimpleParticleData>> NONE_HAND = register("none_hand", SimpleParticleData.DESERIALIZER);
    public static final RegistryObject<ParticleType<SimpleParticleData>> WATER_HAND = register("water_hand", SimpleParticleData.DESERIALIZER);
    public static final RegistryObject<ParticleType<SimpleParticleData>> FIRE_HAND = register("fire_hand", SimpleParticleData.DESERIALIZER);
    public static final RegistryObject<ParticleType<SimpleParticleData>> EARTH_HAND = register("earth_hand", SimpleParticleData.DESERIALIZER);
    public static final RegistryObject<ParticleType<SimpleParticleData>> AIR_HAND = register("air_hand", SimpleParticleData.DESERIALIZER);
    public static final RegistryObject<ParticleType<SimpleParticleData>> LIGHTNING_HAND = register("lightning_hand", SimpleParticleData.DESERIALIZER);
    public static final RegistryObject<ParticleType<SimpleParticleData>> ICE_HAND = register("ice_hand", SimpleParticleData.DESERIALIZER);
    public static final RegistryObject<ParticleType<SimpleParticleData>> NATURE_HAND = register("nature_hand", SimpleParticleData.DESERIALIZER);
    public static final RegistryObject<ParticleType<SimpleParticleData>> LIFE_HAND = register("life_hand", SimpleParticleData.DESERIALIZER);
    public static final RegistryObject<ParticleType<SimpleParticleData>> ARCANE_HAND = register("arcane_hand", SimpleParticleData.DESERIALIZER);
    public static final RegistryObject<ParticleType<SimpleParticleData>> ENDER_HAND = register("ender_hand", SimpleParticleData.DESERIALIZER);

    public static void register() {
    }

    private static <T extends IParticleData> RegistryObject<ParticleType<T>> register(String name, IParticleData.IDeserializer<T> deserializer) {
        return PARTICLE_TYPES.register(name, () -> new ParticleType<>(false, deserializer));
    }
}
