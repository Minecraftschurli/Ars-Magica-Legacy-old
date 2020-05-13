package minecraftschurli.arsmagicalegacy.compat.patchouli;

import com.mojang.blaze3d.systems.RenderSystem;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.EnumSet;
import java.util.List;
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
import minecraftschurli.arsmagicalegacy.util.RenderUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.client.gui.GuiUtils;
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
        int cy = y + 70;
        RenderSystem.pushMatrix();
//        RenderSystem.enableBlend();
//        context.getGui().getMinecraft().getTextureManager().bindTexture(new ResourceLocation(ArsMagicaAPI.MODID, "textures/gui/arcane_compendium_gui_extras.png"));
//        context.getGui().setBlitOffset(context.getGui().getBlitOffset()+1);
//        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
//        RenderUtil.drawTexturedModalRectClassic(x + 42, y + 15, 112, 145, 60, 40, 40, 40, context.getGui().getBlitOffset());
//        RenderUtil.drawTexturedModalRectClassic(x, y , 112, 175, 60, 40, 40, 40, context.getGui().getBlitOffset());
//        context.getGui().setBlitOffset(context.getGui().getBlitOffset()-1);
//        RenderSystem.disableBlend();
        List<SpellModifier> modifiers = cacheModifiers();
        if (modifiers.isEmpty()) cy -= 16;
        else cy += ((modifiers.size() / 7) * 16) + 8;
        renderRecipe(context, cx, cy, mouseX, mouseY);
        RenderSystem.enableBlend();
        Skill skill = SpellRegistry.getSkillFromPart(part);
        context.getGui().getMinecraft().getTextureManager().bindTexture(skill.getIcon());
        RenderSystem.color4f(1, 1, 1, 1);
        RenderUtil.drawTexturedModalRectClassic(cx - 2, cy - 2, 0, 0, 20, 20, 256, 256, context.getGui().getBlitOffset());
        if (context.isAreaHovered(mouseX, mouseY, cx - 2, cy - 2, 20, 20))
            context.setHoverTooltip(skill.getTooltip().stream().map(ITextComponent::getFormattedText).collect(Collectors.toList()));
        renderModifiers(context, x, y, mouseX, mouseY, modifiers);
        RenderSystem.disableBlend();
        RenderSystem.popMatrix();
    }

    private List<SpellModifier> cacheModifiers() {
        ArrayList<SpellModifier> modifiers = new ArrayList<>();
        EnumSet<SpellModifiers> mods = part.getModifiers();
        for (AbstractSpellPart modifier : RegistryHandler.getSpellPartRegistry()) {
            if (part == modifier) continue;
            if (modifier instanceof SpellModifier)
                for (SpellModifiers mod : ((SpellModifier) modifier).getAspectsModified())
                    if (mods.contains(mod)) {
                        modifiers.add((SpellModifier) modifier);
                        break;
                    }
        }
        return modifiers;
    }

    private void renderModifiers(IComponentRenderContext context, int posX, int posY, int mouseX, int mouseY, List<SpellModifier> modifiers) {
        if (modifiers.isEmpty()) return;
        String shapeName = new TranslationTextComponent(ArsMagicaAPI.MODID + (part instanceof SpellComponent ? ".gui.modifies" : ".gui.modified_by")).getUnformattedComponentText();
        context.getFont().drawString(shapeName, posX + 58 - (context.getFont().getStringWidth(shapeName) / 2f), posY, 0);
        RenderSystem.color3f(1.0f, 1.0f, 1.0f);
        int startX = 0;
        int yOffset = -6;
        for (int i = 0; i < modifiers.size(); i++) {
            Skill skill = SpellRegistry.getSkillFromPart(modifiers.get(i));
            context.getGui().getMinecraft().getTextureManager().bindTexture(skill.getIcon());
            if (i % 7 == 0) {
                startX = (114 / 2) - ((Math.min(7, modifiers.size() - i) * 16) / 2)/*(modifiers.size() - i) * 16 - 40*/;
                yOffset += 16;
            }
            RenderSystem.enableBlend();
            RenderUtil.drawTexturedModalRectClassic(posX + startX, posY + yOffset, 0, 0, 16, 16, 256, 256, context.getGui().getBlitOffset());
            RenderSystem.disableBlend();
            if (context.isAreaHovered(mouseX, mouseY, posX + startX, posY + yOffset, 16, 16))
                context.setHoverTooltip(skill.getTooltip().stream().map(ITextComponent::getFormattedText).collect(Collectors.toList()));
            startX += 16;
        }
    }

    private void renderRecipe(IComponentRenderContext context, int cx, int cy, int mousex, int mousey) {
        if (part == null) return;
        ISpellIngredient[] recipe = ArsMagicaAPI.getSpellRecipeManager().getRecipe(part.getRegistryName());
        if (recipe == null || recipe.length <= 0) return;
        final float angleStep = 360.0f / recipe.length;
        final float dist = 45;
        float lastAngle = (angleStep * (recipe.length - 1) + (context.getTicksInBook() * 0.5f)) % 360f;
        float lastX = (float) (cx - Math.cos(Math.toRadians(lastAngle)) * dist);
        float lastY = (float) (cy - Math.sin(Math.toRadians(lastAngle)) * dist);
        for (int i = 0; i <= recipe.length; i++) {
            float angle = (lastAngle + angleStep) % 360f;
            float x = (float) (cx - (Math.cos(Math.toRadians(angle)) * dist));
            float y = (float) (cy - (Math.sin(Math.toRadians(angle)) * dist));
            RenderUtil.line2d(x + 8, y + 8, cx + 8, cy + 8, context.getGui().getBlitOffset(), 0x0000DD);
            RenderUtil.gradientline2d(lastX + 8, lastY + 8, x + 8, y + 8, context.getGui().getBlitOffset(), 0x0000DD, 0xDD00DD);
            if (i < recipe.length) renderCraftingComponent(context, recipe[i], x, y, mousex, mousey);
            lastX = x;
            lastY = y;
            lastAngle = angle;
        }
    }

    private void renderCraftingComponent(IComponentRenderContext context, ISpellIngredient craftingComponent, float sx, float sy, int mousex, int mousey) {
        if (craftingComponent == null) return;
        ItemStack stack;
        if (craftingComponent instanceof ItemStackSpellIngredient)
            stack = ((ItemStackSpellIngredient) craftingComponent).getStack().copy();
        else if (craftingComponent instanceof ItemTagSpellIngredient) {
            Collection<Item> tag = ((ItemTagSpellIngredient) craftingComponent).getTag().getAllElements();
            if (tag.size() == 0) return;
            stack = new ItemStack(tag.stream().findFirst().get());
        } else if (craftingComponent instanceof EtheriumSpellIngredient) {
            renderEssence(context, mousex, mousey, sx, sy, ((EtheriumSpellIngredient) craftingComponent));
            return;
        } else return;
        RenderUtil.renderItemIntoGUI(context.getGui().getMinecraft().getItemRenderer(), context.getGui().getMinecraft().getTextureManager(), stack, sx, sy, context.getGui().getBlitOffset() + 1);
        if (context.isAreaHovered(mousex, mousey, (int) sx, (int) sy, 16, 16))
            context.setHoverTooltip(context.getGui().getTooltipFromItem(stack));
    }

    private void renderEssence(IComponentRenderContext context, int mousex, int mousey, float x, float y, EtheriumSpellIngredient ingredient) {
        if (!ingredient.getEssenceTypes().isEmpty()) {
            Deque<EtheriumType> types = new ArrayDeque<>(ingredient.getEssenceTypes());
            GuiUtils.drawGradientRect(context.getGui().getBlitOffset(), (int) x, (int) y, (int) x + 16, (int) y + 16, types.getFirst().getColor(), types.getLast().getColor());
        }
        if (context.isAreaHovered(mousex, mousey, (int) x, (int) y, 16, 16))
            context.setHoverTooltip(Collections.singletonList(ingredient.getTooltip().getFormattedText()));
    }

    @Override
    public void onVariablesAvailable(Function<String, String> function) {
        component = function.apply(component);
    }
}
