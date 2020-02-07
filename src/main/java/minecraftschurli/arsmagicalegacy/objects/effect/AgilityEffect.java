package minecraftschurli.arsmagicalegacy.objects.effect;

import minecraftschurli.arsmagicalegacy.ArsMagicaLegacy;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;

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
        ArsMagicaLegacy.LOGGER.debug(livingEntity.stepHeight);
        livingEntity.stepHeight = 1.0f;
    }

    @Override
    public void stopEffect(LivingEntity livingEntity, EffectInstance potionEffect) {
        ArsMagicaLegacy.LOGGER.debug(livingEntity.stepHeight);
        livingEntity.stepHeight = 0.6f;
        ArsMagicaLegacy.LOGGER.debug(livingEntity.stepHeight);
    }
}
