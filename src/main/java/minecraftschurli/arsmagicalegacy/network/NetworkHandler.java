package minecraftschurli.arsmagicalegacy.network;

import minecraftschurli.arsmagicalegacy.*;
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
                new ResourceLocation(ArsMagicaLegacy.MODID, "main"),
                () -> PROTOCOL_VERSION,
                PROTOCOL_VERSION::equals,
                PROTOCOL_VERSION::equals
        );
        INSTANCE.registerMessage(nextID(), SyncMana.class, SyncMana::toBytes, SyncMana::new, SyncMana::handle);
        INSTANCE.registerMessage(nextID(), SyncBurnout.class, SyncBurnout::toBytes, SyncBurnout::new, SyncBurnout::handle);
    }
}
