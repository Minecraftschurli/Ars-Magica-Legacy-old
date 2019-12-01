package am2.buffs;

import net.minecraft.entity.EntityLivingBase;
import am2.defs.PotionEffectsDefs;

public class BuffEffectSlowfall extends BuffEffect{

	public BuffEffectSlowfall(int duration, int amplifier){
		super(PotionEffectsDefs.slowfall, duration, amplifier);
	}

	@Override
	public void applyEffect(EntityLivingBase entityliving){
	}

	@Override
	public void stopEffect(EntityLivingBase entityliving){
	}

	@Override
	protected String spellBuffName(){
		return "Slowfall";
	}

}
