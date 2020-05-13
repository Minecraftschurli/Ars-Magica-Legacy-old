package minecraftschurli.arsmagicalegacy.api;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.item.ItemStack;

/**
 * @author Minecraftschurli
 * @version 2020-04-27
 */
public class EtheriumGeneratorManager {
    private static final Map<ItemStack, Integer> FUELS = new HashMap<>();

    public static int getEtheriumValue(ItemStack stack) {
        if (stack.isEmpty()) return 0;
        return FUELS.getOrDefault(stack, 0);
    }

    public static boolean isEtheriumGeneratorFuel(ItemStack stack) {
        if (stack.isEmpty()) return false;
        return FUELS.containsKey(stack);
    }

    public static void registerEtheriumGeneratorFuel(ItemStack stack, int value) {
        if (stack.isEmpty()) throw new IllegalArgumentException("Stack must not be empty!");
        if (value < 0) throw new IllegalArgumentException("The fuel value must not be negative!");
        FUELS.put(stack, value);
    }
}
