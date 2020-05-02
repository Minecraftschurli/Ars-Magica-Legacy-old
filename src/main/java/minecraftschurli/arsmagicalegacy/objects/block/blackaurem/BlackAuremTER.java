package minecraftschurli.arsmagicalegacy.objects.block.blackaurem;

import com.mojang.blaze3d.matrix.MatrixStack;
import minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import minecraftschurli.arsmagicalegacy.api.etherium.generator.EtheriumGeneratorTileEntity;
import minecraftschurli.arsmagicalegacy.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Quaternion;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.ResourceLocation;

/**
 * @author Minecraftschurli
 * @version 2020-04-23
 */
public class BlackAuremTER extends TileEntityRenderer<EtheriumGeneratorTileEntity> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(ArsMagicaAPI.MODID, "textures/block/black_aurem.png");

    public BlackAuremTER(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
    }

    @Override
    public void render(EtheriumGeneratorTileEntity tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        matrixStackIn.push();
        renderBlackAurem(matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
        matrixStackIn.pop();
    }

    private void renderBlackAurem(MatrixStack matrixStack, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        matrixStack.translate(-0.5f, -0.5f, 0.5);
        matrixStack.scale(2f, 2f, 2f);
        matrixStack.translate(0.5f, 0.5f, 0f);
        matrixStack.rotate(Minecraft.getInstance().getRenderManager().getCameraOrientation());
        matrixStack.rotate(new Quaternion(Vector3f.ZP, Minecraft.getInstance().player.ticksExisted, true));
        matrixStack.translate(-0.5f, -0.5f, 0f);
        RenderUtil.renderFlatTexture(0, 0, 0, 0, 1, 1, 256, 256, 0, TEXTURE, matrixStack, bufferIn, combinedLightIn, combinedOverlayIn);
    }
}
