package minecraftschurli.arsmagicalegacy.api.spellsystem;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Random;

/**
 * @author Minecraftschurli
 * @version 2019-11-16
 */
public abstract class SpellComponent extends SpellPart<SpellComponent> {
    public SpellComponent (){}
    /**
     * Apply the effect to a single block
     *
     * @param stack     The item stack that contains the effect data
     * @param world     The world the effect is in
     * @param pos       The position of the block
     * @param blockFace The face of the block that was targeted
     * @param impact    The coordinates of the impact
     * @param caster    The caster of the spell
     * @return True if the effect was successfully applied to the block
     */
    public abstract boolean applyEffectBlock(ItemStack stack, World world, BlockPos pos, Direction blockFace, Vec3d impact, LivingEntity caster);

    /**
     * Apply the effect to a single entity
     *
     * @param stack  The stack representing the spell
     * @param world  The world the spell was cast in
     * @param caster The caster of the spell
     * @param target The current target of the spell
     * @return True if the effect was applied successfully to the entity
     */
    public abstract boolean applyEffectEntity(ItemStack stack, World world, LivingEntity caster, Entity target);

    /**
     * Gets the mana cost of the spell
     */
    public abstract float getManaCost(LivingEntity caster);

    /**
     * Gets the burnout of the spell
     */
    public abstract float getBurnout(LivingEntity caster);

    /**
     * Gets the cooldown of the spell
     */
    public abstract float getCooldown(LivingEntity caster);

    /**
     * Gets any reagents that must be present in the caster's inventory in order
     * to cast the spell.
     */
    public abstract ItemStack[] getReagents(LivingEntity caster);

    /**
     * Spawn visual effects for the component
     *
     * @param colorModifier The color from the color modifier.  -1 if missing.
     */
    public abstract void spawnParticles(World world, Vec3d pos, LivingEntity caster, Entity target, Random rand, int colorModifier);

    /**
     * Gets the affinity of the spell
     */
    //public EnumSet<Affinity> getAffinity();

    /**
     * Gets the amount (before diminishing returns) that this component, when successfully applied,
     * shifts the caster's affinity
     *
     * @param affinity The affinity being shifted
     */
    //public float getAffinityShift(Affinity affinity);
}
