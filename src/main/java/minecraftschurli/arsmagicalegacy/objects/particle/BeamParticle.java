package minecraftschurli.arsmagicalegacy.objects.particle;

import com.mojang.blaze3d.platform.*;
import net.minecraft.client.*;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import org.lwjgl.opengl.*;

public class BeamParticle extends Particle implements IBeamParticle {
    int type;
    double dX, dY, dZ, updateX, updateY, updateZ;
    private float yaw, pitch, prevYaw, prevPitch;
    private float rotateSpeed = 5f;
    private float length;
    private int maxLengthAge = 10;
    private boolean positionChanged = false;
    private boolean firstPerson = false;

    public BeamParticle(World world, double x, double y, double z, double destX, double destY, double destZ){
        this(world, x, y, z, destX, destY, destZ, 0);
    }

    public BeamParticle(World world, double x, double y, double z, double destX, double destY, double destZ, int color){
        super(world, x, y, z);
        this.type = 0;
        this.dX = destX;
        this.dY = destY;
        this.dZ = destZ;
        this.motionX = 0D;
        this.motionY = 0D;
        this.motionZ = 0D;
        setSize(0.2f, 0.2f);
        calculateLengthAndRotation();
        this.prevYaw = this.yaw;
        this.prevPitch = this.pitch;
        rotateSpeed = 30;
        maxLengthAge = 10;
        this.maxAge = 10;
    }

    public BeamParticle setInstantSpawn(){
        this.maxLengthAge = 1;
        return this;
    }

    public void setMaxAge(int maxAge){
        this.maxAge = maxAge;
    }

    private void calculateLengthAndRotation(){
        float deltaX = (float)(this.posX - this.dX);
        float deltaY = (float)(this.posY - this.dY);
        float deltaZ = (float)(this.posZ - this.dZ);
        this.length = MathHelper.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ);
        double hDist = MathHelper.sqrt(deltaX * deltaX + deltaZ * deltaZ);
        this.yaw = ((float)(Math.atan2(deltaX, deltaZ) * 180.0D / 3.141592653589793D));
        this.pitch = ((float)(Math.atan2(deltaY, hDist) * 180.0D / 3.141592653589793D));
    }

    public void setBeamLocationAndTarget(double posX, double posY, double posZ, double targetX, double targetY, double targetZ){
        this.updateX = posX;
        this.updateY = posY;
        this.updateZ = posZ;
        this.dX = targetX;
        this.dY = targetY;
        this.dZ = targetZ;
        if (this.age > this.maxAge - 5) this.maxAge = this.age + 5;
        positionChanged = true;
    }

    private void storePrevInformation(){
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        if (this.positionChanged){
            this.posX = this.updateX;
            this.posY = this.updateY;
            this.posZ = this.updateZ;
            if (this.firstPerson){
                PlayerEntity player = Minecraft.getInstance().player;
                if (player != null){
                    float yaw = player.rotationYaw;
                    float rotationYaw = (float)(yaw * Math.PI / 180);
                    float offsetX = (float)Math.cos(rotationYaw) * 0.06f;
                    float offsetZ = (float)Math.sin(rotationYaw) * 0.06f;
                    this.posX -= offsetX;
                    this.posZ -= offsetZ;
                    this.posY += 0.06f;
                    this.prevPosX = this.posX;
                    this.prevPosY = this.posY;
                    this.prevPosZ = this.posZ;
                }
            }
            this.positionChanged = false;
        }

        this.prevYaw = this.yaw;
        this.prevPitch = this.pitch;
    }

    @Override
    public void setFirstPersonPlayerCast(){
        this.firstPerson = true;
    }

    private void handleAging(){
        this.age++;
        if (this.age >= this.maxAge) this.setExpired();
    }

    @Override
    public void setType(int type){
        this.type = type;
    }

    @Override
    public void tick(){
        storePrevInformation();
        calculateLengthAndRotation();
        handleAging();
    }

    public int getFXLayer(){
        return 2;
    }

    @Override
    public void renderParticle(BufferBuilder tessellator, ActiveRenderInfo ent, float par2, float par3, float par4, float par5, float par6, float par7){
        GL11.glPushMatrix();
//        Minecraft.getInstance().getRenderManager().textureManager.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        GlStateManager.alphaFunc(GL11.GL_GREATER, 0.003921569F);
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        float scaleFactor = 1.0F;
        float rot = this.world.getGameTime() % (360 / this.rotateSpeed) * this.rotateSpeed + this.rotateSpeed * par2;
        float size = (float)this.age / (float)this.maxLengthAge;
        if (size > 1) size = 1;
        float op = 0.4F;
        float widthMod = 1.0f;
        TextureAtlasSprite beamIcon = null;
//        switch (this.type){
//            case 1:
//                beamIcon = AMParticleIcons.instance.getHiddenIconByName("beam1");
//                break;
//            case 2:
//                beamIcon = AMParticleIcons.instance.getHiddenIconByName("beam2");
//                break;
//            default:
//                beamIcon = AMParticleIcons.instance.getHiddenIconByName("beam");
//        }
//
        GL11.glTexParameterf(3553, 10242, 10497.0F);
        GL11.glTexParameterf(3553, 10243, 10497.0F);
        float xx = (float)(this.prevPosX + (this.posX - this.prevPosX) * par2 - interpPosX);
        float yy = (float)(this.prevPosY + (this.posY - this.prevPosY) * par2 - interpPosY);
        float zz = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * par2 - interpPosZ);
        GL11.glTranslated(xx, yy, zz);
        if (firstPerson) widthMod = 0.3f;
        float deltaYaw = Math.abs(this.yaw) - Math.abs(this.prevYaw);
        float ry = this.prevYaw + (deltaYaw) * par2;
        float rp = this.prevPitch + (this.pitch - this.prevPitch) * par2;
        GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
        GL11.glRotatef(180.0F + ry, 0.0F, 0.0F, -1.0F);
        GL11.glRotatef(rp, 1.0F, 0.0F, 0.0F);
        double offset1 = (-0.15D * widthMod) * size;
        double offset2 = (0.15D * widthMod) * size;
        float endMod = 1.0F;
        double offset3 = (-0.15D * widthMod) * size * endMod;
        double offset4 = (0.15D * widthMod) * size * endMod;
        GL11.glRotatef(rot, 0.0F, 1.0F, 0.0F);
        int i = 5;
        float inc = 36.0F;
//        if (ArsMagica2.config.LowGFX()){
//            i = 3;
//            inc = 90;
//        }else if (ArsMagica2.config.NoGFX()){
//            i = 1;
//            inc = 180;
//        }
        for (int t = 0; t < i; t++){
            Tessellator.getInstance().draw();
            tessellator.begin(7, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
            double l = this.length * size * scaleFactor;
            double tl = beamIcon.getMinU();
            double br = beamIcon.getMaxU();
            double mU = beamIcon.getMaxV(); //-1.0F + var12 + t / 3.0F;
            double mV = beamIcon.getMinV(); //this.length * size * scaleFactor + mU;

            GL11.glRotatef(inc, 0.0F, 1.0F, 0.0F);
            GlStateManager.clearCurrentColor();
            int b = this.getBrightnessForRender(par7);
            int j = b >> 16 & 65535;
            int k = b & 65535;
            tessellator.pos(offset3, l, 0.0D).tex( br, mV).color(this.particleRed, this.particleGreen, this.particleBlue, op).lightmap(j, k).endVertex();
            tessellator.pos(offset1, 0.0D, 0.0D).tex( br, mU).color(this.particleRed, this.particleGreen, this.particleBlue, op).lightmap(j, k).endVertex();
            tessellator.pos(offset2, 0.0D, 0.0D).tex( tl, mU).color(this.particleRed, this.particleGreen, this.particleBlue, op).lightmap(j, k).endVertex();
            tessellator.pos(offset4, l, 0.0D).tex( tl, mV).color(this.particleRed, this.particleGreen, this.particleBlue, op).lightmap(j, k).endVertex();
            Tessellator.getInstance().draw();
            tessellator.begin(7, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
        }
        GL11.glPopMatrix();
    }

    @Override
    public IParticleRenderType getRenderType() {
        return null;
    }

    @Override
    public void setColor(float r, float g, float b){
        this.particleGreen = g;
        this.particleRed = r;
        this.particleBlue = b;
    }

    @Override
    public void setColor(int color){
        this.particleRed = ((color >> 16) & 0xFF) / 255.0F;
        this.particleGreen = ((color >> 8) & 0xFF) / 255.0F;
        this.particleBlue = (color & 0xFF) / 255.0F;
    }

    @Override
    public void setColor(int r, int g, int b){
        this.particleRed = r / 255.0f;
        this.particleGreen = g / 255.0f;
        this.particleBlue = b / 255.0f;
    }

    public double getPosX() {
        return posX;
    }

    public double getPosY() {
        return posY;
    }

    public double getPosZ() {
        return posZ;
    }
}
