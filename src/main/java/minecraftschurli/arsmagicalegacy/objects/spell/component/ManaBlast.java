package minecraftschurli.arsmagicalegacy.objects.spell.component;

import com.google.common.collect.Sets;
import java.util.EnumSet;
import java.util.Random;
import java.util.Set;
import minecraftschurli.arsmagicalegacy.api.affinity.Affinity;
import minecraftschurli.arsmagicalegacy.api.capability.CapabilityHelper;
import minecraftschurli.arsmagicalegacy.api.spell.SpellComponent;
import minecraftschurli.arsmagicalegacy.api.spell.SpellModifiers;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ISpellIngredient;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ItemStackSpellIngredient;
import minecraftschurli.arsmagicalegacy.init.ModItems;
import minecraftschurli.arsmagicalegacy.init.ModSpellParts;
import minecraftschurli.arsmagicalegacy.util.SpellUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public final class ManaBlast extends SpellComponent {
    @Override
    public boolean applyEffectBlock(ItemStack stack, World world, BlockPos blockPos, Direction blockFace, double impactX, double impactY, double impactZ, LivingEntity caster) {
        return false;
    }

    @Override
    public ISpellIngredient[] getRecipe() {
        return new ISpellIngredient[]{
                new ItemStackSpellIngredient(new ItemStack(ModItems.PURPLE_RUNE.get())),
                new ItemStackSpellIngredient(new ItemStack(ModItems.GREATER_FOCUS.get())),
                new ItemStackSpellIngredient(new ItemStack(ModItems.MANA_FOCUS.get()))
        };
    }

    @Override
    public boolean applyEffectEntity(ItemStack stack, World world, LivingEntity caster, Entity target) {
        float consumed = CapabilityHelper.getMana(caster);
        CapabilityHelper.decreaseMana(caster, consumed);
        SpellUtils.attackTargetSpecial(stack, target, DamageSource.causeIndirectMagicDamage(caster, caster), SpellUtils.modifyDamage(caster, (float)SpellUtils.getModifiedDoubleMul((consumed / 50), stack, caster, target, world, SpellModifiers.DAMAGE)));
        return true;
    }

    @Override
    public Set<Affinity> getAffinity() {
        return Sets.newHashSet(ModSpellParts.ARCANE.get());
    }

    @Override
    public float getAffinityShift(Affinity affinity) {
        return 0.01f;
    }

    @Override
    public float getBurnout(LivingEntity caster) {
        return 0;
    }

    @Override
    public float getManaCost(LivingEntity caster) {
        return 0;
    }

    @Override
    public EnumSet<SpellModifiers> getModifiers() {
        return EnumSet.of(SpellModifiers.DAMAGE);
    }

    @Override
    public void spawnParticles(World world, double x, double y, double z, LivingEntity caster, Entity target, Random rand, int colorModifier) {
//        double snapAngle = (2 * Math.PI) / (ArsMagicaLegacy.config.getGFXLevel() + 1) * 5;
//        for (int j = 0; j < 4; j++) {
//            for (int i = 0; i < (ArsMagicaLegacy.config.getGFXLevel() + 1) * 5; i++) {
//                double posX = x + (Math.cos(snapAngle * i) * (j * 0.5));
//                double posZ = z + (Math.sin(snapAngle * i) * (j * 0.5));
//                AMParticle particle = (AMParticle) ArsMagicaLegacy.proxy.particleManager.spawn(world, "sparkle2", posX, target.posY + target.height / 2 + j * 0.5, posZ);
//                if (particle != null) {
//                    particle.setIgnoreMaxAge(true);
//                    particle.AddParticleController(new ParticleApproachEntity(particle, target, 0.15f, 0.1, 1, false));
//                    particle.AddParticleController(new ParticleFadeOut(particle, 2, false).setFadeSpeed(0.1f));
//                    particle.setRGBColorF(0.6f, 0f, 0.9f);
//                }
//            }
//        }
    }
}
