package minecraftschurli.arsmagicalegacy.objects.effect;

import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;

/**
 * @author Minecraftschurli
 * @version 2019-12-04
 */
public class AMEffectInstance extends EffectInstance {
    public AMEffectInstance(Effect effect) {
        super(effect);
    }

    public AMEffectInstance(Effect effect, int durationIn) {
        super(effect, durationIn);
    }

    public AMEffectInstance(Effect potionIn, int durationIn, int amplifierIn) {
        super(potionIn, durationIn, amplifierIn);
    }

    public AMEffectInstance(Effect potionIn, int durationIn, int amplifierIn, boolean ambientIn, boolean showParticlesIn) {
        super(potionIn, durationIn, amplifierIn, ambientIn, showParticlesIn);
    }

    public AMEffectInstance(Effect showParticlesIn, int potionIn, int durationIn, boolean amplifierIn, boolean ambientIn, boolean showIconIn) {
        super(showParticlesIn, potionIn, durationIn, amplifierIn, ambientIn, showIconIn);
    }

    public AMEffectInstance(EffectInstance other) {
        super(other);
    }
}
