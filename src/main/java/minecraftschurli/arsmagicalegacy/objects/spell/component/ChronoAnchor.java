package minecraftschurli.arsmagicalegacy.objects.spell.component;

import com.google.common.collect.Sets;
import java.util.EnumSet;
import java.util.Random;
import java.util.Set;
import minecraftschurli.arsmagicalegacy.api.affinity.Affinity;
import minecraftschurli.arsmagicalegacy.api.spell.SpellComponent;
import minecraftschurli.arsmagicalegacy.api.spell.SpellModifiers;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ISpellIngredient;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ItemStackSpellIngredient;
import minecraftschurli.arsmagicalegacy.init.ModEffects;
import minecraftschurli.arsmagicalegacy.init.ModItems;
import minecraftschurli.arsmagicalegacy.init.ModSpellParts;
import minecraftschurli.arsmagicalegacy.util.SpellUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public final class ChronoAnchor extends SpellComponent {
    @Override
    public boolean applyEffectBlock(ItemStack stack, World world, BlockPos blockPos, Direction blockFace, double impactX, double impactY, double impactZ, LivingEntity caster) {
        return false;
    }

    @Override
    public boolean applyEffectEntity(ItemStack stack, World world, LivingEntity caster, Entity target) {
        if (target instanceof LivingEntity && !world.isRemote) {
            ((LivingEntity) target).getActivePotionEffects().remove(new EffectInstance(ModEffects.TEMPORAL_ANCHOR.get()));
            ((LivingEntity) target).addPotionEffect(new EffectInstance(ModEffects.TEMPORAL_ANCHOR.get(), SpellUtils.modifyIntMul(ModEffects.DEFAULT_BUFF_DURATION, stack, caster, target, world, SpellModifiers.DURATION), SpellUtils.countModifiers(SpellModifiers.BUFF_POWER, stack)));
            return true;
        }
        return false;
    }

    @Override
    public Set<Affinity> getAffinity() {
        return Sets.newHashSet(ModSpellParts.ARCANE.get());
    }

    @Override
    public float getAffinityShift(Affinity affinity) {
        return 0.15f;
    }

    @Override
    public float getManaCost(LivingEntity caster) {
        return 80;
    }

    @Override
    public EnumSet<SpellModifiers> getModifiers() {
        return EnumSet.of(SpellModifiers.DURATION, SpellModifiers.BUFF_POWER);
    }

    @Override
    public ISpellIngredient[] getRecipe() {
        return new ISpellIngredient[]{
                new ItemStackSpellIngredient(new ItemStack(Items.CLOCK)),
                new ItemStackSpellIngredient(new ItemStack(Items.NETHER_STAR)),
                new ItemStackSpellIngredient(new ItemStack(ModItems.WHITE_RUNE.get()))
        };
    }

    @Override
    public void spawnParticles(World world, double x, double y, double z, LivingEntity caster, Entity target, Random rand, int colorModifier) {
//        for (int i = 0; i < 25; i++) {
//            AMParticle particle = (AMParticle) ArsMagica2.proxy.particleManager.spawn(world, "clock", x, y, z);
//            if (particle != null) {
//                particle.addRandomOffset(1, 2, 1);
//                particle.setParticleScale(0.1f);
//                particle.AddParticleController(new ParticleOrbitEntity(particle, target, 0.1f + rand.nextFloat() * 0.1f, 1, false));
//                particle.AddParticleController(new ParticleFadeOut(particle, 1, false).setFadeSpeed(0.05f));
//                particle.setMaxAge(40);
//                if (colorModifier > -1) particle.setRGBColorF(((colorModifier >> 16) & 0xFF) / 255, ((colorModifier >> 8) & 0xFF) / 255, (colorModifier & 0xFF) / 255);
//            }
//        }
    }
}
