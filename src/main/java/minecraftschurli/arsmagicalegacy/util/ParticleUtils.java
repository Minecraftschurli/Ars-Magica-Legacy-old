package minecraftschurli.arsmagicalegacy.util;

import minecraftschurli.arsmagicalegacy.objects.particle.SimpleParticleData;
import net.minecraft.particles.ParticleType;
import net.minecraft.world.World;
import net.minecraftforge.fml.RegistryObject;

public class ParticleUtils {
    public static float redFromColor(int color) {
        return ((color >> 16) & 0xFF) / 255f;
    }

    public static float greenFromColor(int color) {
        return ((color >> 8) & 0xFF) / 255f;
    }

    public static float blueFromColor(int color) {
        return (color & 0xFF) / 255f;
    }

    public static void addParticle(World world, RegistryObject<ParticleType<SimpleParticleData>> type, float r, float g, float b, double x, double y, double z, float xSpeed, float ySpeed, float zSpeed) {
        world.addParticle(new SimpleParticleData(type.get(), r, g, b, 1), x, y + 1.5, z, xSpeed, ySpeed, zSpeed);
    }

    public static void addParticle(World world, RegistryObject<ParticleType<SimpleParticleData>> type, int color, double x, double y, double z, float xSpeed, float ySpeed, float zSpeed) {
        world.addParticle(new SimpleParticleData(type.get(), redFromColor(color), greenFromColor(color), blueFromColor(color), 1), x, y + 1.5, z, xSpeed, ySpeed, zSpeed);
    }

    public static void addParticle(World world, RegistryObject<ParticleType<SimpleParticleData>> type, float r, float g, float b, double x, double y, double z) {
        world.addParticle(new SimpleParticleData(type.get(), r, g, b, 1), x, y + 1.5, z, 0, 0, 0);
    }

    public static void addParticle(World world, RegistryObject<ParticleType<SimpleParticleData>> type, int color, double x, double y, double z) {
        world.addParticle(new SimpleParticleData(type.get(), redFromColor(color), greenFromColor(color), blueFromColor(color), 1), x, y + 1.5, z, 0, 0, 0);
    }
}
