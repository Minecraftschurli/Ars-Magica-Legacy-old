package minecraftschurli.arsmagicalegacy.network;

import minecraftschurli.arsmagicalegacy.util.*;
import net.minecraft.network.*;
import net.minecraftforge.fml.network.*;

import java.util.function.*;

/**
 * @author Minecraftschurli
 * @version 2019-12-02
 */
public class LearnSkillPacket {
    private final String skillId;

    public LearnSkillPacket(String id) {
        this.skillId = id;
    }

    public LearnSkillPacket(PacketBuffer buf) {
        this.skillId = buf.readString(32767);
    }

    public void toBytes(PacketBuffer buf) {
        buf.writeString(skillId);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        MagicHelper.learnSkill(ctx.get().getSender(), this.skillId);
        ctx.get().setPacketHandled(true);
    }
}
