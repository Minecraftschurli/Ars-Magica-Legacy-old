package minecraftschurli.arsmagicalegacy.util;

import java.util.function.Supplier;
import minecraftschurli.arsmagicalegacy.objects.particle.SimpleParticleData;
import net.minecraft.entity.Entity;
import net.minecraft.particles.ParticleType;
import net.minecraft.world.server.ServerWorld;

/**
 * @author Minecraftschurli
 * @version 2020-05-20
 */
public final class ParticleUtil {
    public static float getBlue(int color) {
        return (0xFF & color) / 255f;
    }

    public static float getGreen(int color) {
        return (0xFF & (color >> 8)) / 255f;
    }

    public static float getRed(int color) {
        return (0xFF & (color >> 16)) / 255f;
    }

    public static int getColor(float r, float g, float b) {
        int red = (int) (r * 255f) << 16;
        int green = (int) (g * 255f) << 8;
        int blue = (int) (b * 255f);
        return red + green + blue;
    }

    public static int getColor(float r, float g, float b, float a) {
        int alpha = (int) (a * 255f) << 24;
        int red = (int) (r * 255f) << 16;
        int green = (int) (g * 255f) << 8;
        int blue = (int) (b * 255f);
        return red + green + blue + alpha;
    }

    public static void addParticle(ServerWorld world, Entity target, Supplier<ParticleType<SimpleParticleData>> type, int colorModifier, int color, double x, double y, double z) {
        world.spawnParticle(new SimpleParticleData(type.get(), getRed(colorModifier > -1 ? colorModifier : color), getGreen(colorModifier > -1 ? colorModifier : color), getBlue(colorModifier > -1 ? colorModifier : color)), x, y + (target == null ? 0 : target.getEyeHeight()), z, 0, 0, 0, 0, 1);
    }

    public static void addParticle(ServerWorld world, Entity target, Supplier<ParticleType<SimpleParticleData>> type, int colorModifier, int color, double x, double y, double z, float dx, float dy, float dz) {
        world.spawnParticle(new SimpleParticleData(type.get(), getRed(colorModifier > -1 ? colorModifier : color), getGreen(colorModifier > -1 ? colorModifier : color), getBlue(colorModifier > -1 ? colorModifier : color)), x, y + (target == null ? 0 : target.getEyeHeight()), z, 0, dx, dy, dz, 1);
    }
}
