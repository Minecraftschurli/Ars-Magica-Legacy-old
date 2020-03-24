package minecraftschurli.arsmagicalegacy.capabilities;

import com.google.common.collect.ImmutableMap;
import java.util.HashMap;
import java.util.Map;
import minecraftschurli.arsmagicalegacy.api.capability.IAbilityStorage;

/**
 * @author Minecraftschurli
 * @version 2020-03-06
 */
public class AbilityStorage implements IAbilityStorage {
    private final Map<String, Integer> store = new HashMap<>();

    @Override
    public Map<String, Integer> getCooldowns() {
        return ImmutableMap.copyOf(store);
    }

    @Override
    public void addCooldown(String key, int value) {
        store.put(key, value);
    }
}
