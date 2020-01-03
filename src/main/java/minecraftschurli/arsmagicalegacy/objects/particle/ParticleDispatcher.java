package minecraftschurli.arsmagicalegacy.objects.particle;

import minecraftschurli.arsmagicalegacy.*;
import net.minecraft.client.*;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.eventbus.api.*;
import net.minecraftforge.fml.common.*;

@Mod.EventBusSubscriber(modid = ArsMagicaLegacy.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ParticleDispatcher {
    @SubscribeEvent
    public static void registerFactories(ParticleFactoryRegisterEvent e) {
        Minecraft.getInstance().particles.registerFactory(SimpleParticle.TYPE, () -> new SimpleParticle.Factory());
    }
}
