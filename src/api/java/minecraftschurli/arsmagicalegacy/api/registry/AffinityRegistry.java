package minecraftschurli.arsmagicalegacy.api.registry;

import minecraftschurli.arsmagicalegacy.api.affinity.Affinity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;

/**
 * @author Minecraftschurli
 * @version 2020-02-13
 */
public class AffinityRegistry {
    private static final Map<RegistryObject<Affinity>, Supplier<? extends Affinity>> entries = new LinkedHashMap<>();

    /**
     * Registers a new {@link Affinity} with the given color, directOpposite, majorOpposites and minorOpposites on the given modid and name
     *
     * @param modid          : the modid to use for registration
     * @param name           : the name to use for registration
     * @param color          : the color to use for the new {@link Affinity}
     * @param directOpposite : the direct opposite to use for the new {@link Affinity}
     * @param majorOpposites : the major opposites to use for the new {@link Affinity}
     * @param minorOpposites : the minor opposites to use for the new {@link Affinity}
     * @return a registry object for the registered {@link Affinity}
     */
    public static RegistryObject<Affinity> registerAffinity(String modid, String name, int color, ResourceLocation directOpposite, Set<ResourceLocation> majorOpposites, Set<ResourceLocation> minorOpposites) {
        return registerAffinity(new ResourceLocation(modid, name), color, directOpposite, majorOpposites, minorOpposites);
    }

    /**
     * Registers a new {@link Affinity} with the given color, directOpposite, majorOpposites and minorOpposites on the given {@link ResourceLocation}
     *
     * @param key            : the {@link ResourceLocation} to use for registration
     * @param color          : the color to use for the new {@link Affinity}
     * @param directOpposite : the direct opposite to use for the new {@link Affinity}
     * @param majorOpposites : the major opposites to use for the new {@link Affinity}
     * @param minorOpposites : the minor opposites to use for the new {@link Affinity}
     * @return a registry object for the registered {@link Affinity}
     */
    public static RegistryObject<Affinity> registerAffinity(ResourceLocation key, int color, ResourceLocation directOpposite, Set<ResourceLocation> majorOpposites, Set<ResourceLocation> minorOpposites) {
        return registerAffinity(key, () -> new Affinity(color)
                .setDirectOpposite(directOpposite)
                .addMajorOpposite(majorOpposites)
                .addMinorOpposite(minorOpposites));
    }

    /**
     * Registers a supplied {@link Affinity} on the given {@link ResourceLocation}
     *
     * @param key      : the {@link ResourceLocation} to use for registration
     * @param affinity : the supplier of the {@link Affinity} to register
     * @return a registry object for the registered {@link Affinity}
     */
    public static RegistryObject<Affinity> registerAffinity(ResourceLocation key, Supplier<? extends Affinity> affinity) {
        Objects.requireNonNull(key);
        RegistryObject<Affinity> ret = RegistryObject.of(key, RegistryHandler.getAffinityRegistry());
        if (entries.putIfAbsent(ret, () -> affinity.get().setRegistryName(key)) != null) {
            throw new IllegalArgumentException("Duplicate registration " + key.toString());
        }
        return ret;
    }

    static void onAffinityRegister(RegistryEvent.Register<?> event) {
        if (event.getGenericType() == RegistryHandler.getAffinityRegistry().getRegistrySuperType()) {
            IForgeRegistry<Affinity> reg = (IForgeRegistry<Affinity>)event.getRegistry();
            for (Map.Entry<RegistryObject<Affinity>, Supplier<? extends Affinity>> e : entries.entrySet()) {
                reg.register(e.getValue().get());
                e.getKey().updateReference(reg);
            }
        }
    }
}
