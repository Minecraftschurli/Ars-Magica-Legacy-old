package am2.buffs;

import am2.defs.*;
import net.minecraft.entity.*;

public class BuffEffectScrambleSynapses extends BuffEffect{

	public BuffEffectScrambleSynapses(int duration, int amplifier){
		super(PotionEffectsDefs.scrambleSynapses, duration, amplifier);
	}

	@Override
	public void applyEffect(EntityLivingBase entityliving){
	}

	@Override
	public void stopEffect(EntityLivingBase entityliving){
	}

	@Override
	protected String spellBuffName(){
		return "Scramble Synapses";
	}

}
