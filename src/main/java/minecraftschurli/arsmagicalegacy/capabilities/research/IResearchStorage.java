package minecraftschurli.arsmagicalegacy.capabilities.research;

import minecraftschurli.arsmagicalegacy.api.spell.skill.Skill;
import minecraftschurli.arsmagicalegacy.api.spell.skill.SkillPoint;

import java.util.List;

/**
 * @author Minecraftschurli
 * @version 2019-11-14
 */
public interface IResearchStorage {
    int get(String type);

    boolean use(String type, int count);

    default void add(String type) {
        this.add(type, 1);
    }

    void add(String type, int count);

    void set(String type, int count);

    default void setFrom(IResearchStorage old) {
        for (SkillPoint type : SkillPoint.TYPES) {
            this.set(type.getName(), old.get(type.getName()));
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
