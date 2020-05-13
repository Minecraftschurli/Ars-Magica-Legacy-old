package minecraftschurli.arsmagicalegacy.api.capability;

import java.util.Map;
import java.util.stream.Collectors;
import javafx.util.Pair;
import minecraftschurli.arsmagicalegacy.api.affinity.Affinity;
import minecraftschurli.arsmagicalegacy.api.registry.RegistryHandler;
import net.minecraft.util.ResourceLocation;

/**
 * @author Minecraftschurli
 * @version 2020-02-13
 */
public interface IAffinityStorage {
    default double getAffinityDepth(Affinity affinity) {
        return getAffinityDepth(affinity.getRegistryName());
    }

    double getAffinityDepth(ResourceLocation affinity);

    default void setAffinityDepth(Affinity affinity, double depth) {
        this.setAffinityDepth(affinity.getRegistryName(), depth);
    }

    void setAffinityDepth(ResourceLocation affinity, double depth);

    default Map<Affinity, Double> getAffinities() {
        return getAffinitiesInternal().entrySet().stream().map(entry -> new Pair<>(RegistryHandler.getAffinityRegistry().getValue(entry.getKey()), entry.getValue())).filter(entry -> entry.getKey() != null).collect(Collectors.toMap(Pair::getKey, Pair::getValue));
    }

    Map<ResourceLocation, Double> getAffinitiesInternal();

    void setLocked(boolean b);

    boolean isLocked();

    void setFrom(IAffinityStorage other);
}
