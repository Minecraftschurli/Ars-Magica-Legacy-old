package minecraftschurli.arsmagicalegacy.init;

import minecraftschurli.arsmagicalegacy.*;
import minecraftschurli.arsmagicalegacy.objects.particle.*;
import net.minecraft.particles.*;
import net.minecraft.util.registry.*;

/**
 * @author Minecraftschurli
 * @version 2019-11-28
 */
public class ModParticles implements IInit {
    public static final ParticleType<SimpleParticle> ARCANE = register("arcane");
    public static void register() {
    }

    public static ParticleType<SimpleParticle> register(String key) {
        return Registry.<ParticleType<SimpleParticle>>register(Registry.PARTICLE_TYPE, ArsMagicaLegacy.MODID + ":" + key, SimpleParticle.TYPE);
    }
}
