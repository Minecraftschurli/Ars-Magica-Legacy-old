package am2.buffs;

import net.minecraft.entity.EntityLivingBase;
import am2.defs.PotionEffectsDefs;

public class BuffEffectWateryGrave extends BuffEffect{

	public BuffEffectWateryGrave(int duration, int amplifier){
		super(PotionEffectsDefs.wateryGrave, duration, amplifier);
	}

	@Override
	public void applyEffect(EntityLivingBase entityliving){
	}

	@Override
	public void stopEffect(EntityLivingBase entityliving){
	}

	@Override
	protected String spellBuffName(){
		return "Watery Grave";
	}

}
