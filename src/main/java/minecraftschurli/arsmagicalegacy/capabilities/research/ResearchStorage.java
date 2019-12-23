package minecraftschurli.arsmagicalegacy.capabilities.research;

import com.google.common.collect.*;
import minecraftschurli.arsmagicalegacy.api.*;
import minecraftschurli.arsmagicalegacy.api.skill.*;
import net.minecraft.util.*;

import javax.annotation.*;
import java.util.*;

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
    public boolean knows(ResourceLocation skill) {
        return this.learned.contains(skill);
    }

    @Override
    public boolean canLearn(Skill skill) {
        return Arrays.stream(skill.getParents()).map(ResourceLocation::new).allMatch(this::knows) && !knows(skill);
    }

    @Override
    public List<Skill> getLearnedSkills() {
        return this.learned.stream().filter(ArsMagicaLegacyAPI.getSkillRegistry()::containsKey).map(ArsMagicaLegacyAPI.getSkillRegistry()::getValue).collect(ImmutableList.toImmutableList());
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
