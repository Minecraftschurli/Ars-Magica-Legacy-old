package minecraftschurli.arsmagicalegacy.objects.particle;

import com.mojang.brigadier.*;
import com.mojang.brigadier.exceptions.*;
import net.minecraft.network.*;
import net.minecraft.particles.*;
import net.minecraft.util.math.*;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.registries.*;

import java.util.*;

public class BeamParticleData implements IParticleData {
    public static final IParticleData.IDeserializer<BeamParticleData> DESERIALIZER = new IParticleData.IDeserializer<BeamParticleData>() {
        public BeamParticleData deserialize(ParticleType<BeamParticleData> particleTypeIn, StringReader reader) throws CommandSyntaxException {
            reader.expect(' ');
            float f = (float)reader.readDouble();
            reader.expect(' ');
            float f1 = (float)reader.readDouble();
            reader.expect(' ');
            float f2 = (float)reader.readDouble();
            reader.expect(' ');
            float f3 = (float)reader.readDouble();
            return new BeamParticleData(particleTypeIn, f, f1, f2, f3);
        }

        public BeamParticleData read(ParticleType<BeamParticleData> particleTypeIn, PacketBuffer buffer) {
            return new BeamParticleData(particleTypeIn, buffer.readFloat(), buffer.readFloat(), buffer.readFloat(), buffer.readFloat());
        }
    };
    private final float red;
    private final float green;
    private final float blue;
    private final float alpha;
    private final ParticleType<BeamParticleData> type;

    public BeamParticleData(ParticleType<BeamParticleData> particleTypeIn, float red, float green, float blue, float alpha) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = MathHelper.clamp(alpha, 0.01F, 4.0F);
        this.type = particleTypeIn;
    }

    public void write(PacketBuffer buffer) {
        buffer.writeFloat(this.red);
        buffer.writeFloat(this.green);
        buffer.writeFloat(this.blue);
        buffer.writeFloat(this.alpha);
    }

    public String getParameters() {
        return String.format(Locale.ROOT, "%s %.2f %.2f %.2f %.2f", ForgeRegistries.PARTICLE_TYPES.getKey(this.getType()), this.red, this.green, this.blue, this.alpha);
    }

    public ParticleType<BeamParticleData> getType() {
        return type;
    }

    @OnlyIn(Dist.CLIENT)
    public float getRed() {
        return this.red;
    }

    @OnlyIn(Dist.CLIENT)
    public float getGreen() {
        return this.green;
    }

    @OnlyIn(Dist.CLIENT)
    public float getBlue() {
        return this.blue;
    }

    @OnlyIn(Dist.CLIENT)
    public float getAlpha() {
        return this.alpha;
    }
}
