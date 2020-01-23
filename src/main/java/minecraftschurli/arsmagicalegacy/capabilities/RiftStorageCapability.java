package minecraftschurli.arsmagicalegacy.capabilities;

import minecraftschurli.arsmagicalegacy.api.capability.CapabilityHelper;
import minecraftschurli.arsmagicalegacy.api.capability.IRiftStorage;
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
 * @version 2020-01-20
 */
public class RiftStorageCapability implements ICapabilitySerializable<INBT> {
    private LazyOptional<IRiftStorage> riftInstance = LazyOptional.of(CapabilityHelper.getRiftStorageCapability()::getDefaultInstance);

    public static void register() {
        CapabilityManager.INSTANCE.register(IRiftStorage.class, new Capability.IStorage<IRiftStorage>() {
                    @Override
                    public INBT writeNBT(Capability<IRiftStorage> capability, IRiftStorage instance, Direction side) {
                        CompoundNBT compoundNBT = new CompoundNBT();
                        instance.save(compoundNBT);
                        return compoundNBT;
                    }

                    @Override
                    public void readNBT(Capability<IRiftStorage> capability, IRiftStorage instance, Direction side, INBT nbt) {
                        instance.load((CompoundNBT)nbt);
                    }
                },
                RiftStorage::new);
    }

    /**
     * Retrieves the Optional handler for the capability requested on the specific side.
     * The return value <strong>CAN</strong> be the same for multiple faces.
     * Modders are encouraged to cache this value, using the listener capabilities of the Optional to
     * be notified if the requested capability get lost.
     *
     * @param cap
     * @param side
     * @return The requested an optional holding the requested capability.
     */
    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return cap == CapabilityHelper.getRiftStorageCapability() ? riftInstance.cast() : LazyOptional.empty();
    }

    @Override
    public INBT serializeNBT() {
        return CapabilityHelper.getRiftStorageCapability().getStorage().writeNBT(CapabilityHelper.getRiftStorageCapability(), this.riftInstance.orElseThrow(() -> new IllegalArgumentException("LazyOptional must not be empty!")), null);
    }

    @Override
    public void deserializeNBT(INBT nbt) {
        CapabilityHelper.getRiftStorageCapability().getStorage().readNBT(CapabilityHelper.getRiftStorageCapability(), this.riftInstance.orElseThrow(() -> new IllegalArgumentException("LazyOptional must not be empty!")), null, nbt);
    }
}
