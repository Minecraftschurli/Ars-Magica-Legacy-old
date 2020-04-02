package minecraftschurli.arsmagicalegacy.capabilities;

import com.google.common.collect.ImmutableMap;
import minecraftschurli.arsmagicalegacy.api.capability.IAbilityStorage;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Minecraftschurli
 * @version 2020-03-06
 */
public class AbilityStorage implements IAbilityStorage {
    private final Map<String, Integer> cooldownStore = new HashMap<>();
    private final Map<ResourceLocation, Boolean> stateStore = new HashMap<>();

    @Override
    public Map<String, Integer> getCooldowns() {
        return ImmutableMap.copyOf(cooldownStore);
    }

    @Override
    public void addCooldown(String key, int value) {
        cooldownStore.put(key, value);
    }

    @Override
    public boolean getState(ResourceLocation ability) {
        return stateStore.get(ability);
    }

    @Override
    public void setState(ResourceLocation ability, boolean state) {
        stateStore.put(ability, state);
    }

    @Override
    public void setFrom(IAbilityStorage abilityCapability) {
        cooldownStore.clear();
        stateStore.clear();
        if (abilityCapability instanceof AbilityStorage) {
            AbilityStorage storage = (AbilityStorage) abilityCapability;
            cooldownStore.putAll(storage.cooldownStore);
            stateStore.putAll(storage.stateStore);
        }
    }
}
