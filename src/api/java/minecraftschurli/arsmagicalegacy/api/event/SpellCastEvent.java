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
    public final Item spell;
    public final LivingEntity caster;
    public final ItemStack stack;
    public final boolean isChanneled;
    public float manaCost;
    public float burnout;
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
