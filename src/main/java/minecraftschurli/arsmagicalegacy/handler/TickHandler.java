package minecraftschurli.arsmagicalegacy.handler;

import minecraftschurli.arsmagicalegacy.util.*;
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
            MagicHelper.increaseMana(event.player, 0.1f + 0.2f * MagicHelper.getManaRegenLevel(event.player));
            MagicHelper.decreaseBurnout(event.player, 0.1f);
        }
    }
}
