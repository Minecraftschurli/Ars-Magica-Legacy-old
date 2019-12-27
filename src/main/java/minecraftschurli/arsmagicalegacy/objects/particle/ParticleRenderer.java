package minecraftschurli.arsmagicalegacy.objects.particle;

import minecraftschurli.arsmagicalegacy.*;
import minecraftschurli.arsmagicalegacy.init.*;
import net.minecraft.client.*;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.eventbus.api.*;
import net.minecraftforge.fml.common.*;

@Mod.EventBusSubscriber(modid = ArsMagicaLegacy.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ParticleRenderer {
    @SubscribeEvent
    public static void registerFactories(ParticleFactoryRegisterEvent evt) {
        /*Minecraft.getInstance().particles.registerFactory(ModParticles.ARCANE.get(), ParticleFactory::new);
        Minecraft.getInstance().particles.registerFactory(ModParticles.CLOCK.get(), ParticleFactory::new);
        Minecraft.getInstance().particles.registerFactory(ModParticles.EMBER.get(), ParticleFactory::new);
        Minecraft.getInstance().particles.registerFactory(ModParticles.GHOST.get(), ParticleFactory::new);
        Minecraft.getInstance().particles.registerFactory(ModParticles.HEART.get(), ParticleFactory::new);
        Minecraft.getInstance().particles.registerFactory(ModParticles.IMPLOSION.get(), ParticleFactory::new);
        Minecraft.getInstance().particles.registerFactory(ModParticles.LENS_FLARE.get(), ParticleFactory::new);
        Minecraft.getInstance().particles.registerFactory(ModParticles.LIGHTS.get(), ParticleFactory::new);
        Minecraft.getInstance().particles.registerFactory(ModParticles.PLANT.get(), ParticleFactory::new);
        Minecraft.getInstance().particles.registerFactory(ModParticles.PULSE.get(), ParticleFactory::new);
        Minecraft.getInstance().particles.registerFactory(ModParticles.ROCK.get(), ParticleFactory::new);
        Minecraft.getInstance().particles.registerFactory(ModParticles.ROTATING_RINGS.get(), ParticleFactory::new);
        Minecraft.getInstance().particles.registerFactory(ModParticles.SHINY.get(), ParticleFactory::new);
        Minecraft.getInstance().particles.registerFactory(ModParticles.SMOKE.get(), ParticleFactory::new);
        Minecraft.getInstance().particles.registerFactory(ModParticles.SPARKLE.get(), ParticleFactory::new);
        Minecraft.getInstance().particles.registerFactory(ModParticles.WATERBALL.get(), ParticleFactory::new);
        Minecraft.getInstance().particles.registerFactory(ModParticles.WIND.get(), ParticleFactory::new);*/
    }
}
