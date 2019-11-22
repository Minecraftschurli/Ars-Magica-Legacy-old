package minecraftschurli.arsmagicalegacy.objects.spell.shape;

import minecraftschurli.arsmagicalegacy.api.spellsystem.ISpellIngredient;
import minecraftschurli.arsmagicalegacy.api.spellsystem.SpellCastResult;
import minecraftschurli.arsmagicalegacy.api.spellsystem.SpellShape;
import minecraftschurli.arsmagicalegacy.objects.item.SpellItem;
import minecraftschurli.arsmagicalegacy.util.SpellHelper;
import minecraftschurli.arsmagicalegacy.util.SpellUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

/**
 * @author Minecraftschurli
 * @version 2019-11-20
 */
public class Self extends SpellShape {
    /**
     * Creates the target area/entity list and applies the effects to ground/mobs
     *
     * @param item     The spell being cast
     * @param stack    The itemstack representing the spell
     * @param caster   The caster of the spell
     * @param target   The specified target of the spell.  If this is not NULL, this is a forced target, and should be included with any other targets of the shape.  Otherwise the default spell shape logic should apply.
     * @param world    The world the spell is being cast in
     * @param pos      The coordinates of the spell's effect
     * @param side     The side the spell is applied on
     * @param giveXP   This is passed along to be given back to the SpellHelper where needed.
     * @param useCount The number of ticks the spell item has been in use for
     * @return The result of the spell cast.
     */
    @Override
    public SpellCastResult beginStackStage(SpellItem item, ItemStack stack, LivingEntity caster, LivingEntity target, World world, Vec3d pos, Direction side, boolean giveXP, int useCount) {
        SpellCastResult result = SpellHelper.applyStageToEntity(stack, caster, world, caster, 0, giveXP);
        if (result != SpellCastResult.SUCCESS){
            return result;
        }

        ItemStack newItemStack = SpellUtils.popStackStage(stack);
        return SpellHelper.applyStackStage(newItemStack, caster, target, pos, Direction.UP, world, true, giveXP, 0);
    }

    /**
     * Is this shape a valid shape for a channeled/maintained spell?
     */
    @Override
    public boolean isChanneled() {
        return false;
    }

    /**
     * Allows different shapes to vary the mana cost of a spell
     *
     * @param spellStack the itemstack representing the spell (useful if you want to vary based on added modifiers as well, for example)
     * @return
     */
    @Override
    public float getManaCostMultiplier(ItemStack spellStack) {
        return 0;
    }

    /**
     * Is the spell a terminus shape?  Return true if this component does not continue the spell chain when proccing.
     */
    @Override
    public boolean isTerminusShape() {
        return false;
    }

    /**
     * Is the shape a principal shape?  Return true if this spell requires another shape to proc (like runes and zones)
     */
    @Override
    public boolean isPrincipumShape() {
        return false;
    }

    @Override
    public ISpellIngredient[] getRecipeItems() {
        return new ISpellIngredient[0];
    }
}
