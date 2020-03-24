package minecraftschurli.arsmagicalegacy.api.registry;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;
import minecraftschurli.arsmagicalegacy.api.etherium.EtheriumType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.IForgeRegistry;

/**
 * @author Minecraftschurli
 * @version 2020-03-04
 */
public class EtheriumRegistry {
    private static final Map<RegistryObject<EtheriumType>, Supplier<? extends EtheriumType>> etherium = new LinkedHashMap<>();

    static void onEtheriumRegister(RegistryEvent.Register<EtheriumType> event) {
        if (event.getGenericType() != EtheriumType.class)
            return;
        IForgeRegistry<EtheriumType> reg = event.getRegistry();
        for (Map.Entry<RegistryObject<EtheriumType>, Supplier<? extends EtheriumType>> e : etherium.entrySet()) {
            reg.register(e.getValue().get());
            e.getKey().updateReference(reg);
        }
    }

    /**
     * Registers a new {@link EtheriumType} with the given color on the given name
     *
     * @param name  : the name to use for registration
     * @param color : the color to use for the new {@link EtheriumType}
     * @return a registry object for the registered {@link EtheriumType}
     */
    public static RegistryObject<EtheriumType> registerEtherium(String name, int color) {
        return registerEtherium(ModLoadingContext.get().getActiveNamespace(), name, () -> new EtheriumType(color));
    }

    /**
     * Registers a supplied {@link EtheriumType} on the given name
     *
     * @param name     : the name to use for registration
     * @param supplier : the supplier of the {@link EtheriumType} to register
     * @return a registry object for the registered {@link EtheriumType}
     */
    public static RegistryObject<EtheriumType> registerEtherium(String name, Supplier<EtheriumType> supplier) {
        return registerEtherium(ModLoadingContext.get().getActiveNamespace(), name, supplier);
    }

    /**
     * Registers a new {@link EtheriumType} with the given color on the given name and modid
     *
     * @param modid : the modid to use for registration
     * @param name  : the name to use for registration
     * @param color : the color to use for the new {@link EtheriumType}
     * @return a registry object for the registered {@link EtheriumType}
     */
    public static RegistryObject<EtheriumType> registerEtherium(String modid, String name, int color) {
        return registerEtherium(modid, name, () -> new EtheriumType(color));
    }

    /**
     * Registers a supplied {@link EtheriumType} on the given name and modid
     *
     * @param modid    : the modid to use for registration
     * @param name     : the name to use for registration
     * @param supplier : the supplier of the {@link EtheriumType} to register
     * @return a registry object for the registered {@link EtheriumType}
     */
    public static RegistryObject<EtheriumType> registerEtherium(String modid, String name, Supplier<EtheriumType> supplier) {
        ResourceLocation rl = new ResourceLocation(modid, name);
        return registerEtherium(rl, supplier);
    }

    /**
     * Registers a supplied {@link EtheriumType} on the given {@link ResourceLocation}
     *
     * @param key      : the {@link ResourceLocation} to use for registration
     * @param supplier : the supplier of the {@link EtheriumType} to register
     * @return a registry object for the registered {@link EtheriumType}
     */
    public static RegistryObject<EtheriumType> registerEtherium(ResourceLocation key, Supplier<EtheriumType> supplier) {
        RegistryObject<EtheriumType> ret = RegistryObject.of(key, RegistryHandler.getEtheriumRegistry());
        if (EtheriumRegistry.etherium.putIfAbsent(ret, () -> supplier.get().setRegistryName(key)) != null) {
            throw new IllegalArgumentException("Duplicate registration " + key);
        }
        return RegistryObject.of(key, RegistryHandler.getEtheriumRegistry());
    }
}
