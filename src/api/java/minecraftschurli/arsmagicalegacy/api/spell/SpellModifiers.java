package minecraftschurli.arsmagicalegacy.api.spell;

/**
 * @author Minecraftschurli
 * @version 2019-11-26
 */
public enum SpellModifiers {
    SPEED(1),
    GRAVITY(0),
    BOUNCE(0),
    DAMAGE(4),
    HEALING(1),
    VELOCITY_ADDED(0),
    RADIUS(1),
    DURATION(1),
    PROCS(1),
    RANGE(8),
    TARGET_NONSOLID_BLOCKS(0),
    PIERCING(2),
    COLOR(0xFFFFFF),
    MINING_POWER(1),
    FORTUNE_LEVEL(1),
    SILKTOUCH_LEVEL(1),
    DISMEMBERING_LEVEL(1),
    BUFF_POWER(1),
    HOMING(0);

    public double defaultValue;
    public int defaultValueInt;

    SpellModifiers(double defaultValue) {
        this.defaultValue = defaultValue;
        this.defaultValueInt = (int) defaultValue;
    }
}
