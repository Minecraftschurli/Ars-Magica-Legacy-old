package minecraftschurli.arsmagicalegacy.init;

import minecraftschurli.arsmagicalegacy.*;
import minecraftschurli.arsmagicalegacy.objects.particle.*;
import net.minecraft.particles.*;
import net.minecraft.util.registry.*;
import net.minecraftforge.fml.RegistryObject;

/**
 * @author Minecraftschurli
 * @version 2019-11-28
 */
public class ModParticles implements IInit {
    public static final RegistryObject<ParticleType<SimpleParticle>> ARCANE = PARTICLE_TYPES.register("arcane", () -> SimpleParticle.TYPE);

    public static void register() {
    }
}
