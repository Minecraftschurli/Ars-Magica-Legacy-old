package am2.buffs;

import am2.defs.*;
import net.minecraft.entity.*;

public class BuffEffectEntangled extends BuffEffect{


	public BuffEffectEntangled(int duration, int amplifier){
		super(PotionEffectsDefs.entangle, duration, amplifier);
	}

	@Override
	public void applyEffect(EntityLivingBase entityliving){
	}

	@Override
	public void stopEffect(EntityLivingBase entityliving){
	}

	@Override
	public void performEffect(EntityLivingBase entityliving){
		entityliving.motionX = 0f;
		entityliving.motionY = 0f;
		entityliving.motionZ = 0f;
	}

	@Override
	protected String spellBuffName(){
		return "Entangled";
	}

}
