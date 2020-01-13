package minecraftschurli.arsmagicalegacy.api;

import minecraftschurli.arsmagicalegacy.*;
import minecraftschurli.arsmagicalegacy.api.skill.*;

import java.util.*;
import java.util.function.Supplier;

/**
 * @author Minecraftschurli
 * @version 2019-12-04
 */
public class SkillPointRegistry {
    public static final Map<Integer, SkillPoint> SKILL_POINT_REGISTRY = new HashMap<>();

    public static Supplier<SkillPoint> registerSkillPoint(int tier, SkillPoint skillPoint) {
        if (SKILL_POINT_REGISTRY.containsKey(tier)) {
            ArsMagicaLegacy.LOGGER.error("Skillpoint with tier " + tier + " already registered!");
        } else {
            SKILL_POINT_REGISTRY.put(tier, skillPoint);
            if (!skillPoint.setTier(tier)) {
                ArsMagicaLegacy.LOGGER.error("Tier already set!");
            }
        }
        return () -> SKILL_POINT_REGISTRY.get(tier);
    }

    public static SkillPoint getSkillPointFromTier(int tier) {
        return SKILL_POINT_REGISTRY.getOrDefault(tier, null);
    }
}
