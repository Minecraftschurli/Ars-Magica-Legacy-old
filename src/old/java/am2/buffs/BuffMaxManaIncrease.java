package am2.buffs;

import am2.defs.*;
import net.minecraft.entity.*;

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
