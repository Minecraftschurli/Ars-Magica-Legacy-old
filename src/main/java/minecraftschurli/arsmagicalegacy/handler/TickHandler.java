package minecraftschurli.arsmagicalegacy.handler;

import minecraftschurli.arsmagicalegacy.api.capability.*;
import net.minecraftforge.event.*;
import net.minecraftforge.eventbus.api.*;
import net.minecraftforge.fml.*;

/**
 * @author Minecraftschurli
 * @version 2019-11-14
 */
public class TickHandler {

    @SubscribeEvent
    public static void onTick(TickEvent.PlayerTickEvent event) {
        if (event.side == LogicalSide.SERVER && event.phase == TickEvent.Phase.START) {
            CapabilityHelper.increaseMana(event.player, 0.1f + 0.3f * CapabilityHelper.getManaRegenLevel(event.player));
            CapabilityHelper.decreaseBurnout(event.player, 0.4f);
        }
    }
}
