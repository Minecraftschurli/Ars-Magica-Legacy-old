package minecraftschurli.arsmagicalegacy.objects.block.occulus;

import com.mojang.blaze3d.systems.RenderSystem;
import minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import minecraftschurli.arsmagicalegacy.api.affinity.Affinity;
import minecraftschurli.arsmagicalegacy.api.capability.CapabilityHelper;
import minecraftschurli.arsmagicalegacy.api.network.LearnSkillPacket;
import minecraftschurli.arsmagicalegacy.api.network.NetworkHandler;
import minecraftschurli.arsmagicalegacy.api.registry.RegistryHandler;
import minecraftschurli.arsmagicalegacy.api.registry.SkillPointRegistry;
import minecraftschurli.arsmagicalegacy.api.registry.SkillRegistry;
import minecraftschurli.arsmagicalegacy.api.skill.Skill;
import minecraftschurli.arsmagicalegacy.api.skill.SkillPoint;
import minecraftschurli.arsmagicalegacy.api.skill.SkillTree;
import minecraftschurli.arsmagicalegacy.init.ModSkillTrees;
import minecraftschurli.arsmagicalegacy.util.ColorUtil;
import minecraftschurli.arsmagicalegacy.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Minecraftschurli
 * @version 2019-12-02
 */
public class OcculusScreen extends Screen {
    private final int page = 0;
    int xSize = 210;
    int ySize = 210;
    SkillTree currentTree;
    PlayerEntity player;
    int currentTabId;
    private int lastMouseX = 0;
    private int lastMouseY = 0;
    private int offsetX = ((568 / 2) - 82) + 8;
    private int offsetY = 0;
    private Skill hoverItem = null;

    public OcculusScreen(ITextComponent name, PlayerEntity player) {
        super(name);
        this.player = player;
        currentTree = RegistryHandler.getSkillTreeRegistry().getValues().stream().min(Comparator.comparingInt(SkillTree::getOcculusIndex)).get();
        currentTabId = 0;
    }

    @Override
    protected void init() {
        int posX = width / 2 - xSize / 2;
        int posY = height / 2 - ySize / 2;
        for (SkillTree tree : RegistryHandler.getSkillTreeRegistry()) {
            int tabId = tree.getOcculusIndex();
            if (tabId % 16 < 8)
                addButton(new SkillTreeGuiButton(tabId, posX + 7 + ((tabId % 16) * 24), posY - 22, tree, (int) Math.floor((float) tabId / 16F), false, this::actionPerformed));
            else
                addButton(new SkillTreeGuiButton(tabId, posX + 7 + (((tabId % 16) - 8) * 24), posY + 210, tree, (int) Math.floor((float) tabId / 16F), true, this::actionPerformed));
        }
        int maxPage = (int) Math.floor((float) (RegistryHandler.getSkillTreeRegistry().getValues().size() - 1) / 16F);
        Button nextPage = new Button(posX + 212, posY - 21, 20, 20, ">", this::actionPerformed);
        Button prevPage = new Button(posX - 15, posY - 21, 20, 20, "<", this::actionPerformed);
        nextPage.active = page < maxPage;
        prevPage.active = page > 0;
        for (Widget button : buttons)
            if (button instanceof SkillTreeGuiButton)
                button.visible = (int) Math.floor((float) ((SkillTreeGuiButton) button).id / 16F) == page;
        addButton(nextPage);
        addButton(prevPage);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
        if (mouseButton == 0) {
            if (hoverItem != null && !CapabilityHelper.knows(player, hoverItem)) {
                if (CapabilityHelper.canLearn(player, hoverItem) || player.isCreative())
                    NetworkHandler.INSTANCE.sendToServer(new LearnSkillPacket(hoverItem.getID()));
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
        return posX - offsetX + s.getPosX();
    }

    private int calcYOffset(int posY, Skill s) {
        return posY - offsetY + s.getPosY();
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        renderBackground(0);
        int posX = width / 2 - xSize / 2;
        int posY = height / 2 - ySize / 2;
        float renderSize = 32F;
        float renderRatio = 0.29F;
        setBlitOffset(-5);
        Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation(ArsMagicaAPI.MODID, "textures/gui/occulus/overlay.png"));
        blit(posX, posY, 0, 0, 210, 210);
        if ((int) Math.floor((float) currentTabId / 16F) == page) {
            if ((currentTabId % 16) < 8)
                blit(posX + 7 + ((currentTabId % 16) * 24), posY, 22, 210, 22, 7);
            else
                blit(posX + 7 + (((currentTabId % 16) - 8) * 24), posY + 203, 22, 210, 22, 7);
        }
        setBlitOffset(-18);
        if (isDragging()) {
            int dx = lastMouseX - mouseX;
            int dy = lastMouseY - mouseY;
            offsetX += dx;
            offsetY += dy;
            if (offsetX < 0) offsetX = 0;
            if (offsetX > 568) offsetX = 568;
            if (offsetY < 0) offsetY = 0;
            if (offsetY > 568) offsetY = 568;
        }
        lastMouseX = mouseX;
        lastMouseY = mouseY;
        float calcYOffest = ((float) offsetY / 568) * (1 - renderRatio);
        float calcXOffest = ((float) offsetX / 568) * (1 - renderRatio);
        int maxSize = 0;
        for (SkillPoint point : SkillPointRegistry.SKILL_POINT_REGISTRY.values().stream().filter(SkillPoint::canRender).collect(Collectors.toList()))
            maxSize = Math.max(maxSize, font.getStringWidth(point.getDisplayName().getFormattedText() + " : " + CapabilityHelper.getSkillPoint(player, point)));
        setBlitOffset(-1);
        Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation(ArsMagicaAPI.MODID, "textures/gui/occulus/skill_points.png"));
        drawSkillPointBackground(posX, posY, maxSize + 10, 210);
        int pointOffsetX = 5;
        for (SkillPoint point : SkillPointRegistry.SKILL_POINT_REGISTRY.values().stream().filter(SkillPoint::canRender).sorted(Comparator.comparingInt(SkillPoint::getTier)).collect(Collectors.toList())) {
            font.drawString(point.getDisplayName().getFormattedText() + " : " + CapabilityHelper.getSkillPoint(player, point), posX + 215, posY + pointOffsetX, point.getColor());
            pointOffsetX += 10;
        }
        RenderSystem.color3f(1f, 1f, 1f);
        Minecraft.getInstance().getTextureManager().bindTexture(currentTree.getBackground());
        if (currentTree != ModSkillTrees.AFFINITY.get()) {
            RenderUtil.drawBox(posX + 7, posY + 7, 196, 196, getBlitOffset(), calcXOffest, calcYOffest, renderRatio + calcXOffest, renderRatio + calcYOffest);
            List<Skill> skills = SkillRegistry.getSkillsForTree(currentTree);
            setBlitOffset(1);
            for (Skill s : skills) {
                if (s.getPoint() == null || (!s.getPoint().canRender() && !CapabilityHelper.knows(player, s)))
                    continue;
                for (String p : s.getParents()) {
                    if (p == null)
                        continue;
                    Skill parent = RegistryHandler.getSkillRegistry().getValue(new ResourceLocation(p));
                    if (parent == null || !skills.contains(parent)) continue;
                    if (!parent.getPoint().canRender() && !CapabilityHelper.knows(player, parent))
                        continue;
                    int offsetX = calcXOffset(posX, s) + 16;
                    int offsetY = calcYOffset(posY, s) + 16;
                    int offsetX2 = calcXOffset(posX, parent) + 16;
                    int offsetY2 = calcYOffset(posY, parent) + 16;
                    offsetX = MathHelper.clamp(offsetX, posX + 7, posX + 203);
                    offsetY = MathHelper.clamp(offsetY, posY + 7, posY + 203);
                    offsetX2 = MathHelper.clamp(offsetX2, posX + 7, posX + 203);
                    offsetY2 = MathHelper.clamp(offsetY2, posY + 7, posY + 203);
                    boolean hasPrereq = CapabilityHelper.canLearn(player, s) || CapabilityHelper.knows(player, s);
                    int color = (!CapabilityHelper.knows(player, s) ? s.getPoint().getColor() & 0x999999 : 0x00ff00);
                    if (!hasPrereq) color = 0x000000;
                    if (!(offsetX == posX + 7 || offsetX == posX + 203))
                        RenderUtil.lineThick2d(offsetX, offsetY, offsetX, offsetY2, hasPrereq ? 0 : -1, color);
                    if (!(offsetY2 == posY + 7 || offsetY2 == posY + 203))
                        RenderUtil.lineThick2d(offsetX, offsetY2, offsetX2, offsetY2, hasPrereq ? 0 : -1, color);
                }
            }
            for (Skill s : skills) {
                if (s.getPoint() == null || (!s.getPoint().canRender() && !CapabilityHelper.knows(player, s)))
                    continue;
                RenderSystem.color4f(1, 1, 1, 1);
                boolean hasPrereq = CapabilityHelper.canLearn(player, s) || CapabilityHelper.knows(player, s);
                int offsetX = calcXOffset(posX, s);
                int offsetY = calcYOffset(posY, s);
                int tick = (player.ticksExisted % 80) >= 40 ? (player.ticksExisted % 40) - 20 : -(player.ticksExisted % 40) + 20;
                float multiplier = 0.75F + tick / 80F;
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
                if (offsetX < posX + 7) xStartMod = (float) (posX + 7 - offsetX);
                else if (offsetX + renderSize > posX + 203) xEndMod = renderSize - (posX + 203 - offsetX);
                if (offsetY < posY + 7) yStartMod = (float) (posY + 7 - offsetY);
                else if (offsetY + renderSize > posY + 203) yEndMod = renderSize - (posY + 203 - offsetY);
                if (!hasPrereq)
                    RenderSystem.color3f(0.5F, 0.5F, 0.5F);
                else if (!CapabilityHelper.knows(player, s))
                    RenderSystem.color3f(Math.max(ColorUtil.getRed(s.getPoint().getColor()), 0.6F) * multiplier, Math.max(ColorUtil.getGreen(s.getPoint().getColor()), 0.6F) * multiplier, Math.max(ColorUtil.getBlue(s.getPoint().getColor()), 0.6F) * multiplier);
                RenderSystem.enableBlend();
                RenderUtil.drawBox(offsetX + xStartMod, offsetY + yStartMod, renderSize - xStartMod - xEndMod, renderSize - yStartMod - yEndMod, 0, minU + (xStartMod / renderSize * spriteXSize), minV + (yStartMod / renderSize * spriteYSize), maxU - (xEndMod / renderSize * spriteXSize), maxV - (yEndMod / renderSize * spriteYSize));
                RenderSystem.disableBlend();
                RenderSystem.color4f(1, 1, 1, 1);
            }
            if (mouseX > posX && mouseX < posX + 210 && mouseY > posY && mouseY < posY + 210) {
                boolean flag = false;
                setBlitOffset(0);
                for (Skill s : skills) {
                    if (!s.getPoint().canRender() && !CapabilityHelper.knows(player, s))
                        continue;
                    int offsetX = calcXOffset(posX, s);
                    int offsetY = calcYOffset(posY, s);
                    if (offsetX > mouseX || offsetX < mouseX - renderSize || offsetY > mouseY || offsetY < mouseY - renderSize)
                        continue;
                    boolean hasPrereq = true;
                    for (String subParent : s.getParents())
                        hasPrereq &= CapabilityHelper.knows(player, new ResourceLocation(subParent));
                    List<ITextComponent> list = new ArrayList<>();
                    list.add(s.getDisplayName().applyTextStyle(s.getPoint().getChatColor()));
                    if (hasPrereq)
                        list.add(s.getDescription().applyTextStyle(TextFormatting.DARK_GRAY));
                    else
                        list.add(new TranslationTextComponent(ArsMagicaAPI.MODID + ".occulus.missingrequirements").applyTextStyle(TextFormatting.DARK_RED));
                    renderTooltip(list.stream().map(ITextComponent::getFormattedText).collect(Collectors.toList()), mouseX, mouseY, font);
                    flag = true;
                    hoverItem = s;
                    RenderHelper.disableStandardItemLighting();
                    RenderSystem.color3f(1, 1, 1);
                }
                if (!flag) hoverItem = null;
            }
        } else {
            boolean isShiftDown = player.func_226563_dT_();
            RenderUtil.drawBox(posX + 7, posY + 7, 196, 196, getBlitOffset(), 0, 0, 1, 1);
            int affNum = RegistryHandler.getAffinityRegistry().getValues().size() - 1;
            int portion = 360 / affNum;
            int currentID = 0;
            int cX = posX + xSize / 2;
            int cY = posY + ySize / 2;
            List<ITextComponent> drawString = new ArrayList<>();
            for (Affinity aff : RegistryHandler.getAffinityRegistry().getValues()) {
                if (Objects.equals(aff.getRegistryName(), Affinity.NONE))
                    continue;
                double depth = CapabilityHelper.getAffinityDepth(player, aff);
                double var1 = Math.cos(Math.toRadians(portion * currentID));
                double var2 = Math.sin(Math.toRadians(portion * currentID));
                double var3 = Math.toRadians(portion * currentID - portion / 2.);
                double var4 = Math.toRadians(portion * currentID + portion / 2.);
                double affEndX = var1 * 10F + var1 * depth * 60F;
                double affEndY = var2 * 10F + var2 * depth * 60F;
                double affStartX1 = Math.cos(var3) * 10F;
                double affStartY1 = Math.sin(var3) * 10F;
                double affStartX2 = Math.cos(var4) * 10F;
                double affStartY2 = Math.sin(var4) * 10F;
                double affDrawTextX = var1 * 80F - 7;
                double affDrawTextY = var2 * 80F - 7;
                currentID++;
                int displace = (int) ((Math.max(affStartX1, affStartX2) - Math.min(affStartX1, affStartX2) + Math.max(affStartY1, affStartY2) - Math.min(affStartY1, affStartY2)) / 2);
                if (depth > 0.01F) {
                    RenderUtil.fractalLine2dd(affStartX1 + cX, affStartY1 + cY, affEndX + cX, affEndY + cY, getBlitOffset(), aff.getColor(), displace, 0.8F);
                    RenderUtil.fractalLine2dd(affStartX2 + cX, affStartY2 + cY, affEndX + cX, affEndY + cY, getBlitOffset(), aff.getColor(), displace, 0.8F);
                    RenderUtil.fractalLine2dd(affStartX1 + cX, affStartY1 + cY, affEndX + cX, affEndY + cY, getBlitOffset(), aff.getColor(), displace, 1.1F);
                    RenderUtil.fractalLine2dd(affStartX2 + cX, affStartY2 + cY, affEndX + cX, affEndY + cY, getBlitOffset(), aff.getColor(), displace, 1.1F);
                } else {
                    RenderUtil.line2d((float) affStartX1 + cX, (float) affStartY1 + cY, (float) affEndX + cX, (float) affEndY + cY, getBlitOffset(), aff.getColor());
                    RenderUtil.line2d((float) affStartX2 + cX, (float) affStartY2 + cY, (float) affEndX + cX, (float) affEndY + cY, getBlitOffset(), aff.getColor());
                }
                getMinecraft().fontRenderer.drawString("" + (float) Math.round(depth * 10000) / 100F, (int) ((affDrawTextX * 0.9) + cX), (int) ((affDrawTextY * 0.9) + cY), aff.getColor());
                int xMovement = affDrawTextX > 0 ? 5 : -5;
                xMovement = affDrawTextX == 0 ? 0 : xMovement;
                int yMovement = affDrawTextY > 0 ? 5 : -5;
                yMovement = affDrawTextY == 0 ? 0 : yMovement;
                int drawX = (int) ((affDrawTextX * 1.1) + cX + xMovement);
                int drawY = (int) ((affDrawTextY * 1.1) + cY + yMovement);
                getMinecraft().getItemRenderer().renderItemAndEffectIntoGUI(new ItemStack(aff.getEssence()), drawX, drawY);
                if (mouseX > drawX && mouseX < drawX + 16 && mouseY > drawY && mouseY < drawY + 16) {
                    drawString.add(aff.getDisplayName());
//                    List<AbstractAffinityAbility> abilites = Lists.newArrayList(ArsMagicaAPI.getAffinityAbilityRegistry().getValues());
//                    abilites.sort(new Comparator<AbstractAffinityAbility>() {
//                        @Override
//                        public int compare(AbstractAffinityAbility o1, AbstractAffinityAbility o2) {
//                            return (int) ((o1.getMinimumDepth() * 100) - (o2.getMinimumDepth() * 100));
//                        }
//                    });
//                    for (AbstractAffinityAbility ability : abilites) {
//                        if (ability.getAffinity() == aff) {
//                            String advancedTooltip = "";
//                            if (isShiftDown) advancedTooltip = " (Min. : " + Math.round(ability.getMinimumDepth() * 100) + "%" + (ability.hasMax() ?(", Max. : " + Math.round(ability.getMaximumDepth() * 100) + "%")  : "") + ")";
//                            drawString.add(TextFormatting.RESET.toString() + (ability.isEligible(player) ? TextFormatting.GREEN.toString() : TextFormatting.DARK_RED.toString()) + I18n.translateToLocal("affinityability." + ability.getRegistryName().toString().replaceAll("arsmagica2:", "") + ".name") + advancedTooltip);
//                        }
//                    }
                }
            }
            if (!drawString.isEmpty()) {
                if (!isShiftDown)
                    drawString.add(new TranslationTextComponent(ArsMagicaAPI.MODID + ".tooltip.shiftForDetails").applyTextStyle(TextFormatting.GRAY));
                renderTooltip(drawString.stream().map(ITextComponent::getFormattedText).collect(Collectors.toList()), mouseX, mouseY);
            }
            RenderSystem.color3f(1, 1, 1);
            RenderHelper.disableStandardItemLighting();
        }
        RenderSystem.color3f(1, 1, 1);
        super.render(mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    private void drawSkillPointBackground(int startX, int startY, int width, int height) {
        int posX = 210;
        int posY = 0;
        blit(startX + posX + width - 4, startY + posY, 252, 0, 4, 4);
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
            blit(startX + posX + width - 4, startY + posY + 4 + h - y, 252, 4, 4, y);
            h -= y;
        }
    }
}
