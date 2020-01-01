package am2.api.event;

import am2.api.affinity.*;
import net.minecraft.entity.player.*;
import net.minecraftforge.fml.common.eventhandler.*;

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
