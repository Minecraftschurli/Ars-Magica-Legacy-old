package minecraftschurli.arsmagicalegacy.objects.spell.component;

import com.google.common.collect.Sets;
import minecraftschurli.arsmagicalegacy.api.affinity.Affinity;
import minecraftschurli.arsmagicalegacy.api.spell.SpellComponent;
import minecraftschurli.arsmagicalegacy.api.spell.SpellModifiers;
import minecraftschurli.arsmagicalegacy.init.ModAffinities;
import minecraftschurli.arsmagicalegacy.util.SpellUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.EnumSet;
import java.util.Random;
import java.util.Set;

public final class BanishRain extends SpellComponent {
    @Override
    public boolean applyEffectBlock(ItemStack stack, World world, BlockPos pos, Direction blockFace, double impactX, double impactY, double impactZ, LivingEntity caster) {
//        if (RitualShapeHelper.instance.matchesRitual(this, world, caster.getPosition()) {
//            if (world.isRaining()) return false;
//            RitualShapeHelper.instance.consumeReagents(this, world, target.getPosition());
//            world.getWorldInfo().setRainTime(0);
//            world.getWorldInfo().setRaining(true);
//            return true;
//        }
        if (!world.isRaining()) return false;
        world.getWorldInfo().setRainTime(24000);
        world.getWorldInfo().setRaining(false);
        return true;
    }

    @Override
    public boolean applyEffectEntity(ItemStack stack, World world, LivingEntity caster, Entity target) {
        return SpellUtil.doBlockWithEntity(this, stack, world, caster, target);
    }

    @Override
    public Set<Affinity> getAffinity() {
        return Sets.newHashSet(ModAffinities.WATER.get());
    }

    @Override
    public float getAffinityShift(Affinity affinity) {
        return 0.3f;
    }

    @Override
    public float getManaCost(LivingEntity caster) {
        return 750;
    }

    @Override
    public EnumSet<SpellModifiers> getModifiers() {
        return EnumSet.noneOf(SpellModifiers.class);
    }

    /*TODO move to ritual
    @Override
    public Set<ItemStack> getReagents(LivingEntity caster) {
        return Sets.newHashSet(new ItemStack(Items.SNOW), new ItemStack(Items.WATER_BUCKET));
    }*/

    //    @Override
//    public MultiblockStructureDefinition getRitualShape() {
//        return RitualShapeHelper.instance.hourglass;
//    }
//
    @Override
    public void spawnParticles(World world, double x, double y, double z, LivingEntity caster, Entity target, Random rand, int colorModifier) {
//        for (int i = 0; i < 25; i++) {
//            AMParticle particle = (AMParticle) ArsMagica2.proxy.particleManager.spawn(world, "water_ball", x, y, z);
//            if (particle != null) {
//                particle.addRandomOffset(5, 4, 5);
//                particle.AddParticleController(new ParticleFloatUpward(particle, 0f, 0.5f, 1, false));
//                particle.setMaxAge(25 + rand.nextInt(10));
//                if (colorModifier > -1) particle.setRGBColorF(((colorModifier >> 16) & 0xFF) / 255, ((colorModifier >> 8) & 0xFF) / 255, (colorModifier & 0xFF) / 255);
//            }
//        }
    }
}
