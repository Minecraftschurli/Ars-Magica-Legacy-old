package minecraftschurli.arsmagicalegacy.objects.particle;

import minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import minecraftschurli.arsmagicalegacy.init.ModParticles;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ArsMagicaAPI.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ParticleDispatcher {
    @SubscribeEvent
    public static void registerFactories(ParticleFactoryRegisterEvent e) {
        Minecraft.getInstance().particles.registerFactory(ModParticles.ARCANE.get(), SimpleParticle.Factory::new);
        Minecraft.getInstance().particles.registerFactory(ModParticles.CLOCK.get(), SimpleParticle.Factory::new);
        Minecraft.getInstance().particles.registerFactory(ModParticles.EMBER.get(), SimpleParticle.Factory::new);
        Minecraft.getInstance().particles.registerFactory(ModParticles.GHOST.get(), SimpleParticle.Factory::new);
        Minecraft.getInstance().particles.registerFactory(ModParticles.IMPLOSION.get(), SimpleParticle.Factory::new);
        Minecraft.getInstance().particles.registerFactory(ModParticles.LEAF.get(), SimpleParticle.Factory::new);
        Minecraft.getInstance().particles.registerFactory(ModParticles.LENS_FLARE.get(), SimpleParticle.Factory::new);
        Minecraft.getInstance().particles.registerFactory(ModParticles.LIGHT.get(), SimpleParticle.Factory::new);
        Minecraft.getInstance().particles.registerFactory(ModParticles.PLANT.get(), SimpleParticle.Factory::new);
        Minecraft.getInstance().particles.registerFactory(ModParticles.PULSE.get(), SimpleParticle.Factory::new);
        Minecraft.getInstance().particles.registerFactory(ModParticles.ROCK.get(), SimpleParticle.Factory::new);
        Minecraft.getInstance().particles.registerFactory(ModParticles.ROTATING_RINGS.get(), SimpleParticle.Factory::new);
        Minecraft.getInstance().particles.registerFactory(ModParticles.SHINY.get(), SimpleParticle.Factory::new);
        Minecraft.getInstance().particles.registerFactory(ModParticles.SMOKE.get(), SimpleParticle.Factory::new);
        Minecraft.getInstance().particles.registerFactory(ModParticles.SNOWFLAKE.get(), SimpleParticle.Factory::new);
        Minecraft.getInstance().particles.registerFactory(ModParticles.SPARKLE.get(), SimpleParticle.Factory::new);
        Minecraft.getInstance().particles.registerFactory(ModParticles.SYMBOL.get(), SimpleParticle.Factory::new);
        Minecraft.getInstance().particles.registerFactory(ModParticles.WATERBALL.get(), SimpleParticle.Factory::new);
        Minecraft.getInstance().particles.registerFactory(ModParticles.WIND.get(), SimpleParticle.Factory::new);
        Minecraft.getInstance().particles.registerFactory(ModParticles.WITCHWOOD_LEAF.get(), SimpleParticle.Factory::new);
//        Minecraft.getInstance().particles.registerFactory(ModParticles.BEAM.get(), BeamParticle.Factory::new);
//        Minecraft.getInstance().particles.registerFactory(ModParticles.DARK_BEAM.get(), BeamParticle.Factory::new);
//        Minecraft.getInstance().particles.registerFactory(ModParticles.LIGHT_BEAM.get(), BeamParticle.Factory::new);
        Minecraft.getInstance().particles.registerFactory(ModParticles.NONE_HAND.get(), SimpleParticle.Factory::new);
        Minecraft.getInstance().particles.registerFactory(ModParticles.WATER_HAND.get(), SimpleParticle.Factory::new);
        Minecraft.getInstance().particles.registerFactory(ModParticles.FIRE_HAND.get(), SimpleParticle.Factory::new);
        Minecraft.getInstance().particles.registerFactory(ModParticles.EARTH_HAND.get(), SimpleParticle.Factory::new);
        Minecraft.getInstance().particles.registerFactory(ModParticles.AIR_HAND.get(), SimpleParticle.Factory::new);
        Minecraft.getInstance().particles.registerFactory(ModParticles.LIGHTNING_HAND.get(), SimpleParticle.Factory::new);
        Minecraft.getInstance().particles.registerFactory(ModParticles.ICE_HAND.get(), SimpleParticle.Factory::new);
        Minecraft.getInstance().particles.registerFactory(ModParticles.NATURE_HAND.get(), SimpleParticle.Factory::new);
        Minecraft.getInstance().particles.registerFactory(ModParticles.LIFE_HAND.get(), SimpleParticle.Factory::new);
        Minecraft.getInstance().particles.registerFactory(ModParticles.ARCANE_HAND.get(), SimpleParticle.Factory::new);
        Minecraft.getInstance().particles.registerFactory(ModParticles.ENDER_HAND.get(), SimpleParticle.Factory::new);
    }
}
