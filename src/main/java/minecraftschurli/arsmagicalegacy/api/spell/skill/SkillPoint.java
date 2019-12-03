package minecraftschurli.arsmagicalegacy.api.spell.skill;

import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.registries.ForgeRegistryEntry;

/**
 * @author Minecraftschurli
 * @version 2019-11-27
 */
public class SkillPoint extends ForgeRegistryEntry<SkillPoint> {
    private final int color, minEarnLevel, levelsForPoint;
    private final TextFormatting chatColor;
    private final int tier;

    private boolean render = true;

    public SkillPoint(int tier, TextFormatting chatColor, int color, int minEarnLevel, int levelsForPoint) {
        this.tier = tier;
        this.color = color;
        this.minEarnLevel = minEarnLevel;
        this.levelsForPoint = levelsForPoint;
        this.chatColor = chatColor;
    }

    public int getColor() {
        return color;
    }

    public int getLevelsForPoint() {
        return levelsForPoint;
    }

    public int getMinEarnLevel() {
        return minEarnLevel;
    }

    public int getTier() {
        return this.tier;
    }

    public String getDisplayName() {
        return new TranslationTextComponent("skillpoint." + tier).getFormattedText();
    }

    @Override
    public String toString() {
        return getRegistryName().toString();
    }

    public TextFormatting getChatColor() {
        return chatColor;
    }

    public boolean canRender() {
        return render;
    }

    public SkillPoint disableRender() {
        render = false;
        return this;
    }
}
