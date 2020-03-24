package minecraftschurli.arsmagicalegacy.api.network;

import javax.annotation.Nonnull;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

/**
 * @author Minecraftschurli
 * @version 2019-12-12
 */
public interface IPacket {
    void serialize(PacketBuffer buf);

    void deserialize(PacketBuffer buf);

    boolean handle(@Nonnull NetworkEvent.Context ctx);
}
