package am2.affinity.abilities;

import am2.api.affinity.AbstractAffinityAbility;
import am2.api.affinity.Affinity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class AbilityLightningStep extends AbstractAffinityAbility {

	public AbilityLightningStep() {
		super(new ResourceLocation("arsmagica2", "lightningstep"));
	}

	@Override
	public float getMinimumDepth() {
		return 0.5f;
	}

	@Override
	public Affinity getAffinity() {
		return Affinity.LIGHTNING;
	}
	
	@Override
	public void applyTick(EntityPlayer player) {
		player.stepHeight = 1.014F;
	}
	
	@Override
	public void removeEffects(EntityPlayer player) {
		if (player.stepHeight == 1.014F)
			player.stepHeight = 0.6F;
	}

}
