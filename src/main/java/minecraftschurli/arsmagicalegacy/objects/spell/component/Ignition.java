package minecraftschurli.arsmagicalegacy.spell.component;

import com.google.common.collect.*;
import minecraftschurli.arsmagicalegacy.api.affinity.*;
import minecraftschurli.arsmagicalegacy.api.spell.*;
import minecraftschurli.arsmagicalegacy.init.*;
import minecraftschurli.arsmagicalegacy.particles.*;
import minecraftschurli.arsmagicalegacy.utils.*;
import net.minecraft.block.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;

import java.util.*;

public class Ignition extends SpellComponent {
    @Override
    public boolean applyEffectBlock(ItemStack stack, World world, BlockPos pos, Direction blockFace, double impactX, double impactY, double impactZ, LivingEntity caster) {
        switch (blockFace) {
            case EAST:
            case WEST:
            case SOUTH:
            case NORTH:
                pos = pos.offset(blockFace);
                break;
            case DOWN:
            case UP:
            default:
                break;
        }
        Block block = world.getBlockState(pos).getBlock();
        if (world.isAirBlock(pos) || block.equals(Blocks.SNOW) || block instanceof BlockFlower) {
            if (!world.isRemote) world.setBlockState(pos, Blocks.FIRE.getDefaultState());
            return true;
        } else {
            pos = pos.up();
            block = world.getBlockState(pos).getBlock();
            if (world.isAirBlock(pos) || block.equals(Blocks.SNOW) || block instanceof BlockFlower) {
                if (!world.isRemote) world.setBlockState(pos, Blocks.FIRE.getDefaultState());
                return true;
            }
        }
        return false;
    }

    @Override
    public EnumSet<SpellModifiers> getModifiers() {
        return EnumSet.of(SpellModifiers.DURATION);
    }

    @Override
    public boolean applyEffectEntity(ItemStack stack, World world, LivingEntity caster, Entity target) {
        int burnTime = SpellUtils.getModifiedIntMul(3, stack, caster, target, world, SpellModifiers.DURATION);
        //burnTime = SpellUtils.modifyDurationBasedOnArmor(caster, burnTime);
        if (target.isBurning()) {
            return false;
        }
        target.setFire(burnTime);
        return true;
    }

    @Override
    public float getManaCost(LivingEntity caster) {
        return 35;
    }

    @Override
    public ItemStack[] reagents(LivingEntity caster) {
        return null;
    }

    @Override
    public void spawnParticles(World world, double x, double y, double z, LivingEntity caster, Entity target, Random rand, int colorModifier) {
        for (int i = 0; i < 5; ++i) {
            AMParticle particle = (AMParticle) ArsMagica2.proxy.particleManager.spawn(world, "explosion_2", x + 0.5, y + 0.5, z + 0.5);
            if (particle != null) {
                particle.addRandomOffset(1, 0.5, 1);
                particle.addVelocity(rand.nextDouble() * 0.2 - 0.1, 0.3, rand.nextDouble() * 0.2 - 0.1);
                particle.setAffectedByGravity();
                particle.setDontRequireControllers();
                particle.setMaxAge(5);
                particle.setParticleScale(0.1f);
                if (colorModifier > -1) {
                    particle.setRGBColorF(((colorModifier >> 16) & 0xFF) / 255.0f, ((colorModifier >> 8) & 0xFF) / 255.0f, (colorModifier & 0xFF) / 255.0f);
                }
            }
        }
    }

    @Override
    public Set<Affinity> getAffinity() {
        return Sets.newHashSet(Affinity.FIRE);
    }

    @Override
    public void encodeBasicData(CompoundNBT tag, Object[] recipe) {
    }

    @Override
    public ISpellIngredient[] getRecipe() {
        return new ISpellIngredient[]{
                new ItemStack(ModItems.ORANGE_RUNE.get()),
                Items.FLINT_AND_STEEL
        };
    }

    @Override
    public float getAffinityShift(Affinity affinity) {
        return 0.01f;
    }
}