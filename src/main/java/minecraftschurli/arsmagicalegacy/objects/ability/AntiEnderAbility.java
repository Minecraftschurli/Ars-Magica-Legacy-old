package minecraftschurli.arsmagicalegacy.objects.ability;

import java.util.Collection;
import minecraftschurli.arsmagicalegacy.api.affinity.AbstractAffinityAbility;
import minecraftschurli.arsmagicalegacy.api.affinity.Affinity;
import net.minecraft.entity.monster.EndermanEntity;
import net.minecraft.entity.monster.EndermiteEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public final class AntiEnderAbility extends AbstractAffinityAbility {
    @Override
    public float getMinimumDepth() {
        return 0.9f;
    }

    @Override
    public ResourceLocation getAffinity() {
        return Affinity.WATER;
    }

    @Override
    public void applyHurt(PlayerEntity player, LivingHurtEvent event) {
        if (event.getSource().getTrueSource() instanceof EndermanEntity || event.getSource().getTrueSource() instanceof EndermiteEntity) event.getSource().getTrueSource().attackEntityFrom(DamageSource.DROWN, 2);
    }

    @Override
    public Collection<AbilityListenerType> registerListeners(Collection<AbilityListenerType> types) {
        types.add(AbilityListenerType.HURT);
        return types;
    }
}
