package minecraftschurli.arsmagicalegacy.objects.block.blackaurem;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import minecraftschurli.arsmagicalegacy.api.etherium.generator.EtheriumGeneratorTileEntity;
import minecraftschurli.arsmagicalegacy.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Matrix4f;
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

    private static final ResourceLocation texture = new ResourceLocation(ArsMagicaAPI.MODID, "textures/block/black_aurem.png");

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
        final float uScale = 1f / 0x100;
        final float vScale = 1f / 0x100;
        float zLevel = 0;
        float src_x = 0;
        float src_y = 0;
        float src_width = 256;
        float src_height = 256;
        matrixStack.translate(-0.5f, -0.5f, 0.5);
        matrixStack.scale(2f, 2f, 2f);
        matrixStack.translate(0.5f, 0.5f, 0f);
        matrixStack.rotate(Minecraft.getInstance().getRenderManager().getCameraOrientation());
        matrixStack.rotate(new Quaternion(Vector3f.ZP, Minecraft.getInstance().player.ticksExisted, true));
        matrixStack.translate(-0.5f, -0.5f, 0f);
        IVertexBuilder buffer = bufferIn.getBuffer(RenderUtil.getPaneRenderType(texture));
        MatrixStack.Entry matrixStackEntry = matrixStack.getLast();
        Matrix4f matrix = matrixStackEntry.getMatrix();
        buffer.pos(matrix, 0, 1, zLevel)
                .color(1f,1f,1f,1f)
                .tex((src_x) * uScale, (src_y + src_height) * vScale)
                .lightmap(combinedLightIn)
                .overlay(combinedOverlayIn)
                .endVertex();
        buffer.pos(matrix, 1, 1, zLevel)
                .color(1f,1f,1f,1f)
                .tex((src_x + src_width) * uScale, (src_y + src_height) * vScale)
                .lightmap(combinedLightIn)
                .overlay(combinedOverlayIn)
                .endVertex();
        buffer.pos(matrix, 1, 0, zLevel)
                .color(1f,1f,1f,1f)
                .tex((src_x + src_width) * uScale, (src_y) * vScale)
                .lightmap(combinedLightIn)
                .overlay(combinedOverlayIn)
                .endVertex();
        buffer.pos(matrix, 0, 0, zLevel)
                .color(1f,1f,1f,1f)
                .tex((src_x) * uScale, (src_y) * vScale)
                .lightmap(combinedLightIn)
                .overlay(combinedOverlayIn)
                .endVertex();
    }
}
