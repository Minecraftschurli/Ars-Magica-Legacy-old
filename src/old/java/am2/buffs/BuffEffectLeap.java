package am2.buffs;

import am2.defs.*;
import net.minecraft.entity.*;

public class BuffEffectLeap extends BuffEffect{

	public BuffEffectLeap(int duration, int amplifier){
		super(PotionEffectsDefs.leap, duration, amplifier);
	}

	@Override
	public void applyEffect(EntityLivingBase entityliving){

	}

	@Override
	public void stopEffect(EntityLivingBase entityliving){

	}

	@Override
	protected String spellBuffName(){
		return "Leap";
	}

}
