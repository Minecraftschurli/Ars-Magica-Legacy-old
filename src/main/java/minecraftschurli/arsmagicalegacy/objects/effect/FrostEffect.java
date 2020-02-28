package minecraftschurli.arsmagicalegacy.objects.effect;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.potion.EffectType;

import java.util.UUID;

/**
 * @author Minecraftschurli
 * @version 2019-12-04
 */
public class FrostEffect extends AMEffect {

    private static final UUID frostSlowID = UUID.fromString("03B0A79B-9569-43AE-BFE3-820D993D4A64");

    public FrostEffect() {
        super(EffectType.HARMFUL, 0x1fffdd);
        addAttributesModifier(SharedMonsterAttributes.MOVEMENT_SPEED, frostSlowID.toString(), -0.2, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }

    @Override
    public double getAttributeModifierAmount(int amplifier, AttributeModifier modifier) {
        if (modifier.getID().equals(frostSlowID))
            return modifier.getAmount() - (0.3 * amplifier);
        return super.getAttributeModifierAmount(amplifier, modifier);
    }
}
