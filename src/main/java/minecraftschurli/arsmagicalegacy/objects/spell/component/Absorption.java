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
import net.minecraft.potion.Effects;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.EnumSet;
import java.util.Random;
import java.util.Set;

public final class Absorption extends SpellComponent {

    @Override
    public boolean applyEffectBlock(ItemStack stack, World world, BlockPos blockPos, Direction blockFace, double impactX, double impactY, double impactZ, LivingEntity caster) {
        return false;
    }

    @Override
    public boolean applyEffectEntity(ItemStack stack, World world, LivingEntity caster, Entity target) {
        return SpellUtil.potionSpell(Effects.ABSORPTION, stack, world, caster, target);
    }

    @Override
    public Set<Affinity> getAffinity() {
        return Sets.newHashSet(ModAffinities.LIFE.get());
    }

    @Override
    public float getAffinityShift(Affinity affinity) {
        return 0.05F;
    }

    @Override
    public float getManaCost(LivingEntity caster) {
        return 100;
    }

    @Override
    public EnumSet<SpellModifiers> getModifiers() {
        return EnumSet.of(SpellModifiers.DURATION, SpellModifiers.BUFF_POWER);
    }

    /*TODO move to ritual
    @Override
    public Set<ItemStack> getReagents(LivingEntity caster) {
        return Sets.newHashSet(new ItemStack(Items.APPLE), new ItemStack(Items.GOLD_NUGGET));
    }*/

//    @Override
//    public MultiblockStructureDefinition getRitualShape() {
//        return RitualShapeHelper.instance.hourglass;
//    }

    @Override
    public void spawnParticles(ServerWorld world, double x, double y, double z, LivingEntity caster, Entity target, Random rand, int colorModifier) {
//        for (int i = 0; i < 15; i++) {
//            world.addOptionalParticle((IParticleData) ModParticles.LENS_FLARE.get(), x, y, z, 0, 0, 0);
//            SimpleParticle particle = new SimpleParticle(world, x, y, z);
//            particle.setMaxAge(25 + rand.nextInt(10));
//            particle.setColor(244, 200, 60);
//            if (colorModifier > -1) particle.setColor(((colorModifier >> 16) & 0xFF) / 255, ((colorModifier >> 8) & 0xFF) / 255, (colorModifier & 0xFF) / 255);
//        }
    }
}
