package am2.buffs;

import am2.defs.PotionEffectsDefs;

public class BuffEffectTrueSight extends BuffEffectShield{

	public BuffEffectTrueSight(int duration, int amplifier) {
		super(PotionEffectsDefs.trueSight, duration, amplifier);
	}

	@Override
	protected String spellBuffName(){
		return "True Sight";
	}

}
