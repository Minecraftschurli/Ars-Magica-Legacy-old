package minecraftschurli.arsmagicalegacy.network;

import minecraftschurli.arsmagicalegacy.ArsMagicaLegacy;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

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
        INSTANCE.registerMessage(nextID(), SyncResearchPoints.class, SyncResearchPoints::toBytes, SyncResearchPoints::new, SyncResearchPoints::handle);
    }
}
