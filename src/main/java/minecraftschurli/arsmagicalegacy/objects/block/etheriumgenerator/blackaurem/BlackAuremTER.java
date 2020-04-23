package minecraftschurli.arsmagicalegacy.objects.block.etheriumgenerator.blackaurem;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import minecraftschurli.arsmagicalegacy.objects.block.etheriumgenerator.EtheriumGeneratorTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Quaternion;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

/**
 * @author Georg Burkl
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
        matrixStackIn.translate(0.5, 1, 0.5);
        RenderSystem.enableBlend();
        renderArsMagicaEffect(Tessellator.getInstance(), matrixStackIn);
        RenderSystem.disableBlend();
        matrixStackIn.pop();
        //fixme
    }

    private void renderArsMagicaEffect(Tessellator tessellator, MatrixStack matrixStack){
        matrixStack.rotate(new Quaternion(Vector3f.YP, 180 - Minecraft.getInstance().getRenderManager().getCameraOrientation().getY(), true));
        matrixStack.rotate(new Quaternion(Vector3f.XP, -Minecraft.getInstance().getRenderManager().getCameraOrientation().getX(), true));

        Minecraft.getInstance().getTextureManager().bindTexture(texture);
        RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        matrixStack.translate(0.0f, 0.25f, 0.0f);
        matrixStack.rotate(new Quaternion(Vector3f.ZP, Minecraft.getInstance().player.ticksExisted, true));
        matrixStack.scale((float) 1 * 2, (float) 1 * 2, (float) 1 * 2);
        matrixStack.translate(0.0f, -0.25f, 0.0f);
        renderSprite(tessellator);
    }

    private void renderSprite(Tessellator tessellator){

        float TLX = 0;
        float BRX = 1;
        float TLY = 0;
        float BRY = 1;

        float f4 = 1.0F;
        float f5 = 0.5F;
        float f6 = 0.25F;

        try{
            tessellator.getBuffer().begin(7, DefaultVertexFormats.POSITION_TEX);
            tessellator.getBuffer().pos(0.0F - f5, 0.0F - f6, 0.0D).tex(TLX, BRY).endVertex();
            tessellator.getBuffer().pos(f4 - f5, 0.0F - f6, 0.0D).tex(BRX, BRY).endVertex();
            tessellator.getBuffer().pos(f4 - f5, f4 - f6, 0.0D).tex(BRX, TLY).endVertex();
            tessellator.getBuffer().pos(0.0F - f5, f4 - f6, 0.0D).tex(TLX, TLY).endVertex();;
            tessellator.draw();
        } catch (Throwable ignored){}
    }
}
