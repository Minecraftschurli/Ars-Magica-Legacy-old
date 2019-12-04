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

public class Accelerate extends SpellComponent {
    @Override
    public boolean applyEffectEntity(ItemStack stack, World world, LivingEntity caster, Entity target) {
        caster.setAIMoveSpeed(caster.getAIMoveSpeed() * 1.6f);
        return true;
    }

    @Override
    public float getManaCost(LivingEntity caster) {
        return 6;
    }

    @Override
    public ItemStack[] reagents(LivingEntity caster) {
        return null;
    }

    @Override
    public void spawnParticles(World world, double x, double y, double z, LivingEntity caster, Entity target, Random rand, int colorModifier) {
        AMParticle particle = (AMParticle) ArsMagica2.proxy.particleManager.spawn(world, "sparkle", x, y, z);
        if (particle != null) {
            particle.AddParticleController(new ParticleOrbitEntity(particle, caster, 0.1f, 1, false).SetTargetDistance(rand.nextDouble() + 0.5));
            particle.setMaxAge(25 + rand.nextInt(10));
            if (colorModifier > -1) {
                particle.setRGBColorF(((colorModifier >> 16) & 0xFF) / 255.0f, ((colorModifier >> 8) & 0xFF) / 255.0f, (colorModifier & 0xFF) / 255.0f);
            }
        }
    }

    @Override
    public ISpellIngredient[] getRecipe() {
        return new ISpellIngredient[]{
                new ItemStack(ModItems.YELLOW_RUNE.get()),
                Items.LEATHER_BOOTS,
                Items.REDSTONE
        };
    }

    @Override
    public float getAffinityShift(Affinity affinity) {
        if (affinity.equals(Affinity.AIR))
            return 1F;
        return 0;
    }

    @Override
    public void encodeBasicData(CompoundNBT tag, Object[] recipe) {
    }

    @Override
    public boolean applyEffectBlock(ItemStack stack, World world,
                                    BlockPos blockPos, Direction blockFace, double impactX,
                                    double impactY, double impactZ, LivingEntity caster) {
        if (world.rand.nextDouble() < 0.5) {
            Block block = world.getBlockState(blockPos).getBlock();
            if (block != Blocks.AIR) {
                block.updateTick(world, blockPos, world.getBlockState(blockPos), world.rand);
            }
        }
        return true;
    }

    @Override
    public EnumSet<SpellModifiers> getModifiers() {
        return EnumSet.noneOf(SpellModifiers.class);
    }

    @Override
    public Set<Affinity> getAffinity() {
        return Sets.newHashSet(Affinity.AIR);
    }
}