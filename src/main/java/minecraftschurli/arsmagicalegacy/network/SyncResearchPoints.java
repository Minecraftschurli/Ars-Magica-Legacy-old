package minecraftschurli.arsmagicalegacy.network;

import minecraftschurli.arsmagicalegacy.ArsMagicaLegacy;
import minecraftschurli.arsmagicalegacy.api.spellsystem.SkillPoint;
import minecraftschurli.arsmagicalegacy.capabilities.research.CapabilityResearch;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * @author Minecraftschurli
 * @version 2019-11-28
 */
public class SyncResearchPoints {

    private int[] points;

    public SyncResearchPoints(int[] points) {
        this.points = points;
    }

    public SyncResearchPoints(PacketBuffer buf) {
        this.points = buf.readVarIntArray();
    }

    public void toBytes(PacketBuffer buf) {
        buf.writeVarIntArray(this.points);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ArsMagicaLegacy.proxy.getLocalPlayer().getCapability(CapabilityResearch.RESEARCH_POINTS).ifPresent(iStorage -> {
            for (int i = 0; i < this.points.length; i++) {
                iStorage.set(SkillPoint.TYPES.get(i).getName(), this.points[i]);
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
