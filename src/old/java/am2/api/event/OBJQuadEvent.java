package am2.api.event;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.Event;

public class OBJQuadEvent extends Event{
	
	public int tintIndex = -1;
	public String materialName;
	
	public OBJQuadEvent(String materialName) {
		this.materialName = materialName;
	}
	
	public static int post(String str) {
		OBJQuadEvent event = new OBJQuadEvent(str);
		MinecraftForge.EVENT_BUS.post(event);
		return event.tintIndex;
	}
}
