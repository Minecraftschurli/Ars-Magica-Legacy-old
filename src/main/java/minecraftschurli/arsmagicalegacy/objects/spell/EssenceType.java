package minecraftschurli.arsmagicalegacy.objects.spell;

/**
 * @author Minecraftschurli
 * @version 2019-11-19
 */
public enum EssenceType {
    DARK(1),
    LIGHT(2),
    DARK_LIGHT(3),
    NEUTRAL(4),
    DARK_NEUTRAL(5),
    LIGHT_NEUTRAL(6),
    ANY(7);
    public final int i;

    EssenceType(int i) {
        this.i = i;
    }
}
