package minecraftschurli.arsmagicalegacy.objects.block.inscriptiontable;

import com.mojang.blaze3d.systems.RenderSystem;
import minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import minecraftschurli.arsmagicalegacy.api.capability.CapabilityHelper;
import minecraftschurli.arsmagicalegacy.api.registry.RegistryHandler;
import minecraftschurli.arsmagicalegacy.api.registry.SpellRegistry;
import minecraftschurli.arsmagicalegacy.api.skill.Skill;
import minecraftschurli.arsmagicalegacy.api.spell.AbstractSpellPart;
import minecraftschurli.arsmagicalegacy.api.spell.SpellComponent;
import minecraftschurli.arsmagicalegacy.api.spell.SpellModifier;
import minecraftschurli.arsmagicalegacy.api.spell.SpellShape;
import minecraftschurli.arsmagicalegacy.objects.spell.SpellValidator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.util.InputMappings;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL14;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Minecraftschurli
 * @version 2019-12-09
 */
public class InscriptionTableScreen extends ContainerScreen<InscriptionTableContainer> {
    private static final ResourceLocation background = new ResourceLocation(ArsMagicaAPI.MODID, "textures/gui/inscription_table_gui.png");

    private final PlayerEntity usingPlayer;

    private final List<ResourceLocation> knownShapes;
    private final List<ResourceLocation> knownComponents;
    private final List<ResourceLocation> knownModifiers;
    int iconXStartUpper = 41;
    int iconYStartUpper = 5;
    int iconXStartLower = 41;
    int iconYStartLower = 146;
    int shapeGroupWidth = 37;
    int shapeGroupPadding = 3;
    int shapeGroupY = 108;
    int shapeGroupX = 13;
    int iconStep = 17;
    private AbstractSpellPart hoveredItem;
    private boolean dragging;
    private boolean lowerHover;
    private int lowerHoverIndex;
    private int lowerHoverShapeGroup = -1;
    private int lastMouseX;
    private int lastMouseY;
    private int iconX;
    private int iconY;
    private SpellValidator.ValidationResult result;

    private TextFieldWidget searchBar;
    private TextFieldWidget nameBar;
    private Button createSpellButton;
    private Button resetSpellButton;

    private String defaultSearchLabel = new TranslationTextComponent(ArsMagicaAPI.MODID + ".inscriptiontable.search").applyTextStyle(TextFormatting.GRAY).applyTextStyle(style -> style.setItalic(true)).getFormattedText();
    private String defaultNameLabel = new TranslationTextComponent(ArsMagicaAPI.MODID + ".inscriptiontable.name").applyTextStyle(TextFormatting.GRAY).applyTextStyle(style -> style.setItalic(true)).getFormattedText();

    public InscriptionTableScreen(InscriptionTableContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
        usingPlayer = playerInventory.player;
        xSize = 220;
        ySize = 252;
        dragging = false;

        List<ResourceLocation> skills = CapabilityHelper.getLearned(usingPlayer);
        knownShapes = skills.stream().filter(skill -> RegistryHandler.getSpellPartRegistry().getValue(skill) instanceof SpellShape).collect(Collectors.toList());
        knownComponents = skills.stream().filter(skill -> RegistryHandler.getSpellPartRegistry().getValue(skill) instanceof SpellComponent).collect(Collectors.toList());
        knownModifiers = skills.stream().filter(skill -> RegistryHandler.getSpellPartRegistry().getValue(skill) instanceof SpellModifier).collect(Collectors.toList());
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    protected void init() {
        super.init();

        int l = (width - xSize) / 2;
        int i1 = (height - ySize) / 2;

        searchBar = addButton(new TextFieldWidget(Minecraft.getInstance().fontRenderer,
                39 + l,
                59 + i1,
                141,
                12,
                ""));
        searchBar.setMaxStringLength(64);

        nameBar = addButton(new TextFieldWidget(Minecraft.getInstance().fontRenderer,
                39 + l,
                93 + i1,
                141,
                12,
                ""));
        nameBar.setMaxStringLength(64);

        createSpellButton = new Button(l - 65, i1, 60, 20, new TranslationTextComponent(ArsMagicaAPI.MODID + ".inscriptiontable.makeSpell").getFormattedText(), this::actionPerformed);

        resetSpellButton = new Button(l + 120, i1 + 72, 60, 20, new TranslationTextComponent(ArsMagicaAPI.MODID + ".inscriptiontable.resetSpell").getFormattedText(), this::actionPerformed);
        resetSpellButton.visible = false;

        if (usingPlayer.abilities.isCreativeMode) {
            this.addButton(createSpellButton);
        }
        this.addButton(resetSpellButton);

        nameBar.setText(this.container.getSpellName());
        if (nameBar.getText().equals("")) {
            nameBar.setText(defaultNameLabel);
        }

        searchBar.setText(defaultSearchLabel);

        result = this.container.validateCurrentDefinition();
    }

    protected void actionPerformed(Button par1GuiButton) {
        if (par1GuiButton == createSpellButton && usingPlayer.abilities.isCreativeMode) {
            this.container.giveSpellToPlayer(usingPlayer);
        } else if (par1GuiButton == resetSpellButton) {
            this.container.resetSpellNameAndIcon();
        }
    }

    @Override
    public boolean mouseClicked(double x, double y, int button) {
        super.mouseClicked(x, y, button);

        if (hoveredItem != null) {
            if (spellPartIsValidAddition(hoveredItem) && !lowerHover) {
                dragging = true;
            } else if (lowerHover) {
                if (lowerHoverShapeGroup == -1 && this.container.currentRecipeContains(hoveredItem)) {
                    if (hoveredItem instanceof SpellShape) {
                        int index = lowerHoverIndex;
                        int startIndex = index;
                        int count = 0;
                        index++;
                        while (index < this.container.getCurrentRecipeSize() && !(this.container.getRecipeItemAt(index) instanceof SpellShape)) {
                            count++;
                            index++;
                        }
                        this.container.removeMultipleRecipeParts(startIndex, count);
                    } else {
                        this.container.removeSingleRecipePart(lowerHoverIndex);
                    }
                    result = this.container.validateCurrentDefinition();
                } else if (lowerHoverShapeGroup >= 0) {
                    if (hoveredItem instanceof SpellShape) {
                        int index = lowerHoverIndex;
                        int startIndex = index;
                        int count = 0;
                        index++;
                        while (index < this.container.getShapeGroupSize(lowerHoverShapeGroup) &&
                                !(this.container.getShapeGroupPartAt(lowerHoverShapeGroup, index) instanceof SpellShape)) {
                            count++;
                            index++;
                        }
                        this.container.removeMultipleRecipePartsFromGroup(lowerHoverShapeGroup, startIndex, count);
                    } else {
                        this.container.removeSingleRecipePartFromGroup(lowerHoverShapeGroup, lowerHoverIndex);
                    }
                    result = this.container.validateCurrentDefinition();
                }
            }
        } else {
            boolean boxClick = false;
            if (x >= searchBar.x && x <= searchBar.x + searchBar.getWidth() && y >= searchBar.y && y <= searchBar.y + searchBar.getHeight()) {
                if (button == 1 || searchBar.getText().equals(defaultSearchLabel)) {
                    searchBar.setText("");
                }
                if (nameBar.getText().equals("")) {
                    nameBar.setText(defaultNameLabel);
                }
                boxClick = true;
            }
            if (x >= nameBar.x && x <= nameBar.x + nameBar.getWidth() && y >= nameBar.y && y <= nameBar.y + nameBar.getHeight()) {
                if (button == 1 || nameBar.getText().equals(defaultNameLabel)) {
                    nameBar.setText("");
                    this.container.setSpellName(nameBar.getText());
                }
                if (searchBar.getText().equals("")) {
                    searchBar.setText(defaultSearchLabel);
                }
                boxClick = true;
            }
            if (!boxClick) {
                if (nameBar.getText().equals("")) {
                    nameBar.setText(defaultNameLabel);
                }
                if (searchBar.getText().equals("")) {
                    searchBar.setText(defaultSearchLabel);
                }
            }
            searchBar.mouseClicked(x, y, button);
            nameBar.mouseClicked(x, y, button);
        }
        return true;
    }

    @Override
    public boolean mouseReleased(double x, double y, int action) {
        super.mouseReleased(x, y, action);

        int l = (width - xSize) / 2;
        int i1 = (height - ySize) / 2;
        x -= l;
        y -= i1;

        if (action == 0 || action == 1) {
            if (dragging) {
                dragging = false;
                //lower section
                if (x >= iconXStartLower && x <= iconXStartLower + 150) {
                    if (y >= iconYStartLower && y <= iconYStartLower + 18) {
                        this.container.addRecipePart(hoveredItem);
                        result = this.container.validateCurrentDefinition();
                    }
                }
                //spell stage groups
                int sg = this.container.getNumStageGroups();
                for (int i = 0; i < sg; ++i) {
                    int SGX = shapeGroupX + ((shapeGroupWidth + shapeGroupPadding) * i);
                    int SGY = shapeGroupY;
                    if (x >= SGX && x <= SGX + shapeGroupWidth) {
                        if (y >= SGY && y <= SGY + shapeGroupWidth) {
                            this.container.addRecipePartToGroup(i, hoveredItem);
                            result = this.container.validateCurrentDefinition();
                        }
                    }
                }
            }
        }
        return false;
    }

//	private void drawDropZones(){
//
//		int l = (width - xSize) / 2;
//		int i1 = (height - ySize) / 2;
//
//		drawRectangle(l + IIconXStart_upper, i1 + IIconYStart_upper, 150, 60, 0xFF0000);
//		drawRectangle(l + IIconXStart_lower, i1 + IIconYStart_lower, 150, 20, 0xFF0000);
//
//		int sg = ((InscriptionTableContainer)this.container).getNumStageGroups();
//		for (int i = 0; i < sg; ++i){
//			int SGX = l + shapeGroupX + ((shapeGroupWidth + shapeGroupPadding) * i);
//			int SGY = i1 + shapeGroupY;
//
//			drawRectangle(SGX, SGY, shapeGroupWidth, shapeGroupWidth, 0xFF0000);
//		}
//	}
//
//	private void drawRectangle(int x, int y, int width, int height, int color){
//		AMGuiHelper.line2d(x, y, x + width, y, this.zLevel, color);
//		AMGuiHelper.line2d(x + width, y, x + width, y + height, this.zLevel, color);
//		AMGuiHelper.line2d(x, y + height, x + width, y + height, this.zLevel, color);
//		AMGuiHelper.line2d(x, y, x, y + height, this.zLevel, color);
//	}


    @Override
    public boolean keyPressed(int p_keyPressed_1_, int p_keyPressed_2_, int p_keyPressed_3_) {
        InputMappings.Input mouseKey = InputMappings.getInputByCode(p_keyPressed_1_, p_keyPressed_2_);
        if ((searchBar.isFocused() || nameBar.isFocused()) && (this.minecraft.gameSettings.keyBindInventory.isActiveAndMatches(mouseKey)))
            return true;
        return super.keyPressed(p_keyPressed_1_, p_keyPressed_2_, p_keyPressed_3_);
    }

    @Override
    public boolean charTyped(char par1, int par2) {
        if (searchBar.charTyped(par1, par2)) {
            return true;
        } else if (nameBar.charTyped(par1, par2)) {
            this.container.setSpellName(nameBar.getText());
            return true;
        } else {
            return super.charTyped(par1, par2);
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
        Minecraft.getInstance().getTextureManager().bindTexture(background);
        GL14.glColor4f(1, 1, 1, 1);
        int l = (width - xSize) / 2;
        int i1 = (height - ySize) / 2;
        blit(l, i1, 0, 0, xSize, 165);
        blit(l + 22, i1 + 165, 0, 165, 176, 87);


        int offsetX = l + shapeGroupX;
        for (int sg = 0; sg < InscriptionTableTileEntity.MAX_STAGE_GROUPS; ++sg) {
            if (sg >= this.container.getNumStageGroups())
                GL14.glColor3f(0.5f, 0.5f, 0.5f);
            blit(offsetX + (sg * (shapeGroupWidth + shapeGroupPadding)), i1 + shapeGroupY, 176, 165, 37, 37);
        }

        GL14.glColor3f(1f, 1f, 1f);

        blit(l + 101, i1 + 73, 220, 0, 18, 18);

        lastMouseX = i - l;
        lastMouseY = j - i1;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        super.drawGuiContainerForegroundLayer(par1, par2);

        List<String> label = new ArrayList<>();

        int l = (width - xSize) / 2;
        int i1 = (height - ySize) / 2;

        this.setBlitOffset(0);

        GL14.glEnable(GL14.GL_ALPHA_TEST);

        drawBookIcon();
        boolean hovering = false;
        if (drawAvailableParts(label)) {
            hovering = true;
            lowerHover = false;
        }
        if (drawCurrentRecipe(label, l, i1)) {
            hovering = true;
            lowerHover = true;
        }

        if (result.valid) {
            if (this.container.slotHasStack(0)) {
                if (this.container.slotIsBook(0)) {
                    Minecraft.getInstance().fontRenderer.drawSplitString(new TranslationTextComponent(ArsMagicaAPI.MODID + ".inscriptiontable.bookOut").getFormattedText(), 225, 5, 100, 0xFF7700);
                } else {
                    resetSpellButton.visible = true;
                }

            } else {
                resetSpellButton.visible = false;
            }
        } else {
            resetSpellButton.visible = this.container.slotHasStack(0) && !this.container.slotIsBook(0);
            Minecraft.getInstance().fontRenderer.drawSplitString(result.message, 225, 5, 100, 0xFF7700);
        }
        createSpellButton.visible = false;

        if (!dragging) {
            if (hovering) {
                renderTooltip(label, lastMouseX, lastMouseY, Minecraft.getInstance().fontRenderer);
            } else {
                hoveredItem = null;
            }
        } else {
            GL14.glColor4f(1, 1, 1, 1);
            drawDraggedItem();
        }
        super.renderHoveredToolTip(par1 - l, par2 - i1);
    }

    private void drawBookIcon() {
        int x = this.container.getSlot(0).xPos;
        int y = this.container.getSlot(0).yPos;

        TextureAtlasSprite icon = Minecraft.getInstance().getItemRenderer().getItemModelMesher().getParticleIcon(Items.WRITABLE_BOOK);

        //TODO
        /*if (AMGuiHelper.instance.getFastTicker() < 20)
            GL14.glColor4f(1, 1, 1, 0.4f);
        else*///AMGuiHelper.DrawIconAtXY(icon, bookX, bookY, this.zLevel, 16, 16, true);
        /*GL14.glColor4f(1, 1, 1, 0.7f);

        RenderSystem.matrixMode(GL14.GL_TEXTURE);
        RenderSystem.pushMatrix();
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(RenderSystem.SourceFactor.SRC_ALPHA, RenderSystem.DestFactor.ONE_MINUS_SRC_ALPHA);
        // Minecraft.getInstance().getTextureManager().bindTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE);

        Tessellator tessellator = Tessellator.getInstance();

        boolean drawing = false;
        try {
            tessellator.getBuffer().begin(7, DefaultVertexFormats.POSITION_TEX);
        } catch (IllegalStateException e) {
            drawing = true;
            tessellator.draw();
            tessellator.getBuffer().begin(7, DefaultVertexFormats.POSITION_TEX);
        }

        tessellator.getBuffer().pos(x, y + 16, blitOffset).tex(icon.getMinU(), icon.getMaxV()).endVertex();
        tessellator.getBuffer().pos(x + 16, y + 16, blitOffset).tex(icon.getMaxU(), icon.getMaxV()).endVertex();
        tessellator.getBuffer().pos(x + 16, y, blitOffset).tex(icon.getMaxU(), icon.getMinV()).endVertex();
        tessellator.getBuffer().pos(x, y, blitOffset).tex(icon.getMinU(), icon.getMinV()).endVertex();

        tessellator.draw();

        RenderSystem.disableBlend();
        RenderSystem.popMatrix();
        RenderSystem.matrixMode(GL14.GL_MODELVIEW);

        if (drawing)
            tessellator.getBuffer().begin(7, DefaultVertexFormats.POSITION_TEX);
        GL14.glColor4f(1, 1, 1, 1);*/
    }

    private boolean drawCurrentRecipe(List<String> labelText, int l, int i1) {

        iconX = iconXStartLower;
        iconY = iconYStartLower;

        boolean hovering = false;
        int index = 0;

        //main recipe
        for (int i = 0; i < this.container.getCurrentRecipeSize(); ++i) {
            AbstractSpellPart part = this.container.getRecipeItemAt(i);
            if (Objects.equals(part.getRegistryName(), SpellRegistry.MISSING_SHAPE))
                continue;
            String name = SpellRegistry.getSkillFromPart(part).getName().getFormattedText();

            if (drawIcon(part, false)) {
                labelText.add(name);
                hovering = true;
                lowerHoverIndex = index;
                lowerHoverShapeGroup = -1;
            }
            index++;
        }

        //shape groups
        for (int i = 0; i < this.container.getNumStageGroups(); ++i) {
            for (int n = 0; n < this.container.getShapeGroupSize(i); ++n) {
                AbstractSpellPart part = this.container.getShapeGroupPartAt(i, n);
                String name = SpellRegistry.getSkillFromPart(part).getName().getFormattedText();

                int SGX = shapeGroupX + ((shapeGroupWidth + shapeGroupPadding) * i) + 1;
                int SGY = shapeGroupY;

                iconX = SGX + (n % 2) * iconStep;
                iconY = SGY + (int) Math.floor(n / 2.0) * iconStep;

                if (drawIcon(part, false)) {
                    labelText.add(name);
                    hovering = true;
                    lowerHoverIndex = n;
                    lowerHoverShapeGroup = i;
                }
            }
        }
        return hovering;
    }

    private boolean drawAvailableParts(List<String> labelText) {

        iconX = iconXStartUpper;
        iconY = iconYStartUpper;

        boolean b = drawPartIcons(labelText);

        return b;
    }

    private boolean drawPartIcons(List<String> labelText) {
        boolean hovering = false;
        hovering |= drawIconSet(knownShapes, labelText);
        hovering |= drawIconSet(knownComponents, labelText);
        hovering |= drawIconSet(knownModifiers, labelText);
        return hovering;
    }

    private boolean drawIconSet(List<ResourceLocation> ids, List<String> labelText) {
        boolean hovering = false;
        for (ResourceLocation i : ids) {
            Skill part = RegistryHandler.getSkillRegistry().getValue(i);

            if (part == null || RegistryHandler.getSpellPartRegistry().getValue(part.getRegistryName()) == null)// && SkillTreeManager.instance.isSkillDisabled(part))
                continue;

            String name = part.getName().getFormattedText();

            String filterText = searchBar.getText().toLowerCase();
            if (!filterText.equals("") && !filterText.equals(defaultSearchLabel.toLowerCase()) && !name.toLowerCase().contains(filterText)) {
                continue;
            }

            if (iconY < 0 || iconY > 42) return hovering;
            if (drawIcon(RegistryHandler.getSpellPartRegistry().getValue(part.getRegistryName()))) {
                hovering = true;
                labelText.add(name);
            }
        }
        return hovering;
    }

    private boolean spellPartIsValidAddition(AbstractSpellPart part) {
        boolean hasShape = false;
        for (int i = 0; i < this.container.getNumStageGroups(); ++i) {
            for (int n = 0; n < this.container.getShapeGroupSize(i); ++n) {
                AbstractSpellPart groupPart = this.container.getShapeGroupPartAt(i, n);
                if (groupPart instanceof SpellShape) {
                    hasShape = true;
                    break;
                }
            }
        }
        if (!hasShape && !(part instanceof SpellShape))
            return false;
        if (part instanceof SpellShape && this.container.currentRecipeContains(part))
            return false;
        if (part instanceof SpellComponent) {
            int index = this.container.getCurrentRecipeSize() - 1;
            while (index >= 0 && !(this.container.getRecipeItemAt(index) instanceof SpellShape)) {
                AbstractSpellPart curPart = this.container.getRecipeItemAt(index--);
                if (curPart instanceof SpellComponent && SpellRegistry.getSkillFromPart(curPart).getID().equals(SpellRegistry.getSkillFromPart(part).getID())) {
                    return false;
                }
            }
        }
        if (part instanceof SpellModifier) {
            return this.container.modifierCanBeAdded((SpellModifier) part);
        }
        return true;
    }

    private boolean drawIcon(AbstractSpellPart part) {
        return drawIcon(part, true);
    }

    private boolean drawIcon(AbstractSpellPart part, boolean allowDarken) {

        boolean hovering = false;
        /*TextureAtlasSprite shapeIcon = null;//SpellIconManager.INSTANCE.getSprite(SpellRegistry.getSkillFromPart(part).getID());

        if (shapeIcon == null)
            return false;*/

        if (!currentSpellDefIsReadOnly()) {
            if (!spellPartIsValidAddition(part) && allowDarken) {
                GL14.glColor3f(0.3f, 0.3f, 0.3f);
            } else {
                GL14.glColor3f(1, 1, 1);
            }
        } else {
            GL14.glColor3f(1, 0.7f, 0.7f);
        }

        //TODO
        RenderSystem.matrixMode(GL14.GL_TEXTURE);
        RenderSystem.pushMatrix();
        Minecraft.getInstance().getTextureManager().bindTexture(SpellRegistry.getSkillFromPart(part).getIcon());

        Tessellator tessellator = Tessellator.getInstance();

        boolean drawing = false;
        try {
            tessellator.getBuffer().begin(7, DefaultVertexFormats.POSITION_TEX);
        } catch (IllegalStateException e) {
            drawing = true;
            tessellator.draw();
            tessellator.getBuffer().begin(7, DefaultVertexFormats.POSITION_TEX);
        }
        float minU = 0.53125f * 64;
        float minV = 0.15625f * 32;
        float maxU = 0.546875f * 64;
        float maxV = 0.1875f * 32;

        tessellator.getBuffer().pos(iconX, iconY + 16, getBlitOffset()).tex(minU, maxV).endVertex();
        tessellator.getBuffer().pos(iconX + 16, iconY + 16, getBlitOffset()).tex(maxU, maxV).endVertex();
        tessellator.getBuffer().pos(iconX + 16, iconY, getBlitOffset()).tex(maxU, minV).endVertex();
        tessellator.getBuffer().pos(iconX, iconY, getBlitOffset()).tex(minU, minV).endVertex();

        tessellator.draw();

        RenderSystem.popMatrix();
        RenderSystem.matrixMode(GL14.GL_MODELVIEW);

        if (drawing)
            tessellator.getBuffer().begin(7, DefaultVertexFormats.POSITION_TEX);

        if (!dragging) {
            if (lastMouseX > iconX && lastMouseX < iconX + 16) {
                if (lastMouseY > iconY && lastMouseY < iconY + 16) {
                    hoveredItem = part;
                    hovering = true;
                }
            }
        }

        iconX += iconStep;
        if (iconX >= 175) {
            iconX = iconXStartUpper;
            iconY += 17;
        }

        return hovering;
    }

    private void drawDraggedItem() {
        //TODO
        RenderSystem.matrixMode(GL14.GL_TEXTURE);
        RenderSystem.pushMatrix();
        Minecraft.getInstance().getTextureManager().bindTexture(SpellRegistry.getSkillFromPart(hoveredItem).getIcon());

        RenderSystem.enableBlend();
        Tessellator tessellator = Tessellator.getInstance();

        boolean drawing = false;
        try {
            tessellator.getBuffer().begin(7, DefaultVertexFormats.POSITION_TEX);
        } catch (IllegalStateException e) {
            drawing = true;
            tessellator.draw();
            tessellator.getBuffer().begin(7, DefaultVertexFormats.POSITION_TEX);
        }
        float minU = 0.53125f * 64;
        float minV = 0.15625f * 32;
        float maxU = 0.546875f * 64;
        float maxV = 0.1875f * 32;

        tessellator.getBuffer().pos(lastMouseX - 8, lastMouseY - 8 + 16, getBlitOffset()).tex(minU, maxV).endVertex();
        tessellator.getBuffer().pos(lastMouseX - 8 + 16, lastMouseY - 8 + 16, getBlitOffset()).tex(maxU, maxV).endVertex();
        tessellator.getBuffer().pos(lastMouseX - 8 + 16, lastMouseY - 8, getBlitOffset()).tex(maxU, minV).endVertex();
        tessellator.getBuffer().pos(lastMouseX - 8, lastMouseY - 8, getBlitOffset()).tex(minU, minV).endVertex();

        tessellator.draw();
        RenderSystem.disableBlend();
        RenderSystem.popMatrix();
        RenderSystem.matrixMode(GL14.GL_MODELVIEW);

        if (drawing)
            tessellator.getBuffer().begin(7, DefaultVertexFormats.POSITION_TEX);
    }

    @Override
    public void renderTooltip(List<String> tooltip, int x, int y, FontRenderer font) {
        if (!tooltip.isEmpty()) {
            RenderSystem.pushMatrix();
            GL14.glDisable(GL12.GL_RESCALE_NORMAL);
            RenderHelper.disableStandardItemLighting();
            GL14.glDisable(GL14.GL_LIGHTING);
            GL14.glDisable(GL14.GL_DEPTH_TEST);
            int k = 0;

            for (String s : tooltip) {
                int l = font.getStringWidth(s);

                if (l > k) {
                    k = l;
                }
            }

            int i1 = x + 12;
            int j1 = y - 12;
            int k1 = 8;

            if (tooltip.size() > 1) {
                k1 += 2 + (tooltip.size() - 1) * 10;
            }

            if (i1 + k > this.width) {
                i1 -= 28 + k;
            }

            if (j1 + k1 + 6 > this.height) {
                j1 = this.height - k1 - 6;
            }

            this.setBlitOffset(300);
            int l1 = -267386864;
            this.fillGradient(i1 - 3, j1 - 4, i1 + k + 3, j1 - 3, l1, l1);
            this.fillGradient(i1 - 3, j1 + k1 + 3, i1 + k + 3, j1 + k1 + 4, l1, l1);
            this.fillGradient(i1 - 3, j1 - 3, i1 + k + 3, j1 + k1 + 3, l1, l1);
            this.fillGradient(i1 - 4, j1 - 3, i1 - 3, j1 + k1 + 3, l1, l1);
            this.fillGradient(i1 + k + 3, j1 - 3, i1 + k + 4, j1 + k1 + 3, l1, l1);
            int i2 = 1347420415;
            int j2 = (i2 & 16711422) >> 1 | i2 & -16777216;
            this.fillGradient(i1 - 3, j1 - 3 + 1, i1 - 3 + 1, j1 + k1 + 3 - 1, i2, j2);
            this.fillGradient(i1 + k + 2, j1 - 3 + 1, i1 + k + 3, j1 + k1 + 3 - 1, i2, j2);
            this.fillGradient(i1 - 3, j1 - 3, i1 + k + 3, j1 - 3 + 1, i2, i2);
            this.fillGradient(i1 - 3, j1 + k1 + 2, i1 + k + 3, j1 + k1 + 3, j2, j2);

            for (int k2 = 0; k2 < tooltip.size(); ++k2) {
                String s1 = tooltip.get(k2);
                font.drawStringWithShadow(s1, i1, j1, -1);

                if (k2 == 0) {
                    j1 += 2;
                }

                j1 += 10;
            }

            this.setBlitOffset(0);
            GL14.glEnable(GL14.GL_LIGHTING);
            GL14.glEnable(GL14.GL_DEPTH_TEST);
            RenderHelper.enableStandardItemLighting();
            GL14.glEnable(GL12.GL_RESCALE_NORMAL);
            RenderSystem.popMatrix();
        }
    }

    private boolean currentSpellDefIsReadOnly() {
        return this.container.currentSpellDefIsReadOnly();
    }
}
