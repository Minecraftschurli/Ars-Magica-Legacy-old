package minecraftschurli.arsmagicalegacy.objects.effect;

import minecraftschurli.arsmagicalegacy.ArsMagicaLegacy;
import minecraftschurli.arsmagicalegacy.api.capability.CapabilityHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;

/**
 * @author Minecraftschurli
 * @version 2019-12-04
 */
public class TemporalAnchorEffect extends AMEffect {
    public TemporalAnchorEffect() {
        super(EffectType.BENEFICIAL, 0xa2a2a2);
    }

    @Override
    public void startEffect(LivingEntity livingEntity, EffectInstance potionEffect) {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putDouble("X", livingEntity.getPosX());
        nbt.putDouble("Y", livingEntity.getPosY());
        nbt.putDouble("Z", livingEntity.getPosZ());
        nbt.putFloat("RotationPitch", livingEntity.rotationPitch);
        nbt.putFloat("RotationYaw", livingEntity.rotationYaw);
        nbt.putFloat("RotationYawHead", livingEntity.rotationYawHead);
        nbt.putFloat("Mana", CapabilityHelper.getMana(livingEntity));
        nbt.putFloat("Health", livingEntity.getHealth());
        if (!livingEntity.getPersistentData().hasUniqueId(ArsMagicaLegacy.MODID))
            livingEntity.getPersistentData().put(ArsMagicaLegacy.MODID, new CompoundNBT());
        livingEntity.getPersistentData().getCompound(ArsMagicaLegacy.MODID).put("anchor", nbt);
    }

    @Override
    public void stopEffect(LivingEntity livingEntity, EffectInstance potionEffect) {
        try {
            CompoundNBT nbt = livingEntity.getPersistentData().getCompound(ArsMagicaLegacy.MODID).getCompound("anchor");
            double x = nbt.getDouble("X");
            double y = nbt.getDouble("Y");
            double z = nbt.getDouble("Z");
            float rotationPitch = nbt.getFloat("RotationPitch");
            float rotationYaw = nbt.getFloat("RotationYaw");
            float rotationYawHead = nbt.getFloat("RotationYawHead");
            float health = nbt.getFloat("Health");
            float mana = nbt.getFloat("Mana");

            livingEntity.setPositionAndRotation(x, y, z, rotationYaw, rotationPitch);
            livingEntity.rotationYawHead = rotationYawHead;
            CapabilityHelper.decreaseMana(livingEntity, CapabilityHelper.getMana(livingEntity));
            CapabilityHelper.increaseMana(livingEntity, mana);
            livingEntity.setHealth(health);
            livingEntity.fallDistance = 0;
        } catch (Exception ignored) {
        }
    }
}
