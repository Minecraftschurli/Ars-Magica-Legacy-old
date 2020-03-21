package minecraftschurli.arsmagicalegacy.api.network;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import javax.annotation.Nonnull;

/**
 * @author Minecraftschurli
 * @version 2019-12-12
 */
public interface IPacket {
    void serialize(PacketBuffer buf);

    void deserialize(PacketBuffer buf);

    boolean handle(@Nonnull NetworkEvent.Context ctx);
}
