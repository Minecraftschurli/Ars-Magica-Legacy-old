package minecraftschurli.arsmagicalegacy.api.event;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.entity.player.PlayerEvent;

/**
 * @author Minecraftschurli
 * @version 2019-12-09
 */
public class PlayerMagicLevelChangeEvent extends PlayerEvent {
    public PlayerMagicLevelChangeEvent(PlayerEntity player) {
        super(player);
    }
}
