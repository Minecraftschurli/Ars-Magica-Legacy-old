package minecraftschurli.arsmagicalegacy.api.network;

import minecraftschurli.arsmagicalegacy.api.*;
import minecraftschurli.arsmagicalegacy.api.capability.*;
import net.minecraft.network.*;
import net.minecraft.util.*;
import net.minecraftforge.fml.network.*;

import java.util.*;
import java.util.function.*;

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
        int pointsSize = buf.readInt();
        for (int i = 0; i < pointsSize; i++){
            int tier = buf.readInt();
            int count = buf.readInt();
            points.put(tier, count);
        }
        int skillsSize = buf.readInt();
        for (int i = 0; i < skillsSize; i++) {
            ResourceLocation rl = buf.readResourceLocation();
            skills.add(rl);
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
