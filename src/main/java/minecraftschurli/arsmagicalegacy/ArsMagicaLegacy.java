package minecraftschurli.arsmagicalegacy;

import javax.annotation.Nonnull;
import minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import minecraftschurli.arsmagicalegacy.api.Config;
import minecraftschurli.arsmagicalegacy.api.network.NetworkHandler;
import minecraftschurli.arsmagicalegacy.api.registry.SkillPointRegistry;
import minecraftschurli.arsmagicalegacy.capabilities.AbilityCapability;
import minecraftschurli.arsmagicalegacy.capabilities.AffinityCapability;
import minecraftschurli.arsmagicalegacy.capabilities.BurnoutCapability;
import minecraftschurli.arsmagicalegacy.capabilities.MagicCapability;
import minecraftschurli.arsmagicalegacy.capabilities.ManaCapability;
import minecraftschurli.arsmagicalegacy.capabilities.ResearchCapability;
import minecraftschurli.arsmagicalegacy.capabilities.RiftStorageCapability;
import minecraftschurli.arsmagicalegacy.handler.AffinityAbilityHelper;
import minecraftschurli.arsmagicalegacy.handler.PotionEffectHandler;
import minecraftschurli.arsmagicalegacy.handler.TickHandler;
import minecraftschurli.arsmagicalegacy.init.IInit;
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
import minecraftschurli.arsmagicalegacy.init.ModSpellParts;
import minecraftschurli.arsmagicalegacy.init.ModTags;
import minecraftschurli.arsmagicalegacy.init.ModTileEntities;
import minecraftschurli.arsmagicalegacy.objects.block.craftingaltar.CraftingAltarViewTER;
import minecraftschurli.arsmagicalegacy.objects.item.AffinityTomeItem;
import minecraftschurli.arsmagicalegacy.objects.item.InfinityOrbItem;
import minecraftschurli.arsmagicalegacy.objects.spell.SpellRecipeManager;
import minecraftschurli.arsmagicalegacy.proxy.ClientProxy;
import minecraftschurli.arsmagicalegacy.proxy.ServerProxy;
import minecraftschurli.arsmagicalegacy.worldgen.WorldGenerator;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IDyeableArmorItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
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
import net.minecraftforge.fml.event.server.FMLServerAboutToStartEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.forgespi.language.IModInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import vazkii.patchouli.api.PatchouliAPI;

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
            return getCompendium();
        }
    };
    public static final Logger LOGGER = LogManager.getLogger(ArsMagicaAPI.MODID);
    public static minecraftschurli.arsmagicalegacy.proxy.IProxy proxy = DistExecutor.runForDist(() -> ClientProxy::new, () -> ServerProxy::new);
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
        modEventBus.addListener(EventPriority.LOW, this::onRegistrySetupFinish);
        modEventBus.register(Config.class);
        MinecraftForge.EVENT_BUS.addListener(this::onAttachPlayerCapabilities);
        MinecraftForge.EVENT_BUS.addListener(this::onServerLoad);
        MinecraftForge.EVENT_BUS.addListener(this::beforeServerLoad);
        MinecraftForge.EVENT_BUS.register(TickHandler.class);
        MinecraftForge.EVENT_BUS.register(PotionEffectHandler.class);
        preInit();
    }

    //region =========LIFECYCLE=========

    public static SpellRecipeManager getSpellRecipeManager() {
        return instance.spellRecipeManager;
    }

    public static ItemStack getCompendium() {
        return PatchouliAPI.instance.getBookStack(new ResourceLocation(ArsMagicaAPI.MODID, "arcane_compendium"));
    }

    private void preInit() {
        minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI.setup();
        Config.setup();
        proxy.preInit();
        IInit.setEventBus(FMLJavaModLoadingContext.get().getModEventBus());
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.debug("Common Setup");
        minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI.init();
        WorldGenerator.setupBiomeGen();
        WorldGenerator.setupModFeatures();
        proxy.init();
        NetworkHandler.registerMessages();
        ManaCapability.register();
        BurnoutCapability.register();
        ResearchCapability.register();
        MagicCapability.register();
        RiftStorageCapability.register();
        AffinityCapability.register();
        AbilityCapability.register();
        /*ForgeRegistries.BIOMES.getValues()
                .stream()
                .filter(Predicates.instanceOf(ICustomFeatureBiome.class))
                .map(ICustomFeatureBiome.class::cast)
                .forEach(ICustomFeatureBiome::init);*/
        ModBiomes.WITCHWOOD_FOREST.get().init();
        AffinityAbilityHelper.registerListeners();
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        LOGGER.debug("Client Setup");
        ClientRegistry.bindTileEntityRenderer(ModTileEntities.ALTAR_VIEW.get(), CraftingAltarViewTER::new);
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
        RenderTypeLookup.setRenderLayer(ModFluids.LIQUID_ESSENCE_FLOWING.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(ModFluids.LIQUID_ESSENCE.get(), RenderType.getCutout());
    }
    //endregion

    //region =========EVENTS=========

    private void enqueueIMC(final InterModEnqueueEvent event) {
        LOGGER.debug("IMC Enqueue");
    }

    private void processIMC(final InterModProcessEvent event) {
        LOGGER.debug("IMC Process");
    }

    private void onAttachPlayerCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof PlayerEntity) {
            event.addCapability(new ResourceLocation(ArsMagicaAPI.MODID, "mana"), new ManaCapability());
            event.addCapability(new ResourceLocation(ArsMagicaAPI.MODID, "burnout"), new BurnoutCapability());
            event.addCapability(new ResourceLocation(ArsMagicaAPI.MODID, "research"), new ResearchCapability());
            event.addCapability(new ResourceLocation(ArsMagicaAPI.MODID, "magic"), new MagicCapability());
            event.addCapability(new ResourceLocation(ArsMagicaAPI.MODID, "rift_storage"), new RiftStorageCapability());
            event.addCapability(new ResourceLocation(ArsMagicaAPI.MODID, "affinity"), new AffinityCapability());
            event.addCapability(new ResourceLocation(ArsMagicaAPI.MODID, "affinity_ability"), new AbilityCapability());
        }
    }

    private void onServerLoad(final FMLServerStartingEvent event) {
        ModCommands.register(event.getCommandDispatcher());
    }

    private void beforeServerLoad(final FMLServerAboutToStartEvent event) {
        event.getServer().getResourceManager().addReloadListener(this.spellRecipeManager);
    }
    //endregion

    //region =========UTIL=========

    private void registerItemColorHandler(final ColorHandlerEvent.Item event) {
        event.getItemColors().register((stack, index) -> index == 0 ? ((IDyeableArmorItem) stack.getItem()).getColor(stack) : -1, ModItems.SPELL_BOOK.get());
        event.getItemColors().register((stack, index) -> index == 0 && stack.hasTag() ? SkillPointRegistry.getSkillPointFromTier(stack.getTag().getInt(InfinityOrbItem.TYPE_KEY)).getColor() : -1, ModItems.INFINITY_ORB.get());
        event.getItemColors().register((stack, index) -> index > 0 ? -1 : PotionUtils.getColor(stack), ModItems.POTION_BUNDLE.get());
        event.getItemColors().register((stack, index) -> index > 0 ? -1 : AffinityTomeItem.getAffinity(stack).getColor(), ModItems.AFFINITY_TOME.get());
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
//        BrewingRecipeRegistry.addRecipe(Ingredient.fromStacks(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), Potions.AWKWARD)), Ingredient.fromTag(ModTags.Items.GEMS_CHIMERITE), PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), ModEffects.LESSER_MANA_POTION.get()));
//        BrewingRecipeRegistry.addRecipe(Ingredient.fromStacks(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), Potions.AWKWARD)), Ingredient.fromTag(ModTags.Items.GEMS_TOPAZ), PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), ModEffects.MANA_POTION.get()));
//        BrewingRecipeRegistry.addRecipe(Ingredient.fromStacks(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), Potions.AWKWARD)), Ingredient.fromTag(ModTags.Items.DUSTS_VINTEUM), PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), ModEffects.GREATER_MANA_POTION.get()));
//        BrewingRecipeRegistry.addRecipe(Ingredient.fromStacks(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), Potions.AWKWARD)), Ingredient.fromItems(ModItems.ARCANE_ASH.get()), PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), ModEffects.EPIC_MANA_POTION.get()));
//        BrewingRecipeRegistry.addRecipe(Ingredient.fromStacks(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), Potions.AWKWARD)), Ingredient.fromItems(ModItems.PURIFIED_VINTEUM.get()), PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), ModEffects.LEGENDARY_MANA_POTION.get()));
    }

    public IModInfo getModInfo() {
        return ModList.get().getModContainerById(ArsMagicaAPI.MODID).map(ModContainer::getModInfo).get();
    }

    public String getVersion() {
        return getModInfo().getVersion().getQualifier();
    }
    //endregion
}
