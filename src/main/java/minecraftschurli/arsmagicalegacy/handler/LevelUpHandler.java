package minecraftschurli.arsmagicalegacy.handler;

import minecraftschurli.arsmagicalegacy.ArsMagicaLegacy;
import minecraftschurli.arsmagicalegacy.api.capability.CapabilityHelper;
import minecraftschurli.arsmagicalegacy.api.event.SpellCastEvent;
import minecraftschurli.arsmagicalegacy.api.spell.SpellCastResult;
import minecraftschurli.arsmagicalegacy.compat.patchouli.PatchouliCompat;
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
        if (event.getPlayer().isCreative() || event.getPlayer().isSpectator()) return;
        if (CapabilityHelper.getCurrentLevel(event.getPlayer()) >= 0) return;
        if (PatchouliCompat.isCompendium(event.getStack())) CapabilityHelper.addXP(event.getPlayer(), 0);
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void spellCast(final SpellCastEvent.Post event) {
        if (!(event.castResult == SpellCastResult.SUCCESS || event.castResult == SpellCastResult.SUCCESS_REDUCE_MANA))
            return;
        if (!(event.caster instanceof PlayerEntity)) return;
        PlayerEntity player = ((PlayerEntity) event.caster);
        if (player.isCreative() || player.isSpectator()) return;
        if (CapabilityHelper.getCurrentLevel(player) <= 0) return;
        final int complexity = SpellUtil.getParts(event.stack).size();
        final float manaCost = event.manaCost;
        final float burnout = event.burnout;
        final float maxMana = CapabilityHelper.getMaxMana(player);
        final float maxBurnout = CapabilityHelper.getMaxBurnout(player);
        CapabilityHelper.addXP(player, calculateXp(complexity, manaCost, maxMana, burnout, maxBurnout));
    }

    private static float calculateXp(final int complexity, final float manaCost, final float maxMana, final float burnout, final float maxBurnout) {
        float b = (burnout / maxBurnout);
        if (b == 0) {
            b = 1;
        }
        float m = (manaCost / maxMana);
        float v = complexity * m / b;
        ArsMagicaLegacy.LOGGER.debug("Complexity: {}", complexity);
        ArsMagicaLegacy.LOGGER.debug("Mana cost: {}", manaCost);
        ArsMagicaLegacy.LOGGER.debug("Max Mana: {}", maxMana);
        ArsMagicaLegacy.LOGGER.debug("Burnout: {}", burnout);
        ArsMagicaLegacy.LOGGER.debug("Max Burnout: {}", maxBurnout);
        ArsMagicaLegacy.LOGGER.debug("XP: {}", v);
        return v;
    }
}
