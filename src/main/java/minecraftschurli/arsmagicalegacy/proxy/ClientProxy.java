package minecraftschurli.arsmagicalegacy.proxy;

import minecraftschurli.arsmagicalegacy.*;
import minecraftschurli.arsmagicalegacy.event.*;
import minecraftschurli.arsmagicalegacy.init.*;
import minecraftschurli.arsmagicalegacy.objects.item.spellbook.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.text.*;
import net.minecraftforge.client.model.obj.*;
import net.minecraftforge.common.*;

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
    }

    @Override
    public PlayerEntity getLocalPlayer() {
        return Minecraft.getInstance().player;
    }
}
