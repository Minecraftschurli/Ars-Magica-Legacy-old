package minecraftschurli.arsmagicalegacy.objects.ability;

import java.util.Collection;
import minecraftschurli.arsmagicalegacy.api.affinity.AbstractAffinityAbility;
import minecraftschurli.arsmagicalegacy.api.affinity.Affinity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;

public final class LightningWaterWeaknessAbility extends AbstractAffinityAbility {
    private static final AttributeModifier MODIFIER = new AttributeModifier("lightning_water_weakness", -0.25, AttributeModifier.Operation.ADDITION);

    @Override
    public float getMinimumDepth() {
        return 0.5f;
    }

    @Override
    public ResourceLocation getAffinity() {
        return Affinity.LIGHTNING;
    }

    @Override
    public float getMaximumDepth() {
        return 0.9f;
    }

    @Override
    public void applyTick(PlayerEntity player) {
        if (player.isInWater()) player.getAttribute(SharedMonsterAttributes.MAX_HEALTH).applyModifier(MODIFIER);
    }

    @Override
    public void removeEffects(PlayerEntity player) {
        player.getAttribute(SharedMonsterAttributes.MAX_HEALTH).removeModifier(MODIFIER);
    }

    @Override
    public Collection<AbilityListenerType> registerListeners(Collection<AbilityListenerType> types) {
        types.add(AbilityListenerType.TICK);
        return types;
    }
}
