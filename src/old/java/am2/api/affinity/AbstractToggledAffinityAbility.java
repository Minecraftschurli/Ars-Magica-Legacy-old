package am2.api.affinity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public abstract class AbstractToggledAffinityAbility extends AbstractAffinityAbility{

	protected AbstractToggledAffinityAbility(ResourceLocation identifier) {
		super(identifier);
	}
	
	protected abstract boolean isEnabled(EntityPlayer player);
	
	@Override
	public boolean canApply(EntityPlayer player) {
		return super.canApply(player) && isEnabled(player);
	}
}
