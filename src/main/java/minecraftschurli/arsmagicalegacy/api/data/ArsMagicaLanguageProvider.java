package minecraftschurli.arsmagicalegacy.api.data;

import minecraftschurli.arsmagicalegacy.api.*;
import minecraftschurli.arsmagicalegacy.api.skill.*;
import minecraftschurli.arsmagicalegacy.api.spell.*;
import net.minecraft.data.*;
import net.minecraftforge.common.data.*;

import java.util.function.*;

/**
 * @author Minecraftschurli
 * @version 2020-01-05
 */
public abstract class ArsMagicaLanguageProvider extends LanguageProvider {
    public ArsMagicaLanguageProvider(DataGenerator gen, String modid, String locale) {
        super(gen, modid, locale);
    }

    protected void addSpellPart(Supplier<? extends AbstractSpellPart> part, String name, String description) {
        add(part.get(), name, description);
    }

    protected void add(AbstractSpellPart part, String name, String description) {
        add(SpellRegistry.getSkillFromPart(part), name, description);
    }

    protected void addSkill(Supplier<? extends Skill> skill, String name, String description) {
        add(skill.get(), name, description);
    }

    protected void add(Skill skill, String name, String description) {
        add(skill.getTranslationKey() + ".name", name);
        add(skill.getTranslationKey() + ".occulusdesc", description);
    }

    protected void addSkillPoint(Supplier<SkillPoint> point, String name) {
        add(point.get(), name);
    }

    protected void add(SkillPoint point, String name) {
        add(point.getTranslationKey(), name);
    }
}
