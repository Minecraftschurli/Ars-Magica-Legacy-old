package minecraftschurli.arsmagicalegacy.network;

import minecraftschurli.arsmagicalegacy.ArsMagicaLegacy;
import minecraftschurli.arsmagicalegacy.capabilities.mana.CapabilityMana;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * @author Minecraftschurli
 * @version 2019-11-14
 */
public class SyncMana {
    private float mana;
    private float maxMana;

    public SyncMana(PacketBuffer buf) {
        this.maxMana = buf.readFloat();
        this.mana = buf.readFloat();
    }

    public void toBytes(PacketBuffer buf) {
        buf.writeFloat(this.maxMana);
        buf.writeFloat(this.mana);
    }

    public SyncMana(float mana, float maxMana) {
        this.maxMana = maxMana;
        this.mana = mana;
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ArsMagicaLegacy.proxy.getClientPlayer().getCapability(CapabilityMana.MANA).ifPresent(iManaStorage -> {
            iManaStorage.setMaxMana(this.maxMana);
            iManaStorage.setMana(this.mana);
        });
        ctx.get().setPacketHandled(true);
    }
}
