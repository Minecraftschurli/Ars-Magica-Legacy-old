package minecraftschurli.arsmagicalegacy.proxy;

import minecraftschurli.arsmagicalegacy.ArsMagicaLegacy;
import minecraftschurli.arsmagicalegacy.event.UIRender;
import minecraftschurli.arsmagicalegacy.init.ModContainers;
import minecraftschurli.arsmagicalegacy.objects.block.occulus.OcculusContainer;
import minecraftschurli.arsmagicalegacy.objects.block.occulus.OcculusScreen;
import minecraftschurli.arsmagicalegacy.objects.item.spellbook.SpellBookContainer;
import minecraftschurli.arsmagicalegacy.objects.item.spellbook.SpellBookScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;
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
        ScreenManager.registerFactory(ModContainers.SPELLBOOK.get(), new ScreenManager.IScreenFactory<SpellBookContainer, SpellBookScreen>() {
            @Override
            public SpellBookScreen create(SpellBookContainer container, PlayerInventory inventory, ITextComponent name) {
                return new SpellBookScreen(container, inventory, name);
            }
        });
        ScreenManager.registerFactory(ModContainers.OCCULUS.get(), new ScreenManager.IScreenFactory<OcculusContainer, OcculusScreen>() {
            @Override
            public OcculusScreen create(OcculusContainer container, PlayerInventory inventory, ITextComponent name) {
                return new OcculusScreen(name, container, inventory.player);
            }
        });
    }

    @Override
    public PlayerEntity getLocalPlayer() {
        return Minecraft.getInstance().player;
    }
}
