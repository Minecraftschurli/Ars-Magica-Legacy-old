package minecraftschurli.arsmagicalegacy.capabilities.magic;

/**
 * @author Minecraftschurli
 * @version 2019-12-02
 */
public class MagicStorage implements IMagicStorage {
    private int level;
    private float xp;

    @Override
    public int getCurrentLevel() {
        return this.level;
    }

    @Override
    public void levelUp() {
        this.level++;
    }

    @Override
    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public void setXp(float xp) {
        this.xp = xp;
    }

    @Override
    public float getXp() {
        return this.xp;
    }
}
