package minecraftschurli.arsmagicalegacy.objects.particle;

import com.mojang.brigadier.*;
import com.mojang.brigadier.exceptions.*;
import minecraftschurli.arsmagicalegacy.init.ModParticles;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.*;
import net.minecraft.network.*;
import net.minecraft.particles.*;
import net.minecraft.world.*;
import org.lwjgl.system.CallbackI;

import javax.annotation.Nullable;
import java.util.*;

public class SimpleParticle extends SimpleAnimatedParticle {
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
        selectSpriteWithAge(sprite);
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
}
