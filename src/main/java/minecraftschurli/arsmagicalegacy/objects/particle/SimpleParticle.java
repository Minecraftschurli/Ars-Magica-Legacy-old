package minecraftschurli.arsmagicalegacy.objects.particle;

import com.mojang.brigadier.*;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.*;
import net.minecraft.network.*;
import net.minecraft.particles.*;
import net.minecraft.world.*;

import javax.annotation.*;
import java.util.*;

public class SimpleParticle extends SimpleAnimatedParticle {
    private static final Random rand = new Random();
    private float scaleX, scaleY, scaleZ;
    private int maxAge, age;
    public boolean hasGravity;
    public boolean hasMotion;

    public SimpleParticle(World world, double x, double y, double z, IAnimatedSprite sprite) {
        super(world, x, y, z, sprite, 0);
        scaleX = scaleY = scaleZ = 0.2f;
        hasGravity = false;
        hasMotion = true;
        particleRed = particleGreen = particleBlue = particleAlpha = 1;
        maxAge = 20 + rand.nextInt(20);
        particleGravity = 1;
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
    public IParticleRenderType getRenderType() {
        return IParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    public static class SimpleParticleType extends ParticleType<SimpleParticleType> implements IParticleData {
        public SimpleParticleType() {
            super(false, SimpleParticle.DESERIALIZER);
        }

        @Override
        public ParticleType<?> getType() {
            return this;
        }

        @Override
        public void write(PacketBuffer buffer) {

        }

        @Override
        public String getParameters() {
            return getRegistryName().toString();
        }
    }

    public static class Factory implements IParticleFactory<SimpleParticleType> {
        private final IAnimatedSprite spriteSet;

        public Factory(IAnimatedSprite p_i50607_1_) {
            this.spriteSet = p_i50607_1_;
        }

        @Nullable
        @Override
        public Particle makeParticle(SimpleParticleType typeIn, World worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new SimpleParticle(worldIn, x, y, z, spriteSet);
        }
    }

    public static final IParticleData.IDeserializer<SimpleParticleType> DESERIALIZER = new IParticleData.IDeserializer<SimpleParticleType>() {
        @Override
        public SimpleParticleType deserialize(ParticleType particleTypeIn, StringReader reader) {
            return (SimpleParticleType) particleTypeIn;
        }

        @Override
        public SimpleParticleType read(ParticleType particleTypeIn, PacketBuffer buffer) {
            return (SimpleParticleType) particleTypeIn;
        }
    };

    public SimpleParticle setScale(float scale) {
        scaleX = scale;
        scaleY = scale;
        scaleZ = scale;
        return this;
    }

    @Override
    public int getBrightnessForRender(float partialTick) {
        float f = (age + partialTick) / maxAge;
        if (f < 0.0F) f = 0.0F;
        if (f > 1.0F) f = 1.0F;
        int i = super.getBrightnessForRender(partialTick);
        int j = i & 0xff;
        int k = i >> 16 & 0xff;
        j += (int)(f * 15F * 16F);
        if (j > 240) j = 240;
        return j | k << 16;
    }

    @Override
    public void tick() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        if (this.age++ >= this.maxAge) this.setExpired();
        else {
            this.selectSpriteWithAge(this.field_217584_C);
            if (this.age > this.maxAge / 2) this.setAlphaF(1.0F - ((float)this.age - (float)(this.maxAge / 2)) / (float)this.maxAge);
            this.motionY += this.particleGravity;
            this.move(this.motionX, this.motionY, this.motionZ);
            if (this.onGround) {
                this.motionX *= 0.7F;
                this.motionZ *= 0.7F;
            }
        }
    }

    @Override
    public void renderParticle(BufferBuilder buffer, ActiveRenderInfo entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
        if(!world.isRemote || sprite == null) return;
        double x = this.prevPosX + (this.posX - this.prevPosX) * partialTicks - interpPosX;
        double y = this.prevPosY + (this.posY - this.prevPosY) * partialTicks - interpPosY;
        double z = this.prevPosZ + (this.posZ - this.prevPosZ) * partialTicks - interpPosZ;
        buffer.pos(x - rotationYZ * scaleX - rotationX * scaleX, y - rotationXZ * scaleY, z - rotationXY * scaleZ - rotationZ * scaleZ).tex(getMaxU(), getMaxV()).color(particleRed, particleGreen, particleBlue, particleAlpha).endVertex();
        buffer.pos(x - rotationYZ * scaleX + rotationX * scaleX, y + rotationXZ * scaleY, z - rotationXY * scaleZ + rotationZ * scaleZ).tex(getMaxU(), getMinV()).color(particleRed, particleGreen, particleBlue, particleAlpha).endVertex();
        buffer.pos(x + rotationYZ * scaleX + rotationX * scaleX, y + rotationXZ * scaleY, z + rotationXY * scaleZ + rotationZ * scaleZ).tex(getMinU(), getMinV()).color(particleRed, particleGreen, particleBlue, particleAlpha).endVertex();
        buffer.pos(x + rotationYZ * scaleX - rotationX * scaleX, y - rotationXZ * scaleY, z + rotationXY * scaleZ - rotationZ * scaleZ).tex(getMinU(), getMaxV()).color(particleRed, particleGreen, particleBlue, particleAlpha).endVertex();
//        super.renderParticle(buffer, entityIn, partialTicks, rotationX, rotationZ, rotationYZ, rotationXY, rotationXZ);
    }
}
