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

public class FrostDamage extends SpellComponent {
    @Override
    public boolean applyEffectBlock(ItemStack stack, World world, BlockPos pos, Direction blockFace, double impactX, double impactY, double impactZ, LivingEntity caster) {
        return false;
    }

    @Override
    public boolean applyEffectEntity(ItemStack stack, World world, LivingEntity caster, Entity target) {
        if (!(target instanceof LivingEntity)) return false;
        float baseDamage = 10;
        double damage = SpellUtils.getModifiedDoubleAdd(baseDamage, stack, caster, target, world, SpellModifiers.DAMAGE);
//        ((LivingEntity) target).addPotionEffect(new FrostSlowEffect(200, SpellUtils.countModifiers(SpellModifiers.BUFF_POWER, stack)));
        return false;//SpellUtils.attackTargetSpecial(stack, target, DamageSources.causeFrostDamage(caster), SpellUtils.modifyDamage(caster, (float) damage));
    }

    @Override
    public float getManaCost(LivingEntity caster) {
        return 80;
    }

    @Override
    public ItemStack[] getReagents(LivingEntity caster) {
        return null;
    }

    @Override
    public EnumSet<SpellModifiers> getModifiers() {
        return EnumSet.of(SpellModifiers.DAMAGE);
    }

    @Override
    public void spawnParticles(World world, double x, double y, double z, LivingEntity caster, Entity target, Random rand, int colorModifier) {
        for (int i = 0; i < 25; ++i) {
//            AMParticle particle = (AMParticle) ArsMagicaLegacy.proxy.particleManager.spawn(world, "snowflakes", x, y, z);
//            if (particle != null) {
//                particle.addRandomOffset(1, 0.5, 1);
//                particle.addVelocity(rand.nextDouble() * 0.2 - 0.1, rand.nextDouble() * 0.2, rand.nextDouble() * 0.2 - 0.1);
//                particle.setAffectedByGravity();
//                particle.setDontRequireControllers();
//                particle.setMaxAge(5);
//                particle.setParticleScale(0.1f);
//                if (colorModifier > -1) particle.setRGBColorF(((colorModifier >> 16) & 0xFF) / 255.0f, ((colorModifier >> 8) & 0xFF) / 255.0f, (colorModifier & 0xFF) / 255.0f);
//            }
        }
    }

//    @Override
//    public Set<Affinity> getAffinity() {
//        return Sets.newHashSet(Affinity.ICE);
//    }
//
    @Override
    public ISpellIngredient[] getRecipe() {
        return new ISpellIngredient[]{
                new ItemStackSpellIngredient(new ItemStack(ModItems.TOPAZ.get())),
                new ItemStackSpellIngredient(new ItemStack(ModItems.BLUE_RUNE.get())),
                new ItemStackSpellIngredient(new ItemStack(Items.SNOWBALL))
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