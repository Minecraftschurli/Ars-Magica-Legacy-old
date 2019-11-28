package minecraftschurli.arsmagicalegacy.api.spellsystem;

import net.minecraft.util.text.TextFormatting;

import java.util.Arrays;
import java.util.List;

/**
 * @author Minecraftschurli
 * @version 2019-11-27
 */
public class SkillPoint {
    public static List<SkillPoint> TYPES = Arrays.asList(SkillPoint.SILVER_POINT, SkillPoint.SKILL_POINT_1, SkillPoint.SKILL_POINT_2, SkillPoint.SKILL_POINT_3, SkillPoint.SKILL_POINT_4, SkillPoint.SKILL_POINT_5, SkillPoint.SKILL_POINT_6);
    public static final SkillPoint SILVER_POINT = new SkillPoint("Silver", TextFormatting.GRAY, 0x999999, -1, -1).disableRender();
    public static final SkillPoint SKILL_POINT_1 = new SkillPoint("Blue", TextFormatting.BLUE, 0x0000ff, 0, 1);
    public static final SkillPoint SKILL_POINT_2 = new SkillPoint("Green", TextFormatting.GREEN, 0x00ff00, 20, 2);
    public static final SkillPoint SKILL_POINT_3 = new SkillPoint("Red", TextFormatting.RED, 0xff0000, 30, 2);
    public static final SkillPoint SKILL_POINT_4 = new SkillPoint("Yellow", TextFormatting.YELLOW, 0xffff00, 40, 3);
    public static final SkillPoint SKILL_POINT_5 = new SkillPoint("Magenta", TextFormatting.LIGHT_PURPLE, 0xff00ff, 50, 3);
    public static final SkillPoint SKILL_POINT_6 = new SkillPoint("Cyan", TextFormatting.AQUA, 0x00ffff, 60, 4);

    private final int color, minEarnLevel, levelsForPoint;
    private final String name;
    private final TextFormatting chatColor;

    private boolean render = true;

    private SkillPoint(String name, TextFormatting chatColor, int color, int minEarnLevel, int levelsForPoint) {
        this.color = color;
        this.name = name;
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

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
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
