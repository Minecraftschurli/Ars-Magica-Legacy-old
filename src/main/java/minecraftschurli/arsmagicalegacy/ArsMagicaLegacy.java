package minecraftschurli.arsmagicalegacy;

import minecraftschurli.arsmagicalegacy.api.ArsMagicaLegacyAPI;
import minecraftschurli.arsmagicalegacy.api.SkillPointRegistry;
import minecraftschurli.arsmagicalegacy.api.SkillRegistry;
import minecraftschurli.arsmagicalegacy.api.SpellRegistry;
import minecraftschurli.arsmagicalegacy.capabilities.burnout.CapabilityBurnout;
import minecraftschurli.arsmagicalegacy.capabilities.magic.CapabilityMagic;
import minecraftschurli.arsmagicalegacy.capabilities.mana.CapabilityMana;
import minecraftschurli.arsmagicalegacy.capabilities.research.CapabilityResearch;
import minecraftschurli.arsmagicalegacy.event.TickHandler;
import minecraftschurli.arsmagicalegacy.handler.PotionEffectHandler;
import minecraftschurli.arsmagicalegacy.init.*;
import minecraftschurli.arsmagicalegacy.network.NetworkHandler;
import minecraftschurli.arsmagicalegacy.objects.item.InfinityOrbItem;
import minecraftschurli.arsmagicalegacy.util.MagicHelper;
import minecraftschurli.arsmagicalegacy.worldgen.WorldGenerator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.IDyeableArmorItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ColorHandlerEvent;
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
    public static final Logger LOGGER = LogManager.getLogger(MODID);
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

        modEventBus.register(SpellRegistry.class);
        modEventBus.register(SkillRegistry.class);
        modEventBus.register(ArsMagicaLegacyAPI.class);
        MinecraftForge.EVENT_BUS.register(ArsMagicaLegacy.class);
        MinecraftForge.EVENT_BUS.register(TickHandler.class);
        MinecraftForge.EVENT_BUS.register(PotionEffectHandler.class);

        proxy.preInit();

        IInit.setEventBus(modEventBus);

        ModBlocks.register();
        ModFluids.register();
        ModItems.register();
        ModEntities.register();
        ModParticles.register();
        ModEffects.register();
        ModBiomes.register();
        ModContainers.register();
    }

    @SubscribeEvent
    public static void onAttachPlayerCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof PlayerEntity) {
            event.addCapability(new ResourceLocation(MODID, "mana"), new CapabilityMana());
            event.addCapability(new ResourceLocation(MODID, "burnout"), new CapabilityBurnout());
            event.addCapability(new ResourceLocation(MODID, "research"), new CapabilityResearch());
            event.addCapability(new ResourceLocation(MODID, "magic"), new CapabilityMagic());
        }
    }

    @SubscribeEvent
    public static void playerClone(final PlayerEvent.Clone event) {
        if (event.isWasDeath()) {
            PlayerEntity newPlayer = event.getPlayer();
            PlayerEntity oldPlayer = event.getOriginal();
            MagicHelper.getManaCapability(newPlayer).setFrom(MagicHelper.getManaCapability(oldPlayer));
            MagicHelper.getBurnoutCapability(newPlayer).setFrom(MagicHelper.getBurnoutCapability(oldPlayer));
            MagicHelper.getResearchCapability(newPlayer).setFrom(MagicHelper.getResearchCapability(oldPlayer));
            MagicHelper.getMagicCapability(newPlayer).setFrom(MagicHelper.getMagicCapability(oldPlayer));
        }
    }

    @SubscribeEvent
    public static void onPlayerLogin(final PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getPlayer() instanceof ServerPlayerEntity) {
            ServerPlayerEntity player = (ServerPlayerEntity) event.getPlayer();
            MagicHelper.syncMana(player);
            MagicHelper.syncBurnout(player);
            MagicHelper.syncResearch(player);
            MagicHelper.syncMagic(player);
        }
    }

    private void registerItemColorHandler(ColorHandlerEvent.Item event) {
        event.getItemColors()
                .register(
                        (stack, tint) ->
                                tint == 0 ?
                                        SkillPointRegistry.getSkillPointFromTier(stack.getTag().getInt(InfinityOrbItem.TYPE_KEY)).getColor()
                                        : -1,
                        ModItems.INFINITY_ORB.get());
        event.getItemColors().register((stack, tint) -> tint == 0 ? ((IDyeableArmorItem) stack.getItem()).getColor(stack) : -1, ModItems.SPELL_BOOK.get());
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
        CapabilityMagic.register();
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

    public String getVersion() {
        return "0.0.1.0";
    }
}
