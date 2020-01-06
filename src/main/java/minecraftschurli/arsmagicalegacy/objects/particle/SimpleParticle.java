package minecraftschurli.arsmagicalegacy.objects.particle;

import com.mojang.blaze3d.platform.*;
import com.mojang.brigadier.*;
import net.minecraft.client.*;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.vertex.*;
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
    public boolean radiant;

    public SimpleParticle(World world, double x, double y, double z, IAnimatedSprite sprite) {
        super(world, x, y, z, sprite, 0);
        scaleX = scaleY = scaleZ = 0.2f;
        hasGravity = false;
        hasMotion = true;
        radiant = false;
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
        prevPosX = posX;
        prevPosY = posY;
        prevPosZ = posZ;
        if (hasGravity) motionY -= 0.04d * particleGravity;
        move(motionX, motionY, motionZ);
        if (age++ > maxAge) setExpired();
    }

    @Override
    public void renderParticle(BufferBuilder buffer, ActiveRenderInfo entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
        if (!world.isRemote) return;
        if (radiant) {
            RenderHelper.disableStandardItemLighting();
            float f0 = (age + partialTicks) / maxAge;
            float f1 = 0;
            if (f0 > 0.8f) f1 = (f0 - 0.8f) / 0.2f;
            Random r = new Random(432);
            float x = (float)(prevPosX + (posX - prevPosX) * partialTicks - Minecraft.getInstance().player.posX);
            float y = (float)(prevPosY + (posY - prevPosY) * partialTicks - Minecraft.getInstance().player.posY);
            float z = (float)(prevPosZ + (posZ - prevPosZ) * partialTicks - Minecraft.getInstance().player.posZ);
            GlStateManager.pushMatrix();
            GlStateManager.translated(x, y, z);
            GlStateManager.scaled(scaleX, scaleY, scaleZ);
            for (int i = 0; i < 50.0F; ++i){
                GlStateManager.rotated(r.nextFloat() * 360, 1, 0, 0);
                GlStateManager.rotated(r.nextFloat() * 360, 0, 1, 0);
                GlStateManager.rotated(r.nextFloat() * 360, 0, 0, 1);
                GlStateManager.rotated(r.nextFloat() * 360, 1, 0, 0);
                GlStateManager.rotated(r.nextFloat() * 360, 0, 1, 0);
                GlStateManager.rotated(r.nextFloat() * 360 + f0 * 90, 0, 0, 1);
                buffer.begin(6, DefaultVertexFormats.POSITION_COLOR);
                float f2 = r.nextFloat() * 2 + 2 + f1 * 0.5f;
                float f3 = r.nextFloat() * 2 + 1 + f1 * 2;
                buffer.pos(0, 0, 0).color(particleRed, particleGreen, particleBlue, particleAlpha).endVertex();
                buffer.pos(-0.866 * f3, f2, -0.5f * f3).color(particleRed, particleGreen, particleBlue, 0).endVertex();
                buffer.pos(0.866 * f3, f2, -0.5f * f3).color(particleRed, particleGreen, particleBlue, 0).endVertex();
                buffer.pos(0, f2, 1 * f3).color(particleRed, particleGreen, particleBlue, 0).endVertex();
                buffer.pos(-0.866d * f3, f2, -0.5f * f3).color(particleRed, particleGreen, particleBlue, 0).endVertex();
                Tessellator.getInstance().draw();
            }
            GlStateManager.popMatrix();
        } else {
            if (sprite == null) return;
            float x = (float)(prevPosX + (posX - prevPosX) * partialTicks - interpPosX);
            float y = (float)(prevPosY + (posY - prevPosY) * partialTicks - interpPosY);
            float z = (float)(prevPosZ + (posZ - prevPosZ) * partialTicks - interpPosZ);
            buffer.pos(x - rotationX * scaleX - rotationXY * scaleX, y - rotationXZ * scaleY, z - rotationZ * scaleZ - rotationYZ * scaleZ).tex(sprite.getMaxU(), sprite.getMaxV()).color(particleRed, particleGreen, particleBlue, particleAlpha).endVertex();
            buffer.pos(x - rotationX * scaleX + rotationXY * scaleX, y + rotationXZ * scaleY, z - rotationZ * scaleZ + rotationYZ * scaleZ).tex(sprite.getMaxU(), sprite.getMinV()).color(particleRed, particleGreen, particleBlue, particleAlpha).endVertex();
            buffer.pos(x + rotationX * scaleX + rotationXY * scaleX, y + rotationXZ * scaleY, z + rotationZ * scaleZ + rotationYZ * scaleZ).tex(sprite.getMinU(), sprite.getMinV()).color(particleRed, particleGreen, particleBlue, particleAlpha).endVertex();
            buffer.pos(x + rotationX * scaleX - rotationXY * scaleX, y - rotationXZ * scaleY, z + rotationZ * scaleZ - rotationYZ * scaleZ).tex(sprite.getMinU(), sprite.getMaxV()).color(particleRed, particleGreen, particleBlue, particleAlpha).endVertex();
        }
    }
}
