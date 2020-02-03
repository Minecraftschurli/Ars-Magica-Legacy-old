package am2.buffs;

import am2.defs.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;

public class BuffEffectLevitation extends BuffEffect{

	public BuffEffectLevitation(int duration, int amplifier){
		super(PotionEffectsDefs.levitation, duration, amplifier);
	}

	@Override
	public void applyEffect(EntityLivingBase entityliving){
		if (entityliving instanceof EntityPlayer){
			((EntityPlayer)entityliving).capabilities.allowFlying = true;
			((EntityPlayer)entityliving).sendPlayerAbilities();
		}
	}

	@Override
	public void performEffect(EntityLivingBase entityliving){
		if (entityliving instanceof EntityPlayer){
			if (((EntityPlayer)entityliving).capabilities.isFlying){
				float factor = 0.4f;
				entityliving.motionX *= factor;
				entityliving.motionZ *= factor;
				entityliving.motionY *= 0.0001f;
			}
		}
	}

	@Override
	public void stopEffect(EntityLivingBase entityliving){
		if (entityliving instanceof EntityPlayer){
			EntityPlayer player = (EntityPlayer)entityliving;
			if (!player.capabilities.isCreativeMode){
				player.capabilities.allowFlying = false;
				player.capabilities.isFlying = false;
				player.fallDistance = 0f;
				player.sendPlayerAbilities();
			}
		}
	}

	@Override
	protected String spellBuffName(){
		return "Levitation";
	}

}
