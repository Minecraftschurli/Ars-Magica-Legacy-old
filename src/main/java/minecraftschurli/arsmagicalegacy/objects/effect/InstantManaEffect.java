package minecraftschurli.arsmagicalegacy.objects.effect;

import minecraftschurli.arsmagicalegacy.util.MagicHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectType;

import javax.annotation.Nonnull;

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
        MagicHelper.increaseMana(entityLivingBaseIn, 10 + (10 * amplifier));
    }
}
