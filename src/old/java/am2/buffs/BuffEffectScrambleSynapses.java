package am2.buffs;

import net.minecraft.entity.EntityLivingBase;
import am2.defs.PotionEffectsDefs;

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
