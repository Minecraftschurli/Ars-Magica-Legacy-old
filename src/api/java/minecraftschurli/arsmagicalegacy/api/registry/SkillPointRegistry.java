package minecraftschurli.arsmagicalegacy.api.registry;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import minecraftschurli.arsmagicalegacy.api.skill.SkillPoint;

/**
 * @author Minecraftschurli
 * @version 2019-12-04
 */
public class SkillPointRegistry {
    public static final Map<Integer, SkillPoint> SKILL_POINT_REGISTRY = new HashMap<>();

    /**
     * Registers a {@link SkillPoint} for the given tier
     * fails if there is already a {@link SkillPoint} registered for the given tier
     *
     * @param tier       : the tier to register the {@link SkillPoint} for
     * @param skillPoint : the {@link SkillPoint} to register
     * @return a {@link Supplier<SkillPoint>} of the {@link SkillPoint} registered for the given tier
     */
    public static Supplier<SkillPoint> registerSkillPoint(int tier, SkillPoint skillPoint) {
        if (!SKILL_POINT_REGISTRY.containsKey(tier)) {
            SKILL_POINT_REGISTRY.put(tier, skillPoint);
            if (!skillPoint.setTier(tier)) ArsMagicaAPI.LOGGER.error("Tier already set!");
        }
        return () -> SKILL_POINT_REGISTRY.get(tier);
    }

    /**
     * Returns the {@link SkillPoint} for the given tier
     *
     * @param tier : the tier to get the {@link SkillPoint} for
     * @return the {@link SkillPoint} registered for the given tier, null if there is nothing registered for the given tier
     */
    public static SkillPoint getSkillPointFromTier(int tier) {
        return SKILL_POINT_REGISTRY.getOrDefault(tier, null);
    }
}
