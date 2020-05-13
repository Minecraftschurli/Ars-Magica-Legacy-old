package minecraftschurli.arsmagicalegacy.api.advancements;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import java.util.Objects;
import javax.annotation.Nonnull;
import minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import minecraftschurli.arsmagicalegacy.api.capability.CapabilityHelper;
import minecraftschurli.arsmagicalegacy.api.registry.RegistryHandler;
import minecraftschurli.arsmagicalegacy.api.skill.Skill;
import minecraftschurli.arsmagicalegacy.api.skill.SkillPoint;
import net.minecraft.advancements.criterion.AbstractCriterionTrigger;
import net.minecraft.advancements.criterion.CriterionInstance;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;

/**
 * @author Minecraftschurli
 * @version 2020-02-28
 */
public class SilverSkillTrigger extends AbstractCriterionTrigger<SilverSkillTrigger.Instance> {
    private static final ResourceLocation ID = new ResourceLocation(ArsMagicaAPI.MODID, "silver_skill");

    @Nonnull
    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Nonnull
    @Override
    public Instance deserializeInstance(JsonObject json, JsonDeserializationContext context) {
        return new Instance();
    }

    public void trigger(ServerPlayerEntity player) {
        this.func_227070_a_(player.getAdvancements(), (instance) -> instance.test(player));
    }

    public static class Instance extends CriterionInstance {
        private Instance() {
            super(ID);
        }

        public static Instance create() {
            return new Instance();
        }

        public boolean test(ServerPlayerEntity player) {
            return CapabilityHelper.getLearned(player).stream().map(RegistryHandler.getSkillRegistry()::getValue).filter(Objects::nonNull).map(Skill::getPoint).mapToInt(SkillPoint::getTier).filter(i -> (-1) == i).findFirst().isPresent();
        }
    }
}
