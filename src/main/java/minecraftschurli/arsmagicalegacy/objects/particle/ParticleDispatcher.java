package minecraftschurli.arsmagicalegacy.objects.particle;

import minecraftschurli.arsmagicalegacy.*;
import minecraftschurli.arsmagicalegacy.init.*;
import net.minecraft.client.*;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.eventbus.api.*;
import net.minecraftforge.fml.common.*;

@Mod.EventBusSubscriber(modid = ArsMagicaLegacy.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ParticleDispatcher {
    @SubscribeEvent
    public static void registerFactories(ParticleFactoryRegisterEvent e) {
        Minecraft.getInstance().particles.registerFactory(ModParticles.ARCANE.get(), SimpleParticle.FACTORY);
        Minecraft.getInstance().particles.registerFactory(ModParticles.CLOCK.get(), SimpleParticle.FACTORY);
        Minecraft.getInstance().particles.registerFactory(ModParticles.EMBER.get(), SimpleParticle.FACTORY);
        Minecraft.getInstance().particles.registerFactory(ModParticles.GHOST.get(), SimpleParticle.FACTORY);
        Minecraft.getInstance().particles.registerFactory(ModParticles.IMPLOSION.get(), SimpleParticle.FACTORY);
        Minecraft.getInstance().particles.registerFactory(ModParticles.LENS_FLARE.get(), SimpleParticle.FACTORY);
        Minecraft.getInstance().particles.registerFactory(ModParticles.LIGHT.get(), SimpleParticle.FACTORY);
        Minecraft.getInstance().particles.registerFactory(ModParticles.PLANT.get(), SimpleParticle.FACTORY);
        Minecraft.getInstance().particles.registerFactory(ModParticles.PULSE.get(), SimpleParticle.FACTORY);
        Minecraft.getInstance().particles.registerFactory(ModParticles.ROCK.get(), SimpleParticle.FACTORY);
        Minecraft.getInstance().particles.registerFactory(ModParticles.ROTATING_RINGS.get(), SimpleParticle.FACTORY);
        Minecraft.getInstance().particles.registerFactory(ModParticles.SHINY.get(), SimpleParticle.FACTORY);
        Minecraft.getInstance().particles.registerFactory(ModParticles.SMOKE.get(), SimpleParticle.FACTORY);
        Minecraft.getInstance().particles.registerFactory(ModParticles.SPARKLE.get(), SimpleParticle.FACTORY);
        Minecraft.getInstance().particles.registerFactory(ModParticles.WATERBALL.get(), SimpleParticle.FACTORY);
        Minecraft.getInstance().particles.registerFactory(ModParticles.WIND.get(), SimpleParticle.FACTORY);
    }
}
