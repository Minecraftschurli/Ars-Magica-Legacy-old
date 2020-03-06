package minecraftschurli.arsmagicalegacy.api.event;

import minecraftschurli.arsmagicalegacy.api.spell.SpellCastResult;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.eventbus.api.Event;

/**
 * @author Minecraftschurli
 * @version 2019-11-20
 */
public class SpellCastEvent extends Event {
    /**
     * The spell being cast
     */
    public final Item spell;
    /**
     * The caster of the spell
     */
    public final LivingEntity caster;
    /**
     * The itemstack representing the spell
     */
    public final ItemStack stack;
    /**
     * Is the spell channeled (maintained in any way, not the shape, so beam counts too)
     */
    public final boolean isChanneled;
    /**
     * The calculated mana cost of the spell
     */
    public float manaCost;
    /**
     * The calculated burnout of the spell
     */
    public float burnout;
    /**
     * The result to be returned after casting the spell
     */
    public SpellCastResult castResult;

    public SpellCastEvent(ItemStack stack, Item spell, LivingEntity caster, float manaCost, float burnout, boolean isChanneled) {
        this.caster = caster;
        this.spell = spell;
        this.manaCost = manaCost;
        this.burnout = burnout;
        this.stack = stack;
        this.isChanneled = isChanneled;
    }

    public static class Pre extends SpellCastEvent {

        public Pre(ItemStack stack, Item spell, LivingEntity caster, float manaCost, float burnout, boolean isChanneled) {
            super(stack, spell, caster, manaCost, burnout, isChanneled);
        }
    }

    public static class Post extends SpellCastEvent {

        public Post(ItemStack stack, Item spell, LivingEntity caster, float manaCost, float burnout, boolean isChanneled, SpellCastResult castResult) {
            super(stack, spell, caster, manaCost, burnout, isChanneled);
            this.castResult = castResult;
        }
    }
}
