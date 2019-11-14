package minecraftschurli.arsmagicalegacy.capabilities.research;

/**
 * @author Minecraftschurli
 * @version 2019-11-14
 */
public class ResearchPointsStorage implements IResearchPointsStorage {
    private int red, green, blue;

    void setRed(int count) {
        this.red = count;
    }

    void setGreen(int count) {
        this.green = count;
    }

    void setBlue(int count) {
        this.blue = count;
    }

    @Override
    public int getRed() {
        return red;
    }

    @Override
    public int getGreen() {
        return green;
    }

    @Override
    public int getBlue() {
        return blue;
    }

    @Override
    public boolean useRed(int count) {
        if (this.getRed() - count >= 0) {
            this.red -= count;
            return true;
        }
        return false;
    }

    @Override
    public boolean useGreen(int count) {
        if (this.getGreen() - count >= 0) {
            this.green -= count;
            return true;
        }
        return false;
    }

    @Override
    public boolean useBlue(int count) {
        if (this.getBlue() - count >= 0) {
            this.blue -= count;
            return true;
        }
        return false;
    }

    @Override
    public void addRed(int count) {
        this.red+=count;
    }

    @Override
    public void addGreen(int count) {
        this.green+=count;
    }

    @Override
    public void addBlue(int count) {
        this.blue+=count;
    }
}
