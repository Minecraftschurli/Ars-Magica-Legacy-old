package am2.buffs;

import java.util.UUID;

import am2.defs.PotionEffectsDefs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;

public class BuffEffectFrostSlowed extends BuffEffect{

	private static final UUID frostSlowID = UUID.fromString("03B0A79B-9569-43AE-BFE3-820D993D4A64");

	public BuffEffectFrostSlowed(int duration, int amplifier){
		super(PotionEffectsDefs.frostSlow, duration, amplifier);
	}

	@Override
	public boolean shouldNotify(){
		return false;
	}

	@Override
	protected String spellBuffName(){
		return "Frost Slow";
	}

	@Override
	public void applyEffect(EntityLivingBase entityliving){
		IAttributeInstance attributeinstance = entityliving.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);

		if (attributeinstance.getModifier(frostSlowID) != null){
			attributeinstance.removeModifier(attributeinstance.getModifier(frostSlowID));
		}
		
		attributeinstance.applyModifier(new AttributeModifier(frostSlowID, "Frost Slow", -0.2 -(0.3 * this.getAmplifier()), 2));
	}

	@Override
	public void stopEffect(EntityLivingBase entityliving){
		IAttributeInstance attributeinstance = entityliving.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);

		if (attributeinstance.getModifier(frostSlowID) != null){
			attributeinstance.removeModifier(attributeinstance.getModifier(frostSlowID));
		}
	}

	@Override
	public void performEffect(EntityLivingBase entityliving){
	}
}
