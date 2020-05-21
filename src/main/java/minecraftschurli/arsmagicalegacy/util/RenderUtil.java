package minecraftschurli.arsmagicalegacy.util;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Matrix4f;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderState;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;

public final class RenderUtil {
//    private static final VertexFormat POSITION_TEX_NORMAL = new VertexFormat(ImmutableList .<VertexFormatElement>builder().add(DefaultVertexFormats.POSITION_3F).add(DefaultVertexFormats.TEX_2F).add(DefaultVertexFormats.NORMAL_3B).add(DefaultVertexFormats.PADDING_1B).build());
    private static final RenderState.TransparencyState TRANSLUCENT_TRANSPARENCY = new RenderState.TransparencyState("translucent_transparency", () -> {
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
    }, RenderSystem::disableBlend);
    private static final RenderState.AlphaState DEFAULT_ALPHA = new RenderState.AlphaState(0.003921569F);
    private static final RenderState.LightmapState LIGHTMAP_ENABLED = new RenderState.LightmapState(true);
    private static final RenderState.OverlayState OVERLAY_ENABLED = new RenderState.OverlayState(true);
    private static final RenderState.CullState CULL_DISABLED = new RenderState.CullState(false);
    private static final RenderState.DiffuseLightingState DIFFUSE_LIGHTING_ENABLED = new RenderState.DiffuseLightingState(true);

    public static void color(int color) {
        RenderSystem.color4f(ParticleUtil.getRed(color), ParticleUtil.getGreen(color), ParticleUtil.getBlue(color), 0.5F);
    }

    public static Vec3d copyVec(Vec3d vec) {
        return new Vec3d(vec.x, vec.y, vec.z);
    }

    public static void drawBox(float minX, float minY, float maxX, float maxY, float zLevel, float minU, float minV, float maxU, float maxV) {
        Tessellator t = Tessellator.getInstance();
        BufferBuilder wr = t.getBuffer();
        wr.begin(7, DefaultVertexFormats.POSITION_TEX);
        wr.pos(minX, minY + maxY, zLevel).tex(minU, maxV).endVertex();
        wr.pos(minX + maxX, minY + maxY, zLevel).tex(maxU, maxV).endVertex();
        wr.pos(minX + maxX, minY, zLevel).tex(maxU, minV).endVertex();
        wr.pos(minX, minY, zLevel).tex(minU, minV).endVertex();
        t.draw();
    }

    public static void drawTextInWorld(String text, double x, double y, double z, int color) {
        FontRenderer fontrenderer = Minecraft.getInstance().fontRenderer;
        float f = 0.0104166675f;
        RenderSystem.pushMatrix();
        RenderSystem.translatef((float) x, (float) y, (float) z);
        RenderSystem.normal3f(0, 1, 0);
        RenderSystem.rotatef(-Minecraft.getInstance().getRenderManager().getCameraOrientation().getY(), 0, 1, 0);
        RenderSystem.rotatef(Minecraft.getInstance().getRenderManager().getCameraOrientation().getX(), 1, 0, 0);
        RenderSystem.scalef(-f, -f, f);
        RenderSystem.scalef(0.5f, 0.5f, 0.5f);
        RenderSystem.disableTexture();
        RenderSystem.depthMask(false);
        RenderSystem.disableTexture();
        RenderSystem.enableTexture();
        Tessellator tessellator = Tessellator.getInstance();
        RenderSystem.disableTexture();
        tessellator.getBuffer().begin(7, DefaultVertexFormats.POSITION_COLOR);
        int j = fontrenderer.getStringWidth(text) / 2;
        tessellator.getBuffer().pos(-j - 1, -1, 0).color(0, 0, 0, 0.75F).endVertex();
        tessellator.getBuffer().pos(-j - 1, 8, 0).color(0, 0, 0, 0.75F).endVertex();
        tessellator.getBuffer().pos(j + 1, 8, 0).color(0, 0, 0, 0.75F).endVertex();
        tessellator.getBuffer().pos(j + 1, -1, 0).color(0, 0, 0, 0.75F).endVertex();
        tessellator.draw();
        RenderSystem.enableTexture();
        fontrenderer.drawString(text, -fontrenderer.getStringWidth(text) / 2f, 0, 553648127);
        RenderSystem.enableTexture();
        RenderSystem.depthMask(true);
        fontrenderer.drawString(text, -fontrenderer.getStringWidth(text) / 2f, 0, -1);
        RenderSystem.enableTexture();
        RenderSystem.disableTexture();
        RenderSystem.color4f(1, 1, 1, 1);
        RenderSystem.popMatrix();
    }

    public static void fractalLine2dd(double xStart, double yStart, double xEnd, double yEnd, float zLevel, int color, float displace, float fractalDetail) {
        fractalLine2df((float) xStart, (float) yStart, (float) xEnd, (float) yEnd, zLevel, color, displace, fractalDetail);
    }

    public static void fractalLine2df(float xStart, float yStart, float xEnd, float yEnd, float zLevel, int color, float displace, float fractalDetail) {
        if (displace < fractalDetail) line2d(xStart, yStart, xEnd, yEnd, zLevel, color);
        else {
            int mx = (int) ((xEnd + xStart) / 2);
            int my = (int) ((yEnd + yStart) / 2);
            mx += (Minecraft.getInstance().world.rand.nextFloat() - 0.5) * displace;
            my += (Minecraft.getInstance().world.rand.nextFloat() - 0.5) * displace;
            fractalLine2df(xStart, yStart, mx, my, zLevel, color, displace / 2f, fractalDetail);
            fractalLine2df(xEnd, yEnd, mx, my, zLevel, color, displace / 2f, fractalDetail);
        }
    }

    /*public static void renderBlockModel(TileEntity te, IBakedModel model, BlockState defaultState, MatrixStack matrixStack) {
        try {
            RenderSystem.pushMatrix();
            RenderSystem.translated(-te.getPos().getX(), -te.getPos().getY(), -te.getPos().getZ());
            Tessellator t = Tessellator.getInstance();
            BufferBuilder wr = t.getBuffer();
            wr.begin(7, DefaultVertexFormats.BLOCK);
            World world = te.getWorld();
            if (world == null)
                world = Minecraft.getInstance().world;
            if (world == null)
                return;
            BlockState state;
            state = world.getBlockState(te.getPos());
            if (state.getBlock() != defaultState.getBlock()) state = defaultState;
            Minecraft.getInstance().getBlockRendererDispatcher().getBlockModelRenderer().renderModel(world, model, state, te.getPos(), matrixStack, wr, true, world.rand, world.getSeed(), 0, te.getModelData());
            t.draw();
            RenderSystem.popMatrix();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }*/

    /*public static void renderRotatedModelGroup(TileEntity te, IBakedModel model, BlockState defaultState, Vec3d rotation) {
        RenderSystem.pushMatrix();
        RenderSystem.rotatef((float) rotation.x, 1, 0, 0);
        RenderSystem.rotatef((float) rotation.y, 1, 1, 0);
        RenderSystem.rotatef((float) rotation.z, 1, 0, 1);
//        renderBlockModel(te, model, defaultState);
        RenderSystem.popMatrix();
    }*/

    public static void gradientline2d(float src_x, float src_y, float dst_x, float dst_y, float zLevel, int color1, int color2) {
        gradientline2d(src_x, src_y, dst_x, dst_y, zLevel, color1, color2, 1f);
    }

    public static void gradientline2d(float src_x, float src_y, float dst_x, float dst_y, float zLevel, int color1, int color2, float width) {
        RenderSystem.pushMatrix();
        RenderSystem.disableTexture();
        RenderSystem.shadeModel(GL11.GL_SMOOTH);
        RenderSystem.lineWidth(width);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buf = tessellator.getBuffer();
        buf.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION_COLOR);
        buf.pos(src_x, src_y, zLevel).color(ParticleUtil.getRed(color1), ParticleUtil.getGreen(color1), ParticleUtil.getBlue(color1), 0xFF).endVertex();
        buf.pos(dst_x, dst_y, zLevel).color(ParticleUtil.getRed(color2), ParticleUtil.getGreen(color2), ParticleUtil.getBlue(color2), 0xFF).endVertex();
        tessellator.draw();
        RenderSystem.shadeModel(GL11.GL_FLAT);
        RenderSystem.enableTexture();
        RenderSystem.popMatrix();
    }

    public static void line2d(float xStart, float yStart, float xEnd, float yEnd, float zLevel, int color) {
        line2d(xStart, yStart, xEnd, yEnd, zLevel, color, 1f);
    }

    public static void line2d(float xStart, float yStart, float xEnd, float yEnd, float zLevel, int color, float width) {
        gradientline2d(xStart, yStart, xEnd, yEnd, zLevel, color, color, width);
    }

    public static void lineThick2d(float xStart, float yStart, float xEnd, float yEnd, float zLevel, int color) {
        line2d(xStart, yStart, xEnd, yEnd, zLevel, color, 4f);
    }

    public static void renderItemIntoGUI(ItemRenderer renderer, TextureManager textureManager, ItemStack stack, float x, float y, int zLevel) {
        IBakedModel bakedmodel = renderer.getItemModelWithOverrides(stack, null, null);
        RenderSystem.pushMatrix();
        textureManager.bindTexture(PlayerContainer.LOCATION_BLOCKS_TEXTURE);
        textureManager.getTexture(PlayerContainer.LOCATION_BLOCKS_TEXTURE).setBlurMipmapDirect(false, false);
        RenderSystem.enableRescaleNormal();
        RenderSystem.enableAlphaTest();
        RenderSystem.defaultAlphaFunc();
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.translatef(x, y, 100.0F + zLevel);
        RenderSystem.translatef(8.0F, 8.0F, 0.0F);
        RenderSystem.scalef(1.0F, -1.0F, 1.0F);
        RenderSystem.scalef(16.0F, 16.0F, 16.0F);
        MatrixStack matrixstack = new MatrixStack();
        IRenderTypeBuffer.Impl irendertypebuffer$impl = Minecraft.getInstance().getRenderTypeBuffers().getBufferSource();
        boolean flag = !bakedmodel.func_230044_c_();
        if (flag) RenderHelper.setupGuiFlatDiffuseLighting();
        renderer.renderItem(stack, ItemCameraTransforms.TransformType.GUI, false, matrixstack, irendertypebuffer$impl, 15728880, OverlayTexture.NO_OVERLAY, bakedmodel);
        irendertypebuffer$impl.finish();
        RenderSystem.enableDepthTest();
        if (flag) RenderHelper.setupGui3DDiffuseLighting();
        RenderSystem.disableAlphaTest();
        RenderSystem.disableRescaleNormal();
        RenderSystem.popMatrix();
    }

    public static void drawTexturedModalRectClassic(int dst_x, int dst_y, int src_x, int src_y, int dst_width, int dst_height, int src_width, int src_height, int zLevel) {
        final float uScale = 1f / 0x100;
        final float vScale = 1f / 0x100;
        Tessellator tessellator = Tessellator.getInstance();
        tessellator.getBuffer().begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        tessellator.getBuffer().pos(dst_x, dst_y + dst_height, zLevel).tex((src_x) * uScale, (src_y + src_height) * vScale).endVertex();
        tessellator.getBuffer().pos(dst_x + dst_width, dst_y + dst_height, zLevel).tex((src_x + src_width) * uScale, (src_y + src_height) * vScale).endVertex();
        tessellator.getBuffer().pos(dst_x + dst_width, dst_y, zLevel).tex((src_x + src_width) * uScale, (src_y) * vScale).endVertex();
        tessellator.getBuffer().pos(dst_x, dst_y, zLevel).tex((src_x) * uScale, (src_y) * vScale).endVertex();
        tessellator.draw();
    }

    public static RenderType getPaneRenderType(ResourceLocation texture) {
        RenderType.State rendertype$state = RenderType.State.getBuilder()
                .texture(new RenderState.TextureState(texture, false, false))
                .transparency(TRANSLUCENT_TRANSPARENCY)
                .diffuseLighting(DIFFUSE_LIGHTING_ENABLED)
                .alpha(DEFAULT_ALPHA)
                .cull(CULL_DISABLED)
                .lightmap(LIGHTMAP_ENABLED)
                .overlay(OVERLAY_ENABLED)
                .build(false);
        return RenderType.makeType("pane", DefaultVertexFormats.POSITION_TEX, 7, 256, true, true, rendertype$state);
    }

    public static void renderFlatTexture(int dst_x, int dst_y, int src_x, int src_y, int dst_width, int dst_height, int src_width, int src_height, int zLevel, ResourceLocation texture, MatrixStack matrixStack, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        final float uScale = 1f / 0x100;
        final float vScale = 1f / 0x100;
        IVertexBuilder buffer = bufferIn.getBuffer(getPaneRenderType(texture));
        MatrixStack.Entry matrixStackEntry = matrixStack.getLast();
        Matrix4f matrix = matrixStackEntry.getMatrix();
        buffer.pos(matrix, dst_x, dst_y + dst_height, zLevel)
                .color(1f, 1f, 1f, 1f)
                .tex((src_x) * uScale, (src_y + src_height) * vScale)
                .lightmap(combinedLightIn)
                .overlay(combinedOverlayIn)
                .endVertex();
        buffer.pos(matrix, dst_x + dst_width, dst_y + dst_height, zLevel)
                .color(1f, 1f, 1f, 1f)
                .tex((src_x + src_width) * uScale, (src_y + src_height) * vScale)
                .lightmap(combinedLightIn)
                .overlay(combinedOverlayIn)
                .endVertex();
        buffer.pos(matrix, dst_x + dst_width, dst_y, zLevel)
                .color(1f, 1f, 1f, 1f)
                .tex((src_x + src_width) * uScale, (src_y) * vScale)
                .lightmap(combinedLightIn)
                .overlay(combinedOverlayIn)
                .endVertex();
        buffer.pos(matrix, dst_x, dst_y, zLevel)
                .color(1f, 1f, 1f, 1f)
                .tex((src_x) * uScale, (src_y) * vScale)
                .lightmap(combinedLightIn)
                .overlay(combinedOverlayIn)
                .endVertex();
    }
}
