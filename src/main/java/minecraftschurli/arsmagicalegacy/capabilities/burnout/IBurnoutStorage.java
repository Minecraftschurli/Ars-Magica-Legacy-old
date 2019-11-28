package minecraftschurli.arsmagicalegacy.capabilities.burnout;

/**
 * @author Minecraftschurli
 * @version 2019-11-08
 */
public interface IBurnoutStorage {
    float getBurnout();

    float getMaxBurnout();

    void setMaxBurnout(float amount);

    boolean setBurnout(float amount);

    void decrease(float amount);

    boolean increase(float amount);

    default void setFrom(IBurnoutStorage old) {
        this.setMaxBurnout(old.getMaxBurnout());
        this.setBurnout(old.getBurnout());
    }
}
