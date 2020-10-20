package minecraftschurli.arsmagicalegacy.objects.item.spawnegg;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import minecraftschurli.arsmagicalegacy.init.ModEntities;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.CompoundNBT;

public class ManaCreeperSpawnEgg extends SpawnEgg {
    public ManaCreeperSpawnEgg() {
        super(0x0d9bd7, 0);
    }

    @Nonnull
    @Override
    public EntityType<?> getType(@Nullable CompoundNBT p_208076_1_) {
        return ModEntities.MANA_CREEPER.get();
    }
}
