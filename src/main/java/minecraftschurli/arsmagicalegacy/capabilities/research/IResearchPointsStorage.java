package minecraftschurli.arsmagicalegacy.capabilities.research;

import minecraftschurli.arsmagicalegacy.api.spellsystem.SkillPoint;

/**
 * @author Minecraftschurli
 * @version 2019-11-14
 */
public interface IResearchPointsStorage {
    int get(String type);
    boolean use(String type, int count);
    default void add(String type) {
        this.add(type, 1);
    }
    void add(String type, int count);
    void set(String type, int count);

    default void setFrom(IResearchPointsStorage old) {
        for (SkillPoint type : SkillPoint.TYPES) {
            this.set(type.getName(), old.get(type.getName()));
        }
    }
}
