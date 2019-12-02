package am2.api;

import java.util.HashMap;
import java.util.logging.Logger;

import am2.api.skill.SkillPoint;

public class SkillPointRegistry {
	
	private static final HashMap<Integer, SkillPoint> pointMap = new HashMap<Integer, SkillPoint>();
	
	public static void registerSkillPoint(int tier, SkillPoint point) {
		if (pointMap.containsKey(tier)) Logger.getLogger("ArsMagica2 >> Registry").severe("Tier " + tier + " is already existing !");
		else pointMap.put(tier, point);
	}
	
	public static HashMap<Integer, SkillPoint> getSkillPointMap() {
		return pointMap;
	}
	
	public static SkillPoint getPointForTier (int tier) {
		return pointMap.get(tier);
	}

	public static SkillPoint fromName(String string) {
		for (SkillPoint skillPoint : pointMap.values())
			if (skillPoint.getName().equalsIgnoreCase(string)) return skillPoint;
		return null;
	}
}
