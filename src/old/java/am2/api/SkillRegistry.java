package am2.api;

import java.util.ArrayList;

import am2.api.skill.Skill;
import am2.api.skill.SkillPoint;
import am2.api.skill.SkillTree;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class SkillRegistry {
	public static void registerSkill (String ID, ResourceLocation icon, SkillPoint tier, int posX, int posY, SkillTree tree, String... parents) {
		registerSkill(new Skill(icon, tier, posX, posY, tree, parents).setRegistryName(ArsMagicaAPI.getCurrentModId(), ID.toLowerCase()));
	}
	
	public static void registerSkill (boolean createEntry, String ID, ResourceLocation icon, SkillPoint tier, int posX, int posY, SkillTree tree, String... parents) {
		registerSkill(createEntry, new Skill(icon, tier, posX, posY, tree, parents).setRegistryName(ArsMagicaAPI.getCurrentModId(), ID.toLowerCase()));
	}
	
	public static void registerSkill (boolean createEntry, Skill skill) {
		GameRegistry.register(skill);
	}
	
	public static void registerSkill (Skill skill) {
		registerSkill(true, skill);
	}
	
	public static ArrayList<Skill> getSkillsForTree (SkillTree tree) {
		ArrayList<Skill> skillList = new ArrayList<Skill>();
		for (Skill skill : ArsMagicaAPI.getSkillRegistry().getValues()) {
			if (skill != null && skill.getTree() != null && skill.getTree().equals(tree))
				skillList.add(skill);
		}
		return skillList;
	}

	public static Skill getSkillFromName(String str) {
		return ArsMagicaAPI.getSkillRegistry().getObject(new ResourceLocation(str));
	}
	
}
