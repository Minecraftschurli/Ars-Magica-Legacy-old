package minecraftschurli.arsmagicalegacy;

import minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import minecraftschurli.arsmagicalegacy.api.Config;
import minecraftschurli.arsmagicalegacy.api.SkillPointRegistry;
import minecraftschurli.arsmagicalegacy.api.network.NetworkHandler;
import minecraftschurli.arsmagicalegacy.capabilities.*;
import minecraftschurli.arsmagicalegacy.handler.PotionEffectHandler;
import minecraftschurli.arsmagicalegacy.handler.TickHandler;
import minecraftschurli.arsmagicalegacy.init.*;
import minecraftschurli.arsmagicalegacy.objects.block.craftingaltar.CraftingAltarViewTER;
import minecraftschurli.arsmagicalegacy.objects.item.InfinityOrbItem;
import minecraftschurli.arsmagicalegacy.objects.spell.SpellRecipeManager;
import minecraftschurli.arsmagicalegacy.worldgen.WorldGenerator;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IDyeableArmorItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.forgespi.language.IModInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Minecraftschurli
 * @version 2019-11-07
 */
@Mod(ArsMagicaLegacy.MODID)
public final class ArsMagicaLegacy {
    public static final String MODID = ArsMagicaAPI.MODID;
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

    private final SpellRecipeManager spellRecipeManager;

    public ArsMagicaLegacy() {
        instance = this;
        spellRecipeManager = new SpellRecipeManager();

        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::clientSetup);
        modEventBus.addListener(this::enqueueIMC);
        modEventBus.addListener(this::processIMC);
        modEventBus.addListener(this::registerItemColorHandler);
        modEventBus.addListener(EventPriority.LOWEST, this::onRegistrySetupFinish);
        modEventBus.register(Config.class);

        minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI.setup();

        MinecraftForge.EVENT_BUS.addListener(this::onAttachPlayerCapabilities);
        MinecraftForge.EVENT_BUS.addListener(this::onServerLoad);
        MinecraftForge.EVENT_BUS.register(TickHandler.class);
        MinecraftForge.EVENT_BUS.register(PotionEffectHandler.class);
        Config.setup();

        proxy.preInit();

        IInit.setEventBus(modEventBus);
    }

    public static SpellRecipeManager getSpellRecipeManager() {
        return instance.spellRecipeManager;
    }

    public IModInfo getModInfo() {
        return ModList.get().getModContainerById(MODID).map(ModContainer::getModInfo).get();
    }

    public String getVersion() {
        return getModInfo().getVersion().getQualifier();
    }

    //region =========EVENTS=========

    private void onAttachPlayerCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof PlayerEntity) {
            event.addCapability(new ResourceLocation(MODID, "mana"), new ManaCapability());
            event.addCapability(new ResourceLocation(MODID, "burnout"), new BurnoutCapability());
            event.addCapability(new ResourceLocation(MODID, "research"), new ResearchCapability());
            event.addCapability(new ResourceLocation(MODID, "magic"), new MagicCapability());
            event.addCapability(new ResourceLocation(MODID, "rift_storage"), new RiftStorageCapability());
            event.addCapability(new ResourceLocation(MODID, "affinity"), new AffinityCapability());
        }
    }

    private void onServerLoad(final FMLServerStartingEvent event) {
        ModCommands.register(event.getCommandDispatcher());
        event.getServer().getResourceManager().addReloadListener(this.spellRecipeManager);
    }

    private void registerItemColorHandler(final ColorHandlerEvent.Item event) {
        event.getItemColors().register((stack, tint) -> tint == 0 ? ((IDyeableArmorItem) stack.getItem()).getColor(stack) : -1, ModItems.SPELL_BOOK.get());
        //noinspection ConstantConditions
        event.getItemColors().register(
                (stack, tint) -> tint == 0 && stack.hasTag() ? SkillPointRegistry.getSkillPointFromTier(stack.getTag().getInt(InfinityOrbItem.TYPE_KEY)).getColor() : -1,
                ModItems.INFINITY_ORB.get()
        );
        event.getItemColors().register((stack, tint) -> tint > 0 ? -1 : PotionUtils.getColor(stack), ModItems.POTION_BUNDLE.get());
    }

    private void onRegistrySetupFinish(final RegistryEvent.NewRegistry event) {
        ModBlocks.register();
        ModFluids.register();
        ModItems.register();
        ModEntities.register();
        ModTileEntities.register();
        ModParticles.register();
        ModEffects.register();
        ModContainers.register();
        ModFeatures.register();
        ModBiomes.register();
        ModSpellParts.register();
    }

    //endregion

    //region =========LIFECYCLE=========

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
        RiftStorageCapability.register();
        AffinityCapability.register();

        /*ForgeRegistries.BIOMES.getValues()
                .stream()
                .filter(Predicates.instanceOf(ICustomFeatureBiome.class))
                .map(ICustomFeatureBiome.class::cast)
                .forEach(ICustomFeatureBiome::init);*/
        ModBiomes.WITCHWOOD_FOREST.get().init();
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        LOGGER.debug("Client Setup");
        ClientRegistry.bindTileEntityRenderer(ModTileEntities.ALTAR_VIEW.get(), CraftingAltarViewTER::new);
        RenderTypeLookup.setRenderLayer(ModBlocks.OCCULUS.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(ModBlocks.INSCRIPTION_TABLE.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(ModBlocks.WITCHWOOD_DOOR.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(ModBlocks.WITCHWOOD_TRAPDOOR.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(ModBlocks.MAGIC_WALL.get(), RenderType.getTranslucent());
        RenderTypeLookup.setRenderLayer(ModBlocks.REDSTONE_INLAY.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(ModBlocks.IRON_INLAY.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(ModBlocks.GOLD_INLAY.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(ModFluids.LIQUID_ESSENCE_FLOWING.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(ModFluids.LIQUID_ESSENCE.get(), RenderType.getCutout());
    }

    private void enqueueIMC(final InterModEnqueueEvent event) {
        LOGGER.debug("IMC Enqueue");
    }

    private void processIMC(final InterModProcessEvent event) {
        LOGGER.debug("IMC Process");
    }

    //endregion
}
