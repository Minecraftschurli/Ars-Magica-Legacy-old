package am2.api.flickers;

import am2.*;
import am2.api.affinity.*;

import java.util.*;
import java.util.Map.*;

/**
 * This class is used in the flicker lure, it manages the different flicker generation weights
 * 
 * @author Edwin
 *
 */
public class FlickerGenerationPool {
	
	public static FlickerGenerationPool INSTANCE = new FlickerGenerationPool();
	
	private HashMap<Affinity, Integer> map = new HashMap<>();
	
	private FlickerGenerationPool() {
		addWeightedAffinity(Affinity.AIR, 50);
		addWeightedAffinity(Affinity.ARCANE, 25);
		addWeightedAffinity(Affinity.EARTH, 50);
		addWeightedAffinity(Affinity.ENDER, 5);
		addWeightedAffinity(Affinity.FIRE, 50);
		addWeightedAffinity(Affinity.ICE, 25);
		addWeightedAffinity(Affinity.LIFE, 5);
		addWeightedAffinity(Affinity.LIGHTNING, 25);
		addWeightedAffinity(Affinity.NATURE, 25);
		addWeightedAffinity(Affinity.WATER, 50);
	}
	
	public void addWeightedAffinity(Affinity aff, int weight) {
		if (map.containsKey(aff)) LogHelper.warn("Override : %s is already registered, report to the mod author", aff.getRegistryName().toString());
		map.put(aff, weight);
	}
	
	public int getTotalWeight() {
		int total = 0;
		for (Integer integer : map.values())
			if (integer != null) total += integer.intValue();
		return total;
	}
	
	public Affinity getWeightedAffinity() {
		int chosen = new Random().nextInt(getTotalWeight());
		int total = 0;
		for (Entry<Affinity, Integer> entry : map.entrySet()) {
			if (entry.getValue() != null) {
				total += entry.getValue().intValue();
				if (total > chosen)
					return entry.getKey();
			}
		}
		return Affinity.NONE;
	}
}
