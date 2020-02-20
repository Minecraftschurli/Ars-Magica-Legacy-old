package minecraftschurli.arsmagicalegacy.api.advancements;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import minecraftschurli.arsmagicalegacy.api.capability.CapabilityHelper;
import minecraftschurli.arsmagicalegacy.api.skill.Skill;
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
        return new Instance(json.get("skill").getAsString());
    }

    public void trigger(ServerPlayerEntity player) {
        this.func_227070_a_(player.getAdvancements(), (instance) -> instance.test(player));
    }

    public static class Instance extends CriterionInstance {

        private ResourceLocation skill;

        public Instance(String name) {
            this(ResourceLocation.tryCreate(name));
        }

        public Instance(ResourceLocation skill) {
            super(SkillLearnedTrigger.ID);
            this.skill = skill;
        }

        public static SkillLearnedTrigger.Instance forSkill(Skill skill) {
            return forSkill(ArsMagicaAPI.getSkillRegistry().getKey(skill));
        }

        public static SkillLearnedTrigger.Instance forSkill(ResourceLocation skill) {
            return new SkillLearnedTrigger.Instance(skill);
        }

        public static SkillLearnedTrigger.Instance forSkill(String skill) {
            return new SkillLearnedTrigger.Instance(skill);
        }

        public JsonElement serialize() {
            JsonObject jsonobject = new JsonObject();
            jsonobject.addProperty("skill", skill.toString());
            return jsonobject;
        }

        public boolean test(ServerPlayerEntity player) {
            return CapabilityHelper.knows(player, skill);
        }
    }
}
