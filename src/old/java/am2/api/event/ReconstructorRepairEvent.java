package am2.api.event;

import net.minecraft.item.*;
import net.minecraftforge.fml.common.eventhandler.*;

@Cancelable
public class ReconstructorRepairEvent extends Event{

	/**
	 * The item being repaired.
	 */
	public ItemStack item;

	/**
	 * Called when the arcane reconstructor ticks on repairing an item.
	 *
	 * @param item
	 */
	public ReconstructorRepairEvent(ItemStack item){
		this.item = item;
	}
}

