package am2.buffs;

import am2.defs.*;
import net.minecraft.entity.*;

public class BuffEffectSpellReflect extends BuffEffect{

	public BuffEffectSpellReflect(int duration, int amplifier){
		super(PotionEffectsDefs.spellReflect, duration, amplifier);
	}

	@Override
	public void applyEffect(EntityLivingBase entityliving){
	}

	@Override
	public void stopEffect(EntityLivingBase entityliving){
	}

	@Override
	protected String spellBuffName(){
		return "Spell Reflect";
	}

}
