package minecraftschurli.arsmagicalegacy.objects.particle;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import minecraftschurli.arsmagicalegacy.util.ParticleUtil;
import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.SpriteTexturedParticle;
import net.minecraft.world.World;

public class SimpleParticle extends SpriteTexturedParticle {
    private final IAnimatedSprite animatedSprite;

    public SimpleParticle(World world, double x, double y, double z, double dx, double dy, double dz, SimpleParticleData data, IAnimatedSprite sprite) {
        super(world, x, y, z, dx, dy, dz);
        animatedSprite = sprite;
        particleRed = data.getRed();
        particleGreen = data.getGreen();
        particleBlue = data.getBlue();
        particleAlpha = 1;
        maxAge = 20 + rand.nextInt(20);
        particleGravity = 1;
        selectSpriteWithAge(animatedSprite);
    }

    @Override
    public void tick() {
        selectSpriteWithAge(animatedSprite);
        super.tick();
    }

    @Nonnull
    @Override
    public IParticleRenderType getRenderType() {
        return IParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    public void setColor(int color) {
        particleRed = ParticleUtil.getRed(color);
        particleGreen = ParticleUtil.getGreen(color);
        particleBlue = ParticleUtil.getBlue(color);
    }

    public void setColor(float r, float g, float b) {
        particleRed = r;
        particleGreen = g;
        particleBlue = b;
    }

    public static class Factory implements IParticleFactory<SimpleParticleData> {
        private final IAnimatedSprite spriteSet;

        public Factory(IAnimatedSprite p_i50477_1_) {
            spriteSet = p_i50477_1_;
        }

        @Nullable
        @Override
        public Particle makeParticle(SimpleParticleData typeIn, World worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new SimpleParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, typeIn, spriteSet);
        }
    }
}
