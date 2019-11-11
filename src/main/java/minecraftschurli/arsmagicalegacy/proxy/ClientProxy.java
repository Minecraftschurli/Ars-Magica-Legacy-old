package minecraftschurli.arsmagicalegacy.proxy;

import minecraftschurli.arsmagicalegacy.init.Containers;
import minecraftschurli.arsmagicalegacy.objects.item.spellbook.SpellBookContainer;
import minecraftschurli.arsmagicalegacy.objects.item.spellbook.SpellBookScreen;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

/**
 * @author Minecraftschurli
 * @version 2019-11-07
 */
public class ClientProxy implements IProxy {
    @Override
    public void init() {
        ScreenManager.registerFactory(Containers.SPELLBOOK.get(), new ScreenManager.IScreenFactory<SpellBookContainer, SpellBookScreen>() {
            @Override
            public SpellBookScreen create(SpellBookContainer container, PlayerInventory inventory, ITextComponent name) {
                return new SpellBookScreen(container, inventory, name);
            }
        });
    }
}
