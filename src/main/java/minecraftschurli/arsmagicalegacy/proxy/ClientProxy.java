package minecraftschurli.arsmagicalegacy.proxy;

import minecraftschurli.arsmagicalegacy.*;
import minecraftschurli.arsmagicalegacy.event.*;
import minecraftschurli.arsmagicalegacy.init.*;
import minecraftschurli.arsmagicalegacy.objects.block.craftingaltar.CraftingAltarViewTER;
import minecraftschurli.arsmagicalegacy.objects.block.craftingaltar.CraftingAltarTileEntity;
import minecraftschurli.arsmagicalegacy.objects.block.craftingaltar.CraftingAltarViewTileEntity;
import minecraftschurli.arsmagicalegacy.objects.block.inscriptiontable.*;
import minecraftschurli.arsmagicalegacy.objects.item.spellbook.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.model.IUnbakedModel;
import net.minecraft.client.renderer.model.ModelBakery;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.entity.player.*;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.client.model.obj.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.client.registry.ClientRegistry;

/**
 * @author Minecraftschurli
 * @version 2019-11-07
 */
public class ClientProxy implements IProxy {
    @Override
    public void preInit() {
        ClientRegistry.bindTileEntitySpecialRenderer(CraftingAltarViewTileEntity.class, new CraftingAltarViewTER());
        MinecraftForge.EVENT_BUS.register(new UIRender());
        OBJLoader.INSTANCE.addDomain(ArsMagicaLegacy.MODID);
    }

    @Override
    public void init() {
        //noinspection RedundantCast,ConstantConditions
        ScreenManager.registerFactory(ModContainers.SPELLBOOK.get(), (ScreenManager.IScreenFactory<SpellBookContainer, SpellBookScreen>) SpellBookScreen::new);
        //noinspection RedundantCast,ConstantConditions
        ScreenManager.registerFactory(ModContainers.INSCRIPTION_TABLE.get(), (ScreenManager.IScreenFactory<InscriptionTableContainer, InscriptionTableScreen>) InscriptionTableScreen::new);
    }

    @Override
    public PlayerEntity getLocalPlayer() {
        return Minecraft.getInstance().player;
    }
}
