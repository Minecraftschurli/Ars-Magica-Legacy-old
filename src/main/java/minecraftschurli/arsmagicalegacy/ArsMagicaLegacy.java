package minecraftschurli.arsmagicalegacy;

import minecraftschurli.arsmagicalegacy.init.Registries;
import minecraftschurli.arsmagicalegacy.proxy.ClientProxy;
import minecraftschurli.arsmagicalegacy.proxy.IProxy;
import minecraftschurli.arsmagicalegacy.proxy.ServerProxy;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Georg Burkl
 * @version 2019-11-07
 */
@Mod(ArsMagicaLegacy.MODID)
public class ArsMagicaLegacy {
    public static final String MODID = "arsmagicalegacy";

    public static IProxy proxy = DistExecutor.runForDist(() -> ClientProxy::new, () -> ServerProxy::new);

    public static ArsMagicaLegacy instance;

    public static final Logger LOGGER = LogManager.getLogger(MODID);

    public ArsMagicaLegacy (){
        instance = this;

        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::clientSetup);
        modEventBus.addListener(this::enqueueIMC);
        modEventBus.addListener(this::processIMC);

        Registries.setEventBus(modEventBus);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.debug("Common Setup");
        proxy.init();
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        LOGGER.debug("Client Setup");
    }

    private void enqueueIMC(final InterModEnqueueEvent event) {
        LOGGER.debug("IMC Enqueue");
    }

    private void processIMC(final InterModProcessEvent event) {
        LOGGER.debug("IMC Process");
    }
}
