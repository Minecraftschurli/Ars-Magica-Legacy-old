package minecraftschurli.arsmagicalegacy.capabilities;

import com.google.common.collect.ImmutableMap;
import java.util.HashMap;
import java.util.Map;
import minecraftschurli.arsmagicalegacy.api.capability.IAffinityStorage;
import net.minecraft.util.ResourceLocation;

/**
 * @author Minecraftschurli
 * @version 2020-02-13
 */
public class AffinityStorage implements IAffinityStorage {
    private final Map<ResourceLocation, Double> store;
    private boolean locked;

    public AffinityStorage() {
        store = new HashMap<>();
        locked = false;
    }

    @Override
    public double getAffinityDepth(ResourceLocation affinity) {
        return store.containsKey(affinity) ? store.get(affinity) : 0;
    }

    @Override
    public void setAffinityDepth(ResourceLocation affinity, double depth) {
        store.put(affinity, depth);
    }

    @Override
    public Map<ResourceLocation, Double> getAffinitiesInternal() {
        return ImmutableMap.copyOf(store);
    }

    @Override
    public boolean isLocked() {
        return locked;
    }

    @Override
    public void setLocked(boolean b) {
        locked = b;
    }

    @Override
    public void setFrom(IAffinityStorage other) {
        store.clear();
        for (Map.Entry<ResourceLocation, Double> entry : other.getAffinitiesInternal().entrySet())
            store.put(entry.getKey(), entry.getValue());
        setLocked(other.isLocked());
    }
}
