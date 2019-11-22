package minecraftschurli.arsmagicalegacy;

import minecraftschurli.arsmagicalegacy.capabilities.burnout.CapabilityBurnout;
import minecraftschurli.arsmagicalegacy.capabilities.burnout.IBurnoutStorage;
import minecraftschurli.arsmagicalegacy.capabilities.mana.CapabilityMana;
import minecraftschurli.arsmagicalegacy.capabilities.mana.IManaStorage;
import minecraftschurli.arsmagicalegacy.capabilities.research.CapabilityResearch;
import minecraftschurli.arsmagicalegacy.capabilities.research.IResearchPointsStorage;
import minecraftschurli.arsmagicalegacy.event.TickHandler;
import minecraftschurli.arsmagicalegacy.init.ModItems;
import minecraftschurli.arsmagicalegacy.network.NetworkHandler;
import minecraftschurli.arsmagicalegacy.util.MagicHelper;
import minecraftschurli.arsmagicalegacy.worldgen.WorldGenerator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
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
public final class ArsMagicaLegacy {
    public static final String MODID = "arsmagicalegacy";
    public static final ItemGroup ITEM_GROUP = new ItemGroup(MODID) {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(ModItems.ARCANE_COMPENDIUM.get());
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
        MinecraftForge.EVENT_BUS.register(ArsMagicaLegacy.class);
        MinecraftForge.EVENT_BUS.register(TickHandler.class);

        proxy.preInit();
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.debug("Common Setup");
        WorldGenerator.setupOregen();
        WorldGenerator.setupBiomeGen();
        proxy.init();
        NetworkHandler.registerMessages();
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

    @SubscribeEvent
    public static void onAttachPlayerCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof PlayerEntity) {
            event.addCapability(new ResourceLocation(MODID, "mana"), new CapabilityMana());
            event.addCapability(new ResourceLocation(MODID, "burnout"), new CapabilityBurnout());
            event.addCapability(new ResourceLocation(MODID, "research"), new CapabilityResearch());
        }
    }

    @SubscribeEvent
    public static void playerClone(final PlayerEvent.Clone event) {
        if (event.isWasDeath()){
            final IManaStorage oldManaCap = event.getOriginal().getCapability(CapabilityMana.MANA).orElseThrow(() -> new RuntimeException("No player capability found!"));
            final IManaStorage newManaCap = event.getPlayer().getCapability(CapabilityMana.MANA).orElseThrow(() -> new RuntimeException("No player capability found!"));
            newManaCap.setMana(oldManaCap.getMana());
            newManaCap.setMaxMana(oldManaCap.getMaxMana());
            final IBurnoutStorage oldBurnoutCap = event.getOriginal().getCapability(CapabilityBurnout.BURNOUT).orElseThrow(() -> new RuntimeException("No player capability found!"));
            final IBurnoutStorage newBurnoutCap = event.getPlayer().getCapability(CapabilityBurnout.BURNOUT).orElseThrow(() -> new RuntimeException("No player capability found!"));
            newBurnoutCap.setBurnout(oldBurnoutCap.getBurnout());
            newBurnoutCap.setMaxBurnout(oldBurnoutCap.getMaxBurnout());
            final IResearchPointsStorage oldResearchCap = event.getOriginal().getCapability(CapabilityResearch.RESEARCH_POINTS).orElseThrow(() -> new RuntimeException("No player capability found!"));
            final IResearchPointsStorage newResearchCap = event.getPlayer().getCapability(CapabilityResearch.RESEARCH_POINTS).orElseThrow(() -> new RuntimeException("No player capability found!"));
            newResearchCap.setRed(oldResearchCap.getRed());
            newResearchCap.setGreen(oldResearchCap.getGreen());
            newResearchCap.setBlue(oldResearchCap.getBlue());
        }
    }

    @SubscribeEvent
    public static void onPlayerLogin(final PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getPlayer() instanceof ServerPlayerEntity) {
            ServerPlayerEntity player = (ServerPlayerEntity) event.getPlayer();
            MagicHelper.syncMana(player);
            MagicHelper.syncBurnout(player);
        }
    }

    public String getVersion() {
        return "0.0.1.0";
    }
}
