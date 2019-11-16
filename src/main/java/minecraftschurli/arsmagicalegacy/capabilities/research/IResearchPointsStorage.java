package minecraftschurli.arsmagicalegacy.capabilities.research;

/**
 * @author Minecraftschurli
 * @version 2019-11-14
 */
public interface IResearchPointsStorage {
    int getRed();
    int getGreen();
    int getBlue();
    boolean useRed(int count);
    boolean useGreen(int count);
    boolean useBlue(int count);
    default void addRed() {
        this.addRed(1);
    }
    default void addGreen() {
        this.addGreen(1);
    }
    default void addBlue() {
        this.addBlue(1);
    }
    void addRed(int count);
    void addGreen(int count);
    void addBlue(int count);
    void setRed(int count);
    void setGreen(int count);
    void setBlue(int count);
}
