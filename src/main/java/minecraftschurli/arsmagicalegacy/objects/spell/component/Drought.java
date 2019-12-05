package minecraftschurli.arsmagicalegacy.spell.component;

import com.google.common.collect.*;
import minecraftschurli.arsmagicalegacy.api.affinity.*;
import minecraftschurli.arsmagicalegacy.api.spell.*;
import minecraftschurli.arsmagicalegacy.init.*;
import minecraftschurli.arsmagicalegacy.particles.*;
import net.minecraft.block.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;

import java.util.*;

public class Drought extends SpellComponent {
    @Override
    public boolean applyEffectBlock(ItemStack stack, World world, BlockPos pos, Direction blockFace, double impactX, double impactY, double impactZ, LivingEntity caster) {
        Block block = world.getBlockState(pos).getBlock();
        if (block instanceof BlockFlower || block instanceof BlockTallGrass) {
            world.setBlockState(pos, Blocks.DEADBUSH.getDefaultState());
            return true;
        } else if (block == Blocks.GRASS || block == Blocks.MYCELIUM || block == Blocks.SANDSTONE || block == Blocks.DIRT) {
            world.setBlockState(pos, Blocks.SAND.getDefaultState());
            return true;
        } else if (block == Blocks.STONE) {
            world.setBlockState(pos, Blocks.COBBLESTONE.getDefaultState());
            return true;
        } else if (block == Blocks.STONEBRICK) {
            world.setBlockState(pos, Blocks.STONEBRICK.getDefaultState().withProperty(BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.CRACKED));
            return true;
        }
        if (block == Blocks.WATER || block == Blocks.FLOWING_WATER) {
            world.setBlockToAir(pos);
            return true;
        } else {
            pos = pos.offset(blockFace);
            block = world.getBlockState(pos).getBlock();
            if (block == Blocks.WATER || block == Blocks.FLOWING_WATER) {
                world.setBlockToAir(pos);
                return true;
            }
        }
        return false;
    }

    @Override
    public EnumSet<SpellModifiers> getModifiers() {
        return EnumSet.noneOf(SpellModifiers.class);
    }

    @Override
    public boolean applyEffectEntity(ItemStack stack, World world, LivingEntity caster, Entity target) {
        return false;
    }

    @Override
    public float getManaCost(LivingEntity caster) {
        return 60;
    }

    @Override
    public ItemStack[] reagents(LivingEntity caster) {
        return null;
    }

    @Override
    public void spawnParticles(World world, double x, double y, double z, LivingEntity caster, Entity target, Random rand, int colorModifier) {
        for (int i = 0; i < 25; ++i) {
            AMParticle particle = (AMParticle) ArsMagica2.proxy.particleManager.spawn(world, "ember", x + 0.5, y + 1, z + 0.5);
            if (particle != null) {
                particle.addRandomOffset(1, 0, 1);
                particle.AddParticleController(new ParticleFloatUpward(particle, 0, 0.1f, 1, false));
                particle.AddParticleController(new ParticleFadeOut(particle, 1, false).setFadeSpeed(0.05f));
                particle.setAffectedByGravity();
                particle.setRGBColorF(0.9f, 0.8f, 0.5f);
                particle.setMaxAge(40);
                particle.setParticleScale(0.1f);
                if (colorModifier > -1) {
                    particle.setRGBColorF(((colorModifier >> 16) & 0xFF) / 255.0f, ((colorModifier >> 8) & 0xFF) / 255.0f, (colorModifier & 0xFF) / 255.0f);
                }
            }
        }
    }

    @Override
    public Set<Affinity> getAffinity() {
        return Sets.newHashSet(Affinity.FIRE, Affinity.AIR);
    }

    @Override
    public ISpellIngredient[] getRecipe() {
        return new ISpellIngredient[]{
                new ItemStack(ModItems.ORANGE_RUNE.get()),
                Blocks.SAND,
                Blocks.DEADBUSH
        };
    }

    @Override
    public float getAffinityShift(Affinity affinity) {
        if (affinity == Affinity.FIRE)
            return 0.008f;
        else
            return 0.004f;
    }

    @Override
    public void encodeBasicData(CompoundNBT tag, Object[] recipe) {
    }
}