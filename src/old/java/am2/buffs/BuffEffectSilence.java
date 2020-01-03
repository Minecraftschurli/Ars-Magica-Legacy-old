package am2.buffs;

import am2.defs.*;
import net.minecraft.entity.*;

public class BuffEffectSilence extends BuffEffect{

	public BuffEffectSilence(int duration, int amplifier){
		super(PotionEffectsDefs.silence, duration, amplifier);
	}

	@Override
	public void applyEffect(EntityLivingBase entityliving){
	}

	@Override
	public void stopEffect(EntityLivingBase entityliving){
	}

	@Override
	protected String spellBuffName(){
		return "Silence";
	}

}
