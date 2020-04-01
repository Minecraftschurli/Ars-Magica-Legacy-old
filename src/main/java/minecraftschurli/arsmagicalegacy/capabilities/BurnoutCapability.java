package minecraftschurli.arsmagicalegacy.capabilities;

import minecraftschurli.arsmagicalegacy.api.capability.CapabilityHelper;
import minecraftschurli.arsmagicalegacy.api.capability.IBurnoutStorage;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author Minecraftschurli
 * @version 2019-11-08
 */
public class BurnoutCapability implements ICapabilitySerializable<INBT> {
    private LazyOptional<IBurnoutStorage> instance = LazyOptional.of(CapabilityHelper.getBurnoutCapability()::getDefaultInstance);

    public static void register() {
        CapabilityManager.INSTANCE.register(IBurnoutStorage.class, new Capability.IStorage<IBurnoutStorage>() {
                    @Override
                    public INBT writeNBT(Capability<IBurnoutStorage> capability, IBurnoutStorage instance, Direction side) {
                        CompoundNBT compoundNBT = new CompoundNBT();
                        compoundNBT.putFloat("maxBurnout", instance.getMaxBurnout());
                        compoundNBT.putFloat("burnout", instance.getBurnout());
                        return compoundNBT;
                    }

                    @Override
                    public void readNBT(Capability<IBurnoutStorage> capability, IBurnoutStorage instance, Direction side, INBT nbt) {
                        instance.setMaxBurnout(((CompoundNBT) nbt).getFloat("maxBurnout"));
                        instance.setBurnout(((CompoundNBT) nbt).getFloat("burnout"));
                    }
                },
                BurnoutStorage::new);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return cap == CapabilityHelper.getBurnoutCapability() ? instance.cast() : LazyOptional.empty();
    }

    @Override
    public INBT serializeNBT() {
        return CapabilityHelper.getBurnoutCapability().getStorage().writeNBT(CapabilityHelper.getBurnoutCapability(), this.instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional must not be empty!")), null);
    }

    @Override
    public void deserializeNBT(INBT nbt) {
        CapabilityHelper.getBurnoutCapability().getStorage().readNBT(CapabilityHelper.getBurnoutCapability(), this.instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional must not be empty!")), null, nbt);
    }
}
