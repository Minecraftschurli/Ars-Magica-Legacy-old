package minecraftschurli.arsmagicalegacy.objects.ability;

import java.util.Collection;
import minecraftschurli.arsmagicalegacy.api.affinity.AbstractAffinityAbility;
import minecraftschurli.arsmagicalegacy.api.affinity.Affinity;
import minecraftschurli.arsmagicalegacy.api.event.SpellCastEvent;
import minecraftschurli.arsmagicalegacy.init.ModEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;

public final class ManaCostDecreaseAbility extends AbstractAffinityAbility {
    @Override
    public float getMinimumDepth() {
        return 0.5f;
    }

    @Override
    public ResourceLocation getAffinity() {
        return Affinity.ARCANE;
    }

    @Override
    public void applyPreSpellCast(PlayerEntity player, SpellCastEvent.Pre event) {
        event.manaCost *= 0.95f;
        event.burnout *= 0.95f;
        if(player.isPotionActive(ModEffects.CLARITY.get())) {
            event.manaCost = 0;
            event.burnout = 0;
        }
    }

    @Override
    public Collection<AbilityListenerType> registerListeners(Collection<AbilityListenerType> types) {
        types.add(AbilityListenerType.PRE_SPELL_CAST);
        return types;
    }
}
