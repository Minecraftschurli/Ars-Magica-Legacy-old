package minecraftschurli.arsmagicalegacy.objects.block.craftingaltar;

import com.mojang.blaze3d.matrix.MatrixStack;
import minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ISpellIngredient;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ItemStackSpellIngredient;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ItemTagSpellIngredient;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.SpellIngredientList;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Matrix4f;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TranslationTextComponent;

/**
 * @author Minecraftschurli
 * @version 2019-12-20
 */
public class CraftingAltarViewTER extends TileEntityRenderer<CraftingAltarViewTileEntity> {
    public CraftingAltarViewTER(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
    }

    private void doRender(CraftingAltarTileEntity altar, CraftingAltarViewTileEntity view, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        //fixme
        ItemStack book = altar.getBook();
        SpellIngredientList recipe = altar.getRecipe();
        if (book.isEmpty() || recipe == null)
            return;
        matrixStackIn.push();
        matrixStackIn.scale(0.55f, 0.55f, 0.55f);
        if (!altar.hasEnoughPower())
            drawNameplate(new TranslationTextComponent(ArsMagicaAPI.MODID + ".altar.lowpower").getFormattedText(), matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
        else {
            ISpellIngredient ingredient = altar.getCurrentIngredient();
            if (ingredient == null) {
                matrixStackIn.pop();
                return;
            }
            drawNameplate(ingredient.getTooltip().getFormattedText(), matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
        }
        matrixStackIn.pop();
        matrixStackIn.rotate(Vector3f.YP.rotationDegrees(view.itemRotation));
        ItemStack stack;
        if (!altar.hasEnoughPower()) stack = new ItemStack(Blocks.BARRIER);
        else {
            ISpellIngredient ingredient = altar.getCurrentIngredient();
            if (ingredient == null)
                return;
            if (ingredient instanceof ItemStackSpellIngredient)
                stack = ((ItemStackSpellIngredient) ingredient).getStack();
            else if (ingredient instanceof ItemTagSpellIngredient) {
                if (view.itemRotation % 36 == 0)
                    view.itemCache = ((ItemTagSpellIngredient) ingredient).getTag().getRandomElement(altar.getWorld().rand);
                stack = new ItemStack(view.itemCache);
            } else {
                ingredient.render();
                return;
            }
        }
        matrixStackIn.translate(0, -0.1, 0);
        Minecraft.getInstance().getItemRenderer().renderItem(stack, ItemCameraTransforms.TransformType.GROUND, combinedLightIn, combinedOverlayIn, matrixStackIn, bufferIn);
    }

    private void drawNameplate(String text, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        matrixStackIn.push();
        matrixStackIn.translate(0, 0.9, 0);
        matrixStackIn.rotate(this.renderDispatcher.renderInfo.getRotation());
        matrixStackIn.scale(-0.025F, -0.025F, 0.025F);
        matrixStackIn.scale(1.2f, 1.2f, 1.2f);
        Matrix4f matrix4f = matrixStackIn.getLast().getMatrix();
        float f1 = Minecraft.getInstance().gameSettings.getTextBackgroundOpacity(0.25F);
        int j = (int) (f1 * 255) << 24;
        FontRenderer fontrenderer = this.renderDispatcher.fontRenderer;
        float f2 = (float) (-fontrenderer.getStringWidth(text) / 2);
        fontrenderer.renderString(text, f2, 0, 0xbbffffff, false, matrix4f, bufferIn, false, j, combinedLightIn);
        matrixStackIn.pop();
    }

    @Override
    public void render(CraftingAltarViewTileEntity tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        tileEntityIn.itemRotation += 1;
        if (tileEntityIn.itemRotation == 360)
            tileEntityIn.itemRotation = 0;
        CraftingAltarTileEntity altar = tileEntityIn.getAltar();
        if (altar != null && altar.isMultiblockFormed()) {
            matrixStackIn.push();
            matrixStackIn.translate(0.5, 0.5, 0.5);
            doRender(altar, tileEntityIn, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
            matrixStackIn.pop();
        }
    }
}
