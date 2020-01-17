package minecraftschurli.arsmagicalegacy;

import minecraftschurli.arsmagicalegacy.capabilities.burnout.BurnoutCapability;
import minecraftschurli.arsmagicalegacy.capabilities.research.ResearchCapability;
import minecraftschurli.arsmagicalegacy.capabilities.magic.*;
import minecraftschurli.arsmagicalegacy.capabilities.mana.*;
import minecraftschurli.arsmagicalegacy.handler.*;
import minecraftschurli.arsmagicalegacy.init.*;
import minecraftschurli.arsmagicalegacy.network.*;
import minecraftschurli.arsmagicalegacy.objects.block.craftingaltar.CraftingAltarViewTER;
import minecraftschurli.arsmagicalegacy.objects.block.craftingaltar.CraftingAltarViewTileEntity;
import minecraftschurli.arsmagicalegacy.worldgen.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.common.*;
import net.minecraftforge.event.*;
import net.minecraftforge.eventbus.api.*;
import net.minecraftforge.fml.*;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.config.*;
import net.minecraftforge.fml.event.lifecycle.*;
import net.minecraftforge.fml.event.server.*;
import net.minecraftforge.fml.javafmlmod.*;
import org.apache.logging.log4j.*;

/**
 * @author Minecraftschurli
 * @version 2019-11-07
 */
@Mod(ArsMagicaLegacy.MODID)
public final class ArsMagicaLegacy {
    public static final String MODID = "arsmagicalegacy";
    public static final String MODNAME = "ArsMagicaLegacy";

    public static final ItemGroup ITEM_GROUP = new ItemGroup(MODID) {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(ModItems.ARCANE_COMPENDIUM.get());
        }
    };
    public static final Logger LOGGER = LogManager.getLogger();
    @SuppressWarnings("Convert2MethodRef")
    public static minecraftschurli.arsmagicalegacy.proxy.IProxy proxy = DistExecutor.runForDist(() -> () -> new minecraftschurli.arsmagicalegacy.proxy.ClientProxy(), () -> () -> new minecraftschurli.arsmagicalegacy.proxy.ServerProxy());
    public static ArsMagicaLegacy instance;

    public ArsMagicaLegacy() {
        instance = this;

        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::clientSetup);
        modEventBus.addListener(this::enqueueIMC);
        modEventBus.addListener(this::processIMC);
        modEventBus.addListener(this::registerItemColorHandler);
        modEventBus.addListener(Config::reload);

        minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI.setup();

        MinecraftForge.EVENT_BUS.register(ArsMagicaLegacy.class);
        MinecraftForge.EVENT_BUS.register(TickHandler.class);
        MinecraftForge.EVENT_BUS.register(PotionEffectHandler.class);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.commonSpec);

        proxy.preInit();

        IInit.setEventBus(modEventBus);

        ModBlocks.register();
        ModFluids.register();
        ModItems.register();
        ModEntities.register();
        ModTileEntities.register();
        ModParticles.register();
        ModEffects.register();
        ModBiomes.register();
        ModContainers.register();
    }

    @SubscribeEvent
    public static void onAttachPlayerCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof PlayerEntity) {
            event.addCapability(new ResourceLocation(MODID, "mana"), new ManaCapability());
            event.addCapability(new ResourceLocation(MODID, "burnout"), new BurnoutCapability());
            event.addCapability(new ResourceLocation(MODID, "research"), new ResearchCapability());
            event.addCapability(new ResourceLocation(MODID, "magic"), new MagicCapability());
        }
    }

    @SubscribeEvent
    public static void serverLoad(FMLServerStartingEvent event) {
        ModCommands.register(event.getCommandDispatcher());
    }

    private void registerItemColorHandler(ColorHandlerEvent.Item event) {
        event.getItemColors().register((stack, tint) -> tint == 0 ? ((IDyeableArmorItem) stack.getItem()).getColor(stack) : -1, ModItems.SPELL_BOOK.get());
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.debug("Common Setup");
        minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI.init();
        WorldGenerator.setupBiomeGen();
        WorldGenerator.setupOregen();
        proxy.init();
        NetworkHandler.registerMessages();

        ManaCapability.register();
        BurnoutCapability.register();
        ResearchCapability.register();
        MagicCapability.register();
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        LOGGER.debug("Client Setup");
        ClientRegistry.bindTileEntitySpecialRenderer(CraftingAltarViewTileEntity.class, new CraftingAltarViewTER());
    }

    private void enqueueIMC(final InterModEnqueueEvent event) {
        LOGGER.debug("IMC Enqueue");
    }

    private void processIMC(final InterModProcessEvent event) {
        LOGGER.debug("IMC Process");
    }

    public String getVersion() {
        return "0.0.1.0";
    }
}
