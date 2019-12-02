package am2.api.event;

import am2.api.affinity.Affinity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

@Cancelable
public class AffinityChangingEvent extends Event{
	public final EntityPlayer player;
	public float amount;
	public final Affinity affinity;

	public AffinityChangingEvent(EntityPlayer player, Affinity affinity, float amt){
		this.player = player;
		this.amount = amt;
		this.affinity = affinity;
	}
}
