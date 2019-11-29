package minecraftschurli.arsmagicalegacy.event;

import minecraftschurli.arsmagicalegacy.api.spellsystem.*;
import minecraftschurli.arsmagicalegacy.objects.item.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraftforge.eventbus.api.*;

/**
 * @author Minecraftschurli
 * @version 2019-11-20
 */
public class SpellCastingEvent extends Event {
    /**
     * The spell being cast
     */
    public final SpellItem spell;
    /**
     * The caster of the spell
     */
    public final LivingEntity caster;
    /**
     * The calculated mana cost of the spell
     */
    public final float manaCost;
    /**
     * The calculated burnout of the spell
     */
    public final float burnout;
    /**
     * The itemstack representing the spell
     */
    public final ItemStack stack;
    /**
     * Is the spell channeled (maintained in any way, not the shape, so beam counts too)
     */
    public final boolean isChanneled;
    /**
     * The result to be returned after casting the spell
     */
    public SpellCastResult castResult;

    public SpellCastingEvent(ItemStack stack, SpellItem spell, LivingEntity caster, float manaCost, float burnout, boolean isChanneled) {
        this.caster = caster;
        this.spell = spell;
        this.manaCost = manaCost;
        this.burnout = burnout;
        this.stack = stack;
        this.isChanneled = isChanneled;
    }

    public static class Pre extends SpellCastingEvent {

        public Pre(ItemStack stack, SpellItem spell, LivingEntity caster, float manaCost, float burnout, boolean isChanneled) {
            super(stack, spell, caster, manaCost, burnout, isChanneled);
        }
    }

    public static class Post extends SpellCastingEvent {

        public Post(ItemStack stack, SpellItem spell, LivingEntity caster, float manaCost, float burnout, boolean isChanneled, SpellCastResult castResult) {
            super(stack, spell, caster, manaCost, burnout, isChanneled);
            this.castResult = castResult;
        }
    }
}
