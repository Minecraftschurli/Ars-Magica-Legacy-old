package minecraftschurli.arsmagicalegacy;

import minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import minecraftschurli.arsmagicalegacy.api.registry.SkillPointRegistry;
import minecraftschurli.arsmagicalegacy.compat.patchouli.PatchouliCompat;
import minecraftschurli.arsmagicalegacy.init.ModBlocks;
import minecraftschurli.arsmagicalegacy.init.ModContainers;
import minecraftschurli.arsmagicalegacy.init.ModFluids;
import minecraftschurli.arsmagicalegacy.init.ModItems;
import minecraftschurli.arsmagicalegacy.init.ModParticles;
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
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.item.IDyeableArmorItem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD, modid = ArsMagicaAPI.MODID)
public final class ClientSetup {
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
        RenderTypeLookup.setRenderLayer(ModBlocks.ILLUMINATION_LIGHT.get(), RenderType.getTranslucent());
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
