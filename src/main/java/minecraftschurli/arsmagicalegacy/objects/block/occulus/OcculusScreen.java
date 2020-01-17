package minecraftschurli.arsmagicalegacy.objects.block.occulus;

import com.mojang.blaze3d.platform.*;
import minecraftschurli.arsmagicalegacy.*;
import minecraftschurli.arsmagicalegacy.api.*;
import minecraftschurli.arsmagicalegacy.api.skill.*;
import minecraftschurli.arsmagicalegacy.init.ModSpellParts;
import minecraftschurli.arsmagicalegacy.network.*;
import minecraftschurli.arsmagicalegacy.util.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.screen.*;
import net.minecraft.client.gui.widget.*;
import net.minecraft.client.gui.widget.button.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.util.text.*;

import java.util.*;
import java.util.stream.*;

/**
 * @author Minecraftschurli
 * @version 2019-12-02
 */
public class OcculusScreen extends Screen {
    int xSize = 210;
    int ySize = 210;
    SkillTree currentTree;
    PlayerEntity player;
    int currentTabId;

    private int lastMouseX = 0;
    private int lastMouseY = 0;
    private int page = 0;
    private int maxPage = 0;
    private int offsetX = ((568 / 2) - 82) + 8;
    private int offsetY = 0;
    private Skill hoverItem = null;

    private Button nextPage;
    private Button prevPage;

    public OcculusScreen(ITextComponent name, PlayerEntity player) {
        super(name);
        this.player = player;
        currentTree = ArsMagicaAPI.getSkillTreeRegistry().getValues().stream().min(Comparator.comparingInt(SkillTree::getOcculusIndex)).get();
        currentTabId = 0;
    }

    @Override
    protected void init() {
        int posX = width / 2 - xSize / 2;
        int posY = height / 2 - ySize / 2;
        for (SkillTree tree : ArsMagicaAPI.getSkillTreeRegistry()) {
            int tabId = tree.getOcculusIndex();
            if (tabId % 16 < 8) {
                addButton(new SkillTreeGuiButton(tabId, posX + 7 + ((tabId % 16) * 24), posY - 22, tree, (int) Math.floor((float) tabId / 16F), false, this::actionPerformed));
            } else {
                addButton(new SkillTreeGuiButton(tabId, posX + 7 + (((tabId % 16) - 8) * 24), posY + 210, tree, (int) Math.floor((float) tabId / 16F), true, this::actionPerformed));
            }
        }
        maxPage = (int) Math.floor((float) (ArsMagicaAPI.getSkillTreeRegistry().getValues().size() - 1) / 16F);
        nextPage = new Button(posX + 212, posY - 21, 20, 20, ">", this::actionPerformed);
        prevPage = new Button(posX - 15, posY - 21, 20, 20, "<", this::actionPerformed);
        nextPage.active = page < maxPage;
        prevPage.active = page > 0;
        for (Widget button : buttons) {
            if (button instanceof SkillTreeGuiButton) {
                button.visible = (int) Math.floor((float) ((SkillTreeGuiButton) button).id / 16F) == page;
            }
        }
        addButton(nextPage);
        addButton(prevPage);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
        if (mouseButton == 0) {
            if (hoverItem != null && !MagicHelper.knows(player, hoverItem)) {
                if (MagicHelper.canLearn(player, hoverItem)) {
                    NetworkHandler.INSTANCE.sendToServer(new LearnSkillPacket(hoverItem.getID()));
                }
            } else setDragging(true);
        }
        return super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    private void actionPerformed(Button button) {
        if (button instanceof SkillTreeGuiButton) {
            currentTree = ((SkillTreeGuiButton) button).getTree();
            currentTabId = ((SkillTreeGuiButton) button).id;
            offsetX = 568 / 2 - 82 + 8;
            offsetY = 0;
        }
    }

    private int calcXOffset(int posX, Skill s) {
        return posX - this.offsetX + s.getPosX();
    }

    private int calcYOffset(int posY, Skill s) {
        return posY - this.offsetY + s.getPosY();
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        int posX = width / 2 - xSize / 2;
        int posY = height / 2 - ySize / 2;
        float renderSize = 32F;
        float renderRatio = 0.29F;
        this.blitOffset = -5;
        Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation(ArsMagicaLegacy.MODID, "textures/gui/occulus/overlay.png"));
        //Overlay
        blit(posX, posY, 0, 0, 210, 210);
        //Tab Under
        if ((int) Math.floor((float) currentTabId / 16F) == page) {
            if ((currentTabId % 16) < 8)
                blit(posX + 7 + ((currentTabId % 16) * 24), posY, 22, 210, 22, 7);
            else
                blit(posX + 7 + (((currentTabId % 16) - 8) * 24), posY + 203, 22, 210, 22, 7);
        }
        this.blitOffset = -18;
        if (this.isDragging()) {
            int dx = lastMouseX - mouseX;
            int dy = lastMouseY - mouseY;

            this.offsetX += dx;
            this.offsetY += dy;

            if (this.offsetX < 0) this.offsetX = 0;
            if (this.offsetX > 568) this.offsetX = 568;

            if (this.offsetY < 0) this.offsetY = 0;
            if (this.offsetY > 568) this.offsetY = 568;
        }
        lastMouseX = mouseX;
        lastMouseY = mouseY;
        float calcYOffest = ((float) offsetY / 568) * (1 - renderRatio);
        float calcXOffest = ((float) offsetX / 568) * (1 - renderRatio);
        int maxSize = 0;
        for (SkillPoint point : SkillPointRegistry.SKILL_POINT_REGISTRY.values().stream().filter(SkillPoint::canRender).collect(Collectors.toList())) {
            maxSize = Math.max(maxSize, font.getStringWidth(point.getDisplayName().getFormattedText() + " : " + MagicHelper.getSkillPoint(player, point)));
        }
        blitOffset = -1;
        Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation(ArsMagicaLegacy.MODID, "textures/gui/occulus/skill_points.png"));
        drawSkillPointBackground(posX, posY, maxSize + 10, 210);
        int pointOffsetX = 5;
        for (SkillPoint point : SkillPointRegistry.SKILL_POINT_REGISTRY.values().stream().filter(SkillPoint::canRender).sorted(Comparator.comparingInt(SkillPoint::getTier)).collect(Collectors.toList())) {
            font.drawString(point.getDisplayName().getFormattedText() + " : " + MagicHelper.getSkillPoint(player, point), posX + 215, posY + pointOffsetX, point.getColor());
            pointOffsetX += 10;
        }
        GlStateManager.color3f(1f, 1f, 1f);
        Minecraft.getInstance().getTextureManager().bindTexture(currentTree.getBackground());
        if (currentTree != ModSpellParts.AFFINITY.get()) {
            RenderUtils.drawBox(posX + 7, posY + 7, 196, 196, blitOffset, calcXOffest, calcYOffest, renderRatio + calcXOffest, renderRatio + calcYOffest);
            List<Skill> skills = SkillRegistry.getSkillsForTree(currentTree);
            blitOffset = 1;
            for (Skill s : skills) {
                if (s.getPoint() == null || (!s.getPoint().canRender() && !MagicHelper.knows(player, s)))
                    continue;
                for (String p : s.getParents()) {
                    if (p == null)
                        continue;
                    Skill parent = ArsMagicaAPI.getSkillRegistry().getValue(new ResourceLocation(p));
                    if (parent == null || !skills.contains(parent)) continue;
                    if (!parent.getPoint().canRender() && !MagicHelper.knows(player, parent))
                        continue;
                    int offsetX = calcXOffset(posX, s) + 16;
                    int offsetY = calcYOffset(posY, s) + 16;
                    int offsetX2 = calcXOffset(posX, parent) + 16;
                    int offsetY2 = calcYOffset(posY, parent) + 16;
                    offsetX = MathHelper.clamp(offsetX, posX + 7, posX + 203);
                    offsetY = MathHelper.clamp(offsetY, posY + 7, posY + 203);
                    offsetX2 = MathHelper.clamp(offsetX2, posX + 7, posX + 203);
                    offsetY2 = MathHelper.clamp(offsetY2, posY + 7, posY + 203);
                    boolean hasPrereq = MagicHelper.canLearn(player, s) || MagicHelper.knows(player, s);
                    int color = (!MagicHelper.knows(player, s) ? s.getPoint().getColor() & 0x999999 : 0x00ff00);
                    if (!hasPrereq) color = 0x000000;
                    if (!(offsetX == posX + 7 || offsetX == posX + 203))
                        RenderUtils.lineThick2d(offsetX, offsetY, offsetX, offsetY2, hasPrereq ? 0 : -1, color);
                    if (!(offsetY2 == posY + 7 || offsetY2 == posY + 203))
                        RenderUtils.lineThick2d(offsetX, offsetY2, offsetX2, offsetY2, hasPrereq ? 0 : -1, color);
                }
            }
            //Minecraft.getInstance().getTextureManager().bindTexture();
            for (Skill s : skills) {
                if (s.getPoint() == null || (!s.getPoint().canRender() && !MagicHelper.knows(player, s)))
                    continue;
                GlStateManager.color4f(1, 1, 1, 1.0F);
                boolean hasPrereq = MagicHelper.canLearn(player, s) || MagicHelper.knows(player, s);
                int offsetX = calcXOffset(posX, s);
                int offsetY = calcYOffset(posY, s);
                int tick = (player.ticksExisted % 80) >= 40 ? (player.ticksExisted % 40) - 20 : -(player.ticksExisted % 40) + 20;
                float multiplier = 0.75F + tick / 80F;
                //TextureAtlasSprite sprite = Minecraft.getInstance().getTextureMap().getSprite(s.getIcon());
                if (offsetX + renderSize < posX + 7 || offsetX > posX + 203 || offsetY + renderSize < posY + 7 || offsetY > posY + 203 /*|| sprite == null*/) {
                    continue;
                }
                Minecraft.getInstance().getTextureManager().bindTexture(s.getIcon());
                float minU = 0.53125f * 64;
                float minV = 0.15625f * 32;
                float maxU = 0.546875f * 64;
                float maxV = 0.1875f * 32;
                float spriteXSize = maxU - minU;
                float spriteYSize = maxV - minV;
                float xStartMod = 0;
                float yStartMod = 0;
                float xEndMod = 0;
                float yEndMod = 0;
                if (offsetX < posX + 7) {
                    xStartMod = (float) (posX + 7 - offsetX);
                } else if (offsetX + renderSize > posX + 203) {
                    xEndMod = renderSize - (posX + 203 - offsetX);
                }
                if (offsetY < posY + 7) {
                    yStartMod = (float) (posY + 7 - offsetY);
                } else if (offsetY + renderSize > posY + 203) {
                    yEndMod = renderSize - (posY + 203 - offsetY);
                }
                if (!hasPrereq)
                    GlStateManager.color3f(0.5F, 0.5F, 0.5F);
                else if (!MagicHelper.knows(player, s))
                    GlStateManager.color3f(Math.max(RenderUtils.getRed(s.getPoint().getColor()), 0.6F) * multiplier, Math.max(RenderUtils.getGreen(s.getPoint().getColor()), 0.6F) * multiplier, Math.max(RenderUtils.getBlue(s.getPoint().getColor()), 0.6F) * multiplier);

                /*if (ArsMagicaLegacy.disabledSkills.isSkillDisabled(s.getID()))
                    GlStateManager.color3f(0.3f, 0.3f, 0.3f);*/
                //blit((int) (offsetX + xStartMod), (int) (offsetY + yStartMod), 0, 0, 0, (int) renderSize, (int) renderSize, (int) renderSize, (int) renderSize);
                RenderUtils.drawBox(offsetX + xStartMod,
                        offsetY + yStartMod,
                        renderSize - xStartMod - xEndMod,
                        renderSize - yStartMod - yEndMod,
                        0,
                        minU + (xStartMod / renderSize * spriteXSize),
                        minV + (yStartMod / renderSize * spriteYSize),
                        maxU - (xEndMod / renderSize * spriteXSize),
                        maxV - (yEndMod / renderSize * spriteYSize));
                GlStateManager.color4f(1, 1, 1, 1.0F);
                /*if (ArsMagicaLegacy.disabledSkills.isSkillDisabled(s.getID())){
                    sprite = AMGuiIcons.padlock;
                    spriteXSize = maxU - minU;
                    spriteYSize = maxV - minV;
                    xStartMod = 0;
                    yStartMod = 0;
                    xEndMod = 0;
                    yEndMod = 0;
                    if (offsetX + 8 < posX + 7) {
                        float mod = (posX + 7 - offsetX - 8);
                        xStartMod = mod;
                    }
                    else if (offsetX + 24 > posX + 203) {
                        float mod = 16 - (posX + 203 - offsetX - 8);
                        xEndMod = mod;
                    }
                    if (offsetY + 8 < posY + 7) {
                        float mod = (posY + 7 - offsetY - 8);
                        yStartMod = mod;
                    }
                    else if (offsetY + 24 > posY + 203) {
                        float mod = 16 - (posY + 203 - offsetY - 8);
                        yEndMod = mod;
                    }

                    RenderUtils.drawBox(offsetX + xStartMod + 8,
                            offsetY + yStartMod + 8,
                            16 - xStartMod - xEndMod,
                            16 - yStartMod - yEndMod,
                            0,
                            minU + (xStartMod / 16 * spriteXSize),
                            minV + (yStartMod / 16 * spriteYSize),
                            maxU - (xEndMod / 16 * spriteXSize),
                            maxV - (yEndMod / 16 * spriteYSize));
                    GlStateManager.color4f(1, 1, 1, 1.0F);
                }*/

            }

            //Get the skill

            if (mouseX > posX && mouseX < posX + 210 && mouseY > posY && mouseY < posY + 210) {
                boolean flag = false;
                blitOffset = 0;
                for (Skill s : skills) {
                    if (!s.getPoint().canRender() && !MagicHelper.knows(player, s))
                        continue;
                    int offsetX = calcXOffset(posX, s);
                    int offsetY = calcYOffset(posY, s);
                    if (offsetX > mouseX || offsetX < mouseX - renderSize || offsetY > mouseY || offsetY < mouseY - renderSize)
                        continue;
                    boolean hasPrereq = true;
                    for (String subParent : s.getParents()) {
                        hasPrereq &= MagicHelper.knows(player, new ResourceLocation(subParent));
                    }
                    List<ITextComponent> list = new ArrayList<>();
                    list.add(s.getName().applyTextStyle(s.getPoint().getChatColor()));
                    /*if (ArsMagica2.disabledSkills.isSkillDisabled(s.getID()))
                        list.add(new TranslationTextComponent(".gui.occulus.disabled").setStyle(new Style().setColor(TextFormatting.DARK_RED)));
                    else*/
                    if (hasPrereq)
                        list.add(s.getOcculusDesc().applyTextStyle(TextFormatting.DARK_GRAY));
                    else
                        list.add(new TranslationTextComponent(ArsMagicaLegacy.MODID + ".gui.occulus.missingrequirements").applyTextStyle(TextFormatting.DARK_RED));

                    renderTooltip(list.stream().map(ITextComponent::getFormattedText).collect(Collectors.toList()), mouseX, mouseY, font);
                    flag = true;
                    hoverItem = s;
                    RenderHelper.disableStandardItemLighting();
                    GlStateManager.color3f(1.0F, 1.0F, 1.0F);
                }
                if (!flag) {
                    hoverItem = null;
                }
            }

        } else {
            //boolean isShiftDown = false;
            RenderUtils.drawBox(posX + 7, posY + 7, 196, 196, blitOffset, 0, 0, 1, 1);
            /*int affNum = ArsMagicaAPI.getAffinityRegistry().getValues().size() - 1;
            int portion = 360 / affNum;
            int currentID = 0;
            int cX = posX + xSize/2;
            int cY = posY + ySize/2;
            //float finalPercentage = AffinityData.For(player).getAffinityDepth(SkillDefs.NONE) * 100;
            ArrayList<String> drawString = new ArrayList<>();
            for (Affinity aff : ArsMagicaAPI.getAffinityRegistry().getValues()) {
                if (aff == Affinity.NONE)
                    continue;
                double depth = AffinityData.For(player).getAffinityDepth(aff);
                double affEndX = Math.cos(Math.toRadians(portion*currentID)) * 10F + Math.cos(Math.toRadians(portion*currentID)) * depth * 60F;
                double affEndY = Math.sin(Math.toRadians(portion*currentID)) * 10F + (Math.sin(Math.toRadians(portion*currentID))) * depth * 60F;
                double affStartX1 = Math.cos(Math.toRadians(portion*currentID - portion/2)) * 10F;
                double affStartY1 = Math.sin(Math.toRadians(portion*currentID - portion/2)) * 10F;
                double affStartX2 = Math.cos(Math.toRadians(portion*currentID + portion/2)) * 10F;
                double affStartY2 = Math.sin(Math.toRadians(portion*currentID + portion/2)) * 10F;
                double affDrawTextX =  Math.cos(Math.toRadians(portion*currentID)) * 80F - 7;
                double affDrawTextY =  Math.sin(Math.toRadians(portion*currentID)) * 80F - 7;
                currentID++;

                int displace = (int)((Math.max(affStartX1, affStartX2) - Math.min(affStartX1, affStartX2) + Math.max(affStartY1, affStartY2) - Math.min(affStartY1, affStartY2)) / 2);
                if (depth > 0.01F) {
                    RenderUtils.fractalLine2dd(affStartX1 + cX, affStartY1 + cY, affEndX + cX, affEndY + cY, zLevel, aff.getColor(), displace, 0.8F);
                    RenderUtils.fractalLine2dd(affStartX2 + cX, affStartY2 + cY, affEndX + cX, affEndY + cY, zLevel, aff.getColor(), displace, 0.8F);

                    RenderUtils.fractalLine2dd(affStartX1 + cX, affStartY1 + cY, affEndX + cX, affEndY + cY, zLevel, aff.getColor(), displace, 1.1F);
                    RenderUtils.fractalLine2dd(affStartX2 + cX, affStartY2 + cY, affEndX + cX, affEndY + cY, zLevel, aff.getColor(), displace, 1.1F);
                } else {
                    RenderUtils.line2d((float)affStartX1 + cX, (float)affStartY1 + cY, (float)affEndX + cX, (float)affEndY + cY, zLevel, aff.getColor());
                    RenderUtils.line2d((float)affStartX2 + cX, (float)affStartY2 + cY, (float)affEndX + cX, (float)affEndY + cY, zLevel, aff.getColor());
                }

                Minecraft.getInstance().fontRendererObj.drawString("" + (float)Math.round(depth * 10000) / 100F, (int)((affDrawTextX *0.9) + cX), (int)((affDrawTextY*0.9) + cY), aff.getColor());
                //Minecraft.getInstance().fontRendererObj.drawString("" + (float)Math.round(depth * 10000) / 100F, , aff.getColor());
                int xMovement = affDrawTextX > 0 ? 5 : -5;
                xMovement = affDrawTextX == 0 ? 0 : xMovement;
                int yMovement = affDrawTextY > 0 ? 5 : -5;
                yMovement = affDrawTextY == 0 ? 0 : yMovement;
                int drawX = (int)((affDrawTextX * 1.1) + cX + xMovement);
                int drawY = (int)((affDrawTextY * 1.1) + cY + yMovement);
                this.itemRender.renderItemAndEffectIntoGUI(new ItemStack(ItemDefs.essence, 1, ArsMagicaAPI.getAffinityRegistry().getId(aff)) , drawX, drawY);
                if (mouseX > drawX && mouseX < drawX + 16 && mouseY > drawY && mouseY < drawY + 16) {
                    drawString.add(TextFormatting.RESET.toString() + aff.getLocalizedName());
                    ArrayList<AbstractAffinityAbility> abilites = Lists.newArrayList(ArsMagicaAPI.getAffinityAbilityRegistry().getValues());
                    abilites.sort(new Comparator<AbstractAffinityAbility>() {

                        @Override
                        public int compare(AbstractAffinityAbility o1, AbstractAffinityAbility o2) {
                            return (int) ((o1.getMinimumDepth() * 100) - (o2.getMinimumDepth() * 100));
                        }
                    });


                    for (AbstractAffinityAbility ability : abilites) {
                        if (ability.getAffinity() == aff) {
                            String advancedTooltip = "";
                            if (isShiftDown) {
                                advancedTooltip = " (Min. : " + Math.round(ability.getMinimumDepth() * 100) + "%" + (ability.hasMax() ?(", Max. : " + Math.round(ability.getMaximumDepth() * 100) + "%")  : "") + ")";
                            }
                            drawString.add(TextFormatting.RESET.toString()
                                    + (ability.isEligible(player) ? TextFormatting.GREEN.toString()
                                    : TextFormatting.DARK_RED.toString())
                                    + I18n.translateToLocal("affinityability."
                                    + ability.getRegistryName().toString().replaceAll("arsmagica2:", "")
                                    + ".name") + advancedTooltip);
                        }
                    }
                }
            }
            if (!drawString.isEmpty()) {
                if (!isShiftDown)
                    drawString.add(TextFormatting.GRAY.toString() + I18n.translateToLocal("am2.tooltip.shiftForDetails"));
                drawHoveringText(drawString, mouseX, mouseY);
            }
            GlStateManager.color(1, 1, 1);
            RenderHelper.disableStandardItemLighting();*/
        }

        GlStateManager.color3f(1, 1, 1);

        super.render(mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    private void drawSkillPointBackground(int startX, int startY, int width, int height) {
        int posX = 210;
        int posY = 0;
        //blit(startX + posX, startY + posY, 0, 0, 4, 4);
        blit(startX + posX + width - 4, startY + posY, 252, 0, 4, 4);
        //blit(startX + posX, startY + posY + height - 4, 0, 252, 4, 4);
        blit(startX + posX + width - 4, startY + posY + height - 4, 252, 252, 4, 4);
        int w = width - 4;
        int h = height - 8;
        while (w > 0) {
            int x = Math.min(w, 252);
            while (h > 0) {
                int y = Math.min(h, 248);
                blit(startX + posX + w - x, startY + posY + 4 + h - y, 4, 4, x, y);
                h -= y;
            }
            w -= x;
        }
        w = width - 4;
        h = height - 8;
        while (w > 0) {
            int x = Math.min(w, 252);
            blit(startX + posX + w - x, startY + posY, 4, 0, x, 4);
            blit(startX + posX + w - x, startY + posY + height - 4, 4, 252, x, 4);
            w -= x;
        }
        while (h > 0) {
            int y = Math.min(h, 248);
            //blit(startX + posX, startY + posY + 4 + h - y, 0, 4, 4, y);
            blit(startX + posX + width - 4, startY + posY + 4 + h - y, 252, 4, 4, y);
            h -= y;
        }
    }
}
