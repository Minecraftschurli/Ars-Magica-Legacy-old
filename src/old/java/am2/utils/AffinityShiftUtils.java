package am2.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import am2.api.ArsMagicaAPI;
import am2.api.affinity.Affinity;
import am2.api.event.AffinityChangingEvent;
import am2.api.extensions.IAffinityData;
import am2.api.spell.SpellComponent;
import am2.api.spell.SpellShape;
import am2.armor.ArmorHelper;
import am2.armor.infusions.GenericImbuement;
import am2.defs.ItemDefs;
import am2.defs.SkillDefs;
import am2.extensions.AffinityData;
import am2.extensions.EntityExtension;
import am2.extensions.SkillData;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;

public class AffinityShiftUtils {
	
	public static void doAffinityShift(EntityLivingBase caster, SpellComponent component, SpellShape governingShape){
		if (!(caster instanceof EntityPlayer)) return;
		IAffinityData aff = AffinityData.For(caster);
		Set<Affinity> affList = component.getAffinity();
		for (Affinity affinity : affList){
			float shift = component.getAffinityShift(affinity) * aff.getDiminishingReturnsFactor() * 5;
			float xp = 0.05f * aff.getDiminishingReturnsFactor();
			if (governingShape.isChanneled()){
				shift /= 4;
				xp /= 4;
			}

			if (caster instanceof EntityPlayer){
				if (SkillData.For((EntityPlayer)caster).hasSkill(SkillDefs.AFFINITY_GAINS.getID())){
					shift *= 1.1f;
					xp *= 0.9f;
				}
				ItemStack chestArmor = ((EntityPlayer)caster).getItemStackFromSlot(EntityEquipmentSlot.CHEST);
				if (chestArmor != null && ArmorHelper.isInfusionPreset(chestArmor, GenericImbuement.magicXP))
					xp *= 1.25f;
			}

			if (shift > 0){
				AffinityChangingEvent event = new AffinityChangingEvent((EntityPlayer)caster, affinity, shift);
				MinecraftForge.EVENT_BUS.post(event);
				if (!event.isCanceled())
					aff.incrementAffinity(affinity, event.amount);
			}
			if (xp > 0){
				xp *= caster.getAttributeMap().getAttributeInstance(ArsMagicaAPI.xpGainModifier).getAttributeValue();
				EntityExtension.For(caster).addMagicXP(xp);
			}
		}
		aff.addDiminishingReturns(governingShape.isChanneled());
	}
	
	public static Affinity getMainShiftForStack(ItemStack stack) {
		Affinity aff = Affinity.NONE;
		float maxDepth = 0F;
		HashMap<Affinity, Float> customDepthMap = new HashMap<>();
		ArrayList<SpellComponent> components = SpellUtils.getComponentsForStage(stack, -1);
		for (SpellComponent component : components) {
			for (Affinity aff1 : component.getAffinity()) {
				if (customDepthMap.get(aff1) != null) {
					customDepthMap.put(aff1, customDepthMap.get(aff1) + component.getAffinityShift(aff1));
				} else {
					customDepthMap.put(aff1, component.getAffinityShift(aff1));
				}
			}
		}
		
		for (Entry<Affinity, Float> entry : customDepthMap.entrySet()) {
			if (entry.getValue() > maxDepth) {
				maxDepth = entry.getValue();
				aff = entry.getKey();
			}
		}
		return aff;
	}
	
	public static ItemStack getEssenceForAffinity (Affinity affinity) {
		int meta = 0;
		for (Affinity aff : ArsMagicaAPI.getAffinityRegistry().getValues()) {
			if (aff.equals(affinity))
				break;
			meta++;
		}
		return new ItemStack(ItemDefs.essence, 1, meta);
	}
}
