package minecraftschurli.arsmagicalegacy.objects.spell.component;

import com.google.common.collect.Sets;
import minecraftschurli.arsmagicalegacy.api.affinity.Affinity;
import minecraftschurli.arsmagicalegacy.api.spell.SpellComponent;
import minecraftschurli.arsmagicalegacy.api.spell.SpellModifiers;
import minecraftschurli.arsmagicalegacy.init.ModSpellParts;
import minecraftschurli.arsmagicalegacy.objects.entity.BlizzardEntity;
import minecraftschurli.arsmagicalegacy.util.SpellUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.EnumSet;
import java.util.Set;

public final class Blizzard extends SpellComponent {
    @Override
    public boolean applyEffectBlock(ItemStack stack, World world, BlockPos pos, Direction blockFace, double impactX, double impactY, double impactZ, LivingEntity caster) {
        if (!world.getEntitiesWithinAABB(BlizzardEntity.class, new AxisAlignedBB(impactX - 10, impactY - 10, impactZ - 10, impactX + 10, impactY + 10, impactZ + 10)).isEmpty())
            return false;
        if (!world.isRemote) {
            BlizzardEntity blizzard = new BlizzardEntity(world);
            blizzard.setPosition(impactX, impactY, impactZ);
            blizzard.setRadius(SpellUtil.modifyIntAdd(2, stack, caster, caster, world, SpellModifiers.RADIUS));
            blizzard.setTicksToExist(SpellUtil.modifyIntMul(100, stack, caster, caster, world, SpellModifiers.DURATION));
            blizzard.setDamage((float) SpellUtil.modifyDoubleMul(1, stack, caster, caster, world, SpellModifiers.DAMAGE));
            blizzard.setCasterAndStack(caster, stack);
            world.addEntity(blizzard);
        }
        return true;
    }

    @Override
    public boolean applyEffectEntity(ItemStack stack, World world, LivingEntity caster, Entity target) {
        return SpellUtil.doBlockWithEntity(this, stack, world, caster, target);
    }

    @Override
    public Set<Affinity> getAffinity() {
        return Sets.newHashSet(ModSpellParts.ICE.get());
    }

    @Override
    public float getAffinityShift(Affinity affinity) {
        return 0.1f;
    }

    @Override
    public float getManaCost(LivingEntity caster) {
        return 1200;
    }

    @Override
    public EnumSet<SpellModifiers> getModifiers() {
        return EnumSet.of(SpellModifiers.RADIUS, SpellModifiers.DAMAGE, SpellModifiers.DURATION, SpellModifiers.COLOR);
    }
}
