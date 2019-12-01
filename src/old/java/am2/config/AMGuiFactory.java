package am2.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import am2.ArsMagica2;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.IModGuiFactory;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;

public class AMGuiFactory implements IModGuiFactory {

	@Override
	public void initialize(Minecraft minecraftInstance) {
		
	}

	@Override
	public Class<? extends GuiScreen> mainConfigGuiClass() {
		return AMGuiConfig.class;
	}

	@Override
	public Set<RuntimeOptionCategoryElement> runtimeGuiCategories() {
		return null;
	}

	@Override
	public RuntimeOptionGuiHandler getHandlerFor(RuntimeOptionCategoryElement element) {
		return null;
	}
	
	public static class AMGuiConfig extends GuiConfig {

		public AMGuiConfig(GuiScreen parentScreen) {
			super(parentScreen, getConfigElements(), ArsMagica2.MODID, false, false, I18n.format("am2.gui.config"));
		}
		
		private static List<IConfigElement> getConfigElements() {
			ArrayList<IConfigElement> list = new ArrayList<>();
			list.add(new ConfigElement(ArsMagica2.disabledSkills.getCategory("enabled_spell_part")));
			ArrayList<String> childs = new ArrayList<String>();
			for (String category : ArsMagica2.config.getCategoryNames()) {
				if (ArsMagica2.config.getCategory(category).parent == null)
					childs.add(category);
			}
			for (String category : childs) {
				if (category.contains("\\."))
					continue;
				list.add((new ConfigElement(ArsMagica2.config.getCategory(category))));
			}
			return list;
		}
		
	}

}
