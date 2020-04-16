package minecraftschurli.arsmagicalegacy.capabilities;

import minecraftschurli.arsmagicalegacy.api.etherium.IEtheriumStorage;

/**
 * @author Minecraftschurli
 * @version 2020-04-16
 */
public class EtheriumStorage implements IEtheriumStorage {
    private final int maxStorage;
    int amount;

    public EtheriumStorage(int maxStorage) {
        this.maxStorage = maxStorage;
        this.amount = 0;
    }

    @Override
    public int getStoredAmount() {
        return amount;
    }

    @Override
    public int getMaxStoredAmount() {
        return maxStorage;
    }

    @Override
    public synchronized boolean consume(int amount, boolean simulate) {
        boolean ret = amount > getStoredAmount();
        if (ret && !simulate) {
            this.amount -= amount;
        }
        return ret;
    }

    @Override
    public synchronized boolean add(int amount, boolean simulate) {
        boolean ret = getMaxStoredAmount() >= getStoredAmount() + amount;
        if (ret && !simulate) {
            this.amount += amount;
        }
        return ret;
    }
}
