package minecraftschurli.arsmagicalegacy.objects.effect;

import minecraftschurli.arsmagicalegacy.api.network.NetworkHandler;
import minecraftschurli.arsmagicalegacy.api.network.UpdateStepHeightPacket;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraftforge.fml.network.PacketDistributor;

/**
 * @author Minecraftschurli
 * @version 2019-12-04
 */
public class AgilityEffect extends AMEffect {
    public AgilityEffect() {
        super(EffectType.BENEFICIAL, 0xade000);
        addAttributesModifier(SharedMonsterAttributes.MOVEMENT_SPEED, "91AEAA56-376B-4498-935B-2F7F68070633", 1.2F, AttributeModifier.Operation.MULTIPLY_BASE);
    }

    @Override
    public void startEffect(LivingEntity livingEntity, EffectInstance potionEffect) {
        livingEntity.stepHeight = 1.0f;
        if (livingEntity instanceof ServerPlayerEntity)
            NetworkHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) livingEntity), new UpdateStepHeightPacket(livingEntity.stepHeight));
    }

    @Override
    public void stopEffect(LivingEntity livingEntity, EffectInstance potionEffect) {
        livingEntity.stepHeight = 0.6f;
        if (livingEntity instanceof ServerPlayerEntity)
            NetworkHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) livingEntity), new UpdateStepHeightPacket(livingEntity.stepHeight));
    }
}
