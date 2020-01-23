package minecraftschurli.arsmagicalegacy.api.spell;

/**
 * @author Minecraftschurli
 * @version 2019-11-16
 */
public enum SpellCastResult {
    SUCCESS,
    NOT_ENOUGH_MANA,
    BURNED_OUT,
    REAGENTS_MISSING,
    MALFORMED_SPELL_STACK,
    EFFECT_FAILED,
    SUCCESS_REDUCE_MANA,
    SILENCED;
}
