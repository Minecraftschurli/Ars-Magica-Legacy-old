package minecraftschurli.arsmagicalegacy.network;

import minecraftschurli.arsmagicalegacy.ArsMagicaLegacy;
import minecraftschurli.arsmagicalegacy.capabilities.magic.CapabilityMagic;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * @author Minecraftschurli
 * @version 2019-12-02
 */
public class SyncMagicPacket implements IPacket {
    private int level;

    public SyncMagicPacket(PacketBuffer buf) {
        this.level = buf.readInt();
    }

    public SyncMagicPacket(int level) {
        this.level = level;
    }

    @Override
    public void toBytes(PacketBuffer buf) {
        buf.writeInt(this.level);
    }

    @Override
    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ArsMagicaLegacy.proxy.getLocalPlayer().getCapability(CapabilityMagic.MAGIC).ifPresent(iMagicStorage -> {
            iMagicStorage.setLevel(this.level);
        });
        ctx.get().setPacketHandled(true);
    }
}
