package minecraftschurli.arsmagicalegacy.network;

import minecraftschurli.arsmagicalegacy.*;
import minecraftschurli.arsmagicalegacy.api.spellsystem.*;
import minecraftschurli.arsmagicalegacy.capabilities.research.*;
import net.minecraft.network.*;
import net.minecraftforge.fml.network.*;

import java.util.function.*;

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
