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
public class EffectManaRegen extends Effect {
    public EffectManaRegen() {
        super(EffectType.BENEFICIAL, 0x74FFFC);
    }

    @Override
    public void performEffect(@Nonnull LivingEntity entityLivingBaseIn, int amplifier) {
        if (!(entityLivingBaseIn instanceof PlayerEntity))
            return;
        PlayerEntity player = (PlayerEntity) entityLivingBaseIn;
        player.getCapability(CapabilityMana.MANA)
                .ifPresent(iManaStorage -> iManaStorage.increase(Math.min(amplifier, iManaStorage.getMaxMana() - iManaStorage.getMana())));
        player.getCapability(CapabilityBurnout.BURNOUT)
                .ifPresent(iBurnoutStorage -> iBurnoutStorage.decrease(Math.min(amplifier, iBurnoutStorage.getBurnout())));
    }
}
