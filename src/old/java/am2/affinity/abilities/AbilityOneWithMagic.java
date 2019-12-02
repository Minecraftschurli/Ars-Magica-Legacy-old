package am2.affinity.abilities;

import am2.api.affinity.AbstractAffinityAbility;
import am2.api.affinity.Affinity;
import am2.api.event.SpellCastEvent.Pre;
import am2.defs.PotionEffectsDefs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class AbilityOneWithMagic extends AbstractAffinityAbility {

	public AbilityOneWithMagic() {
		super(new ResourceLocation("arsmagica2", "onewithmagic"));
	}

	@Override
	public float getMinimumDepth() {
		return 0.5f;
	}

	@Override
	public Affinity getAffinity() {
		return Affinity.ARCANE;
	}
	
	@Override
	public void applyPreSpellCast(EntityPlayer player, Pre event) {
		event.manaCost *= 0.95f;
		event.burnout *= 0.95f;

		if (event.entityLiving.isPotionActive(PotionEffectsDefs.clarity)){
			event.manaCost = 0f;
			event.burnout = 0f;
		}
	}
}
