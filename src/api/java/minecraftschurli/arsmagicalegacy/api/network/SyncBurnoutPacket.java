package minecraftschurli.arsmagicalegacy.api.network;

import minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import minecraftschurli.arsmagicalegacy.api.capability.CapabilityHelper;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

/**
 * @author Minecraftschurli
 * @version 2019-11-14
 */
public class SyncBurnoutPacket implements IPacket {
    private float burnout;
    private float maxBurnout;

    public SyncBurnoutPacket() {}

    public SyncBurnoutPacket(float burnout, float maxBurnout) {
        this.maxBurnout = maxBurnout;
        this.burnout = burnout;
    }

    @Override
    public void serialize(PacketBuffer buf) {
        buf.writeFloat(maxBurnout);
        buf.writeFloat(burnout);
    }

    @Override
    public void deserialize(PacketBuffer buf) {
        this.maxBurnout = buf.readFloat();
        this.burnout = buf.readFloat();
    }

    @Override
    public boolean handle(NetworkEvent.Context ctx) {
        ArsMagicaAPI.getLocalPlayer().getCapability(CapabilityHelper.getBurnoutCapability()).ifPresent(iBurnoutStorage -> {
            iBurnoutStorage.setMaxBurnout(maxBurnout);
            iBurnoutStorage.setBurnout(burnout);
        });
        return true;
    }
}
