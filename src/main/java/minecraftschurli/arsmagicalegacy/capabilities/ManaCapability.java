package minecraftschurli.arsmagicalegacy.capabilities;

import minecraftschurli.arsmagicalegacy.api.capability.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.*;

import javax.annotation.*;


/**
 * @author Minecraftschurli
 * @version 2019-11-07
 */
public class ManaCapability implements ICapabilitySerializable<INBT> {

    private LazyOptional<IManaStorage> instance = LazyOptional.of(CapabilityHelper.getManaCapability()::getDefaultInstance);

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

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return cap == CapabilityHelper.getManaCapability() ? instance.cast() : LazyOptional.empty();
    }

    @Override
    public INBT serializeNBT() {
        return CapabilityHelper.getManaCapability().getStorage().writeNBT(CapabilityHelper.getManaCapability(), this.instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional must not be empty!")), null);
    }

    @Override
    public void deserializeNBT(INBT nbt) {
        CapabilityHelper.getManaCapability().getStorage().readNBT(CapabilityHelper.getManaCapability(), this.instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional must not be empty!")), null, nbt);
    }
}
