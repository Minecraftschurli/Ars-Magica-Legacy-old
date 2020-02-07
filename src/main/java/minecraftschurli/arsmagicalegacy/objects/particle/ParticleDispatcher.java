package minecraftschurli.arsmagicalegacy.objects.particle;

import minecraftschurli.arsmagicalegacy.ArsMagicaLegacy;
import minecraftschurli.arsmagicalegacy.init.ModParticles;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ArsMagicaLegacy.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ParticleDispatcher {
    @SubscribeEvent
    public static void registerFactories(ParticleFactoryRegisterEvent e) {
        Minecraft.getInstance().particles.registerFactory(ModParticles.ARCANE.get(), SimpleParticle.Factory::new);
        Minecraft.getInstance().particles.registerFactory(ModParticles.CLOCK.get(), SimpleParticle.Factory::new);
        Minecraft.getInstance().particles.registerFactory(ModParticles.EMBER.get(), SimpleParticle.Factory::new);
        Minecraft.getInstance().particles.registerFactory(ModParticles.GHOST.get(), SimpleParticle.Factory::new);
        Minecraft.getInstance().particles.registerFactory(ModParticles.IMPLOSION.get(), SimpleParticle.Factory::new);
        Minecraft.getInstance().particles.registerFactory(ModParticles.LENS_FLARE.get(), SimpleParticle.Factory::new);
        Minecraft.getInstance().particles.registerFactory(ModParticles.LIGHT.get(), SimpleParticle.Factory::new);
        Minecraft.getInstance().particles.registerFactory(ModParticles.PLANT.get(), SimpleParticle.Factory::new);
        Minecraft.getInstance().particles.registerFactory(ModParticles.PULSE.get(), SimpleParticle.Factory::new);
        Minecraft.getInstance().particles.registerFactory(ModParticles.ROCK.get(), SimpleParticle.Factory::new);
        Minecraft.getInstance().particles.registerFactory(ModParticles.ROTATING_RINGS.get(), SimpleParticle.Factory::new);
        Minecraft.getInstance().particles.registerFactory(ModParticles.SHINY.get(), SimpleParticle.Factory::new);
        Minecraft.getInstance().particles.registerFactory(ModParticles.SMOKE.get(), SimpleParticle.Factory::new);
        Minecraft.getInstance().particles.registerFactory(ModParticles.SPARKLE.get(), SimpleParticle.Factory::new);
        Minecraft.getInstance().particles.registerFactory(ModParticles.WATERBALL.get(), SimpleParticle.Factory::new);
        Minecraft.getInstance().particles.registerFactory(ModParticles.WIND.get(), SimpleParticle.Factory::new);
    }
}
