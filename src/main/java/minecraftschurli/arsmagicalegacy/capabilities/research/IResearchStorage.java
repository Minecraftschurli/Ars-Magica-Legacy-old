package minecraftschurli.arsmagicalegacy.capabilities.research;

import minecraftschurli.arsmagicalegacy.api.SkillPointRegistry;
import minecraftschurli.arsmagicalegacy.api.skill.Skill;
import minecraftschurli.arsmagicalegacy.api.skill.SkillPoint;

import java.util.List;

/**
 * @author Minecraftschurli
 * @version 2019-11-14
 */
public interface IResearchStorage {
    int get(int tier);

    boolean use(int tier, int count);

    default boolean use(int tier) {
        return use(tier, 1);
    }

    default void add(int tier) {
        this.add(tier, 1);
    }

    void add(int tier, int count);

    void set(int tier, int count);

    default void setFrom(IResearchStorage old) {
        for (SkillPoint type : SkillPointRegistry.SKILL_POINT_REGISTRY.values()) {
            this.set(type.getTier(), old.get(type.getTier()));
        }
        this.forgetAll();
        old.getLearned().forEach(this::learn);
    }

    List<Skill> getLearned();

    void learn(Skill location);

    void forget(Skill location);

    void forgetAll();

    boolean knows(Skill skill);

    boolean canLearn(Skill skill);
}
