package minecraftschurli.arsmagicalegacy.capabilities.magic;

import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.*;

import javax.annotation.*;

/**
 * @author Minecraftschurli
 * @version 2019-12-02
 */
public class MagicCapability implements ICapabilitySerializable<INBT> {
    @CapabilityInject(IMagicStorage.class)
    public static Capability<IMagicStorage> MAGIC = null;

    private LazyOptional<IMagicStorage> instance = LazyOptional.of(MAGIC::getDefaultInstance);

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
        return cap == MAGIC ? instance.cast() : LazyOptional.empty();
    }

    @Override
    public INBT serializeNBT() {
        return MAGIC.getStorage().writeNBT(MAGIC, this.instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional must not be empty!")), null);
    }

    @Override
    public void deserializeNBT(INBT nbt) {
        MAGIC.getStorage().readNBT(MAGIC, this.instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional must not be empty!")), null, nbt);
    }
}
