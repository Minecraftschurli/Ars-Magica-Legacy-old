package minecraftschurli.arsmagicalegacy.network;

import net.minecraft.network.*;
import net.minecraftforge.fml.network.*;

import java.util.function.*;

/**
 * @author Minecraftschurli
 * @version 2019-12-12
 */
public interface IPacket {
    void toBytes(PacketBuffer buf);

    void handle(Supplier<NetworkEvent.Context> ctx);
}
