package minecraftschurli.arsmagicalegacy.objects.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.potion.Effects;

/**
 * @author Minecraftschurli
 * @version 2019-12-07
 */
public class FuryEffect extends AMEffect {
    public FuryEffect() {
        super(EffectType.HARMFUL, 0xff8033);
    }

    @Override
    public void stopEffect(LivingEntity livingEntity, EffectInstance potionEffect) {
        if (!livingEntity.world.isRemote) {
            livingEntity.addPotionEffect(new EffectInstance(Effects.HUNGER, 200, 1));
            livingEntity.addPotionEffect(new EffectInstance(Effects.NAUSEA, 200, 1));
        }
    }
}
