package minecraftschurli.arsmagicalegacy.objects.particle;

import com.mojang.blaze3d.platform.*;
import com.mojang.blaze3d.vertex.*;
import minecraftschurli.arsmagicalegacy.util.*;
import net.minecraft.client.*;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import org.lwjgl.opengl.*;

import javax.annotation.*;

public class BeamParticle extends SpriteTexturedParticle {
    private double dX, dY, dZ, uX, uY, uZ;
    private float yaw, pitch, prevYaw, prevPitch;
    private boolean change;
    private boolean fppc;
    private int maxLengthAge;
    private BeamType type;
    private float length;
    private float rotate;

    public BeamParticle(World world, double x, double y, double z, double destX, double destY, double destZ, BeamParticleData data, TextureAtlasSprite sprite) {
        super(world, x, y, z);
        dX = destX;
        dY = destY;
        dZ = destZ;
        motionX = motionY = motionZ = 0;
        particleAlpha = data.getAlpha();
        particleRed = data.getRed();
        particleGreen = data.getGreen();
        particleBlue = data.getBlue();
        change = false;
        fppc = false;
        setSize(0.2f, 0.2f);
        lengthRotation();
        maxLengthAge = 10;
        type = BeamType.NORMAL;
        rotate = 30;
        maxAge = 10;
        this.sprite = sprite;
    }

    private void lengthRotation() {
        double deltaX = posX - dX;
        double deltaY = posY - dY;
        double deltaZ = posZ - dZ;
        length = MathHelper.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ);
        yaw = ((float) (Math.atan2(deltaX, deltaZ) * 180 / Math.PI));
        pitch = ((float) (Math.atan2(deltaY, MathHelper.sqrt(deltaX * deltaX + deltaZ * deltaZ)) * 180 / Math.PI));
    }

    public BeamParticle instant() {
        maxLengthAge = 1;
        return this;
    }

    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }

    public void setLocationAndTarget(double pX, double pY, double pZ, double tX, double tY, double tZ) {
        uX = pX;
        uY = pY;
        uZ = pZ;
        dX = tX;
        dY = tY;
        dZ = tZ;
        if(age > maxAge - 5) maxAge = age + 5;
        change = true;
    }

    private void storePrev() {
        prevPosX = posX;
        prevPosY = posY;
        prevPosZ = posZ;
        if(change) {
            posX = uX;
            posY = uY;
            posZ = uZ;
            if(fppc) {
                PlayerEntity player = Minecraft.getInstance().player;
                if(player != null) {
                    double rotYaw = player.rotationYaw * Math.PI / 180;
                    posX -= Math.cos(rotYaw) * 0.06f;
                    posZ -= Math.sin(rotYaw) * 0.06f;
                    posY += 0.06f;
                    prevPosX = posX;
                    prevPosY = posY;
                    prevPosZ = posZ;
                }
            }
            change = false;
        }
        prevYaw = yaw;
        prevPitch = pitch;
    }

    public BeamParticle setFPPC() {
        fppc = true;
        return this;
    }

    private void beamAging() {
        age++;
        if(age >= maxAge) setExpired();
    }

    public void setType(BeamType type) {
        this.type = type;
    }

    @Override
    public void tick() {
        storePrev();
        lengthRotation();
        beamAging();
    }

    @Override
    public void renderParticle(IVertexBuilder iVertexBuilder, ActiveRenderInfo activeRenderInfo, float v) {
        GL11.glPushMatrix();
        GlStateManager.alphaFunc(GL11.GL_GREATER, 0.003921569F);
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA.param, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA.param);
        float rot = (world.getDayTime() % (360 / rotate) + 1) * rotate;
        float size = age / maxLengthAge;
        if (size > 1) size = 1;
        float op = 0.4F;
        float widthMod = 1.0f;
        TextureAtlasSprite beamIcon = sprite;
        GL11.glTexParameterf(3553, 10242, 10497);
        GL11.glTexParameterf(3553, 10243, 10497);
        GL11.glTranslated(prevPosX + (posX - prevPosX) * v - posX, prevPosY + (posY - prevPosY) * v - posY, prevPosZ + (posZ - prevPosZ) * v - posZ);
        if (fppc) widthMod = 0.3f;
        GL11.glRotatef(90, 1, 0, 0);
        GL11.glRotatef(180 + prevYaw + Math.abs(yaw) - Math.abs(prevYaw), 0, 0, -1);
        GL11.glRotatef(pitch, 1, 0, 0);
        double offset1 = (-0.15D * widthMod) * size;
        double offset2 = (0.15D * widthMod) * size;
        GL11.glRotatef(rot, 0, 1, 0);
        int i = 5;
        float inc = 45;
//        if (ArsMagicaLegacy.config.LowGFX()){
//            i = 3;
//            inc = 90;
//        }else if (ArsMagicaLegacy.config.NoGFX()){
//            i = 1;
//            inc = 180;
//        }
        for (int t = 0; t < i; t++){
            Tessellator.getInstance().draw();
            iVertexBuilder.lightmap(7);
            double l = length * size * particleScale;
            GL11.glRotatef(inc, 0.0F, 1.0F, 0.0F);
            GlStateManager.clearCurrentColor();
			if (t % 2 == 0) GL11.glColor4f(particleRed, particleGreen, particleBlue, op);
			else GL11.glColor4f(1, 1, 1, 0.4f);
            iVertexBuilder.pos(offset1, l, 0).tex(beamIcon.getMaxU(), beamIcon.getMinV()).color(particleRed, particleGreen, particleBlue, op).endVertex();
            iVertexBuilder.pos(offset1, 0, 0).tex(beamIcon.getMaxU(), beamIcon.getMaxV()).color(particleRed, particleGreen, particleBlue, op).endVertex();
            iVertexBuilder.pos(offset2, 0, 0).tex(beamIcon.getMinU(), beamIcon.getMaxV()).color(particleRed, particleGreen, particleBlue, op).endVertex();
            iVertexBuilder.pos(offset2, l, 0).tex(beamIcon.getMinU(), beamIcon.getMinV()).color(particleRed, particleGreen, particleBlue, op).endVertex();
            Tessellator.getInstance().draw();
            iVertexBuilder.lightmap(7);
        }
        GL11.glPopMatrix();
    }

    @Override
    public IParticleRenderType getRenderType() {
        return IParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    public enum BeamType {
        NORMAL,
        DARK,
        LIGHT
    }

    public static class Factory implements IParticleFactory<BeamParticleData> {
        private TextureAtlasSprite sprite;
        public Factory(TextureAtlasSprite sprite) {
            this.sprite = sprite;
        }
        @Nullable
        @Override
        public Particle makeParticle(BeamParticleData beamParticleData, World world, double v, double v1, double v2, double v3, double v4, double v5) {
            return new BeamParticle(world, v, v1, v2, v3, v4, v5, beamParticleData, sprite);
        }
    }

    public void setColor(int color){
        particleRed = RenderUtils.getRed(color);
        particleGreen = RenderUtils.getGreen(color);
        particleBlue = RenderUtils.getBlue(color);
    }

    public void setColor(float r, float g, float b) {
        particleRed = r;
        particleGreen = g;
        particleBlue = b;
    }
}
