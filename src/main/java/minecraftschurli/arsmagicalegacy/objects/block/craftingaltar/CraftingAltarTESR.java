package minecraftschurli.arsmagicalegacy.objects.block.craftingaltar;

import com.mojang.blaze3d.platform.GlStateManager;
import minecraftschurli.arsmagicalegacy.ArsMagicaLegacy;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ISpellIngredient;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ItemStackSpellIngredient;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ItemTagSpellIngredient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL14;

/**
 * @author Minecraftschurli
 * @version 2019-12-20
 */
public class CraftingAltarTESR extends TileEntityRenderer<CraftingAltarTileEntity> {
    @Override
    public void render(CraftingAltarTileEntity tileEntityIn, double x, double y, double z, float partialTicks, int destroyStage) {
        //ArsMagicaLegacy.LOGGER.debug("hello");
        if (tileEntityIn.multiblockState) {
            ISpellIngredient ingredient = tileEntityIn.getCurrentIngredient();
            if (ingredient == null)
                return;
            GlStateManager.pushMatrix();
            Vec3d offset = tileEntityIn.getLecternOffset();
            //bindTexture(tileEntityIn.getCamouflageRL());
            GlStateManager.translated(x + offset.x + 0.5,y + offset.y + 1.5,z + offset.z + 0.5);
            drawNameplate(tileEntityIn, ingredient.getTooltip().getFormattedText(), x + offset.x,y + offset.y + 1.5,z + offset.z, 32);
            if (ingredient instanceof ItemStackSpellIngredient){
                Minecraft.getInstance().getItemRenderer().renderItem(((ItemStackSpellIngredient)ingredient).getStack(), ItemCameraTransforms.TransformType.GROUND);
            } else if (ingredient instanceof ItemTagSpellIngredient) {
                Minecraft.getInstance().getItemRenderer().renderItem(((ItemTagSpellIngredient)ingredient).getTag().getAllElements().stream().findFirst().map(ItemStack::new).orElse(ItemStack.EMPTY), ItemCameraTransforms.TransformType.GROUND);
            }
            GlStateManager.popMatrix();
        }
    }

    @Override
    public boolean isGlobalRenderer(CraftingAltarTileEntity te) {
        return true;
    }
}
