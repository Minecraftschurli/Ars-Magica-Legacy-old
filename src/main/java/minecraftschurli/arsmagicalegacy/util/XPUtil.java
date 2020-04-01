package minecraftschurli.arsmagicalegacy.util;

import net.minecraft.entity.player.PlayerEntity;

public final class XPUtil {
    public static int deductXP(int amount, PlayerEntity player) {
        int i = player.experienceTotal;
        if (amount > i) amount = i;
        player.experienceTotal -= amount;
        player.experience = 0;
        player.experienceLevel = 0;
        int addedXP = 0;
        while (addedXP < player.experienceTotal) {
            int toAdd = player.experienceTotal - addedXP;
            toAdd = Math.min(toAdd, player.xpBarCap());
            player.experience = toAdd / (float) player.xpBarCap();
            if (player.experience == 1f) {
                player.experienceLevel++;
                player.experience = 0;
            }
            addedXP += toAdd;
        }
        return amount;
    }

    public static int getLevelFromXP(float xp) {
        int level = 0;
        xp = (int) Math.floor(xp);
        while (true) {
            int cap = xpBarCap(level);
            xp -= cap;
            if (xp < 0) break;
            level++;
        }
        return level;
    }

    public static int getXPFromLevel(int level) {
        int totalXP = 0;
        for (int i = 0; i < level; i++) totalXP += xpBarCap(i);
        return totalXP;
    }

    public static int xpBarCap(int experienceLevel) {
        return experienceLevel >= 30 ? 112 + (experienceLevel - 30) * 9 : (experienceLevel >= 15 ? 37 + (experienceLevel - 15) * 5 : 7 + experienceLevel * 2);
    }
}
