package minecraftschurli.arsmagicalegacy.objects.effect;

import minecraftschurli.arsmagicalegacy.capabilities.burnout.CapabilityBurnout;
import minecraftschurli.arsmagicalegacy.capabilities.mana.CapabilityMana;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;

import javax.annotation.Nonnull;

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
