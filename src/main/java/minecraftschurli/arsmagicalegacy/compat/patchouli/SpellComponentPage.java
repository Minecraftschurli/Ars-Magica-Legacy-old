package minecraftschurli.arsmagicalegacy.compat.patchouli;

import com.mojang.blaze3d.systems.RenderSystem;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.EnumSet;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;
import minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import minecraftschurli.arsmagicalegacy.api.etherium.EtheriumType;
import minecraftschurli.arsmagicalegacy.api.registry.RegistryHandler;
import minecraftschurli.arsmagicalegacy.api.registry.SpellRegistry;
import minecraftschurli.arsmagicalegacy.api.skill.Skill;
import minecraftschurli.arsmagicalegacy.api.spell.AbstractSpellPart;
import minecraftschurli.arsmagicalegacy.api.spell.SpellComponent;
import minecraftschurli.arsmagicalegacy.api.spell.SpellModifier;
import minecraftschurli.arsmagicalegacy.api.spell.SpellModifiers;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.EtheriumSpellIngredient;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ISpellIngredient;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ItemStackSpellIngredient;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ItemTagSpellIngredient;
import minecraftschurli.arsmagicalegacy.util.RenderUtils;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.client.gui.GuiUtils;
import org.lwjgl.opengl.GL11;
import vazkii.patchouli.api.IComponentRenderContext;
import vazkii.patchouli.api.ICustomComponent;

/**
 * @author Minecraftschurli
 * @version 2020-02-20
 */
public class SpellComponentPage implements ICustomComponent {
    private String component;
    private transient int x, y;
    private transient AbstractSpellPart part;

    @Override
    public void build(int x, int y, int num) {
        this.x = x;
        this.y = y;
        this.part = RegistryHandler.getSpellPartRegistry().getValue(ResourceLocation.tryCreate(component));
    }

    @Override
    public void render(IComponentRenderContext context, float pticks, int mouseX, int mouseY) {
        int cx = x + 50;
        int cy = y + 76;
        RenderSystem.pushMatrix();
        /*RenderSystem.enableBlend();
        context.getGui().getMinecraft().getTextureManager().bindTexture(new ResourceLocation(ArsMagicaAPI.MODID, "textures/gui/arcane_compendium_gui_extras.png"));
        context.getGui().setBlitOffset(context.getGui().getBlitOffset()+1);
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        drawTexturedModalRectClassic(x + 42, y + 15, 112, 145, 60, 40, 40, 40, context.getGui().getBlitOffset());
        drawTexturedModalRectClassic(x, y , 112, 175, 60, 40, 40, 40, context.getGui().getBlitOffset());
        context.getGui().setBlitOffset(context.getGui().getBlitOffset()-1);
        RenderSystem.disableBlend();*/
        renderRecipe(context, cx, cy, mouseX, mouseY);
        RenderSystem.enableBlend();
        Skill skill = SpellRegistry.getSkillFromPart(part);
        context.getGui().getMinecraft().getTextureManager().bindTexture(skill.getIcon());
        RenderSystem.color4f(1, 1, 1, 1);
        drawTexturedModalRectClassic(cx, cy, 0, 0, 16, 16, 256, 256, context.getGui().getBlitOffset());
        if (context.isAreaHovered(mouseX, mouseY, cx, cy, 16, 16)) {
            context.setHoverTooltip(skill.getTooltip()
                    .stream()
                    .map(ITextComponent::getFormattedText)
                    .collect(Collectors.toList()));
        }
        renderModifiers(context, x, y, mouseX, mouseY);
        RenderSystem.disableBlend();
        RenderSystem.popMatrix();
    }

    private void renderModifiers(IComponentRenderContext context, int posX, int posY, int mouseX, int mouseY) {
        ArrayList<SpellModifier> modifiers = new ArrayList<>();
        EnumSet<SpellModifiers> mods = part.getModifiers();
        for (AbstractSpellPart modifier : RegistryHandler.getSpellPartRegistry()) {
            if (part == modifier)
                continue;
            if (modifier instanceof SpellModifier) {
                for (SpellModifiers mod : ((SpellModifier) modifier).getAspectsModified()) {
                    if (mods.contains(mod)) {
                        modifiers.add((SpellModifier) modifier);
                        break;
                    }
                }
            }
        }
        int startX = 58 - (8 * modifiers.size());
        int yOffset = 10;
        if (!modifiers.isEmpty()) {
            String shapeName = new TranslationTextComponent(ArsMagicaAPI.MODID + (part instanceof SpellComponent ? ".gui.modifies" : ".gui.modified_by")).getUnformattedComponentText();
            context.getFont().drawString(shapeName, posX + 58 - (context.getFont().getStringWidth(shapeName) / 2f), posY, 0);
            RenderSystem.color3f(1.0f, 1.0f, 1.0f);
        }
        for (SpellModifier mod : modifiers) {
            Skill skill = SpellRegistry.getSkillFromPart(mod);
            context.getGui().getMinecraft().getTextureManager().bindTexture(skill.getIcon());
            RenderSystem.enableBlend();
            drawTexturedModalRectClassic(posX + startX, posY + yOffset, 0, 0, 16, 16, 256, 256, context.getGui().getBlitOffset());
            RenderSystem.disableBlend();
            if (context.isAreaHovered(mouseX, mouseY, posX + startX, posY + yOffset, 16, 16)) {
                context.setHoverTooltip(skill.getTooltip()
                        .stream()
                        .map(ITextComponent::getFormattedText)
                        .collect(Collectors.toList()));
            }
            startX += 16;
        }
    }

    private void renderRecipe(IComponentRenderContext context, int cx, int cy, int mousex, int mousey) {
        if (part == null || part.getRecipe() == null) return;
        float angleStep = (360.0f / part.getRecipe().length);
        for (int i = 0; i < part.getRecipe().length; i++) {
            float angle = (float) (Math.toRadians((angleStep * i) + (context.getTicksInBook() * 0.5) % 360));
            float nextangle = (float) (Math.toRadians((angleStep * ((i + 1) % part.getRecipe().length)) + (context.getTicksInBook() * 0.5) % 360));
            float dist = 45;
            float x = (float) (cx - Math.cos(angle) * dist);
            float y = (float) (cy - Math.sin(angle) * dist);
            float nextx = (float) (cx - Math.cos(nextangle) * dist);
            float nexty = (float) (cy - Math.sin(nextangle) * dist);
            RenderUtils.line2d(x + 8, y + 8, cx + 8, cy + 8, context.getGui().getBlitOffset(), 0x0000DD);
            RenderUtils.gradientline2d(x + 8, y + 8, nextx + 8, nexty + 8, context.getGui().getBlitOffset(), 0x0000DD, 0xDD00DD);
            renderCraftingComponent(context, i, x, y, mousex, mousey);
        }
    }

    private void renderCraftingComponent(IComponentRenderContext context, int index, float sx, float sy, int mousex, int mousey) {
        ISpellIngredient craftingComponent = part.getRecipe()[index];
        if (craftingComponent == null) return;
        ItemStack stack;
        if (craftingComponent instanceof ItemStackSpellIngredient) {
            stack = ((ItemStackSpellIngredient) craftingComponent).getStack().copy();
        } else if (craftingComponent instanceof ItemTagSpellIngredient) {
            int size = (((ItemTagSpellIngredient) craftingComponent).getTag().getAllElements()).size();
            if (size == 0)
                return;
            stack = new ItemStack(((ItemTagSpellIngredient) craftingComponent).getTag().getRandomElement(new Random()));
        } else if (craftingComponent instanceof EtheriumSpellIngredient) {
            renderEssence(context, mousex, mousey, sx, sy, ((EtheriumSpellIngredient) craftingComponent));
            return;
        } else return;
        RenderUtils.renderItemIntoGUI(context.getGui().getMinecraft().getItemRenderer(), context.getGui().getMinecraft().getTextureManager(), stack, sx, sy, context.getGui().getBlitOffset() + 1);
        if (context.isAreaHovered(mousex, mousey, (int) sx, (int) sy, 16, 16)) {
            context.setHoverTooltip(context.getGui().getTooltipFromItem(stack));
        }
    }

    private void renderEssence(IComponentRenderContext context, int mousex, int mousey, float x, float y, EtheriumSpellIngredient ingredient) {
        if (!ingredient.getEssenceTypes().isEmpty()) {
            //TODO render
            Deque<EtheriumType> types = new ArrayDeque<>(ingredient.getEssenceTypes());
            GuiUtils.drawGradientRect(context.getGui().getBlitOffset(), (int) x, (int) y, (int) x + 16, (int) y + 16, types.getFirst().getColor(), types.getLast().getColor());
        }
        if (context.isAreaHovered(mousex, mousey, (int) x, (int) y, 16, 16)) {
            context.setHoverTooltip(Collections.singletonList(ingredient.getTooltip().getFormattedText()));
        }
    }

    public void drawTexturedModalRectClassic(int dst_x, int dst_y, int src_x, int src_y, int dst_width, int dst_height, int src_width, int src_height, int zLevel) {
        final float uScale = 1f / 0x100;
        final float vScale = 1f / 0x100;
        Tessellator var9 = Tessellator.getInstance();
        var9.getBuffer().begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        var9.getBuffer().pos(dst_x, dst_y + dst_height, zLevel).tex((src_x) * uScale, (src_y + src_height) * vScale).endVertex();
        var9.getBuffer().pos(dst_x + dst_width, dst_y + dst_height, zLevel).tex((src_x + src_width) * uScale, (src_y + src_height) * vScale).endVertex();
        var9.getBuffer().pos(dst_x + dst_width, dst_y, zLevel).tex((src_x + src_width) * uScale, (src_y) * vScale).endVertex();
        var9.getBuffer().pos(dst_x, dst_y, zLevel).tex((src_x) * uScale, (src_y) * vScale).endVertex();
        var9.draw();
    }

    @Override
    public void onVariablesAvailable(Function<String, String> function) {
        component = function.apply(component);
    }
}
