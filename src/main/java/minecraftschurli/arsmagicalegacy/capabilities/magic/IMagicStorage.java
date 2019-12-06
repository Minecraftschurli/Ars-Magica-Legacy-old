package minecraftschurli.arsmagicalegacy.capabilities.magic;

/**
 * @author Minecraftschurli
 * @version 2019-12-02
 */
public interface IMagicStorage {
    int getCurrentLevel();

    void levelUp();

    void setLevel(int level);

    default void setFrom(IMagicStorage old) {
        this.setLevel(old.getCurrentLevel());
    }
}
