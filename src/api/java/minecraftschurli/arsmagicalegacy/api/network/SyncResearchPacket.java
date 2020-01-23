package minecraftschurli.arsmagicalegacy.api.network;

import minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import minecraftschurli.arsmagicalegacy.api.capability.CapabilityHelper;
import minecraftschurli.arsmagicalegacy.api.capability.IResearchStorage;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * @author Minecraftschurli
 * @version 2019-11-28
 */
public class SyncResearchPacket implements IPacket {

    private Map<Integer, Integer> points;
    private List<ResourceLocation> skills;

    public SyncResearchPacket(IResearchStorage capability) {
        points = capability.getPoints();
        skills = capability.getLearned();
    }

    public SyncResearchPacket(PacketBuffer buf) {
        points = new HashMap<>();
        skills = new ArrayList<>();
        for (int i = 0; i < buf.readInt(); i++){
            points.put(buf.readInt(), buf.readInt());
        }
        for (int i = 0; i < buf.readInt(); i++) {
            skills.add(buf.readResourceLocation());
        }
    }

    @Override
    public void toBytes(PacketBuffer buf) {
        buf.writeInt(points.size());
        points.forEach((tier, points) -> {
            buf.writeInt(tier);
            buf.writeInt(points);
        });
        buf.writeInt(skills.size());
        skills.forEach(buf::writeResourceLocation);
    }

    @Override
    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ArsMagicaAPI.getLocalPlayer().getCapability(CapabilityHelper.getResearchCapability()).ifPresent(iStorage -> {
            iStorage.forgetAll();
            points.forEach(iStorage::set);
            skills.forEach(iStorage::learn);
        });
        ctx.get().setPacketHandled(true);
    }
}
