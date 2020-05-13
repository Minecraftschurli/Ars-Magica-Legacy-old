package minecraftschurli.arsmagicalegacy.objects.item.spellbook;

import com.mojang.blaze3d.matrix.MatrixStack;
import javax.annotation.Nonnull;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.item.ItemStack;

/**
 * @author Minecraftschurli
 * @version 2020-04-28
 */
public class SpellBookISTER extends ItemStackTileEntityRenderer {
    @Override
    public void render(@Nonnull ItemStack itemStackIn, @Nonnull MatrixStack matrixStackIn, @Nonnull IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        ItemStack activeItemStack = SpellBookItem.getActiveItemStack(itemStackIn);
        if (activeItemStack.isEmpty()) return;
        ItemStackTileEntityRenderer renderer = activeItemStack.getItem().getItemStackTileEntityRenderer();
        if (renderer == ItemStackTileEntityRenderer.instance) return;
        renderer.render(activeItemStack, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
    }
}
