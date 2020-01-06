package minecraftschurli.arsmagicalegacy.api.skill;

import net.minecraft.util.text.*;

/**
 * @author Minecraftschurli
 * @version 2019-11-27
 */
public class SkillPoint {
    private final int color, minEarnLevel, levelsForPoint;
    private final TextFormatting chatColor;
    private int tier = -2;

    private boolean render = true;

    public SkillPoint(TextFormatting chatColor, int color, int minEarnLevel, int levelsForPoint) {
        this.color = color;
        this.minEarnLevel = minEarnLevel;
        this.levelsForPoint = levelsForPoint;
        this.chatColor = chatColor;
    }

    public boolean setTier(int tier) {
        if (this.tier != -2) return false;
        this.tier = tier;
        return true;
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

    public ITextComponent getDisplayName() {
        return new TranslationTextComponent(getTranslationKey());
    }

    @Override
    public String toString() {
        return "skillpoint" + tier;
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

    public String getTranslationKey() {
        return "skillpoint." + getTier();
    }
}
