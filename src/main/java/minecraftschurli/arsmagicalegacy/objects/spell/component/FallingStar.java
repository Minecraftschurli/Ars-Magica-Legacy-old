package minecraftschurli.arsmagicalegacy.spell.component;

import com.google.common.collect.*;
import minecraftschurli.arsmagicalegacy.api.affinity.*;
import minecraftschurli.arsmagicalegacy.api.spell.*;
import minecraftschurli.arsmagicalegacy.entity.*;
import minecraftschurli.arsmagicalegacy.init.*;
import minecraftschurli.arsmagicalegacy.items.*;
import minecraftschurli.arsmagicalegacy.utils.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;

import java.util.*;

public class FallingStar extends SpellComponent {
    @Override
    public ISpellIngredient[] getRecipe() {
        return new ISpellIngredient[]{
                new ItemStack(ModItems.ARCANE_ESSENCE.get()),
                new ItemStack(ModItems.itemOre, 1, ItemOre.META_ARCANEASH),
                new ItemStack(ModItems.ARCANE_ESSENCE.get()),
                ModBlocks.manaBattery,
                Items.LAVA_BUCKET
        };
    }

    private boolean spawnStar(ItemStack spellStack, LivingEntity caster, Entity target, World world, double x, double y, double z) {
        List<EntityThrownRock> rocks = world.getEntitiesWithinAABB(EntityThrownRock.class, new AxisAlignedBB(x - 10, y - 10, z - 10, x + 10, y + 10, z + 10));
        int damageMultitplier = SpellUtils.getModifiedIntMul(15, spellStack, caster, target, world, SpellModifiers.DAMAGE);
        for (EntityThrownRock rock : rocks) {
            if (rock.getIsShootingStar())
                return false;
        }
        if (!world.isRemote) {
            EntityThrownRock star = new EntityThrownRock(world);
            star.setPosition(x, world.getActualHeight(), z);
            star.setShootingStar(2 * damageMultitplier);
            star.setThrowingEntity(caster);
            star.setSpellStack(spellStack.copy());
            world.addEntity(star);
        }
        return true;
    }

    @Override
    public EnumSet<SpellModifiers> getModifiers() {
        return EnumSet.of(SpellModifiers.DAMAGE, SpellModifiers.COLOR);
    }

    @Override
    public boolean applyEffectBlock(ItemStack stack, World world, BlockPos pos, Direction blockFace, double impactX, double impactY, double impactZ, LivingEntity caster) {
        return spawnStar(stack, caster, caster, world, impactX, impactY + 50, impactZ);
    }

    @Override
    public boolean applyEffectEntity(ItemStack stack, World world, LivingEntity caster, Entity target) {
        return spawnStar(stack, caster, target, world, target.posX, target.posY + 50, target.posZ);
    }

    @Override
    public float getManaCost(LivingEntity caster) {
        return 400;
    }

    @Override
    public ItemStack[] reagents(LivingEntity caster) {
        return null;
    }

    @Override
    public void spawnParticles(World world, double x, double y, double z, LivingEntity caster, Entity target, Random rand, int colorModifier) {
    }

    @Override
    public Set<Affinity> getAffinity() {
        return Sets.newHashSet(Affinity.ARCANE);
    }

    @Override
    public float getAffinityShift(Affinity affinity) {
        return 0.05f;
    }

    @Override
    public void encodeBasicData(CompoundNBT tag, Object[] recipe) {
    }
}