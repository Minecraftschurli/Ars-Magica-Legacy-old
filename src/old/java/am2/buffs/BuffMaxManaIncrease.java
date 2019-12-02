package am2.buffs;

import net.minecraft.entity.EntityLivingBase;
import am2.defs.PotionEffectsDefs;

public class BuffMaxManaIncrease extends BuffEffect{

	public BuffMaxManaIncrease(int duration, int amplifier){
		super(PotionEffectsDefs.manaBoost, duration, amplifier);
	}

	@Override
	public void applyEffect(EntityLivingBase entityliving){
	}

	@Override
	public void stopEffect(EntityLivingBase entityliving){
	}

	@Override
	protected String spellBuffName(){
		return "Mana Boost";
	}

}
