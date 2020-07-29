package minecraftschurli.arsmagicalegacy.objects.ability;

import java.util.Collection;
import minecraftschurli.arsmagicalegacy.api.affinity.AbstractAffinityAbility;
import minecraftschurli.arsmagicalegacy.api.affinity.Affinity;
import minecraftschurli.arsmagicalegacy.api.capability.CapabilityHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public final class SolidBonesAbility extends AbstractAffinityAbility {
    @Override
    public float getMinimumDepth() {
        return 0.5f;
    }

    @Override
    public ResourceLocation getAffinity() {
        return Affinity.EARTH;
    }

    @Override
    public void applyFall(PlayerEntity player, LivingFallEvent event) {
        event.setDistance((float) (event.getDistance() + 1.25f * CapabilityHelper.getAffinityDepth(player, Affinity.EARTH)));
    }

    @Override
    public void applyHurt(PlayerEntity player, LivingHurtEvent event) {
        event.setAmount((float) (event.getAmount() - event.getAmount() * 0.1f * CapabilityHelper.getAffinityDepth(player, Affinity.EARTH)));
    }

    @Override
    public void applyTick(PlayerEntity player) {
        if (player.isInWater() && player.getMotion().y > -0.3f)
            player.addVelocity(0, -0.01f * CapabilityHelper.getAffinityDepth(player, Affinity.EARTH), 0);
    }

    @Override
    public Collection<AbilityListenerType> registerListeners(Collection<AbilityListenerType> types) {
        types.add(AbilityListenerType.FALL);
        types.add(AbilityListenerType.HURT);
        types.add(AbilityListenerType.TICK);
        return types;
    }
}
