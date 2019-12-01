package am2.api.event;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

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

