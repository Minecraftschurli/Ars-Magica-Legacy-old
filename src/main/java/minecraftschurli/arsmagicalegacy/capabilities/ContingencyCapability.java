package minecraftschurli.arsmagicalegacy.capabilities;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import minecraftschurli.arsmagicalegacy.api.capability.CapabilityHelper;
import minecraftschurli.arsmagicalegacy.api.capability.IContingencyStorage;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

/**
 * @author Minecraftschurli
 * @version 2019-11-08
 */
public class ContingencyCapability implements ICapabilitySerializable<INBT> {
    private final LazyOptional<IContingencyStorage> instance = LazyOptional.of(CapabilityHelper.getContingencyCapability()::getDefaultInstance);

    public static void register() {
        CapabilityManager.INSTANCE.register(IContingencyStorage.class, new Capability.IStorage<IContingencyStorage>() {
            @Override
            public INBT writeNBT(Capability<IContingencyStorage> capability, IContingencyStorage instance, Direction side) {
                CompoundNBT compoundNBT = new CompoundNBT();
                if (instance.getContingency() != null)
                    compoundNBT.putString("contingency", instance.getContingency().toString());
                if (instance.getSpell() != null) compoundNBT.put("spell", instance.getSpell().write(new CompoundNBT()));
                return compoundNBT;
            }

            @Override
            public void readNBT(Capability<IContingencyStorage> capability, IContingencyStorage instance, Direction side, INBT nbt) {
                if (((CompoundNBT) nbt).contains("contingency"))
                    instance.setContingency(ResourceLocation.tryCreate(((CompoundNBT) nbt).getString("contingency")));
                if (((CompoundNBT) nbt).contains("spell"))
                    instance.setSpell(ItemStack.read(((CompoundNBT) nbt).getCompound("spell")));
            }
        }, ContingencyStorage::new);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return cap == CapabilityHelper.getContingencyCapability() ? instance.cast() : LazyOptional.empty();
    }

    @Override
    public INBT serializeNBT() {
        return CapabilityHelper.getContingencyCapability().getStorage().writeNBT(CapabilityHelper.getContingencyCapability(), this.instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional must not be empty!")), null);
    }

    @Override
    public void deserializeNBT(INBT nbt) {
        CapabilityHelper.getContingencyCapability().getStorage().readNBT(CapabilityHelper.getContingencyCapability(), this.instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional must not be empty!")), null, nbt);
    }
}
