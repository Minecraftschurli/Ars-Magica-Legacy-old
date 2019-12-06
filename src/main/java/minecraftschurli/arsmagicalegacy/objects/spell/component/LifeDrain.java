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

public class LifeDrain extends SpellComponent {
    @Override
    public boolean applyEffectBlock(ItemStack stack, World world, BlockPos pos, Direction blockFace, double impactX, double impactY, double impactZ, LivingEntity caster) {
        return false;
    }

    @Override
    public boolean applyEffectEntity(ItemStack stack, World world, LivingEntity caster, Entity target) {
        if (world.isRemote || !(target instanceof LivingEntity) || ((LivingEntity) target).isEntityUndead()) return true;
        int magnitude = SpellUtils.getModifiedIntAdd(4, stack, caster, target, world, SpellModifiers.DAMAGE);
        boolean success = SpellUtils.attackTargetSpecial(stack, target, DamageSource.causeIndirectMagicDamage(caster, caster), SpellUtils.modifyDamage(caster, magnitude));
        if (success) {
            caster.heal((int) Math.ceil(magnitude / 4));
            return true;
        }
        return false;
    }

    @Override
    public float getManaCost(LivingEntity caster) {
        return 300;
    }

    @Override
    public ItemStack[] getReagents(LivingEntity caster) {
        return null;
    }

    @Override
    public void spawnParticles(World world, double x, double y, double z, LivingEntity caster, Entity target, Random rand, int colorModifier) {
        for (int i = 0; i < 15; ++i) {
//            AMParticle particle = (AMParticle) ArsMagicaLegacy.proxy.particleManager.spawn(world, "ember", x, y, z);
//            if (particle != null) {
//                particle.addRandomOffset(1, 1, 1);
//                particle.setIgnoreMaxAge(true);
//                particle.AddParticleController(new ParticleArcToEntity(particle, 1, caster, false).SetSpeed(0.03f).generateControlPoints());
//                particle.setRGBColorF(1, 0.2f, 0.2f);
//                particle.SetParticleAlpha(0.5f);
//                if (colorModifier > -1) particle.setRGBColorF(((colorModifier >> 16) & 0xFF) / 255.0f, ((colorModifier >> 8) & 0xFF) / 255.0f, (colorModifier & 0xFF) / 255.0f);
//            }
        }
    }

    @Override
    public EnumSet<SpellModifiers> getModifiers() {
        return EnumSet.of(SpellModifiers.DAMAGE);
    }

//    @Override
//    public Set<Affinity> getAffinity() {
//        return Sets.newHashSet(Affinity.LIFE);
//    }
//
    @Override
    public ISpellIngredient[] getRecipe() {
        return new ISpellIngredient[]{
                new ItemStackSpellIngredient(new ItemStack(ModItems.BLACK_RUNE.get())),
                new ItemTagSpellIngredient(ModTags.Items.GEMS_SUNSTONE),
                new ItemStackSpellIngredient(new ItemStack(ModItems.AUM.get()))
        };
    }

//    @Override
//    public float getAffinityShift(Affinity affinity) {
//        return 0.01f;
//    }
//
    @Override
    public void encodeBasicData(CompoundNBT tag, ISpellIngredient[] recipe) {
    }
}