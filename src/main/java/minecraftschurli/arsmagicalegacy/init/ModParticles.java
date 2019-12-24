package minecraftschurli.arsmagicalegacy.init;

import minecraftschurli.arsmagicalegacy.*;
import net.minecraft.particles.*;
import net.minecraft.util.registry.*;
import net.minecraftforge.event.*;
import net.minecraftforge.eventbus.api.*;
import net.minecraftforge.fml.*;

/**
 * @author Minecraftschurli
 * @version 2019-11-28
 */
public class ModParticles implements IInit {
    public static final RegistryObject<BasicParticleType> ARCANE = PARTICLE_TYPES.register("arcane", () -> new BasicParticleType(false));
    public static final RegistryObject<BasicParticleType> CLOCK = PARTICLE_TYPES.register("clock", () -> new BasicParticleType(false));
    public static final RegistryObject<BasicParticleType> EMBER = PARTICLE_TYPES.register("ember", () -> new BasicParticleType(false));
    public static final RegistryObject<BasicParticleType> GHOST = PARTICLE_TYPES.register("ghost", () -> new BasicParticleType(false));
    public static final RegistryObject<BasicParticleType> HEART = PARTICLE_TYPES.register("heart", () -> new BasicParticleType(false));
    public static final RegistryObject<BasicParticleType> IMPLOSION = PARTICLE_TYPES.register("implosion", () -> new BasicParticleType(false));
//    public static final RegistryObject<BasicParticleType> LEAF = PARTICLE_TYPES.register("leaf", () -> new BasicParticleType(true));
    public static final RegistryObject<BasicParticleType> LENS_FLARE = PARTICLE_TYPES.register("lens_flare", () -> new BasicParticleType(false));
    public static final RegistryObject<BasicParticleType> LIGHTS = PARTICLE_TYPES.register("lights", () -> new BasicParticleType(false));
    public static final RegistryObject<BasicParticleType> PLANT = PARTICLE_TYPES.register("plant", () -> new BasicParticleType(false));
    public static final RegistryObject<BasicParticleType> PULSE = PARTICLE_TYPES.register("pulse", () -> new BasicParticleType(false));
    public static final RegistryObject<BasicParticleType> ROCK = PARTICLE_TYPES.register("rock", () -> new BasicParticleType(false));
    public static final RegistryObject<BasicParticleType> ROTATING_RINGS = PARTICLE_TYPES.register("rotating_rings", () -> new BasicParticleType(false));
    public static final RegistryObject<BasicParticleType> SHINY = PARTICLE_TYPES.register("shiny", () -> new BasicParticleType(false));
    public static final RegistryObject<BasicParticleType> SMOKE = PARTICLE_TYPES.register("smoke", () -> new BasicParticleType(false));
    public static final RegistryObject<BasicParticleType> SPARKLE = PARTICLE_TYPES.register("sparkle", () -> new BasicParticleType(false));
    public static final RegistryObject<BasicParticleType> WATERBALL = PARTICLE_TYPES.register("waterball", () -> new BasicParticleType(false));
    public static final RegistryObject<BasicParticleType> WIND = PARTICLE_TYPES.register("wind", () -> new BasicParticleType(false));
    @SubscribeEvent
    public static void registerParticles(RegistryEvent.Register<ParticleType<?>> evt) {
        evt.getRegistry().registerAll(
                ARCANE.get(),
                CLOCK.get(),
                EMBER.get(),
                GHOST.get(),
                HEART.get(),
                IMPLOSION.get(),
                LENS_FLARE.get(),
                LIGHTS.get(),
                PLANT.get(),
                PULSE.get(),
                ROCK.get(),
                ROTATING_RINGS.get(),
                SHINY.get(),
                SMOKE.get(),
                SPARKLE.get(),
                WATERBALL.get(),
                WIND.get()
//                new BasicParticleType(false).setRegistryName(ArsMagicaLegacy.MODID, "arcane"),
//                new BasicParticleType(false).setRegistryName(ArsMagicaLegacy.MODID, "clock"),
//                new BasicParticleType(false).setRegistryName(ArsMagicaLegacy.MODID, "ember"),
//                new BasicParticleType(false).setRegistryName(ArsMagicaLegacy.MODID, "ghost"),
//                new BasicParticleType(false).setRegistryName(ArsMagicaLegacy.MODID, "heart"),
//                new BasicParticleType(false).setRegistryName(ArsMagicaLegacy.MODID, "implosion"),
////                new BasicParticleType(true).setRegistryName(ArsMagicaLegacy.MODID, "leaf"),
//                new BasicParticleType(false).setRegistryName(ArsMagicaLegacy.MODID, "lens_flare"),
//                new BasicParticleType(false).setRegistryName(ArsMagicaLegacy.MODID, "lights"),
//                new BasicParticleType(false).setRegistryName(ArsMagicaLegacy.MODID, "plant"),
//                new BasicParticleType(false).setRegistryName(ArsMagicaLegacy.MODID, "pulse"),
//                new BasicParticleType(false).setRegistryName(ArsMagicaLegacy.MODID, "rock"),
//                new BasicParticleType(false).setRegistryName(ArsMagicaLegacy.MODID, "rotating_rings"),
//                new BasicParticleType(false).setRegistryName(ArsMagicaLegacy.MODID, "shiny"),
//                new BasicParticleType(false).setRegistryName(ArsMagicaLegacy.MODID, "smoke"),
//                new BasicParticleType(false).setRegistryName(ArsMagicaLegacy.MODID, "sparkle"),
//                new BasicParticleType(false).setRegistryName(ArsMagicaLegacy.MODID, "waterball"),
//                new BasicParticleType(false).setRegistryName(ArsMagicaLegacy.MODID, "wind")
        );
    }

    public static void register() {
    }

    public static BasicParticleType register(String key, boolean alwaysShow) {
        return (BasicParticleType) Registry.<ParticleType<? extends IParticleData>>register(Registry.PARTICLE_TYPE, ArsMagicaLegacy.MODID + ":" + key, new BasicParticleType(alwaysShow));
    }
}
