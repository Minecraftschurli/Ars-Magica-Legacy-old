package minecraftschurli.arsmagicalegacy.api.data;


import minecraftschurli.arsmagicalegacy.api.SpellRegistry;
import minecraftschurli.arsmagicalegacy.api.skill.Skill;
import minecraftschurli.arsmagicalegacy.api.skill.SkillPoint;
import minecraftschurli.arsmagicalegacy.api.spell.AbstractSpellPart;

import java.util.function.Supplier;

/**
 * @author Minecraftschurli
 * @version 2020-01-15
 */
public interface ArsMagicaLanguagePlugin {
    default void addSpellPart(Supplier<? extends AbstractSpellPart> part, String name, String description) {
        add(part.get(), name, description);
    }

    default void add(AbstractSpellPart part, String name, String description) {
        add(SpellRegistry.getSkillFromPart(part), name, description);
    }

    default void addSkill(Supplier<? extends Skill> skill, String name, String description) {
        add(skill.get(), name, description);
    }

    default void add(Skill skill, String name, String description) {
        add(skill.getTranslationKey() + ".name", name);
        add(skill.getTranslationKey() + ".occulusdesc", description);
    }

    default void addSkillPoint(Supplier<SkillPoint> point, String name) {
        add(point.get(), name);
    }

    default void add(SkillPoint point, String name) {
        add(point.getTranslationKey(), name);
    }

    void add(String key, String value);
}
