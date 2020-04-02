package minecraftschurli.arsmagicalegacy.objects.spell.component;

import com.google.common.collect.Sets;
import minecraftschurli.arsmagicalegacy.api.affinity.Affinity;
import minecraftschurli.arsmagicalegacy.api.spell.SpellComponent;
import minecraftschurli.arsmagicalegacy.api.spell.SpellModifiers;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ISpellIngredient;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ItemStackSpellIngredient;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ItemTagSpellIngredient;
import minecraftschurli.arsmagicalegacy.init.ModItems;
import minecraftschurli.arsmagicalegacy.init.ModSpellParts;
import minecraftschurli.arsmagicalegacy.objects.entity.FireRainEntity;
import minecraftschurli.arsmagicalegacy.util.SpellUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.Tags;

import java.util.EnumSet;
import java.util.Set;

public final class FireRain extends SpellComponent {
    @Override
    public boolean applyEffectBlock(ItemStack stack, World world, BlockPos pos, Direction blockFace, double impactX, double impactY, double impactZ, LivingEntity caster) {
        if (!world.getEntitiesWithinAABB(FireRainEntity.class, new AxisAlignedBB(impactX - 10, impactY - 10, impactZ - 10, impactX + 10, impactY + 10, impactZ + 10)).isEmpty())
            return false;
        if (!world.isRemote) {
            FireRainEntity fire = new FireRainEntity(world);
            fire.setPosition(impactX, impactY, impactZ);
            fire.setRadius(SpellUtil.modifyIntAdd(2, stack, caster, caster, world, SpellModifiers.RADIUS) / 2f + 1);
            fire.setDamage((float) SpellUtil.modifyDoubleMul(1, stack, caster, caster, world, SpellModifiers.DAMAGE));
            fire.setTicksToExist(SpellUtil.modifyIntMul(100, stack, caster, caster, world, SpellModifiers.DURATION));
            fire.setCasterAndStack(caster, stack);
            world.addEntity(fire);
        }
        return true;
    }

    @Override
    public boolean applyEffectEntity(ItemStack stack, World world, LivingEntity caster, Entity target) {
        return SpellUtil.doBlockWithEntity(this, stack, world, caster, target);
    }

    @Override
    public Set<Affinity> getAffinity() {
        return Sets.newHashSet(ModSpellParts.FIRE.get());
    }

    @Override
    public float getAffinityShift(Affinity affinity) {
        return 0.1f;
    }

    @Override
    public float getManaCost(LivingEntity caster) {
        return 3000;
    }

    @Override
    public EnumSet<SpellModifiers> getModifiers() {
        return EnumSet.of(SpellModifiers.RADIUS, SpellModifiers.DAMAGE, SpellModifiers.DURATION, SpellModifiers.COLOR);
    }

    @Override
    public ISpellIngredient[] getRecipe() {
        return new ISpellIngredient[]{
                new ItemTagSpellIngredient(Tags.Items.NETHERRACK),
                new ItemStackSpellIngredient(new ItemStack(Items.LAVA_BUCKET)),
                new ItemStackSpellIngredient(new ItemStack(ModItems.ARCANE_ASH.get())),
                new ItemStackSpellIngredient(new ItemStack(ModItems.FIRE_ESSENCE.get(), 2))
        };
    }
}
