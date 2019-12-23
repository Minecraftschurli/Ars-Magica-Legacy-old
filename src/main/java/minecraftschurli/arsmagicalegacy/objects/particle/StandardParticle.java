package minecraftschurli.arsmagicalegacy.objects.particle;

import com.mojang.blaze3d.platform.*;
import net.minecraft.client.*;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.entity.*;
import net.minecraft.network.*;
import net.minecraft.particles.*;
import net.minecraft.util.*;
import net.minecraft.world.*;

import java.util.*;

public class StandardParticle extends Particle implements IParticleData {
    private ResourceLocation particleTexture;
    private boolean ignoreMaxAge;
    private final List<ParticleController> controllers;
    private final ControllerComparator comparer;
    private float scaleX;
    private float scaleY;
    private float scaleZ;
    private boolean isRadiant;
    private boolean isBreak;
    private boolean isAffectedByGravity;
    private boolean ignoreNoControllers;
    private boolean doVelocityUpdates;

    @Override
    public ParticleType<?> getType() {
        return null;
    }

    @Override
    public void write(PacketBuffer packetBuffer) {
    }

    @Override
    public String getParameters() {
        return null;
    }

    public class ControllerComparator implements Comparator<ParticleController> {
        @Override
        public int compare(ParticleController o1, ParticleController o2) {
            return (o1.getPriority() > o2.getPriority() ? 1 : (o1 == o2 ? 0 : -1));
        }
    }

    public void setAge(int age) {
        this.age = age;
    }

    public StandardParticle(World world, double x, double y, double z){
        super(world, x, y, z, 0, 0, 0);
        particleRed = particleGreen = particleBlue = particleAlpha = 1.0F;
        scaleX = scaleY = scaleZ = 0.2f;
        this.ignoreMaxAge = this.isRadiant = this.isBreak = this.isAffectedByGravity = this.ignoreNoControllers = false;
        this.doVelocityUpdates = true;
        this.maxAge = 20 + rand.nextInt(20);
        this.particleGravity = 1;
        this.setRandomScale(0.1f, 0.3f);
        this.controllers = new ArrayList<>();
        this.comparer = new ControllerComparator();
    }

    public StandardParticle setGravity(){
        this.isAffectedByGravity = true;
        return this;
    }

    public StandardParticle setNoControllers(){
        this.ignoreNoControllers = true;
        return this;
    }
    public void setTexture(ResourceLocation texture) {
        this.particleTexture = texture;
    }
    public ResourceLocation getTexture() {
        return this.particleTexture;
    }

    public void setNoVelocityUpdates(){
        this.doVelocityUpdates = false;
    }

    public boolean isRadiant(){
        return isRadiant;
    }

    public boolean isBlockTexture(){
        return isBreak;
    }

    public void addOffset(double maxX, double maxY, double maxZ){
        double newX = this.posX + rand.nextDouble() * maxX - maxX / 2;
        double newY = this.posY + rand.nextDouble() * maxY - maxY / 2;
        double newZ = this.posZ + rand.nextDouble() * maxZ - maxZ / 2;
        this.setPosition(newX, newY, newZ);
    }

    public float getScaleX(){
        return this.scaleX;
    }

    public float getScaleY(){
        return this.scaleY;
    }

    public float getScaleZ(){
        return this.scaleZ;
    }

    public StandardParticle setRandomScale(float min, float max){
        this.setScale((rand.nextFloat() * (max - min)) + min);
        return this;
    }

    public void setScale(float scale){
        setScale(scale, scale, scale);
    }

    public void setScale(float scaleX, float scaleY, float scaleZ){
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        this.scaleZ = scaleZ;
    }

    public int getAge(){
        return this.age;
    }

    public int getParticleMaxAge(){
        return this.maxAge;
    }

    public void setMaxAge(int age){
        this.maxAge = age;
    }

    public void setIgnoreMaxAge(boolean ignore){
        this.ignoreMaxAge = ignore;
        this.age = 0;
    }

    public void setColorF(float r, float g, float b){
        this.particleRed = r;
        this.particleGreen = g;
        this.particleBlue = b;
    }

    public void setColorI(int color){
        this.particleRed = ((color >> 16) & 0xFF) / 255.0f;
        this.particleGreen = ((color >> 8) & 0xFF) / 255.0f;
        this.particleBlue = (color & 0xFF) / 255.0f;
    }

    public void setAlpha(float alpha){
        this.particleAlpha = alpha;
    }

    public float getRed(){
        return this.particleRed;
    }

    public float getGreen(){
        return this.particleGreen;
    }

    public float getBlue(){
        return this.particleBlue;
    }

    public float getAlpha(){
        return this.particleAlpha;
    }

    public void addController(ParticleController controller){
        controllers.add(controller);
        Collections.sort(controllers, comparer);
    }

    public void removeController(ParticleController controller){
        this.controllers.remove(controller);
    }

    public void clearControllers(){
        this.controllers.clear();
    }

    @Override
    public int getBrightnessForRender(float par1){
        float f = (age + par1) / maxAge;
        if (f < 0.0F) f = 0.0F;
        if (f > 1.0F) f = 1.0F;
        int i = super.getBrightnessForRender(par1);
        int j = i & 0xff;
        int k = i >> 16 & 0xff;
        j += (int)(f * 15F * 16F);
        if (j > 240) j = 240;
        return j | k << 16;
    }

    @Override
    public void tick(){
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        if (isAffectedByGravity) this.motionY -= 0.04D * this.particleGravity;
        if (doVelocityUpdates) this.move(this.motionX, this.motionY, this.motionZ);
        List<ParticleController> remove = new ArrayList<>();
        for (ParticleController pmc : controllers){
            if (pmc.getFinished()){
                remove.add(pmc);
                continue;
            }
            pmc.onUpdate(this.world);
            if (pmc.getExclusive()) break;
        }
        for (ParticleController pmc : remove) controllers.remove(pmc);
        if ((age++ > maxAge && !this.ignoreMaxAge) || (!ignoreNoControllers && controllers.size() == 0)) this.setExpired();
    }

    @Override
    public void renderParticle(BufferBuilder tessellator, ActiveRenderInfo ent, float partialframe, float cosyaw, float cospitch, float sinyaw, float sinsinpitch, float cossinpitch) {
        if (!this.world.isRemote) return;
        float f11 = (float)(this.prevPosX + (this.posX - this.prevPosX) * partialframe - interpPosX);
        float f12 = (float)(this.prevPosY + (this.posY - this.prevPosY) * partialframe - interpPosY);
        float f13 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * partialframe - interpPosZ);
        if (this.isRadiant) renderRadiant(Tessellator.getInstance(), partialframe);
        else {
            if (this.particleTexture == null) return;
            float scaleFactorX = this.getScaleX();
            float scaleFactorY = this.getScaleY();
            float scaleFactorZ = this.getScaleZ();
//            float min_u = this.particleTexture.getMinU();
//            float min_v = this.particleTexture.getMinV();
//            float max_u = this.particleTexture.getMaxU();
//            float max_v = this.particleTexture.getMaxV();
//            tessellator.pos(f11 - cosyaw * scaleFactorX - sinsinpitch * scaleFactorX, f12 - cospitch * scaleFactorY, f13 - sinyaw * scaleFactorZ - cossinpitch * scaleFactorZ)/*.tex(max_u, max_v)*/.color(this.getRed(), this.getGreen(), this.getBlue(), this.getAlpha()).endVertex();
//            tessellator.pos(f11 - cosyaw * scaleFactorX + sinsinpitch * scaleFactorX, f12 + cospitch * scaleFactorY, f13 - sinyaw * scaleFactorZ + cossinpitch * scaleFactorZ)/*.tex(max_u, min_v)*/.color(this.getRed(), this.getGreen(), this.getBlue(), this.getAlpha()).endVertex();
//            tessellator.pos(f11 + cosyaw * scaleFactorX + sinsinpitch * scaleFactorX, f12 + cospitch * scaleFactorY, f13 + sinyaw * scaleFactorZ + cossinpitch * scaleFactorZ)/*.tex(min_u, min_v)*/.color(this.getRed(), this.getGreen(), this.getBlue(), this.getAlpha()).endVertex();
//            tessellator.pos(f11 + cosyaw * scaleFactorX - sinsinpitch * scaleFactorX, f12 - cospitch * scaleFactorY, f13 + sinyaw * scaleFactorZ - cossinpitch * scaleFactorZ)/*.tex(min_u, max_v)*/.color(this.getRed(), this.getGreen(), this.getBlue(), this.getAlpha()).endVertex();
        }
    }

    @Override
    public IParticleRenderType getRenderType() {
        return null;
    }

    private void renderRadiant(Tessellator tessellator, float partialFrame) {
        RenderHelper.disableStandardItemLighting();
        float var4 = (this.getAge() + partialFrame) / this.getParticleMaxAge();
        float var5 = 0.0F;
        if (var4 > 0.8F) var5 = (var4 - 0.8F) / 0.2F;
        Random var6 = new Random(432L);
        float f11 = (float)(this.prevPosX + (this.posX - this.prevPosX) * partialFrame - Minecraft.getInstance().renderViewEntity.posX);
        float f12 = (float)(this.prevPosY + (this.posY - this.prevPosY) * partialFrame - Minecraft.getInstance().renderViewEntity.posY);
        float f13 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * partialFrame - Minecraft.getInstance().renderViewEntity.posZ);
        GlStateManager.pushMatrix();
        GlStateManager.translated(f11, f12, f13);
        GlStateManager.scaled(getScaleX(), getScaleY(), getScaleZ());
        for (int var7 = 0; var7 < 50.0F; ++var7) {
            GlStateManager.rotated(var6.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotated(var6.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotated(var6.nextFloat() * 360.0F, 0.0F, 0.0F, 1.0F);
            GlStateManager.rotated(var6.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotated(var6.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotated(var6.nextFloat() * 360.0F + var4 * 90.0F, 0.0F, 0.0F, 1.0F);
            tessellator.getBuffer().begin(6, DefaultVertexFormats.POSITION_COLOR);
            float var8 = var6.nextFloat() * 2.0F + 2.0F + var5 * 0.5F;
            float var9 = var6.nextFloat() * 2.0F + 1.0F + var5 * 2.0F;
            tessellator.getBuffer().pos(0.0D, 0.0D, 0.0D).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).endVertex();
            tessellator.getBuffer().pos(-0.866D * var9, var8, -0.5F * var9).color(this.particleRed, this.particleGreen, this.particleBlue, 0).endVertex();
            tessellator.getBuffer().pos(0.866D * var9, var8, -0.5F * var9).color(this.particleRed, this.particleGreen, this.particleBlue, 0).endVertex();
            tessellator.getBuffer().pos(0.0D, var8, 1.0F * var9).color(this.particleRed, this.particleGreen, this.particleBlue, 0).endVertex();
            tessellator.getBuffer().pos(-0.866D * var9, var8, -0.5F * var9).color(this.particleRed, this.particleGreen, this.particleBlue, 0).endVertex();
            tessellator.draw();
        }
        GlStateManager.popMatrix();
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

    public double getPrevPosX() {
        return prevPosX;
    }

    public double getPrevPosY() {
        return prevPosY;
    }

    public double getPrevPosZ() {
        return prevPosZ;
    }

    public void setPrevPos(double prevPosX, double prevPosY, double prevPosZ) {
        this.prevPosX = prevPosX;
        this.prevPosY = prevPosY;
        this.prevPosZ = prevPosZ;
    }

    public void pushPos() {
        setPrevPos(posX, posY, posZ);
    }

    public World getWorld() {
        return world;
    }

//    public void setParticleTextureByName(String name) {
//        this.particleTexture = AMParticleIcons.instance.getIconByName(name);
//    }
//
    public void setPosition(Entity entity) {
        setPosition(entity.posX, entity.posY, entity.posZ);
    }

    public boolean isCollided() {
        return canCollide;
    }

    public void addMotion(double x, double y, double z) {
        this.motionX = x;
        this.motionY = y;
        this.motionZ = z;
    }

    public double getMotionX() {
        return this.motionX;
    }

    public double getMotionY() {
        return this.motionY;
    }

    public double getMotionZ() {
        return this.motionZ;
    }
}
