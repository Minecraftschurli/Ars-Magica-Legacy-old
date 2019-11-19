package minecraftschurli.arsmagicalegacy.objects.spell.component;

import minecraftschurli.arsmagicalegacy.api.spellsystem.ISpellIngredient;
import minecraftschurli.arsmagicalegacy.api.spellsystem.ItemStackSpellIngredient;
import minecraftschurli.arsmagicalegacy.api.spellsystem.SpellComponent;
import minecraftschurli.arsmagicalegacy.init.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Random;

/**
 * @author Minecraftschurli
 * @version 2019-11-19
 */
public class LightningDamage extends SpellComponent {
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
    @Override
    public boolean applyEffectBlock(ItemStack stack, World world, BlockPos pos, Direction blockFace, Vec3d impact, LivingEntity caster) {
        return false;
    }

    /**
     * Apply the effect to a single entity
     *
     * @param stack  The stack representing the spell
     * @param world  The world the spell was cast in
     * @param caster The caster of the spell
     * @param target The current target of the spell
     * @return True if the effect was applied successfully to the entity
     */
    @Override
    public boolean applyEffectEntity(ItemStack stack, World world, LivingEntity caster, Entity target) {
        return false;
    }

    /**
     * Gets the mana cost of the spell
     *
     * @param caster
     */
    @Override
    public float getManaCost(LivingEntity caster) {
        return 0;
    }

    /**
     * Gets the burnout of the spell
     *
     * @param caster
     */
    @Override
    public float getBurnout(LivingEntity caster) {
        return 0;
    }

    /**
     * Gets the cooldown of the spell
     *
     * @param caster
     */
    @Override
    public float getCooldown(LivingEntity caster) {
        return 0;
    }

    /**
     * Gets any reagents that must be present in the caster's inventory in order
     * to cast the spell.
     *
     * @param caster
     */
    @Override
    public ItemStack[] getReagents(LivingEntity caster) {
        return new ItemStack[0];
    }

    /**
     * Spawn visual effects for the component
     *
     * @param world
     * @param pos
     * @param caster
     * @param target
     * @param rand
     * @param colorModifier The color from the color modifier.  -1 if missing.
     */
    @Override
    public void spawnParticles(World world, Vec3d pos, LivingEntity caster, Entity target, Random rand, int colorModifier) {

    }

    @Override
    public ISpellIngredient[] getRecipeItems() {
        return new ISpellIngredient[]{
                new ItemStackSpellIngredient(new ItemStack(ModItems.YELLOW_RUNE.get(), 1)),
                new ItemStackSpellIngredient(new ItemStack(Items.IRON_INGOT, 1)),
                new ItemStackSpellIngredient(new ItemStack(Items.STICK, 1)),
                new ItemStackSpellIngredient(new ItemStack(ModItems.VINTEUM.get(), 1))
        };
    }
}
