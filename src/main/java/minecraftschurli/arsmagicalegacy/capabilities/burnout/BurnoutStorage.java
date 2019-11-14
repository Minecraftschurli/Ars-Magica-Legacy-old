package minecraftschurli.arsmagicalegacy.capabilities.burnout;

/**
 * @author Minecraftschurli
 * @version 2019-11-08
 */
public class BurnoutStorage implements IBurnoutStorage {
    int burnout;
    int maxBurnout;

    @Override
    public int getBurnout() {
        return burnout;
    }

    @Override
    public int getMaxBurnout() {
        return maxBurnout;
    }

    @Override
    public void decrease(int amount) {
        if (amount > 0)
            this.burnout = Math.max(0, getBurnout() - amount);
    }

    @Override
    public void increase(int amount) {
        if (amount > 0)
            this.burnout = Math.min(getBurnout() + amount, getMaxBurnout());
    }
}
