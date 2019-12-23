package minecraftschurli.arsmagicalegacy.objects.particle;

import com.mojang.blaze3d.platform.*;
import minecraftschurli.arsmagicalegacy.*;
import net.minecraft.client.*;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.network.*;
import net.minecraft.particles.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import org.lwjgl.opengl.*;

import java.util.*;

public class BoltParticle extends Particle implements IParticleData {
    private int type = 0;
    private int overrideColor = -1;
    private BoltCommon main;

    public BoltParticle(World world, Vec3d jammervec, Vec3d targetvec){
        super(world, 0, 0, 0, 0, 0, 0);
        this.main = new BoltCommon(world, jammervec, targetvec);
        setupFromMain();
    }

    public BoltParticle(World world, Entity detonator, Entity target){
        super(world, 0, 0, 0, 0, 0, 0);
        this.main = new BoltCommon(world, detonator, target);
        setupFromMain();
    }

    public BoltParticle(World world, Entity detonator, Entity target, int speed){
        super(world, 0, 0, 0, 0, 0, 0);
        this.main = new BoltCommon(world, detonator, target, speed);
        setupFromMain();
    }

    public BoltParticle(World world, double x1, double y1, double z1, double x, double y, double z, int duration, float multi){
        super(world, 0, 0, 0, 0, 0, 0);
        this.main = new BoltCommon(world, x1, y1, z1, x, y, z, duration, multi);
        setupFromMain();
    }

    public BoltParticle(World world, double x1, double y1, double z1, double x, double y, double z, int duration, float multi, int speed){
        super(world, 0, 0, 0, 0, 0, 0);
        this.main = new BoltCommon(world, x1, y1, z1, x, y, z, duration, multi, speed);
        setupFromMain();
    }

    public BoltParticle(World world, double x1, double y1, double z1, double x, double y, double z, int duration){
        super(world, 0, 0, 0, 0, 0, 0);
        this.main = new BoltCommon(world, x1, y1, z1, x, y, z, duration, 1.0F);
        setupFromMain();
    }

    public BoltParticle(World world, double x1, double y1, double z1, double x, double y, double z){
        super(world, 0, 0, 0, 0, 0, 0);
        this.main = new BoltCommon(world, x1, y1, z1, x, y, z, 30, 1);
        setupFromMain();
    }

    private void setupFromMain(){
        this.age = this.main.particleMaxAge;
        setPosition(this.main.start.x, this.main.start.y, this.main.start.z);
        motionX = 0;
        motionY = 0;
        motionZ = 0;
    }

    public void defaultFractal(){
        this.main.defaultFractal();
    }

    public void fractal(int splits, float amount, float splitchance, float splitlength, float splitangle){
        this.main.fractal(splits, amount, splitchance, splitlength, splitangle);
    }

    public void finalizeBolt(){
        this.main.finalizeBolt();
//        Minecraft.getInstance().gameRenderer.add(this);
    }

    public void setOverrideColor(int overrideColor){
        this.overrideColor = overrideColor;
    }

    public void setSourceEntity(Entity entity){
        this.main.wrapper = entity;
    }

    public void setType(int type){
        this.type = type;
        this.main.type = type;
    }

    public void setDamage(int dmg){
        this.main.damage = dmg;
    }

    public void setNonLethal(){
        this.main.nonLethal = true;
    }

    public void setMultiplier(float m){
        this.main.multiplier = m;
    }

    @Override
    public void tick(){
        this.main.onUpdate();
        if (this.main.particleAge >= this.main.particleMaxAge) setExpired();
    }

    private static Vec3d getRelativeViewVector(Vec3d pos){
        PlayerEntity renderentity = ArsMagicaLegacy.proxy.getLocalPlayer();
        return new Vec3d((float)renderentity.posX - pos.x, (float)renderentity.posY - pos.y, (float)renderentity.posZ - pos.z);
    }

    private void renderBolt(BufferBuilder buffer, float partialframe, float cosyaw, float cospitch, float sinyaw, float cossinpitch, int pass){
        Vec3d playervec = new Vec3d(sinyaw * -cospitch, -cossinpitch / cosyaw, cosyaw * cospitch);
        float boltage = this.main.particleAge >= 0 ? this.main.particleAge / this.main.particleMaxAge : 0.0F;
        float mainalpha = 1.0F;
        if (pass == 0) mainalpha = (1.0F - boltage) * 0.9F;
        else if (pass == 1) mainalpha = 1.0F - boltage * 0.6F;
        else mainalpha = 1.0F - boltage * 0.3F;
        int renderlength = (int)((this.main.particleAge + partialframe + (int)(this.main.length * 3.0F)) / (int)(this.main.length * 3.0F) * this.main.numsegments0);
        for (Iterator<BoltCommon.Segment> iterator = this.main.segments.iterator(); iterator.hasNext(); ){
            BoltCommon.Segment rendersegment = iterator.next();
            if (rendersegment.segmentno <= renderlength){
                float width = 0.015F * (float)(getRelativeViewVector(rendersegment.startpoint.point).length() / 10 + 1) * (1 + rendersegment.light);
                if (width > 0.05F) width = 0.05F;
                if (pass == 1) width += 0.025f;
                Vec3d diff1 = null;//Vec3d.crossProduct(playervec, rendersegment.prevdiff).scale(width / rendersegment.sinprev);
                Vec3d diff2 = null;//Vec3d.crossProduct(playervec, rendersegment.nextdiff).scale(width / rendersegment.sinnext);
                Vec3d startvec = rendersegment.startpoint.point;
                Vec3d endvec = rendersegment.endpoint.point;
                float rx1 = (float)(startvec.x - interpPosX);
                float ry1 = (float)(startvec.y - interpPosY);
                float rz1 = (float)(startvec.z - interpPosZ);
                float rx2 = (float)(endvec.x - interpPosX);
                float ry2 = (float)(endvec.y - interpPosY);
                float rz2 = (float)(endvec.z - interpPosZ);
                buffer.pos(rx2 - diff2.x, ry2 - diff2.y, rz2 - diff2.z).tex(0.5D, 0).color(this.particleRed, this.particleGreen, this.particleBlue, mainalpha * rendersegment.light).endVertex();
                buffer.pos(rx1 - diff1.x, ry1 - diff1.y, rz1 - diff1.z).tex(0.5D, 0).color(this.particleRed, this.particleGreen, this.particleBlue, mainalpha * rendersegment.light).endVertex();
                buffer.pos(rx1 + diff1.x, ry1 + diff1.y, rz1 + diff1.z).tex(0.5D, 1.0D).color(this.particleRed, this.particleGreen, this.particleBlue, mainalpha * rendersegment.light).endVertex();
                buffer.pos(rx2 + diff2.x, ry2 + diff2.y, rz2 + diff2.z).tex(0.5D, 1.0D).color(this.particleRed, this.particleGreen, this.particleBlue, mainalpha * rendersegment.light).endVertex();
                if (rendersegment.next == null){
                    Vec3d roundend = rendersegment.endpoint.point.add(rendersegment.diff.normalize().scale(width));
                    float rx3 = (float)(roundend.x - interpPosX);
                    float ry3 = (float)(roundend.y - interpPosY);
                    float rz3 = (float)(roundend.z - interpPosZ);
                    buffer.pos(rx3 - diff2.x, ry3 - diff2.y, rz3 - diff2.z).tex(0, 0).color(this.particleRed, this.particleGreen, this.particleBlue, mainalpha * rendersegment.light).endVertex();
                    buffer.pos(rx2 - diff2.x, ry2 - diff2.y, rz2 - diff2.z).tex(0.5D, 0).color(this.particleRed, this.particleGreen, this.particleBlue, mainalpha * rendersegment.light).endVertex();
                    buffer.pos(rx2 + diff2.x, ry2 + diff2.y, rz2 + diff2.z).tex(0.5D, 1.0D).color(this.particleRed, this.particleGreen, this.particleBlue, mainalpha * rendersegment.light).endVertex();
                    buffer.pos(rx3 + diff2.x, ry3 + diff2.y, rz3 + diff2.z).tex(0, 1.0D).color(this.particleRed, this.particleGreen, this.particleBlue, mainalpha * rendersegment.light).endVertex();
                }
                if (rendersegment.prev == null){
                    Vec3d roundend = rendersegment.startpoint.point.subtract(rendersegment.diff.normalize().scale(width));
                    float rx3 = (float)(roundend.x - interpPosX);
                    float ry3 = (float)(roundend.y - interpPosY);
                    float rz3 = (float)(roundend.z - interpPosZ);
                    buffer.pos(rx1 - diff1.x, ry1 - diff1.y, rz1 - diff1.z).tex(0.5D, 0).color(this.particleRed, this.particleGreen, this.particleBlue, mainalpha * rendersegment.light).endVertex();
                    buffer.pos(rx3 - diff1.x, ry3 - diff1.y, rz3 - diff1.z).tex(0, 0).color(this.particleRed, this.particleGreen, this.particleBlue, mainalpha * rendersegment.light).endVertex();
                    buffer.pos(rx3 + diff1.x, ry3 + diff1.y, rz3 + diff1.z).tex(0, 1.0D).color(this.particleRed, this.particleGreen, this.particleBlue, mainalpha * rendersegment.light).endVertex();
                    buffer.pos(rx1 + diff1.x, ry1 + diff1.y, rz1 + diff1.z).tex(0.5D, 1.0D).color(this.particleRed, this.particleGreen, this.particleBlue, mainalpha * rendersegment.light).endVertex();
                }
            }
        }
    }

    @Override
    public void renderParticle(BufferBuilder worldRendererIn, ActiveRenderInfo entityIn, float partialTicks, float cosyaw, float cospitch, float sinyaw, float sinsinpitch, float cossinpitch) {
        Tessellator tessellator = Tessellator.getInstance();
        PlayerEntity renderentity = ArsMagicaLegacy.proxy.getLocalPlayer();
        int visibleDistance = 100;
        if (!Minecraft.getInstance().gameSettings.fancyGraphics) visibleDistance = 50;
//        if (renderentity.getDistance(this.posX, this.posY, this.posZ) > visibleDistance) return;
        GlStateManager.pushMatrix();
        GL11.glPushAttrib(GL11.GL_TEXTURE_BIT | GL11.GL_COLOR_BUFFER_BIT);
        GlStateManager.depthMask(false);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        this.particleRed = (this.particleGreen = this.particleBlue = 1.0F);
        switch (this.type){
            case 0:
                this.particleRed = 0.1F;
                this.particleGreen = 0.1F;
                this.particleBlue = 0.6F;
                GL11.glBlendFunc(770, 1);
                break;
            case 1:
                this.particleRed = 0.6F;
                this.particleGreen = 0.6F;
                this.particleBlue = 0.1F;
                GL11.glBlendFunc(770, 1);
                break;
            case 2:
                this.particleRed = 0.6F;
                this.particleGreen = 0.1F;
                this.particleBlue = 0.6F;
                GL11.glBlendFunc(770, 1);
                break;
            case 3:
                this.particleRed = 0.1F;
                this.particleGreen = 1.0F;
                this.particleBlue = 0.1F;
                GL11.glBlendFunc(770, 1);
                break;
            case 4:
                this.particleRed = 0.1F;
                this.particleGreen = 0.1F;
                this.particleBlue = 0.1F;
                GL11.glBlendFunc(770, 771);
                break;
            case 5:
                this.particleRed = 0.6F;
                this.particleGreen = 0.3F;
                this.particleBlue = 0.6F;
                GL11.glBlendFunc(770, 771);
                break;
            case 6:
                this.particleRed = 0.1f;
                this.particleGreen = 0.1f;
                this.particleBlue = 0.1f;
                GL11.glBlendFunc(770, 1);
                break;
        }
        if (overrideColor != -1){
            this.particleRed = ((overrideColor >> 16) & 0xFF) / 255.0f;
            this.particleGreen = ((overrideColor >> 8) & 0xFF) / 255.0f;
            this.particleBlue = (overrideColor & 0xFF) / 255.0f;
        }
        try{
            worldRendererIn.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        }catch (Throwable t){
            tessellator.draw();
            worldRendererIn.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        }
        renderBolt(worldRendererIn, partialTicks, cosyaw, cospitch, sinyaw, cossinpitch, 0);
        switch (this.type){
            case 0:
                this.particleRed = 1.0F;
                this.particleGreen = 0.6F;
                this.particleBlue = 1.0F;
                break;
            case 1:
                this.particleRed = 0.1F;
                this.particleGreen = 0.1F;
                this.particleBlue = 1.0F;
                break;
            case 2:
                this.particleRed = 0.0F;
                this.particleGreen = 0.0F;
                this.particleBlue = 0.0F;
                break;
            case 3:
                this.particleRed = 0.1F;
                this.particleGreen = 0.6F;
                this.particleBlue = 0.1F;
                break;
            case 4:
                this.particleRed = 0.6F;
                this.particleGreen = 0.1F;
                this.particleBlue = 0.1F;
                GL11.glBlendFunc(770, 771);
                break;
            case 5:
                this.particleRed = 1.0F;
                this.particleGreen = 1.0F;
                this.particleBlue = 0.1F;
                GL11.glBlendFunc(770, 771);
                break;
            case 6:
                this.particleRed = 0.6f;
                this.particleGreen = 0.1f;
                this.particleBlue = 0.6f;
                GL11.glBlendFunc(770, 1);
                break;
        }
        if (overrideColor != -1){
            this.particleRed = ((overrideColor >> 16) & 0xFF) / 255.0f;
            this.particleGreen = ((overrideColor >> 8) & 0xFF) / 255.0f;
            this.particleBlue = (overrideColor & 0xFF) / 255.0f;
        }
        renderBolt(worldRendererIn, partialTicks, cosyaw, cospitch, sinyaw, cossinpitch, 1);
        switch (this.type){
            case 0:
                this.particleRed = 1.0F;
                this.particleGreen = 0.6F;
                this.particleBlue = 1.0F;
                break;
            case 1:
                this.particleRed = 0.1F;
                this.particleGreen = 0.1F;
                this.particleBlue = 1.0F;
                break;
            case 2:
                this.particleRed = 0.0F;
                this.particleGreen = 0.0F;
                this.particleBlue = 0.0F;
                break;
            case 3:
                this.particleRed = 0.1F;
                this.particleGreen = 0.6F;
                this.particleBlue = 0.1F;
                break;
            case 4:
                this.particleRed = 0.1F;
                this.particleGreen = 0.1F;
                this.particleBlue = 0.1F;
                GL11.glBlendFunc(770, 771);
                break;
            case 5:
                this.particleRed = 1.0F;
                this.particleGreen = 1.0F;
                this.particleBlue = 0.1F;
                GL11.glBlendFunc(770, 771);
                break;
            case 6:
                this.particleRed = 0.6f;
                this.particleGreen = 0.1f;
                this.particleBlue = 0.6f;
                GL11.glBlendFunc(770, 1);
                break;
        }
        if (overrideColor != -1){
            this.particleRed = ((overrideColor >> 16) & 0xFF) / 255.0f;
            this.particleGreen = ((overrideColor >> 8) & 0xFF) / 255.0f;
            this.particleBlue = (overrideColor & 0xFF) / 255.0f;
        }
        renderBolt(worldRendererIn, partialTicks, cosyaw, cospitch, sinyaw, cossinpitch, 2);
        try{
            tessellator.draw();
            worldRendererIn.begin(7, DefaultVertexFormats.POSITION_TEX);
        }catch (Throwable ignored){}
        GlStateManager.disableBlend();
        GL11.glDepthMask(true);
        GL11.glPopAttrib();
        GL11.glPopMatrix();
    }

    @Override
    public IParticleRenderType getRenderType() {
        return null;
    }

    public int getRenderPass(){
        return 2;
    }

    @Override
    public ParticleType<?> getType() {
        return null;
    }

    @Override
    public void write(PacketBuffer buffer) {

    }

    @Override
    public String getParameters() {
        return null;
    }
    public double getX() {
        return posX;
    }
    public double getY() {
        return posY;
    }
    public double getZ() {
        return posZ;
    }
    public double getMotionX() {
        return motionX;
    }
    public double getMotionY() {
        return motionY;
    }
    public double getMotionZ() {
        return motionZ;
    }
}
