package minecraftschurli.arsmagicalegacy.api.util;

import java.util.List;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

/**
 * @author Minecraftschurli
 * @version 2020-03-21
 */
public interface TooltipEnabled {

    void addTooltip(List<ITextComponent> tooltip);

    interface Auto extends TooltipEnabled {

        String getTooltipTranslationKey();

        @Override
        default void addTooltip(List<ITextComponent> tooltip) {
            String key = getTooltipTranslationKey();
            tooltip.addAll(TextUtils.getLocalized(key));
            TranslationTextComponent subtitle = new TranslationTextComponent(key + ".subtitle");
            if (subtitle.getKey().equals(subtitle.getFormattedText())) return;
            tooltip.addAll(TextUtils.breakUp(subtitle));
        }

    }

}
