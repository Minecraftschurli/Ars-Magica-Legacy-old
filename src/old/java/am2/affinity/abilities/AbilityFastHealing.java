package am2.affinity.abilities;

import am2.api.affinity.AbstractAffinityAbility;
import am2.api.affinity.Affinity;
import am2.extensions.AffinityData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class AbilityFastHealing extends AbstractAffinityAbility {

	public AbilityFastHealing() {
		super(new ResourceLocation("arsmagica2", "fasthealing"));
	}

	@Override
	public float getMinimumDepth() {
		return 0.0f;
	}

	@Override
	public Affinity getAffinity() {
		return Affinity.LIFE;
	}
	
	@Override
	public void applyTick(EntityPlayer player) {
		AffinityData.For(player).accumulatedLifeRegen += 0.025 * AffinityData.For(player).getAffinityDepth(Affinity.LIFE);
		if (AffinityData.For(player).accumulatedLifeRegen > 1.0f){
			AffinityData.For(player).accumulatedLifeRegen -= 1.0f;
			player.heal(1);
		}
	}

}
