package minecraftschurli.arsmagicalegacy.objects.spell.component;

import minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import minecraftschurli.arsmagicalegacy.api.spell.*;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.*;
import minecraftschurli.arsmagicalegacy.init.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.util.text.*;
import net.minecraft.world.*;

import java.util.*;

@SuppressWarnings("deprecated")
public class Recall extends SpellComponent {
    @Override
    public boolean applyEffectBlock(ItemStack stack, World world, BlockPos pos, Direction blockFace, double impactX, double impactY, double impactZ, LivingEntity caster) {
        return false;
    }

    @Override
    public boolean applyEffectEntity(ItemStack stack, World world, LivingEntity caster, Entity target) {
        if (!(target instanceof LivingEntity)) return false;
        if (caster.isPotionActive(ModEffects.ASTRAL_DISTORTION.get()) || ((LivingEntity) target).isPotionActive(ModEffects.ASTRAL_DISTORTION.get()))
            if (caster instanceof PlayerEntity) {
                caster.sendMessage(new TranslationTextComponent(ArsMagicaAPI.MODID + ".chat.noRecall"));
                return false;
            }
//        if (RitualShapeHelper.instance.matchesRitual(this, world, target.getPosition())) {
//            ItemStack[] ritualRunes = RitualShapeHelper.instance.checkForRitual(this, world, target.getPosition());
//            if (ritualRunes != null) return handleRitualReagents(ritualRunes, world, target.getPosition(), caster, target);
//        }
        if (caster.dimension.getId() == -512) {
            if (caster instanceof PlayerEntity && !world.isRemote)
                caster.sendMessage(new TranslationTextComponent(ArsMagicaAPI.MODID + ".chat.noRecall"));
            return false;
        }
        if (!world.isRemote) target.setPositionAndUpdate(caster.getPosX(), caster.getPosY(), caster.getPosZ());
        return true;
    }

    @Override
    public EnumSet<SpellModifiers> getModifiers() {
        return EnumSet.noneOf(SpellModifiers.class);
    }

    @Override
    public float getManaCost(LivingEntity caster) {
        return 500;
    }

    @Override
    public void spawnParticles(World world, double x, double y, double z, LivingEntity caster, Entity target, Random rand, int colorModifier) {
        for (int i = 0; i < 25; ++i) {
//            AMParticle particle = (AMParticle) ArsMagicaLegacy.proxy.particleManager.spawn(world, "arcane", x, y - 1, z);
//            if (particle != null) {
//                particle.addRandomOffset(1, 0, 1);
//                particle.AddParticleController(new ParticleExpandingCollapsingRingAtPoint(particle, x, y - 1, z, 0.1, 3, 0.3, 1, false).setCollapseOnce());
//                particle.setMaxAge(20);
//                particle.setParticleScale(0.2f);
//                if (colorModifier > -1) particle.setRGBColorF(((colorModifier >> 16) & 0xFF) / 255, ((colorModifier >> 8) & 0xFF) / 255, (colorModifier & 0xFF) / 255);
//            }
        }
    }

    //    @Override
//    public Set<Affinity> getAffinity() {
//        return Sets.newHashSet(Affinity.ARCANE);
//    }
//
    @Override
    public ISpellIngredient[] getRecipe() {
        return new ISpellIngredient[]{
                new ItemStackSpellIngredient(new ItemStack(ModItems.ORANGE_RUNE.get())),
                new ItemStackSpellIngredient(new ItemStack(Items.COMPASS)),
                new ItemStackSpellIngredient(new ItemStack(Items.ENDER_PEARL)),
                new ItemStackSpellIngredient(new ItemStack(Items.MAP))
        };
    }

    //    @Override
//    public float getAffinityShift(Affinity affinity) {
//        return 0.1f;
//    }
//
//    @Override
//    public MultiblockStructureDefinition getRitualShape() {
//        return RitualShapeHelper.instance.ringedCross;
//    }
//
    @Override
    public ItemStack[] getReagents(LivingEntity caster) {
        return new ItemStack[]{
                new ItemStack(ModItems.RUNE.get()),
                new ItemStack(ModItems.RUNE.get()),
                new ItemStack(ModItems.RUNE.get())
        };
    }

    //    @Override
//    public int getReagentSearchRadius() {
//        return RitualShapeHelper.instance.ringedCross.getWidth();
//    }
//
    @Override
    public void encodeBasicData(CompoundNBT tag, ISpellIngredient[] recipe) {
    }
}
