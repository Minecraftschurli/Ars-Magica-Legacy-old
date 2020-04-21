package minecraftschurli.arsmagicalegacy.api.util;

import net.minecraft.util.Util;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.registries.IForgeRegistryEntry;

/**
 * @author Minecraftschurli
 * @version 2020-04-21
 */
public interface ITranslatable<T extends IForgeRegistryEntry<T>> extends IForgeRegistryEntry<T> {
    String getType();

    default String getTranslationKey() {
        return Util.makeTranslationKey(getType(), getRegistryName());
    }

    default ITextComponent getDisplayName() {
        return new TranslationTextComponent(getTranslationKey());
    }

    interface WithDescription<T extends IForgeRegistryEntry<T>> extends ITranslatable<T> {
        @Override
        default ITextComponent getDisplayName() {
            return new TranslationTextComponent(getTranslationKey()+".name");
        }

        default ITextComponent getDescription() {
            return new TranslationTextComponent(getTranslationKey()+".description");
        }
    }
}
