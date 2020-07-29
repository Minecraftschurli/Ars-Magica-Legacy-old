package minecraftschurli.arsmagicalegacy.objects.ability;

import java.util.Collection;
import minecraftschurli.arsmagicalegacy.api.affinity.AbstractAffinityAbility;
import minecraftschurli.arsmagicalegacy.api.affinity.Affinity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public final class MagicWeaknessAbility extends AbstractAffinityAbility {
    @Override
    public float getMinimumDepth() {
        return 0.25f;
    }

    @Override
    public ResourceLocation getAffinity() {
        return Affinity.ARCANE;
    }

    @Override
    public void applyHurt(PlayerEntity player, LivingHurtEvent event) {
        if (event.getSource() == DamageSource.MAGIC) event.setAmount(event.getAmount() * 1.1f);
    }

    @Override
    public Collection<AbilityListenerType> registerListeners(Collection<AbilityListenerType> types) {
        types.add(AbilityListenerType.HURT);
        return types;
    }
}
