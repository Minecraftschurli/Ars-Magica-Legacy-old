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
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ItemTagSpellIngredient;
import minecraftschurli.arsmagicalegacy.init.ModItems;
import minecraftschurli.arsmagicalegacy.init.ModSpellParts;
import minecraftschurli.arsmagicalegacy.init.ModTags;
import minecraftschurli.arsmagicalegacy.util.SpellUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public final class LifeDrain extends SpellComponent {
    @Override
    public boolean applyEffectBlock(ItemStack stack, World world, BlockPos pos, Direction blockFace, double impactX, double impactY, double impactZ, LivingEntity caster) {
        return false;
    }

    @Override
    public boolean applyEffectEntity(ItemStack stack, World world, LivingEntity caster, Entity target) {
        if (world.isRemote || !(target instanceof LivingEntity) || ((LivingEntity) target).isEntityUndead())
            return true;
        if (SpellUtils.attackTargetSpecial(stack, target, DamageSource.causeIndirectMagicDamage(caster, caster), SpellUtils.modifyDamage(caster, SpellUtils.getModifiedIntAdd(4, stack, caster, target, world, SpellModifiers.DAMAGE)))) {
            caster.heal((int) Math.ceil(SpellUtils.getModifiedIntAdd(4, stack, caster, target, world, SpellModifiers.DAMAGE) / 4f));
            return true;
        }
        return false;
    }

    @Override
    public Set<Affinity> getAffinity() {
        return Sets.newHashSet(ModSpellParts.LIFE.get());
    }

    @Override
    public float getAffinityShift(Affinity affinity) {
        return 0.01f;
    }

    @Override
    public float getManaCost(LivingEntity caster) {
        return 300;
    }

    @Override
    public EnumSet<SpellModifiers> getModifiers() {
        return EnumSet.of(SpellModifiers.DAMAGE);
    }

    @Override
    public ISpellIngredient[] getRecipe() {
        return new ISpellIngredient[]{
                new ItemStackSpellIngredient(new ItemStack(ModItems.AUM.get())),
                new ItemStackSpellIngredient(new ItemStack(ModItems.BLACK_RUNE.get())),
                new ItemTagSpellIngredient(ModTags.Items.GEMS_SUNSTONE)
        };
    }

    @Override
    public void spawnParticles(World world, double x, double y, double z, LivingEntity caster, Entity target, Random rand, int colorModifier) {
//        for (int i = 0; i < 15; ++i) {
//            AMParticle particle = (AMParticle) ArsMagicaLegacy.proxy.particleManager.spawn(world, "ember", x, y, z);
//            if (particle != null) {
//                particle.addRandomOffset(1, 1, 1);
//                particle.setIgnoreMaxAge(true);
//                particle.AddParticleController(new ParticleArcToEntity(particle, 1, caster, false).SetSpeed(0.03f).generateControlPoints());
//                particle.setRGBColorF(1, 0.2f, 0.2f);
//                particle.SetParticleAlpha(0.5f);
//                if (colorModifier > -1) particle.setRGBColorF(((colorModifier >> 16) & 0xFF) / 255, ((colorModifier >> 8) & 0xFF) / 255, (colorModifier & 0xFF) / 255);
//            }
//        }
    }
}
