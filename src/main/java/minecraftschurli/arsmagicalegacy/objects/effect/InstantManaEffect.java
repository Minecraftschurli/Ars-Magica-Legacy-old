package minecraftschurli.arsmagicalegacy.objects.effect;

import minecraftschurli.arsmagicalegacy.api.capability.*;
import net.minecraft.entity.*;
import net.minecraft.potion.*;

import javax.annotation.*;

/**
 * @author Minecraftschurli
 * @version 2019-12-07
 */
public class InstantManaEffect extends AMInstantEffect {
    public InstantManaEffect() {
        super(EffectType.BENEFICIAL, 0x00ffff);
    }

    @Override
    public void performEffect(@Nonnull LivingEntity entityLivingBaseIn, int amplifier) {
        float manaRestored;
        switch (amplifier) {
            case 0:
                manaRestored = 100;
                break;
            case 1:
                manaRestored = 250;
                break;
            case 2:
                manaRestored = 2000;
                break;
            case 3:
                manaRestored = 5000;
                break;
            case 4:
                manaRestored = 10000;
                break;
            default:
                manaRestored = 100 + (100 * amplifier);
        }
        CapabilityHelper.increaseMana(entityLivingBaseIn, manaRestored);
    }
}
