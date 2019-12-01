package minecraftschurli.arsmagicalegacy.network;

import minecraftschurli.arsmagicalegacy.ArsMagicaLegacy;
import minecraftschurli.arsmagicalegacy.capabilities.research.CapabilityResearch;
import minecraftschurli.arsmagicalegacy.capabilities.research.IResearchStorage;
import minecraftschurli.arsmagicalegacy.capabilities.research.ResearchStorage;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * @author Minecraftschurli
 * @version 2019-11-28
 */
public class SyncResearch {

    private IResearchStorage capability;

    public SyncResearch(IResearchStorage capability) {
        this.capability = capability;
    }

    public SyncResearch(PacketBuffer buf) {
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
