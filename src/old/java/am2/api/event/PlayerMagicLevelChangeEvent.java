package am2.api.event;

import net.minecraft.entity.player.*;
import net.minecraftforge.event.entity.player.*;

public class PlayerMagicLevelChangeEvent extends PlayerEvent {

	protected final int level;

	public PlayerMagicLevelChangeEvent(EntityPlayer player, int level) {
		super(player);
		this.level = level;
	}
	
	public int getLevel() {
		return level;
	}

}
