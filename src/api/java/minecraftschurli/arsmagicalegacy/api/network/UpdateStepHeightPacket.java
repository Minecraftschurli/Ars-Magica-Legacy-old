package minecraftschurli.arsmagicalegacy.api.network;

import minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * @author Minecraftschurli
 * @version 2020-02-15
 */
public class UpdateStepHeightPacket implements IPacket {
    private float stepHeight;

    public UpdateStepHeightPacket(float stepHeight) {
        this.stepHeight = stepHeight;
    }

    @Override
    public void toBytes(PacketBuffer buf) {
        buf.writeFloat(stepHeight);
    }

    @Override
    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ArsMagicaAPI.getLocalPlayer().stepHeight = this.stepHeight;
        ctx.get().setPacketHandled(true);
    }
}
