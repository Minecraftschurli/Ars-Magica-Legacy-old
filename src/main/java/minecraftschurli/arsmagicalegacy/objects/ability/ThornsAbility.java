package minecraftschurli.arsmagicalegacy.objects.ability;

import java.util.Collection;
import minecraftschurli.arsmagicalegacy.api.affinity.AbstractAffinityAbility;
import minecraftschurli.arsmagicalegacy.api.affinity.Affinity;
import minecraftschurli.arsmagicalegacy.api.capability.CapabilityHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public final class ThornsAbility extends AbstractAffinityAbility {
    @Override
    public float getMinimumDepth() {
        return 0.5f;
    }

    @Override
    public ResourceLocation getAffinity() {
        return Affinity.NATURE;
    }

    @Override
    public void applyHurt(PlayerEntity player, LivingHurtEvent event) {
        if (event.getSource().getTrueSource() instanceof LivingEntity)
            event.getSource().getTrueSource().attackEntityFrom(DamageSource.CACTUS, CapabilityHelper.getAffinityDepth(player, Affinity.NATURE) == 1 ? 3 : CapabilityHelper.getAffinityDepth(player, Affinity.NATURE) >= 0.75 ? 2 : 1);
    }

    @Override
    public Collection<AbilityListenerType> registerListeners(Collection<AbilityListenerType> types) {
        types.add(AbilityListenerType.TICK);
        return types;
    }
}
