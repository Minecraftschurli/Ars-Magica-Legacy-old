package minecraftschurli.arsmagicalegacy.api;

import minecraftschurli.arsmagicalegacy.api.skill.SkillPoint;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * @author Minecraftschurli
 * @version 2019-12-04
 */
public class SkillPointRegistry {
    public static final Map<Integer, SkillPoint> SKILL_POINT_REGISTRY = new HashMap<>();

    public static Supplier<SkillPoint> registerSkillPoint(int tier, SkillPoint skillPoint) {
        if (SKILL_POINT_REGISTRY.containsKey(tier)) {
            ArsMagicaAPI.LOGGER.error("Skillpoint with tier " + tier + " already registered!");
        } else {
            SKILL_POINT_REGISTRY.put(tier, skillPoint);
            if (!skillPoint.setTier(tier)) {
                ArsMagicaAPI.LOGGER.error("Tier already set!");
            }
        }
        return () -> SKILL_POINT_REGISTRY.get(tier);
    }

    public static SkillPoint getSkillPointFromTier(int tier) {
        return SKILL_POINT_REGISTRY.getOrDefault(tier, null);
    }
}
