package minecraftschurli.arsmagicalegacy.proxy;

import minecraftschurli.arsmagicalegacy.init.Containers;
import minecraftschurli.arsmagicalegacy.objects.item.spellbook.SpellBookScreen;
import net.minecraft.client.gui.ScreenManager;

/**
 * @author Minecraftschurli
 * @version 2019-11-07
 */
public class ClientProxy implements IProxy {
    @Override
    public void init() {
        ScreenManager.registerFactory(Containers.SPELLBOOK.get(), SpellBookScreen::new);
    }
}
