package minecraftschurli.arsmagicalegacy.objects.effect;

import minecraftschurli.arsmagicalegacy.*;
import minecraftschurli.arsmagicalegacy.util.*;
import net.minecraft.entity.*;
import net.minecraft.nbt.*;
import net.minecraft.potion.*;

/**
 * @author Minecraftschurli
 * @version 2019-12-04
 */
public class TemporalAnchorEffect extends AMEffect {
    public TemporalAnchorEffect(EffectType type, int liquidColor) {
        super(type, liquidColor);
    }

    @Override
    public void startEffect(LivingEntity livingEntity) {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putDouble("X", livingEntity.posX);
		nbt.putDouble("Y", livingEntity.posY);
		nbt.putDouble("Z", livingEntity.posZ);
		nbt.putFloat("RotationPitch", livingEntity.rotationPitch);
		nbt.putFloat("RotationYaw", livingEntity.rotationYaw);
		nbt.putFloat("RotationYawHead", livingEntity.rotationYawHead);
		nbt.putFloat("Mana", MagicHelper.getMana(livingEntity));
		nbt.putFloat("Health", livingEntity.getHealth());
		if (!livingEntity.getPersistentData().hasUniqueId(ArsMagicaLegacy.MODID))
		    livingEntity.getPersistentData().put(ArsMagicaLegacy.MODID, new CompoundNBT());
		livingEntity.getPersistentData().getCompound(ArsMagicaLegacy.MODID).put("anchor", nbt);
    }

    @Override
    public void stopEffect(LivingEntity livingEntity) {
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
            MagicHelper.getManaCapability(livingEntity).setMana(mana);
            livingEntity.setHealth(health);
            livingEntity.fallDistance = 0;
        } catch (Exception ignored) {}
    }
}
