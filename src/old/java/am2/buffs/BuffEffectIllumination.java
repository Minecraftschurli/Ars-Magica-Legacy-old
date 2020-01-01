package am2.buffs;

import am2.defs.*;
import net.minecraft.entity.*;

public class BuffEffectIllumination extends BuffEffect{

	public BuffEffectIllumination(int duration, int amplifier){
		super(PotionEffectsDefs.illumination, duration, amplifier);
	}

	@Override
	public void applyEffect(EntityLivingBase entityliving){
	}

	@Override
	public void stopEffect(EntityLivingBase entityliving){
	}

	@Override
	public void performEffect(EntityLivingBase entityliving){
		if (!entityliving.worldObj.isRemote && entityliving.ticksExisted % 10 == 0) {
			if (entityliving.worldObj.isAirBlock(entityliving.getPosition()) && entityliving.worldObj.getLight(entityliving.getPosition()) < 7){
				entityliving.worldObj.setBlockState(entityliving.getPosition(), BlockDefs.invisibleLight.getDefaultState());
			}
		}
	}

	@Override
	protected String spellBuffName(){
		return "Illumination";
	}

}
