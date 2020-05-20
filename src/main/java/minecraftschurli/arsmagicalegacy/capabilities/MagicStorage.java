package minecraftschurli.arsmagicalegacy.capabilities;

import minecraftschurli.arsmagicalegacy.api.capability.IMagicStorage;

/**
 * @author Minecraftschurli
 * @version 2019-12-02
 */
public class MagicStorage implements IMagicStorage {
    private int level = -1;
    private float xp = 0;

    @Override
    public int getCurrentLevel() {
        return level;
    }

    @Override
    public void levelUp() {
        level++;
    }

    @Override
    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public float getXp() {
        return xp;
    }

    @Override
    public void setXp(float xp) {
        if (xp < 0) {
            this.xp = 0;
            return;
        }
        this.xp = xp;
    }
}
