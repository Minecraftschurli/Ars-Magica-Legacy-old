package am2.api.event;

import net.minecraftforge.fml.common.eventhandler.Event;

public class GenerateCompendiumLoreEvent extends Event {
	
	public String lore;
	public String entry;
	
	public GenerateCompendiumLoreEvent(String lore, String entry) {
		this.lore = lore;
		this.entry = entry;
	}
	
}
