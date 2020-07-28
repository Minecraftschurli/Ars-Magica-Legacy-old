package minecraftschurli.arsmagicalegacy.objects.ability;

import java.util.Collection;
import minecraftschurli.arsmagicalegacy.api.affinity.AbstractAffinityAbility;
import minecraftschurli.arsmagicalegacy.api.affinity.Affinity;
import minecraftschurli.arsmagicalegacy.api.event.SpellCastEvent;
import minecraftschurli.arsmagicalegacy.init.ModEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;

public final class FeatherLightAbility extends AbstractAffinityAbility {
    @Override
    public float getMinimumDepth() {
        return 0.5f;
    }

    @Override
    public ResourceLocation getAffinity() {
        return Affinity.AIR;
    }

    @Override
    public float getMaximumDepth() {
        return 0.85f;
    }

    @Override
    public void applyPreSpellCast(PlayerEntity player, SpellCastEvent.Pre event) {
        if (!player.world.isRemote && player.world.isRainingAt(player.getPosition()) && player.world.rand.nextInt(100) < 10 && !player.isSneaking() && !player.isPotionActive(ModEffects.GRAVITY_WELL.get()) && !player.isInWater() && player.isWet()) player.addVelocity(player.world.rand.nextDouble() - 0.5, player.world.rand.nextDouble() - 0.5, player.world.rand.nextDouble() - 0.5);
    }

    @Override
    public Collection<AbilityListenerType> registerListeners(Collection<AbilityListenerType> types) {
        types.add(AbilityListenerType.PRE_SPELL_CAST);
        return types;
    }
}
