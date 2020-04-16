package minecraftschurli.arsmagicalegacy.api.advancements;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.Objects;
import java.util.stream.Stream;
import minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import minecraftschurli.arsmagicalegacy.api.capability.CapabilityHelper;
import net.minecraft.advancements.criterion.AbstractCriterionTrigger;
import net.minecraft.advancements.criterion.CriterionInstance;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;

/**
 * @author Minecraftschurli
 * @version 2020-02-19
 */
public class SkillLearnedTrigger extends AbstractCriterionTrigger<SkillLearnedTrigger.Instance> {
    private static final ResourceLocation ID = new ResourceLocation(ArsMagicaAPI.MODID, "skill_learned");

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    public Instance deserializeInstance(JsonObject json, JsonDeserializationContext context) {
        return new Instance(ImmutableList.copyOf(json.get("skills").getAsJsonArray()).stream().map(JsonElement::getAsString).collect(ImmutableList.toImmutableList()), json.get("function").getAsString());
    }

    public void trigger(ServerPlayerEntity player) {
        this.func_227070_a_(player.getAdvancements(), (instance) -> instance.test(player));
    }

    public static class Instance extends CriterionInstance {

        private final ImmutableList<String> skills;
        private final String function;

        private Instance(ImmutableList<String> skills, String function) {
            super(SkillLearnedTrigger.ID);
            this.skills = skills;
            this.function = function;
        }

        public static SkillLearnedTrigger.Instance anyOf(String... skill) {
            return new Instance(ImmutableList.copyOf(skill), "or");
        }

        public static SkillLearnedTrigger.Instance allOf(String... skill) {
            return new Instance(ImmutableList.copyOf(skill), "and");
        }

        public static SkillLearnedTrigger.Instance of(String skill) {
            return new Instance(ImmutableList.of(skill), "one");
        }

        public JsonElement serialize() {
            JsonObject jsonobject = new JsonObject();
            JsonArray skills = new JsonArray();
            for (String skill : this.skills) {
                skills.add(skill);
            }
            jsonobject.add("skills", skills);
            jsonobject.addProperty("function", function);
            return jsonobject;
        }

        public boolean test(ServerPlayerEntity player) {
            if (function.equals("one"))
                return CapabilityHelper.knows(player, ResourceLocation.tryCreate(skills.get(0)));
            Stream<Boolean> s = skills.stream()
                    .map(ResourceLocation::tryCreate)
                    .filter(Objects::nonNull)
                    .map(rl -> CapabilityHelper.knows(player, rl));
            if (function.equals("or"))
                return s.anyMatch(aBoolean -> aBoolean);
            else if (function.equals("and"))
                return s.allMatch(aBoolean -> aBoolean);
            else return false;
        }
    }
}
