package minecraftschurli.arsmagicalegacy.init;

import minecraftschurli.arsmagicalegacy.objects.particle.*;
import net.minecraft.particles.*;
import net.minecraftforge.fml.*;

/**
 * @author Minecraftschurli
 * @version 2019-11-28
 */
public class ModParticles implements IInit {
    public static final RegistryObject<ParticleType<SimpleParticle.SimpleParticleType>> ARCANE = PARTICLE_TYPES.register("arcane", SimpleParticle.SimpleParticleType::new);
    public static final RegistryObject<ParticleType<SimpleParticle.SimpleParticleType>> CLOCK = PARTICLE_TYPES.register("clock", SimpleParticle.SimpleParticleType::new);
    public static final RegistryObject<ParticleType<SimpleParticle.SimpleParticleType>> EMBER = PARTICLE_TYPES.register("ember", SimpleParticle.SimpleParticleType::new);
    public static final RegistryObject<ParticleType<SimpleParticle.SimpleParticleType>> GHOST = PARTICLE_TYPES.register("ghost", SimpleParticle.SimpleParticleType::new);
    public static final RegistryObject<ParticleType<SimpleParticle.SimpleParticleType>> IMPLOSION = PARTICLE_TYPES.register("implosion", SimpleParticle.SimpleParticleType::new);
    public static final RegistryObject<ParticleType<SimpleParticle.SimpleParticleType>> LENS_FLARE = PARTICLE_TYPES.register("lens_flare", SimpleParticle.SimpleParticleType::new);
    public static final RegistryObject<ParticleType<SimpleParticle.SimpleParticleType>> LIGHT = PARTICLE_TYPES.register("light", SimpleParticle.SimpleParticleType::new);
    public static final RegistryObject<ParticleType<SimpleParticle.SimpleParticleType>> PLANT = PARTICLE_TYPES.register("plant", SimpleParticle.SimpleParticleType::new);
    public static final RegistryObject<ParticleType<SimpleParticle.SimpleParticleType>> PULSE = PARTICLE_TYPES.register("pulse", SimpleParticle.SimpleParticleType::new);
    public static final RegistryObject<ParticleType<SimpleParticle.SimpleParticleType>> ROCK = PARTICLE_TYPES.register("rock", SimpleParticle.SimpleParticleType::new);
    public static final RegistryObject<ParticleType<SimpleParticle.SimpleParticleType>> ROTATING_RINGS = PARTICLE_TYPES.register("rotating_rings", SimpleParticle.SimpleParticleType::new);
    public static final RegistryObject<ParticleType<SimpleParticle.SimpleParticleType>> SHINY = PARTICLE_TYPES.register("shiny", SimpleParticle.SimpleParticleType::new);
    public static final RegistryObject<ParticleType<SimpleParticle.SimpleParticleType>> SMOKE = PARTICLE_TYPES.register("smoke", SimpleParticle.SimpleParticleType::new);
    public static final RegistryObject<ParticleType<SimpleParticle.SimpleParticleType>> SPARKLE = PARTICLE_TYPES.register("sparkle", SimpleParticle.SimpleParticleType::new);
    public static final RegistryObject<ParticleType<SimpleParticle.SimpleParticleType>> WATERBALL = PARTICLE_TYPES.register("waterball", SimpleParticle.SimpleParticleType::new);
    public static final RegistryObject<ParticleType<SimpleParticle.SimpleParticleType>> WIND = PARTICLE_TYPES.register("wind", SimpleParticle.SimpleParticleType::new);

    public static void register() {
    }
}
