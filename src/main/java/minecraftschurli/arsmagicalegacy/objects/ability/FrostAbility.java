package minecraftschurli.arsmagicalegacy.objects.ability;

import java.util.Collection;
import minecraftschurli.arsmagicalegacy.api.affinity.AbstractAffinityAbility;
import minecraftschurli.arsmagicalegacy.api.affinity.Affinity;
import minecraftschurli.arsmagicalegacy.api.capability.CapabilityHelper;
import minecraftschurli.arsmagicalegacy.init.ModEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public final class FrostAbility extends AbstractAffinityAbility {
    @Override
    public float getMinimumDepth() {
        return 0.1f;
    }

    @Override
    public ResourceLocation getAffinity() {
        return Affinity.ICE;
    }

    @Override
    public void applyHurt(PlayerEntity player, LivingHurtEvent event) {
        if(event.getSource().getTrueSource() instanceof LivingEntity) event.getEntityLiving().addPotionEffect(new EffectInstance(ModEffects.FROST.get(), (int)(200 * CapabilityHelper.getAffinityDepth(player, Affinity.ICE)), CapabilityHelper.getAffinityDepth(player, Affinity.ICE) == 1 ? 3 : CapabilityHelper.getAffinityDepth(player, Affinity.ICE) >= 0.7f ? 2 : CapabilityHelper.getAffinityDepth(player, Affinity.ICE) >= 0.4f ? 1 : 0, true, false));
    }

    @Override
    public Collection<AbilityListenerType> registerListeners(Collection<AbilityListenerType> types) {
        types.add(AbilityListenerType.HURT);
        return types;
    }
}
