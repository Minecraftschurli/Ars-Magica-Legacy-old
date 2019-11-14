package minecraftschurli.arsmagicalegacy.capabilities.mana;

/**
 * @author Minecraftschurli
 * @version 2019-11-07
 */
public interface IManaStorage {
    int getMana();
    int getMaxMana();
    boolean increase(int amount);
    boolean decrease(int amount);
}
