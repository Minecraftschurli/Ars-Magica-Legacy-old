package minecraftschurli.arsmagicalegacy.objects.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectType;
import net.minecraft.util.math.Vec3d;

/**
 * @author Minecraftschurli
 * @version 2019-12-04
 */
public class EntangleEffect extends AMEffect {
    public EntangleEffect() {
        super(EffectType.BENEFICIAL, 0x009300);
    }

    @Override
    public void performEffect(LivingEntity livingEntity, int amplifier) {
        livingEntity.setMotion(Vec3d.ZERO);
    }
}
