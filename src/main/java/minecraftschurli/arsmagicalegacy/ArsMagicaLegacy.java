package minecraftschurli.arsmagicalegacy;

import com.google.common.collect.ImmutableList;
import javax.annotation.Nonnull;
import minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import minecraftschurli.arsmagicalegacy.api.IMCHandler;
import minecraftschurli.arsmagicalegacy.api.affinity.AffinityOverrideModel;
import minecraftschurli.arsmagicalegacy.api.config.Config;
import minecraftschurli.arsmagicalegacy.api.network.NetworkHandler;
import minecraftschurli.arsmagicalegacy.api.registry.RegistryHandler;
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
import minecraftschurli.arsmagicalegacy.objects.block.craftingaltar.CraftingAltarModel;
import minecraftschurli.arsmagicalegacy.worldgen.WorldGenerator;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.BlockModelShapes;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
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
            if ((rl = affinity.getTextureLocation("tome")) != null)
                ModelLoader.addSpecialModel(new ModelResourceLocation(rl, "inventory"));
            if ((rl = affinity.getTextureLocation("essence")) != null)
                ModelLoader.addSpecialModel(new ModelResourceLocation(rl, "inventory"));
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
}
