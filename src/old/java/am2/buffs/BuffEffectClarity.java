package am2.buffs;

import am2.defs.*;
import net.minecraft.entity.*;

public class BuffEffectClarity extends BuffEffect{

	public BuffEffectClarity(int duration, int amplifier){
		super(PotionEffectsDefs.clarity, duration, amplifier);
	}

	@Override
	public void applyEffect(EntityLivingBase entityliving){
	}

	@Override
	public void stopEffect(EntityLivingBase entityliving){
	}

	@Override
	protected String spellBuffName(){
		return "Clarity";
	}

}
