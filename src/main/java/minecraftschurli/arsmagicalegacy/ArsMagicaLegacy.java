package minecraftschurli.arsmagicalegacy;

import minecraftschurli.arsmagicalegacy.capabilities.burnout.CapabilityBurnout;
import minecraftschurli.arsmagicalegacy.capabilities.mana.CapabilityMana;
import minecraftschurli.arsmagicalegacy.capabilities.research.CapabilityResearch;
import minecraftschurli.arsmagicalegacy.init.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
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
 * @author Minecraftschurli
 * @version 2019-11-07
 */
@Mod(ArsMagicaLegacy.MODID)
public class ArsMagicaLegacy {
    public static final String MODID = "arsmagicalegacy";
    public static final ItemGroup ITEM_GROUP = new ItemGroup(MODID) {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(ModItems.SPELL_BOOK.get());
        }
    };

    public static minecraftschurli.arsmagicalegacy.proxy.IProxy proxy = DistExecutor.runForDist(() -> () -> new minecraftschurli.arsmagicalegacy.proxy.ClientProxy(), () -> () -> new minecraftschurli.arsmagicalegacy.proxy.ServerProxy());

    public static ArsMagicaLegacy instance;

    public static final Logger LOGGER = LogManager.getLogger(MODID);

    public ArsMagicaLegacy (){
        instance = this;

        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::clientSetup);
        modEventBus.addListener(this::enqueueIMC);
        modEventBus.addListener(this::processIMC);
        modEventBus.addListener(this::onAttachPlayerCapabilities);

        Registries.setEventBus(modEventBus);

        ModBlocks.register();
        ModFluids.register();
        ModItems.register();
        ModEffects.register();
        ModContainers.register();

        proxy.preInit();
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.debug("Common Setup");
        proxy.init();
        CapabilityMana.register();
        CapabilityBurnout.register();
        CapabilityResearch.register();
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

    private void onAttachPlayerCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof PlayerEntity) {
            event.addCapability(new ResourceLocation(MODID, "mana"), new CapabilityMana());
            event.addCapability(new ResourceLocation(MODID, "burnout"), new CapabilityBurnout());
            event.addCapability(new ResourceLocation(MODID, "research"), new CapabilityResearch());
        }
    }

    /*private void onAttachCapabilities(AttachCapabilitiesEvent<ItemStack> event) {
        event.addCapability(new ResourceLocation(MODID, "spell"), null);
    }*/
}
