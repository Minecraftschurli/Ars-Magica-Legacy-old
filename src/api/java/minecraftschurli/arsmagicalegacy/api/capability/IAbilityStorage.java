package minecraftschurli.arsmagicalegacy.api.capability;

import net.minecraft.util.ResourceLocation;

import java.util.Map;

/**
 * @author Minecraftschurli
 * @version 2020-03-06
 */
public interface IAbilityStorage {
    Map<String, Integer> getCooldowns();

    void addCooldown(String key, int value);

    boolean getState(ResourceLocation ability);

    void setState(ResourceLocation ability, boolean state);

    void setFrom(IAbilityStorage abilityCapability);
}
