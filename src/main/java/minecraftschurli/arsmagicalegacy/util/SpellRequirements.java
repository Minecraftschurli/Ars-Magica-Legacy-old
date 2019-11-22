package minecraftschurli.arsmagicalegacy.util;

import net.minecraft.item.ItemStack;

import java.util.ArrayList;

/**
 * @author Minecraftschurli
 * @version 2019-11-20
 */
public class SpellRequirements{
    public final float manaCost;
    public final float burnout;
    public final ArrayList<ItemStack> reagents;

    public SpellRequirements(float mana, float burnout, ArrayList<ItemStack> reagents){
        this.manaCost = mana;
        this.burnout = burnout;
        this.reagents = reagents;
    }
}
