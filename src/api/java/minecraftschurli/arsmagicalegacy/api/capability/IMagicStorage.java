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
        return (float)Math.pow(getCurrentLevel() * 0.25f, 1.5f);
    }

    void setXp(float xp);

    float getXp();

    default void setFrom(IMagicStorage old) {
        this.setLevel(old.getCurrentLevel());
        this.setXp(old.getXp());
    }
}
