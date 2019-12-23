package minecraftschurli.arsmagicalegacy.objects.spell.component;

import minecraftschurli.arsmagicalegacy.api.spell.*;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.*;
import minecraftschurli.arsmagicalegacy.init.*;
import minecraftschurli.arsmagicalegacy.util.*;
import net.minecraft.entity.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.util.text.*;
import net.minecraft.world.*;

import java.util.*;

public class Charm extends SpellComponent {
    @Override
    public ISpellIngredient[] getRecipe() {
        return new ISpellIngredient[]{
                new ItemStackSpellIngredient(new ItemStack(ModItems.RED_RUNE.get())),
                new ItemStackSpellIngredient(new ItemStack(ModItems.LIFE_ESSENCE.get()))
        };
    }

    @Override
    public boolean applyEffectEntity(ItemStack stack, World world, LivingEntity caster, Entity target) {
        if (!(target instanceof CreatureEntity)/* || ((CreatureEntity) target).isPotionActive(ModEffects.CHARME) || EntityUtils.isSummon((CreatureEntity) target)*/)
            return false;
        int duration = SpellUtils.getModifiedIntMul(ModEffects.DEFAULT_BUFF_DURATION, stack, caster, target, world, SpellModifiers.DURATION);
//        if (RitualShapeHelper.instance.matchesRitual(this, world, target.getPosition())) {
//            duration += (3600 * (SpellUtils.countModifiers(SpellModifiers.BUFF_POWER, stack) + 1));
//            RitualShapeHelper.instance.consumeReagents(this, world, target.getPosition());
//        }
        if (target instanceof AnimalEntity) {
            ((AnimalEntity) target).setInLove(null);
            return true;
        }
//        if (EntityExtension.For(caster).getCanHaveMoreSummons()) {
//            if (caster instanceof PlayerEntity) {
//                if (target instanceof CreatureEntity) {
//                    CharmEffect charmBuff = new CharmEffect(duration, CharmEffect.CHARM_TO_PLAYER);
//                    charmBuff.setCharmer(caster);
//                    ((CreatureEntity) target).addPotionEffect(charmBuff);
//                }
//                return true;
//            } else if (caster instanceof LivingEntity) {
//                if (target instanceof CreatureEntity) {
//                    CharmEffect charmBuff = new CharmEffect(duration, CharmEffect.CHARM_TO_MONSTER);
//                    charmBuff.setCharmer(caster);
//                    ((CreatureEntity) target).addPotionEffect(charmBuff);
//                }
//                return true;
//            }
//        } else {
        if (caster instanceof PlayerEntity)
            ((PlayerEntity) caster).sendMessage(new TranslationTextComponent("minecraftschurli.arsmagicalegacy.tooltip.noMoreSummons"));
        return true;
//        }
//        return false;
    }

    @Override
    public EnumSet<SpellModifiers> getModifiers() {
        return EnumSet.of(SpellModifiers.DURATION);
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
        for (int i = 0; i < 10; ++i) {
//            AMParticle particle = (AMParticle) ArsMagica2.proxy.particleManager.spawn(world, "heart", x, y, z);
//            if (particle != null) {
//                particle.addRandomOffset(1, 2, 1);
//                particle.AddParticleController(new ParticleFloatUpward(particle, 0, 0.05f + rand.nextFloat() * 0.1f, 1, false));
//                particle.setMaxAge(20);
//                if (colorModifier > -1) particle.setRGBColorF(((colorModifier >> 16) & 0xFF) / 255.0f, ((colorModifier >> 8) & 0xFF) / 255.0f, (colorModifier & 0xFF) / 255.0f);
//            }
        }
    }

    //    @Override
//    public Set<Affinity> getAffinity() {
//        return Sets.newHashSet(Affinity.LIFE);
//    }
//
//    @Override
//    public float getAffinityShift(Affinity affinity) {
//        return 0.1f;
//    }
//
//    @Override
//    public MultiblockStructureDefinition getRitualShape() {
//        return RitualShapeHelper.instance.hourglass;
//    }
//
//    @Override
//    public int getReagentSearchRadius() {
//        return 3;
//    }
//
    @Override
    public void encodeBasicData(CompoundNBT tag, ISpellIngredient[] recipe) {
    }

    @Override
    public boolean applyEffectBlock(ItemStack stack, World world, BlockPos blockPos, Direction blockFace, double impactX, double impactY, double impactZ, LivingEntity caster) {
        return false;
    }
}
