package am2.api;

import java.util.ArrayList;

import com.google.common.collect.ImmutableList;

import am2.api.skill.SkillTree;
import net.minecraft.util.ResourceLocation;

public class SkillTreeRegistry {
	
	private static final ArrayList<SkillTree> treeMap = new ArrayList<SkillTree>();

	public static void registerSkillTree (SkillTree tree) {
		treeMap.add(tree);
	}
	
	public static void registerSkillTree (String name, ResourceLocation background, ResourceLocation icon) {
		registerSkillTree(new SkillTree(name, background, icon));
	}

	public static ImmutableList<SkillTree> getSkillTreeMap() {
		return ImmutableList.copyOf(treeMap);
	}

	public static SkillTree getSkillTreeFromName (String name) {
		for (SkillTree tree : treeMap) {
			if (tree.getName().equalsIgnoreCase(name))
				return tree;
		}
		return null;
	}
	

}
