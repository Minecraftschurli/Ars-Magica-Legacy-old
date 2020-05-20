package minecraftschurli.arsmagicalegacy.api.capability;

/**
 * @author Minecraftschurli
 * @version 2019-12-02
 */
public interface IMagicStorage {
    int getCurrentLevel();

    void levelUp();

    void setLevel(int level);

    default float getMaxXP() {
        if (getCurrentLevel() < 0) return 0;
        int x = getCurrentLevel();
        return (float) ((Math.atan(x * 0.2 + 0.6) * 3.0) + (Math.pow(x, 2) * 0.006) + (x * -0.01) - 1.2);
    }

    void setXp(float xp);

    float getXp();

    default void setFrom(IMagicStorage old) {
        this.setLevel(old.getCurrentLevel());
        this.setXp(old.getXp());
    }
}
