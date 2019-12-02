package minecraftschurli.arsmagicalegacy.capabilities.research;

import com.google.common.collect.ImmutableList;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Minecraftschurli
 * @version 2019-11-14
 */
public class ResearchStorage implements IResearchStorage {
    private Map<String, Integer> points = new HashMap<>();
    private List<ResourceLocation> learned = new ArrayList<>();

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

    @Override
    public void forgetAll() {
        this.learned.clear();
    }

    @Override
    public List<ResourceLocation> getLearned() {
        return ImmutableList.copyOf(this.learned);
    }

    @Override
    public void learn(ResourceLocation location) {
        this.learned.add(location);
    }

    @Override
    public void forget(ResourceLocation location) {
        this.learned.remove(location);
    }
}
