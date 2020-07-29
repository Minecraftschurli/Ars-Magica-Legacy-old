package minecraftschurli.arsmagicalegacy.util;

import minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import minecraftschurli.arsmagicalegacy.objects.block.occulus.OcculusScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.TranslationTextComponent;

public final class ClientUtil {
    public static void displayOcculusScreen(PlayerEntity player) {
        Minecraft.getInstance().displayGuiScreen(new OcculusScreen(new TranslationTextComponent(ArsMagicaAPI.MODID + ".occulus.displayname"), player));
    }
}
