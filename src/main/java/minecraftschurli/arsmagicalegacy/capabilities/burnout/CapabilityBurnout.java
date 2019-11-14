package minecraftschurli.arsmagicalegacy.capabilities.burnout;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author Minecraftschurli
 * @version 2019-11-08
 */
public class CapabilityBurnout implements ICapabilitySerializable<INBT> {
    @CapabilityInject(IBurnoutStorage.class)
    public static Capability<IBurnoutStorage> BURNOUT = null;

    private LazyOptional<IBurnoutStorage> instance = LazyOptional.of(BURNOUT::getDefaultInstance);

    public static void register()
    {
        CapabilityManager.INSTANCE.register(IBurnoutStorage.class, new Capability.IStorage<IBurnoutStorage>() {
                    @Override
                    public INBT writeNBT(Capability<IBurnoutStorage> capability, IBurnoutStorage instance, Direction side)
                    {
                        CompoundNBT compoundNBT = new CompoundNBT();
                        compoundNBT.putInt("burnout", instance.getBurnout());
                        compoundNBT.putInt("maxBurnout", instance.getMaxBurnout());
                        return compoundNBT;
                    }

                    @Override
                    public void readNBT(Capability<IBurnoutStorage> capability, IBurnoutStorage instance, Direction side, INBT nbt)
                    {
                        if (!(instance instanceof BurnoutStorage))
                            throw new IllegalArgumentException("Can not deserialize to an instance that isn't the default implementation");
                        ((BurnoutStorage)instance).burnout = ((CompoundNBT)nbt).getInt("burnout");
                        ((BurnoutStorage)instance).maxBurnout = ((CompoundNBT)nbt).getInt("maxBurnout");
                    }
                },
                BurnoutStorage::new);
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
        return cap == BURNOUT ? instance.cast() : LazyOptional.empty();
    }

    @Override
    public INBT serializeNBT() {
        return BURNOUT.getStorage().writeNBT(BURNOUT, this.instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional must not be empty!")), null);
    }

    @Override
    public void deserializeNBT(INBT nbt) {
        BURNOUT.getStorage().readNBT(BURNOUT, this.instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional must not be empty!")), null, nbt);
    }
}
