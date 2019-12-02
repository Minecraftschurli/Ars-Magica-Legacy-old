package am2.buffs;

import net.minecraft.entity.EntityLivingBase;
import am2.defs.PotionEffectsDefs;

public class BuffEffectManaRegen extends BuffEffect{

	public BuffEffectManaRegen(int duration, int amplifier){
		super(PotionEffectsDefs.manaRegen, duration, amplifier);
	}

	@Override
	public void applyEffect(EntityLivingBase entityliving){
	}

	@Override
	public void stopEffect(EntityLivingBase entityliving){
	}

	@Override
	protected String spellBuffName(){
		return "Mana Regen";
	}

}
