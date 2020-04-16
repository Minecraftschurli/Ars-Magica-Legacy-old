package minecraftschurli.arsmagicalegacy.api.etherium;

/**
 * @author Minecraftschurli
 * @version 2020-04-16
 */
public interface IEtheriumStorage {
    int getStoredAmount();
    int getMaxStoredAmount();
    boolean consume(int amount, boolean simulate);
    boolean add(int amount, boolean simulate);
}
