package minecraftschurli.arsmagicalegacy.util;

import com.mojang.blaze3d.systems.*;
import minecraftschurli.arsmagicalegacy.objects.particle.*;
import net.minecraft.block.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.model.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.particles.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import net.minecraftforge.fml.*;
import org.lwjgl.opengl.*;

import java.util.*;

public class RenderUtils {
    public static Vec3d copyVec(Vec3d vec) {
        return new Vec3d(vec.x, vec.y, vec.z);
    }

    public static void addParticle(World world, RegistryObject<ParticleType<SimpleParticleData>> type, float r, float g, float b, double x, double y, double z, float xSpeed, float ySpeed, float zSpeed) {
        world.addParticle(new SimpleParticleData(type.get(), r, g, b, 1), x, y + 1.5, z, xSpeed, ySpeed, zSpeed);
    }

    public static void addParticle(World world, RegistryObject<ParticleType<SimpleParticleData>> type, int color, double x, double y, double z, float xSpeed, float ySpeed, float zSpeed) {
        world.addParticle(new SimpleParticleData(type.get(), getRed(color), getGreen(color), getBlue(color), 1), x, y + 1.5, z, xSpeed, ySpeed, zSpeed);
    }

    public static void addParticle(World world, RegistryObject<ParticleType<SimpleParticleData>> type, float r, float g, float b, double x, double y, double z) {
        world.addParticle(new SimpleParticleData(type.get(), r, g, b, 1), x, y + 1.5, z, 0, 0, 0);
    }

    public static void addParticle(World world, RegistryObject<ParticleType<SimpleParticleData>> type, int color, double x, double y, double z) {
        world.addParticle(new SimpleParticleData(type.get(), getRed(color), getGreen(color), getBlue(color), 1), x, y + 1.5, z, 0, 0, 0);
    }

    private static Random rand = new Random();

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

    public static float getRed(int color) {
        return ((color & 0xFF0000) >> 16) / 255;
    }

    public static float getGreen(int color) {
        return ((color & 0x00FF00) >> 8) / 255;
    }

    public static float getBlue(int color) {
        return (color & 0x0000FF) / 255;
    }

    public static void color(int color) {
        GL11.glColor4f(getRed(color), getGreen(color), getBlue(color), 0.5F);
    }

    public static int getColor(float r, float g, float b) {
        int red = (int) (r * 255f) << 16;
        int green = (int) (g * 255f) << 8;
        int blue = (int) (b * 255f);
        return red + green + blue;
    }

    public static void line2d(float xStart, float yStart, float xEnd, float yEnd, float zLevel, int color) {
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        RenderSystem.enableDepthTest();
        GL11.glLineWidth(1f);
        GL11.glColor3d(getRed(color), getGreen(color), getBlue(color));
        GL11.glBegin(GL11.GL_LINES);
        GL11.glVertex3f(xStart, yStart, zLevel);
        GL11.glVertex3f(xEnd, yEnd, zLevel);
        GL11.glEnd();
        GL11.glColor3d(1, 1, 1);
        RenderSystem.disableDepthTest();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
    }

    public static void lineThick2d(float xStart, float yStart, float xEnd, float yEnd, float zLevel, int color) {
        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        RenderSystem.enableDepthTest();
        GL11.glLineWidth(4f);
        GL11.glColor3d(((color & 0xFF0000) >> 16) / 255, ((color & 0x00FF00) >> 8) / 255, (color & 0x0000FF) / 255);
        GL11.glBegin(GL11.GL_LINES);
        GL11.glVertex3f(xStart, yStart, zLevel);
        GL11.glVertex3f(xEnd, yEnd, zLevel);
        GL11.glEnd();
        GL11.glColor3d(1, 1, 1);
        RenderSystem.disableDepthTest();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glPopMatrix();
    }

    public static void fractalLine2df(float xStart, float yStart, float xEnd, float yEnd, float zLevel, int color, float displace, float fractalDetail) {
        if (displace < fractalDetail) line2d(xStart, yStart, xEnd, yEnd, zLevel, color);
        else {
            int mid_x = (int) ((xEnd + xStart) / 2);
            int mid_y = (int) ((yEnd + yStart) / 2);
            mid_x += (rand.nextFloat() - 0.5) * displace;
            mid_y += (rand.nextFloat() - 0.5) * displace;
            fractalLine2df(xStart, yStart, mid_x, mid_y, zLevel, color, displace / 2f, fractalDetail);
            fractalLine2df(xEnd, yEnd, mid_x, mid_y, zLevel, color, displace / 2f, fractalDetail);
        }
    }

    public static void fractalLine2dd(double xStart, double yStart, double xEnd, double yEnd, float zLevel, int color, float displace, float fractalDetail) {
        fractalLine2df((float) xStart, (float) yStart, (float) xEnd, (float) yEnd, zLevel, color, displace, fractalDetail);
    }

    public static void drawTextInWorldAtOffset(String text, double x, double y, double z, int color) {
        FontRenderer fontrenderer = Minecraft.getInstance().fontRenderer;
        float f = 0.0104166675f;
        GL11.glPushMatrix();
        GL11.glTranslatef((float) x, (float) y, (float) z);
        GL11.glNormal3f(0, 1, 0);
        GL11.glRotatef(-Minecraft.getInstance().getRenderManager().getCameraOrientation().getY(), 0, 1, 0);
        GL11.glRotatef(Minecraft.getInstance().getRenderManager().getCameraOrientation().getX(), 1, 0, 0);
        GL11.glScalef(-f, -f, f);
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDepthMask(false);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        Tessellator tessellator = Tessellator.getInstance();
        byte b0 = 0;
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        tessellator.getBuffer().begin(7, DefaultVertexFormats.POSITION_COLOR);
        int j = fontrenderer.getStringWidth(text) / 2;
        tessellator.getBuffer().pos(-j - 1, -1 + b0, 0).color(0, 0, 0, 0.75F).endVertex();
        tessellator.getBuffer().pos(-j - 1, 8 + b0, 0).color(0, 0, 0, 0.75F).endVertex();
        tessellator.getBuffer().pos(j + 1, 8 + b0, 0).color(0, 0, 0, 0.75F).endVertex();
        tessellator.getBuffer().pos(j + 1, -1 + b0, 0).color(0, 0, 0, 0.75F).endVertex();
        tessellator.draw();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        fontrenderer.drawString(text, -fontrenderer.getStringWidth(text) / 2, b0, 553648127);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(true);
        fontrenderer.drawString(text, -fontrenderer.getStringWidth(text) / 2, b0, -1);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glColor4f(1, 1, 1, 1);
        GL11.glPopMatrix();
    }

    public static void renderRotatedModelGroup(TileEntity te, IBakedModel model, BlockState defaultState, Vec3d rotation) {
        RenderSystem.pushMatrix();
        RenderSystem.rotatef((float)rotation.x, 1, 0, 0);
        RenderSystem.rotatef((float)rotation.y, 1, 1, 0);
        RenderSystem.rotatef((float)rotation.z, 1, 0, 1);
        renderBlockModel(te, model, defaultState);
        RenderSystem.popMatrix();
    }

    public static void renderBlockModel(TileEntity te, IBakedModel model, BlockState defaultState) {
        try {
            RenderSystem.pushMatrix();
            RenderSystem.translated(-te.getPos().getX(), -te.getPos().getY(), -te.getPos().getZ());
            Tessellator t = Tessellator.getInstance();
            BufferBuilder wr = t.getBuffer();
            wr.begin(7, DefaultVertexFormats.BLOCK);
            World world = te.getWorld();
            if (world == null)
                world = Minecraft.getInstance().world;
            BlockState state = null;
            if (world != null) state = world.getBlockState(te.getPos());
            if (state.getBlock() != defaultState.getBlock()) state = defaultState;
//            Minecraft.getInstance().getBlockRendererDispatcher().getBlockModelRenderer().renderModel(world, model, state, te.getPos(), wr, true, world.rand, world.getSeed(), te.getModelData());
            t.draw();
            RenderSystem.popMatrix();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

}
