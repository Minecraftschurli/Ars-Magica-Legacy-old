package minecraftschurli.arsmagicalegacy.objects.spell.component;

import com.google.common.collect.Sets;
import java.util.EnumSet;
import java.util.Random;
import java.util.Set;
import minecraftschurli.arsmagicalegacy.api.affinity.Affinity;
import minecraftschurli.arsmagicalegacy.api.spell.SpellComponent;
import minecraftschurli.arsmagicalegacy.api.spell.SpellModifiers;
import minecraftschurli.arsmagicalegacy.init.ModAffinities;
import minecraftschurli.arsmagicalegacy.init.ModParticles;
import minecraftschurli.arsmagicalegacy.util.ParticleUtil;
import minecraftschurli.arsmagicalegacy.util.SpellUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public final class LifeDrain extends SpellComponent {
    @Override
    public boolean applyEffectBlock(ItemStack stack, World world, BlockPos pos, Direction blockFace, double impactX, double impactY, double impactZ, LivingEntity caster) {
        return false;
    }

    @Override
    public boolean applyEffectEntity(ItemStack stack, World world, LivingEntity caster, Entity target) {
        if (world.isRemote || !(target instanceof LivingEntity) || ((LivingEntity) target).isEntityUndead())
            return true;
        if (SpellUtil.attackWithType(stack, target, DamageSource.causeIndirectMagicDamage(caster, caster), SpellUtil.modifyDamage(caster, SpellUtil.modifyIntAdd(4, stack, caster, target, world, SpellModifiers.DAMAGE)))) {
            caster.heal((int) Math.ceil(SpellUtil.modifyIntAdd(4, stack, caster, target, world, SpellModifiers.DAMAGE) / 4f));
            return true;
        }
        return false;
    }

    @Override
    public Set<Affinity> getAffinity() {
        return Sets.newHashSet(ModAffinities.LIFE.get());
    }

    @Override
    public float getAffinityShift(Affinity affinity) {
        return 0.01f;
    }

    @Override
    public float getManaCost(LivingEntity caster) {
        return 300;
    }

    @Override
    public EnumSet<SpellModifiers> getModifiers() {
        return EnumSet.of(SpellModifiers.DAMAGE);
    }

    @Override
    public void spawnParticles(ServerWorld world, double x, double y, double z, LivingEntity caster, Entity target, Random rand, int colorModifier) {
        for (int i = 0; i < 15; i++) ParticleUtil.addParticle(world, target, ModParticles.EMBER, colorModifier, 0xff3232, x + rand.nextInt(2) - 1, y + rand.nextInt(2) - 1, z + rand.nextInt(2) - 1);
    }
}
