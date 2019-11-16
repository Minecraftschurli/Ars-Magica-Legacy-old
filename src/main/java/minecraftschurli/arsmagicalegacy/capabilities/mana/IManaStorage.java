package minecraftschurli.arsmagicalegacy.capabilities.mana;

/**
 * @author Minecraftschurli
 * @version 2019-11-07
 */
public interface IManaStorage {
    float getMana();
    float getMaxMana();
    boolean setMana(float amount);
    void setMaxMana(float amount);
    boolean increase(float amount);
    boolean decrease(float amount);
}
