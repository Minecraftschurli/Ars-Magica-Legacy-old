package minecraftschurli.arsmagicalegacy.api.spell.crafting;

import net.minecraft.nbt.*;

import javax.annotation.*;
import java.util.*;

/**
 * Registry for all {@link ISpellIngredient} deserializers
 *
 * @author Minecraftschurli
 * @version 2019-12-09
 */
public final class IngredientTypes {
    private static final Map<String, SpellIngredientFactory<?>> TYPES = new HashMap<>();

    private IngredientTypes (){}

    /**
     * Registers a deserializer with the given name
     * The name needs to be stored in the nbt under the {@link ISpellIngredient#TYPE_KEY}
     *
     * @param name    : the name of the {@link ISpellIngredient}
     * @param factory : the deserializer for the {@link ISpellIngredient}
     */
    public static void register(String name, SpellIngredientFactory<?> factory) {
        TYPES.put(name, factory);
    }

    public static void registerDefault() {
        register(EssenceSpellIngredient.TYPE, EssenceSpellIngredient::new);
        register(ItemStackSpellIngredient.TYPE, ItemStackSpellIngredient::new);
        register(ItemTagSpellIngredient.TYPE, ItemTagSpellIngredient::new);
    }

    @Nonnull
    public static ISpellIngredient get(String type, CompoundNBT nbt) {
        SpellIngredientFactory<?> factory = TYPES.get(type);
        if (factory == null)
            throw new IllegalStateException("No factory for this type ("+type+")");
        return factory.create(nbt);
    }

    public static ISpellIngredient deserialize(CompoundNBT nbt) {
        return get(nbt.getString(ISpellIngredient.TYPE_KEY), nbt);
    }

    @FunctionalInterface
    public interface SpellIngredientFactory<T extends ISpellIngredient> {
        T create(CompoundNBT nbt);
    }
}
