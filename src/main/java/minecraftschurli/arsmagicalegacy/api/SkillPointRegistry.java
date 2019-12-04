package minecraftschurli.arsmagicalegacy.api;

import minecraftschurli.arsmagicalegacy.ArsMagicaLegacy;
import minecraftschurli.arsmagicalegacy.api.skill.SkillPoint;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Minecraftschurli
 * @version 2019-12-04
 */
public class SkillPointRegistry {
    public static final Map<Integer, SkillPoint> SKILL_POINT_REGISTRY = new HashMap<>();

    public static SkillPoint registerSkillPoint(int tier, SkillPoint skillPoint) {
        if (SKILL_POINT_REGISTRY.containsKey(tier)){
            ArsMagicaLegacy.LOGGER.error("Skill with tier "+tier+" already registered!");
            return null;
        } else {
            SKILL_POINT_REGISTRY.put(tier, skillPoint);
            if (!skillPoint.setTier(tier)) {
                ArsMagicaLegacy.LOGGER.error("Tier already set!");
                return null;
            }
            return skillPoint;
        }
    }

    public static SkillPoint getSkillPointFromTier(int tier) {
        return SKILL_POINT_REGISTRY.getOrDefault(tier, null);
    }
}
