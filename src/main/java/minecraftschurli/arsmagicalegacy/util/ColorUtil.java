package minecraftschurli.arsmagicalegacy.util;

/**
 * @author Minecraftschurli
 * @version 2020-05-20
 */
public final class ColorUtil {
    public static float getBlue(int color) {
        return (0xFF & color) / 255f;
    }

    public static float getGreen(int color) {
        return (0xFF & ( color >> 8 )) / 255f;
    }

    public static float getRed(int color) {
        return (0xFF & ( color >> 16)) / 255f;
    }

    public static float getAlpha(int color) {
        return (0xFF & ( color >> 24)) / 255f;
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
}
