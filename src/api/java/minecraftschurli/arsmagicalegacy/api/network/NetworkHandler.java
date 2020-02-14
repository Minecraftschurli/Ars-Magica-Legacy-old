package minecraftschurli.arsmagicalegacy.api.network;

import minecraftschurli.arsmagicalegacy.api.*;
import net.minecraft.util.*;
import net.minecraftforge.fml.network.*;
import net.minecraftforge.fml.network.simple.*;

/**
 * @author Minecraftschurli
 * @version 2019-11-14
 */
public class NetworkHandler {
    private static final String PROTOCOL_VERSION = "1";
    public static SimpleChannel INSTANCE;
    private static int ID = 0;

    public static int nextID() {
        return ID++;
    }

    public static void registerMessages() {
        INSTANCE = NetworkRegistry.newSimpleChannel(
                new ResourceLocation(ArsMagicaAPI.MODID, "main"),
                () -> PROTOCOL_VERSION,
                PROTOCOL_VERSION::equals,
                PROTOCOL_VERSION::equals
        );
        INSTANCE.registerMessage(nextID(), SyncManaPacket.class, SyncManaPacket::toBytes, SyncManaPacket::new, SyncManaPacket::handle);
        INSTANCE.registerMessage(nextID(), SyncBurnoutPacket.class, SyncBurnoutPacket::toBytes, SyncBurnoutPacket::new, SyncBurnoutPacket::handle);
        INSTANCE.registerMessage(nextID(), SyncResearchPacket.class, SyncResearchPacket::toBytes, SyncResearchPacket::new, SyncResearchPacket::handle);
        INSTANCE.registerMessage(nextID(), SyncMagicPacket.class, SyncMagicPacket::toBytes, SyncMagicPacket::new, SyncMagicPacket::handle);
        INSTANCE.registerMessage(nextID(), SyncAffinityPacket.class, SyncAffinityPacket::toBytes, SyncAffinityPacket::new, SyncAffinityPacket::handle);
        INSTANCE.registerMessage(nextID(), LearnSkillPacket.class, LearnSkillPacket::toBytes, LearnSkillPacket::new, LearnSkillPacket::handle);
        INSTANCE.registerMessage(nextID(), InscriptionTablePacket.class, InscriptionTablePacket::toBytes, InscriptionTablePacket::new, InscriptionTablePacket::handle);
        INSTANCE.registerMessage(nextID(), TEClientSyncPacket.class, TEClientSyncPacket::toBytes, TEClientSyncPacket::new, TEClientSyncPacket::handle);
    }
}
