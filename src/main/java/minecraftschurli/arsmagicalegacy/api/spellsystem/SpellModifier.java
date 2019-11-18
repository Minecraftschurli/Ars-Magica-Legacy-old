package minecraftschurli.arsmagicalegacy.api.spellsystem;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.EnumSet;

/**
 * @author Minecraftschurli
 * @version 2019-11-16
 */
public abstract class SpellModifier extends SpellPart<SpellModifier> {
    public SpellModifier (){}
    /**
     * Returns a list of the aspects of a spell that this modifier can change.
     *
     * @return the aspects modified by this {@link SpellModifier}
     */
    public abstract EnumSet<Type> getAspectsModified();

    /**
     * Returns the modified value for the specified type.
     *
     * @param type     The type of value we are modifying
     * @param caster   The caster
     * @param target   The target (can be the same as the caster)
     * @param world    The world in which the spell is being cast.
     * @param metadata Any metadata written to the spell for this modifier (obtained from getModifierMetadata)
     * @return A factor to multiply the default value by (or add, depending on the component's programming)
     */
    public abstract float getModifier(Type type, LivingEntity caster, Entity target, World world, byte[] metadata);

    /**
     * Gets the amount that adding this modifier to the spell alters the mana cost.
     *
     * @param spellStack The itemstack comprising the spell (in case you want to base it on other modifiers added)
     * @param stage      The stage in which this modifier has been added (if the modifier is added to multiple stages, this will be called multiple times, once per stage)
     * @param quantity   The quantity of this multiplier in the specified stage.
     */
    public abstract float getManaCostMultiplier(ItemStack spellStack, int stage, int quantity);

    public enum Type {
        SPEED(1.0D),
        GRAVITY(0),
        BOUNCE(0),
        DAMAGE(4.0D),
        HEALING(1.0D),
        VELOCITY_ADDED(0.0D),
        RADIUS(1.0D),
        DURATION(1.0D),
        PROCS(1),
        RANGE(8.0D),
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

        Type(double defaultValue){
            this.defaultValue = defaultValue;
            this.defaultValueInt = (int)defaultValue;
        }
    }
}
