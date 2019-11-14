package minecraftschurli.arsmagicalegacy.capabilities.burnout;

/**
 * @author Minecraftschurli
 * @version 2019-11-08
 */
public interface IBurnoutStorage {
    int getBurnout();
    int getMaxBurnout();
    void decrease(int amount);
    void increase(int amount);
}
