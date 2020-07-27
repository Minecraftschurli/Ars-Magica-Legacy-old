package minecraftschurli.arsmagicalegacy;

import com.google.common.collect.ImmutableList;
import javax.annotation.Nonnull;
import minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import minecraftschurli.arsmagicalegacy.api.IMCHandler;
import minecraftschurli.arsmagicalegacy.api.affinity.AffinityOverrideModel;
import minecraftschurli.arsmagicalegacy.api.config.Config;
import minecraftschurli.arsmagicalegacy.api.network.NetworkHandler;
import minecraftschurli.arsmagicalegacy.api.registry.RegistryHandler;
import minecraftschurli.arsmagicalegacy.api.registry.SkillPointRegistry;
import minecraftschurli.arsmagicalegacy.capabilities.AbilityCapability;
import minecraftschurli.arsmagicalegacy.capabilities.AffinityCapability;
import minecraftschurli.arsmagicalegacy.capabilities.BurnoutCapability;
import minecraftschurli.arsmagicalegacy.capabilities.ContingencyCapability;
import minecraftschurli.arsmagicalegacy.capabilities.EtheriumCapability;
import minecraftschurli.arsmagicalegacy.capabilities.MagicCapability;
import minecraftschurli.arsmagicalegacy.capabilities.ManaCapability;
import minecraftschurli.arsmagicalegacy.capabilities.ResearchCapability;
import minecraftschurli.arsmagicalegacy.capabilities.RiftStorageCapability;
import minecraftschurli.arsmagicalegacy.compat.patchouli.PatchouliCompat;
import minecraftschurli.arsmagicalegacy.handler.AffinityAbilityHelper;
import minecraftschurli.arsmagicalegacy.handler.LevelUpHandler;
import minecraftschurli.arsmagicalegacy.handler.PotionEffectHandler;
import minecraftschurli.arsmagicalegacy.handler.TickHandler;
import minecraftschurli.arsmagicalegacy.handler.UIRender;
import minecraftschurli.arsmagicalegacy.init.IInit;
import minecraftschurli.arsmagicalegacy.init.ModAffinities;
import minecraftschurli.arsmagicalegacy.init.ModBiomes;
import minecraftschurli.arsmagicalegacy.init.ModBlocks;
import minecraftschurli.arsmagicalegacy.init.ModCommands;
import minecraftschurli.arsmagicalegacy.init.ModContainers;
import minecraftschurli.arsmagicalegacy.init.ModEffects;
import minecraftschurli.arsmagicalegacy.init.ModEntities;
import minecraftschurli.arsmagicalegacy.init.ModFeatures;
import minecraftschurli.arsmagicalegacy.init.ModFluids;
import minecraftschurli.arsmagicalegacy.init.ModItems;
import minecraftschurli.arsmagicalegacy.init.ModParticles;
import minecraftschurli.arsmagicalegacy.init.ModRituals;
import minecraftschurli.arsmagicalegacy.init.ModSkillTrees;
import minecraftschurli.arsmagicalegacy.init.ModSpellParts;
import minecraftschurli.arsmagicalegacy.init.ModTileEntities;
import minecraftschurli.arsmagicalegacy.objects.block.blackaurem.BlackAuremTER;
import minecraftschurli.arsmagicalegacy.objects.block.craftingaltar.CraftingAltarModel;
import minecraftschurli.arsmagicalegacy.objects.block.craftingaltar.CraftingAltarViewTER;
import minecraftschurli.arsmagicalegacy.objects.block.inscriptiontable.InscriptionTableContainer;
import minecraftschurli.arsmagicalegacy.objects.block.inscriptiontable.InscriptionTableScreen;
import minecraftschurli.arsmagicalegacy.objects.block.obelisk.ObeliskContainer;
import minecraftschurli.arsmagicalegacy.objects.block.obelisk.ObeliskScreen;
import minecraftschurli.arsmagicalegacy.objects.item.InfinityOrbItem;
import minecraftschurli.arsmagicalegacy.objects.item.spellbook.SpellBookContainer;
import minecraftschurli.arsmagicalegacy.objects.item.spellbook.SpellBookScreen;
import minecraftschurli.arsmagicalegacy.objects.particle.SimpleParticle;
import minecraftschurli.arsmagicalegacy.worldgen.WorldGenerator;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.BlockModelShapes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IDyeableArmorItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerAboutToStartEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.forgespi.language.IModInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Minecraftschurli
 * @version 2019-11-07
 */
@Mod(ArsMagicaAPI.MODID)
public final class ArsMagicaLegacy {
    public static final ItemGroup ITEM_GROUP = new ItemGroup(ArsMagicaAPI.MODID) {
        @Nonnull
        @Override
        @OnlyIn(Dist.CLIENT)
        public ItemStack createIcon() {
            return PatchouliCompat.getCompendiumStack();
        }
    };
    public static final Logger LOGGER = LogManager.getLogger(ArsMagicaAPI.MODID);
    public static ArsMagicaLegacy instance;

    public ArsMagicaLegacy() {
        instance = this;
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(IMCHandler::processIMC);
        modEventBus.addListener(EventPriority.LOW, this::onRegistrySetupFinish);
        modEventBus.register(Config.class);
        MinecraftForge.EVENT_BUS.addListener(this::onAttachPlayerCapabilities);
        MinecraftForge.EVENT_BUS.addListener(this::onServerLoad);
        MinecraftForge.EVENT_BUS.addListener(this::beforeServerLoad);
        MinecraftForge.EVENT_BUS.register(TickHandler.class);
        MinecraftForge.EVENT_BUS.register(PotionEffectHandler.class);
        MinecraftForge.EVENT_BUS.register(LevelUpHandler.class);
        preInit();
    }

    //region =========LIFECYCLE=========

    private void preInit() {
        ArsMagicaAPI.setup();
        Config.setup();
        //noinspection Convert2MethodRef
        DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> preInitClient());
        IInit.setEventBus(FMLJavaModLoadingContext.get().getModEventBus());
    }

    private void preInitClient() {
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::registerModels);
        modEventBus.addListener(this::replaceModel);
        MinecraftForge.EVENT_BUS.register(new UIRender());
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        ArsMagicaAPI.init();
        WorldGenerator.setupBiomeGen();
        WorldGenerator.setupModFeatures();
        NetworkHandler.registerMessages();
        ManaCapability.register();
        BurnoutCapability.register();
        ResearchCapability.register();
        MagicCapability.register();
        RiftStorageCapability.register();
        AffinityCapability.register();
        AbilityCapability.register();
        ContingencyCapability.register();
        EtheriumCapability.register();
//        ForgeRegistries.BIOMES.getValues().stream().filter(Predicates.instanceOf(ICustomFeatureBiome.class)).map(ICustomFeatureBiome.class::cast).forEach(ICustomFeatureBiome::init);
        ModBiomes.WITCHWOOD_FOREST.get().init();
        AffinityAbilityHelper.registerListeners();
        PatchouliCompat.init();
    }

    //endregion
    //region =========EVENTS=========

    private void onAttachPlayerCapabilities(final AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof PlayerEntity) {
            event.addCapability(new ResourceLocation(ArsMagicaAPI.MODID, "mana"), new ManaCapability());
            event.addCapability(new ResourceLocation(ArsMagicaAPI.MODID, "burnout"), new BurnoutCapability());
            event.addCapability(new ResourceLocation(ArsMagicaAPI.MODID, "research"), new ResearchCapability());
            event.addCapability(new ResourceLocation(ArsMagicaAPI.MODID, "magic"), new MagicCapability());
            event.addCapability(new ResourceLocation(ArsMagicaAPI.MODID, "rift_storage"), new RiftStorageCapability());
            event.addCapability(new ResourceLocation(ArsMagicaAPI.MODID, "affinity"), new AffinityCapability());
            event.addCapability(new ResourceLocation(ArsMagicaAPI.MODID, "affinity_ability"), new AbilityCapability());
            event.addCapability(new ResourceLocation(ArsMagicaAPI.MODID, "contingency"), new ContingencyCapability());
        }
    }

    private void registerModels(final ModelRegistryEvent event) {
        RegistryHandler.getAffinityRegistry().forEach(affinity -> {
            ResourceLocation rl;
            if ((rl = affinity.getTextureLocation("tome")) != null) ModelLoader.addSpecialModel(new ModelResourceLocation(rl, "inventory"));
            if ((rl = affinity.getTextureLocation("essence")) != null) ModelLoader.addSpecialModel(new ModelResourceLocation(rl, "inventory"));
        });
    }

    private void replaceModel(final ModelBakeEvent event) {
        {
            ModelResourceLocation key = new ModelResourceLocation(ModItems.AFFINITY_TOME.getId(), "inventory");
            IBakedModel oldModel = event.getModelRegistry().get(key);
            event.getModelRegistry().put(key, new AffinityOverrideModel(oldModel, "tome"));
        }
        {
            ModelResourceLocation key = new ModelResourceLocation(ModItems.AFFINITY_ESSENCE.getId(), "inventory");
            IBakedModel oldModel = event.getModelRegistry().get(key);
            event.getModelRegistry().put(key, new AffinityOverrideModel(oldModel, "essence"));
        }
        {
            ImmutableList<BlockState> states = ModBlocks.ALTAR_CORE.get().getStateContainer().getValidStates();
            for (BlockState blockState : states) {
                ModelResourceLocation key = BlockModelShapes.getModelLocation(blockState);
                IBakedModel oldModel = event.getModelRegistry().get(key);
                event.getModelRegistry().put(key, new CraftingAltarModel(oldModel));
            }
        }
        /*{
            ModelResourceLocation key = new ModelResourceLocation(ModItems.SPELL.getId(), "inventory");
            IBakedModel oldModel = event.getModelRegistry().get(key);
            event.getModelRegistry().put(key, new SpellModel(oldModel));
        }*/
    }

    private void onServerLoad(final FMLServerStartingEvent event) {
        ModCommands.register(event.getCommandDispatcher());
    }

    private void beforeServerLoad(final FMLServerAboutToStartEvent event) {
        ArsMagicaAPI.beforeServerLoad(event);
    }
    //endregion
    //region =========UTIL=========

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
        ModSkillTrees.register();
        ModAffinities.register();
        ModSpellParts.register();
        ModRituals.register();
    }

    public IModInfo getModInfo() {
        return ModList.get().getModContainerById(ArsMagicaAPI.MODID).map(ModContainer::getModInfo).get();
    }

    public String getVersion() {
        return getModInfo().getVersion().getQualifier();
    }
    //endregion

    @Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD, modid = ArsMagicaAPI.MODID)
    public static final class ClientSetup {
        @SubscribeEvent
        public static void registerItemColorHandler(ColorHandlerEvent.Item e) {
            e.getItemColors().register((stack, index) -> index == 0 ? ((IDyeableArmorItem) stack.getItem()).getColor(stack) : -1, ModItems.SPELL_BOOK.get());
            e.getItemColors().register((stack, index) -> index == 0 && stack.hasTag() ? SkillPointRegistry.getSkillPointFromTier(stack.getTag().getInt(InfinityOrbItem.TYPE_KEY)).getColor() : -1, ModItems.INFINITY_ORB.get());
        }

        @SuppressWarnings("RedundantCast")
        @SubscribeEvent
        public static void clientSetup(final FMLClientSetupEvent event) {
            PatchouliCompat.clientInit();
            ScreenManager.registerFactory(ModContainers.SPELLBOOK.get(), (ScreenManager.IScreenFactory<SpellBookContainer, SpellBookScreen>) SpellBookScreen::new);
            ScreenManager.registerFactory(ModContainers.INSCRIPTION_TABLE.get(), (ScreenManager.IScreenFactory<InscriptionTableContainer, InscriptionTableScreen>) InscriptionTableScreen::new);
            ScreenManager.registerFactory(ModContainers.ETHERIUM_GENERATOR.get(), (ScreenManager.IScreenFactory<ObeliskContainer, ObeliskScreen>) ObeliskScreen::new);
            ClientRegistry.bindTileEntityRenderer(ModTileEntities.ALTAR_VIEW.get(), CraftingAltarViewTER::new);
//        ClientRegistry.bindTileEntityRenderer(ModTileEntities.ALTAR_CORE.get(), CraftingAltarTER::new);
            ClientRegistry.bindTileEntityRenderer(ModTileEntities.BLACK_AUREM.get(), BlackAuremTER::new);
            RenderTypeLookup.setRenderLayer(ModBlocks.AUM.get(), RenderType.getCutout());
            RenderTypeLookup.setRenderLayer(ModBlocks.CERUBLOSSOM.get(), RenderType.getCutout());
            RenderTypeLookup.setRenderLayer(ModBlocks.DESERT_NOVA.get(), RenderType.getCutout());
            RenderTypeLookup.setRenderLayer(ModBlocks.TARMA_ROOT.get(), RenderType.getCutout());
            RenderTypeLookup.setRenderLayer(ModBlocks.WAKEBLOOM.get(), RenderType.getCutout());
            RenderTypeLookup.setRenderLayer(ModBlocks.WITCHWOOD_SAPLING.get(), RenderType.getCutout());
            RenderTypeLookup.setRenderLayer(ModBlocks.OCCULUS.get(), RenderType.getCutout());
            RenderTypeLookup.setRenderLayer(ModBlocks.INSCRIPTION_TABLE.get(), RenderType.getCutout());
            RenderTypeLookup.setRenderLayer(ModBlocks.WITCHWOOD_DOOR.get(), RenderType.getCutout());
            RenderTypeLookup.setRenderLayer(ModBlocks.WITCHWOOD_TRAPDOOR.get(), RenderType.getCutout());
            RenderTypeLookup.setRenderLayer(ModBlocks.MAGIC_WALL.get(), RenderType.getTranslucent());
            RenderTypeLookup.setRenderLayer(ModBlocks.REDSTONE_INLAY.get(), RenderType.getCutout());
            RenderTypeLookup.setRenderLayer(ModBlocks.IRON_INLAY.get(), RenderType.getCutout());
            RenderTypeLookup.setRenderLayer(ModBlocks.GOLD_INLAY.get(), RenderType.getCutout());
            RenderTypeLookup.setRenderLayer(ModBlocks.OBELISK.get(), RenderType.getCutout());
            RenderTypeLookup.setRenderLayer(ModBlocks.CELESTIAL_PRISM.get(), RenderType.getCutout());
            RenderTypeLookup.setRenderLayer(ModBlocks.BLACK_AUREM.get(), RenderType.getCutout());
            RenderTypeLookup.setRenderLayer(ModBlocks.WIZARD_CHALK.get(), RenderType.getCutout());
            RenderTypeLookup.setRenderLayer(ModFluids.LIQUID_ESSENCE_FLOWING.get(), RenderType.getTranslucent());
            RenderTypeLookup.setRenderLayer(ModFluids.LIQUID_ESSENCE.get(), RenderType.getTranslucent());
            RenderTypeLookup.setRenderLayer(ModBlocks.ALTAR_CORE.get(), RenderType.getTranslucent());
        }

        @SubscribeEvent
        public static void preStitch(TextureStitchEvent.Pre event) {
            if (!event.getMap().getTextureLocation().toString().equals(CraftingAltarModel.BLOCK_ATLAS)) return;
            event.addSprite(CraftingAltarModel.OVERLAY_LOC);
        }

        @SubscribeEvent
        public static void postStitch(TextureStitchEvent.Post event) {
            if (!event.getMap().getTextureLocation().toString().equals(CraftingAltarModel.BLOCK_ATLAS)) return;
            CraftingAltarModel.OVERLAY = event.getMap().getSprite(CraftingAltarModel.OVERLAY_LOC);
        }

        @SubscribeEvent
        public static void registerParticleFactories(ParticleFactoryRegisterEvent e) {
            Minecraft.getInstance().particles.registerFactory(ModParticles.ARCANE.get(), SimpleParticle.Factory::new);
            Minecraft.getInstance().particles.registerFactory(ModParticles.CLOCK.get(), SimpleParticle.Factory::new);
            Minecraft.getInstance().particles.registerFactory(ModParticles.EMBER.get(), SimpleParticle.Factory::new);
            Minecraft.getInstance().particles.registerFactory(ModParticles.GHOST.get(), SimpleParticle.Factory::new);
            Minecraft.getInstance().particles.registerFactory(ModParticles.IMPLOSION.get(), SimpleParticle.Factory::new);
            Minecraft.getInstance().particles.registerFactory(ModParticles.LEAF.get(), SimpleParticle.Factory::new);
            Minecraft.getInstance().particles.registerFactory(ModParticles.LENS_FLARE.get(), SimpleParticle.Factory::new);
            Minecraft.getInstance().particles.registerFactory(ModParticles.LIGHT.get(), SimpleParticle.Factory::new);
            Minecraft.getInstance().particles.registerFactory(ModParticles.PLANT.get(), SimpleParticle.Factory::new);
            Minecraft.getInstance().particles.registerFactory(ModParticles.PULSE.get(), SimpleParticle.Factory::new);
            Minecraft.getInstance().particles.registerFactory(ModParticles.ROCK.get(), SimpleParticle.Factory::new);
            Minecraft.getInstance().particles.registerFactory(ModParticles.ROTATING_RINGS.get(), SimpleParticle.Factory::new);
            Minecraft.getInstance().particles.registerFactory(ModParticles.SHINY.get(), SimpleParticle.Factory::new);
            Minecraft.getInstance().particles.registerFactory(ModParticles.SMOKE.get(), SimpleParticle.Factory::new);
            Minecraft.getInstance().particles.registerFactory(ModParticles.SNOWFLAKE.get(), SimpleParticle.Factory::new);
            Minecraft.getInstance().particles.registerFactory(ModParticles.SPARKLE.get(), SimpleParticle.Factory::new);
            Minecraft.getInstance().particles.registerFactory(ModParticles.SYMBOL.get(), SimpleParticle.Factory::new);
            Minecraft.getInstance().particles.registerFactory(ModParticles.WATERBALL.get(), SimpleParticle.Factory::new);
            Minecraft.getInstance().particles.registerFactory(ModParticles.WIND.get(), SimpleParticle.Factory::new);
            Minecraft.getInstance().particles.registerFactory(ModParticles.WITCHWOOD_LEAF.get(), SimpleParticle.Factory::new);
//        Minecraft.getInstance().particles.registerFactory(ModParticles.BEAM.get(), BeamParticle.Factory::new);
//        Minecraft.getInstance().particles.registerFactory(ModParticles.DARK_BEAM.get(), BeamParticle.Factory::new);
//        Minecraft.getInstance().particles.registerFactory(ModParticles.LIGHT_BEAM.get(), BeamParticle.Factory::new);
            Minecraft.getInstance().particles.registerFactory(ModParticles.NONE_HAND.get(), SimpleParticle.Factory::new);
            Minecraft.getInstance().particles.registerFactory(ModParticles.WATER_HAND.get(), SimpleParticle.Factory::new);
            Minecraft.getInstance().particles.registerFactory(ModParticles.FIRE_HAND.get(), SimpleParticle.Factory::new);
            Minecraft.getInstance().particles.registerFactory(ModParticles.EARTH_HAND.get(), SimpleParticle.Factory::new);
            Minecraft.getInstance().particles.registerFactory(ModParticles.AIR_HAND.get(), SimpleParticle.Factory::new);
            Minecraft.getInstance().particles.registerFactory(ModParticles.LIGHTNING_HAND.get(), SimpleParticle.Factory::new);
            Minecraft.getInstance().particles.registerFactory(ModParticles.ICE_HAND.get(), SimpleParticle.Factory::new);
            Minecraft.getInstance().particles.registerFactory(ModParticles.NATURE_HAND.get(), SimpleParticle.Factory::new);
            Minecraft.getInstance().particles.registerFactory(ModParticles.LIFE_HAND.get(), SimpleParticle.Factory::new);
            Minecraft.getInstance().particles.registerFactory(ModParticles.ARCANE_HAND.get(), SimpleParticle.Factory::new);
            Minecraft.getInstance().particles.registerFactory(ModParticles.ENDER_HAND.get(), SimpleParticle.Factory::new);
        }
    }
}
