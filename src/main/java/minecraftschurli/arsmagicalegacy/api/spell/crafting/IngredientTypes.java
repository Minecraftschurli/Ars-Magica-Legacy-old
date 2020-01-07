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
        register(EssenceSpellIngredient.TYPE, EssenceSpellIngredient::new);
        register(ItemStackSpellIngredient.TYPE, ItemStackSpellIngredient::new);
        register(ItemTagSpellIngredient.TYPE, ItemTagSpellIngredient::new);
    }

    public static ISpellIngredient get(String type, CompoundNBT nbt) {
        SpellIngredientFactory<?> factory = TYPES.get(type);
        if (factory == null)
            return null;
        return factory.create(nbt);
    }

    @FunctionalInterface
    public interface SpellIngredientFactory<T extends ISpellIngredient> {
        T create(CompoundNBT nbt);
    }
}
