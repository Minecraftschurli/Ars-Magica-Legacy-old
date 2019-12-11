package minecraftschurli.arsmagicalegacy.proxy;

import minecraftschurli.arsmagicalegacy.ArsMagicaLegacy;
import minecraftschurli.arsmagicalegacy.event.UIRender;
import minecraftschurli.arsmagicalegacy.init.ModContainers;
import minecraftschurli.arsmagicalegacy.objects.block.inscriptiontable.InscriptionTableContainer;
import minecraftschurli.arsmagicalegacy.objects.block.inscriptiontable.InscriptionTableScreen;
import minecraftschurli.arsmagicalegacy.objects.item.spellbook.SpellBookContainer;
import minecraftschurli.arsmagicalegacy.objects.item.spellbook.SpellBookScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.common.MinecraftForge;

/**
 * @author Minecraftschurli
 * @version 2019-11-07
 */
public class ClientProxy implements IProxy {
    @Override
    public void preInit() {
        MinecraftForge.EVENT_BUS.register(new UIRender());
        OBJLoader.INSTANCE.addDomain(ArsMagicaLegacy.MODID);
    }

    @Override
    public void init() {
        //noinspection RedundantCast
        ScreenManager.registerFactory(ModContainers.SPELLBOOK.get(), (ScreenManager.IScreenFactory<SpellBookContainer, SpellBookScreen>) SpellBookScreen::new);
        ScreenManager.registerFactory(ModContainers.INSCRIPTION_TABLE.get(), (ScreenManager.IScreenFactory<InscriptionTableContainer, InscriptionTableScreen>) InscriptionTableScreen::new);
    }

    @Override
    public PlayerEntity getLocalPlayer() {
        return Minecraft.getInstance().player;
    }
}
