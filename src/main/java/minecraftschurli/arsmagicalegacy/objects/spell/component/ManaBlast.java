package minecraftschurli.arsmagicalegacy.objects.spell.component;

import minecraftschurli.arsmagicalegacy.api.spell.*;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.*;
import minecraftschurli.arsmagicalegacy.init.*;
import minecraftschurli.arsmagicalegacy.util.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;

import java.util.*;

public class ManaBlast extends SpellComponent {
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
        float consumed = MagicHelper.getMana(caster);
        MagicHelper.getManaCapability(caster).setMana(0);
        double damage = SpellUtils.getModifiedDoubleMul((consumed / 50F), stack, caster, target, world, SpellModifiers.DAMAGE);
        SpellUtils.attackTargetSpecial(stack, target, DamageSource.causeIndirectMagicDamage(caster, caster), SpellUtils.modifyDamage(caster, (float) damage));
        return true;
    }

    @Override
    public EnumSet<SpellModifiers> getModifiers() {
        return EnumSet.of(SpellModifiers.DAMAGE);
    }

    @Override
    public float getManaCost(LivingEntity caster) {
        return 0;
    }

    //    @Override
//    public float burnout(LivingEntity caster) {
//        return 0;
//    }
//
    @Override
    public ItemStack[] getReagents(LivingEntity caster) {
        return null;
    }

    @Override
    public void spawnParticles(World world, double x, double y, double z, LivingEntity caster, Entity target, Random rand, int colorModifier) {
//        double snapAngle = (2 * Math.PI) / (ArsMagicaLegacy.config.getGFXLevel() + 1) * 5;
        for (int j = 0; j < 4; j++) {
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
        }
    }

    //    @Override
//    public Set<Affinity> getAffinity() {
//        return Sets.newHashSet(Affinity.ARCANE);
//    }
//
//    @Override
//    public float getAffinityShift(Affinity affinity) {
//        return 0.01f;
//    }
//
    @Override
    public boolean applyEffectBlock(ItemStack stack, World world, BlockPos blockPos, Direction blockFace, double impactX, double impactY, double impactZ, LivingEntity caster) {
        return false;
    }

    @Override
    public void encodeBasicData(CompoundNBT tag, ISpellIngredient[] recipe) {
    }
}
