package am2.buffs;

import am2.defs.*;
import net.minecraft.entity.*;

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
