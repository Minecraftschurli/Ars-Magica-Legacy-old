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
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public final class Ignition extends SpellComponent {
    @Override
    public boolean applyEffectBlock(ItemStack stack, World world, BlockPos pos, Direction blockFace, double impactX, double impactY, double impactZ, LivingEntity caster) {
        switch (blockFace) {
            case EAST:
            case WEST:
            case SOUTH:
            case NORTH:
                pos = pos.offset(blockFace);
        }
        if (world.isAirBlock(pos)) {
            if (!world.isRemote) world.setBlockState(pos, Blocks.FIRE.getDefaultState());
            return true;
        } else if (world.isAirBlock(pos.up())) {
            if (!world.isRemote) world.setBlockState(pos.up(), Blocks.FIRE.getDefaultState());
            return true;
        }
        return false;
    }

    @Override
    public boolean applyEffectEntity(ItemStack stack, World world, LivingEntity caster, Entity target) {
        if (target.isBurning()) return false;
        target.setFire(SpellUtil.modifyIntMul(3, stack, caster, target, world, SpellModifiers.DURATION));
        return true;
    }

    @Override
    public Set<Affinity> getAffinity() {
        return Sets.newHashSet(ModAffinities.FIRE.get());
    }

    @Override
    public float getAffinityShift(Affinity affinity) {
        return 0.01f;
    }

    @Override
    public float getManaCost(LivingEntity caster) {
        return 35;
    }

    @Override
    public EnumSet<SpellModifiers> getModifiers() {
        return EnumSet.of(SpellModifiers.DURATION);
    }

    @Override
    public void spawnParticles(ServerWorld world, double x, double y, double z, LivingEntity caster, Entity target, Random rand, int colorModifier) {
        for (int i = 0; i < 5; i++)
            ParticleUtil.addParticle(world, target, ModParticles.IMPLOSION, colorModifier, 0, x + rand.nextInt(2) - 1, y, z + rand.nextInt(2) - 1, (float) (rand.nextDouble() * 0.2 - 0.1), -0.2f, (float) (rand.nextDouble() * 0.2 - 0.1));
    }
}
