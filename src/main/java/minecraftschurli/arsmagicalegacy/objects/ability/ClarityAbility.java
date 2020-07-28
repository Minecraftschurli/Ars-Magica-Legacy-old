package minecraftschurli.arsmagicalegacy.objects.ability;

import java.util.Collection;
import minecraftschurli.arsmagicalegacy.api.affinity.AbstractAffinityAbility;
import minecraftschurli.arsmagicalegacy.api.affinity.Affinity;
import minecraftschurli.arsmagicalegacy.api.event.SpellCastEvent;
import minecraftschurli.arsmagicalegacy.init.ModEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ResourceLocation;

public final class ClarityAbility extends AbstractAffinityAbility {
    @Override
    public float getMinimumDepth() {
        return 0.4f;
    }

    @Override
    public ResourceLocation getAffinity() {
        return Affinity.ARCANE;
    }

    @Override
    public void applyPreSpellCast(PlayerEntity player, SpellCastEvent.Pre event) {
        if (player.world.rand.nextInt(100) < 5 && !player.world.isRemote && (!player.isPotionActive(ModEffects.CLARITY.get()) || player.getActivePotionEffect(ModEffects.CLARITY.get()).getDuration() <= 220)) player.addPotionEffect(new EffectInstance(ModEffects.CLARITY.get(), 300, 0, true, false));
    }

    @Override
    public Collection<AbilityListenerType> registerListeners(Collection<AbilityListenerType> types) {
        types.add(AbilityListenerType.PRE_SPELL_CAST);
        return types;
    }
}
