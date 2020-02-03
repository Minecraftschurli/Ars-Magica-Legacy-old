package minecraftschurli.arsmagicalegacy.objects.effect;

import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.potion.*;

/**
 * @author Minecraftschurli
 * @version 2019-12-04
 */
public class FlightEffect extends AMEffect {
    public FlightEffect() {
        super(EffectType.BENEFICIAL, 0xc6dada);
    }

    @Override
    public void startEffect(LivingEntity livingEntity, EffectInstance potionEffect) {
        if (!(livingEntity instanceof ServerPlayerEntity)) return;
        if (((ServerPlayerEntity) livingEntity).isCreative()) return;
        ((ServerPlayerEntity) livingEntity).abilities.allowFlying = true;
        ((ServerPlayerEntity) livingEntity).sendPlayerAbilities();
    }

    @Override
    public void stopEffect(LivingEntity livingEntity, EffectInstance potionEffect) {
        if (!(livingEntity instanceof ServerPlayerEntity)) return;
        if (((ServerPlayerEntity) livingEntity).isCreative()) return;
        ((ServerPlayerEntity) livingEntity).abilities.allowFlying = false;
        ((ServerPlayerEntity) livingEntity).abilities.isFlying = false;
        livingEntity.fallDistance = 0;
        ((ServerPlayerEntity) livingEntity).sendPlayerAbilities();
    }
}
