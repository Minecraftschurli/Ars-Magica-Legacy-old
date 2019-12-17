package minecraftschurli.arsmagicalegacy.objects.effect;

import minecraftschurli.arsmagicalegacy.ArsMagicaLegacy;
import minecraftschurli.arsmagicalegacy.util.MagicHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.FloatNBT;
import net.minecraft.potion.EffectType;

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
        livingEntity.getPersistentData().getCompound(ArsMagicaLegacy.MODID).put("mana_boost", FloatNBT.func_229689_a_(MagicHelper.getMaxMana(livingEntity)));
    }

    @Override
    public void stopEffect(LivingEntity livingEntity) {
        MagicHelper.getManaCapability(livingEntity).setMaxMana(livingEntity.getPersistentData().getCompound(ArsMagicaLegacy.MODID).getFloat("mana_boost"));
    }
}
