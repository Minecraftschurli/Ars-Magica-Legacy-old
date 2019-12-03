package minecraftschurli.arsmagicalegacy.util;

import minecraftschurli.arsmagicalegacy.ArsMagicaLegacy;
import minecraftschurli.arsmagicalegacy.api.spell.skill.Skill;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.HashMap;

public class SpellIconManager {
	
	private static final HashMap<String, TextureAtlasSprite> sprites = new HashMap<>();

	@SubscribeEvent
	public static void init(TextureStitchEvent.Pre e) {
		sprites.clear();
		ArsMagicaLegacy.LOGGER.debug("sprites");
		//AMGuiIcons.instance.init(e.getMap());
		for (Skill skill : SpellRegistry.SKILL_REGISTRY.getValues()) {
			if (skill.getIcon() != null)
				sprites.put(skill.getID(), e.getMap().getSprite(skill.getIcon()));
		}
//		sprites.put("CasterRuneSide", e.getMap().registerSprite(new ResourceLocation("arsmagica2:blocks/CasterRuneSide")));
//		sprites.put("RuneStone", e.getMap().registerSprite(new ResourceLocation("arsmagica2:blocks/RuneStone")));
	}
	
	public static TextureAtlasSprite getSprite(String name) {
		return sprites.get(name);
	}
	
}
