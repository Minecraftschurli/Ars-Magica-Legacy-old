package minecraftschurli.arsmagicalegacy.api.capability;

import java.util.Map;

/**
 * @author Minecraftschurli
 * @version 2020-03-06
 */
public interface IAbilityStorage {
    Map<String, Integer> getCooldowns();

    void addCooldown(String key, int value);
}
