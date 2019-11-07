package minecraftschurli.arsmagicalegacy.util;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.*;
import java.util.function.Supplier;

/**
 * @author Georg Burkl
 * @version 2019-11-07
 */
public class CustomDeferredRegister<T extends IForgeRegistryEntry<T>> {
    protected final IForgeRegistry<T> type;
    protected final String modid;
    protected final Map<RegistryObject<T>, Supplier<? extends T>> entries = new LinkedHashMap<>();
    protected final Set<RegistryObject<T>> entriesView = Collections.unmodifiableSet(entries.keySet());

    public CustomDeferredRegister(IForgeRegistry<T> reg, String modid)
    {
        this.type = reg;
        this.modid = modid;
    }

    /**
     * Adds a new supplier to the list of entries to be registered, and returns a RegistryObject that will be populated with the created entry automatically.
     *
     * @param name The new entry's name, it will automatically have the modid prefixed.
     * @param sup A factory for the new entry, it should return a new instance every time it is called.
     * @return A RegistryObject that will be updated with when the entries in the registry change.
     */
    @SuppressWarnings("unchecked")
    public <I extends T> RegistryObject<I> register(final String name, final Supplier<? extends I> sup)
    {
        Objects.requireNonNull(name);
        Objects.requireNonNull(sup);
        final ResourceLocation key = new ResourceLocation(modid, name);
        RegistryObject<I> ret = RegistryObject.of(key, this.type);
        if (entries.putIfAbsent((RegistryObject<T>) ret, () -> sup.get().setRegistryName(key)) != null) {
            throw new IllegalArgumentException("Duplicate registration " + name);
        }
        return ret;
    }

    public <I extends T> RegistryObject<I> register(final ResourceLocation key, final Supplier<? extends I> sup)
    {
        Objects.requireNonNull(key);
        Objects.requireNonNull(sup);
        RegistryObject<I> ret = RegistryObject.of(key, this.type);
        if (entries.putIfAbsent((RegistryObject<T>) ret, () -> sup.get().setRegistryName(key)) != null) {
            throw new IllegalArgumentException("Duplicate registration " + key.getPath());
        }
        return ret;
    }

    /**
     * Adds our event handler to the specified event bus, this MUST be called in order for this class to function.
     * See the example usage.
     *
     * @param bus The Mod Specific event bus.
     */
    public void register(IEventBus bus)
    {
        bus.addListener(this::addEntries);
    }

    /**
     * @return The unmodifiable view of registered entries. Useful for bulk operations on all values.
     */
    public Collection<RegistryObject<T>> getEntries()
    {
        return entriesView;
    }

    private void addEntries(RegistryEvent.Register<?> event)
    {
        if (event.getGenericType() == this.type.getRegistrySuperType())
        {
            @SuppressWarnings("unchecked")
            IForgeRegistry<T> reg = (IForgeRegistry<T>)event.getRegistry();
            for (Map.Entry<RegistryObject<T>, Supplier<? extends T>> e : entries.entrySet())
            {
                reg.register(e.getValue().get());
                e.getKey().updateReference(reg);
            }
        }
    }
}
