package minecraftschurli.arsmagicalegacy.capabilities.mana;

/**
 * @author Minecraftschurli
 * @version 2019-11-07
 */
public class ManaStorage implements IManaStorage {

    int maxMana;
    int mana;

    @Override
    public int getMana() {
        return mana;
    }

    @Override
    public int getMaxMana() {
        return maxMana;
    }

    boolean setMana(int mana) {
        if (mana < 0)
            return false;
        this.mana = Math.min(mana, this.maxMana);
        return true;
    }

    void setMaxMana(int maxMana) {
        if (maxMana >= 0)
            this.maxMana = maxMana;
    }

    @Override
    public boolean increase(int amount) {
        if (amount <= 0) return false;
        return this.setMana(this.getMana() + amount);
    }

    @Override
    public boolean decrease(int amount) {
        if (amount <= 0) return false;
        return this.setMana(this.getMana() - amount);
    }
}
