package minecraftschurli.arsmagicalegacy.capabilities;

import java.util.Map;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import minecraftschurli.arsmagicalegacy.api.capability.CapabilityHelper;
import minecraftschurli.arsmagicalegacy.api.capability.IAbilityStorage;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.LazyOptional;

/**
 * @author Minecraftschurli
 * @version 2020-02-13
 */
public class AbilityCapability implements ICapabilitySerializable<INBT> {
    private LazyOptional<IAbilityStorage> instance = LazyOptional.of(CapabilityHelper.getAbilityCapability()::getDefaultInstance);

    public static void register() {
        CapabilityManager.INSTANCE.register(IAbilityStorage.class, new Capability.IStorage<IAbilityStorage>() {
                    @Override
                    public INBT writeNBT(Capability<IAbilityStorage> capability, IAbilityStorage instance, Direction side) {
                        CompoundNBT compoundNBT = new CompoundNBT();
                        ListNBT list = new ListNBT();
                        for (Map.Entry<String, Integer> entry : instance.getCooldowns().entrySet()) {
                            CompoundNBT compound = new CompoundNBT();
                            compound.putString("key", entry.getKey());
                            compound.putDouble("value", entry.getValue());
                            list.add(compound);
                        }
                        compoundNBT.put("data", list);
                        return compoundNBT;
                    }

                    @Override
                    public void readNBT(Capability<IAbilityStorage> capability, IAbilityStorage instance, Direction side, INBT nbt) {
                        for (INBT entry : ((CompoundNBT) nbt).getList("data", Constants.NBT.TAG_COMPOUND)) {
                            CompoundNBT compound = (CompoundNBT) entry;
                            instance.addCooldown(compound.getString("key"), compound.getInt("value"));
                        }
                    }
                },
                AbilityStorage::new);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return cap == CapabilityHelper.getAbilityCapability() ? instance.cast() : LazyOptional.empty();
    }

    @Override
    public INBT serializeNBT() {
        return CapabilityHelper.getAbilityCapability().getStorage().writeNBT(CapabilityHelper.getAbilityCapability(), this.instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional must not be empty!")), null);
    }

    @Override
    public void deserializeNBT(INBT nbt) {
        CapabilityHelper.getAbilityCapability().getStorage().readNBT(CapabilityHelper.getAbilityCapability(), this.instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional must not be empty!")), null, nbt);
    }
}
