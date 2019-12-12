package minecraftschurli.arsmagicalegacy.network;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * @author Minecraftschurli
 * @version 2019-12-12
 */
public interface IPacket {
    void toBytes(PacketBuffer buf);
    void handle(Supplier<NetworkEvent.Context> ctx);
}
