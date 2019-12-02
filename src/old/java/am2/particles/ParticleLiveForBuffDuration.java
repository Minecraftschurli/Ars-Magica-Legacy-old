package am2.particles;

import net.minecraft.entity.EntityLiving;
import net.minecraft.potion.Potion;

public class ParticleLiveForBuffDuration extends ParticleController{

	private int updateTicks;
	private Potion effect;
	private EntityLiving entity;
	private int ticksWithoutBuff;

	public ParticleLiveForBuffDuration(AMParticle particleEffect, EntityLiving entity, Potion buffID, int priority, boolean exclusive){
		super(particleEffect, priority, exclusive);
		this.entity = entity;
		this.effect = buffID;
		ticksWithoutBuff = 0;
	}

	@Override
	public void doUpdate(){
		updateTicks++;
		if (updateTicks % 10 == 0){
			if (!entity.isPotionActive(effect)){
				ticksWithoutBuff++;
				if (ticksWithoutBuff > 3)
					particle.setExpired();
			}else{
				ticksWithoutBuff = 0;
			}
			updateTicks = 0;
		}
	}

	@Override
	public ParticleController clone(){
		return new ParticleLiveForBuffDuration(particle, entity, effect, priority, exclusive);
	}

}
