package minecraftschurli.arsmagicalegacy.api.affinity;

import java.util.Collection;
import java.util.Objects;
import javax.annotation.Nullable;
import minecraftschurli.arsmagicalegacy.api.capability.CapabilityHelper;
import minecraftschurli.arsmagicalegacy.api.event.SpellCastEvent;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.registries.ForgeRegistryEntry;

public abstract class AbstractAffinityAbility extends ForgeRegistryEntry<AbstractAffinityAbility> {
	
	/**
	 * At which point does this ability enable ?
	 * 
	 * @return a depth.
	 */	
	public abstract float getMinimumDepth();
	
	/**
	 * At which point does this ability disables ?
	 * 
	 * @return a depth or any value under 0 or over 1 to ignore
	 */
	public float getMaximumDepth() {
		return -1F;
	}
	
	/**
	 * Setting this to null or NONE will make this class useless.
	 * 
	 * @return the ability that is required.
	 */
	public abstract ResourceLocation getAffinity();
	
	/**
	 * If this Affinity Ability uses a key binding, return it, otherwise just return null
	 * 
	 * @return the key binding that this ability uses, or null.
	 */
	@Nullable
	public KeyBinding getKey() {
		return null;
	}
	
	/**
	 * Checks if the player can use this ability. Most of the time you won't need change this unless you are using toggle {@link KeyBinding}s, in that case consider using {@link AbstractToggledAffinityAbility}.
	 * 
	 * @param player : the current player.
	 * @return if the player can use this ability.
	 */
	public boolean canApply(PlayerEntity player) {
		return isEligible(player);
	}
	
	public boolean isEligible(PlayerEntity player) {
		ResourceLocation aff = getAffinity();
		if (Objects.equals(aff, Affinity.NONE))
			return false;
		double depth = CapabilityHelper.getAffinityDepth(player, aff);
		if (getMaximumDepth() < 0F || getMaximumDepth() > 1F || getMaximumDepth() < getMinimumDepth())
			return depth >= getMinimumDepth();
		return depth >= getMinimumDepth() && depth <= getMaximumDepth();
	}

	public void applyTick(PlayerEntity player) {}
	
	public void applyKeyPress(PlayerEntity player) {};

	public void applyHurt(PlayerEntity player, LivingHurtEvent event) {}

	public void applyHurting(PlayerEntity player, LivingHurtEvent event) {}
	
	public void applyFall(PlayerEntity player, LivingFallEvent event) {}
	
	public void applySpellCast(PlayerEntity player, SpellCastEvent.Post event) {}
	
	public void applyPreSpellCast(PlayerEntity player, SpellCastEvent.Pre event) {}
	
	public void applyDeath(PlayerEntity player, LivingDeathEvent event) {}
	
	public void applyKill(PlayerEntity player, LivingDeathEvent event) {}
	
	public void applyJump(PlayerEntity player, LivingJumpEvent event) {}
	
	public void removeEffects(PlayerEntity player) {}

	/**
	 * For internal use
	 */
	public Runnable createRunnable(PlayerEntity player) {
		return new Apply(player, this);
	}
	
	public boolean hasMax() {
		return getMaximumDepth() >= 0F && getMaximumDepth() <= 1F && getMaximumDepth() > getMinimumDepth();
	}

	public Collection<AbilityListenerType> registerListeners(Collection<AbilityListenerType> types) { return types;}
	
	private static class Apply implements Runnable {
		
		private final PlayerEntity player;
		private final AbstractAffinityAbility ability;
		
		public Apply(PlayerEntity player, AbstractAffinityAbility ability) {
			this.player = player;
			this.ability = ability;
		}
		
		@Override
		public void run() {
			ability.applyKeyPress(player);
		}
	}

	public enum AbilityListenerType {
		TICK,
		KEY_PRESS,
		HURT,
		FALL,
		SPELL_CAST,
		PRE_SPELL_CAST,
		DEATH,
		KILL,
		HURTING,
		JUMP
	}
}
