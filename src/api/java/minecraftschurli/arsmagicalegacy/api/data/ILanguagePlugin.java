package minecraftschurli.arsmagicalegacy.api.data;

import java.util.function.Supplier;
import minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import minecraftschurli.arsmagicalegacy.api.registry.SpellRegistry;
import minecraftschurli.arsmagicalegacy.api.skill.SkillPoint;
import minecraftschurli.arsmagicalegacy.api.spell.AbstractSpellPart;
import minecraftschurli.arsmagicalegacy.api.util.ITranslatable;

/**
 * @author Minecraftschurli
 * @version 2020-01-15
 */
public interface ILanguagePlugin {
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
        addWithDescription(SpellRegistry.getSkillFromPart(part), name, description);
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
     * Adds the translation of the name and description of an {@link ITranslatable.WithDescription}
     * @param translatable the translatable to add the translation for
     * @param name         the name for the translatable
     * @param description  the description for the translatable
     */
    default void addTranslatableWithDescription(Supplier<? extends ITranslatable.WithDescription<?>> translatable, String name, String description) {
        addWithDescription(translatable.get(), name, description);
    }

    /**
     * Adds the translation of the name and description of an {@link ITranslatable.WithDescription}
     * @param translatable the translatable to add the translation for
     * @param name         the name for the translatable
     * @param description  the description for the translatable
     */
    default void addWithDescription(ITranslatable.WithDescription<?> translatable, String name, String description) {
        addWithDescription(translatable.getTranslationKey(), name, description);
    }

    /**
     * Adds the translation of an {@link ITranslatable}
     * @param translatable the translatable to add the translation for
     * @param name         the name for the translatable
     */
    default void addTranslatable(Supplier<? extends ITranslatable<?>> translatable, String name) {
        add(translatable.get(), name);
    }

    /**
     * Adds the translation of an {@link ITranslatable}
     * @param translatable the translatable to add the translation for
     * @param name         the name for the translatable
     */
    default void add(ITranslatable<?> translatable, String name) {
        add(translatable.getTranslationKey(), name);
    }

    /**
     * Adds the translation for anything that has a name and a description
     * @param key         the base translation key
     * @param name        the translated name
     * @param description the translated description
     */
    default void addWithDescription(String key, String name, String description) {
        add(key+".name", name);
        add(key+".description", description);
    }

    /**
     * Adds the translation for the given category key
     * @param key         the key of the category
     * @param name        the translated name
     * @param description the translated description
     */
    default void addCategory(String key, String name, String description){
        addWithDescription("compendium.categories."+key, name, description);
    }

    /**
     * Adds the translation for a text page
     * @param category    the page's category
     * @param entry       the page's entry
     * @param number      the page number
     * @param translation the translated text
     */
    default void addTextPage(String category, String entry, int number, String translation) {
        add("compendium.entries." + category + "." + entry + ".page." + number, translation);
    }

    /**
     * Adds the translation for a section's title
     * @param category    the section's category
     * @param entry       the section's actual name
     * @param translation the translated text
     */
    default void addSectionTitle(String category, String entry, String translation) {
        add("compendium.entries." + category + "." + entry + ".name", translation);
    }

    /**
     * Adds an advancement translation.
     * @param key   the advancement key
     * @param title the title translation
     * @param desc  the description translation
     */
    default void addAdvancement(String key, String title, String desc) {
        add(ArsMagicaAPI.MODID + ".advancements." + key + ".title", title);
        add(ArsMagicaAPI.MODID + ".advancements." + key + ".description", desc);
    }

    /**
     * Adds an item group translation.
     * @param key         the item group key
     * @param translation the item group translation
     */
    default void addItemGroup(String key, String translation) {
        add("itemGroup." + key, translation);
    }

    default void addPotion(String key, String potion, String splashPotion, String lingeringPotion, String tippedArrow) {
        add("item.minecraft.potion.effect." + key, potion);
        add("item.minecraft.splash_potion.effect." + key, splashPotion);
        add("item.minecraft.lingering_potion.effect." + key, lingeringPotion);
        add("item.minecraft.tipped_arrow.effect." + key, tippedArrow);
    }

    /**
     * Proxy method to make {@link net.minecraftforge.common.data.LanguageProvider#add(String,String) LanguageProvider#add} available to this interface
     */
    void add(String key, String value);
}
