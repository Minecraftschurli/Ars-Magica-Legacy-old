package minecraftschurli.arsmagicalegacy.network;

import minecraftschurli.arsmagicalegacy.ArsMagicaLegacy;
import minecraftschurli.arsmagicalegacy.capabilities.burnout.CapabilityBurnout;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * @author Minecraftschurli
 * @version 2019-11-14
 */
public class SyncBurnoutPacket implements IPacket {
    private float burnout;
    private float maxBurnout;

    public SyncBurnoutPacket(PacketBuffer buf) {
        this.maxBurnout = buf.readFloat();
        this.burnout = buf.readFloat();
    }

    public SyncBurnoutPacket(float burnout, float maxBurnout) {
        this.maxBurnout = maxBurnout;
        this.burnout = burnout;
    }

    @Override
    public void toBytes(PacketBuffer buf) {
        buf.writeFloat(this.maxBurnout);
        buf.writeFloat(this.burnout);
    }

    @Override
    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ArsMagicaLegacy.proxy.getLocalPlayer().getCapability(CapabilityBurnout.BURNOUT).ifPresent(iBurnoutStorage -> {
            iBurnoutStorage.setMaxBurnout(this.maxBurnout);
            iBurnoutStorage.setBurnout(this.burnout);
        });
        ctx.get().setPacketHandled(true);
    }
}
