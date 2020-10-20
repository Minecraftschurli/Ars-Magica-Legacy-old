package minecraftschurli.arsmagicalegacy.objects.item.spawnegg;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import minecraftschurli.arsmagicalegacy.init.ModItems;
import net.minecraft.entity.EntityType;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.nbt.CompoundNBT;

public abstract class SpawnEgg extends SpawnEggItem {
    public SpawnEgg(int primary, int secondary) {
        super(null, primary, secondary, ModItems.ITEM_64);
    }

    @Nonnull
    @Override
    public abstract EntityType<?> getType(@Nullable CompoundNBT p_208076_1_);
}
