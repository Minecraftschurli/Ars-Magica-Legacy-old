package minecraftschurli.arsmagicalegacy.objects.ability;

import java.util.Collection;
import minecraftschurli.arsmagicalegacy.api.affinity.AbstractAffinityAbility;
import minecraftschurli.arsmagicalegacy.api.affinity.Affinity;
import minecraftschurli.arsmagicalegacy.api.capability.CapabilityHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public final class FireImmunityAbility extends AbstractAffinityAbility {
    @Override
    public float getMinimumDepth() {
        return 0;
    }

    @Override
    public ResourceLocation getAffinity() {
        return Affinity.FIRE;
    }

    @Override
    public void applyHurt(PlayerEntity player, LivingHurtEvent event) {
        if (event.getSource().isFireDamage()) event.setAmount((float) (event.getAmount() * (1 - (0.6f * CapabilityHelper.getAffinityDepth(player, Affinity.FIRE)))));
    }

    @Override
    public Collection<AbilityListenerType> registerListeners(Collection<AbilityListenerType> types) {
        types.add(AbilityListenerType.HURT);
        return types;
    }
}
