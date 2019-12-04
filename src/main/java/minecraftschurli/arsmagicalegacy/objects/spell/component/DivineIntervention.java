package minecraftschurli.arsmagicalegacy.spell.component;

import com.google.common.collect.*;
import minecraftschurli.arsmagicalegacy.api.affinity.*;
import minecraftschurli.arsmagicalegacy.api.spell.*;
import minecraftschurli.arsmagicalegacy.init.*;
import minecraftschurli.arsmagicalegacy.particles.*;
import minecraftschurli.arsmagicalegacy.utils.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;

import java.util.*;

public class DivineIntervention extends SpellComponent {
    @Override
    public boolean applyEffectEntity(ItemStack stack, World world, LivingEntity caster, Entity target) {
        if (world.isRemote || !(target instanceof LivingEntity)) return true;
        if (((LivingEntity) target).isPotionActive(PotionEffectsDefs.astralDistortion)) {
            if (target instanceof PlayerEntity)
                ((PlayerEntity) target).addChatMessage(new TextComponentString("The distortion around you prevents you from teleporting"));
            return true;
        }
        if (target.dimension == 1) {
            if (target instanceof PlayerEntity)
                ((PlayerEntity) target).addChatMessage(new TextComponentString("Nothing happens..."));
            return true;
        } else if (target.dimension == 0) {
            BlockPos coords = target instanceof PlayerEntity ? ((PlayerEntity) target).getBedLocation(target.dimension) : null;
            if (coords == null || (coords == BlockPos.ORIGIN)) {
                coords = world.getSpawnPoint();
            }
            while (world.getBlockState(coords).getBlock() != Blocks.AIR && world.getBlockState(coords.up()).getBlock() != Blocks.AIR) {
                coords = coords.up();
            }
            target.setPositionAndUpdate(coords.getX() + 0.5, coords.getY(), coords.getZ() + 0.5);
        } else {
            DimensionUtilities.doDimensionTransfer((LivingEntity) target, 0);
            ArsMagica2.proxy.addDeferredDimensionTransfer((LivingEntity) target, 0);
        }
        return true;
    }

    @Override
    public EnumSet<SpellModifiers> getModifiers() {
        return EnumSet.noneOf(SpellModifiers.class);
    }

    @Override
    public float getManaCost(LivingEntity caster) {
        return 400;
    }

    @Override
    public ItemStack[] reagents(LivingEntity caster) {
        return new ItemStack[]{new ItemStack(ModItems.ENDER_ESSENCE.get())};
    }

    @Override
    public void spawnParticles(World world, double x, double y, double z, LivingEntity caster, Entity target, Random rand, int colorModifier) {
        for (int i = 0; i < 100; ++i) {
            AMParticle particle = (AMParticle) ArsMagica2.proxy.particleManager.spawn(world, "arcane", x, y - 1, z);
            if (particle != null) {
                particle.addRandomOffset(1, 1, 1);
                if (rand.nextBoolean())
                    particle.AddParticleController(new ParticleOrbitEntity(particle, target, 0.1f, 1, false).SetTargetDistance(rand.nextDouble() + 0.5));
                else
                    particle.AddParticleController(new ParticleOrbitPoint(particle, x, y, z, 1, false).SetOrbitSpeed(0.1f).SetTargetDistance(rand.nextDouble() + 0.5));
                particle.setMaxAge(25 + rand.nextInt(10));
                if (colorModifier > -1) {
                    particle.setRGBColorF(((colorModifier >> 16) & 0xFF) / 255.0f, ((colorModifier >> 8) & 0xFF) / 255.0f, (colorModifier & 0xFF) / 255.0f);
                }
            }
        }
    }

    @Override
    public Set<Affinity> getAffinity() {
        return Sets.newHashSet(Affinity.ENDER);
    }

    @Override
    public ISpellIngredient[] getRecipe() {
        return new ISpellIngredient[]{
                new ItemStack(ModItems.PURPLE_RUNE.get()),
                Items.BED,
                Items.ENDER_PEARL
        };
    }

    @Override
    public float getAffinityShift(Affinity affinity) {
        return 0.4f;
    }

    @Override
    public void encodeBasicData(CompoundNBT tag, Object[] recipe) {
    }

    @Override
    public boolean applyEffectBlock(ItemStack stack, World world, BlockPos blockPos, Direction blockFace,
                                    double impactX, double impactY, double impactZ, LivingEntity caster) {
        return false;
    }
}