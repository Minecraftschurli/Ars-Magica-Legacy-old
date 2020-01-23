package minecraftschurli.arsmagicalegacy.capabilities;

import minecraftschurli.arsmagicalegacy.api.capability.CapabilityHelper;
import minecraftschurli.arsmagicalegacy.api.capability.IMagicStorage;
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
 * @version 2019-12-02
 */
public class MagicCapability implements ICapabilitySerializable<INBT> {

    private LazyOptional<IMagicStorage> instance = LazyOptional.of(CapabilityHelper.getMagicCapability()::getDefaultInstance);

    public static void register() {
        CapabilityManager.INSTANCE.register(IMagicStorage.class, new Capability.IStorage<IMagicStorage>() {
                    @Override
                    public INBT writeNBT(Capability<IMagicStorage> capability, IMagicStorage instance, Direction side) {
                        CompoundNBT compoundNBT = new CompoundNBT();
                        compoundNBT.putInt("level", instance.getCurrentLevel());
                        compoundNBT.putFloat("xp", instance.getXp());
                        return compoundNBT;
                    }

                    @Override
                    public void readNBT(Capability<IMagicStorage> capability, IMagicStorage instance, Direction side, INBT nbt) {
                        instance.setLevel(((CompoundNBT) nbt).getInt("level"));
                        instance.setXp(((CompoundNBT) nbt).getInt("xp"));
                    }
                },
                MagicStorage::new);
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
        return cap == CapabilityHelper.getMagicCapability() ? instance.cast() : LazyOptional.empty();
    }

    @Override
    public INBT serializeNBT() {
        return CapabilityHelper.getMagicCapability().getStorage().writeNBT(CapabilityHelper.getMagicCapability(), this.instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional must not be empty!")), null);
    }

    @Override
    public void deserializeNBT(INBT nbt) {
        CapabilityHelper.getMagicCapability().getStorage().readNBT(CapabilityHelper.getMagicCapability(), this.instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional must not be empty!")), null, nbt);
    }
}
