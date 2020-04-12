package minecraftschurli.arsmagicalegacy.objects.ability;

import minecraftschurli.arsmagicalegacy.api.affinity.AbstractToggledAffinityAbility;
import minecraftschurli.arsmagicalegacy.api.affinity.Affinity;
import minecraftschurli.arsmagicalegacy.api.capability.CapabilityHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ResourceLocation;

import java.util.Collection;

public class NightVisionAbility extends AbstractToggledAffinityAbility {

	@Override
	protected boolean isEnabled(PlayerEntity player) {
		return CapabilityHelper.getAbilityState(player, this.getRegistryName());
	}

	@Override
	public float getMinimumDepth() {
		return 0.75f;
	}

	@Override
	public ResourceLocation getAffinity() {
		return Affinity.ENDER;
	}

	@Override
	public void applyTick(PlayerEntity player) {
		Effect nightVision = Effects.NIGHT_VISION;
		if (!player.world.isRemote && (!player.isPotionActive(nightVision) || player.getActivePotionEffect(nightVision).getDuration() <= 220)){
			player.addPotionEffect(new EffectInstance(nightVision, 300, 0, true, false));
		}
	}

	@Override
	public Collection<AbilityListenerType> registerListeners(Collection<AbilityListenerType> types) {
		types.add(AbilityListenerType.TICK);
		return types;
	}
}
