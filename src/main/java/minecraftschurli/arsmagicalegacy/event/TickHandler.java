package minecraftschurli.arsmagicalegacy.event;

import minecraftschurli.arsmagicalegacy.util.MagicHelper;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;

/**
 * @author Minecraftschurli
 * @version 2019-11-14
 */
public class TickHandler {

    @SubscribeEvent
    public static void onTick(TickEvent.PlayerTickEvent event) {
        if (event.side == LogicalSide.SERVER && event.phase == TickEvent.Phase.START) {
            MagicHelper.regenMana(event.player, 0.1f);
            MagicHelper.regenBurnout(event.player, 0.1f);
        }
    }
}
