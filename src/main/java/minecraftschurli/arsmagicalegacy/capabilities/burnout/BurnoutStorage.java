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
}
