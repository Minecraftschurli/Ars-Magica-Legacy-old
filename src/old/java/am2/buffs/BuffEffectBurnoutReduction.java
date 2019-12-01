package am2.buffs;

import net.minecraft.entity.EntityLivingBase;
import am2.defs.PotionEffectsDefs;

public class BuffEffectBurnoutReduction extends BuffEffect{
	public BuffEffectBurnoutReduction(int duration, int amplifier){
		super(PotionEffectsDefs.burnoutReduction, duration, amplifier);
	}

	@Override
	public void applyEffect(EntityLivingBase entityliving){
	}

	@Override
	public void stopEffect(EntityLivingBase entityliving){
	}

	@Override
	protected String spellBuffName(){
		return "Burnout Reduction";
	}
}
