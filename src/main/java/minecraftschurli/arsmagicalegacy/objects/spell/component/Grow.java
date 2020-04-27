package minecraftschurli.arsmagicalegacy.objects.spell.component;

import com.google.common.collect.Sets;
import minecraftschurli.arsmagicalegacy.api.affinity.Affinity;
import minecraftschurli.arsmagicalegacy.api.spell.SpellComponent;
import minecraftschurli.arsmagicalegacy.api.spell.SpellModifiers;
import minecraftschurli.arsmagicalegacy.init.ModAffinities;
import minecraftschurli.arsmagicalegacy.util.SpellUtil;
import net.minecraft.block.*;
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

import java.util.*;

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
    public void spawnParticles(World world, double x, double y, double z, LivingEntity caster, Entity target, Random rand, int colorModifier) {
//        for (int i = 0; i < 25; i++) {
//            AMParticle particle = (AMParticle) ArsMagicaLegacy.proxy.particleManager.spawn(world, "plant", x + 0.5, y + 1, z + 0.5);
//            if (particle != null) {
//                particle.addRandomOffset(1, 1, 1);
//                particle.AddParticleController(new ParticleFloatUpward(particle, 0, 0.1f, 1, false));
//                particle.AddParticleController(new ParticleOrbitPoint(particle, x + 0.5, y + 0.5, z + 0.5, 2, false).setIgnoreYCoordinate(true).SetOrbitSpeed(0.1f).SetTargetDistance(0.3f + rand.nextDouble() * 0.3));
//                particle.AddParticleController(new ParticleFadeOut(particle, 1, false).setFadeSpeed(0.05f).setKillParticleOnFinish(true));
//                particle.setMaxAge(20);
//                particle.setParticleScale(0.1f);
//                if (colorModifier > -1) particle.setRGBColorF(((colorModifier >> 16) & 0xFF) / 255, ((colorModifier >> 8) & 0xFF) / 255, (colorModifier & 0xFF) / 255);
//            }
//        }
    }
}
