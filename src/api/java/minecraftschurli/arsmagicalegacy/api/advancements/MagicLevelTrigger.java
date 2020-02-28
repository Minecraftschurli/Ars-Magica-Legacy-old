package minecraftschurli.arsmagicalegacy.api.advancements;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import minecraftschurli.arsmagicalegacy.api.capability.CapabilityHelper;
import net.minecraft.advancements.criterion.AbstractCriterionTrigger;
import net.minecraft.advancements.criterion.CriterionInstance;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;

/**
 * @author Minecraftschurli
 * @version 2020-02-19
 */
public class MagicLevelTrigger extends AbstractCriterionTrigger<MagicLevelTrigger.Instance> {
    private static final ResourceLocation ID = new ResourceLocation(ArsMagicaAPI.MODID, "magic_level");

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    public Instance deserializeInstance(JsonObject json, JsonDeserializationContext context) {
        MinMaxBounds.IntBound bound = MinMaxBounds.IntBound.fromJson(json.get("bound"));
        return new Instance(bound);
    }

    public void trigger(ServerPlayerEntity player) {
        this.func_227070_a_(player.getAdvancements(), (instance) -> instance.test(player));
    }

    public static class Instance extends CriterionInstance {
        private final MinMaxBounds.IntBound bound;

        private Instance(MinMaxBounds.IntBound bound) {
            super(MagicLevelTrigger.ID);
            this.bound = bound;
        }

        public static MagicLevelTrigger.Instance forLevel(int level) {
            return new MagicLevelTrigger.Instance(MinMaxBounds.IntBound.atLeast(level));
        }

        public JsonElement serialize() {
            JsonObject jsonobject = new JsonObject();
            jsonobject.add("bound", this.bound.serialize());
            return jsonobject;
        }

        public boolean test(ServerPlayerEntity player) {
            return this.bound.test(CapabilityHelper.getCurrentLevel(player));
        }
    }
}
