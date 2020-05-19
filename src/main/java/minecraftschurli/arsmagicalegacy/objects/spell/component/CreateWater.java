package minecraftschurli.arsmagicalegacy.objects.spell.component;

import com.google.common.collect.Sets;
import minecraftschurli.arsmagicalegacy.api.affinity.Affinity;
import minecraftschurli.arsmagicalegacy.api.spell.SpellComponent;
import minecraftschurli.arsmagicalegacy.api.spell.SpellModifiers;
import minecraftschurli.arsmagicalegacy.init.ModAffinities;
import minecraftschurli.arsmagicalegacy.util.SpellUtil;
import net.minecraft.block.Blocks;
import net.minecraft.block.CauldronBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.EnumSet;
import java.util.Random;
import java.util.Set;

public final class CreateWater extends SpellComponent {
    @Override
    public boolean applyEffectBlock(ItemStack stack, World world, BlockPos pos, Direction blockFace, double impactX, double impactY, double impactZ, LivingEntity caster) {
        if (world.getBlockState(pos).getBlock() == Blocks.CAULDRON) {
            world.setBlockState(pos, Blocks.CAULDRON.getDefaultState().with(CauldronBlock.LEVEL, 3));
            return true;
        }
        pos = pos.offset(blockFace);
        if (world.isAirBlock(pos) || world.getFluidState(pos).getFluid() == Fluids.FLOWING_WATER || world.getFluidState(pos).getFluid() == Fluids.WATER) {
            world.setBlockState(pos, Blocks.WATER.getDefaultState());
            return true;
        }
        return false;
    }

    @Override
    public boolean applyEffectEntity(ItemStack stack, World world, LivingEntity caster, Entity target) {
        return SpellUtil.doBlockWithEntity(this, stack, world, caster, target);
    }

    @Override
    public Set<Affinity> getAffinity() {
        return Sets.newHashSet(ModAffinities.WATER.get());
    }

    @Override
    public float getAffinityShift(Affinity affinity) {
        return 0.001f;
    }

    @Override
    public float getManaCost(LivingEntity caster) {
        return 25;
    }

    @Override
    public EnumSet<SpellModifiers> getModifiers() {
        return EnumSet.noneOf(SpellModifiers.class);
    }

    @Override
    public void spawnParticles(ServerWorld world, double x, double y, double z, LivingEntity caster, Entity target, Random rand, int colorModifier) {
        for (int i = 0; i < 15; i++)
            world.addParticle(ParticleTypes.SPLASH, x - 0.5 + rand.nextDouble(), y, z - 0.5 + rand.nextDouble(), 0.5 - rand.nextDouble(), 0.1, 0.5 - rand.nextDouble());
    }
}
