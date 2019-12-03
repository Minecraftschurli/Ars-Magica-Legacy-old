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
    private Map<Integer, Integer> points = new HashMap<>();
    private List<Skill> learned = new ArrayList<>();

    @Override
    public int get(int tier) {
        return this.points.getOrDefault(tier, 0);
    }

    @Override
    public boolean use(int tier, int count) {
        if (this.points.containsKey(tier)) {
            int newVal = this.points.get(tier) - count;
            if (newVal < 0) return false;
            this.points.put(tier, newVal);
            return true;
        } else
            return false;
    }

    @Override
    public void add(int tier, int count) {
        this.points.computeIfPresent(tier, (s, integer) -> integer + count);
    }

    @Override
    public void set(int tier, int count) {
        this.points.put(tier, count);
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
