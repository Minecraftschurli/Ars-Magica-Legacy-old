package am2.buffs;

import net.minecraft.entity.EntityLivingBase;
import am2.defs.PotionEffectsDefs;

public class BuffEffectAstralDistortion extends BuffEffect{

	public BuffEffectAstralDistortion(int duration, int amplifier){
		super(PotionEffectsDefs.astralDistortion, duration, amplifier);
	}

	@Override
	public void applyEffect(EntityLivingBase entityliving){
	}

	@Override
	public void stopEffect(EntityLivingBase entityliving){
	}

	@Override
	protected String spellBuffName(){
		return "Astral Distortion";
	}

}
