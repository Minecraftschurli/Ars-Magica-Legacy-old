package minecraftschurli.arsmagicalegacy.objects.effect;

import minecraftschurli.arsmagicalegacy.api.capability.CapabilityHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectType;

/**
 * @author Minecraftschurli
 * @version 2019-12-07
 */
public class ManaRegenEffect extends AMEffect {
    public ManaRegenEffect() {
        super(EffectType.BENEFICIAL, 0x8bffff);
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        int k = 50 >> amplifier;
        if (k > 0) {
            return duration % k == 0;
        } else {
            return true;
        }
    }

    @Override
    public void performEffect(LivingEntity livingEntity, int amplifier) {
        CapabilityHelper.increaseMana(livingEntity, 5 + (3.5f * amplifier));
    }
}
