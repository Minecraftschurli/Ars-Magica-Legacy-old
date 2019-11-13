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

    public int getMaxMana() {
        return maxMana;
    }
}
