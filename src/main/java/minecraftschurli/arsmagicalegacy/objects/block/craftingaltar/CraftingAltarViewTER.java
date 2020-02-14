package minecraftschurli.arsmagicalegacy.objects.block.craftingaltar;

import com.mojang.blaze3d.matrix.*;
import minecraftschurli.arsmagicalegacy.*;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.*;
import net.minecraft.block.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.model.*;
import net.minecraft.client.renderer.tileentity.*;
import net.minecraft.item.*;
import net.minecraft.util.text.*;

/**
 * @author Minecraftschurli
 * @version 2019-12-20
 */
public class CraftingAltarViewTER extends TileEntityRenderer<CraftingAltarViewTileEntity> {
    public CraftingAltarViewTER(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
    }

    private void doRender(CraftingAltarTileEntity altar, CraftingAltarViewTileEntity view, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        ItemStack book = altar.getBook();
        SpellIngredientList recipe = altar.getRecipe();
        if (book.isEmpty() || recipe == null)
            return;
        matrixStackIn.push();
        matrixStackIn.scale(0.55f, 0.55f, 0.55f);
        if (!altar.hasEnoughPower()) {
            drawNameplate(new TranslationTextComponent(ArsMagicaLegacy.MODID+".altar.lowpower").getFormattedText(), matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
        } else {
            ISpellIngredient ingredient = altar.getCurrentIngredient();
            if (ingredient == null)
                return;
            drawNameplate(ingredient.getTooltip().getFormattedText(), matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
        }
        matrixStackIn.pop();
        matrixStackIn.rotate(new Quaternion(new Vector3f(0,1,0), view.itemRotation, true));
        //RenderSystem.rotatef(this.rendererDispatcher.renderInfo.getPitch(), 1, 0, 0);
        ItemStack stack;
        if (!altar.hasEnoughPower()) {
            stack = new ItemStack(Blocks.BARRIER);
        } else {
            ISpellIngredient ingredient = altar.getCurrentIngredient();
            if (ingredient == null)
                return;
            if (ingredient instanceof ItemStackSpellIngredient) {
                stack = ((ItemStackSpellIngredient) ingredient).getStack();
            } else if (ingredient instanceof ItemTagSpellIngredient) {
                if (view.itemRotation % 36 == 0) {
                    view.itemCache = ((ItemTagSpellIngredient) ingredient).getTag().getRandomElement(altar.getWorld().rand);
                }
                stack = new ItemStack(view.itemCache);
            } else {
                ingredient.render();
                return;
            }
        }
        //noinspection deprecation
        Minecraft.getInstance().getItemRenderer().renderItem(stack, ItemCameraTransforms.TransformType.GROUND, 0,0, matrixStackIn, bufferIn);
    }

    private void drawNameplate(String text, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        matrixStackIn.push();
        matrixStackIn.translate(-0.5, -0.4, -0.5);
        matrixStackIn.rotate(this.renderDispatcher.renderInfo.getRotation());
        matrixStackIn.scale(-0.025F, -0.025F, 0.025F);
        Matrix4f matrix4f = matrixStackIn.getLast().getMatrix();
        float f1 = Minecraft.getInstance().gameSettings.getTextBackgroundOpacity(0.25F);
        int j = (int)(f1 * 255) << 24;
        FontRenderer fontrenderer = this.renderDispatcher.fontRenderer;
        float f2 = (float)(-fontrenderer.getStringWidth(text) / 2);
        fontrenderer.renderString(text, f2, 0, 553648127, false, matrix4f, bufferIn, true, j, combinedLightIn);
        /*if (flag) {
            fontrenderer.renderString(text, f2, 0, -1, false, matrix4f, bufferIn, false, 0, packedLightIn);
        }*/

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
            //setLightmapDisabled(true);

            doRender(altar, tileEntityIn, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);

            //setLightmapDisabled(false);
            matrixStackIn.pop();
        }
    }
}
