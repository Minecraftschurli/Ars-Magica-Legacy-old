package minecraftschurli.arsmagicalegacy.event;

import com.mojang.blaze3d.platform.GlStateManager;
import minecraftschurli.arsmagicalegacy.ArsMagicaLegacy;
import minecraftschurli.arsmagicalegacy.capabilities.burnout.CapabilityBurnout;
import minecraftschurli.arsmagicalegacy.capabilities.burnout.IBurnoutStorage;
import minecraftschurli.arsmagicalegacy.capabilities.mana.CapabilityMana;
import minecraftschurli.arsmagicalegacy.capabilities.mana.IManaStorage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.eventbus.api.SubscribeEvent;

/**
 * @author Minecraftschurli
 * @version 2019-11-12
 */
public class UIRender {

    private final Minecraft mc = Minecraft.getInstance();
    private FontRenderer fontRenderer() {return mc.fontRenderer;}
    public static final ResourceLocation BAR_TEXTURE = new ResourceLocation(ArsMagicaLegacy.MODID, "textures/gui/bar.png");

    @SubscribeEvent
    public void renderBars(RenderGameOverlayEvent.Pre event) {
        if (event.getType() != RenderGameOverlayEvent.ElementType.ALL)
            return;

        Entity renderViewEntity = mc.getRenderViewEntity();
        PlayerEntity player = (PlayerEntity) renderViewEntity;
        if (player == null)
            return;
        renderManaBar(player);
        renderBurnoutBar(player);
    }

    private void renderBurnoutBar(PlayerEntity player) {
        int scaledWidth = mc.mainWindow.getScaledWidth();
        int scaledHeight = mc.mainWindow.getScaledHeight();
        int xStart = scaledWidth / 2 - 181;
        int yStart = scaledHeight - 15;
        LazyOptional<IBurnoutStorage> burnoutStore =  player.getCapability(CapabilityBurnout.BURNOUT, null);
        double burnout = burnoutStore.map(IBurnoutStorage::getBurnout).orElse(0.0f);
        double maxBurnout = burnoutStore.map(IBurnoutStorage::getMaxBurnout).orElse(0.0f);
        renderBar(xStart, yStart, burnout, maxBurnout, 0xAA0000, "burnout");
    }

    private void renderManaBar(PlayerEntity player) {
        int scaledWidth = mc.mainWindow.getScaledWidth();
        int scaledHeight = mc.mainWindow.getScaledHeight();
        int xStart = scaledWidth / 2 + 121;
        int yStart = scaledHeight - 15;
        LazyOptional<IManaStorage> manaStore =  player.getCapability(CapabilityMana.MANA, null);
        double mana = manaStore.map(IManaStorage::getMana).orElse(0.0f);
        double maxMana = manaStore.map(IManaStorage::getMaxMana).orElse(0.0f);
        renderBar(xStart, yStart, mana, maxMana, 0x99FFFF, "mana");
    }

    private void renderBar(int x, int y, double value, double maxValue, int color, String name) {
        mc.getProfiler().startSection(name);
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();

        mc.getTextureManager().bindTexture(BAR_TEXTURE);

        drawTexturedModalRect(x, y, 0, 0, 81, 9);

        float r = (color >> 16 & 0xFF) / 255f;
        float g = (color >> 8 & 0xFF) / 255f;
        float b = (color & 0xFF) / 255f;
        GlStateManager.color3f(r, g, b);
        drawTexturedModalRect(x+2, y+2, 2, 11, getWidth(value, maxValue)-1, 7);
        GlStateManager.color4f(1,1,1,1);

        int i2 = getStringLength((int)value + "");
        drawStringOnHUD((int)value + "", x - 5 - i2, y - 1, color);

        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
        mc.getProfiler().endSection();
    }

    public static int getWidth(double d1, double d2) {
        int w = 80;
        double d3 = Math.max(w * (d1 / d2), 0);
        return (int) Math.ceil(d3);
    }

    public void drawTexturedModalRect(int x, int y, int textureX, int textureY, int width, int height) {
        mc.ingameGUI.blit(x, y, textureX, textureY, width, height);
    }

    public int getStringLength(String s) {
        return fontRenderer().getStringWidth(s);
    }

    public void drawScaledBar(double value, double maxValue, int x, int y, boolean left) {

    }

    public void drawStringOnHUD(String string, int xOffset, int yOffset, int color) {
        xOffset += 2;
        yOffset += 2;
        fontRenderer().drawStringWithShadow(string, xOffset, yOffset, color);
    }
}
