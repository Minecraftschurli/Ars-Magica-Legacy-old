package am2.utils;

import java.util.HashMap;

import am2.ArsMagica2;
import net.minecraft.util.ResourceLocation;

public class ResourceUtils {
		
	public static ResourceLocation getSkillIcon (String iconName) {
		return new ResourceLocation(ArsMagica2.MODID, "items/spells/skills/" + iconName);
	}
	
	public static <K, V> HashMap<K, V> createHashMap (K i, V j) {
		HashMap<K, V> map = new HashMap<K, V>();
		map.put(i, j);
		return map;
	}
}
