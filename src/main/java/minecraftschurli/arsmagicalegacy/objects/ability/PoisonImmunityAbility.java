package minecraftschurli.arsmagicalegacy.objects.ability;

import java.util.Collection;
import minecraftschurli.arsmagicalegacy.api.affinity.AbstractAffinityAbility;
import minecraftschurli.arsmagicalegacy.api.affinity.Affinity;
import minecraftschurli.arsmagicalegacy.api.capability.CapabilityHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public final class PoisonImmunityAbility extends AbstractAffinityAbility {
    @Override
    public float getMinimumDepth() {
        return 0.25f;
    }

    @Override
    public ResourceLocation getAffinity() {
        return Affinity.ENDER;
    }

    @Override
    public void applyHurt(PlayerEntity player, LivingHurtEvent event) {
        if (event.getSource() == DamageSource.MAGIC || event.getSource() == DamageSource.WITHER)
            event.setAmount(event.getAmount() * (float) (1 - (0.75f * CapabilityHelper.getAffinityDepth(player, Affinity.ENDER))));
    }

    @Override
    public Collection<AbilityListenerType> registerListeners(Collection<AbilityListenerType> types) {
        types.add(AbilityListenerType.HURT);
        return types;
    }
}
