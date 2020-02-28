package minecraftschurli.arsmagicalegacy.api.advancements;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
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
public class ManaLevelTrigger extends AbstractCriterionTrigger<ManaLevelTrigger.Instance> {
    private static final ResourceLocation ID = new ResourceLocation(ArsMagicaAPI.MODID, "mana_level");

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    public Instance deserializeInstance(JsonObject json, JsonDeserializationContext context) {
        return new Instance(json.get("percentage").getAsFloat(), json.get("min").getAsBoolean());
    }

    public void trigger(ServerPlayerEntity player) {
        this.func_227070_a_(player.getAdvancements(), (instance) -> instance.test(player));
    }

    public static class Instance extends CriterionInstance {

        private final float percentage;
        private final boolean min;

        public Instance(float percentage, boolean min) {
            super(ManaLevelTrigger.ID);
            this.percentage = percentage;
            this.min = min;
        }

        public static ManaLevelTrigger.Instance forPercentageMin(float percentage) {
            return new ManaLevelTrigger.Instance(percentage, true);
        }

        public static ManaLevelTrigger.Instance forPercentageMax(float percentage) {
            return new ManaLevelTrigger.Instance(percentage, false);
        }

        public JsonElement serialize() {
            JsonObject jsonobject = new JsonObject();
            jsonobject.addProperty("percentage", percentage);
            jsonobject.addProperty("min", min);
            return jsonobject;
        }

        public boolean test(ServerPlayerEntity player) {
            if (min)
                return CapabilityHelper.getMana(player) >= CapabilityHelper.getMaxMana(player)*percentage;
            else
                return CapabilityHelper.getMana(player) <= CapabilityHelper.getMaxMana(player)*percentage;
        }
    }
}
