package minecraftschurli.arsmagicalegacy.capabilities.mana;

import minecraftschurli.arsmagicalegacy.Config;

/**
 * @author Minecraftschurli
 * @version 2019-11-07
 */
public class ManaStorage implements IManaStorage {

    private float maxMana = Config.COMMON.DEFAULT_MAX_MANA.get();
    private float mana = maxMana;

    @Override
    public float getMana() {
        return mana;
    }

    @Override
    public float getMaxMana() {
        return maxMana;
    }

    public void setMaxMana(float maxMana) {
        if (maxMana >= 0)
            this.maxMana = Math.max(maxMana, Config.COMMON.DEFAULT_MAX_MANA.get());
    }

    public boolean setMana(float mana) {
        if (mana < 0)
            return false;
        this.mana = Math.min(mana, this.maxMana);
        return true;
    }

    @Override
    public boolean increase(float amount) {
        if (amount <= 0) return false;
        return this.setMana(this.getMana() + amount);
    }

    @Override
    public boolean decrease(float amount) {
        if (amount <= 0) return false;
        return this.setMana(this.getMana() - amount);
    }

    @Override
    public String toString() {
        return "ManaStorage{maxMana=" + maxMana + ", mana=" + mana + "}";
    }
}
