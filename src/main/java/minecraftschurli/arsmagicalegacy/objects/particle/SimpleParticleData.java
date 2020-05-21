package minecraftschurli.arsmagicalegacy.objects.particle;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Locale;
import javax.annotation.Nonnull;
import minecraftschurli.arsmagicalegacy.util.ParticleUtil;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleType;
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
            float r = (float) reader.readDouble();
            reader.expect(' ');
            float g = (float) reader.readDouble();
            reader.expect(' ');
            float b = (float) reader.readDouble();
            return new SimpleParticleData(particleTypeIn, r, g, b);
        }

        public SimpleParticleData read(ParticleType<SimpleParticleData> particleTypeIn, PacketBuffer buffer) {
            return new SimpleParticleData(particleTypeIn, buffer.readFloat(), buffer.readFloat(), buffer.readFloat());
        }
    };
    private final float red;
    private final float green;
    private final float blue;
    private final ParticleType<SimpleParticleData> type;

    public SimpleParticleData(ParticleType<SimpleParticleData> typeIn, float red, float green, float blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        type = typeIn;
    }

    public SimpleParticleData(ParticleType<SimpleParticleData> typeIn, int color) {
        this.red = ParticleUtil.getRed(color);
        this.green = ParticleUtil.getGreen(color);
        this.blue = ParticleUtil.getBlue(color);
        type = typeIn;
    }

    @Override
    public void write(PacketBuffer buffer) {
        buffer.writeFloat(red);
        buffer.writeFloat(green);
        buffer.writeFloat(blue);
    }

    @Nonnull
    public String getParameters() {
        return String.format(Locale.ROOT, "%s %.2f %.2f %.2f", ForgeRegistries.PARTICLE_TYPES.getKey(getType()), red, green, blue);
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
}
