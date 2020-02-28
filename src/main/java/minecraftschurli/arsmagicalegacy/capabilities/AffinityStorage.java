package minecraftschurli.arsmagicalegacy.capabilities;

import com.google.common.collect.ImmutableMap;
import minecraftschurli.arsmagicalegacy.api.capability.IAffinityStorage;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Minecraftschurli
 * @version 2020-02-13
 */
public class AffinityStorage implements IAffinityStorage {
    private Map<ResourceLocation, Double> store;
    private boolean locked;

    public AffinityStorage() {
        this.store = new HashMap<>();
        this.locked = false;
    }

    @Override
    public double getAffinityDepth(ResourceLocation affinity) {
        return this.store.containsKey(affinity) ? this.store.get(affinity) : 0;
    }

    @Override
    public void setAffinityDepth(ResourceLocation affinity, double depth) {
        this.store.put(affinity, depth);
    }

    @Override
    public Map<ResourceLocation, Double> getAffinitiesInternal() {
        return ImmutableMap.copyOf(this.store);
    }

    @Override
    public void setLocked(boolean b) {
        this.locked = b;
    }

    @Override
    public boolean isLocked() {
        return this.locked;
    }

    @Override
    public void setFrom(IAffinityStorage other) {
        this.store.clear();
        for (Map.Entry<ResourceLocation, Double> entry : other.getAffinitiesInternal().entrySet()) {
            this.store.put(entry.getKey(), entry.getValue());
        }
        this.setLocked(other.isLocked());
    }
}
