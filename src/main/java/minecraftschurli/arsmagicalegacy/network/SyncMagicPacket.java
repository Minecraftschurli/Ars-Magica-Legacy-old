package minecraftschurli.arsmagicalegacy.network;

import minecraftschurli.arsmagicalegacy.*;
import minecraftschurli.arsmagicalegacy.capabilities.magic.*;
import net.minecraft.network.*;
import net.minecraftforge.fml.network.*;

import java.util.function.*;

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
        ArsMagicaLegacy.proxy.getLocalPlayer().getCapability(MagicCapability.MAGIC).ifPresent(iMagicStorage -> {
            iMagicStorage.setLevel(this.level);
        });
        ctx.get().setPacketHandled(true);
    }
}
