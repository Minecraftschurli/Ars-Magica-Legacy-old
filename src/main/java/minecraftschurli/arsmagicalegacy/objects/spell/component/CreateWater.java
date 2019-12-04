package minecraftschurli.arsmagicalegacy.spell.component;

import com.google.common.collect.*;
import minecraftschurli.arsmagicalegacy.api.affinity.*;
import minecraftschurli.arsmagicalegacy.api.spell.*;
import minecraftschurli.arsmagicalegacy.init.*;
import net.minecraft.block.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;

import java.util.*;

public class CreateWater extends SpellComponent {
    @Override
    public boolean applyEffectBlock(ItemStack stack, World world, BlockPos pos, Direction blockFace, double impactX, double impactY, double impactZ, LivingEntity caster) {
        Block block = world.getBlockState(pos).getBlock();
        if (block == Blocks.CAULDRON) {
            world.setBlockState(pos, Blocks.CAULDRON.getDefaultState().withProperty(BlockCauldron.LEVEL, 3));
            return true;
        }
        pos = pos.offset(blockFace);
        block = world.getBlockState(pos).getBlock();
        if (world.isAirBlock(pos) || block == Blocks.SNOW_LAYER || block == Blocks.WATER || block == Blocks.FLOWING_WATER || block instanceof BlockFlower) {
            world.setBlockState(pos, Blocks.WATER.getDefaultState());
            return true;
        }
        return false;
    }

    @Override
    public boolean applyEffectEntity(ItemStack stack, World world, LivingEntity caster, Entity target) {
        return false;
    }

    @Override
    public float getManaCost(LivingEntity caster) {
        return 25;
    }

    @Override
    public ItemStack[] reagents(LivingEntity caster) {
        return null;
    }

    @Override
    public void spawnParticles(World world, double x, double y, double z, LivingEntity caster, Entity target, Random rand, int colorModifier) {
        for (int i = 0; i < 15; ++i) {
            world.spawnParticle(EnumParticleTypes.WATER_SPLASH, x - 0.5 + rand.nextDouble(), y, z - 0.5 + rand.nextDouble(), 0.5 - rand.nextDouble(), 0.1, 0.5 - rand.nextDouble());
        }
    }

    @Override
    public Set<Affinity> getAffinity() {
        return Sets.newHashSet(Affinity.WATER);
    }

    @Override
    public ISpellIngredient[] getRecipe() {
        return new ISpellIngredient[]{
                new ItemStack(ModItems.BLUE_RUNE.get()),
                Items.WATER_BUCKET
        };
    }

    @Override
    public EnumSet<SpellModifiers> getModifiers() {
        return EnumSet.noneOf(SpellModifiers.class);
    }

    @Override
    public float getAffinityShift(Affinity affinity) {
        return 0.001f;
    }

    @Override
    public void encodeBasicData(CompoundNBT tag, Object[] recipe) {
    }
}