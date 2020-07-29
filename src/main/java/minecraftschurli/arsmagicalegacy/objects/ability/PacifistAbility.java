package minecraftschurli.arsmagicalegacy.objects.ability;

import java.util.Collection;
import minecraftschurli.arsmagicalegacy.api.affinity.AbstractAffinityAbility;
import minecraftschurli.arsmagicalegacy.api.affinity.Affinity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

public final class PacifistAbility extends AbstractAffinityAbility {
    @Override
    public float getMinimumDepth() {
        return 0.6f;
    }

    @Override
    public ResourceLocation getAffinity() {
        return Affinity.LIFE;
    }

    @Override
    public void applyKill(PlayerEntity player, LivingDeathEvent event) {
        if (!event.getEntityLiving().isEntityUndead()) {
            player.addPotionEffect(new EffectInstance(Effects.NAUSEA, 100, 1));
            player.addPotionEffect(new EffectInstance(Effects.HUNGER, 100, 1));
            player.addPotionEffect(new EffectInstance(Effects.MINING_FATIGUE, 100, 1));
            player.addPotionEffect(new EffectInstance(Effects.WEAKNESS, 100, 1));
        }
    }

    @Override
    public Collection<AbilityListenerType> registerListeners(Collection<AbilityListenerType> types) {
        types.add(AbilityListenerType.KILL);
        return types;
    }
}
