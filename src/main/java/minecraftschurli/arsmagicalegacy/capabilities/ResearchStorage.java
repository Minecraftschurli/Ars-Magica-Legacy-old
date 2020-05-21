package minecraftschurli.arsmagicalegacy.capabilities;

import com.google.common.collect.ImmutableList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nonnull;
import minecraftschurli.arsmagicalegacy.api.capability.IResearchStorage;
import minecraftschurli.arsmagicalegacy.api.registry.RegistryHandler;
import minecraftschurli.arsmagicalegacy.api.skill.Skill;
import net.minecraft.util.ResourceLocation;

/**
 * @author Minecraftschurli
 * @version 2019-11-14
 */
public class ResearchStorage implements IResearchStorage {
    private final Map<Integer, Integer> points = new HashMap<>();
    private final Set<ResourceLocation> learned = new HashSet<>();

    public ResearchStorage() {
        set(0, 2);
    }

    @Override
    public int get(int tier) {
        return points.getOrDefault(tier, 0);
    }

    @Override
    public boolean use(int tier, int count) {
        if (points.containsKey(tier)) {
            int newVal = points.get(tier) - count;
            if (newVal < 0) return false;
            points.put(tier, newVal);
            return true;
        }
        return false;
    }

    @Override
    public void add(int tier, int count) {
        points.compute(tier, (s, integer) -> (integer != null ? integer : 0) + count);
    }

    @Override
    public void set(int tier, int count) {
        points.put(tier, count);
    }

    @Override
    public void forgetAll() {
        learned.clear();
    }

    @Override
    public boolean knows(ResourceLocation skill) {
        return learned.contains(skill);
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
        return learned.stream().filter(RegistryHandler.getSkillRegistry()::containsKey).map(RegistryHandler.getSkillRegistry()::getValue).collect(ImmutableList.toImmutableList());
    }

    @Override
    public List<ResourceLocation> getLearned() {
        return ImmutableList.copyOf(learned);
    }

    @Override
    public void learn(@Nonnull ResourceLocation location) {
        learned.add(location);
    }

    @Override
    public void forget(@Nonnull ResourceLocation location) {
        learned.remove(location);
    }
}
