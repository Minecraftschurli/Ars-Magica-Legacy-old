package am2.api.event;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraftforge.fml.common.eventhandler.Event;


public class ArmorTextureEvent extends Event{
	public final EntityEquipmentSlot slot;
	public final int renderIndex;

	public String texture;

	public ArmorTextureEvent(EntityEquipmentSlot slot2, int renderIndex){
		this.slot = slot2;
		this.renderIndex = renderIndex;
	}
}
