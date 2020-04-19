package minecraftschurli.arsmagicalegacy.handler;

import minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import minecraftschurli.arsmagicalegacy.api.capability.CapabilityHelper;
import minecraftschurli.arsmagicalegacy.api.event.SpellCastEvent;
import minecraftschurli.arsmagicalegacy.api.spell.SpellCastResult;
import minecraftschurli.arsmagicalegacy.util.SpellUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

/**
 * @author Minecraftschurli
 * @version 2020-04-19
 */
public class LevelUpHandler {
    @SubscribeEvent
    public static void compendiumPickup(final PlayerEvent.ItemPickupEvent event) {
        if (CapabilityHelper.getCurrentLevel(event.getPlayer()) > 0) return;
        if (event.getStack().equals(ArsMagicaAPI.getCompendium())) {
            CapabilityHelper.addXP(event.getPlayer(), 0);
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void spellCast(final SpellCastEvent.Post event) {
        if (!(event.castResult == SpellCastResult.SUCCESS || event.castResult == SpellCastResult.SUCCESS_REDUCE_MANA)) return;
        if (!(event.caster instanceof PlayerEntity)) return;
        if (CapabilityHelper.getCurrentLevel((PlayerEntity) event.caster) <= 0) return;
        final int complexity = SpellUtil.getParts(event.stack).size();
        final float manaCost = event.manaCost;
        final float burnout = event.burnout;
        final float maxMana = CapabilityHelper.getMaxMana(event.caster);
        final float maxBurnout = CapabilityHelper.getMaxBurnout(event.caster);
        CapabilityHelper.addXP((PlayerEntity) event.caster, calculateXp(complexity, manaCost, maxMana, burnout, maxBurnout));
    }

    private static float calculateXp(final int complexity, final float manaCost, final float maxMana, final float burnout, final float maxBurnout) {
        return complexity * manaCost / (burnout / maxBurnout);
    }
}
