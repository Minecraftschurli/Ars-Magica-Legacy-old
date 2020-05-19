package minecraftschurli.arsmagicalegacy.objects.spell.component;

import com.google.common.collect.Sets;
import minecraftschurli.arsmagicalegacy.api.affinity.Affinity;
import minecraftschurli.arsmagicalegacy.api.spell.SpellComponent;
import minecraftschurli.arsmagicalegacy.api.spell.SpellModifiers;
import minecraftschurli.arsmagicalegacy.init.ModAffinities;
import minecraftschurli.arsmagicalegacy.init.ModEffects;
import minecraftschurli.arsmagicalegacy.util.SpellUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.EnumSet;
import java.util.Random;
import java.util.Set;

public final class GravityWell extends SpellComponent {
    @Override
    public boolean applyEffectBlock(ItemStack stack, World world, BlockPos pos, Direction blockFace, double impactX, double impactY, double impactZ, LivingEntity caster) {
        return false;
    }

    @Override
    public boolean applyEffectEntity(ItemStack stack, World world, LivingEntity caster, Entity target) {
        return SpellUtil.potionSpell(ModEffects.GRAVITY_WELL.get(), stack, world, caster, target);
    }

    @Override
    public Set<Affinity> getAffinity() {
        return Sets.newHashSet(ModAffinities.EARTH.get(), ModAffinities.ENDER.get());
    }

    @Override
    public float getAffinityShift(Affinity affinity) {
        return affinity == ModAffinities.EARTH.get() ? 0.03f : 0.01f;
    }

    @Override
    public float getManaCost(LivingEntity caster) {
        return 80;
    }

    @Override
    public EnumSet<SpellModifiers> getModifiers() {
        return EnumSet.of(SpellModifiers.DURATION, SpellModifiers.BUFF_POWER);
    }

    /*TODO move to ritual
    @Override
    public Set<ItemStack> getReagents(LivingEntity caster) {
        return new ItemStack[]{
                new ItemStack(Items.BLAZE_POWDER)
        };
    }*/

    //    @Override
//    public MultiblockStructureDefinition getRitualShape() {
//        return RitualShapeHelper.instance.hourglass;
//    }
//
    @Override
    public void spawnParticles(ServerWorld world, double x, double y, double z, LivingEntity caster, Entity target, Random rand, int colorModifier) {
//        for (int i = 0; i < 25; i++) {
//            AMParticle particle = (AMParticle) ArsMagicaLegacy.proxy.particleManager.spawn(world, "pulse", x, y, z);
//            if (particle != null) {
//                particle.addRandomOffset(1, 2, 1);
//                particle.AddParticleController(new ParticleOrbitEntity(particle, target, 0.2f, 1, false));
//                particle.AddParticleController(new ParticleFadeOut(particle, 1, false).setFadeSpeed(0.05f).setKillParticleOnFinish(true));
//                particle.AddParticleController(new ParticleLeaveParticleTrail(particle, "pulse", false, 5, 2, false).addControllerToParticleList(new ParticleFloatUpward(particle, 0, -0.3f, 1, false)).setParticleRGB_F(0.7f, 0.2f, 0.9f).setTicksBetweenSpawns(2));
//                particle.setMaxAge(20);
//                particle.setParticleScale(0.01f);
//                if (colorModifier > -1) particle.setRGBColorF(((colorModifier >> 16) & 0xFF) / 255, ((colorModifier >> 8) & 0xFF) / 255, (colorModifier & 0xFF) / 255);
//            }
//        }
    }
}
