package am2.buffs;

import am2.defs.*;
import am2.utils.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.*;
import net.minecraft.potion.*;

import java.util.*;
import java.util.Map.*;

public class BuffStatModifiers {
	public static final BuffStatModifiers instance = new BuffStatModifiers();

	public void applyStatModifiersBasedOnBuffs(EntityLivingBase entity){
		//entangled
		applyOrRemoveModifiersForBuff(entity, PotionEffectsDefs.entangle, ResourceUtils.createHashMap(SharedMonsterAttributes.MOVEMENT_SPEED, entangled));
		//frost slow
		applyOrRemoveScalingModifiersForBuff(entity, PotionEffectsDefs.frostSlow, SharedMonsterAttributes.MOVEMENT_SPEED, frostSlow_Diminished, frostSlow_Normal, frostSlow_Augmented);
		//fury
		HashMap<IAttribute, AttributeModifier> fury = new HashMap<IAttribute, AttributeModifier>();
		fury.put(SharedMonsterAttributes.MOVEMENT_SPEED, furyMoveMod);
		fury.put(SharedMonsterAttributes.ATTACK_DAMAGE, furyDmgMod);
		applyOrRemoveModifiersForBuff(entity, PotionEffectsDefs.fury, fury);
	}

	private void applyOrRemoveModifiersForBuff(EntityLivingBase entity, Potion buffID, Map<IAttribute, AttributeModifier> modifiers){
		if (entity.isPotionActive(buffID)){
			applyAllModifiers(entity, modifiers);
		}else{
			clearAllModifiers(entity, modifiers);
		}
	}

	private void applyOrRemoveScalingModifiersForBuff(EntityLivingBase entity, Potion potionID, IAttribute attribute, AttributeModifier... modifiers){
		IAttributeInstance inst = entity.getEntityAttribute(attribute);
		if (inst == null) {
			return;
		}
		AttributeModifier currentModifier = inst.getModifier(modifiers[0].getID());
		if (entity.isPotionActive(potionID)){
			int magnitude = entity.getActivePotionEffect(potionID).getAmplifier();
			if (magnitude < 0) magnitude = 0;
			AttributeModifier modifier = modifiers[Math.min(magnitude, modifiers.length - 1)];
			if (currentModifier != modifier) {
				if (currentModifier != null) {
					inst.removeModifier(currentModifier);
				}
				inst.applyModifier(modifier);
			}
		}else{
			if (currentModifier != null) {
				inst.removeModifier(currentModifier);
			}
		}
	}

	private void applyAllModifiers(EntityLivingBase entity, Map<IAttribute, AttributeModifier> modifiers){
		for (Entry<IAttribute, AttributeModifier> entry : modifiers.entrySet()){
			IAttributeInstance inst = entity.getEntityAttribute(entry.getKey());
			if (inst == null)
				continue;
			AttributeModifier currentModifier = inst.getModifier(entry.getValue().getID());
			if (currentModifier != entry.getValue()){
				if (currentModifier != null) {
					inst.removeModifier(currentModifier);
				}
				inst.applyModifier(entry.getValue());
			}
		}
	}

	private void clearAllModifiers(EntityLivingBase entity, Map<IAttribute, AttributeModifier> modifiers){
		for (Entry<IAttribute, AttributeModifier> entry : modifiers.entrySet()){
			IAttributeInstance inst = entity.getEntityAttribute(entry.getKey());
			if (inst == null)
				continue;
			AttributeModifier currentModifier = inst.getModifier(entry.getValue().getID());
			if (currentModifier == entry.getValue()) {
				inst.removeModifier(currentModifier);
			}
		}
	}

	//*  Fury  *//
	private static final AttributeModifier furyMoveMod = (new AttributeModifier(IDDefs.furyMoveID, "Fury (Movement)", 2, 2));

	private static final AttributeModifier furyDmgMod = (new AttributeModifier(IDDefs.furyDmgID, "Fury (Damage)", 5, 2));

	//*  Entangle  *//
	private static final AttributeModifier entangled = (new AttributeModifier(IDDefs.entangledID, "Entangled", -10, 2));

	//*  Frost Slow *//
	private static final AttributeModifier frostSlow_Diminished = (new AttributeModifier(IDDefs.frostSlowID, "Frost Slow (Diminished)", -0.2, 2));
	private static final AttributeModifier frostSlow_Normal = (new AttributeModifier(IDDefs.frostSlowID, "Frost Slow (Normal)", -0.5, 2));
	private static final AttributeModifier frostSlow_Augmented = (new AttributeModifier(IDDefs.frostSlowID, "Frost Slow (Augmented)", -0.8, 2));
}
