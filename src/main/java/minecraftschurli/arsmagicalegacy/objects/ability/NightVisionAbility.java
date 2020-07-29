package minecraftschurli.arsmagicalegacy.objects.ability;

import java.util.Collection;
import minecraftschurli.arsmagicalegacy.api.affinity.AbstractToggledAffinityAbility;
import minecraftschurli.arsmagicalegacy.api.affinity.Affinity;
import minecraftschurli.arsmagicalegacy.api.capability.CapabilityHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ResourceLocation;

public final class NightVisionAbility extends AbstractToggledAffinityAbility {
    @Override
    public float getMinimumDepth() {
        return 0.75f;
    }

    @Override
    public ResourceLocation getAffinity() {
        return Affinity.ENDER;
    }

    @Override
    public void applyTick(PlayerEntity player) {
        if (!player.world.isRemote && (!player.isPotionActive(Effects.NIGHT_VISION) || player.getActivePotionEffect(Effects.NIGHT_VISION).getDuration() <= 220))
            player.addPotionEffect(new EffectInstance(Effects.NIGHT_VISION, 300, 0, true, false));
    }

    @Override
    public Collection<AbilityListenerType> registerListeners(Collection<AbilityListenerType> types) {
        types.add(AbilityListenerType.TICK);
        return types;
    }

    @Override
    protected boolean isEnabled(PlayerEntity player) {
        return CapabilityHelper.getAbilityState(player, this.getRegistryName());
    }
}
