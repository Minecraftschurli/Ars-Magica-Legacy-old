package minecraftschurli.arsmagicalegacy.objects.particle;

import net.minecraft.client.particle.*;
import net.minecraft.particles.*;
import net.minecraft.world.*;
import net.minecraftforge.api.distmarker.*;

import javax.annotation.*;
@OnlyIn(Dist.CLIENT)
public class ParticleFactory implements IParticleFactory<BasicParticleType> {
    private final IAnimatedSprite sprite;

    public ParticleFactory(IAnimatedSprite sprite) {
        this.sprite = sprite;
    }

    @Nullable
    @Override
    public Particle makeParticle(BasicParticleType typeIn, World worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
        return new StandardParticle(worldIn, x, y, z);
    }
}
