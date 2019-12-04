package minecraftschurli.arsmagicalegacy.spell.component;

import com.google.common.collect.*;
import minecraftschurli.arsmagicalegacy.api.affinity.*;
import minecraftschurli.arsmagicalegacy.api.spell.*;
import minecraftschurli.arsmagicalegacy.buffs.*;
import minecraftschurli.arsmagicalegacy.extensions.*;
import minecraftschurli.arsmagicalegacy.init.*;
import minecraftschurli.arsmagicalegacy.particles.*;
import minecraftschurli.arsmagicalegacy.utils.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;

import java.util.*;

public class Regeneration extends SpellComponent {
    @Override
    public boolean applyEffectBlock(ItemStack stack, World world, BlockPos blockPos, Direction blockFace, double impactX, double impactY, double impactZ, LivingEntity caster) {
        return false;
    }

    @Override
    public boolean applyEffectEntity(ItemStack stack, World world, LivingEntity caster, Entity target) {
        if (target instanceof LivingEntity) {
            if (EntityExtension.For((LivingEntity) target).canHeal()) {
                int duration = SpellUtils.getModifiedIntMul(PotionEffectsDefs.default_buff_duration, stack, caster, target, world, SpellModifiers.DURATION);
                //duration = SpellUtils.modifyDurationBasedOnArmor(caster, duration);
                if (!world.isRemote)
                    ((LivingEntity) target).addPotionEffect(new BuffEffectRegeneration(duration, SpellUtils.countModifiers(SpellModifiers.BUFF_POWER, stack)));
                EntityExtension.For((LivingEntity) target).setHealCooldown(60);
                return true;
            }
        }
        return false;
    }

    @Override
    public float getManaCost(LivingEntity caster) {
        return 540;
    }

    @Override
    public ItemStack[] reagents(LivingEntity caster) {
        return null;
    }

    @Override
    public EnumSet<SpellModifiers> getModifiers() {
        return EnumSet.of(SpellModifiers.BUFF_POWER, SpellModifiers.DURATION);
    }

    @Override
    public void spawnParticles(World world, double x, double y, double z, LivingEntity caster, Entity target, Random rand, int colorModifier) {
        for (int i = 0; i < 25; ++i) {
            AMParticle particle = (AMParticle) ArsMagica2.proxy.particleManager.spawn(world, "sparkle", x, y - 1, z);
            if (particle != null) {
                particle.addRandomOffset(1, 1, 1);
                particle.AddParticleController(new ParticleFloatUpward(particle, 0, 0.1f, 1, false));
                particle.AddParticleController(new ParticleOrbitEntity(particle, target, 0.5f, 2, false).setIgnoreYCoordinate(true).SetTargetDistance(0.3f + rand.nextDouble() * 0.3));
                particle.setMaxAge(20);
                particle.setParticleScale(0.2f);
                particle.setRGBColorF(0.1f, 1f, 0.8f);
                if (colorModifier > -1) {
                    particle.setRGBColorF(((colorModifier >> 16) & 0xFF) / 255.0f, ((colorModifier >> 8) & 0xFF) / 255.0f, (colorModifier & 0xFF) / 255.0f);
                }
            }
        }
    }

    @Override
    public Set<Affinity> getAffinity() {
        return Sets.newHashSet(Affinity.LIFE, Affinity.NATURE);
    }

    @Override
    public ISpellIngredient[] getRecipe() {
        return new ISpellIngredient[]{
                new ItemStack(ModItems.BLUE_RUNE.get()),
                Items.GOLDEN_APPLE
        };
    }

    @Override
    public float getAffinityShift(Affinity affinity) {
        return 0.05f;
    }

    @Override
    public void encodeBasicData(CompoundNBT tag, Object[] recipe) {
    }
}