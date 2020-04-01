package minecraftschurli.arsmagicalegacy.objects.particle;

import minecraftschurli.arsmagicalegacy.util.RenderUtil;
import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.SpriteTexturedParticle;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class SimpleParticle extends SpriteTexturedParticle {
    private final IAnimatedSprite animatedSprite;
    public boolean hasGravity;
    public boolean hasMotion;
    private float scaleX, scaleY, scaleZ;
    private int maxAge, age;

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

    public float getScaleX() {
        return scaleX;
    }

    public SimpleParticle setScaleX(float x) {
        scaleX = x;
        return this;
    }

    public float getScaleY() {
        return scaleY;
    }

    public SimpleParticle setScaleY(float y) {
        scaleY = y;
        return this;
    }

    public float getScaleZ() {
        return scaleZ;
    }

    public SimpleParticle setScaleZ(float z) {
        scaleZ = z;
        return this;
    }

    public float getRed() {
        return particleRed;
    }

    public SimpleParticle setRed(float r) {
        particleRed = r;
        return this;
    }

    public float getBlue() {
        return particleBlue;
    }

    public SimpleParticle setBlue(float b) {
        particleBlue = b;
        return this;
    }

    public float getGreen() {
        return particleGreen;
    }

    public SimpleParticle setGreen(float g) {
        particleGreen = g;
        return this;
    }

    public float getAlpha() {
        return particleAlpha;
    }

    public SimpleParticle setAlpha(float a) {
        particleAlpha = a;
        return this;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
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

    public void setColor(int color) {
        particleRed = RenderUtil.getRed(color);
        particleGreen = RenderUtil.getGreen(color);
        particleBlue = RenderUtil.getBlue(color);
    }

    public void setColor(float r, float g, float b) {
        particleRed = r;
        particleGreen = g;
        particleBlue = b;
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
}
