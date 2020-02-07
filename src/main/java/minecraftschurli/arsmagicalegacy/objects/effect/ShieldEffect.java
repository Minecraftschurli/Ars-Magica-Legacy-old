package minecraftschurli.arsmagicalegacy.objects.effect;

import net.minecraft.potion.EffectType;

/**
 * @author Minecraftschurli
 * @version 2019-12-07
 */
public class ShieldEffect extends AMEffect {
    public ShieldEffect(int liquidColor) {
        super(EffectType.BENEFICIAL, liquidColor);
    }

    public ShieldEffect() {
        super(EffectType.BENEFICIAL, 0xc4c4c4);
    }
}
