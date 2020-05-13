package minecraftschurli.arsmagicalegacy.api.event;

import minecraftschurli.arsmagicalegacy.api.spell.SpellModifiers;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.eventbus.api.Event;

/**
 * @author Minecraftschurli
 * @version 2019-11-17
 */
public class ModifierCalculatedEvent extends Event {
    public final SpellModifiers attribute;
    public final LivingEntity caster;
    public final ItemStack spell;
    public final double initialValue;
    public final OperationType operation;
    public double modifiedValue;

    public ModifierCalculatedEvent(ItemStack spell, LivingEntity caster, SpellModifiers attribute, double initialValue, double modifiedValue, OperationType operation) {
        this.spell = spell;
        this.caster = caster;
        this.attribute = attribute;
        this.initialValue = initialValue;
        this.modifiedValue = modifiedValue;
        this.operation = operation;
    }

    public enum OperationType {
        ADD,
        MULTIPLY
    }
}
