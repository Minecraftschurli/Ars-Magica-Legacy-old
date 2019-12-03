package minecraftschurli.arsmagicalegacy.objects.block.occulus;

import com.mojang.blaze3d.platform.GlStateManager;
import minecraftschurli.arsmagicalegacy.ArsMagicaLegacy;
import minecraftschurli.arsmagicalegacy.api.spell.skill.Skill;
import minecraftschurli.arsmagicalegacy.api.spell.skill.SkillPoint;
import minecraftschurli.arsmagicalegacy.api.spell.skill.SkillTree;
import minecraftschurli.arsmagicalegacy.capabilities.research.IResearchStorage;
import minecraftschurli.arsmagicalegacy.init.SpellParts;
import minecraftschurli.arsmagicalegacy.network.LearnSkillPacket;
import minecraftschurli.arsmagicalegacy.network.NetworkHandler;
import minecraftschurli.arsmagicalegacy.util.MagicHelper;
import minecraftschurli.arsmagicalegacy.util.RenderUtils;
import minecraftschurli.arsmagicalegacy.util.SpellIconManager;
import minecraftschurli.arsmagicalegacy.util.SpellRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.IHasContainer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Minecraftschurli
 * @version 2019-12-02
 */
public class OcculusScreen extends Screen implements IHasContainer<OcculusContainer> {
    int xSize = 210;
    int ySize = 210;
    SkillTree currentTree = SpellParts.OFFENSE.get();
    PlayerEntity player;
    int currentTabId = 0;

    private int lastMouseX = 0;
    private int lastMouseY = 0;
    private int page = 0;
    private int maxPage = 0;
    private int offsetX = ((568 / 2) - 82) + 8;
    private int offsetY = 0;
    private Skill hoverItem = null;

    private Button nextPage;
    private Button prevPage;
    private OcculusContainer container;

    public OcculusScreen(ITextComponent name, OcculusContainer container, PlayerEntity player) {
        super(name);
        this.container = container;
        this.player = player;
    }

    @Override
    protected void init() {
        int tabId = 0;
        int posX = width/2 - xSize/2;
        int posY = height/2 - ySize/2;
        for (SkillTree tree : SpellRegistry.SKILL_TREE_REGISTRY.getValues()){
            if (tabId % 16 < 8) {
                addButton(new GuiButtonSkillTree(tabId,posX + 7 + ((tabId % 16) * 24), posY - 22, tree, (int)Math.floor((float)tabId / 16F), false, this::actionPerformed));
            } else {
                addButton(new GuiButtonSkillTree(tabId, posX + 7 + (((tabId % 16) - 8) * 24), posY + 210, tree, (int)Math.floor((float)tabId / 16F), true, this::actionPerformed));
            }
            tabId++;
        }
        maxPage = (int)Math.floor((float)(tabId - 1) / 16F);
        nextPage = new Button(posX + 212, posY - 21, 20, 20, ">", this::actionPerformed);
        prevPage = new Button(posX - 15, posY - 21, 20, 20, "<", this::actionPerformed);
        nextPage.active = page < maxPage;
        prevPage.active = page > 0;
        for (Widget button : buttons) {
            if (button instanceof GuiButtonSkillTree) {
                button.visible = (int)Math.floor((float) ((GuiButtonSkillTree) button).id / 16F) == page;
            }
        }
        addButton(nextPage);
        addButton(prevPage);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
        if (mouseButton == 0) {
            if (hoverItem != null && !MagicHelper.getResearchCapability(player).knows(hoverItem)) {
                IResearchStorage data = MagicHelper.getResearchCapability(player);
                if (data.canLearn(hoverItem)) {
                    NetworkHandler.INSTANCE.sendToServer(new LearnSkillPacket(hoverItem.getID()));
                }
            }
        }
        return super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    private void actionPerformed(Button button) {
        if (button instanceof GuiButtonSkillTree) {
            currentTree = ((GuiButtonSkillTree)button).getTree();
            currentTabId = ((GuiButtonSkillTree) button).id;
            offsetX = 568 / 2 - 82 + 8;
            offsetY = 0;
        }
    }

    @Override
    public OcculusContainer getContainer() {
        return this.container;
    }

    private int calcXOffset(int posX, Skill s) {
        return posX - this.offsetX + s.getPosX();
    }

    private int calcYOffset(int posY, Skill s) {
        return posY - this.offsetY + s.getPosY();
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        int posX = width/2 - xSize/2;
        int posY = height/2 - ySize/2;
        float renderSize = 32F;
        float renderRatio = 0.29F;
        Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation(ArsMagicaLegacy.MODID, "textures/gui/occulus/overlay.png"));
        //Overlay
        blit(posX, posY, 0, 0, 210, 210);
        //Tab Under
        if ((int)Math.floor((float)currentTabId / 16F) == page) {
            if ((currentTabId % 16) < 8)
                blit(posX + 7 + ((currentTabId % 16) * 24), posY, 22, 210, 22, 7);
            else
                blit(posX + 7 + (((currentTabId % 16) - 8) * 24), posY + 203, 22, 210, 22, 7);
        }
        this.blitOffset = -18;
        if (this.isDragging()){
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
        float calcYOffest = ((float)offsetY / 568) * (1 - renderRatio);
        float calcXOffest = ((float)offsetX / 568) * (1 - renderRatio);
        {
            int maxSize = 0;
            for (SkillPoint point : SpellRegistry.SKILL_POINT_REGISTRY.getValues()) {
                if (!point.canRender()) continue;
                maxSize = Math.max(maxSize, font.getStringWidth(point.getName() + " : " + MagicHelper.getSkillPoint(player, point)));
            }
            blitOffset = 0;
            Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation(ArsMagicaLegacy.MODID, "textures/gui/occulus/skill_points.png"));
            drawSkillPointBackground(posX, posY, maxSize + 10, 210);
            int pointOffsetX = 5;
            for (SkillPoint point : SpellRegistry.SKILL_POINT_REGISTRY) {
                if (!point.canRender()) continue;
                font.drawString(point.getDisplayName() + " : " + MagicHelper.getResearchCapability(player).get(point.getName()), posX + 215, posY + pointOffsetX, point.getColor());
                pointOffsetX+=10;
            }
            GlStateManager.color3f(1f, 1f, 1f);
        }
        Minecraft.getInstance().getTextureManager().bindTexture(currentTree.getBackground());
        if (currentTree != SpellParts.AFFINITY.get()) {
            RenderUtils.drawBox(posX + 7, posY + 7, 196, 196, blitOffset, calcXOffest, calcYOffest, renderRatio + calcXOffest, renderRatio + calcYOffest);
            List<Skill> skills = SpellRegistry.getSkillsForTree(currentTree);
            blitOffset = 1;
            IResearchStorage data = MagicHelper.getResearchCapability(player);
            for (Skill s : skills) {
                if (!s.getPoint().canRender() && !data.knows(s))
                    continue;
                for (String p : s.getParents()) {
                    if (p == null)
                        continue;
                    Skill parent = SpellRegistry.SKILL_REGISTRY.getValue(new ResourceLocation(p));
                    if (parent == null || !skills.contains(parent)) continue;
                    if (!parent.getPoint().canRender() && !data.knows(parent))
                        continue;
                    int offsetX = calcXOffset(posX, s) + 16;
                    int offsetY = calcYOffset(posY, s) + 16;
                    int offsetX2 = calcXOffset(posX, parent) + 16;
                    int offsetY2 = calcYOffset(posY, parent) + 16;
                    offsetX = MathHelper.clamp(offsetX, posX + 7, posX + 203);
                    offsetY = MathHelper.clamp(offsetY, posY + 7, posY + 203);
                    offsetX2 = MathHelper.clamp(offsetX2, posX + 7, posX + 203);
                    offsetY2 = MathHelper.clamp(offsetY2, posY + 7, posY + 203);
                    boolean hasPrereq = data.canLearn(s) || data.knows(s);
                    int color = (!data.knows(s) ? s.getPoint().getColor() & 0x999999 : 0x00ff00);
                    if (!hasPrereq) color = 0x000000;
                    if (!(offsetX == posX + 7 || offsetX == posX + 203))
                        RenderUtils.lineThick2d(offsetX, offsetY, offsetX, offsetY2, hasPrereq ? 0 : -1, color);
                    if (!(offsetY2 == posY + 7 || offsetY2 == posY + 203))
                        RenderUtils.lineThick2d(offsetX, offsetY2, offsetX2, offsetY2, hasPrereq ? 0 : -1, color);
                }
            }
            //Minecraft.getInstance().getTextureManager().bindTexture();
            for (Skill s : skills) {
                // FIXME @minecraftschurli @ichhabehunger54
                if (!s.getPoint().canRender() && !data.knows(s))
                    continue;
                GlStateManager.color4f(1, 1, 1, 1.0F);
                IResearchStorage skillData = MagicHelper.getResearchCapability(player);
                boolean hasPrereq = skillData.canLearn(s) || data.knows(s);
                int offsetX = calcXOffset(posX, s);
                int offsetY = calcYOffset(posY, s);
                int tick = (player.ticksExisted % 80) >= 40 ? (player.ticksExisted % 40) - 20 : -(player.ticksExisted % 40) + 20;
                float multiplier = 0.75F + tick / 80F;
                TextureAtlasSprite sprite = SpellIconManager.getSprite(s.getID());
                if (offsetX + renderSize < posX + 7 || offsetX > posX + 203 || offsetY + renderSize < posY + 7 || offsetY > posY + 203 || sprite == null) {
                    continue;
                }
                Minecraft.getInstance().getTextureManager().bindTexture(sprite.getName());
                float spriteXSize = sprite.getMaxU() - sprite.getMinU();
                float spriteYSize = sprite.getMaxV() - sprite.getMinV();
                float xStartMod = 0;
                float yStartMod = 0;
                float xEndMod = 0;
                float yEndMod = 0;
                if (offsetX < posX + 7) {
                    float mod = (posX + 7 - offsetX);
                    xStartMod = mod;
                }
                else if (offsetX + renderSize > posX + 203) {
                    float mod = renderSize - (posX + 203 - offsetX);
                    xEndMod = mod;
                }
                if (offsetY < posY + 7) {
                    float mod = (posY + 7 - offsetY);
                    yStartMod = mod;
                }
                else if (offsetY + renderSize > posY + 203) {
                    float mod = renderSize - (posY + 203 - offsetY);
                    yEndMod = mod;
                }
                if (!hasPrereq)
                    GlStateManager.color3f(0.1F, 0.1F, 0.1F);
                else if (!skillData.knows(s))
                    GlStateManager.color3f(Math.max(RenderUtils.getRed(s.getPoint().getColor()), 0.6F) * multiplier, Math.max(RenderUtils.getGreen(s.getPoint().getColor()), 0.6F) * multiplier, Math.max(RenderUtils.getBlue(s.getPoint().getColor()), 0.6F) * multiplier);

                /*if (ArsMagicaLegacy.disabledSkills.isSkillDisabled(s.getID()))
                    GlStateManager.color3f(0.3f, 0.3f, 0.3f);*/
                RenderUtils.drawBox(offsetX + xStartMod,
                        offsetY + yStartMod,
                        renderSize - xStartMod - xEndMod,
                        renderSize - yStartMod - yEndMod,
                        0,
                        sprite.getMinU() + (xStartMod / renderSize * spriteXSize),
                        sprite.getMinV() + (yStartMod / renderSize * spriteYSize),
                        sprite.getMaxU() - (xEndMod / renderSize * spriteXSize),
                        sprite.getMaxV() - (yEndMod / renderSize * spriteYSize));
                GlStateManager.color4f(1, 1, 1, 1.0F);
                /*if (ArsMagicaLegacy.disabledSkills.isSkillDisabled(s.getID())){
                    sprite = AMGuiIcons.padlock;
                    spriteXSize = sprite.getMaxU() - sprite.getMinU();
                    spriteYSize = sprite.getMaxV() - sprite.getMinV();
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
                            sprite.getMinU() + (xStartMod / 16 * spriteXSize),
                            sprite.getMinV() + (yStartMod / 16 * spriteYSize),
                            sprite.getMaxU() - (xEndMod / 16 * spriteXSize),
                            sprite.getMaxV() - (yEndMod / 16 * spriteYSize));
                    GlStateManager.color4f(1, 1, 1, 1.0F);
                }*/

            }

            //Get the skill

            if (mouseX > posX && mouseX < posX + 210 && mouseY > posY && mouseY < posY + 210) {
                boolean flag = false;
                blitOffset = 0;
                for (Skill s : skills) {
                    if (!s.getPoint().canRender() && !data.knows(s))
                        continue;
                    int offsetX = calcXOffset(posX, s);
                    int offsetY = calcYOffset(posY, s);
                    if (offsetX > mouseX || offsetX < mouseX - renderSize|| offsetY > mouseY || offsetY < mouseY - renderSize)
                        continue;
                    boolean hasPrereq = true;
                    for (String subParent : s.getParents()) {
                        hasPrereq &= data.knows(SpellRegistry.SKILL_REGISTRY.getValue(new ResourceLocation(subParent)));
                    }
                    List<ITextComponent> list = new ArrayList<>();
                    list.add(new StringTextComponent(s.getPoint().getChatColor().toString() + s.getName()));
                    /*if (ArsMagica2.disabledSkills.isSkillDisabled(s.getID()))
                        list.add(new TranslationTextComponent(".gui.occulus.disabled").setStyle(new Style().setColor(TextFormatting.DARK_RED)));
                    else*/ if (hasPrereq)
                        list.add(s.getOcculusDesc().setStyle(new Style().setColor(TextFormatting.DARK_GRAY)));
                    else
                        list.add(new TranslationTextComponent(ArsMagicaLegacy.MODID+".gui.occulus.missingrequirements").setStyle(new Style().setColor(TextFormatting.DARK_RED)));

                    flag = true;
                    hoverItem = s;
                    RenderHelper.disableStandardItemLighting();
                    GlStateManager.color3f(1.0F, 1.0F, 1.0F);
                }
                if (!flag)
                    hoverItem = null;
            }

        } /*else {
            boolean isShiftDown = false;
            RenderUtils.drawBox(posX + 7, posY + 7, 196, 196, zLevel, 0, 0, 1, 1);
            int affNum = ArsMagicaAPI.getAffinityRegistry().getValues().size() - 1;
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
            RenderHelper.disableStandardItemLighting();
        }*/

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
            int x = 0;
            if (w > 252)
                x = 252;
            else
                x = w;
            while (h > 0) {
                int y = 0;
                if (h > 248)
                    y = 248;
                else
                    y = h;
                blit(startX + posX + w - x, startY + posY + 4 + h - y, 4, 4, x, y);
                h -= y;
            }
            w -= x;
        }
        w = width - 4;
        h = height - 8;
        while (w > 0) {
            int x = 0;
            if (w > 252)
                x = 252;
            else
                x = w;
            blit(startX + posX + w - x, startY + posY, 4, 0, x, 4);
            blit(startX + posX + w - x, startY + posY + height - 4, 4, 252, x, 4);
            w -= x;
        }
        while (h > 0) {
            int y = 0;
            if (h > 248)
                y = 248;
            else
                y = h;
            //blit(startX + posX, startY + posY + 4 + h - y, 0, 4, 4, y);
            blit(startX + posX + width - 4, startY + posY + 4 + h - y, 252, 4, 4, y);
            h -= y;
        }
    }
}
