package am2.api.skill;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;

@SuppressWarnings("deprecation")
public class SkillTree {
		
//	static {
//		registerSkillTree("offense", new ResourceLocation(ArsMagica2.MODID, "textures/occulus/offense.png"), new ResourceLocation(ArsMagica2.MODID, "textures/icons/offense.png"));
//		registerSkillTree("defense", new ResourceLocation(ArsMagica2.MODID, "textures/occulus/defense.png"), new ResourceLocation(ArsMagica2.MODID, "textures/icons/defense.png"));
//		registerSkillTree("utility", new ResourceLocation(ArsMagica2.MODID, "textures/occulus/utility.png"), new ResourceLocation(ArsMagica2.MODID, "textures/icons/utility.png"));
//		registerSkillTree("talent", new ResourceLocation(ArsMagica2.MODID, "textures/occulus/talent.png"), new ResourceLocation(ArsMagica2.MODID, "textures/icons/talent.png"));
//		registerSkillTree("affinity", new ResourceLocation(ArsMagica2.MODID, "textures/occulus/affinity.png"), new ResourceLocation(ArsMagica2.MODID, "textures/icons/affinity.png"));
//	}
	
	private String name;
	private ResourceLocation background;
	private ResourceLocation icon;
	private boolean canRender = true;
	private String unlock = null;
	
	public SkillTree(String name, ResourceLocation background, ResourceLocation icon) {
		this.name = name.toLowerCase();
		this.background = background;
		this.icon = icon;
	}
		
	public String getName() {
		return name;
	}
	
	public ResourceLocation getBackground() {
		return background;
	}
	
	public ResourceLocation getIcon() {
		return icon;
	}
		
	public String getUnlocalizedName() {
		return "skilltree." + name;
	}
	
	public String getLocalizedName() {
		return I18n.translateToLocal(getUnlocalizedName());
	}

	public SkillTree disableRender(String compendiumUnlock) {
		canRender = false;
		this.unlock = compendiumUnlock;
		return this;
	}
	
	public String getUnlock() {
		return unlock;
	}
	
	public boolean canRender() {
		return canRender;
	}
}
