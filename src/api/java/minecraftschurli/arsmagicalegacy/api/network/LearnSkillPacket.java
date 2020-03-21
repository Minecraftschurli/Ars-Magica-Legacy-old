package minecraftschurli.arsmagicalegacy.api.network;

import minecraftschurli.arsmagicalegacy.api.capability.CapabilityHelper;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

/**
 * @author Minecraftschurli
 * @version 2019-12-02
 */
public class LearnSkillPacket implements IPacket {
    private String skillId;

    public LearnSkillPacket(String id) {
        this.skillId = id;
    }

    public LearnSkillPacket() {}

    @Override
    public void serialize(PacketBuffer buf) {
        buf.writeString(skillId);
    }

    @Override
    public void deserialize(PacketBuffer buf) {
        this.skillId = buf.readString(32767);
    }

    @Override
    public boolean handle(NetworkEvent.Context ctx) {
        CapabilityHelper.learnSkill(ctx.getSender(), this.skillId);
        return true;
    }
}
