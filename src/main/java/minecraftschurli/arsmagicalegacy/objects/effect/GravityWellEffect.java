package minecraftschurli.arsmagicalegacy.objects.effect;

import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectType;

/**
 * @author Minecraftschurli
 * @version 2019-12-04
 */
public class GravityWellEffect extends AMEffect {
    public GravityWellEffect() {
        super(EffectType.HARMFUL, 0xa400ff);
        addAttributesModifier(PlayerEntity.ENTITY_GRAVITY, "CC5AF142-2BD2-4215-B836-2605AED11727", 2, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }
}
