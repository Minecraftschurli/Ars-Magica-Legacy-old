package minecraftschurli.arsmagicalegacy.objects.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;

/**
 * @author Minecraftschurli
 * @version 2019-12-04
 */
public class AMEffect extends Effect {
    public AMEffect(EffectType type, int liquidColor) {
        super(type, liquidColor);
    }

    @Override
    public void performEffect(LivingEntity livingEntity, int amplifier) {}

    public void stopEffect(LivingEntity livingEntity) {}

    public void startEffect(LivingEntity livingEntity) {}
}
