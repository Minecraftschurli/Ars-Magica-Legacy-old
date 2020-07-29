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
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowerBlock;
import net.minecraft.block.GravelBlock;
import net.minecraft.block.SnowyDirtBlock;
import net.minecraft.block.TallGrassBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public final class Drought extends SpellComponent {
    @Override
    public boolean applyEffectBlock(ItemStack stack, World world, BlockPos pos, Direction blockFace, double impactX, double impactY, double impactZ, LivingEntity caster) {
        Block block = world.getBlockState(pos).getBlock();
        if (block instanceof FlowerBlock || block instanceof TallGrassBlock) {
            world.setBlockState(pos, Blocks.DEAD_BUSH.getDefaultState());
            return true;
        } else if (block == Blocks.GRASS || block == Blocks.MYCELIUM || block == Blocks.COARSE_DIRT || block == Blocks.DIRT || block instanceof SnowyDirtBlock || block instanceof GravelBlock) {
            world.setBlockState(pos, Blocks.SAND.getDefaultState());
            return true;
        } else if (block == Blocks.STONE) {
            world.setBlockState(pos, Blocks.COBBLESTONE.getDefaultState());
            return true;
        } else if (block == Blocks.STONE_BRICKS) {
            world.setBlockState(pos, Blocks.CRACKED_STONE_BRICKS.getDefaultState());
            return true;
        } else if (block == Blocks.WATER) {
            world.setBlockState(pos, Blocks.AIR.getDefaultState());
            return true;
        } else {
            pos = pos.offset(blockFace);
            block = world.getBlockState(pos).getBlock();
            if (block == Blocks.WATER) {
                world.setBlockState(pos, Blocks.AIR.getDefaultState());
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean applyEffectEntity(ItemStack stack, World world, LivingEntity caster, Entity target) {
        return SpellUtil.doBlockWithEntity(this, stack, world, caster, target);
    }

    @Override
    public Set<Affinity> getAffinity() {
        return Sets.newHashSet(ModAffinities.FIRE.get(), ModAffinities.AIR.get());
    }

    @Override
    public float getAffinityShift(Affinity affinity) {
        return affinity == ModAffinities.FIRE.get() ? 0.008f : 0.004f;
    }

    @Override
    public float getManaCost(LivingEntity caster) {
        return 60;
    }

    @Override
    public EnumSet<SpellModifiers> getModifiers() {
        return EnumSet.noneOf(SpellModifiers.class);
    }

    @Override
    public void spawnParticles(ServerWorld world, double x, double y, double z, LivingEntity caster, Entity target, Random rand, int colorModifier) {
        for (int i = 0; i < 25; i++)
            ParticleUtil.addParticle(world, target, ModParticles.EMBER, colorModifier, 0xe5b27f, x + rand.nextInt(2) - 1, y, z + rand.nextInt(2) - 1, 0, -0.5f, 0);
    }
}
