package minecraftschurli.arsmagicalegacy.api.network;

import minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import minecraftschurli.arsmagicalegacy.api.capability.CapabilityHelper;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

/**
 * @author Minecraftschurli
 * @version 2019-12-02
 */
public class SyncMagicPacket implements IPacket {
    private int level;
    private float xp;

    public SyncMagicPacket() {}

    public SyncMagicPacket(int level, float xp) {
        this.level = level;
        this.xp = xp;
    }

    @Override
    public void serialize(PacketBuffer buf) {
        buf.writeInt(this.level);
        buf.writeFloat(this.xp);
    }

    @Override
    public void deserialize(PacketBuffer buf) {
        this.level = buf.readInt();
        this.xp = buf.readFloat();
    }

    @Override
    public boolean handle(NetworkEvent.Context ctx) {
        ArsMagicaAPI.getLocalPlayer().getCapability(CapabilityHelper.getMagicCapability()).ifPresent(iMagicStorage -> {
            iMagicStorage.setLevel(this.level);
            iMagicStorage.setXp(this.xp);
        });
        return true;
    }
}
