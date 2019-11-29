package minecraftschurli.arsmagicalegacy.capabilities.mana;

import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.*;

import javax.annotation.*;

/**
 * @author Minecraftschurli
 * @version 2019-11-07
 */
public class CapabilityMana implements ICapabilitySerializable<INBT> {
    @CapabilityInject(IManaStorage.class)
    public static Capability<IManaStorage> MANA = null;

    private LazyOptional<IManaStorage> instance = LazyOptional.of(MANA::getDefaultInstance);

    public static void register() {
        CapabilityManager.INSTANCE.register(IManaStorage.class, new Capability.IStorage<IManaStorage>() {
                    @Override
                    public INBT writeNBT(Capability<IManaStorage> capability, IManaStorage instance, Direction side) {
                        CompoundNBT compoundNBT = new CompoundNBT();
                        compoundNBT.putFloat("maxMana", instance.getMaxMana());
                        compoundNBT.putFloat("mana", instance.getMana());
                        return compoundNBT;
                    }

                    @Override
                    public void readNBT(Capability<IManaStorage> capability, IManaStorage instance, Direction side, INBT nbt) {
                        instance.setMaxMana(((CompoundNBT) nbt).getFloat("maxMana"));
                        instance.setMana(((CompoundNBT) nbt).getFloat("mana"));
                    }
                },
                ManaStorage::new);
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
        return cap == MANA ? instance.cast() : LazyOptional.empty();
    }

    @Override
    public INBT serializeNBT() {
        return MANA.getStorage().writeNBT(MANA, this.instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional must not be empty!")), null);
    }

    @Override
    public void deserializeNBT(INBT nbt) {
        MANA.getStorage().readNBT(MANA, this.instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional must not be empty!")), null, nbt);
    }
}
