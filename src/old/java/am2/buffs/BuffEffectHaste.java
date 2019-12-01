package am2.buffs;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import am2.defs.IDDefs;
import am2.defs.PotionEffectsDefs;

public class BuffEffectHaste extends BuffEffect{

	private static final AttributeModifier hasteSpeedBoost = (new AttributeModifier(IDDefs.hasteID, "Haste Speed Boost", 0.2D, 2));

	public BuffEffectHaste(int duration, int amplifier){
		super(PotionEffectsDefs.haste, duration, amplifier);
	}

	@Override
	public void applyEffect(EntityLivingBase entityliving){
		IAttributeInstance attributeinstance = entityliving.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);

		if (attributeinstance.getModifier(IDDefs.hasteID) != null){
			attributeinstance.removeModifier(hasteSpeedBoost);
		}
		
		attributeinstance.applyModifier(new AttributeModifier(IDDefs.hasteID, "Haste Speed Boost", 0.2D + (0.35 * getAmplifier()), 2));
	}

	@Override
	public void performEffect(EntityLivingBase entityliving){

	}

	@Override
	public void stopEffect(EntityLivingBase entityliving){
		IAttributeInstance attributeinstance = entityliving.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);

		if (attributeinstance.getModifier(IDDefs.hasteID) != null){
			attributeinstance.removeModifier(hasteSpeedBoost);
		}
	}

	@Override
	protected String spellBuffName(){
		return "Haste";
	}

}
