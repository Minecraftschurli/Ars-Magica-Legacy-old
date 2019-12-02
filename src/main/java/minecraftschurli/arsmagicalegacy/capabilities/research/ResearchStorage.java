package minecraftschurli.arsmagicalegacy.capabilities.research;

import com.google.common.collect.ImmutableList;
import minecraftschurli.arsmagicalegacy.api.spell.skill.Skill;
import minecraftschurli.arsmagicalegacy.util.SpellRegistry;
import net.minecraft.util.ResourceLocation;

import java.util.*;

/**
 * @author Minecraftschurli
 * @version 2019-11-14
 */
public class ResearchStorage implements IResearchStorage {
    private Map<String, Integer> points = new HashMap<>();
    private List<Skill> learned = new ArrayList<>();

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
    public boolean knows(Skill skill) {
        return this.learned.contains(skill);
    }

    @Override
    public boolean canLearn(Skill skill) {
        return Arrays.stream(skill.getParents()).map(ResourceLocation::new).map(SpellRegistry.SKILL_REGISTRY::getValue).allMatch(this::knows);
    }

    @Override
    public List<Skill> getLearned() {
        return ImmutableList.copyOf(this.learned);
    }

    @Override
    public void learn(Skill location) {
        this.learned.add(location);
    }

    @Override
    public void forget(Skill location) {
        this.learned.remove(location);
    }
}
