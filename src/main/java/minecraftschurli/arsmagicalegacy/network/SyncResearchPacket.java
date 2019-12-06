package minecraftschurli.arsmagicalegacy.network;

import minecraftschurli.arsmagicalegacy.*;
import minecraftschurli.arsmagicalegacy.capabilities.research.*;
import net.minecraft.nbt.*;
import net.minecraft.network.*;
import net.minecraftforge.fml.network.*;

import java.util.function.*;

/**
 * @author Minecraftschurli
 * @version 2019-11-28
 */
public class SyncResearchPacket {

    private IResearchStorage capability;

    public SyncResearchPacket(IResearchStorage capability) {
        this.capability = capability;
    }

    public SyncResearchPacket(PacketBuffer buf) {
        this.capability = new ResearchStorage();
        CapabilityResearch.RESEARCH.readNBT(this.capability, null, buf.readCompoundTag());
    }

    public void toBytes(PacketBuffer buf) {
        buf.writeCompoundTag((CompoundNBT) CapabilityResearch.RESEARCH.writeNBT(this.capability, null));
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ArsMagicaLegacy.proxy.getLocalPlayer().getCapability(CapabilityResearch.RESEARCH).ifPresent(iStorage -> iStorage.setFrom(capability));
        ctx.get().setPacketHandled(true);
    }
}
