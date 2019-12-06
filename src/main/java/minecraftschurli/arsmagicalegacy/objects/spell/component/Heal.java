package minecraftschurli.arsmagicalegacy.objects.spell.component;

import minecraftschurli.arsmagicalegacy.api.spell.SpellComponent;
import minecraftschurli.arsmagicalegacy.api.spell.SpellModifiers;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ISpellIngredient;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ItemStackSpellIngredient;
import minecraftschurli.arsmagicalegacy.init.ModItems;
import minecraftschurli.arsmagicalegacy.util.SpellUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.EnumSet;
import java.util.Random;

public class Heal extends SpellComponent {
    @Override
    public boolean applyEffectBlock(ItemStack stack, World world, BlockPos blockPos, Direction blockFace, double impactX, double impactY, double impactZ, LivingEntity caster) {
        return false;
    }

    @Override
    public boolean applyEffectEntity(ItemStack stack, World world, LivingEntity caster, Entity target) {
        if (target instanceof LivingEntity) {
            if (((LivingEntity) target).isEntityUndead()) {
                int healing = SpellUtils.getModifiedIntMul(10, stack, caster, target, world, SpellModifiers.HEALING);
                target.setFire(2);
//                return SpellUtils.attackTargetSpecial(stack, target, DamageSources.causeHolyDamage(caster), (float) (healing * (0.5f + 2 * AffinityData.For(caster).getAffinityDepth(Affinity.LIFE))));
            } else {
                int healing = SpellUtils.getModifiedIntMul(2, stack, caster, target, world, SpellModifiers.HEALING);
//                if (caster.isNonBoss()) healing *= 1F + AffinityData.For(caster).getAffinityDepth(Affinity.LIFE);
//                if (EntityExtension.For((LivingEntity) target).getHealCooldown() == 0) {
//                    ((LivingEntity) target).heal(healing);
//                    EntityExtension.For((LivingEntity) target).setHealCooldown(60);
//                    return true;
//                }
            }
        }
        return false;
    }

    @Override
    public EnumSet<SpellModifiers> getModifiers() {
        return EnumSet.of(SpellModifiers.HEALING);
    }

    @Override
    public float getManaCost(LivingEntity caster) {
        return 225f;
    }

    @Override
    public ItemStack[] getReagents(LivingEntity caster) {
        return null;
    }

    @Override
    public void spawnParticles(World world, double x, double y, double z, LivingEntity caster, Entity target, Random rand, int colorModifier) {
        if (target instanceof LivingEntity && ((LivingEntity) target).isEntityUndead()) {
            for (int i = 0; i < 25; ++i) {
//                AMParticle particle = (AMParticle) ArsMagicaLegacy.proxy.particleManager.spawn(world, "symbols", x, y - 1, z);
//                if (particle != null) {
//                    particle.addRandomOffset(1, 1, 1);
//                    particle.AddParticleController(new ParticleHoldPosition(particle, 20, 1, true));
//                    particle.AddParticleController(new ParticleFloatUpward(particle, 0, -0.01f, 2, false));
//                    particle.AddParticleController(new ParticleFadeOut(particle, 2, false).setFadeSpeed(0.02f));
//                    particle.setParticleScale(0.1f);
//                    particle.setRGBColorF(1f, 0.2f, 0.2f);
//                }
            }
        } else {
            for (int i = 0; i < 25; ++i) {
//                AMParticle particle = (AMParticle) ArsMagicaLegacy.proxy.particleManager.spawn(world, "sparkle", x, y - 1, z);
//                if (particle != null) {
//                    particle.addRandomOffset(1, 1, 1);
//                    particle.AddParticleController(new ParticleFloatUpward(particle, 0, 0.1f, 1, false));
//                    particle.AddParticleController(new ParticleOrbitEntity(particle, target, 0.5f, 2, false).setIgnoreYCoordinate(true).SetTargetDistance(0.3f + rand.nextDouble() * 0.3));
//                    particle.setMaxAge(20);
//                    particle.setParticleScale(0.2f);
//                    particle.setRGBColorF(0.1f, 1f, 0.1f);
//                    if (colorModifier > -1) particle.setRGBColorF(((colorModifier >> 16) & 0xFF) / 255.0f, ((colorModifier >> 8) & 0xFF) / 255.0f, (colorModifier & 0xFF) / 255.0f);
//                }
            }
        }
    }

    //    @Override
//    public Set<Affinity> getAffinity() {
//        return Sets.newHashSet(Affinity.LIFE);
//    }
//
    @Override
    public ISpellIngredient[] getRecipe() {
        return new ISpellIngredient[]{
                new ItemStackSpellIngredient(new ItemStack(ModItems.GREEN_RUNE.get())),
                new ItemStackSpellIngredient(new ItemStack(ModItems.AUM.get()))
        };
    }

    //    @Override
//    public float getAffinityShift(Affinity affinity) {
//        return 0.05f;
//    }
//
    @Override
    public void encodeBasicData(CompoundNBT tag, ISpellIngredient[] recipe) {
    }
}
