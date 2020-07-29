package minecraftschurli.arsmagicalegacy.objects.spell.component;

import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
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
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.IGrowable;
import net.minecraft.block.MushroomBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.eventbus.api.Event;

public final class Grow extends SpellComponent {
    private final static List<Block> flowers = new ArrayList<>();

    static {
        flowers.addAll(BlockTags.FLOWERS.getAllElements());
    }

    @Override
    public boolean applyEffectBlock(ItemStack stack, World world, BlockPos pos, Direction blockFace, double impactX, double impactY, double impactZ, LivingEntity caster) {
        BlockState block = world.getBlockState(pos);
        BonemealEvent event = new BonemealEvent((PlayerEntity) caster, world, pos, world.getBlockState(pos), stack);
        if (MinecraftForge.EVENT_BUS.post(event)) return false;
        if (event.getResult() == Event.Result.ALLOW) return true;
        if (world.rand.nextInt(100) < 3 && block.isSolid()) {
            Collections.shuffle(flowers);
            for (Block flower : flowers)
                if (world.getBlockState(pos).canSustainPlant(world, pos.up(), Direction.UP, (IPlantable) flower)) {
                    if (!world.isRemote) world.setBlockState(pos.up(), flower.getDefaultState());
                    return true;
                }
        }
        if (block.getBlock() instanceof MushroomBlock) {
            if (!world.isRemote && world.rand.nextInt(10) < 1)
                ((MushroomBlock) block.getBlock()).grow((ServerWorld) world, world.rand, pos, block);
            return true;
        }
        if (block.getBlock() == Blocks.TALL_GRASS) {
            if (Blocks.TALL_GRASS.canSustainPlant(Blocks.TALL_GRASS.getDefaultState(), world, pos, null, (IPlantable) Blocks.TALL_GRASS)) {
                if (!world.isRemote && world.rand.nextInt(10) < 2)
                    world.setBlockState(pos, Blocks.TALL_GRASS.getDefaultState());
                return true;
            }
        }
        if (block.getBlock() == Blocks.DEAD_BUSH) {
            if (Blocks.TALL_GRASS.canSustainPlant(Blocks.DEAD_BUSH.getDefaultState(), world, pos, null, (IPlantable) Blocks.DEAD_BUSH)) {
                if (!world.isRemote && world.rand.nextInt(10) < 2)
                    world.setBlockState(pos, Blocks.DEAD_BUSH.getDefaultState());
                return true;
            }
        }
        if (block instanceof IGrowable) {
            IGrowable igrowable = (IGrowable) block;
            if (igrowable.canGrow(world, pos, block, world.isRemote)) {
                if (!world.isRemote && world.rand.nextInt(10) < 3 && igrowable.canUseBonemeal(world, world.rand, pos, block))
                    igrowable.grow((ServerWorld) world, world.rand, pos, block);
                return true;
            }
        }
        return true;
    }

    @Override
    public boolean applyEffectEntity(ItemStack stack, World world, LivingEntity caster, Entity target) {
        return SpellUtil.doBlockWithEntity(this, stack, world, caster, target);
    }

    @Override
    public Set<Affinity> getAffinity() {
        return Sets.newHashSet(ModAffinities.NATURE.get());
    }

    @Override
    public float getAffinityShift(Affinity affinity) {
        return 0.02f;
    }

    @Override
    public float getManaCost(LivingEntity caster) {
        return 17.4f;
    }

    @Override
    public EnumSet<SpellModifiers> getModifiers() {
        return EnumSet.noneOf(SpellModifiers.class);
    }

    @Override
    public void spawnParticles(ServerWorld world, double x, double y, double z, LivingEntity caster, Entity target, Random rand, int colorModifier) {
        for (int i = 0; i < 25; i++)
            ParticleUtil.addParticle(world, target, ModParticles.PLANT, colorModifier, 0, x + rand.nextInt(2) - 1, y + rand.nextInt(2) - 1, z + rand.nextInt(2) - 1);
    }
}
