package minecraftschurli.arsmagicalegacy.objects.effect;

import minecraftschurli.arsmagicalegacy.*;
import minecraftschurli.arsmagicalegacy.util.*;
import net.minecraft.entity.*;
import net.minecraft.nbt.*;
import net.minecraft.potion.*;

/**
 * @author Minecraftschurli
 * @version 2019-12-07
 */
public class ManaBoostEffect extends AMEffect {
    public ManaBoostEffect() {
        super(EffectType.BENEFICIAL, 0x0093ff);
    }

    @Override
    public void startEffect(LivingEntity livingEntity) {
        if (!livingEntity.getPersistentData().hasUniqueId(ArsMagicaLegacy.MODID))
            livingEntity.getPersistentData().put(ArsMagicaLegacy.MODID, new CompoundNBT());
        livingEntity.getPersistentData().getCompound(ArsMagicaLegacy.MODID).put("mana_boost", new FloatNBT(MagicHelper.getMaxMana(livingEntity)));
    }

    @Override
    public void stopEffect(LivingEntity livingEntity) {
        MagicHelper.getManaCapability(livingEntity).setMaxMana(livingEntity.getPersistentData().getCompound(ArsMagicaLegacy.MODID).getFloat("mana_boost"));
    }
}
