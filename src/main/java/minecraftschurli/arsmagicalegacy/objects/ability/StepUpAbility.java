package minecraftschurli.arsmagicalegacy.objects.ability;

import java.util.Collection;
import minecraftschurli.arsmagicalegacy.api.affinity.AbstractAffinityAbility;
import minecraftschurli.arsmagicalegacy.api.affinity.Affinity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;

public final class StepUpAbility extends AbstractAffinityAbility {
    @Override
    public float getMinimumDepth() {
        return 0.5f;
    }

    @Override
    public ResourceLocation getAffinity() {
        return Affinity.LIGHTNING;
    }

    @Override
    public void applyTick(PlayerEntity player) {
        player.stepHeight = 1.014f;
    }

    @Override
    public void removeEffects(PlayerEntity player) {
        if(player.stepHeight == 1.014f) player.stepHeight = 0.6f;
    }

    @Override
    public Collection<AbilityListenerType> registerListeners(Collection<AbilityListenerType> types) {
        types.add(AbilityListenerType.TICK);
        return types;
    }
}
