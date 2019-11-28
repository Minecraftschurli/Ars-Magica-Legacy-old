package minecraftschurli.arsmagicalegacy.capabilities.research;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Minecraftschurli
 * @version 2019-11-14
 */
public class ResearchPointsStorage implements IResearchPointsStorage {
    private Map<String, Integer> points = new HashMap<>();

    @Override
    public int get(String type) {
        return this.points.getOrDefault(type, 0);
    }

    @Override
    public boolean use(String type, int count) {
        if (this.points.containsKey(type)) {
            int newVal = this.points.get(type) - count;
            if (newVal < 0) return false;
            this.points.put(type, newVal);
            return true;
        } else
            return false;
    }

    @Override
    public void add(String type, int count) {
        this.points.computeIfPresent(type, (s, integer) -> integer + count);
    }

    @Override
    public void set(String type, int count) {
        this.points.put(type, count);
    }
}
