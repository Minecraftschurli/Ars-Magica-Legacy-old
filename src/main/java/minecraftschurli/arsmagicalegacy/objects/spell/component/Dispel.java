package minecraftschurli.arsmagicalegacy.objects.spell.component;

import com.google.common.collect.Sets;
import java.util.ArrayList;
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
import minecraftschurli.arsmagicalegacy.util.SummonUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public final class Dispel extends SpellComponent {
    @Override
    public boolean applyEffectBlock(ItemStack stack, World world, BlockPos blockPos, Direction blockFace, double impactX, double impactY, double impactZ, LivingEntity caster) {
        return false;
    }

    @Override
    public boolean applyEffectEntity(ItemStack stack, World world, LivingEntity caster, Entity target) {
        if (!(target instanceof LivingEntity)) return false;
        if (SummonUtil.isSummon((LivingEntity) target)) {
            if (SummonUtil.getOwner((LivingEntity) target) == caster.getEntityId()) {
                target.attackEntityFrom(DamageSource.MAGIC, 50000);
                return true;
            }
        }
        List<EffectInstance> effectsToRemove = new ArrayList<>();
        int left = 6;
        for (EffectInstance pe : ((LivingEntity) target).getActivePotionEffects()) {
            int amplifier = pe.getAmplifier();
            if (left >= amplifier) {
                left -= amplifier;
                if (!world.isRemote) ((LivingEntity) target).removePotionEffect(pe.getPotion());
                effectsToRemove.add(pe);
            }
        }
        if (effectsToRemove.size() == 0) return false;
        if (!world.isRemote)
            for (EffectInstance e : effectsToRemove) ((LivingEntity) target).removePotionEffect(e.getPotion());
        return true;
    }

    @Override
    public Set<Affinity> getAffinity() {
        return Sets.newHashSet(ModAffinities.NONE.get());
    }

    @Override
    public float getAffinityShift(Affinity affinity) {
        return 0;
    }

    @Override
    public float getManaCost(LivingEntity caster) {
        return 200;
    }

    @Override
    public EnumSet<SpellModifiers> getModifiers() {
        return EnumSet.noneOf(SpellModifiers.class);
    }

    @Override
    public void spawnParticles(ServerWorld world, double x, double y, double z, LivingEntity caster, Entity target, Random rand, int colorModifier) {
        for (int i = 0; i < 25; i++) ParticleUtil.addParticle(world, target, ModParticles.SHINY, colorModifier, 0xb217b2, x + rand.nextInt(2) - 1, y + rand.nextInt(4) - 2, z + rand.nextInt(2) - 1);
    }
}
