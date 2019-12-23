package minecraftschurli.arsmagicalegacy.api.spell.crafting;

import net.minecraft.nbt.*;

import java.util.*;

/**
 * @author Minecraftschurli
 * @version 2019-12-09
 */
public class IngredientTypes {
    private static final Map<String, SpellIngredientFactory<?>> TYPES = new HashMap<>();

    public static void register(String name, SpellIngredientFactory<?> factory) {
        TYPES.put(name, factory);
    }

    public static void registerDefault() {
        register("essence", EssenceSpellIngredient::new);
        register("item_stack", ItemStackSpellIngredient::new);
        register("item_tag", ItemTagSpellIngredient::new);
    }

    public static ISpellIngredient get(String type, CompoundNBT nbt) {
        return TYPES.get(type).create(nbt);
    }

    @FunctionalInterface
    public interface SpellIngredientFactory<T extends ISpellIngredient> {
        T create(CompoundNBT nbt);
    }
}
