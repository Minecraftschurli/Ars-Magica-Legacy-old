package minecraftschurli.arsmagicalegacy.api;

import minecraftschurli.arsmagicalegacy.api.affinity.*;
import net.minecraft.util.*;
import net.minecraftforge.event.*;
import net.minecraftforge.fml.*;
import net.minecraftforge.registries.*;

import java.util.*;
import java.util.function.*;

/**
 * @author Minecraftschurli
 * @version 2020-02-13
 */
public class AffinityRegistry {
    private static final Map<RegistryObject<Affinity>, Supplier<? extends Affinity>> entries = new LinkedHashMap<>();

    public static RegistryObject<Affinity> registerAffinity(String modid, String name, int color, ResourceLocation directOpposite, Set<ResourceLocation> majorOpposites, Set<ResourceLocation> minorOpposites) {
        return registerAffinity(new ResourceLocation(modid, name), color, directOpposite, majorOpposites, minorOpposites);
    }

    public static RegistryObject<Affinity> registerAffinity(ResourceLocation key, int color, ResourceLocation directOpposite, Set<ResourceLocation> majorOpposites, Set<ResourceLocation> minorOpposites) {
        return registerAffinity(key, () -> new Affinity(color)
                .setDirectOpposite(directOpposite)
                .addMajorOpposite(majorOpposites)
                .addMinorOpposite(minorOpposites));
    }

    public static RegistryObject<Affinity> registerAffinity(ResourceLocation key, Supplier<? extends Affinity> affinity) {
        Objects.requireNonNull(key);
        RegistryObject<Affinity> ret = RegistryObject.of(key, ArsMagicaAPI.getAffinityRegistry());
        if (entries.putIfAbsent(ret, () -> affinity.get().setRegistryName(key)) != null) {
            throw new IllegalArgumentException("Duplicate registration " + key.toString());
        }
        return ret;
    }

    static void onAffinityRegister(RegistryEvent.Register<?> event) {
        if (event.getGenericType() == ArsMagicaAPI.getAffinityRegistry().getRegistrySuperType()) {
            @SuppressWarnings("unchecked")
            IForgeRegistry<Affinity> reg = (IForgeRegistry<Affinity>)event.getRegistry();
            for (Map.Entry<RegistryObject<Affinity>, Supplier<? extends Affinity>> e : entries.entrySet()) {
                reg.register(e.getValue().get());
                e.getKey().updateReference(reg);
            }
        }
    }
}
