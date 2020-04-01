package minecraftschurli.arsmagicalegacy.capabilities;

import com.google.common.collect.ImmutableList;
import minecraftschurli.arsmagicalegacy.api.capability.IResearchStorage;
import minecraftschurli.arsmagicalegacy.api.registry.RegistryHandler;
import minecraftschurli.arsmagicalegacy.api.skill.Skill;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Minecraftschurli
 * @version 2019-11-14
 */
public class ResearchStorage implements IResearchStorage {
    private Map<Integer, Integer> points = new HashMap<>();
    private Set<ResourceLocation> learned = new HashSet<>();

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
        this.points.compute(tier, (s, integer) -> (integer != null ? integer : 0) + count);
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
    public boolean knows(ResourceLocation skill) {
        return this.learned.contains(skill);
    }

    @Override
    public boolean canLearn(Skill skill) {
        return Arrays.stream(skill.getParents()).map(ResourceLocation::new).allMatch(this::knows) && !knows(skill);
    }

    @Override
    public Map<Integer, Integer> getPoints() {
        return new HashMap<>(points);
    }

    @Override
    public List<Skill> getLearnedSkills() {
        return this.learned.stream().filter(RegistryHandler.getSkillRegistry()::containsKey).map(RegistryHandler.getSkillRegistry()::getValue).collect(ImmutableList.toImmutableList());
    }

    @Override
    public List<ResourceLocation> getLearned() {
        return ImmutableList.copyOf(this.learned);
    }

    @Override
    public void learn(@Nonnull ResourceLocation location) {
        this.learned.add(location);
    }

    @Override
    public void forget(@Nonnull ResourceLocation location) {
        this.learned.remove(location);
    }
}
