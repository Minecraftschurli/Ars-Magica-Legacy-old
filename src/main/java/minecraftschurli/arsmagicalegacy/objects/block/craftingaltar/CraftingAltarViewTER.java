package minecraftschurli.arsmagicalegacy.objects.block.craftingaltar;

import com.mojang.blaze3d.platform.GlStateManager;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ISpellIngredient;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ItemStackSpellIngredient;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ItemTagSpellIngredient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.item.ItemStack;

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
        if (altar != null && altar.multiblockState) {
            ISpellIngredient ingredient = altar.getCurrentIngredient();
            if (ingredient == null)
                return;
            GlStateManager.pushMatrix();
            //bindTexture(tileEntityIn.getCamouflageRL());
            GlStateManager.translated(x + 0.5,y + 0.5,z + 0.5);
            setLightmapDisabled(true);
            drawNameplate(tileEntityIn, ingredient.getTooltip().getFormattedText(), x,y + 0.3,z, 128);
            GlStateManager.rotatef(tileEntityIn.itemRotation, 0.0F, 1.0F, 0.0F);
            //GlStateManager.rotatef(this.rendererDispatcher.renderInfo.getPitch(), 1.0F, 0.0F, 0.0F);
            if (ingredient instanceof ItemStackSpellIngredient){
                Minecraft.getInstance().getItemRenderer().renderItem(((ItemStackSpellIngredient)ingredient).getStack(), ItemCameraTransforms.TransformType.GROUND);
            } else if (ingredient instanceof ItemTagSpellIngredient) {
                if (tileEntityIn.itemRotation % 3 == 0){
                    tileEntityIn.itemCache = ((ItemTagSpellIngredient)ingredient).getTag().getRandomElement(getWorld().rand);
                }
                Minecraft.getInstance().getItemRenderer().renderItem(new ItemStack(tileEntityIn.itemCache), ItemCameraTransforms.TransformType.GROUND);
            }
            setLightmapDisabled(false);
            GlStateManager.popMatrix();
        }
    }

    /*@Override
    public boolean isGlobalRenderer(CraftingAltarViewTileEntity te) {
        return true;
    }*/
}
