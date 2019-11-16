package minecraftschurli.arsmagicalegacy.capabilities.burnout;

/**
 * @author Minecraftschurli
 * @version 2019-11-08
 */
public interface IBurnoutStorage {
    float getBurnout();
    float getMaxBurnout();
    boolean setBurnout(float amount);
    void setMaxBurnout(float amount);
    void decrease(float amount);
    boolean increase(float amount);
}
