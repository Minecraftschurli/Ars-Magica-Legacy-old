package minecraftschurli.arsmagicalegacy.util;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
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
            tooltip.addAll(Arrays.stream(new TranslationTextComponent(key).getFormattedText().split("\n")).map(StringTextComponent::new).collect(Collectors.toList()));
            TranslationTextComponent subtitle = new TranslationTextComponent(key + ".subtitle");
            if (subtitle.getKey().equals(subtitle.getFormattedText())) return;
            tooltip.addAll(Arrays.stream(subtitle.getFormattedText().split("\n")).map(StringTextComponent::new).collect(Collectors.toList()));
        }
    }
}
