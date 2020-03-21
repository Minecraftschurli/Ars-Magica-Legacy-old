package minecraftschurli.arsmagicalegacy.api.network;

import minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

/**
 * @author Minecraftschurli
 * @version 2020-02-15
 */
public class UpdateStepHeightPacket implements IPacket {
    private float stepHeight;

    public UpdateStepHeightPacket(float stepHeight) {
        this.stepHeight = stepHeight;
    }

    public UpdateStepHeightPacket() {}

    @Override
    public void serialize(PacketBuffer buf) {
        buf.writeFloat(stepHeight);
    }

    @Override
    public void deserialize(PacketBuffer buf) {
        this.stepHeight = buf.readFloat();
    }

    @Override
    public boolean handle(NetworkEvent.Context ctx) {
        ArsMagicaAPI.getLocalPlayer().stepHeight = this.stepHeight;
        return true;
    }
}
