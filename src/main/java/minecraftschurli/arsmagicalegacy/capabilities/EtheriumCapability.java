package minecraftschurli.arsmagicalegacy.capabilities;

import minecraftschurli.arsmagicalegacy.api.etherium.IEtheriumStorage;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.IntNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;

/**
 * @author Minecraftschurli
 * @version 2020-04-16
 */
public class EtheriumCapability {
    public static void register() {
        CapabilityManager.INSTANCE.register(IEtheriumStorage.class, new Capability.IStorage<IEtheriumStorage>() {
                    @Override
                    public INBT writeNBT(Capability<IEtheriumStorage> capability, IEtheriumStorage instance, Direction side) {
                        return IntNBT.valueOf(instance.getStoredAmount());
                    }

                    @Override
                    public void readNBT(Capability<IEtheriumStorage> capability, IEtheriumStorage instance, Direction side, INBT nbt) {
                        if (!(instance instanceof EtheriumStorage))
                            throw new IllegalArgumentException("Can not deserialize to an instance that isn't the default implementation");
                        ((EtheriumStorage)instance).amount = ((IntNBT)nbt).getInt();
                    }
                }, () -> new EtheriumStorage(1000));
    }
}
