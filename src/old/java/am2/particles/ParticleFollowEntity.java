package am2.particles;

import net.minecraft.entity.Entity;

public class ParticleFollowEntity extends ParticleController{

	private Entity followTarget;

	public ParticleFollowEntity(AMParticle particleEffect, int priority, Entity followTarget, boolean exclusive){
		super(particleEffect, priority, exclusive);

		this.followTarget = followTarget;
	}

	@Override
	public void doUpdate(){
		particle.pushPos();

		this.particle.setPosition(followTarget);
	}

	@Override
	public ParticleController clone(){
		return new ParticleFollowEntity(particle, priority, followTarget, exclusive);
	}

}
