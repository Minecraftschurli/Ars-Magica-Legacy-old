package minecraftschurli.arsmagicalegacy.objects.ability;

import java.util.Collection;
import minecraftschurli.arsmagicalegacy.api.affinity.AbstractAffinityAbility;
import minecraftschurli.arsmagicalegacy.api.affinity.Affinity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;

public final class ReflexesAbility extends AbstractAffinityAbility {
    private static final AttributeModifier MODIFIER = new AttributeModifier("reflexes", 1.2, AttributeModifier.Operation.ADDITION);

    @Override
    public float getMinimumDepth() {
        return 0.65f;
    }

    @Override
    public ResourceLocation getAffinity() {
        return Affinity.LIGHTNING;
    }

    @Override
    public void applyTick(PlayerEntity player) {
        player.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).applyModifier(MODIFIER);
    }

    @Override
    public void removeEffects(PlayerEntity player) {
        player.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).removeModifier(MODIFIER);
    }

    @Override
    public Collection<AbilityListenerType> registerListeners(Collection<AbilityListenerType> types) {
        types.add(AbilityListenerType.TICK);
        return types;
    }
}
