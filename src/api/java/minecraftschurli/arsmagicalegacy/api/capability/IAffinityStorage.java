package minecraftschurli.arsmagicalegacy.api.capability;

import javafx.util.Pair;
import minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import minecraftschurli.arsmagicalegacy.api.affinity.Affinity;
import net.minecraft.util.ResourceLocation;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Minecraftschurli
 * @version 2020-02-13
 */
public interface IAffinityStorage {
    default double getAffinityDepth(Affinity affinity) {
        return this.getAffinityDepth(affinity.getRegistryName());
    }

    double getAffinityDepth(ResourceLocation affinity);

    default void setAffinityDepth(Affinity affinity, double depth) {
        this.setAffinityDepth(affinity.getRegistryName(), depth);
    }

    void setAffinityDepth(ResourceLocation affinity, double depth);

    default Map<Affinity, Double> getAffinities() {
        return this.getAffinitiesInternal()
                .entrySet()
                .stream()
                .map(entry -> new Pair<>(ArsMagicaAPI.getAffinityRegistry().getValue(entry.getKey()), entry.getValue()))
                .filter(entry -> entry.getKey() != null)
                .collect(Collectors.toMap(Pair::getKey, Pair::getValue));
    }

    Map<ResourceLocation, Double> getAffinitiesInternal();

    void setLocked(boolean b);

    boolean isLocked();

    void setFrom(IAffinityStorage other);
}
