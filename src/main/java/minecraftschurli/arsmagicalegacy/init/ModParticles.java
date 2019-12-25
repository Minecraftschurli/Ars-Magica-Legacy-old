package minecraftschurli.arsmagicalegacy.init;

import minecraftschurli.arsmagicalegacy.*;
import minecraftschurli.arsmagicalegacy.objects.particle.*;
import net.minecraft.particles.*;
import net.minecraft.util.registry.*;
import net.minecraftforge.registries.*;

/**
 * @author Minecraftschurli
 * @version 2019-11-28
 */
public class ModParticles implements IInit {
    public static void register() {
    }

    public static ParticleType<? extends IParticleData> register(String key) {
        return Registry.<ParticleType<? extends IParticleData>>register((Registry<? super ParticleType<? extends IParticleData>>) ForgeRegistries.PARTICLE_TYPES, ArsMagicaLegacy.MODID + ":" + key, SimpleParticle.TYPE);
    }
}
