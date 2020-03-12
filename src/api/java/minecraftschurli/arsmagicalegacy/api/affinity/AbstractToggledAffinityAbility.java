package minecraftschurli.arsmagicalegacy.api.affinity;

import net.minecraft.entity.player.PlayerEntity;

public abstract class AbstractToggledAffinityAbility extends AbstractAffinityAbility {
	
	protected abstract boolean isEnabled(PlayerEntity player);
	
	@Override
	public boolean canApply(PlayerEntity player) {
		return super.canApply(player) && isEnabled(player);
	}
}
