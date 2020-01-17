package minecraftschurli.arsmagicalegacy.capabilities.mana;

/**
 * @author Minecraftschurli
 * @version 2019-11-07
 */
public interface IManaStorage {
    float getMana();

    float getMaxMana();

    void setMaxMana(float amount);

    boolean setMana(float amount);

    boolean increase(float amount);

    boolean decrease(float amount);

    default void setFrom(IManaStorage old) {
        this.setMaxMana(old.getMaxMana());
        this.setMana(old.getMana());
    }
}
