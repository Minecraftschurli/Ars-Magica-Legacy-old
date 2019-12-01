package am2.affinity.abilities;

import am2.affinity.AffinityAbilityModifiers;
import am2.api.affinity.AbstractAffinityAbility;
import am2.api.affinity.Affinity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class AbilityWaterWeakness extends AbstractAffinityAbility {
	
	public Affinity aff;
	
	public AbilityWaterWeakness(Affinity aff) {
		super(new ResourceLocation("arsmagica2", "waterweakness_" + aff.getName()));
		this.aff = aff;
	}

	@Override
	public float getMinimumDepth() {
		return 0.5f;
	}
	
	@Override
	public float getMaximumDepth() {
		return 0.9f;
	}

	@Override
	public Affinity getAffinity() {
		return aff;
	}
	
	@Override
	public void applyTick(EntityPlayer player) {
		IAttributeInstance attribute = player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH);
		AffinityAbilityModifiers.instance.applyOrRemoveModifier(attribute, AffinityAbilityModifiers.waterWeakness, player.isWet());
	}
	
	@Override
	public void removeEffects(EntityPlayer player) {
		IAttributeInstance attribute = player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH);
		AffinityAbilityModifiers.instance.applyOrRemoveModifier(attribute, AffinityAbilityModifiers.waterWeakness, false);
	}

}
