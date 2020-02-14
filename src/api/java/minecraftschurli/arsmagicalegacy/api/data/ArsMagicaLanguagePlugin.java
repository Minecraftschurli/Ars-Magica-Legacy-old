package minecraftschurli.arsmagicalegacy.api.data;


import minecraftschurli.arsmagicalegacy.api.*;
import minecraftschurli.arsmagicalegacy.api.affinity.*;
import minecraftschurli.arsmagicalegacy.api.skill.*;
import minecraftschurli.arsmagicalegacy.api.spell.*;

import java.util.function.*;

/**
 * @author Minecraftschurli
 * @version 2020-01-15
 */
public interface ArsMagicaLanguagePlugin {
    /**
     * Creates a new language provider, based on the vanilla language provider.
     * @param part        the data generator
     * @param name        the mod id to use (e. g. ArsMagicaLegacy)
     * @param description the file name
     */
    default void addSpellPart(Supplier<? extends AbstractSpellPart> part, String name, String description) {
        add(part.get(), name, description);
    }

    /**
     * Adds a spell part translation
     * @param part        the spell part supplier to add the translation for
     * @param name        the name for the spell part
     * @param description the description for the spell part
     */
    default void add(AbstractSpellPart part, String name, String description) {
        add(SpellRegistry.getSkillFromPart(part), name, description);
    }

    /**
     * Adds a skill translation
     * @param skill       the skill supplier to add the translation for
     * @param name        the name for the skill
     * @param description the description for the skill
     */
    default void addSkill(Supplier<? extends Skill> skill, String name, String description) {
        add(skill.get(), name, description);
    }

    /**
     * Adds a skill translation
     * @param skill       the skill to add the translation for
     * @param name        the name for the skill
     * @param description the description for the skill
     */
    default void add(Skill skill, String name, String description) {
        add(skill.getTranslationKey() + ".name", name);
        add(skill.getTranslationKey() + ".occulusdesc", description);
    }

    /**
     * Adds a skill point translation
     * @param point the skill point supplier to add the translation for
     * @param name  the name for the skill point
     */
    default void addSkillPoint(Supplier<SkillPoint> point, String name) {
        add(point.get(), name);
    }

    /**
     * Adds a skill point translation
     * @param point the skill point to add the translation for
     * @param name  the name for the skill point
     */
    default void add(SkillPoint point, String name) {
        add(point.getTranslationKey(), name);
    }

    /**
     * Adds an affinities point translation
     * @param affinity the skill point supplier to add the translation for
     * @param name     the name for the skill point
     */
    default void addAffinity(Supplier<SkillPoint> affinity, String name) {
        add(affinity.get(), name);
    }

    /**
     * Adds an affinities point translation
     * @param affinity the skill point to add the translation for
     * @param name     the name for the skill point
     */
    default void add(Affinity affinity, String name) {
        add(affinity.getTranslationKey(), name);
    }

    void add(String key, String value);
}
