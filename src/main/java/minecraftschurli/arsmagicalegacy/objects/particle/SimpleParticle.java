package minecraftschurli.arsmagicalegacy.objects.particle;

import net.minecraft.client.particle.*;
import net.minecraft.world.*;

import javax.annotation.*;

public class SimpleParticle extends SpriteTexturedParticle {
    private final IAnimatedSprite animatedSprite;
    private float scaleX, scaleY, scaleZ;
    private int maxAge, age;
    public boolean hasGravity;
    public boolean hasMotion;

    public SimpleParticle(World world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, SimpleParticleData data, IAnimatedSprite sprite) {
        super(world, x, y, z, xSpeed, ySpeed, zSpeed);
        this.animatedSprite = sprite;
        scaleX = scaleY = scaleZ = 0.2f;
        hasGravity = false;
        hasMotion = true;
        particleRed = data.getRed();
        particleGreen = data.getGreen();
        particleBlue = data.getBlue();
        particleAlpha = data.getAlpha();
        maxAge = 20 + rand.nextInt(20);
        particleGravity = 1;
        selectSpriteWithAge(animatedSprite);
    }

    public SimpleParticle setScaleX(float x) {
        scaleX = x;
        return this;
    }

    public SimpleParticle setScaleY(float y) {
        scaleY = y;
        return this;
    }

    public SimpleParticle setScaleZ(float z) {
        scaleZ = z;
        return this;
    }

    public SimpleParticle setRed(float r) {
        particleRed = r;
        return this;
    }

    public SimpleParticle setGreen(float g) {
        particleGreen = g;
        return this;
    }

    public SimpleParticle setBlue(float b) {
        particleBlue = b;
        return this;
    }

    public SimpleParticle setAlpha(float a) {
        particleAlpha = a;
        return this;
    }

    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public float getScaleX() {
        return scaleX;
    }

    public float getScaleY() {
        return scaleY;
    }

    public float getScaleZ() {
        return scaleZ;
    }

    public float getRed() {
        return particleRed;
    }

    public float getBlue() {
        return particleBlue;
    }

    public float getGreen() {
        return particleGreen;
    }

    public float getAlpha() {
        return particleAlpha;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public int getAge() {
        return age;
    }

    @Override
    public void tick() {
        selectSpriteWithAge(animatedSprite);
        super.tick();
    }

    @Override
    public IParticleRenderType getRenderType() {
        return IParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    public SimpleParticle setScale(float scale) {
        scaleX = scale;
        scaleY = scale;
        scaleZ = scale;
        return this;
    }

    public static class Factory implements IParticleFactory<SimpleParticleData> {
        private final IAnimatedSprite spriteSet;

        public Factory(IAnimatedSprite p_i50477_1_) {
            this.spriteSet = p_i50477_1_;
        }

        @Nullable
        @Override
        public Particle makeParticle(SimpleParticleData typeIn, World worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new SimpleParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, typeIn, this.spriteSet);
        }
    }

    public void setColor(int color){
        particleRed = ((color >> 16) & 0xFF) / 255.0f;
        particleGreen = ((color >> 8) & 0xFF) / 255.0f;
        particleBlue = (color & 0xFF) / 255.0f;
    }

    public void setColor(float r, float g, float b) {
        particleRed = r;
        particleGreen = g;
        particleBlue = b;
    }
}
