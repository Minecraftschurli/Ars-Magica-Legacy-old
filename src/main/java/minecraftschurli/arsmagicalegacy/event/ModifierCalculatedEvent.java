package minecraftschurli.arsmagicalegacy.event;

import minecraftschurli.arsmagicalegacy.api.spellsystem.SpellModifier;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.eventbus.api.Event;

/**
 * @author Minecraftschurli
 * @version 2019-11-17
 */
public class ModifierCalculatedEvent extends Event {
    //The attribute being modified
    public final SpellModifier.Type attribute;
    //The caster of the spell
    public final LivingEntity caster;
    //The spell being cast.  This is a copy of the ItemStack.
    public final ItemStack spell;
    //the initial value of the variable
    public final double initialValue;
    //the type of operation being used to modify the value
    public final OperationType operation;

    //the final modified value of the variable
    public double modifiedValue;

    public enum OperationType{
        ADD,
        MULTIPLY
    }

    public ModifierCalculatedEvent(ItemStack spell, LivingEntity caster, SpellModifier.Type attribute, double initialValue, double modifiedValue, OperationType operation){
        this.spell = spell;
        this.caster = caster;
        this.attribute = attribute;
        this.initialValue = initialValue;
        this.modifiedValue = modifiedValue;
        this.operation = operation;
    }
}
