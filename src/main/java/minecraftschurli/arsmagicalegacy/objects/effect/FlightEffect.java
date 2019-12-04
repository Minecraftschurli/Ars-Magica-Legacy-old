package minecraftschurli.arsmagicalegacy.objects.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.potion.EffectType;

/**
 * @author Minecraftschurli
 * @version 2019-12-04
 */
public class FlightEffect extends AMEffect {
    public FlightEffect() {
        super(EffectType.BENEFICIAL, 0xc6dada);
    }

    @Override
    public void startEffect(LivingEntity livingEntity) {
        if (!(livingEntity instanceof ServerPlayerEntity)) return;
        if (((ServerPlayerEntity) livingEntity).isCreative()) return;
        ((ServerPlayerEntity)livingEntity).abilities.allowFlying = true;
        ((ServerPlayerEntity) livingEntity).sendPlayerAbilities();
    }

    @Override
    public void stopEffect(LivingEntity livingEntity) {
        if (!(livingEntity instanceof ServerPlayerEntity)) return;
        if (((ServerPlayerEntity) livingEntity).isCreative()) return;
        ((ServerPlayerEntity)livingEntity).abilities.allowFlying = false;
        ((ServerPlayerEntity)livingEntity).abilities.isFlying = false;
        livingEntity.fallDistance = 0;
        ((ServerPlayerEntity) livingEntity).sendPlayerAbilities();
    }
}
