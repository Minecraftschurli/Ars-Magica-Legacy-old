package minecraftschurli.arsmagicalegacy.objects.spell.component;

import com.google.common.collect.Sets;
import minecraftschurli.arsmagicalegacy.api.affinity.Affinity;
import minecraftschurli.arsmagicalegacy.api.spell.SpellComponent;
import minecraftschurli.arsmagicalegacy.api.spell.SpellModifiers;
import minecraftschurli.arsmagicalegacy.init.ModSpellParts;
import minecraftschurli.arsmagicalegacy.objects.entity.ThrownRockEntity;
import minecraftschurli.arsmagicalegacy.util.SpellUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;

public final class FallingStar extends SpellComponent {
    @Override
    public boolean applyEffectBlock(ItemStack stack, World world, BlockPos pos, Direction blockFace, double impactX, double impactY, double impactZ, LivingEntity caster) {
        List<ThrownRockEntity> rocks = world.getEntitiesWithinAABB(ThrownRockEntity.class, new AxisAlignedBB(impactX - 10, impactY + 40, impactZ - 10, impactX + 10, impactY + 60, impactZ + 10));
        int damageMultitplier = SpellUtil.modifyIntMul(15, stack, caster, caster, world, SpellModifiers.DAMAGE);
        for (ThrownRockEntity rock : rocks) if (rock.getIsShootingStar()) return false;
        if (!world.isRemote) {
            ThrownRockEntity star = new ThrownRockEntity(world);
            star.setPosition(impactX, world.getActualHeight(), impactZ);
            star.setShootingStar(2 * damageMultitplier);
            star.setThrowingEntity(caster);
            star.setSpellStack(stack);
            world.addEntity(star);
        }
        return true;
    }

    @Override
    public boolean applyEffectEntity(ItemStack stack, World world, LivingEntity caster, Entity target) {
        return SpellUtil.doBlockWithEntity(this, stack, world, caster, target);
    }

    @Override
    public Set<Affinity> getAffinity() {
        return Sets.newHashSet(ModSpellParts.ARCANE.get());
    }

    @Override
    public float getAffinityShift(Affinity affinity) {
        return 0.05f;
    }

    @Override
    public float getManaCost(LivingEntity caster) {
        return 400;
    }

    @Override
    public EnumSet<SpellModifiers> getModifiers() {
        return EnumSet.of(SpellModifiers.DAMAGE, SpellModifiers.COLOR);
    }
}
