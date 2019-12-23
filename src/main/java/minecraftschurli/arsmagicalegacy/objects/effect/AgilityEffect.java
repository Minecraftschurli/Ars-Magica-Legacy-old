package minecraftschurli.arsmagicalegacy.objects.effect;

import minecraftschurli.arsmagicalegacy.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.*;
import net.minecraft.potion.*;

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
    public void startEffect(LivingEntity livingEntity) {
        ArsMagicaLegacy.LOGGER.debug(livingEntity.stepHeight);
        livingEntity.stepHeight = 1.0f;
    }

    @Override
    public void stopEffect(LivingEntity livingEntity) {
        ArsMagicaLegacy.LOGGER.debug(livingEntity.stepHeight);
        livingEntity.stepHeight = 0.6f;
        ArsMagicaLegacy.LOGGER.debug(livingEntity.stepHeight);
    }
}
