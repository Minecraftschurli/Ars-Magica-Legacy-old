package minecraftschurli.arsmagicalegacy.objects.ability;

import java.util.Collection;
import minecraftschurli.arsmagicalegacy.api.affinity.AbstractAffinityAbility;
import minecraftschurli.arsmagicalegacy.api.affinity.Affinity;
import minecraftschurli.arsmagicalegacy.api.capability.CapabilityHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;

public final class JumpHeightAbility extends AbstractAffinityAbility {
    @Override
    public float getMinimumDepth() {
        return 0.5f;
    }

    @Override
    public ResourceLocation getAffinity() {
        return Affinity.AIR;
    }

    @Override
    public void applyFall(PlayerEntity player, LivingFallEvent event) {
        event.setDistance((float) (event.getDistance() - (2 * CapabilityHelper.getAffinityDepth(player, Affinity.AIR))));
        if (event.getDistance() < 0) event.setDistance(0);
    }

    @Override
    public void applyJump(PlayerEntity player, LivingEvent.LivingJumpEvent event) {
        player.addVelocity(0, CapabilityHelper.getAffinityDepth(player, Affinity.AIR) * 0.35f, 0);
    }

    @Override
    public Collection<AbilityListenerType> registerListeners(Collection<AbilityListenerType> types) {
        types.add(AbilityListenerType.JUMP);
        return types;
    }
}
