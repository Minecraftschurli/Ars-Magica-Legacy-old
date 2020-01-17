package minecraftschurli.arsmagicalegacy.network;

import minecraftschurli.arsmagicalegacy.*;
import minecraftschurli.arsmagicalegacy.capabilities.research.ResearchCapability;
import minecraftschurli.arsmagicalegacy.capabilities.research.IResearchStorage;
import minecraftschurli.arsmagicalegacy.capabilities.research.ResearchStorage;
import net.minecraft.nbt.*;
import net.minecraft.network.*;
import net.minecraftforge.fml.network.*;

import java.util.function.*;

/**
 * @author Minecraftschurli
 * @version 2019-11-28
 */
public class SyncResearchPacket implements IPacket {

    private IResearchStorage capability;

    public SyncResearchPacket(IResearchStorage capability) {
        this.capability = capability;
    }

    public SyncResearchPacket(PacketBuffer buf) {
        this.capability = new ResearchStorage();
        ResearchCapability.RESEARCH.readNBT(this.capability, null, buf.readCompoundTag());
    }

    @Override
    public void toBytes(PacketBuffer buf) {
        buf.writeCompoundTag((CompoundNBT) ResearchCapability.RESEARCH.writeNBT(this.capability, null));
    }

    @Override
    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ArsMagicaLegacy.proxy.getLocalPlayer().getCapability(ResearchCapability.RESEARCH).ifPresent(iStorage -> iStorage.setFrom(capability));
        ctx.get().setPacketHandled(true);
    }
}
