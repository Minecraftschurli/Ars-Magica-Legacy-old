package am2.buffs;

import am2.defs.*;
import net.minecraft.entity.*;

public class BuffEffectShrink extends BuffEffect{

	public BuffEffectShrink(int duration, int amplifier){
		super(PotionEffectsDefs.shrink, duration, amplifier);
	}

	@Override
	public void applyEffect(EntityLivingBase entityliving){

	}

	@Override
	public void stopEffect(EntityLivingBase entityliving){
	}

	@Override
	protected String spellBuffName(){
		return "Shrunken";
	}

}
