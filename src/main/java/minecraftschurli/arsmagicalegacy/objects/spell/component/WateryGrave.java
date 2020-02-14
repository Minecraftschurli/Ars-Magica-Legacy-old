package minecraftschurli.arsmagicalegacy.objects.spell.component;

import minecraftschurli.arsmagicalegacy.api.spell.*;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.*;
import minecraftschurli.arsmagicalegacy.init.*;
import minecraftschurli.arsmagicalegacy.util.*;
import net.minecraft.block.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.potion.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import net.minecraftforge.common.*;

import java.util.*;

public class WateryGrave extends SpellComponent {
    @Override
    public boolean applyEffectEntity(ItemStack stack, World world, LivingEntity caster, Entity target) {
        if (target instanceof LivingEntity) {
            int duration = SpellUtils.getModifiedIntMul(ModEffects.DEFAULT_BUFF_DURATION, stack, caster, target, world, SpellModifiers.DURATION);
//            if (RitualShapeHelper.instance.matchesRitual(this, world, target.getPosition())) {
//                duration += (3600 * (SpellUtils.countModifiers(SpellModifiers.BUFF_POWER, stack) + 1));
//                RitualShapeHelper.instance.consumeReagents(this, world, target.getPosition());
//            }
            if (!world.isRemote)
                ((LivingEntity) target).addPotionEffect(new EffectInstance(ModEffects.WATERY_GRAVE.get(), duration, 0));
            return true;
        }
        return false;
    }

    @Override
    public float getManaCost(LivingEntity caster) {
        return 80;
    }

    @Override
    public void spawnParticles(World world, double x, double y, double z, LivingEntity caster, Entity target, Random rand, int colorModifier) {
        for (int i = 0; i < 25; ++i) {
//            AMParticle particle = (AMParticle) ArsMagicaLegacy.proxy.particleManager.spawn(world, "water_ball", x, y, z);
//            if (particle != null) {
//                particle.addRandomOffset(1, 2, 1);
//                particle.AddParticleController(new ParticleOrbitEntity(particle, target, 0.2f, 1, false));
//                particle.AddParticleController(new ParticleFadeOut(particle, 1, false).setFadeSpeed(0.05f).setKillParticleOnFinish(true));
//                particle.AddParticleController(new ParticleLeaveParticleTrail(particle, "water_ball", false, 5, 2, false).addControllerToParticleList(new ParticleFloatUpward(particle, 0, -0.3f, 1, false)).setParticleRGB_F(1, 1, 1).setTicksBetweenSpawns(2));
//                particle.setMaxAge(20);
//                particle.setParticleScale(0.01f);
//                if (colorModifier > -1) particle.setRGBColorF(((colorModifier >> 16) & 0xFF) / 255, ((colorModifier >> 8) & 0xFF) / 255, (colorModifier & 0xFF) / 255);
//            }
        }
    }

    //    @Override
//    public Set<Affinity> getAffinity() {
//        return Sets.newHashSet(Affinity.WATER);
//    }
//
    @Override
    public ISpellIngredient[] getRecipe() {
        return new ISpellIngredient[]{
                new ItemStackSpellIngredient(new ItemStack(ModItems.BLUE_RUNE.get())),
                new ItemStackSpellIngredient(new ItemStack(Items.LEATHER_BOOTS)),
                new ItemTagSpellIngredient(Tags.Items.STONE)
        };
    }

    //    @Override
//    public float getAffinityShift(Affinity affinity) {
//        return 0.05f;
//    }
//
//    @Override
//    public MultiblockStructureDefinition getRitualShape() {
//        return RitualShapeHelper.instance.hourglass;
//    }
//
    @Override
    public ItemStack[] getReagents(LivingEntity caster) {
        return new ItemStack[]{
                new ItemStack(Blocks.STONE)
        };
    }

    //    @Override
//    public int getReagentSearchRadius() {
//        return 3;
//    }
//
    @Override
    public void encodeBasicData(CompoundNBT tag, ISpellIngredient[] recipe) {
    }

    @Override
    public EnumSet<SpellModifiers> getModifiers() {
        return EnumSet.of(SpellModifiers.BUFF_POWER, SpellModifiers.DURATION);
    }

    @Override
    public boolean applyEffectBlock(ItemStack stack, World world, BlockPos blockPos, Direction blockFace, double impactX, double impactY, double impactZ, LivingEntity caster) {
        return false;
    }
}
