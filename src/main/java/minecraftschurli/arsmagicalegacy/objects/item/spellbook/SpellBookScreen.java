package minecraftschurli.arsmagicalegacy.objects.item.spellbook;

import minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import org.lwjgl.opengl.GL11;

/**
 * @author Minecraftschurli
 * @version 2019-11-07
 */
public class SpellBookScreen extends ContainerScreen<SpellBookContainer> {
    private static final ResourceLocation background = new ResourceLocation(ArsMagicaAPI.MODID, "textures/gui/spell_book_gui.png");
    private static final ResourceLocation extras = new ResourceLocation(ArsMagicaAPI.MODID, "textures/gui/spell_book_gui_2.png");
    private int bookActiveSlot;

    public SpellBookScreen(SpellBookContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
        xSize = 256;
        ySize = 250;
    }

    public SpellBookScreen(SpellBookContainer container, PlayerInventory inventoryplayer, ItemStack spellBookStack) {
        super(container, inventoryplayer, spellBookStack.getDisplayName());
        bookActiveSlot = ((SpellBookItem) spellBookStack.getItem()).getActiveSlot(spellBookStack);
        xSize = 256;
        ySize = 250;
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        this.renderBackground();
        super.render(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
        this.fillGradient(0, 0, this.width, this.height, -1072689136, -804253680);
        Minecraft.getInstance().getTextureManager().bindTexture(background);
        GL11.glColor4f(1, 1, 1, 1);
        int l = (width - xSize) / 2;
        int i1 = (height - ySize) / 2;
        blit(l, i1, 0, 0, xSize, ySize);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        for (int i = 0; i < 8; i++) {
            ItemStack stack = container.getInventory().get(i);
            if (stack.isEmpty()) {
                continue;
            }
            String[] nameParts = stack.getDisplayName().getString().split(" ");
            int X = 37;
            int Y = 5 + i * 18;
            int maxWidth = 120;
            int line = 1;
            for (String s : nameParts) {
                int width = font.getStringWidth(s);
                if (X + width > maxWidth && line == 1) {
                    Y += font.FONT_HEIGHT;
                    line++;
                    X = 37;
                }
                font.drawString(s.replace("\247b", ""), X, Y, 0x404040);
                X += font.getStringWidth(s + " ");
            }
        }
        int x = 16;
        int y = 3 + bookActiveSlot * 18;
        Minecraft.getInstance().getTextureManager().bindTexture(extras);
        GL11.glColor4f(1, 1, 1, 1);
        blit(x, y, 0, 0, 20, 20);
        //special slot
        int index = this.container.specialSlotIndex - (8 + 4 * 8 + 3 * 9);
        x = 48 + 18 * index;
        y = 229;
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColor4f(1, 1, 1, 0.5F);
        blit(x, y, 0, 20, 16, 16);
        GL11.glDisable(GL11.GL_BLEND);
    }

    @Override
    public boolean keyPressed(int p_keyPressed_1_, int p_keyPressed_2_, int p_keyPressed_3_) {
        if (!Character.isDigit(p_keyPressed_1_))
            return super.keyPressed(p_keyPressed_1_, p_keyPressed_2_, p_keyPressed_3_);
        return true;
    }
}
