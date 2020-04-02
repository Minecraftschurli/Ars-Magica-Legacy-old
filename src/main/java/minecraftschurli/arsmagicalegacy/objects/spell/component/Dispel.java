package minecraftschurli.arsmagicalegacy.objects.spell.component;

import com.google.common.collect.Sets;
import minecraftschurli.arsmagicalegacy.api.affinity.Affinity;
import minecraftschurli.arsmagicalegacy.api.spell.SpellComponent;
import minecraftschurli.arsmagicalegacy.api.spell.SpellModifiers;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ISpellIngredient;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ItemStackSpellIngredient;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ItemTagSpellIngredient;
import minecraftschurli.arsmagicalegacy.init.ModItems;
import minecraftschurli.arsmagicalegacy.init.ModSpellParts;
import minecraftschurli.arsmagicalegacy.init.ModTags;
import minecraftschurli.arsmagicalegacy.util.SummonUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

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
        return Sets.newHashSet(ModSpellParts.NONE.get());
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
    public ISpellIngredient[] getRecipe() {
        return new ISpellIngredient[]{
                new ItemTagSpellIngredient(ModTags.Items.GEMS_TOPAZ),
                new ItemStackSpellIngredient(new ItemStack(Items.MILK_BUCKET)),
                new ItemStackSpellIngredient(new ItemStack(ModItems.ARCANE_ASH.get())),
                new ItemStackSpellIngredient(new ItemStack(ModItems.PURPLE_RUNE.get()))
        };
    }

    @Override
    public void spawnParticles(World world, double x, double y, double z, LivingEntity caster, Entity target, Random rand, int colorModifier) {
//        for (int i = 0; i < 25; i++) {
//            AMParticle particle = (AMParticle) ArsMagica2.proxy.particleManager.spawn(world, "sparkle2", x, y, z);
//            if (particle != null) {
//                particle.addRandomOffset(1, 2, 1);
//                particle.AddParticleController(new ParticleOrbitEntity(particle, target, 0.1f + rand.nextFloat() * 0.1f, 1, false));
//                if (rand.nextBoolean()) particle.setRGBColorF(0.7f, 0.1f, 0.7f);
//                particle.setMaxAge(20);
//                particle.setParticleScale(0.1f);
//                if (colorModifier > -1) particle.setRGBColorF(((colorModifier >> 16) & 0xFF) / 255, ((colorModifier >> 8) & 0xFF) / 255, (colorModifier & 0xFF) / 255);
//            }
//        }
    }
}
