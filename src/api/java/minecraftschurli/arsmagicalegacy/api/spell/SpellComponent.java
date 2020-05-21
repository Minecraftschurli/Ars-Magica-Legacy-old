package minecraftschurli.arsmagicalegacy.api.spell;

import com.google.common.collect.ImmutableSet;
import java.util.Collections;
import java.util.Random;
import java.util.Set;
import javax.annotation.Nonnull;
import minecraftschurli.arsmagicalegacy.api.affinity.Affinity;
import minecraftschurli.arsmagicalegacy.api.config.Config;
import minecraftschurli.arsmagicalegacy.api.rituals.AbstractRitual;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.IParticleData;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

/**
 * @author Minecraftschurli
 * @version 2019-11-16
 */
public abstract class SpellComponent extends AbstractSpellPart {
    /**
     * Apply the effect to a single block
     * @apiNote This method is executed on both sides
     *
     * @param stack     The item stack that contains the effect data
     * @param world     The world the effect is in
     * @param blockPos  The coordinates of the block
     * @param blockFace The face of the block that was targeted
     * @param impactX   The x coordinate of the impact
     * @param impactY   The y coordinate of the impact
     * @param impactZ   The z coordinate of the impact
     * @param caster    The caster of the spell
     * @return True if the effect was successfully applied to the block
     */
    public abstract boolean applyEffectBlock(ItemStack stack, World world, BlockPos blockPos, Direction blockFace, double impactX, double impactY, double impactZ, LivingEntity caster);

    /**
     * Apply the effect to a single entity
     * @apiNote This method is executed on both sides
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
     * Spawn visual effects for the component
     * This is in fact not client side. Spells are entirely executed on the server.
     * use {@link ServerWorld#spawnParticle(IParticleData, double, double, double, int, double, double, double, double)} to spawn particles
     *
     * @param colorModifier The color from the color modifier. -1 if missing.
     */
    public void spawnParticles(ServerWorld world, double x, double y, double z, LivingEntity caster, Entity target, Random rand, int colorModifier) {}

    /**
     * Gets the affinity of the spell
     */
    public Set<Affinity> getAffinity() {
        return ImmutableSet.of();
    }

    /**
     * Gets the burnout of the spell
     */
    public float getBurnout(LivingEntity caster) {
        return getManaCost(caster) * Config.SERVER.MANA_BURNOUT_RATIO.get().floatValue();
    }

    /**
     * Gets the amount (before diminishing returns) that this component, when successfully applied,
     * shifts the caster's affinity
     *
     * @param affinity The affinity being shifted
     */
    public float getAffinityShift(Affinity affinity) {
        return 0;
    }

    /**
     * Gets the {@link AbstractRitual Ritual} associated with this {@link SpellComponent}
     *
     * @param  caster the caster of the spell
     * @return the {@link AbstractRitual Ritual} associated with this {@link SpellComponent} or null
     */
    public AbstractRitual getRitual(LivingEntity caster) {
        return null;
    }

    /**
     * Gets an iterable of reagents required to cast the spell
     *
     * @param caster the casting entity of the spell
     * @return an iterable of the reagents required to cast the spell
     */
    @Nonnull
    public Set<ItemStack> getReagents(LivingEntity caster) {
        return Collections.emptySet();
    }
}
