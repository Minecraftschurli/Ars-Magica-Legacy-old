package minecraftschurli.arsmagicalegacy.util;

import java.util.List;
import net.minecraft.item.ItemStack;

/**
 * @author Minecraftschurli
 * @version 2019-11-20
 */
public class SpellRequirements {
    public final float manaCost;
    public final float burnout;
    public final List<ItemStack> reagents;

    public SpellRequirements(float mana, float burnout, List<ItemStack> reagents) {
        this.manaCost = mana;
        this.burnout = burnout;
        this.reagents = reagents;
    }
}
