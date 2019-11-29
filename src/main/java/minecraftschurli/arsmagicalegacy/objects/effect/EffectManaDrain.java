package minecraftschurli.arsmagicalegacy.objects.effect;

import minecraftschurli.arsmagicalegacy.capabilities.burnout.*;
import minecraftschurli.arsmagicalegacy.capabilities.mana.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.potion.*;

import javax.annotation.*;

/**
 * @author Minecraftschurli
 * @version 2019-11-14
 */
public class EffectManaDrain extends Effect {
    public EffectManaDrain() {
        super(EffectType.HARMFUL, 0x74FFFC);
    }

    @Override
    public void performEffect(@Nonnull LivingEntity entityLivingBaseIn, int amplifier) {
        if (!(entityLivingBaseIn instanceof PlayerEntity))
            return;
        PlayerEntity player = (PlayerEntity) entityLivingBaseIn;
        player.getCapability(CapabilityMana.MANA)
                .ifPresent(iManaStorage -> iManaStorage.decrease(Math.min(amplifier, iManaStorage.getMana())));
        player.getCapability(CapabilityBurnout.BURNOUT)
                .ifPresent(iBurnoutStorage -> iBurnoutStorage.increase(Math.min(amplifier, iBurnoutStorage.getMaxBurnout() - iBurnoutStorage.getBurnout())));
    }
}
