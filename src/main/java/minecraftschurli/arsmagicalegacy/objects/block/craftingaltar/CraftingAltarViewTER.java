package minecraftschurli.arsmagicalegacy.objects.block.craftingaltar;

import com.mojang.blaze3d.platform.*;
import minecraftschurli.arsmagicalegacy.ArsMagicaLegacy;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.*;
import net.minecraft.block.Blocks;
import net.minecraft.client.*;
import net.minecraft.client.renderer.model.*;
import net.minecraft.client.renderer.tileentity.*;
import net.minecraft.item.*;
import net.minecraft.util.text.TranslationTextComponent;

/**
 * @author Minecraftschurli
 * @version 2019-12-20
 */
public class CraftingAltarViewTER extends TileEntityRenderer<CraftingAltarViewTileEntity> {
    @Override
    public void render(CraftingAltarViewTileEntity tileEntityIn, double x, double y, double z, float partialTicks, int destroyStage) {
        tileEntityIn.itemRotation += 1;
        if (tileEntityIn.itemRotation == 360)
            tileEntityIn.itemRotation = 0;
        CraftingAltarTileEntity altar = tileEntityIn.getAltar();
        if (altar != null && altar.isMultiblockFormed()) {
            GlStateManager.pushMatrix();
            GlStateManager.translated(x + 0.5, y + 0.5, z + 0.5);
            setLightmapDisabled(true);

            doRender(altar, tileEntityIn);

            setLightmapDisabled(false);
            GlStateManager.popMatrix();
        }
    }

    private void doRender(CraftingAltarTileEntity altar, CraftingAltarViewTileEntity view) {
        ItemStack book = altar.getBook();
        SpellIngredientList recipe = altar.getRecipe();
        if (book.isEmpty() || recipe == null)
            return;
        GlStateManager.pushMatrix();
        GlStateManager.scaled(0.55, 0.55, 0.55);
        if (!altar.hasEnoughPower()) {
            drawNameplate(view, new TranslationTextComponent(ArsMagicaLegacy.MODID+".altar.lowpower").getFormattedText(), -0.5, -0.4, -0.5, 32);
        } else {
            ISpellIngredient ingredient = altar.getCurrentIngredient();
            if (ingredient == null)
                return;
            drawNameplate(view, ingredient.getTooltip().getFormattedText(), -0.5, -0.4, -0.5, 32);
        }
        GlStateManager.popMatrix();
        GlStateManager.rotatef(view.itemRotation, 0.0F, 1.0F, 0.0F);
        //GlStateManager.rotatef(this.rendererDispatcher.renderInfo.getPitch(), 1.0F, 0.0F, 0.0F);
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
                    view.itemCache = ((ItemTagSpellIngredient) ingredient).getTag().getRandomElement(getWorld().rand);
                }
                stack = new ItemStack(view.itemCache);
            } else {
                ingredient.render();
                return;
            }
        }
        //noinspection deprecation
        Minecraft.getInstance().getItemRenderer().renderItem(stack, ItemCameraTransforms.TransformType.GROUND);
    }
}
