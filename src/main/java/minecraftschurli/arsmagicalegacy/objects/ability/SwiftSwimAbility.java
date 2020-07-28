package minecraftschurli.arsmagicalegacy.objects.ability;

import java.util.Collection;
import minecraftschurli.arsmagicalegacy.api.affinity.AbstractAffinityAbility;
import minecraftschurli.arsmagicalegacy.api.affinity.Affinity;
import minecraftschurli.arsmagicalegacy.api.capability.CapabilityHelper;
import minecraftschurli.arsmagicalegacy.init.ModEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ResourceLocation;

public final class SwiftSwimAbility extends AbstractAffinityAbility {
    @Override
    public float getMinimumDepth() {
        return 0.5f;
    }

    @Override
    public ResourceLocation getAffinity() {
        return Affinity.WATER;
    }

    @Override
    public void applyTick(PlayerEntity player) {
        if (player.isInWater() && !player.world.isRemote && (!player.isPotionActive(ModEffects.SWIFT_SWIM.get()) || player.getActivePotionEffect(ModEffects.SWIFT_SWIM.get()).getDuration() < 10)) player.addPotionEffect(new EffectInstance(ModEffects.SWIFT_SWIM.get(), 100, CapabilityHelper.getAffinityDepth(player, Affinity.WATER) > 0.75f ? 1 : 0));
    }

    @Override
    public Collection<AbilityListenerType> registerListeners(Collection<AbilityListenerType> types) {
        types.add(AbilityListenerType.TICK);
        return types;
    }
}
