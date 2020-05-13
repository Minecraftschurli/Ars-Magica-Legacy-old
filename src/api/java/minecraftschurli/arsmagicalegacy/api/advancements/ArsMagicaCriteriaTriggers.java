package minecraftschurli.arsmagicalegacy.api.advancements;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.ICriterionTrigger;
import net.minecraft.util.ResourceLocation;

/**
 * @author Minecraftschurli
 * @version 2020-02-19
 */
public class ArsMagicaCriteriaTriggers {
    private static final Map<ResourceLocation,ICriterionTrigger<?>> CRITERION_TRIGGERS = new HashMap<>();
    public static final MagicLevelTrigger MAGIC_LEVEL = register(new MagicLevelTrigger());
    public static final ManaLevelTrigger MANA_LEVEL = register(new ManaLevelTrigger());
    public static final SkillLearnedTrigger SKILL_LEARNED = register(new SkillLearnedTrigger());
    public static final SilverSkillTrigger SILVER_SKILL = register(new SilverSkillTrigger());

    public static void registerDefaults() {
        for (ICriterionTrigger<?> criterionTrigger : CRITERION_TRIGGERS.values()) CriteriaTriggers.register(criterionTrigger);
    }

    public static <T extends ICriterionTrigger<?>> T register(T criterion) {
        if (CRITERION_TRIGGERS.containsKey(criterion.getId())) throw new IllegalArgumentException("Duplicate criterion " + criterion.getId());
        CRITERION_TRIGGERS.put(criterion.getId(), criterion);
        return criterion;
    }
}
