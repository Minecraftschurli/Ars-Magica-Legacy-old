package minecraftschurli.arsmagicalegacy.objects.particle;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Locale;
import javax.annotation.Nonnull;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleType;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * @author Minecraftschurli
 * @version 2020-01-13
 */
public class SimpleParticleData implements IParticleData {
    public static final IParticleData.IDeserializer<SimpleParticleData> DESERIALIZER = new IParticleData.IDeserializer<SimpleParticleData>() {
        public SimpleParticleData deserialize(ParticleType<SimpleParticleData> particleTypeIn, StringReader reader) throws CommandSyntaxException {
            reader.expect(' ');
            float f = (float) reader.readDouble();
            reader.expect(' ');
            float f1 = (float) reader.readDouble();
            reader.expect(' ');
            float f2 = (float) reader.readDouble();
            reader.expect(' ');
            float f3 = (float) reader.readDouble();
            return new SimpleParticleData(particleTypeIn, f, f1, f2, f3);
        }

        public SimpleParticleData read(ParticleType<SimpleParticleData> particleTypeIn, PacketBuffer buffer) {
            return new SimpleParticleData(particleTypeIn, buffer.readFloat(), buffer.readFloat(), buffer.readFloat(), buffer.readFloat());
        }
    };
    private final float red;
    private final float green;
    private final float blue;
    private final float alpha;
    private final ParticleType<SimpleParticleData> type;

    public SimpleParticleData(ParticleType<SimpleParticleData> particleTypeIn, float red, float green, float blue, float alpha) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = MathHelper.clamp(alpha, 0.01F, 4);
        type = particleTypeIn;
    }

    public void write(PacketBuffer buffer) {
        buffer.writeFloat(red);
        buffer.writeFloat(green);
        buffer.writeFloat(blue);
        buffer.writeFloat(alpha);
    }

    @Nonnull
    public String getParameters() {
        return String.format(Locale.ROOT, "%s %.2f %.2f %.2f %.2f", ForgeRegistries.PARTICLE_TYPES.getKey(getType()), red, green, blue, alpha);
    }

    @Nonnull
    public ParticleType<SimpleParticleData> getType() {
        return type;
    }

    @OnlyIn(Dist.CLIENT)
    public float getRed() {
        return red;
    }

    @OnlyIn(Dist.CLIENT)
    public float getGreen() {
        return green;
    }

    @OnlyIn(Dist.CLIENT)
    public float getBlue() {
        return blue;
    }

    @OnlyIn(Dist.CLIENT)
    public float getAlpha() {
        return alpha;
    }
}
