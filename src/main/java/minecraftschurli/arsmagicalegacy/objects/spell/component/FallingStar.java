package minecraftschurli.arsmagicalegacy.objects.spell.component;

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
 * @version 2019-11-18
 */
public class FallingStar extends SpellComponent {
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
        return spawnStar(stack, caster, caster, world, impact.x, impact.y + 50, impact.z);
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
        return spawnStar(stack, caster, target, world, target.posX, target.posY + 50, target.posZ);
    }

    /**
     * Gets the mana cost of the spell
     *
     * @param caster
     */
    @Override
    public float getManaCost(LivingEntity caster) {
        return 400;
    }

    /**
     * Gets the burnout of the spell
     *
     * @param caster
     */
    @Override
    public float getBurnout(LivingEntity caster) {
        return 120;
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
        return null;
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
    public void spawnParticles(World world, Vec3d pos, LivingEntity caster, Entity target, Random rand, int colorModifier) {}

    private boolean spawnStar(ItemStack spellStack, LivingEntity caster, Entity target, World world, double x, double y, double z){
/*
        List<EntityThrownRock> rocks = world.getEntitiesWithinAABB(EntityThrownRock.class, AxisAlignedBB.getBoundingBox(x - 10, y - 10, z - 10, x + 10, y + 10, z + 10));

        int damageMultitplier = SpellUtils.instance.getModifiedInt_Mul(15, spellStack, caster, target, world, 0, SpellModifiers.DAMAGE);

        for (EntityThrownRock rock : rocks){
            if (rock.getIsShootingStar())
                return false;
        }

        if (!world.isRemote){
            EntityThrownRock star = new EntityThrownRock(world);
            star.setPosition(x, world.getActualHeight(), z);
            star.setShootingStar(2 * damageMultitplier);
            star.setThrowingEntity(caster);
            star.setSpellStack(spellStack.copy());
            world.spawnEntityInWorld(star);
        }*/
        return true;
    }

    @Override
    public Object[] getRecipeItems() {
        return new Object[]{
                new ItemStack(ModItems.ARCANE_ESSENCE.get(), 1),
                new ItemStack(ModItems.ARCANE_ASH.get(), 1),
                new ItemStack(ModItems.ARCANE_ESSENCE.get(), 1),
                //new ItemStack(ModBlocks.MANA_BATTERY, 1),
                new ItemStack(Items.LAVA_BUCKET, 1)
        };
    }
}
