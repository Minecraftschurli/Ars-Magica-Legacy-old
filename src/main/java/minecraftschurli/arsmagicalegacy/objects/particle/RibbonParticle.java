package minecraftschurli.arsmagicalegacy.objects.particle;

import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.*;
import net.minecraft.network.*;
import net.minecraft.particles.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import net.minecraft.world.gen.*;
import org.lwjgl.opengl.*;

import java.util.*;

public class RibbonParticle extends Particle implements IParticleData {
    int curveCount, curveResolution, stepId, movement, ribbonColor;
    float ribbonWidth, noiseStep, maxSeparation, ribbonSeparation, noisePos;
    Vector<Vec3d> pts;
    LinkedList<RibbonCurve> curves;
    PerlinNoiseGenerator noise;
    RibbonCurve currentCurve;
    Vec3d ribbonTarget;
    public RibbonParticle(World world, double x, double y, double z, float width, int color){
        super(world, x, y, z, 0.0, 0.0, 0.0);
        this.setPosition(x, y, z);
        motionX = 0;
        motionY = 0;
        motionZ = 0;
        noise = new PerlinNoiseGenerator(new Random(), 1);
        curves = new LinkedList<>();
        pts = new Vector<>();
        ribbonColor = color;
        ribbonWidth = width;
        stepId = 0;
        ribbonTarget = new Vec3d(random(-movement, movement), random(-movement, movement), random(-movement, movement));
        ribbonSeparation = lerp(-maxSeparation, maxSeparation, (float)noise.noiseAt(noisePos += noiseStep, 0, false));
        pts.addElement(getRandPt());
        pts.addElement(getRandPt());
        pts.addElement(getRandPt());
        this.age = 0;
        this.maxAge = 200;
        addRibbonCurve();
    }

    @Override
    public void renderParticle(BufferBuilder par1Tessellator, ActiveRenderInfo ent, float partialframe, float cosyaw, float cospitch, float sinyaw, float sinsinpitch, float cossinpitch){
        GL11.glPushAttrib(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glTexEnvf(GL11.GL_TEXTURE_ENV, GL11.GL_TEXTURE_ENV_MODE, GL11.GL_MODULATE);
        GL11.glPushMatrix();
        GL11.glEnable(32826 /*GL_RESCALE_NORMAL_EXT*/);
//        double d = (this.prevPosX + (this.posX - this.prevPosX) * partialframe) - Minecraft.getInstance().getRenderManager().viewerPosX;
//        double d1 = (this.prevPosY + (this.posY - this.prevPosY) * partialframe) - Minecraft.getInstance().getRenderManager().viewerPosY;
//        double d2 = (this.prevPosZ + (this.posZ - this.prevPosZ) * partialframe) - Minecraft.getInstance().getRenderManager().viewerPosZ;
//        GL11.glTranslatef((float)d, (float)d1, (float)d2);
        GL11.glColor4f(1, 1, 1, 1f);
        draw();
        GL11.glPopMatrix();
        GL11.glPopAttrib();
    }

    @Override
    public IParticleRenderType getRenderType() {
        return null;
    }

    public int getFXLayer(){
        return 2;
    }

    void draw(){
        ribbonTarget = new Vec3d(random(-movement, movement), random(-movement, movement), random(-movement, movement));
        ribbonSeparation = lerp(-maxSeparation, maxSeparation, noise.noiseAt(noisePos += noiseStep, 0, false));
        currentCurve.addSegment();
        int size = curves.size();
        if (size > curveCount - 1){
            RibbonCurve c = curves.get(0);
            c.removeSegment();
        }
        stepId++;
        if (stepId > curveResolution) addRibbonCurve();
        for (RibbonCurve curve : curves) curve.draw();
    }

    private float random(float min, float max){
        return (float)((Math.random() * max) - min);
    }

    private float lerp(float start, float stop, double amount){
        return (float)(start + ((stop - start) * amount));
    }

    void addRibbonCurve(){
        pts.addElement(getRandPt());
        Vec3d nextPt = pts.elementAt(pts.size() - 1);
        Vec3d curPt = pts.elementAt(pts.size() - 2);
        Vec3d lastPt = pts.elementAt(pts.size() - 3);
        Vec3d lastMidPt = new Vec3d((curPt.getX() + lastPt.getX()) / 2, (curPt.getY() + lastPt.getY()) / 2, (curPt.getZ() + lastPt.getZ()) / 2);
        Vec3d midPt = new Vec3d((curPt.getX() + nextPt.getX()) / 2, (curPt.getY() + nextPt.getY()) / 2, (curPt.getZ() + nextPt.getZ()) / 2);
        float width = 0.2f;
        currentCurve = new RibbonCurve(lastMidPt, midPt, curPt, width, curveResolution, ribbonColor);
        curves.add(currentCurve);
        if (curves.size() > curveCount) curves.removeFirst();
        stepId = 0;
    }

    Vec3d getRandPt(){
        return new Vec3d(ribbonTarget.getX() + random(-ribbonSeparation, ribbonSeparation), ribbonTarget.getY() + random(-ribbonSeparation, ribbonSeparation), ribbonTarget.getZ() + random(-ribbonSeparation, ribbonSeparation));
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
}
