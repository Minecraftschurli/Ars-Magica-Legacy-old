package am2.api.event;

import net.minecraft.nbt.*;
import net.minecraft.potion.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class PotionEvent extends Event{
	
	public Potion id;
	public int duration, amplifier;
	public boolean ambient, showParticules;
	public PotionEffect effect;
	
	protected PotionEvent(Potion id, int duration, int amplifier, boolean ambient, boolean showParticules) {
		this.id = id;
		this.duration = duration;
		this.amplifier = amplifier;
		this.ambient = ambient;
		this.showParticules = showParticules;
		this.effect = new PotionEffect(id, duration, amplifier, ambient, showParticules);
	}
	
	protected PotionEvent(PotionEffect effect) {
		this.effect = effect;
		id = effect.getPotion();
		duration = effect.getDuration();
		amplifier = effect.getAmplifier();
		showParticules = effect.doesShowParticles();
		ambient = effect.getIsAmbient();
	}
	
	public PotionEffect getEffect() {
		return effect;
	}
	
	public static class EventPotionAdded extends PotionEvent {

		public EventPotionAdded(Potion id, int duration, int amplifier, boolean ambient, boolean showParticules) {
			super(id, duration, amplifier, ambient, showParticules);
		}
		
		public EventPotionAdded(PotionEffect effect) {
			super(effect);
		}

	}
	
	public static class EventPotionLoaded extends PotionEvent {
		
		private NBTTagCompound compound;
		
		public EventPotionLoaded(PotionEffect effect, NBTTagCompound compound) {
			super(effect);
			this.compound = compound;
		}
		
		public NBTTagCompound getCompound() {
			return compound;
		}
		
		public static PotionEffect post(PotionEffect effect, NBTTagCompound compound) {
			EventPotionLoaded event = new EventPotionLoaded(effect, compound);
			MinecraftForge.EVENT_BUS.post(event);
			return event.getEffect();
		}
	}

}
