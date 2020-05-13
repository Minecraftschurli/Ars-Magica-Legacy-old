package minecraftschurli.arsmagicalegacy.api.network;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import minecraftschurli.arsmagicalegacy.api.capability.CapabilityHelper;
import minecraftschurli.arsmagicalegacy.api.capability.IResearchStorage;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkEvent;

/**
 * @author Minecraftschurli
 * @version 2019-11-28
 */
public class SyncResearchPacket implements IPacket {
    private final Map<Integer, Integer> points;
    private final List<ResourceLocation> skills;

    public SyncResearchPacket(IResearchStorage capability) {
        points = capability.getPoints();
        skills = capability.getLearned();
    }

    public SyncResearchPacket() {
        points = new HashMap<>();
        skills = new ArrayList<>();
    }

    @Override
    public void serialize(PacketBuffer buf) {
        buf.writeInt(points.size());
        points.forEach((tier, points) -> {
            buf.writeInt(tier);
            buf.writeInt(points);
        });
        buf.writeInt(skills.size());
        skills.forEach(buf::writeResourceLocation);
    }

    @Override
    public void deserialize(PacketBuffer buf) {
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
    public boolean handle(NetworkEvent.Context ctx) {
        ArsMagicaAPI.getLocalPlayer().getCapability(CapabilityHelper.getResearchCapability()).ifPresent(iStorage -> {
            iStorage.forgetAll();
            points.forEach(iStorage::set);
            skills.forEach(iStorage::learn);
        });
        return true;
    }
}
