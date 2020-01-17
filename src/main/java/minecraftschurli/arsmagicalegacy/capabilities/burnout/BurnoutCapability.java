package minecraftschurli.arsmagicalegacy.capabilities.burnout;

import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.*;

import javax.annotation.*;

/**
 * @author Minecraftschurli
 * @version 2019-11-08
 */
public class BurnoutCapability implements ICapabilitySerializable<INBT> {
    @CapabilityInject(IBurnoutStorage.class)
    public static Capability<IBurnoutStorage> BURNOUT = null;

    private LazyOptional<IBurnoutStorage> instance = LazyOptional.of(BURNOUT::getDefaultInstance);

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