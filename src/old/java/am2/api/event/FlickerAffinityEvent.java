package am2.api.event;

import java.util.ArrayList;

import am2.api.affinity.Affinity;
import am2.entity.EntityFlicker;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.eventhandler.Event;

public class FlickerAffinityEvent extends Event {
	
	private final ArrayList<Affinity> validAffinity;
	private final EntityFlicker flicker;
	private final Biome biome;
	
	public FlickerAffinityEvent(ArrayList<Affinity> validAffinity, EntityFlicker flicker, Biome biome) {
		this.validAffinity = validAffinity;
		this.flicker = flicker;
		this.biome = biome;
	}
	
	public Biome getBiome() {
		return biome;
	}
	
	public EntityFlicker getFlicker() {
		return flicker;
	}
	
	public ArrayList<Affinity> getValidAffinity() {
		return validAffinity;
	}
}
