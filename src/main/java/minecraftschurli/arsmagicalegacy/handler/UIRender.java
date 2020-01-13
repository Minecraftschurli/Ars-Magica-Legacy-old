package minecraftschurli.arsmagicalegacy.handler;

import com.mojang.blaze3d.platform.*;
import minecraftschurli.arsmagicalegacy.*;
import minecraftschurli.arsmagicalegacy.capabilities.burnout.*;
import minecraftschurli.arsmagicalegacy.capabilities.mana.*;
import minecraftschurli.arsmagicalegacy.init.*;
import minecraftschurli.arsmagicalegacy.objects.item.spellbook.SpellBookItem;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.common.util.*;
import net.minecraftforge.eventbus.api.*;

import java.util.List;

/**
 * @author Minecraftschurli
 * @version 2019-11-12
 */
public class UIRender {

    public static final ResourceLocation BAR_TEXTURE = new ResourceLocation(ArsMagicaLegacy.MODID, "textures/gui/bar.png");
    public static final ResourceLocation SPELL_BOOK_UI_TEXTURE = new ResourceLocation(ArsMagicaLegacy.MODID, "textures/gui/spellbook_ui.png");
    private final Minecraft mc = Minecraft.getInstance();
    private float blitOffset;

    public static int getWidth(double d1, double d2) {
        int w = 80;
        double d3 = Math.max(w * (d1 / d2), 0);
        return (int) Math.ceil(d3);
    }

    private FontRenderer fontRenderer() {
        return mc.fontRenderer;
    }

    @SubscribeEvent
    public void render(RenderGameOverlayEvent.Pre event) {
        if (event.getType() != RenderGameOverlayEvent.ElementType.ALL)
            return;

        Entity renderViewEntity = mc.getRenderViewEntity();
        PlayerEntity player = (PlayerEntity) renderViewEntity;
        if (player == null)
            return;
        renderManaBar(player);
        renderBurnoutBar(player);
        if (player.getHeldItem(Hand.MAIN_HAND).getItem().equals(ModItems.SPELL_BOOK.get()))
            renderSpellBook(player, Hand.MAIN_HAND);
        else if (player.getHeldItem(Hand.OFF_HAND).getItem().equals(ModItems.SPELL_BOOK.get()))
            renderSpellBook(player, Hand.OFF_HAND);
    }

    private void renderSpellBook(PlayerEntity player, Hand hand) {
        double scaledWidth = mc.mainWindow.getScaledWidth();
        int scaledHeight = mc.mainWindow.getScaledHeight();
        int spellBookUIWidth = 148;
        int spellBookUIHeight = 22;
        int spellBookUIX = (int)Math.round(0.5 * scaledWidth - 230);
        int spellBookUIY = scaledHeight + 3 - spellBookUIHeight;
        int activeSpellSize = 15;
        ItemStack bookStack = player.getHeldItem(hand);
        int bookSlot = ((SpellBookItem)bookStack.getItem()).getActiveSlot(bookStack);
        int x = spellBookUIX + bookSlot * 13;
        int y = spellBookUIY;
        //ArsMagicaLegacy.LOGGER.debug("{}, {}", spellBookUIX, spellBookUIY);

        mc.getTextureManager().bindTexture(SPELL_BOOK_UI_TEXTURE);

        GlStateManager.color4f(1.0f, 1.0f, 1.0f, 1.0f);

        /*if (hand == Hand.MAIN_HAND) {
            int slot = player.inventory.getSlotFor(player.getHeldItem(hand));

        } else {

        }*/

        setBlitOffset(-5);

        drawTexturedModalRect(spellBookUIX, spellBookUIY, 0, 0, 106, 15, spellBookUIWidth, spellBookUIHeight);

        List<ItemStack> activeScrolls = ((SpellBookItem)bookStack.getItem()).getActiveInventory(bookStack);

        for (int n = 0; n < 8; ++n){
            float iconX = spellBookUIX + 1.5f + n * 12.9f;
            ItemStack stackItem = activeScrolls.get(n);
            if (stackItem == null){
                continue;
            }
            GlStateManager.pushMatrix();
            GlStateManager.translatef(iconX, spellBookUIY + 1.5f, getBlitOffset());
            GlStateManager.scalef(12f / 16f, 12f / 16f, 12f / 16f);
            mc.getItemRenderer().renderItemIntoGUI(stackItem, 0, 0);
            GlStateManager.popMatrix();
        }
        mc.getTextureManager().bindTexture(SPELL_BOOK_UI_TEXTURE);
        setBlitOffset(1000);
        drawTexturedModalRect(x, y, 148, 0, activeSpellSize, activeSpellSize, 20, 20);
        setBlitOffset(0);
    }

    private void renderBurnoutBar(PlayerEntity player) {
        int scaledWidth = mc.mainWindow.getScaledWidth();
        int scaledHeight = mc.mainWindow.getScaledHeight();
        int xStart = scaledWidth / 2 + 121;
        int yStart = scaledHeight - 13;
        LazyOptional<IBurnoutStorage> burnoutStore = player.getCapability(CapabilityBurnout.BURNOUT, null);
        double burnout = burnoutStore.map(IBurnoutStorage::getBurnout).orElse(0.0f);
        double maxBurnout = burnoutStore.map(IBurnoutStorage::getMaxBurnout).orElse(0.0f);
        renderBar(xStart, yStart, burnout, maxBurnout, 0x880000, "burnout");
    }

    private void renderManaBar(PlayerEntity player) {
        int scaledWidth = mc.mainWindow.getScaledWidth();
        int scaledHeight = mc.mainWindow.getScaledHeight();
        int xStart = scaledWidth / 2 + 121;
        int yStart = scaledHeight - 23;
        LazyOptional<IManaStorage> manaStore = player.getCapability(CapabilityMana.MANA, null);
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
        drawTexturedModalRect(x + 2, y + 2, 2, 11, getWidth(value, maxValue) - 1, 7);
        GlStateManager.color4f(1, 1, 1, 1);

        if (false) {
            int i2 = getStringLength((int) value + "");
            drawStringOnHUD((int) value + "", x - 5 - i2, y - 1, color);
        }
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
        mc.getProfiler().endSection();
    }

    public void drawTexturedModalRect(int x, int y, int textureX, int textureY, int width, int height) {
        mc.ingameGUI.blit(x, y, textureX, textureY, width, height);
    }

    public void drawTexturedModalRect(int dst_x, int dst_y, float src_x, float src_y, int dst_width, int dst_height, int src_width, int src_height) {
        //AbstractGui.blit(dst_x, dst_y, src_x, src_y, dst_width, dst_height, src_width, src_height);
        /*Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.pos(dst_x, dst_y + dst_height, getBlitOffset()).tex(src_x, src_y + src_height).endVertex();
        bufferbuilder.pos(dst_x + dst_width, dst_y + dst_height, getBlitOffset()).tex(src_x + src_width, src_y + src_height).endVertex();
        bufferbuilder.pos(dst_x + dst_width, dst_y, getBlitOffset()).tex(src_x + src_width, src_y).endVertex();
        bufferbuilder.pos(dst_x, dst_y, getBlitOffset()).tex(src_x, src_y).endVertex();
        tessellator.draw();*/
        float var7 = 0.00390625F;
        float var8 = 0.00390625F;

        Tessellator var9 = Tessellator.getInstance();
        var9.getBuffer().begin(7, DefaultVertexFormats.POSITION_TEX);
        var9.getBuffer().pos(dst_x + 0, dst_y + dst_height, getBlitOffset()).tex((src_x + 0) * var7, (src_y + src_height) * var8).endVertex();
        var9.getBuffer().pos(dst_x + dst_width, dst_y + dst_height, getBlitOffset()).tex((src_x + src_width) * var7, (src_y + src_height) * var8).endVertex();
        var9.getBuffer().pos(dst_x + dst_width, dst_y + 0, getBlitOffset()).tex((src_x + src_width) * var7, (src_y + 0) * var8).endVertex();
        var9.getBuffer().pos(dst_x + 0, dst_y + 0, getBlitOffset()).tex((src_x + 0) * var7, (src_y + 0) * var8).endVertex();
        var9.draw();
    }

    private float getBlitOffset() {
        return blitOffset;
    }

    private void setBlitOffset(float blitOffset) {
        this.blitOffset = blitOffset;
    }

    public int getStringLength(String s) {
        return fontRenderer().getStringWidth(s);
    }

    public void drawStringOnHUD(String string, int xOffset, int yOffset, int color) {
        xOffset += 2;
        yOffset += 2;
        fontRenderer().drawStringWithShadow(string, xOffset, yOffset, color);
    }
}
