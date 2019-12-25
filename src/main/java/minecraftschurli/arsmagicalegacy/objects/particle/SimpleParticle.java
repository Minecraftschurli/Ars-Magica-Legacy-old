package minecraftschurli.arsmagicalegacy.objects.particle;

import com.mojang.brigadier.*;
import com.mojang.brigadier.exceptions.*;
import net.minecraft.client.particle.*;
import net.minecraft.network.*;
import net.minecraft.particles.*;
import net.minecraft.util.*;
import net.minecraft.world.*;

import java.util.*;

public class SimpleParticle extends SpriteTexturedParticle implements IParticleData {
    private static final Random rand = new Random();
    private float scaleX, scaleY, scaleZ;
    private int maxAge, age;
    private ResourceLocation texture;
    public boolean hasGravity;
    public boolean hasMotion;
    public boolean radiant;

    public SimpleParticle(World world, double x, double y, double z) {
        super(world, x, y, z, 0, 0, 0);
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

    public SimpleParticle setTexture(ResourceLocation location) {
        texture = location;
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
        return null;
    }

    @Override
    public ParticleType<?> getType() {
        return TYPE;
    }

    @Override
    public void write(PacketBuffer buffer) {
        buffer.writeDouble(posX);
        buffer.writeDouble(posY);
        buffer.writeDouble(posZ);
    }

    @Override
    public String getParameters() {
        return null;
    }

    public static class SimpleParticleType extends ParticleType<SimpleParticle> {
        public SimpleParticleType() {
            super(false, SimpleParticle.DESERIALIZER);
        }
    }

    public static final SimpleParticleType TYPE = new SimpleParticleType();

    public static final IDeserializer<SimpleParticle> DESERIALIZER = new IDeserializer<SimpleParticle>() {
        @Override
        public SimpleParticle deserialize(ParticleType particleTypeIn, StringReader reader) throws CommandSyntaxException {
            double x = reader.readDouble();
            reader.expect(' ');
            double y = reader.readDouble();
            reader.expect(' ');
            double z = reader.readDouble();
            reader.expect(' ');
            return new SimpleParticle(null, x, y, z);
        }

        @Override
        public SimpleParticle read(ParticleType particleTypeIn, PacketBuffer buffer) {
            return new SimpleParticle(null, buffer.readDouble(), buffer.readDouble(), buffer.readDouble());
        }
    };

    public SimpleParticle setScale(float scale) {
        scaleX = scale;
        scaleY = scale;
        scaleZ = scale;
        return this;
    }

    public SimpleParticle setColor(int color) {
        this.particleRed = ((color >> 16) & 0xFF) / 255.0f;
        this.particleGreen = ((color >> 8) & 0xFF) / 255.0f;
        this.particleBlue = (color & 0xFF) / 255.0f;
        return this;
    }

    public SimpleParticle setTexture(String tex) {
        texture = new ResourceLocation(tex);
        return this;
    }

    public SimpleParticle setTexture(String mod, String tex) {
        texture = new ResourceLocation(mod, tex);
        return this;
    }
}
